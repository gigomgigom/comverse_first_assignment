package com.comverse.firstsubject.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component("loginSuccessHandler")
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	public void onAuthenticationSuccess(HttpServletRequest rq, HttpServletResponse rs, Authentication auth) throws ServletException, IOException {
		log.info("로그인 성공 : " + auth.getName());
		setDefaultTargetUrl("/board/list");
		super.onAuthenticationSuccess(rq, rs, auth);
	}
}
