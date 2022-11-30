package com.example.AuthService.service;

import com.example.AuthService.dto.LoginDTO;
import com.example.AuthService.model.AuthControl;

public interface AuthControlService {

	AuthControl findByUsername(String username);
}
