package com.comverse.firstsubject.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component("logoutSuccessHandler")
@Slf4j
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler{
	
	public void onLogoutSuccesss(HttpServletRequest rq, HttpServletResponse rs, Authentication auth) throws ServletException, IOException {
		log.info("로그아웃");
		setDefaultTargetUrl("/");
		super.onLogoutSuccess(rq, rs, auth);
	}
}
