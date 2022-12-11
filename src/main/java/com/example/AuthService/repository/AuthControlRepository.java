package com.example.AuthService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuthService.dto.LoginDTO;
import com.example.AuthService.model.AuthControl;

import java.util.Optional;


@Repository
public interface AuthControlRepository  extends JpaRepository<AuthControl, String> {

    Optional<AuthControl> findAuthControlByUsername(String username);

}
