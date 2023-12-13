package com.example.checkmateqq.triedy;

import java.util.Date;

public class Shift {
    int id;

    int station_id;

    Date date;

    public Shift(int id, int station_id, Date date, boolean isFirst) {
        this.id = id;
        this.station_id = station_id;
        this.date = date;
        this.isFirst = isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    boolean isFirst;

    public boolean isFirst() {
        return isFirst;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Shift(int id, int station_id, Date date) {
        this.id = id;
        this.station_id = station_id;
        this.date = date;
    }
}
