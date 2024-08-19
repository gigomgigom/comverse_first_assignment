package com.comverse.firstsubject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comverse.firstsubject.dto.MemberDto;
import com.comverse.firstsubject.service.MemberService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(MemberDto member) {
		//유효성 검사 예
		int createdRow = memberService.createNewAccount(member);
		if(createdRow > 0) {
			return "redirect:/auth/login";
		} else {
			return "redirect:/auth/signup";
		}
		
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/auth/login";
	}
	
}
