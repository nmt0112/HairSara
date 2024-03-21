package com.example.hairsara.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hairsara.Interface.BookingApi;
import com.example.hairsara.Interface.CustomerApi;
import com.example.hairsara.Models.Booking;
import com.example.hairsara.Models.Customer;
import com.example.hairsara.R;
import com.example.hairsara.RetrofitClient;
import com.example.hairsara.databinding.FragmentTwoBinding;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTwo extends Fragment {
    private FragmentTwoBinding binding;
    private CustomerApi customerApi;
    private BookingApi bookingApi;
    private int idCustomer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTwoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences preferences = requireActivity().getSharedPreferences("myPrefs", requireActivity().MODE_PRIVATE);
        String storedToken = preferences.getString("token", null);

        // Initialize SwipeRefreshLayout
        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayoutTwo);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Trigger the refresh
                handleCustomerApi(storedToken);

                // Disable refreshing animation after data is loaded
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if (storedToken != null) {
            customerApi = RetrofitClient.getClient().create(CustomerApi.class);
            bookingApi = RetrofitClient.getClient().create(BookingApi.class);

            // Gọi phương thức mới để xử lý API và dữ liệu
            handleCustomerApi(storedToken);
        }

        return root;
    }


    private void handleCustomerApi(String token) {
        Call<Customer> call = customerApi.getIdCustomer("Bearer " + token);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Customer customerResponse = response.body();
                    idCustomer = customerResponse.getId();
                    Log.d("Customer", "Id Khách Hàng là: " + idCustomer);
                    // Gọi phương thức mới để xử lý API và dữ liệu booking
                    handleBookingApi();
                } else {
                    // Xử lý khi API không thành công
                    Log.e("Customer", "Không thành công khi gọi API GetIdCustomer. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                // Xử lý khi có lỗi kết nối mạng hoặc lỗi từ API
                Log.e("Customer", "Lỗi kết nối hoặc lỗi từ API GetIdCustomer", t);
            }
        });
    }

    private void handleBookingApi() {
        final LinearLayout linearLayout = binding.linearLayoutTwo;
        Call<List<Booking>> bookingsCall = bookingApi.getBookingsByCustomer(idCustomer);
        bookingsCall.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful()) {
                    List<Booking> bookings = response.body();
                    // Xử lý danh sách booking được trả về
                    linearLayout.removeAllViews();
                    for (Booking booking : bookings) {
                        // Kiểm tra nếu trangThai là false
                        if (booking.isTrangThai()) {
                            View cardView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_two_card, null);

                            // Tìm các TextView trong CardView để hiển thị thông tin
                            TextView textViewId = cardView.findViewById(R.id.textViewIdTwo);
                            TextView textViewDate = cardView.findViewById(R.id.textViewDateTwo);
                            TextView textViewTime = cardView.findViewById(R.id.textViewTimeTwo);

                            // Hiển thị thông tin đặt chỗ trong TextViews
                            textViewId.setText("Booking ID: " + booking.getId());

                            // Tách chuỗi ngày và giờ
                            String dateTimeString = booking.getThoiGianBatDau();
                            String[] parts = dateTimeString.split("T");
                            String date = parts[0];
                            String time = parts[1]; // Lấy giá trị thời gian ở đây

                            // Định dạng lại ngày và giờ
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            SimpleDateFormat outputTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

                            try {
                                // Hiển thị thông tin ngày và giờ trong TextView
                                Date dateObject = inputFormat.parse(date);
                                String dateText = outputDateFormat.format(dateObject);

                                Date timeObject = outputTimeFormat.parse(time);
                                String timeText = outputTimeFormat.format(timeObject);

                                textViewDate.setText("Date: " + dateText);
                                textViewTime.setText("Time: " + timeText);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            // Thêm CardView vào LinearLayout
                            linearLayout.addView(cardView);
                        }
                    }
                } else {
                    // Xử lý khi API không thành công
                    Log.e("Booking", "Không thành công khi gọi API GetBookingsByCustomer. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                // Xử lý khi có lỗi kết nối mạng hoặc lỗi từ API
                Log.e("Booking", "Lỗi kết nối hoặc lỗi từ API GetBookingsByCustomer", t);
            }
        });
    }
}


