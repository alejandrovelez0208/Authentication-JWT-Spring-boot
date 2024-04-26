package com.example.jwt.myrunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.jwt.service.RoleService;

@Component
public class MyRunner implements CommandLineRunner{

	@Autowired
	private RoleService roleService;
	
	@Override
	public void run(String... args) throws Exception {
		
		roleService.createRole("ADMIN");
		roleService.createRole("USER");
		roleService.createRole("STUDENT");
	}

}
