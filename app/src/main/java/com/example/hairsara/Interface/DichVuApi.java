package com.example.hairsara.Interface;

import com.example.hairsara.Models.DichVu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DichVuApi {
    @GET("DichVu/GetByDanhMuc")
    Call<List<DichVu>> getDichVuByDanhMuc(@Query("idDanhMuc") int idDanhMuc);
}
