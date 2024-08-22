package com.comverse.firstsubject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping("")
	public String home() {
		return "redirect:/board/list";
	}
	
	@GetMapping("favicon.ico")
	@ResponseBody
	public void returnNoFavicon() {
		
	}
}
