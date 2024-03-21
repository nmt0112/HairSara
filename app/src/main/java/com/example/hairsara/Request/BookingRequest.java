package com.example.hairsara.Request;

import com.google.gson.annotations.SerializedName;

public class BookingRequest {
    @SerializedName("idCustomer")
    private int idCustomer;

    @SerializedName("idChiNhanh")
    private int idChiNhanh;

    @SerializedName("idBarber")
    private int idBarber;

    @SerializedName("idDichVu")
    private int idDichVu;

    @SerializedName("thoiGianBatDau")
    private String thoiGianBatDau;

    public BookingRequest(int idCustomer, int idChiNhanh, int idBarber, int idDichVu, String thoiGianBatDau) {
        this.idCustomer = idCustomer;
        this.idChiNhanh = idChiNhanh;
        this.idBarber = idBarber;
        this.idDichVu = idDichVu;
        this.thoiGianBatDau = thoiGianBatDau;
    }
}

