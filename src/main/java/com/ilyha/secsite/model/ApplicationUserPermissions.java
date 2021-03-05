package com.ilyha.secsite.model;

public enum ApplicationUserPermissions {
	STUDENT_READ("student-read"),
	STUDENT_WRITE("student-write"),
	COURSE_READ("course-read"),
	COURSE_WRITE("course-write");
	
	private String permission;
	
	ApplicationUserPermissions(String permission){
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}