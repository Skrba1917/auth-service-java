package com.example.AuthService.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UsernameAndFPTokenDTO {


    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}",message = "Invalide username format")
    private String username;

    private String forgotPasswordToken;

    @Pattern(regexp="^[a-zA-Z0-9]{8,30}", message="The password format is invalid")
    private String newpassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getForgotPasswordToken() {
        return forgotPasswordToken;
    }

    public void setForgotPasswordToken(String forgotPasswordToken) {
        this.forgotPasswordToken = forgotPasswordToken;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public UsernameAndFPTokenDTO(String username, String forgotPasswordToken, String newpassword) {
        this.username = username;
        this.forgotPasswordToken = forgotPasswordToken;
        this.newpassword = newpassword;
    }



    public UsernameAndFPTokenDTO() {
    }
}
