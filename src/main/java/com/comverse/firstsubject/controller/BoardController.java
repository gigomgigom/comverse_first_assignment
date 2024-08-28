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

	// 목록 조회 화면 이동
	@GetMapping("/list")
	public String boardList(Model model, SearchIndex searchIndex) {
		if(searchIndex.getPageNo() == null || searchIndex.getPageNo().equals("")) {
			searchIndex.setPageNo("1");
		}
		// 목록 가져오기
		List<BoardDto> list = boardService.getBoardList(searchIndex);
		
		model.addAttribute("searchIndex", searchIndex);
		model.addAttribute("boardList", list);
		model.addAttribute("pager", searchIndex.getPager());
		return "boardlist";
	}

	// 상세 조회 화면 이동
	@GetMapping("/detail")
	public String detailBoard(Model model, int boardNo, String searchCtg, String keyword, String pageNo, Authentication auth) {
		BoardDto board = boardService.getBoardDetail(boardNo);
		SearchIndex searchIndex = new SearchIndex(searchCtg, keyword, pageNo);
		log.info(searchIndex.toString());
		if (board == null) {
			// 해당 게시판은 존재하지 않습니다! 알림 띄우고 리다이렉트하기
			model.addAttribute("msg", "존재하지 않는 게시글입니다.");
			model.addAttribute("url", "/board/list?searchCtg="+searchCtg+"&keyword="+keyword+"&pageNo="+pageNo);
			return "alert";
		} else {
			model.addAttribute("board", board);
			if (board.getBoardDepth() != 0) {
				BoardDto parentBoard = boardService.getParentBoard(board.getBoardNo());
				model.addAttribute("parentBoard", parentBoard);
			}
			// 회원 접속 여부 파악
			if (auth != null) {
				model.addAttribute("id", auth.getName());
			} else {
				model.addAttribute("id", "anonymous");
			}
		}
		List<BoardDto> replyList = boardService.getReplyList(boardNo);
		model.addAttribute("replyList", replyList);
		return "boarddetail";
	}

	// 게시물 이미지 다운로드
	@GetMapping("/download_img")
	public void downloadImg(HttpServletResponse rs, int boardNo) {
		BoardDto board = boardService.getBoardImg(boardNo);
		if (board == null) {
			log.info("No image");
		} else {
			try {
				rs.setContentType(board.getImgType());
				String fileName = new String(board.getImgName().getBytes("UTF-8"), "ISO-8859-1");
				rs.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
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

	// 게시글 작성 화면 이동
	// 인증받은 자만 요청할수 있게끔 해야함.
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String writeBoard() {
		return "boardwrite";
	}

	// 게시글 작성하기
	// 인증받은 자만 요청할 수 있게끔 해야함.
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/save")
	public String saveBoard(@Valid BoardDto boardDto, Authentication auth, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("msg", "제목과 내용은 필수적인 내용입니다.");
			model.addAttribute("url", "/");
			return "alert";
		}

		if (boardDto.getBattach() != null && !boardDto.getBattach().isEmpty()) {
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
		boardDto.setBoardStep(1);
		boardDto.setBoardDepth(0);
		boardService.saveBoardOnDataBase(boardDto);
		return "redirect:/board/list";
	}

	// 게시글 수정 화면 이동
	// 작성한 자만 요청할 수 있게 해야함.
	@GetMapping("/modify")
	public String modifyBoard(Authentication auth, Model model, int boardNo) {
		BoardDto board = boardService.getBoardDetail(boardNo);
		if (board.getBoardWriter() != null) {
			if (auth != null && auth.getName().equals(board.getBoardWriter())) {
				model.addAttribute("board", board);
				return "boardupdate";
			} else {
				model.addAttribute("msg", "권한이 유효하지않습니다");
				model.addAttribute("url", "/board/detail?boardNo=" + boardNo);
				return "alert";
			}
		} else {
			model.addAttribute("board", board);
			return "boardupdate";
		}

	}

	// 게시글 수정하기
	// 작성한 자만 요청할 수 있게 해야함.
	// 1. 데이터베이스에서 해당 게시글의 정보를 가져와서 작성한자를 비교하기
	// 2. 게시글 수정 화면에서 넘어오는 값에 게시글의 작성자정보를 추가하여 인증객체와 비교한다.

	// 익명 게시글 수정 기능 구현해야함
	@PostMapping("/update")
	public String updateBoard(Model model, Authentication auth, BoardDto board) {
		BoardDto boardInDB = boardService.getBoardWriterByBoNo(board.getBoardNo());
		if (boardInDB == null) {
			model.addAttribute("msg", "존재하지 않은 게시물입니다.");
			model.addAttribute("url", "/");
			return "alert";
		} else {
			if(boardInDB.getBoardWriter() != null) {
				if (auth != null && boardInDB.getBoardWriter().equals(auth.getName())) {
					if (board.getBattach() != null && !board.getBattach().isEmpty()) {
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
					return "redirect:/board/detail?boardNo=" + board.getBoardNo();
				} else {
					//작성한자가 아니라는 알림 메세지 출력
					model.addAttribute("msg", "권한이 유효하지않습니다.");
					model.addAttribute("url", "/");
					return "alert";
				}
			} else {
				if(boardInDB.getAnonPw().equals(board.getAnonPw())) {
					if (board.getBattach() != null && !board.getBattach().isEmpty()) {
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
					return "redirect:/board/detail?boardNo=" + board.getBoardNo();
				} else {
					model.addAttribute("msg", "입력하신 비밀번호가 일치하지 않습니다.");
					model.addAttribute("url", "/");
					return "alert";
				}
			}			
		}
	}

	// 게시글 삭제
	// 작성한 자만 요청할 수 있게 해야함.
	@GetMapping("/delete")
	public String deleteBoard(Model model, Authentication auth, int boardNo, String anonPw) {
		BoardDto board = boardService.getBoardWriterByBoNo(boardNo);
		if (board == null) {
			model.addAttribute("msg", "존재하지 않은 개시글입니다.");
			model.addAttribute("url", "/board/detail?boardNo=" + boardNo);
			return "alert";
		} else if (board.getBoardWriter() != null) {
			if (auth != null && auth.getName().equals(board.getBoardWriter())) {
				boardService.deleteBoard(boardNo);
				return "redirect:/board/list";
			} else {
				model.addAttribute("msg", "권한이 유효하지않습니다.");
				model.addAttribute("url", "/board/detail?boardNo=" + boardNo);
				return "alert";
			}
		} else {
			if (board.getAnonPw().equals(anonPw)) {
				boardService.deleteBoard(boardNo);
				return "redirect:/board/list";
			} else {
				model.addAttribute("msg", "입력하신 비밀번호와 등록된 비밀번호가 일치하지 않습니다.");
				model.addAttribute("url", "/board/detail?boardNo=" + boardNo);
				return "alert";
			}
		}
	}

	// 댓글 작성
	// 인증된 자만 요청할 수 있게 해야함
	@PostMapping("/write_comment")
	public String writeComment(Model model, Authentication auth, @Valid BoardDto reply) {
		if (reply.getBattach() != null && !reply.getBattach().isEmpty()) {
			MultipartFile mf = reply.getBattach();
			reply.setImgName(mf.getOriginalFilename());
			reply.setImgType(mf.getContentType());
			try {
				reply.setImgData(mf.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (auth != null) {
			reply.setBoardWriter(auth.getName());
		} else {
			reply.setAnonId("(비) " + reply.getAnonId());
		}
		reply.setBoardEnabled(true);
		reply.setBoardCtg(1);
		// invoke Service method
		boardService.writeReply(reply);
		return "redirect:/board/detail?boardNo=" + reply.getPreBoard();
	}

}
