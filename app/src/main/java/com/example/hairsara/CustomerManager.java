package com.example.hairsara;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hairsara.Interface.CustomerApi;
import com.example.hairsara.Models.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerManager {

    private Context context;
    private CustomerApi customerApi;

    public CustomerManager(Context context) {
        this.context = context;
        customerApi = RetrofitClient.getClient().create(CustomerApi.class);
    }

    public void getIdCustomerFromApi() {
        SharedPreferences preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String storedToken = preferences.getString("token", null);

        // Kiểm tra token và gọi API nếu tồn tại
        if (storedToken != null) {
            // Gọi API GetIdCustomer
            Call<Customer> call = customerApi.getIdCustomer("Bearer " + storedToken);
            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.isSuccessful()) {
                        Customer customerResponse = response.body();
                        int idCustomer = customerResponse.getId();
                        Log.d("Customer", "Id Khách Hàng là: " + idCustomer);

                        // Lưu idCustomer vào SharedPreferences hoặc thực hiện các thao tác khác cần thiết
                        saveIdCustomerToPrefs(idCustomer);

                        // Log to indicate that customer ID has been obtained successfully
                        Log.d("CustomerManager", "Customer ID obtained successfully: " + idCustomer);
                    } else {
                        // Xử lý khi API trả về không thành công
                        Log.e("CustomerManager", "API response not successful");
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    // Xử lý khi có lỗi kết nối mạng hoặc lỗi từ API
                    Log.e("CustomerManager", "API call failed: " + t.getMessage());
                }
            });
        }
    }

    private void saveIdCustomerToPrefs(int idCustomer) {
        // Lưu idCustomer vào SharedPreferences hoặc thực hiện các thao tác khác cần thiết
        SharedPreferences preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idCustomer", idCustomer);
        editor.apply();
    }
    public int getIdCustomer() {
        SharedPreferences preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return preferences.getInt("idCustomer", -1); // -1 is a default value if idCustomer is not found
    }
}
