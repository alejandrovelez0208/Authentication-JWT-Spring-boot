package com.example.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.entity.dto.AuthResponse;
import com.example.jwt.entity.dto.LoginDto;
import com.example.jwt.entity.dto.RegisterRequest;
import com.example.jwt.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(authService.login(loginDto));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest user) {
		return ResponseEntity.ok(authService.register(user));
	}

}
