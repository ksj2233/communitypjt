package com.keduit.helloworld.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.security.dto.AuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("티테일 서비스에서 유저 이름이 잘 찍히는지 : "+username);
		
		Optional<Member> result = memberRepository.findByEmail(username, false);
		if(result.isEmpty()) {
			throw new UsernameNotFoundException("이메일이나 소셜 가입 여부를 확인해주세요.");
		}
		Member member = result.get();
		
		AuthMemberDTO memberDTO = new AuthMemberDTO(member.getId()
												, member.getPw()
//												,member.getEmail()
//												,member.getName()
//												, member.getNickname()
												, member.getPurview()
												, member.getRoleset().stream()
													.map(i-> new SimpleGrantedAuthority("ROLE_"+ i.name()))
													.collect(Collectors.toSet()));
		memberDTO.setName(member.getName());
		memberDTO.setPurview(member.getPurview());
													
		return memberDTO;
	}
	
	
	
}
