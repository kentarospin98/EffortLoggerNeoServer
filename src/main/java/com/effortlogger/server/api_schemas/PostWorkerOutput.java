package com.effortlogger.server.api_schemas;

public class PostWorkerOutput {
    public String status;
    public long id;
    public String username;

    public PostWorkerOutput(String status) {
        this.status = status;
    }

    public PostWorkerOutput(String status, long id, String username) {
        this.status = status;
        this.id = id;
        this.username = username;
    }
}
