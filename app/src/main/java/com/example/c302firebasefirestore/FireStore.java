package com.example.c302firebasefirestore;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class FireStore implements Serializable {

    private String id;
    private String name;
    private  double cost;

    public FireStore() {
    }

    public FireStore(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public String toString() {
        return "Name: " + name;
    }
}
