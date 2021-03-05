package com.ilyha.secsite.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ilyha.secsite.model.User;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier("JPARealization")
	ApplicationUserDao applicationUserDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User us = applicationUserDao.selectUserByUsername(username);
		if (us != null)
			return new UserPrincipal(us);
		else
			throw new UsernameNotFoundException(String.format("User: %s, has not been found!", username));
	}
}