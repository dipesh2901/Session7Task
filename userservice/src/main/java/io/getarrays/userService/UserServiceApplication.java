package io.getarrays.userService;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.getarrays.userService.domain.Role;
import io.getarrays.userService.domain.User;
import io.getarrays.userService.service.UserService;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Dipesh", "dipesh123", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Sornodeep", "sorno123", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Sruti", "sruti123", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Ketan", "ketan123", "1234", new ArrayList<>()));

			userService.addRoleToUser("dipesh123", "ROLE_USER");
			userService.addRoleToUser("dipesh123", "ROLE_MANAGER");
			userService.addRoleToUser("sorno123", "ROLE_MANAGER");
			userService.addRoleToUser("sruti123", "ROLE_ADMIN");
			userService.addRoleToUser("ketan123", "ROLE_USER");
			userService.addRoleToUser("ketan123", "ROLE_ADMIN");
			userService.addRoleToUser("ketan123", "ROLE_SUPER_ADMIN");
		};
	}

}
