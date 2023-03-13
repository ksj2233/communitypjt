package com.keduit.helloworld.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.keduit.helloworld.security.handler.LoginSuccessHandler;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	@Autowired
//	private UserDetailsService userDetailsService;
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		log.info("----------- fileterchain -----------");
		
		http.authorizeRequests()
						.antMatchers("/hello/display","/iconcss/**","/fragments/**","/icon/calculator","/icon/memo","/icon/compass","/icon/stopwatch","/hello/noticelist/**","/hello/noticeread","/hello/communityread/**", "/hello/register/**", "/hello/index", "/hello/communitylist/**", "/css/**", "/img/**", "/js/**").permitAll()
						.anyRequest().authenticated()
						.and()
					.formLogin()
						.loginPage("/hello/login")
						.defaultSuccessUrl("/hello/index")
						.failureUrl("/hello/login?error")
						.usernameParameter("userId") 
		                .passwordParameter("passwd")
						.permitAll()
						.and()
					.logout()
						.permitAll();
				
		http.formLogin();										//인가, 인증에 문제가 발생할 때 화면을 띄움
		http.csrf().disable();
		http.logout();
		
		http.oauth2Login().loginPage("/hello/login").defaultSuccessUrl("/hello/index");         //카카오로그인
		
//		http.rememberMe().tokenValiditySeconds(60*60*7).userDetailsService(userDetailsService); // 토큰 유효시간 7일로 잡음
		
		return http.build();
	}
	@Bean
	public LoginSuccessHandler successHandler() {

		
		return new LoginSuccessHandler(passwordEncoder());
	}




}
