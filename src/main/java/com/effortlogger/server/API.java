package com.effortlogger.server;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.effortlogger.server.api_schemas.GetIndex;
import com.effortlogger.server.api_schemas.PostLoginInput;
import com.effortlogger.server.api_schemas.PostLoginOutput;
import com.effortlogger.server.api_schemas.PostWorkerInput;
import com.effortlogger.server.api_schemas.PostWorkerOutput;
import com.effortlogger.server.api_schemas.PostLogInput;
import com.effortlogger.server.api_schemas.PostLogOutput;
import com.effortlogger.server.models.AccessToken;
import com.effortlogger.server.models.Worker;
import com.effortlogger.server.models.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

@RestController
public class API {
	JdbcPooledConnectionSource connectionSource;
	Dao<Worker, String> workerDao;
	Dao<AccessToken, String> accessTokenDao;
	Dao<Log, String> logDao;

	API() {
		/*
		 * API Constuctor.
		 * 
		 * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
		 */

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
			TableUtils.createTableIfNotExists(connectionSource, AccessToken.class);
			this.accessTokenDao = DaoManager.createDao(connectionSource, AccessToken.class);
			TableUtils.createTableIfNotExists(connectionSource, Log.class);
			this.logDao = DaoManager.createDao(connectionSource, Log.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/")
	public GetIndex index() {
		/*
		 * GET /
		 * Always returns success
		 * 
		 * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
		 */
		return new GetIndex("success");
	}

	@PostMapping("/login")
	public ResponseEntity<PostLoginOutput> post_login(@RequestBody PostLoginInput credentials) {
		/*
		 * POST /login
		 * Logs the user in and returns an access token.
		 * 
		 * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
		 */

		List<Worker> workers;

		// Get the worker in the username.
		try {
			workers = this.workerDao.queryForEq("username", credentials.username);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new PostLoginOutput("failed"));
		}

		// If the worker with the username is not found, return an error
		if (workers.size() < 1)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new PostLoginOutput("username or password incorrect.", null, null));

		// If the worker is found and password is correct, log the user in.
		if (workers.get(0).check_password(credentials.password)) {
			// Generate a new access token for the user
			AccessToken access_token = new AccessToken(workers.get(0));

			// Add it to the database
			try {
				this.accessTokenDao.create(access_token);
			} catch (SQLException e) {
				return ResponseEntity.internalServerError()
						.body(new PostLoginOutput("failed"));
			}

			// Return the access token
			return ResponseEntity.ok()
					.body(new PostLoginOutput("logged in", workers.get(0).username, access_token.access_token));
		}

		// If password is incorrect, return an error
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new PostLoginOutput("username or password incorrect.", null, null));
	}

	@PostMapping("/worker")
	public PostWorkerOutput post_worker(@RequestBody PostWorkerInput worker) {
		/*
		 * POST /worker
		 * Creates a new worker, and returns the worker details.
		 * 
		 * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
		 */

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

	@PostMapping("/log")
	public PostLogOutput post_log(@RequestBody PostLogInput log) {
		/*
		 * POST /log
		 * Creates a log, and returns it
		 * 
		 * TODO: Check user's login status
		 * 
		 * @author Jordan Eiselt <jeiselt@asu.edu>
		 */
		Log new_log = new Log(log);
		try {
			this.logDao.create(new_log);
		} catch (SQLException e) {
			return new PostLogOutput("Failed to create new log");
		}
		return new PostLogOutput("Success", new_log);
	}

}
