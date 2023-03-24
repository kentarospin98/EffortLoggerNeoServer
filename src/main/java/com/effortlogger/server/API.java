package com.effortlogger.server;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.effortlogger.server.api_schemas.GetIndex;
import com.effortlogger.server.api_schemas.PostWorkerInput;
import com.effortlogger.server.api_schemas.PostWorkerOutput;
import com.effortlogger.server.models.Worker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

@RestController
public class API {
	JdbcPooledConnectionSource connectionSource;
	Dao<Worker, String> workerDao;

	API() {
		// Connect to the database
		try {
			this.connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:efforlogger.sqlite");
		} catch (SQLException e) {
			System.out.println("Unable to connect to the database.");
			e.printStackTrace();
			return;
		}

		// Create the class that handles all database operations.
		// https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html#DAO-Setup
		try {
			TableUtils.createTableIfNotExists(connectionSource, Worker.class);
			this.workerDao = DaoManager.createDao(connectionSource, Worker.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/")
	public GetIndex index() {
		return new GetIndex("success");
	}

	@PostMapping("/worker")
	public PostWorkerOutput post_worker(@RequestBody PostWorkerInput worker) {
		// Create a worker object
		Worker new_worker = new Worker(worker);

		// Add it to the database.
		try {
			this.workerDao.create(new_worker);

		} catch (SQLException e) {
			// If Worker already exists, return failure
			return new PostWorkerOutput("failure: Already exists");
		}

		// Return success
		return new PostWorkerOutput("success", new_worker);
	}

}
