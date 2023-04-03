import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "logs")
public class Log {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(columnName = "date", canBeNull = false)
    private LocalDateTime date;

    @DatabaseField(columnName = "start_time", format = "HH:mm:ss", canBeNull = false)
    private LocalDateTime startTime;

    @DatabaseField(columnName = "end_time", format = "HH:mm:ss")
    private LocalDateTime endTime;

    @DatabaseField(columnName = "delta_time")
    private double deltaTime;

    @DatabaseField(columnName = "life_cycle_step")
    private String lifeCycleStep;

    @DatabaseField(columnName = "effort_category")
    private String effortCategory;

    @DatabaseField(columnName = "deliverable")
    private String deliverable;

    public Log(String user) {
    	username = user;
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
    private void set_username(String un) {
    	username = un;
    }
    private void set_date(String d) {
    	setFormattedDate(d);
    }
    private void set_startTime(LocalDateTime start) {
    	startTime = start;
    }
    private void set_endTime(LocalDateTime end) {
    	endTime = end;
    }
    private void set_deltaTime(double delta) {
    	deltaTime = delta;
    }
    private void set_lifeCycleStep(String step) {
    	lifeCycleStep = step;
    }
    private void set_effortCategory(String category) {
    	effortCategory = category;
    }    
    private void set_deliverable(String deliv) {
    	deliverable = deliv;
    }
    
    
    //Getters
    private String get_username() {
    	return username;
    }
    private String get_date() {
    	return getFormattedDate();
    }
    private LocalDateTime get_startTime() {
    	return startTime;
    }
	private LocalDateTime get_endTime() {
    	return endTime;
    }
	private double get_deltaTime() {
		return deltaTime;
	}
	private String get_lifeCycleStep() {
		return lifeCycleStep;
	}
	private String get_effortCategory() {
		return effortCategory;
	}
	private String get_deliverable() {
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