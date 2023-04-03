package com.example.music_player;

import com.example.music_player.model.User;
import com.example.music_player.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition

public class MusicPlayerApplication implements CommandLineRunner {
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(MusicPlayerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user=new User();
		user.setPassword("sks123");
		user.setEmail("sks@gmail.com");
		user.setAge(22);
		user.setFirstName("sonu");
		user.setLastName("kumar");
		user.setUsername("sks");
		List<String> roles=new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_NORMAL");
		//user.setRoles("ROLE_ADMIN");
		user.setRoles(roles);
		userService.saveUser(user);
	}
}
