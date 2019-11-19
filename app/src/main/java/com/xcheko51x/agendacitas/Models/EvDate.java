package com.xcheko51x.agendacitas.Models;

public class EvDate {

    private int year;
    private int day;
    private int month;
    private int hours;
    private int minutes;

    public EvDate() {
    }

    public EvDate(int year, int day, int month, int hours, int minutes) {
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "EvDate{" +
                "year=" + year +
                ", day=" + day +
                ", month=" + month +
                ", hours=" + hours +
                ", minutes=" + minutes +
                '}';
    }
}
