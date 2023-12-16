package com.example.checkmateqq.triedy;

public class Uhs {
    int user_id;
    int shift_id;
    boolean isConfirmed;

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public Uhs(int user_id, int shift_id, boolean isConfirmed) {
        this.user_id = user_id;
        this.shift_id = shift_id;
        this.isConfirmed = isConfirmed;
    }

    public Uhs(int user_id, int shift_id) {
        this.user_id = user_id;
        this.shift_id = shift_id;
    }
}
