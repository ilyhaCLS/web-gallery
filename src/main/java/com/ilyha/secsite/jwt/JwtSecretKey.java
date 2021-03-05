package com.ilyha.secsite.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableConfigurationProperties(JwtConfig.class)
public class JwtSecretKey {

	private JwtConfig jwtConfig;
	
	@Autowired
	JwtSecretKey(JwtConfig jwtConfig){
		this.jwtConfig = jwtConfig;
	}
	
	@Bean
	SecretKey secretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}
}