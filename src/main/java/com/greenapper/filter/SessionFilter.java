package com.greenapper.filter;

import com.greenapper.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(0)
public class SessionFilter implements Filter {
	@Autowired
	private SessionService sessionService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (sessionService.getSessionUser() == null)
			sessionService.setSessionUser();

		filterChain.doFilter(servletRequest, servletResponse);
	}
}
