package rs.raf.scheduler.models;

import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.repositories.IModelRepository;

public class Task extends Model {

    private String date;
    private String startTime;
    private String endTime;
    private String title;
    private Priority priority;
    private String description;

    public Task(String date, String title, String startTime, String endTime, Priority priority, String description) {
        super(App.getTaskRepository());
        this.date = date;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum Priority {
        LOW, MID, HIGH
    }
}
