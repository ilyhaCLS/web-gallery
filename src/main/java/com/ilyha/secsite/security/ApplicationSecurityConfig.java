package com.ilyha.secsite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ilyha.secsite.auth.ApplicationUserDetailsService;
import com.ilyha.secsite.jwt.JwtConfig;
import com.ilyha.secsite.jwt.JwtUserPasswordAuthFilter;
import com.ilyha.secsite.jwt.JwtVerifierFilter;

import static com.ilyha.secsite.model.ApplicationUserRoles.*;

import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import static com.ilyha.secsite.model.ApplicationUserPermissions.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ApplicationUserDetailsService applicationUserDetailsService;

	private SecretKey secretKey;
	private JwtConfig jwtConfig;

	ApplicationSecurityConfig(SecretKey secretKey, JwtConfig jwtConfig) {
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(applicationUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				// .addFilter(new JwtUserPasswordAuthFilter(authenticationManager(), jwtConfig,
				// secretKey))
				// .addFilterAfter(new JwtVerifierFilter(secretKey, jwtConfig),
				// JwtUserPasswordAuthFilter.class)
				.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/img/**","/register").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/student/*").hasRole(STUDENT.name())
				.antMatchers(HttpMethod.POST, "/api/v1/management/*").hasAuthority(STUDENT_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/api/management/*").hasAuthority(STUDENT_WRITE.getPermission())
				.antMatchers(HttpMethod.DELETE, "/api/v1/management/*").hasAuthority(STUDENT_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/api/v1/management").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
				.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/gallery", true)
				.and()
					.rememberMe()
					.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(14)).key("securekey")
					.userDetailsService(applicationUserDetailsService)
				.and()
					.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.clearAuthentication(true)
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID", "remember-me")
						.logoutSuccessUrl("/");
	}
}