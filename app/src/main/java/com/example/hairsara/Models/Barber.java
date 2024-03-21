package com.example.hairsara.Models;

import com.google.gson.annotations.SerializedName;

public class Barber {
    @SerializedName("id")
    private int id;

    @SerializedName("nameBarber")
    private String nameBarber;

    public Barber(int id, String nameBarber) {
        this.id = id;
        this.nameBarber = nameBarber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBarber() {
        return nameBarber;
    }

    public void setNameBarber(String nameBarber) {
        this.nameBarber = nameBarber;
    }
}
