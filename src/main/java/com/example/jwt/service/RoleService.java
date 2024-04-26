package com.example.jwt.service;

import com.example.jwt.entity.Role;

public interface RoleService {

	Role findByName(String name);
	
	Role createRole(String roleName);
}
