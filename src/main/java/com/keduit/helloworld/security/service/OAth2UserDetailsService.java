package com.keduit.helloworld.security.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.entity.MemberRole;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.security.dto.AuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAth2UserDetailsService extends DefaultOAuth2UserService{

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		log.info("=====================================================");
		log.info("User Request : " + userRequest);
		
		String clientName = userRequest.getClientRegistration().getClientName();
		
		log.info( "=======클라이언트 네임 ======= "+clientName);							//카카오나 구글같이 쓸 경우 이걸로 구분
		log.info( "=======파라미터 ======= "+userRequest.getAdditionalParameters());
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		Map<String, Object> paramMap = oAuth2User.getAttributes();
		
		String email = null;
		
		switch (clientName) {
		case "kakao":
			email = getKakaoEmail(paramMap);
			break;
//		case "google":
//			email = getGoogleEmail(paramMap);
		default:
			break;
		}
		
		Member member = saveSocialMember(email);
		
		AuthMemberDTO authmemberDTO = new AuthMemberDTO(
											member.getId()
											, member.getPw()
//											,member.getEmail()
//											, member.getName()
//											, member.getNickname()
											, true
											, member.getRoleset()
												.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
												.collect(Collectors.toList())
											,oAuth2User.getAttributes());
		authmemberDTO.setName(member.getName());
		
		return authmemberDTO;
	}

	private Member saveSocialMember(String email) {

		Optional<Member> result = memberRepository.findByEmail(email, true);
		if(result.isPresent()) {
			return result.get();
		}
		Member member = Member.builder().id(email)
										.name(email)
										.pw(passwordEncoder.encode("1111"))
										.nickname(email+"user")
										.email(email)
										.purview(true)
										.build();
		member.addMemberRole(MemberRole.USER);
		memberRepository.save(member);
		
		return member;
	}

//	private String getGoogleEmail(Map<String, Object> paramMap) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private String getKakaoEmail(Map<String, Object> paramMap) {
		log.info("카카오 -----------------------");
		
		
		Object value = paramMap.get("kakao_account");
		log.info("카카오 어카운트 : "+value);
		
		LinkedHashMap accountMap = (LinkedHashMap)value;
		String email = (String)accountMap.get("email");		//email꺼내오기
		
		log.info("이메일 : " + email);
		
		return email;
	}
	
}
