package com.example.AuthService.dto;

import com.example.AuthService.model.ERole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BusinessRegisterDTO {

    @NotBlank
    private String username;
    @Pattern(regexp="^[a-zA-Z0-9]{8,30}", message="The password is invalid IDIOTA")
    private String password;

    @NotBlank
    private String companyName;

    @NotBlank
    private String email;

    @NotBlank
    private String website;

    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BusinessRegisterDTO(String username, String password, String companyName, String email, String website, String role) {
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.email = email;
        this.website = website;
        this.role = role;
    }

    public BusinessRegisterDTO(){

    }
}
