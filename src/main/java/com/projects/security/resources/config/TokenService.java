package com.projects.security.resources.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

//@Configuration
public class TokenService {
	
	@Primary
	@Bean
	public RemoteTokenServices tokenService() {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl("http://localhost:8090/full-secured-application/oauth/check_token");
		tokenService.setClientId("fooClientIdPassword");
		tokenService.setClientSecret("secret");
		return tokenService;
	}
}
