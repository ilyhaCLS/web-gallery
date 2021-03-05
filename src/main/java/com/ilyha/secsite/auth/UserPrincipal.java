package com.ilyha.secsite.auth;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.TimeZone;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ilyha.secsite.model.User;

public class UserPrincipal implements UserDetails {

	private User user;
	
	public UserPrincipal(User user) {
		this.user = user;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		
		String permissions = user.getAuthorities();
		char[] arr = permissions.toCharArray();
		
		int prev_delimiter = 0;
		for(int i=0; i < arr.length; i++) {
			if(arr[i]==':') {
				authorities.add(new SimpleGrantedAuthority(permissions.substring(prev_delimiter, i)));
				prev_delimiter = i+1;
			}
		}
		return authorities;
	}

	public String getRegDate() { 
	String[] months	= {"Jan","Feb","Mar","Apr","May","Jul","Jun","Aug","Sep","Oct","Nov","Dec"};
	
	Calendar regDate = user.getRegDate();
	regDate.setTimeZone(TimeZone.getTimeZone("GMT"));
	
	int min_primordial = regDate.get(Calendar.MINUTE);
	String min =  (min_primordial<10)?"0"+min_primordial:""+min_primordial;
	
	int sec_primordial = regDate.get(Calendar.SECOND);
	String sec =  (sec_primordial<10)?"0"+sec_primordial:""+sec_primordial;
	
	String date = new String(regDate.get(Calendar.DATE)+" "+months[regDate.get(Calendar.MONTH)]+" "+regDate.get(Calendar.YEAR)
							+"  "+regDate.get(Calendar.HOUR_OF_DAY)+":"+min+":"+sec+" UTC/GMT+0");
		return date;
	}
	
	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
}