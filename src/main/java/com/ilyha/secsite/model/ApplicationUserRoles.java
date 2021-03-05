package com.ilyha.secsite.model;

import java.util.Set;
import java.util.HashSet;

import static com.ilyha.secsite.model.ApplicationUserPermissions.*;

public enum ApplicationUserRoles {
	STUDENT(new HashSet<ApplicationUserPermissions>()),
	ADMINTRAINEE(Set.of(STUDENT_READ,COURSE_READ)),
	ADMIN(Set.of(STUDENT_READ,STUDENT_WRITE,COURSE_READ,COURSE_WRITE));

	private Set<ApplicationUserPermissions> permissions;
	
	ApplicationUserRoles(Set<ApplicationUserPermissions> permissions){
		this.permissions = permissions;
	}
	
	public Set<ApplicationUserPermissions> getPermissions(){
		return permissions;
	}
}