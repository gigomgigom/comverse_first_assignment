package com.comverse.firstsubject.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component("loginFailureHandler")
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest rq, HttpServletResponse rs, AuthenticationException e) throws IOException, ServletException {
		String errorMessage = "ID and Password don't match";
		
		setDefaultFailureUrl("/auth/login?error=true&errorMsg="+errorMessage);
		
		super.onAuthenticationFailure(rq, rs, e);
	}
}
