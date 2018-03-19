package com.example.joes.timetable;

/**
 * Created by Joes on 14/3/2018.
 */

public class Timetable {

    private String Date;
    private String StartTime;
    private String EndTime;
    private String Location;
    private String Module;

    public Timetable(String date, String startTime, String endTime, String location, String module) {
        Date = date;
        StartTime = startTime;
        EndTime = endTime;
        Location = location;
        Module = module;
    }

    public String getDate() {
        return Date;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getLocation() {
        return Location;
    }

    public String getModule() {
        return Module;
    }
}
