package com.keduit.helloworld.security.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.helloworld.serviceImpl.loginServiceImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@ToString
@Log4j2
public class AuthMemberDTO extends User implements OAuth2User{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pw;
	private String name;
	private boolean purview;
	
	private Map<String, Object> attr;
	
	public AuthMemberDTO(String id,
						 String pw,
//						 String name,
//						 String email,
//						 String nickname,
						 boolean purview,
						 Collection<? extends GrantedAuthority> authorities) {
		super(id, pw, authorities);
		this.id = id;
		this.pw = pw;
		this.purview = purview;
		log.info("================ authMemberDTO임 ================"+id);
	}
	
	
	@Override
	public Map<String, Object> getAttributes() {
		
		return this.attr;
	}
	
	public AuthMemberDTO(String id,
						 String pw,
//						 String name,
//					 	 String email,
//						 String nickname,
						 Boolean purview,
						 Collection<? extends GrantedAuthority> authorities,
						 Map<String, Object> attr) {
		this(id, pw,
//				name, email, nickname, 
				purview, authorities);
		this.attr = attr;
	}
}
