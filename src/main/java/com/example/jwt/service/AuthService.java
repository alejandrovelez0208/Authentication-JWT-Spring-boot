package com.example.jwt.service;

import com.example.jwt.entity.dto.LoginDto;

public interface AuthService {

	String login(LoginDto loginDto);
}
