package com.example.checkmateqq.triedy;

public class Station {
    int id;
    String town;
    String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Station(int id, String town, String address) {
        this.id = id;
        this.town = town;
        this.address = address;
    }
}
