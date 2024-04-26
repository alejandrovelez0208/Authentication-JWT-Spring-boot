package com.example.jwt.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.User;
import com.example.jwt.entity.dto.LoginDto;
import com.example.jwt.jwt.JwtTokenProvider;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	@SuppressWarnings("unused")
	private UserRepository userRepository;

	@SuppressWarnings("unused")
	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public Boolean register(User user) {
		Boolean ok = false;
		try {
			User newUser = User.builder().userName(user.getName()).name(user.getUserName()).email(user.getEmail())
					.password(passwordEncoder.encode(user.getPassword())).roles(null).build();

			userRepository.save(newUser);
			ok = true;
		} catch (Exception e) {
			log.error("Error en el metodo [Register]");
		}
		return ok;
	}
}
