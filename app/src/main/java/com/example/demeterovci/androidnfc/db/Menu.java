package com.example.demeterovci.androidnfc.db;

/**
 * Created by martin on 27. 10. 2017.
 */

public class Menu {

    int id;
    String name;
    double cost;

    // constructor
    public Menu(int id, String name, double cost){
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    // constructor
    public Menu(String name, double cost){
        this.name = name;
        this.cost = cost;
    }

    public Menu(){
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
