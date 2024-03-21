package com.example.hairsara.Api;

import com.example.hairsara.Models.Booking;
import com.example.hairsara.Models.Customer;

import com.example.hairsara.Models.Barber;
import com.example.hairsara.Models.ChiNhanh;
import com.example.hairsara.Models.DanhMuc;
import com.example.hairsara.Models.DichVu;

import com.example.hairsara.Models.ViTri;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("Barber/BarberByChiNhanh")
    Call<List<Barber>> getBarberByChiNhanh(@Query("chiNhanhId") int chiNhanhId);
    @GET("Barber/ListBarber")
    Call<List<Barber>> getBarber();
    @GET("ChiNhanh/ListChiNhanh")
    Call<List<ChiNhanh>> getChiNhanh();

    @GET("ChiNhanh/GetByViTri")

    Call<List<ChiNhanh>> getChiNhanhByViTri(@Query("idViTri") int idViTri);
    @GET("DanhMuc/ListDanhMuc")
    Call<List<DanhMuc>> getListDanhMuc();
    @GET("DichVu/ListDichVu")
    Call<List<DichVu>> getDichVu();
    @GET("DichVu/GetByDanhMuc")
    Call<List<DichVu>> getDichVuByDanhMuc(@Query("idDanhMuc") int idDanhMuc);

    @GET("ViTri/ListViTri")
    Call<List<ViTri>> getListViTri();
    @GET("GetIdCustomer")
    @Headers("Accept: */*")
    Call<Customer> getIdCustomer(@Header("Authorization") String authorizationHeader);


    @GET("Booking/GetByCustomer")
    Call<List<Booking>> getBookingsByCustomer(@Query("idCustomer") int idCustomer);
}


