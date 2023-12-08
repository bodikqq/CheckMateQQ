package com.example.checkmateqq;

import java.util.Date;

public class ForTestTable {
    private Date date;
    private String result;

    private String test_type;

    public ForTestTable(Date date, String result,String test_type) {
        this.date = date;
        this.result = result;
        this.test_type = test_type;
    }

    public Date getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }
    public String getTest_type() {
        return test_type;
    }
    // Make sure to have appropriate setters if needed
}
