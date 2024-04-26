package com.example.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.entity.User;
import com.example.jwt.entity.dto.JWTAuthResponse;
import com.example.jwt.entity.dto.LoginDto;
import com.example.jwt.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto) {
		String toke = authService.login(loginDto);

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(toke);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Boolean> register(@RequestBody User user) {
		return ResponseEntity.ok(authService.register(user));
	}

}
