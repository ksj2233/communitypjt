package com.keduit.helloworld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class LayOutConfig {
	
	// thymeleaf layout
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
}