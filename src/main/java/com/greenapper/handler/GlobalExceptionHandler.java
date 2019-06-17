package com.greenapper.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Order(0)
public class GlobalExceptionHandler extends AbstractHandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ModelAndView doResolveException
			(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("", ex);
		return null;
	}
}