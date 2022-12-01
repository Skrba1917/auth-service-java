package com.example.AuthService.dto;

public class BusinessPassDTO {

    private String username;

    private String companyName;

    private String email;

    private String website;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public BusinessPassDTO(String username, String companyName, String email, String website) {
        this.username = username;
        this.companyName = companyName;
        this.email = email;
        this.website = website;
    }

    public BusinessPassDTO() {
    }
}
