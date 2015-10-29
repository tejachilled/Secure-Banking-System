package com.bankapp.userexceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

public class FooBarHandlerExceptionResolver extends DefaultHandlerExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		ModelAndView model = new ModelAndView("login");
		if (ex instanceof NoHandlerFoundException) {
			model.addObject("error", "No Mapping found");
		}else if (ex instanceof HttpRequestMethodNotSupportedException) {
			model.addObject("error", "Session expired");
		}
		System.out.println("doResolveException");
		SecurityContextHolder.getContext().setAuthentication(null);
		return model;

	}
}
