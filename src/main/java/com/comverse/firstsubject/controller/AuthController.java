package com.comverse.firstsubject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			model.addAttribute("msg", "Id is already exists");
			model.addAttribute("url", "/auth/join");
			return "alert";
		}
		if(memberService.findAccountByEmail(member.getMemberEmail())) {
			model.addAttribute("msg", "Email is already exiss");
			model.addAttribute("url", "/auth/join");
			return "alert";
		}
		memberService.createNewAccount(member);
		return "redirect:/auth/login";
		
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/auth/login";
	}
	
}
