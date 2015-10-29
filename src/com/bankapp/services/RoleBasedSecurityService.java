package com.bankapp.services;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service("roleBasedControl")
public class RoleBasedSecurityService implements AuthenticationSuccessHandler {

	@Autowired
	UserService userService;
	RedirectStrategy redirect;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		redirect = new DefaultRedirectStrategy();
		boolean flag = true;
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		boolean captcha = CaptchaService.verify(gRecaptchaResponse);

		if (authentication.isAuthenticated() && captcha) {
			if (userService.isFirstLogin(authentication.getName())) {
				redirect.sendRedirect(request, response, "/atFirstLogin");
				flag = false;
			}

			Collection<GrantedAuthority> list = (Collection<GrantedAuthority>) authentication.getAuthorities();

			for (GrantedAuthority auth : list) {
				if (flag) {
					if (auth.getAuthority().equals("ROLE_SM") || auth.getAuthority().equals("ROLE_RE")) {
						redirect.sendRedirect(request, response, "/intHome");
					}
					if (auth.getAuthority().equals("ROLE_SA")) {
						redirect.sendRedirect(request, response, "/adminHome");
					}

					if (auth.getAuthority().equals("ROLE_U")) {
						redirect.sendRedirect(request, response, "/extHome");
					}

					if (auth.getAuthority().equals("ROLE_M")) {
						redirect.sendRedirect(request, response, "/merchHome");
					}

					if (auth.getAuthority().equals("ROLE_G")) {
						redirect.sendRedirect(request, response, "/govt");
					}
				}
			}
		}

		if (!captcha) {
			redirect.sendRedirect(request, response, "/loginFailed");
		}
	}

}
