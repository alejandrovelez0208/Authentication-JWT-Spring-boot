package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.entity.dto.AuthResponse;
import com.example.jwt.entity.dto.LoginDto;

public interface AuthService {

	AuthResponse login(LoginDto loginDto);
	
	Boolean register(User user);
}
