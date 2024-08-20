package com.comverse.firstsubject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comverse.firstsubject.dto.BoardDto;
import com.comverse.firstsubject.dto.ReplyDto;
import com.comverse.firstsubject.dto.SearchIndex;
import com.comverse.firstsubject.service.BoardService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	//목록 조회 화면 이동
	@GetMapping("/list")
	public String boardList(Model model, HttpSession session, SearchIndex searchIndex) {
		
		//검색 조건 불러오기 / 세팅
		if(searchIndex.getSearchCtg() == null) {
			searchIndex.setSearchCtg((String)session.getAttribute("searchCtg"));
		}
		if(searchIndex.getKeyword() == null) {
			searchIndex.setKeyword((String) session.getAttribute("keyword"));
		}
		if(searchIndex.getPageNo() == null) {
			searchIndex.setPageNo((String)session.getAttribute("pageNo"));
			if(searchIndex.getPageNo() == null) {
				searchIndex.setPageNo("1");
			}
		}
		session.setAttribute("searchCtg", searchIndex.getSearchCtg());
		session.setAttribute("keyword", searchIndex.getKeyword());
		session.setAttribute("pageNo", searchIndex.getPageNo());
		
		//목록 가져오기
		List<BoardDto> list = boardService.getBoardList(searchIndex);
		
		model.addAttribute("boardList", list);
		model.addAttribute("pager", searchIndex.getPager());
		return "boardlist";
	}	
	
	//상세 조회 화면 이동
	@GetMapping("/detail")
	public String detailBoard(Model model, int boardNo, Authentication auth) {
		BoardDto board = boardService.getBoardDetail(boardNo);
		if(board == null) {
			//해당 게시판은 존재하지 않습니다! 알림 띄우고 리다이렉트하기 
			return null;
		} else {		
			model.addAttribute("board", board);
			if(auth != null) {
				model.addAttribute("id", auth.getName());
			} else {
				model.addAttribute("id", "anonymous");
			}
		}
		List<ReplyDto> replyList = boardService.getReplyList(boardNo);
		model.addAttribute("replyList", replyList);
		return "boarddetail";
	}
	
	//게시물 이미지 다운로드
	@GetMapping("/download_img")
	public void downloadImg(HttpServletResponse rs, int boardNo) {
		BoardDto board = boardService.getBoardImg(boardNo);
		try {
			rs.setContentType(board.getImgType());
			String fileName = new String(board.getImgName().getBytes("UTF-8"), "ISO-8859-1");
			rs.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		} catch (UnsupportedEncodingException e) {
			log.info(e.toString());
		}
		try {
			OutputStream os = rs.getOutputStream();
			os.write(board.getImgData());
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//게시글 작성 화면 이동
	//인증받은 자만 요청할수 있게끔 해야함.
	@GetMapping("/write")
	public String writeBoard() {
		return "boardwrite";
	}
	
	//게시글 작성하기
	//인증받은 자만 요청할 수 있게끔 해야함.
	@PostMapping("/save")
	public String saveBoard(BoardDto boardDto, Authentication auth) {
		if(boardDto.getBattach() != null && !boardDto.getBattach().isEmpty()) {
			MultipartFile mf = boardDto.getBattach();
			boardDto.setImgName(mf.getOriginalFilename());
			boardDto.setImgType(mf.getContentType());
			try {
				boardDto.setImgData(mf.getBytes());
			} catch (Exception e) {
				log.info(e.toString());
			}
		}
		boardDto.setBattach(null);
		boardDto.setBoardWriter(auth.getName());
		boardDto.setBoardEnabled(true);
		boardService.saveBoardOnDataBase(boardDto);
		return "redirect:/board/list";
	}
	
	//게시글 수정 화면 이동
	//작성한 자만 요청할 수 있게 해야함.
	@GetMapping("/modify")
	public String modifyBoard(Authentication auth, Model model, int boardNo) {
		BoardDto board = boardService.getBoardDetail(boardNo);
		if(!auth.getName().equals(board.getBoardWriter())) {
			return "redirect:/";
		} else {
			model.addAttribute("board", board);
			return "boardupdate";
		}
	}
	
	//게시글 수정하기
	//작성한 자만 요청할 수 있게 해야함.
	//1. 데이터베이스에서 해당 게시글의 정보를 가져와서 작성한자를 비교하기
	//2. 게시글 수정 화면에서 넘어오는 값에 게시글의 작성자정보를 추가하여 인증객체와 비교한다.
	@PostMapping("/update")
	public String updateBoard(Authentication auth, BoardDto board) {
		if(!board.getBoardWriter().equals(auth.getName())) {
			//작성한자가 아니라는 알림 메세지 출력
			return "redirect:/";
		} else {
			if(board.getBattach() != null && !board.getBattach().isEmpty()) {
				MultipartFile mf = board.getBattach();
				board.setImgName(mf.getOriginalFilename());
				board.setImgType(mf.getContentType());
				try {
					board.setImgData(mf.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				board.setBattach(null);
			}
			boardService.modifyBoard(board);
			return "redirect:/board/detail?boardNo="+board.getBoardNo();
		}
	}
	
	//게시글 삭제
	//작성한 자만 요청할 수 있게 해야함.
	@GetMapping("/delete")
	public String deleteBoard(Authentication auth, int boardNo, String boardWriter) {
		if(auth == null) {
			//권한 부족 알림 
			return "redirect:/board/list";
		} else if(!auth.getName().equals(boardWriter)) {
			//권한 부족 알림 
			return "redirect:/board/list";
		} else {
			log.info("권한 유효함");
			boardService.deleteBoard(boardNo);
			return "redirect:/board/list";
		}
	}
	
	//댓글 작성
	//인증된 자만 요청할 수 있게 해야함
	@PostMapping("/write_comment")
	public String writeComment(Authentication auth, ReplyDto reply) {
		if(auth == null) {
			//권한 부족 알림
			return "redirect:/board/list";
		}
		reply.setReplyWriter(auth.getName());
		reply.setReplyEnabled(true);
		boardService.writeReply(reply);
		return "redirect:/board/detail?boardNo="+reply.getReplyBoard();
	}
	
	//댓글 수정
	//작성한 자만 요청할 수 있게 해야함
	@ResponseBody
	@PostMapping("/update_comment")
	public String updateComment(@RequestBody ReplyDto reply){
		
		return "/board/detail?boardNo="+reply.getReplyBoard();
	}
	
	//댓글 삭제
	//작성한 자만 요청할 수 있게 해야함
	@GetMapping("/remove_comment")
	public String removeComment(Authentication auth, ReplyDto reply) {
		if(auth == null) {
			//권한 부족 알림
			return "redirect:/board/list";
		} else if(!auth.getName().equals(reply.getReplyWriter())) {
			//권한 부족 알림 
			return "redirect:/board/list";
		} else {
			boardService.removeComment(reply.getReplyNo());
			return "redirect:/board/detail?boardNo="+reply.getReplyBoard();
		}
	}
	
	
}
