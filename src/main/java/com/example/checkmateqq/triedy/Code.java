package com.example.checkmateqq.triedy;

public class Code {
    int id;
    String code;
    Boolean is_active;

    public Code(int id, String code, Boolean is_active) {
        this.id = id;
        this.code = code;
        this.is_active = is_active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
}
