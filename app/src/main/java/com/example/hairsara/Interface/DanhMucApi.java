package com.example.hairsara.Interface;

import com.example.hairsara.Models.DanhMuc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DanhMucApi {
    @GET("DanhMuc/ListDanhMuc")
    Call<List<DanhMuc>> getListDanhMuc();
}
