package com.comverse.firstsubject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comverse.firstsubject.dto.SearchIndex;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
	
	//목록 조회 화면 이동
	@GetMapping("/list")
	public String mainHome(Model model, SearchIndex searchIndex) {
		List<String> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			list.add(i+"");
		}
		model.addAttribute("boardList", list);
		return "boardlist";
	}	
	
	//상세 조회 화면 이동
	@GetMapping("/detail")
	public String detailBoard() {
		return "boarddetail";
	}
	
	//게시글 작성 화면 이동
	//인증받은 자만 요청할수 있게끔 해야함.
	@GetMapping("/write")
	public String writeBoard() {
		return "boardwrite";
	}
	
	//게시글 수정 화면 이동
	@GetMapping("/modify")
	public String modifyBoard() {
		return "boardupdate";
	}
	
	//게시글 삭제
	@PatchMapping("/delete")
	public String deleteBoard() {
		return "redirect:/board/list";
	}
	

}
