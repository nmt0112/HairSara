package com.example.hairsara.Models;

import com.google.gson.annotations.SerializedName;

public class DanhMuc {
    @SerializedName("id")
    private int id;

    @SerializedName("tenDanhMuc")
    private String tenDanhMuc;

    @SerializedName("moTaDanhMuc")
    private String moTaDanhMuc;

    public DanhMuc(int id, String tenDanhMuc, String moTaDanhMuc) {
        this.id = id;
        this.tenDanhMuc = tenDanhMuc;
        this.moTaDanhMuc = moTaDanhMuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMoTaDanhMuc() {
        return moTaDanhMuc;
    }

    public void setMoTaDanhMuc(String moTaDanhMuc) {
        this.moTaDanhMuc = moTaDanhMuc;
    }
}
