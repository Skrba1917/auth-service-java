package com.example.AuthService.model;

import javax.persistence.Entity;


public class BusinessUser {

	 private String username;

	    private String password;

	    private String companyName;

	    private String email;

	    private String website;

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

	    public String getCompanyName() {
	        return companyName;
	    }

	    public void setCompanyName(String companyName) {
	        this.companyName = companyName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getWebsite() {
	        return website;
	    }

	    public void setWebsite(String website) {
	        this.website = website;
	    }

	    public BusinessUser(String username, String password, String companyName, String email, String website) {
	        this.username = username;
	        this.password = password;
	        this.companyName = companyName;
	        this.email = email;
	        this.website = website;
	    }

	    public BusinessUser() {
	    }
}
