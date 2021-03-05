package com.ilyha.secsite.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ilyha.secsite.model.User;

@Repository("JPARealization")
public class JpaAppUserDaoService implements ApplicationUserDao {

	@Autowired
	UserRepository repo;
	
	@Override
	public User selectUserByUsername(String username) {
		return repo.findByUsername(username);
	}
}