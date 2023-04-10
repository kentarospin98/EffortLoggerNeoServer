package com.effortlogger.server.models;

import com.effortlogger.server.api_schemas.PostWorkerInput;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DatabaseTable(tableName = "workers")
public class Worker {
    /*
     * Worker Object. Stored in the workers table.
     * All fields except password_hash are public.
     * 
     * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
     */

    @DatabaseField(generatedId = true)
    public long id;

    @DatabaseField(canBeNull = false, unique = true)
    public String username;

    @DatabaseField(canBeNull = false)
    private String password_hash;

    @DatabaseField(canBeNull = false)
    public String first_name;

    @DatabaseField(canBeNull = false)
    public String last_name;

    @DatabaseField(canBeNull = true)
    public String middle_name;

    @DatabaseField(canBeNull = false)
    public String preferred_name;

    // This is needed by ORMLite
    public Worker() {
    }

    public Worker(String username, String password, String first_name, String last_name, String middle_name,
            String preferred_name) {
        /*
         * Worker constructor using individual fields
         * 
         * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
         */
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.preferred_name = preferred_name;
        this.password_hash = new BCryptPasswordEncoder().encode(password);
    }

    public Worker(PostWorkerInput worker) {
        /*
         * Worker constructor using PostWorkerInput object
         * 
         * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
         */
        this.username = worker.username;
        this.first_name = worker.first_name;
        this.last_name = worker.last_name;
        this.middle_name = worker.middle_name;
        this.preferred_name = worker.preferred_name;
        this.password_hash = new BCryptPasswordEncoder().encode(worker.password);
    }

    public void set_password(String password) {
        /*
         * Hashes the password and sets it in the pasword_hash field.
         * 
         * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
         */
        this.password_hash = new BCryptPasswordEncoder().encode(password);
    }

    public boolean check_password(String password) {
        /*
         * Checks if the passed in matches the hashed password.
         * 
         * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
         */
        return new BCryptPasswordEncoder().matches(password, this.password_hash);
    }
}