package com.effortlogger.server;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.effortlogger.server.api_schemas.PostWorker;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

@RestController
public class API {
	JdbcPooledConnectionSource connectionSource;

	API() {
		try {
			this.connectionSource = new JdbcPooledConnectionSource("jdbc:h2:mem:myDb");
		} catch (SQLException e) {
			System.out.println("Unable to connect to the database.");
			e.printStackTrace();
		}
	}

	@GetMapping("/")
	public String index() {
		return "{\"status\": \"success\"}";
	}

	@PostMapping("/worker")
	public String post_worker(@RequestBody PostWorker worker) {

		return "{\"status\": \"created user " + worker.username + "\"}";
	}

}
