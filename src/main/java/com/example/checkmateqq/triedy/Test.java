package com.example.checkmateqq.triedy;

import java.sql.Time;
import java.util.Date;

public class Test {
    int id;
    int result;
    Date date;
    int patient_id;

    public Test(Date date, int patient_id, Time time, int test_type) {
        this.date = date;
        this.patient_id = patient_id;
        this.time = time;
        this.test_type = test_type;
    }

    int shift_id;
    Time time;
    int test_type;

    public int getTest_type() {
        return test_type;
    }

    public void setTest_type(int test_type) {
        this.test_type = test_type;
    }

    public Test(int id, int result, Date date, int patient_id, int shift_id, Time time, int test_type) {
        this.id = id;
        this.result = result;
        this.date = date;
        this.patient_id = patient_id;
        this.shift_id = shift_id;
        this.time = time;
        this.test_type = test_type;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }


    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }


    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
