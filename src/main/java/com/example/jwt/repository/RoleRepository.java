package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
}
