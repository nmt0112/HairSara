package com.example.hairsara.Interface;

import com.example.hairsara.Models.Barber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BarberApi {
    @GET("Barber/BarberByChiNhanh")
    Call<List<Barber>> getBarberByChiNhanh(@Query("chiNhanhId") int chiNhanhId);
}
