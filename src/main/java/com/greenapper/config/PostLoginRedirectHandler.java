package com.greenapper.config;

import com.greenapper.models.CampaignManager;
import com.greenapper.models.User;
import com.greenapper.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PostLoginRedirectHandler implements AuthenticationSuccessHandler {

	protected Logger logger = LoggerFactory.getLogger(PostLoginRedirectHandler.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private SessionService sessionService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		sessionService.setSessionUser();
		redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, determineRedirectUrl());
		clearAuthenticationAttributes(httpServletRequest);
	}

	private String determineRedirectUrl() {
		final User sessionUser = sessionService.getSessionUser();
		if (sessionUser instanceof CampaignManager)
			return "/campaign-manager/login-success";
		return "/home";
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
