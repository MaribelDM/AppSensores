package com.microservicio.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
@EnableResourceServer
@RestController
public class MicroserviciosAplicacionApplication {
	
	  @Value("${security.oauth2.public-key}")
	  private String publicKey;

	  @Value("${security.oauth2.private-key}")
	  private String privateKey;

	public static void main(String[] args) {
		 SpringApplication.run(MicroserviciosAplicacionApplication.class, args);
	}
	
	 @Bean
	    public ResourceServerTokenServices tokenService() {
	        RemoteTokenServices tokenServices = new RemoteTokenServices();
	        tokenServices.setClientId("client-id");
	        tokenServices.setClientSecret("client-secret");
	        tokenServices.setCheckTokenEndpointUrl("http://localhost:7001/auth/oauth/check_token");
	        tokenServices.setAccessTokenConverter(accessTokenConverter());
	        return tokenServices;
	    }

	 @Bean
	  public JwtAccessTokenConverter accessTokenConverter() {

	    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {

	      @Override
	      public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

	        accessToken = super.enhance(accessToken, authentication);
	        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());
	        return accessToken;
	      }

	      @Override
	      public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
	        OAuth2Authentication auth = super.extractAuthentication(map);
	        auth.setDetails(map);
	        return auth;
	      }
	    };

	    converter.setSigningKey(privateKey);
	    converter.setVerifierKey(publicKey);

	    return converter;
	  }
}
