package com.comverse.firstsubject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import jakarta.validation.Valid;
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
		} else {
			//검색을 했을때 무조건 1page로 이동하게끔 함.
			searchIndex.setPageNo("1");
		}
		if(searchIndex.getPageNo() == null || Integer.parseInt(searchIndex.getPageNo()) < 1) {
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
	public String detailBoard(Model model, int boardNo, String prev, String next, Authentication auth) {
		BoardDto board = boardService.getBoardDetail(boardNo);
		if(board == null) {
			//해당 게시판은 존재하지 않습니다! 알림 띄우고 리다이렉트하기 
			model.addAttribute("msg", "존재하지 않는 게시글입니다.");
			model.addAttribute("url", "/");
			return "alert";
		} else {		
			model.addAttribute("board", board);
			Map<String, Object> preBoardWithStts = new HashMap<>();
			//상단 게시글 참조 여부 파악
			if(board.getPreBoard() != 0) {
				BoardDto preBoard = boardService.getBoardDetail(board.getPreBoard());
				//삭제 여부 파악
				if(preBoard != null) {
					preBoardWithStts.put("preBoard", preBoard);
					preBoardWithStts.put("stts", true);
				} else {
					preBoardWithStts.put("stts", false);
				}
				model.addAttribute("preBoardWithStts", preBoardWithStts);
			} else {
				model.addAttribute("preBoardWithStts", null);
			}
			//회원 접속 여부 파악
			if(auth != null) {
				model.addAttribute("id", auth.getName());
			} else {
				model.addAttribute("id", "anonymous");
			}
		}
		if(prev != null && !prev.isEmpty()) {
			int prevNo = Integer.parseInt(prev);
			BoardDto prevBo = boardService.getBoardDetail(prevNo);
			model.addAttribute("prev", prevBo);
		} else {
			model.addAttribute("prev", null);
		}
		if(next != null && !next.isEmpty()) {
			int nextNo = Integer.parseInt(next);
			BoardDto nextBo = boardService.getBoardDetail(nextNo);
			model.addAttribute("next", nextBo);
		} else {
			model.addAttribute("next", null);
		}
		List<BoardDto> replyList = boardService.getReplyList(boardNo);
		model.addAttribute("replyList", replyList);
		return "boarddetail";
	}
	
	//게시물 이미지 다운로드
	@GetMapping("/download_img")
	public void downloadImg(HttpServletResponse rs, int boardNo) {
		BoardDto board = boardService.getBoardImg(boardNo);
		if(board == null) {
			log.info("No image");
		} else {
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
	}
	//게시글 작성 화면 이동
	//인증받은 자만 요청할수 있게끔 해야함.
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String writeBoard() {
		return "boardwrite";
	}
	
	//게시글 작성하기
	//인증받은 자만 요청할 수 있게끔 해야함.
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/save")
	public String saveBoard(@Valid BoardDto boardDto, Authentication auth, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", "제목과 내용은 필수적인 내용입니다.");
			model.addAttribute("url", "/");
			return "alert";
		}
		
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
		boardDto.setBoardCtg(1);
		boardDto.setBoardEnabled(true);
		boardService.saveBoardOnDataBase(boardDto);
		return "redirect:/board/list";
	}
	
	//게시글 수정 화면 이동
	//작성한 자만 요청할 수 있게 해야함.
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify")
	public String modifyBoard(Authentication auth, Model model, int boardNo) {
		BoardDto board = boardService.getBoardDetail(boardNo);
		if(!auth.getName().equals(board.getBoardWriter())) {
			model.addAttribute("msg", "권한이 유효하지않습니다");
			model.addAttribute("url", "/board/detail?boardNo="+boardNo);
			return "alert";
		} else {
			model.addAttribute("board", board);
			return "boardupdate";
		}
	}
	
	//게시글 수정하기
	//작성한 자만 요청할 수 있게 해야함.
	//1. 데이터베이스에서 해당 게시글의 정보를 가져와서 작성한자를 비교하기
	//2. 게시글 수정 화면에서 넘어오는 값에 게시글의 작성자정보를 추가하여 인증객체와 비교한다.
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String updateBoard(Model model, Authentication auth, BoardDto board) {
		String boardWriter = boardService.getBoardWriterByBoNo(board.getBoardNo());
		if(boardWriter == null) {
			model.addAttribute("msg", "존재하지 않은 게시물입니다.");
			model.addAttribute("url", "/");
			return "alert";
		} else if(!boardWriter.equals(auth.getName())) {
			//작성한자가 아니라는 알림 메세지 출력
			model.addAttribute("msg", "권한이 유효하지않습니다.");
			model.addAttribute("url", "/");
			return "alert";
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
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String deleteBoard(Model model, Authentication auth, int boardNo, String boardWriter) {
		String boWriter = boardService.getBoardWriterByBoNo(boardNo);
		if(boWriter == null) {
			model.addAttribute("msg", "존재하지 않은 개시글입니다.");
			model.addAttribute("url", "/board/detail?boardNo="+boardNo);
			return "alert";
		} else if(auth == null) {
			model.addAttribute("msg", "권한이 유효하지않습니다");
			model.addAttribute("url", "/board/detail?boardNo="+boardNo);
			return "alert";
		} else if(!auth.getName().equals(boWriter)) {
			model.addAttribute("msg", "권한이 유효하지않습니다");
			model.addAttribute("url", "/board/detail?boardNo="+boardNo);
			return "alert";
		} else {
			boardService.deleteBoard(boardNo);
			return "redirect:/board/list";
		}
	}
	
	//댓글 작성
	//인증된 자만 요청할 수 있게 해야함
	@PostMapping("/write_comment")
	public String writeComment(Model model, Authentication auth, @Valid BoardDto reply) {
		if(reply.getBattach() != null && !reply.getBattach().isEmpty()) {
			MultipartFile mf = reply.getBattach();
			reply.setImgName(mf.getOriginalFilename());
			reply.setImgType(mf.getContentType());
			try {
				reply.setImgData(mf.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(auth != null) {
			reply.setBoardWriter(auth.getName());
		} else {
			reply.setAnonId("(비) " + reply.getAnonId());
		}
		reply.setBoardEnabled(true);
		reply.setBoardCtg(1);
		// invoke Service method
		boardService.writeReply(reply);
		return "redirect:/board/detail?boardNo="+reply.getPreBoard();
	}
	
	//댓글 수정
	//작성한 자만 요청할 수 있게 해야함
	@PreAuthorize("isAuthenticated() and (#reply.getReplyWriter() == #auth.getName)")
	@ResponseBody
	@PostMapping("/update_comment")
	public ResponseEntity<?> updateComment(@Valid @RequestBody BoardDto reply, Authentication auth, Model model, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("blank data");
		}
		if(auth == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No auth");
		}
		if(!reply.getBoardWriter().equals(auth.getName())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("forbidden");
		}
		// invoke Service method
		return ResponseEntity.ok("/board/detail?boardNo="+reply.getPreBoard());
	}
	
	//댓글 삭제
	//작성한 자만 요청할 수 있게 해야함
	@PreAuthorize("isAuthenticated() and (#reply.getReplyWriter() == #auth.getName)")
	@GetMapping("/remove_comment")
	public String removeComment(Model model, Authentication auth, BoardDto reply) {
		return null;
	}
	
	//비회원 아이디 비밀번호 일치여부 파악
	@PostMapping("/check_anon")
	public ResponseEntity<?> checkAnon(String anonId, String anonPw, int boardNo) {
		Boolean result = boardService.checkAnonIdPw(anonId, anonPw, boardNo);
		return ResponseEntity.ok(result);
	}
	
}
