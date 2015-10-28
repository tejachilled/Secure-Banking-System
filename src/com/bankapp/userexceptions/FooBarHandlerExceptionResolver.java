package com.bankapp.userexceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

public class FooBarHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
	@Override
    protected ModelAndView handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
		ModelAndView model = new ModelAndView("login");
		model.addObject("error", "Session expired");
		System.out.println("foo bar");
        return model;
    }
}
