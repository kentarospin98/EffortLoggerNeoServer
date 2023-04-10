package com.effortlogger.server.models;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.effortlogger.server.api_schemas.PostLogInput;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "logs")
public class Log {
    /*
     * Log object. Stored in the logs table.
     * All fields are public.
     * 
     * @author Jordan Eiselt <jeiselt@asu.edu>
     * 
     * @author Vikriti Lokegaonkar <dlokegao@asu.edu>
     */

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String username;

    @DatabaseField(columnName = "date", canBeNull = false)
    public Date date;

    @DatabaseField(columnName = "start_time", format = "HH:mm:ss", canBeNull = false)
    public Date startTime;

    @DatabaseField(columnName = "end_time", format = "HH:mm:ss")
    public Date endTime;

    @DatabaseField(columnName = "life_cycle_step")
    public String lifeCycleStep;

    @DatabaseField(columnName = "effort_category")
    public String effortCategory;

    @DatabaseField(columnName = "deliverable")
    public String deliverable;

    public Log() {
    }

    public Log(String user) {
        this.username = user;
        this.date = new Date();
        start();
    }

    public Log(PostLogInput user) {
        this.username = user.username;
        this.date = new Date();
        start();
    }

    public void start() {
        /*
         * Method to set the start time
         * 
         * @author Jordan Eiselt <jeiselt@asu.edu>
         */
        this.startTime = new Date();
    }

    public void end() {
        /*
         * Method to set the end time and calculate delta time
         * 
         * @author Jordan Eiselt <jeiselt@asu.edu>
         */
        this.endTime = new Date();
        // this.deltaTime = (double) (this.endTime.toInstant().toEpochMilli()
        // - this.startTime.toInstant().toEpochMilli()) / 60000.0;
    }

    // Custom formatter for the date field
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getFormattedDate() {
        return this.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(dateFormatter);
    }

    public void setFormattedDate(String dateStr) {
        this.date = Date.from(LocalDateTime.parse(dateStr, dateFormatter).atZone(ZoneId.systemDefault()).toInstant());
    }
}