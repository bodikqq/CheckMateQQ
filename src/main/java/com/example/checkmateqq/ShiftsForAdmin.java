package com.example.checkmateqq;

import java.util.Date;

public class ShiftsForAdmin {
    String station;
    Date date;
    String time;
    String employee;
    String numberOfEmployeesYet;

    public ShiftsForAdmin(String station,Date date, String time, String employee, String numberOfEmployeesYet) {
        this.station = station;
        this.date = date;
        this.time = time;
        this.employee = employee;
        this.numberOfEmployeesYet = numberOfEmployeesYet;
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

    public ShiftsForAdmin(String station, Date date, String time, String employee) {
        this.station = station;
        this.date = date;
        this.time = time;
        this.employee = employee;
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

    public String getNumberOfEmployeesYet() {
        return numberOfEmployeesYet;
    }

    public void setNumberOfEmployeesYet(String numberOfEmployeesYet) {
        this.numberOfEmployeesYet = numberOfEmployeesYet;
    }
}
