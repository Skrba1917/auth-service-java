package com.example.AuthService.repository;

import com.example.AuthService.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,Integer> {

    @Query(value = "select * from forgotpasswordtoken where username=?1 and token=?2",nativeQuery = true)
    ForgotPasswordToken findByUsernameAndToken(String username,String token);
}
