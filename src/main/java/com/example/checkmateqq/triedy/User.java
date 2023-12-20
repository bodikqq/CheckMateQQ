package com.example.checkmateqq.triedy;

public class User {

    int id;
    String name;
    String surname;

    String login;
    String password;
    boolean isEmployee = false;
    boolean isAdmin = false;
    int card_id;
    double balance;

    public User(int id, String name, String surname, String login, String password, boolean isEmployee, boolean isAdmin, int card_id, double balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.isEmployee = isEmployee;
        this.isAdmin = isAdmin;
        this.card_id = card_id;
        this.balance = balance;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isEmployee=" + isEmployee +
                ", isAdmin=" + isAdmin +
                '}';
    }




    public User(int id, String name, String surname, String login, String password, boolean isEmployee, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.isEmployee = isEmployee;
        this.isAdmin = isAdmin;
    }
    public User( String name, String surname, String login, String password, boolean isEmployee, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.isEmployee = isEmployee;
        this.isAdmin = isAdmin;
    }

    public static User clone(User u) {return new User(u.id, u.name, u.surname,u.login,u.password,u.isEmployee,u.isAdmin);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
