package com.modulo.projetoapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	//Configurações das credenciais do cliente 
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("angular")
		.secret("@ngul@r0")
		//necessário definir senão gera erro
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		//Tempo de acesso do token em segundos
		.accessTokenValiditySeconds(1800)
		.refreshTokenValiditySeconds(3600 * 24) //tempo de vida do refresh token 1 dia.
	.and()
		.withClient("mobile")
		.secret("m0b1l30")
		//necessário definir senão gera erro
		.scopes("read")
		.authorizedGrantTypes("password", "refresh_token")
		//Tempo de acesso do token em segundos
		.accessTokenValiditySeconds(1800)
		.refreshTokenValiditySeconds(3600 * 24); //tempo de vida do refresh token 1 dia.
	}

	//Configurações para o endpoints do Token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.reuseRefreshTokens(false) //sempre que solicitado será enviado refresh token para o usuário.Ele não se deslogará.se não setado.
		.authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter acessTokenConverter = new JwtAccessTokenConverter();
		acessTokenConverter.setSigningKey("senhavalidatoken");
		return acessTokenConverter;
	}

	//Responsável por armazenar o token
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}
