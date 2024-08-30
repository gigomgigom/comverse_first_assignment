package com.comverse.firstsubject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.comverse.firstsubject.dto.BoardDto;
import com.comverse.firstsubject.dto.SearchIndex;
import com.comverse.firstsubject.service.NoticeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired 
	private NoticeService noticeService;
	
	//공지사항 목록 조회
	@GetMapping("/list")
	public String noticeList(Model model, String searchCtg, String keyword, @RequestParam(defaultValue="1") String pageNo) {
		SearchIndex searchIndex = new SearchIndex(searchCtg, keyword, pageNo);
		log.info(searchIndex.toString());
		List<BoardDto> noticeList = noticeService.getNoticeList(searchIndex);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("searchIndex", searchIndex);
		return "notice/list";
	}
	//공지사항 상세보기 조회
	@GetMapping("/detail")
	public String noticeDetail(int boardNo, String searchCtg, String keyword, String pageNo, Model model, Authentication auth) {
		SearchIndex searchIndex = new SearchIndex(searchCtg, keyword, pageNo);
		String pageName = noticeService.getNoticeDetail(boardNo, model, searchIndex);
		model.addAttribute("searchIndex", searchIndex);
		model.addAttribute("id", auth.getName());
		return pageName;
	}
	//작성 폼으로 이동
	@GetMapping("/admin/form")
	public String noticeForm() {
		return "notice/write";
	}
	//작성된 내용 제출
	@PostMapping("/admin/write")
	public String noticeWrite(@Valid BoardDto board, Model model, Authentication auth, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("msg", "제목과 내용은 필수적인 내용입니다.");
			model.addAttribute("url", "/");
			return "alert";
		}
		noticeService.saveNoticeOnDataBase(board, auth);
		return "redirect:/notice/list";
	}
	//수정 폼으로 이동
	@GetMapping("/admin/edit")
	public String noticeEdit(int boardNo, Authentication auth, Model model) {
		return noticeService.checkBeforeEditForm(boardNo, auth, model);
	}
	//공지사항 수정 제출
	@PostMapping("/admin/update")
	public String noticeUpdate(@Valid BoardDto board, Model model, Authentication auth, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", "제목과 내용은 필수적인 내용입니다.");
			model.addAttribute("url", "/notice/admin/edit/boardNo="+board.getBoardNo());
			return "alert";
		}
		String result = noticeService.modifyNotice(board, auth, model);
		return result;
	}
	//공지사항 삭제 요청
	@GetMapping("/admin/delete")
	public String noticeDelete(int boardNo, Authentication auth, Model model) {
		return noticeService.deleteNotice(boardNo, auth, model);
	}
}
