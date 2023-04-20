package rs.raf.scheduler.models;

import java.time.LocalDate;

public class Day extends Model {

    private LocalDate date;

    public Day(LocalDate date) {
        super(null);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
