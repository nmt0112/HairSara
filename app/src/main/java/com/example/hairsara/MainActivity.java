package com.example.hairsara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.example.hairsara.Interface.CustomerApi;
import com.example.hairsara.Interface.Login;
import com.example.hairsara.Models.ApiResponse;
import com.example.hairsara.Request.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Login logininterface;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Khởi tạo views
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        // Kiểm tra nếu đã đăng nhập trước đó và Token còn hạn
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("token", null);

        if (storedToken != null) {
            // Kiểm tra hợp lệ của Token ở đây nếu cần
            // Nếu Token hợp lệ, chuyển hướng người dùng đến màn hình Home
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }

        // Initialize logininterface
        logininterface = RetrofitClient.getClient().create(Login.class);

        // Kiểm tra nếu loginButton không null trước khi thêm OnClickListener
        if (loginButton != null) {
            loginButton.setOnClickListener(v -> {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Call<ApiResponse> call = logininterface.login(new LoginRequest(username, password));
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            // Xử lý phản hồi thành công
                            ApiResponse apiResponse = response.body();
                            String token = apiResponse.getToken();
                            // Lưu Token vào SharedPreferences
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token);
                            editor.apply();
                            Log.d("TOKEN", "Token được lưu thành công: " + token);
                            // Chuyển hướng người dùng đến màn hình Home
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showAlertDialog("Login Failed", "Invalid username or password");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        showAlertDialog("Login Failed", "Error: " + t.getMessage());
                    }
                });
            });
        }
    }
    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}