package com.example.AuthService.service;

import java.util.Optional;

import com.example.AuthService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AuthService.dto.LoginDTO;
import com.example.AuthService.model.AuthControl;
import com.example.AuthService.repository.AuthControlRepository;

@Service
public class AuthControlServiceImpl implements AuthControlService {

	@Autowired
	private AuthControlRepository authControlRepository;
	@Override
	public AuthControl findByUsername(String username) {
		 AuthControl user = authControlRepository.findById(username).get();
		return user;
	}


	

}
