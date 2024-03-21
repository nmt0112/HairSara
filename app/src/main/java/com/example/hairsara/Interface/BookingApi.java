package com.example.hairsara.Interface;

import com.example.hairsara.Models.Booking;
import com.example.hairsara.Request.BookingRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingApi {
    @POST("Booking/DatLich")
    Call<Void> datLich(@Query("idCustomer") int idCustomer,
                       @Query("idChiNhanh") int idChiNhanh,
                       @Query("idBarber") int idBarber,
                       @Query("idDichVu") int idDichVu,
                       @Body BookingRequest bookingRequest);
    @GET("Booking/GetByCustomer")
    Call<List<Booking>> getBookingsByCustomer(@Query("idCustomer") int idCustomer);
}
