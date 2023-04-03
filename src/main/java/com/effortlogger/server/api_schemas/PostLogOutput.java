package com.effortlogger.server.api_schemas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.effortlogger.server.models.Log;

public class PostLogOutput {
    public String status;
    public long id;
    public String username;
    public Date startTime;
    public Date endTime;
    public double deltaTime;
    public String lifeCycleStep;
    public String effortCategory;
    public String deliverable;

    public PostLogOutput(String status) {
        this.status = status;
    }

    public PostLogOutput(String status, Log log) {
        this.status = status;
        this.id = log.id;
        this.username = log.username;
        this.startTime = log.startTime;
        this.endTime = log.endTime;
        this.deltaTime = log.deltaTime;
        this.lifeCycleStep = log.lifeCycleStep;
        this.effortCategory = log.effortCategory;
        this.deliverable = log.deliverable;
    }
}
