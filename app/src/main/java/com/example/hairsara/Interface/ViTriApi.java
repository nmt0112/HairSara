package com.example.hairsara.Interface;

import com.example.hairsara.Models.ViTri;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ViTriApi {
    @GET("ViTri/ListViTri")
    Call<List<ViTri>> getListViTri();
}
