package com.ilyha.secsite.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

public class JwtUserPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private SecretKey secretKey;
	private JwtConfig jwtConfig;

	public JwtUserPasswordAuthFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UsernamePasswordAuthRequest authRequest = new ObjectMapper().readValue(request.getInputStream(),
					UsernamePasswordAuthRequest.class);

			Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
					authRequest.getPassword());

			Authentication authenticate = authenticationManager.authenticate(authentication);

			return authenticate;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
	
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getExpirationDays())))
				.setIssuedAt(new Date())
				.signWith(secretKey)
				.compact();
			
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix()+token);
	}
}