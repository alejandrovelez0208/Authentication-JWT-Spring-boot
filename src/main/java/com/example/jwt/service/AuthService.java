package com.example.jwt.service;

import com.example.jwt.entity.dto.AuthResponse;
import com.example.jwt.entity.dto.LoginDto;
import com.example.jwt.entity.dto.RegisterRequest;

public interface AuthService {

	AuthResponse login(LoginDto loginDto);
	
	AuthResponse register(RegisterRequest user);
}
