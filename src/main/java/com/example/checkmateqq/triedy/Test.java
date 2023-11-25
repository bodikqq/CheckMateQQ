package com.example.checkmateqq.triedy;

public class Test {
    int id;

    int result;

    String testcol;

    int patient_id;

    int shift_id;

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

    public String getTestcol() {
        return testcol;
    }

    public void setTestcol(String testcol) {
        this.testcol = testcol;
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

    public Test(int id, int result, String testcol, int patient_id, int shift_id) {
        this.id = id;
        this.result = result;
        this.testcol = testcol;
        this.patient_id = patient_id;
        this.shift_id = shift_id;
    }
}
