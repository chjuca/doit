package com.xcheko51x.agendacitas.Models;

public class EvDate {

    private int year;
    private String day;
    private String month;
    private String hours;
    private String minutes;

    public EvDate() {
    }

    public EvDate(int year, String day, String month, String hours, String minutes) {
        this.year = year;
        this.day = day;
        this.month = month;
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
}
