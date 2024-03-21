package com.example.hairsara.Models;

// ViTri.java
import com.google.gson.annotations.SerializedName;

public class ViTri {
    @SerializedName("id")
    private int id;

    @SerializedName("tinhThanhPho")
    private String tinhThanhPho;

    public int getId() {
        return id;
    }

    public String getTinhThanhPho() {
        return tinhThanhPho;
    }
}

