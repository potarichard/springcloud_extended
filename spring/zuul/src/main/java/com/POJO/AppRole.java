package com.POJO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.POJO.AppPermission;

public enum AppRole {

	STUDENT(new HashSet<AppPermission>()),
	
	ADMIN(new HashSet<AppPermission>(Arrays.asList(AppPermission.COURSE_READ, AppPermission.COURSE_WRITE, AppPermission.STUDENT_READ, AppPermission.STUDENT_WRITE))),
	
	ADMINTRAINEE(new HashSet<AppPermission>(Arrays.asList(AppPermission.COURSE_READ, AppPermission.STUDENT_READ)));
	
	private Set<AppPermission> permissions;

	private AppRole(Set<AppPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<AppPermission> getPermissions() {
		return permissions;
	}	
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		
		Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
			.collect(Collectors.toSet());
		
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		
		return permissions;
	}
}
