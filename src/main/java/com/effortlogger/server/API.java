package com.effortlogger.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class API {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}
