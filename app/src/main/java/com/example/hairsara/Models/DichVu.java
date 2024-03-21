package com.example.hairsara.Models;

import com.google.gson.annotations.SerializedName;

public class DichVu {
    @SerializedName("id")
    private int id;

    @SerializedName("tenDichVu")
    private String tenDichVu;

    @SerializedName("moTa")
    private String moTa;

    @SerializedName("gia")
    private float gia;

    public DichVu(int id, String tenDichVu, String moTa, float gia) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.moTa = moTa;
        this.gia = gia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
}
