package com.example.AuthService.repository;

import com.example.AuthService.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,Integer> {
}
