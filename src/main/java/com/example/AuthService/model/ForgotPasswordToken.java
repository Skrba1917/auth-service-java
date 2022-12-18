package com.example.AuthService.model;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "forgotpasswordtoken")
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fpId;

    private String username;

    private String email;

    private String token;

    private LocalDateTime expireDate;

    private boolean used;


    public int getFpId() {
        return fpId;
    }

    public void setFpId(int fpId) {
        this.fpId = fpId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public ForgotPasswordToken(int fpId, String username, String email, String token, LocalDateTime expireDate,boolean used) {
        this.fpId = fpId;
        this.username = username;
        this.email = email;
        this.token = token;
        this.expireDate = expireDate;
        this.used=used;
    }

    public ForgotPasswordToken() {
    }
}
