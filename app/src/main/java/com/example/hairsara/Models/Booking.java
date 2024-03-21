package com.example.hairsara.Models;

import com.google.gson.annotations.SerializedName;
public class Booking {
    @SerializedName("id")
    private int id;

    @SerializedName("thoiGianBatDau")
    private String thoiGianBatDau;

    @SerializedName("trangThai")
    private boolean trangThai;

    @SerializedName("idCustomer")
    private int idCustomer;

    @SerializedName("idBarber")
    private int idBarber;

    @SerializedName("idDichVu")
    private int idDichVu;

    @SerializedName("idChiNhanh")
    private int idChiNhanh;

    public Booking(int id, String thoiGianBatDau, boolean trangThai, int idCustomer, int idBarber, int idDichVu, int idChiNhanh) {
        this.id = id;
        this.thoiGianBatDau = thoiGianBatDau;
        this.trangThai = trangThai;
        this.idCustomer = idCustomer;
        this.idBarber = idBarber;
        this.idDichVu = idDichVu;
        this.idChiNhanh = idChiNhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdBarber() {
        return idBarber;
    }

    public void setIdBarber(int idBarber) {
        this.idBarber = idBarber;
    }

    public int getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(int idDichVu) {
        this.idDichVu = idDichVu;
    }

    public int getIdChiNhanh() {
        return idChiNhanh;
    }

    public void setIdChiNhanh(int idChiNhanh) {
        this.idChiNhanh = idChiNhanh;
    }
}
