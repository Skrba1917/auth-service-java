package com.example.AuthService.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginDTO {


	@NotBlank
	private String username;
		@Pattern(regexp="^[a-zA-Z0-9]{8}", message="The password is invalid dumb")
	    private String password;


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

	
	    
		public LoginDTO() {
			
		}
}
