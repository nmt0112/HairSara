// ChiNhanhApi.java
package com.example.hairsara.Interface;

import com.example.hairsara.Models.ChiNhanh;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChiNhanhApi {
    @GET("ChiNhanh/GetByViTri")
    Call<List<ChiNhanh>> getChiNhanhByViTri(@Query("idViTri") int idViTri);
}
