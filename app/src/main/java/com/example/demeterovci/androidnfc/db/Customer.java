package com.example.demeterovci.androidnfc.db;

/**
 * Created by martin on 27. 10. 2017.
 */

public class Customer {

    int id;
    String card_id;
    double money;

    // constructor
    public Customer(int id, String card_id, double money){
        this.id = id;
        this.card_id = card_id;
        this.money = money;
    }

    public Customer(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString(){
        return this.card_id;
    }
}
