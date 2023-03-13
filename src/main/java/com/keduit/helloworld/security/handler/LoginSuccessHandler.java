package com.keduit.helloworld.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.keduit.helloworld.security.dto.AuthMemberDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private PasswordEncoder passwordEncoder;
	
	
	public LoginSuccessHandler(PasswordEncoder PasswordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		
		log.info("----------------------------------------");
		log.info("------onAuthenticationSuccess------");
		
		AuthMemberDTO authMemberDTO = (AuthMemberDTO)authentication.getPrincipal();
		
		boolean fromSocial = authMemberDTO.isPurview();
		
		log.info("사용자 정보를 수정여부 : " + fromSocial);
		
		boolean passwordResult = passwordEncoder.matches("1111", authMemberDTO.getPassword());
		
		if(fromSocial && passwordResult) {
			redirectStrategy.sendRedirect(request, response, "/hello/modify?from=purview");
		}
		
	}

}
