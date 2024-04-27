package com.example.jwt.myrunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.RoleService;
import com.example.jwt.service.impl.UserServiceImpl;

@Component
public class MyRunner implements CommandLineRunner {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Override
	public void run(String... args) throws Exception {

		roleService.createRole("ADMIN");
		roleService.createRole("USER");
		roleService.createRole("STUDENT");

		User user = User.builder().name("Alejandro").userName("alejo0208").email("alejandro@gmail.com")
				.password("123456").build();

		userRepository.save(user);

		userServiceImpl.assignRoleToUser("alejandro@gmail.com", "STUDENT");

	}

}
