package com.ilyha.secsite.model;

import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {

	@Id
	@GeneratedValue
	private int userId;
	
	private String username;
	private String password;
	private String authorities;
	private boolean isAccountNonLocked;
	private boolean isAccountNonExpired;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	private Calendar regDate;
	
	public User(){
		
	}
	
	public User(String username, String password) {
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isEnabled = true;
		this.isCredentialsNonExpired = true;
		this.authorities = "ROLE_STUDENT:";
		this.username = username;
		this.password = password;
		this.regDate = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	}
	
	///////////////////////////////////
	public Calendar getRegDate() {
		return regDate;
	}

	public void setRegDate(Calendar regDate) {
		this.regDate = regDate;
	}

	////////////////////////////
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	////////////////////////////////////
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	//////////////////////////////////////
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	///////////////////////////////////////
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
	/////////////////////////////////////
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}
	
	//////////////////////////////////////
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}
	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}
	
	/////////////////////////////////////////
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}
	
	////////////////////////////////////
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}