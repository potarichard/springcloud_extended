package com.entities;

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.POJO.AppRole;

public class User implements UserDetails
{
	private static final long serialVersionUID = 1L;

	private Long id;	
	
	private String username;
	
	private String password;
		
    private String role;
	
    private String email;

	private Set<? extends GrantedAuthority> grantedAuthorities;
	
    public User() {   }

    public User(String username, String password, String mail, String Role)
    {
    	this.username	= username;
    	this.password	= password;
    	this.email		= mail;
    	this.role		= Role;
    }
    
    @Override
	public boolean isEnabled() {
		return true;
	}
    
    @Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
    
    @Override
	public boolean isAccountNonLocked() {
		return true;
	}
    
    @Override
	public boolean isAccountNonExpired() {
		return true;
	}
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{		
		switch (role) 
		{
			case "STUDENT":					
				this.grantedAuthorities = AppRole.STUDENT.getGrantedAuthorities();
				break;
			
			case "ADMIN":	
				this.grantedAuthorities = AppRole.ADMIN.getGrantedAuthorities();
				break;
				
			case "ADMINTRAINEE":
				this.grantedAuthorities = AppRole.ADMINTRAINEE.getGrantedAuthorities();
				break;
		}	
		
		return this.grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {		
		return this.username;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<? extends GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	

}
