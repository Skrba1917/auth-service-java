package com.example.AuthService.model;

import javax.persistence.*;

@Entity
@Table(name="AuthClass")
public class AuthControl {

	
	@Id
	@Column(name="Username", unique=true, nullable=false)
	private String username;
	
	@Column(name="Password", unique=false, nullable=false)
	private String password;
	
	@Column(name="Role", unique=false, nullable=false)
	private ERole role;

	@Column(name="Email", unique = false, nullable = false)
	private String email;

	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ERole getRole() {
		return role;
	}

	public void setRole(ERole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AuthControl() {
		
	}

}
