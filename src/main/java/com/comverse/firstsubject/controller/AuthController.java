package com.comverse.firstsubject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comverse.firstsubject.dto.MemberDto;
import com.comverse.firstsubject.service.MemberService;
import com.comverse.firstsubject.validator.MemberValidator;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/login")
	public String login(@RequestParam(required = false) String error,
						@RequestParam(required= false) String errorMsg,
						Model model) {
		if(error != null) {
			model.addAttribute("error", error);
			model.addAttribute("errorMsg", errorMsg);
		}
		return "login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "signup";
	}
	
	@InitBinder("memberDto")
	public void memberDtoValidator(WebDataBinder binder) {
		binder.setValidator(new MemberValidator());
	}
	
	@PostMapping("/signup")
	public String signup(@Valid MemberDto member, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldErrors().get(0).getCode();
			model.addAttribute("msg", msg);
			model.addAttribute("url", "/auth/join");
			return "alert";
		}
		if(memberService.findAccountById(member.getMemberId())) {
			model.addAttribute("msg", "해당 아이디는 이미 존재합니다.");
			model.addAttribute("url", "/auth/join");
			return "alert";
		}
		if(memberService.findAccountByEmail(member.getMemberEmail())) {
			model.addAttribute("msg", "해당 이메일로 가입된 계정이 존재합니다.");
			model.addAttribute("url", "/auth/join");
			return "alert";
		}
		memberService.createNewAccount(member);
		return "redirect:/auth/login";
		
	}
	
	@ResponseBody
	@GetMapping("/find_id_exist")
	public ResponseEntity<?> findIdExist(String memberId) {
		if(memberService.findAccountById(memberId)) {
			return ResponseEntity.ok("true");
		} else {
			return ResponseEntity.ok("fail");
		}
	}
	
}
