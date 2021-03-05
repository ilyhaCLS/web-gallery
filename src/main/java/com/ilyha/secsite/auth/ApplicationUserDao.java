package com.ilyha.secsite.auth;

import com.ilyha.secsite.model.User;

public interface ApplicationUserDao {
	public User selectUserByUsername(String username);
	
}