package com.example.jwt.service.impl;

import org.springframework.stereotype.Service;

import com.example.jwt.entity.Role;
import com.example.jwt.entity.User;
import com.example.jwt.repository.RoleRepository;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	@Override
	public void assignRoleToUser(String email, String nameRole) {
		try {
			User user = userRepository.existsByEmail(email);

			Role role = roleRepository.findByName(nameRole);

			user.assignRoleToUser(role);
		} catch (Exception e) {
			log.error("Error ejecutando funcion [assignRoleToUser]", e.getMessage());
		}
	}

}
