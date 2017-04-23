package com.projects.security.authorization.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.projects.security.authorization.config.DataSourceConfig;

@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

	private Logger log = LoggerFactory.getLogger(AuthServerOAuth2Config.class);

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private DataSourceConfig dataSourceConfig;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		log.info("Inside security configuration");
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		log.info("Inside Oauth2 configuration");
		clients.jdbc(dataSourceConfig.dataSource()).withClient("sampleClientId").authorizedGrantTypes("implicit")
				.scopes("read").autoApprove(true).and().withClient("clientIdPassword").secret("secret")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("read");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		log.info("Inside configuration of tokenstore persistency");
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
	}

	@Bean
	public TokenStore tokenStore() {

		TokenStore tokenStore = new JdbcTokenStore(dataSourceConfig.dataSource());
		log.info("Inside Token Store Bean");
		log.info("Token store : " + tokenStore.toString());

		return tokenStore;
	}

}
