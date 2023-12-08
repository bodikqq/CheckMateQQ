package com.example.checkmateqq;

import java.sql.Time;

public class HourMinutes {
    String time0;
    String time1;
    String time2;
    String time3;
    String time4;

    public void setTime0(String time0) {
        this.time0 = time0;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public void setTime5(String time5) {
        this.time5 = time5;
    }

    @Override
    public String toString() {
        return "HourMinutes{" +
                "time0='" + time0 + '\'' +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", time3='" + time3 + '\'' +
                ", time4='" + time4 + '\'' +
                ", time5='" + time5 + '\'' +
                '}';
    }

    public String getTime0() {
        return time0;
    }

    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public String getTime3() {
        return time3;
    }

    public String getTime4() {
        return time4;
    }

    public String getTime5() {
        return time5;
    }

    public HourMinutes(String time0, String time1, String time2, String time3, String time4, String time5) {
        this.time0 = time0;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
    }

    String time5;
}


