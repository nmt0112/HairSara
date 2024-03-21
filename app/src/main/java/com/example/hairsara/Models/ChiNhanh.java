package com.example.hairsara.Models;

import com.google.gson.annotations.SerializedName;

public class ChiNhanh {
    @SerializedName("id")
    private int id;

    @SerializedName("tenChiNhanh")
    private String tenChiNhanh;

    @SerializedName("diaChi")
    private String diaChi;

    public ChiNhanh(int id, String tenChiNhanh, String diaChi) {
        this.id = id;
        this.tenChiNhanh = tenChiNhanh;
        this.diaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public String getDiaChi() {
        return diaChi;
    }
}

