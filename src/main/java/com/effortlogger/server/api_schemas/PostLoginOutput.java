package com.effortlogger.server.api_schemas;

public class PostLoginOutput {
    public String status;
    public String username;
    public String access_token;

    public PostLoginOutput(String status, String username, String access_token) {
        this.status = status;
        this.username = username;
        this.access_token = access_token;
    }

    public PostLoginOutput(String status) {
        this.status = status;
    }
}
