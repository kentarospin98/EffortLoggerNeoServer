package com.effortlogger.server.api_schemas;

import com.effortlogger.server.models.Worker;

public class PostWorkerOutput {
    public String status;
    public long id;
    public String username;
    public String first_name;
    public String last_name;
    public String middle_name;
    public String preferred_name;

    public PostWorkerOutput(String status) {
        this.status = status;
    }

    public PostWorkerOutput(String status, Worker worker) {
        this.status = status;
        this.id = worker.id;
        this.username = worker.username;
        this.first_name = worker.first_name;
        this.last_name = worker.last_name;
        this.middle_name = worker.middle_name;
        this.preferred_name = worker.preferred_name;
    }
}
