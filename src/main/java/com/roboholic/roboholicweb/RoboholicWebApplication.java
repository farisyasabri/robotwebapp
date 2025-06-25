package com.roboholic.roboholicweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RoboholicWebApplication {

	public static void main(String[] args) {

		// System.out.println("Encoded password: " + 
        //     new BCryptPasswordEncoder().encode("admin123"));

		SpringApplication.run(RoboholicWebApplication.class, args);
	}

}
