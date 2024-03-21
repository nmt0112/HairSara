package com.example.hairsara.Interface;

import com.example.hairsara.Models.Customer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CustomerApi {
    @GET("GetIdCustomer")
    @Headers("Accept: */*")
    Call<Customer> getIdCustomer(@Header("Authorization") String authorizationHeader);
}



