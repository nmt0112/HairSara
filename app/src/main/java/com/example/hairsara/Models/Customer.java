package com.example.hairsara.Models;

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("id")
    private int id;

    @SerializedName("nameCustomer")
    private String nameCustomer;

    public Customer(int id, String nameCustomer) {
        this.id = id;
        this.nameCustomer = nameCustomer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }
}
