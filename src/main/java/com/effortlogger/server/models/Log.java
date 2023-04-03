package com.effortlogger.server.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.effortlogger.server.api_schemas.PostLogInput;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "logs")
public class Log {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String username;

    @DatabaseField(columnName = "date", canBeNull = false)
    public LocalDateTime date;

    @DatabaseField(columnName = "start_time", format = "HH:mm:ss", canBeNull = false)
    public LocalDateTime startTime;

    @DatabaseField(columnName = "end_time", format = "HH:mm:ss")
    public LocalDateTime endTime;

    @DatabaseField(columnName = "delta_time")
    public double deltaTime;

    @DatabaseField(columnName = "life_cycle_step")
    public String lifeCycleStep;

    @DatabaseField(columnName = "effort_category")
    public String effortCategory;

    @DatabaseField(columnName = "deliverable")
    public String deliverable;

    public Log(String user) {
    	username = user;
    	date = LocalDateTime.now();
        start();
    }
    
    public Log(PostLogInput user) {
    	username = user.username;
    	date = LocalDateTime.now();
        start();
    }

    // Function to set the start time
    public void start() {
        this.startTime = LocalDateTime.now();
    }

    // Function to set the end time and calculate delta time
    public void end() {
        this.endTime = LocalDateTime.now();
        this.deltaTime = (double) (this.endTime.toEpochSecond(null) - this.startTime.toEpochSecond(null)) / 60.0;
    }

    //Setters
    public void set_username(String un) {
    	username = un;
    }
    public void set_date(String d) {
    	setFormattedDate(d);
    }
    public void set_startTime(LocalDateTime start) {
    	startTime = start;
    }
    public void set_endTime(LocalDateTime end) {
    	endTime = end;
    }
    public void set_deltaTime(double delta) {
    	deltaTime = delta;
    }
    public void set_lifeCycleStep(String step) {
    	lifeCycleStep = step;
    }
    public void set_effortCategory(String category) {
    	effortCategory = category;
    }    
    public void set_deliverable(String deliv) {
    	deliverable = deliv;
    }
    
    
    //Getters
    public String get_username() {
    	return username;
    }
    public String get_date() {
    	return getFormattedDate();
    }
    public LocalDateTime get_startTime() {
    	return startTime;
    }
    public LocalDateTime get_endTime() {
    	return endTime;
    }
    public double get_deltaTime() {
		return deltaTime;
	}
    public String get_lifeCycleStep() {
		return lifeCycleStep;
	}
    public String get_effortCategory() {
		return effortCategory;
	}
    public String get_deliverable() {
		return deliverable;
	}
	
    // Custom formatter for the date field
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getFormattedDate() {
        return this.date.format(dateFormatter);
    }

    public void setFormattedDate(String dateStr) {
        this.date = LocalDateTime.parse(dateStr, dateFormatter);
    }
}