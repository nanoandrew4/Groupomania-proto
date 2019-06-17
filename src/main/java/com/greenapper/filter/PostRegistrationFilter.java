package com.greenapper.filter;

import com.greenapper.controllers.CampaignManagerController;
import com.greenapper.controllers.CampaignManagerProfileController;
import com.greenapper.models.CampaignManager;
import com.greenapper.models.CampaignManagerProfile;
import com.greenapper.models.User;
import com.greenapper.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class PostRegistrationFilter implements Filter {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private SessionService sessionService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final User sessionUser = sessionService.getSessionUser();

		String redirectUri = null;
		if (sessionUser instanceof CampaignManager)
			redirectUri = checkCampaignManagerData((CampaignManager) sessionUser);

		if (redirectUri != null && allowRedirect(((HttpServletRequest) request), redirectUri))
			redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response, redirectUri);
		else
			chain.doFilter(request, response);
	}

	private boolean allowRedirect(final HttpServletRequest request, final String redirectUri) {
		final String requestUri = request.getRequestURI();
		return "GET".equals(request.getMethod()) &&
			   (!(requestUri.equals(redirectUri) || requestUri.equals("/login") || requestUri.equals("/logout")));
	}

	private String checkCampaignManagerData(final CampaignManager sessionCampaignManager) {
		if (sessionCampaignManager.isPasswordChangeRequired())
			return CampaignManagerController.PASSWORD_UPDATE_URI;

		final CampaignManagerProfile sessionProfile = sessionCampaignManager.getCampaignManagerProfile();
		if (sessionProfile == null || sessionProfile.getName() == null || sessionProfile.getEmail() == null)
			return CampaignManagerProfileController.PROFILE_UPDATE_URI;
		return null;
	}
}
