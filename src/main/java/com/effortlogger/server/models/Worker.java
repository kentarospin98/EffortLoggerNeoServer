package com.effortlogger.server.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DatabaseTable(tableName = "workers")
public class Worker {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(canBeNull = false)
    private String password_hash;

    public Worker(String username, String password) {
        this.username = username;
        this.password_hash = new BCryptPasswordEncoder().encode(password);
    }

    boolean check_password(String password) {
        return new BCryptPasswordEncoder().matches(password, this.password_hash);
    }

}