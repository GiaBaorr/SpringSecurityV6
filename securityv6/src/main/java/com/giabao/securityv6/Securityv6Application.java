package com.giabao.securityv6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
public class Securityv6Application {

	public static void main(String[] args) {
		SpringApplication.run(Securityv6Application.class, args);
	}

}
