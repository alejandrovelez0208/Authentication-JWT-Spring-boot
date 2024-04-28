package com.example.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.Role;
import com.example.jwt.repository.RoleRepository;
import com.example.jwt.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role findByName(String name) {
		try {
			return roleRepository.findByName(name);
		} catch (Exception e) {
			log.error("Error funcion [FindByName]");
		}
		return null;
	}

	@Override
	public Role createRole(String roleName) {
		try {
			Role role = Role.builder().name(roleName).build();
			return roleRepository.save(role);
		} catch (Exception e) {
			log.error("Error funcion [CreateRole]");
		}
		return null;
	}
}
