package com.effortlogger.server.models;

import com.effortlogger.server.api_schemas.PostWorkerInput;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "access_tokens")
public class AccessToken {
    /*
     * AccessToken object, stored in the access_tokens table.
     * All fields are public.
     * 
     * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
     */

    @DatabaseField(generatedId = true)
    public long id;

    // Adds a field called worker_id, which links to the workers table.
    @DatabaseField(canBeNull = false, foreign = true)
    public Worker worker;

    @DatabaseField(canBeNull = false)
    public String access_token;

    // This is needed by ORMLite
    public AccessToken() {
    }

    public AccessToken(Worker worker) {
        /*
         * AccessToken constructor using a Worker object.
         * 
         * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
         */
        this.worker = worker;

        // Generate a random string for the access token.
        // Using UUIDs to make sure they're unique
        // + they store info about when they were created
        this.access_token = UUID.randomUUID().toString();
    }

}