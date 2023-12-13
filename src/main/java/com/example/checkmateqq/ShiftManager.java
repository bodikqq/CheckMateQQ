package com.example.checkmateqq;

import java.util.Date;

public class ShiftManager {

    String station;
    Date date;
    String time;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ShiftManager(String station, Date date, String time) {
        this.station = station;
        this.date = date;
        this.time = time;
    }




}
