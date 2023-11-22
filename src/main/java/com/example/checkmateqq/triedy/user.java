package com.example.checkmateqq.triedy;

public class user {
    String name;
    String surname;

    String login;
    String password;
    boolean isEmployee = false;
    boolean isAdmin = false;
    public user(String name, String surname, String login, String password, boolean isEmployee, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.isEmployee = isEmployee;
        this.isAdmin = isAdmin;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
