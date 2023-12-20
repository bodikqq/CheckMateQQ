package com.example.checkmateqq.triedy;

public class Card {
    int id;

    public Card(int id, String cardNumber, int cvv, int date) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    String cardNumber;
    int cvv;
    int date;

}
