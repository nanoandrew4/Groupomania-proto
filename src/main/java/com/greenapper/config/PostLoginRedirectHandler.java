package com.greenapper.config;

import com.greenapper.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostLoginRedirectHandler implements AuthenticationSuccessHandler {

	protected Logger logger = LoggerFactory.getLogger(PostLoginRedirectHandler.class);

	@Autowired
	private SessionService sessionService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
		sessionService.setSessionUser();
	}
}
