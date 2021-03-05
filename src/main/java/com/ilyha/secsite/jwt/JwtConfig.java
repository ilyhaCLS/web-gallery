package com.ilyha.secsite.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="application.jwt")
public class JwtConfig {

	private String secretKey;
	private String tokenPrefix;
	private String authorizationHeader;
	private int expirationDays;
	
	JwtConfig(){
		
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getTokenPrefix() {
		return tokenPrefix;
	}
	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}
	
	public String getAuthorizationHeader() {
		return authorizationHeader;
	}

	public void setAuthorizationHeader(String authorizationHeader) {
		this.authorizationHeader = authorizationHeader;
	}

	public int getExpirationDays() {
		return expirationDays;
	}
	public void setExpirationDays(int expirationDays) {
		this.expirationDays = expirationDays;
	}
}