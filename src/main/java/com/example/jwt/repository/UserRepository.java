package com.example.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String userName);

	User existsByEmail(String email);
	
	User findByEmail(String email);

	Optional<User> findByUserNameOrEmail(String userName, String email);

	Boolean existsByUserName(String userName);
}
