package com.example.AuthService.model;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.Table;

@Entity
@Table(name="AuthClass")
public class AuthControl {

	
	@javax.persistence.Id
	@Column(name="Username", unique=true, nullable=false)
	private String Username;
	
	@Column(name="Password", unique=false, nullable=false)
	private String Password;
	
	@Column(name="Role", unique=false, nullable=false)
	private ERole Role;

	@Column(name="Email", unique = false, nullable = false)
	private String email;

	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ERole getRole() {
		return Role;
	}

	public void setRole(ERole role) {
		Role = role;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
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
