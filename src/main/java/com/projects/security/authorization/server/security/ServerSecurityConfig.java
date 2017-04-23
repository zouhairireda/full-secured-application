package com.projects.security.authorization.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.projects.security.authorization.server.AuthServerOAuth2Config;

@Configuration
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

	private Logger log = LoggerFactory.getLogger(ServerSecurityConfig.class);

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Inside in memory authentication");
		auth.inMemoryAuthentication().withUser("reda").password("123").roles("USER");
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.info("Inside bean authentication manager preOauth");
		return super.authenticationManagerBean();
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Inside route configuration");
		http.authorizeRequests().antMatchers("/login").permitAll()
		.anyRequest().authenticated().and().formLogin().permitAll();
	}

}
