package com.example.checkmateqq;

import java.util.Date;

public class ShiftsForAdmin {
    int id;
    String station;
    Date date;
    String time;
    String employee;

    int employeeId;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public ShiftsForAdmin(int id, String station, Date date, String time, String employee, int employeeId) {
        this.id = id;
        this.station = station;
        this.date = date;
        this.time = time;
        this.employee = employee;
        this.employeeId = employeeId;
    }

    public ShiftsForAdmin(int id, String station, Date date, String time, String employee) {
        this.id = id;
        this.station = station;
        this.date = date;
        this.time = time;
        this.employee = employee;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
}
