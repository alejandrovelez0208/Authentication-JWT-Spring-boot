package com.example.jwt.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.User;
import com.example.jwt.entity.dto.AuthResponse;
import com.example.jwt.entity.dto.LoginDto;
import com.example.jwt.entity.dto.RegisterRequest;
import com.example.jwt.jwt.JwtTokenProvider;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserServiceImpl userServiceImpl;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public AuthResponse login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));

		UserDetails user = userRepository.findByUserName(loginDto.getUserNameOrEmail()).orElseThrow();

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.getToken(user);

		return AuthResponse.builder().token(token).build();
	}

	@Override
	public AuthResponse register(RegisterRequest request) {
		try {
			Optional<User> userExists = userRepository.findByUserNameOrEmail(request.getUserName(), request.getEmail());
			if (userExists != null && !userExists.isEmpty()) {
				log.error("Error...There's already a register user with that email");
				throw new RuntimeException();
			}

			User user = User.builder().name(request.getName()).userName(request.getUserName()).email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword())).build();

			userRepository.save(user);

			userServiceImpl.assignRoleToUser(request.getEmail(), "STUDENT");

			return AuthResponse.builder().token(jwtTokenProvider.getToken(user)).build();
		} catch (Exception e) {
			log.error("Error en el metodo [Register]");
		}
		return null;
	}
}
