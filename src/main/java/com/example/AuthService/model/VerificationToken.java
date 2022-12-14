package com.example.AuthService.model;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = EAGER)
    private AuthControl authControl;
    private Instant expiryDate;

    public VerificationToken(){}

    public VerificationToken(Long id, String token, AuthControl authControl, Instant expiryDate) {
        this.id = id;
        this.token = token;
        this.authControl = authControl;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public AuthControl getAuthControl() {
        return authControl;
    }

    public void setAuthControl(AuthControl authControl) {
        this.authControl = authControl;
    }
}

