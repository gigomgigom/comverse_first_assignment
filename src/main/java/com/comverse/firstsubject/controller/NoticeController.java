package com.comverse.firstsubject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@GetMapping("/list")
	public String noticeList() {
		return null;
	}
	
	@GetMapping("/detail")
	public String noticeDetail() {
		return null;
	}
	
	@GetMapping("/form")
	public String noticeForm() {
		return null;
	}
	
	@PostMapping("/write")
	public String noticeWrite() {
		return null;
	}
	
	@GetMapping("/edit")
	public String noticeEdit() {
		return null;
	}
	
	@PostMapping("/update")
	public String noticeUpdate() {
		return null;
	}
	
	@DeleteMapping("/delete")
	public String noticeDelete() {
		return null;
	}
}
