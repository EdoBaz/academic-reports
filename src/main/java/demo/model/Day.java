package demo.model;

import java.time.LocalDate;

public class Day {
    private LocalDate date;
    private int hours;
    private boolean isHoliday;

    public Day(LocalDate date, int hours, boolean isHoliday) {
        this.date = date;
        this.hours = hours;
        this.isHoliday = isHoliday;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }
}
