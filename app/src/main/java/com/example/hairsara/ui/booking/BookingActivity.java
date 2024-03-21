package com.example.hairsara.ui.booking;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import com.example.hairsara.Home;
import com.example.hairsara.Interface.BarberApi;
import com.example.hairsara.Interface.BookingApi;
import com.example.hairsara.Interface.ChiNhanhApi;
import com.example.hairsara.Interface.CustomerApi;
import com.example.hairsara.Interface.DanhMucApi;
import com.example.hairsara.Interface.DichVuApi;
import com.example.hairsara.Interface.ViTriApi;
import com.example.hairsara.Models.Barber;
import com.example.hairsara.Models.ChiNhanh;
import com.example.hairsara.Models.Customer;
import com.example.hairsara.Models.DanhMuc;
import com.example.hairsara.Models.DichVu;
import com.example.hairsara.Models.ViTri;
import com.example.hairsara.R;
import com.example.hairsara.Request.BookingRequest;
import com.example.hairsara.RetrofitClient;
import com.example.hairsara.ui.notifications.NotificationsFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    private BookingApi bookingApi;
    private int idChiNhanh = -1;
    private int idDichVu = -1;
    private int idBarber = -1;
    private String selectedNgay = "";
    private String selectedGio = "";
    private Spinner spinnerViTri;
    private Spinner spinnerDanhMuc;
    private TextView logTextView;
    private Spinner spinnerNgay;
    private Button selectedTime;
    private Button selectedChiNhanh;
    private Button selectedDichVu;
    private Button selectedBarber;
    private GridLayout gridLayoutGio;
    private CustomerApi customerApi;
    private int idCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("token", null);

        // Kiểm tra token và gọi API nếu tồn tại
        if (storedToken != null) {
            customerApi = RetrofitClient.getClient().create(CustomerApi.class);

            // Gọi API GetIdCustomer
            Call<Customer> call = customerApi.getIdCustomer("Bearer " + storedToken);
            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.isSuccessful()) {
                        Customer customerResponse = response.body();
                        idCustomer = customerResponse.getId();
                        Log.d("Customer", "Id Khách Hàng là: " + idCustomer);
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    // Xử lý khi có lỗi kết nối mạng hoặc lỗi từ API
                }
            });
        }
        Button buttonDatLich = findViewById(R.id.buttonDatLich);
        buttonDatLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingApi = RetrofitClient.getClient().create(BookingApi.class);
                Log.d("DateTimeValues", "Selected Ngay: " + selectedNgay);
                Log.d("DateTimeValues", "Selected Gio: " + selectedGio);
                String dateTimeString = selectedNgay + " " + selectedGio;
                Log.d("DateTimeValues", "DateTimeString: " + dateTimeString);

                // Tạo một đối tượng Calendar với ngày và giờ đã chọn
                Calendar selectedDateTime = Calendar.getInstance();
                SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

                try {
                    Date dateTime = sdfDateTime.parse(selectedNgay + " " + selectedGio);
                    selectedDateTime.setTime(dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
                SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
                String thoiGianBatDau = isoDateFormat.format(selectedDateTime.getTime());

                Log.d("DateTimeValues", "ThoiGianBatDau: " + thoiGianBatDau);

                // Tạo đối tượng BookingRequest từ dữ liệu người dùng
                BookingRequest bookingRequest = new BookingRequest(idCustomer, idChiNhanh, idBarber, idDichVu, thoiGianBatDau);

                // Gọi API đặt lịch
                Call<Void> call = bookingApi.datLich(idCustomer, idChiNhanh, idBarber, idDichVu, bookingRequest);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Xử lý khi đặt lịch thành công
                            Toast.makeText(BookingActivity.this, "Đặt lịch thành công", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(BookingActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Xử lý khi có lỗi response từ API
                            Toast.makeText(BookingActivity.this, "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
                            // Log lỗi ra Logcat
                            Log.e("BookingActivity", "Đặt lịch thất bại. Mã lỗi: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Xử lý khi có lỗi kết nối mạng hoặc lỗi từ API
                        Toast.makeText(BookingActivity.this, "Đặt lịch thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        // Log lỗi ra Logcat
                        Log.e("BookingActivity", "Đặt lịch thất bại: " + t.getMessage(), t);
                    }
                });
            }
        });

        spinnerNgay = findViewById(R.id.spinnerNgay);
        gridLayoutGio = findViewById(R.id.gridLayoutGio);

        loadNgay();

        // Initialize views
        spinnerViTri = findViewById(R.id.spinnerViTri);
        spinnerDanhMuc = findViewById(R.id.spinnerDanhMuc);
        logTextView = findViewById(R.id.logTextView);

        ViTriApi viTriApi = RetrofitClient.getClient().create(ViTriApi.class);
        Call<List<ViTri>> callViTri = viTriApi.getListViTri();
        callViTri.enqueue(new Callback<List<ViTri>>() {
            @Override
            public void onResponse(Call<List<ViTri>> call, Response<List<ViTri>> response) {
                if (response.isSuccessful()) {
                    List<ViTri> viTriList = response.body();
                    // Update the ArrayAdapter in the onCreate method
                    ArrayAdapter<ViTri> adapter = new ArrayAdapter<ViTri>(BookingActivity.this, android.R.layout.simple_spinner_item, viTriList) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(android.R.id.text1);
                            textView.setText(getItem(position).getTinhThanhPho());
                            return view;
                        }

                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View dropDownView = super.getDropDownView(position, convertView, parent);
                            TextView textView = (TextView) dropDownView.findViewById(android.R.id.text1);
                            textView.setText(getItem(position).getTinhThanhPho());
                            return dropDownView;
                        }
                    };

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerViTri.setAdapter(adapter);
                    spinnerViTri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            ViTri selectedViTri = (ViTri) parentView.getSelectedItem();
                            loadChiNhanhList(selectedViTri.getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // Do nothing here
                        }
                    });
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<ViTri>> call, Throwable t) {
                // Handle failure
            }
        });

        ////////////////////
        DanhMucApi danhMucApi = RetrofitClient.getClient().create(DanhMucApi.class);
        Call<List<DanhMuc>> callDanhMuc = danhMucApi.getListDanhMuc();
        callDanhMuc.enqueue(new Callback<List<DanhMuc>>() {
            @Override
            public void onResponse(Call<List<DanhMuc>> call, Response<List<DanhMuc>> response) {
                if (response.isSuccessful()) {
                    List<DanhMuc> danhMucList = response.body();

                    // Create a custom ArrayAdapter for the spinner
                    ArrayAdapter<DanhMuc> danhMucAdapter = new ArrayAdapter<DanhMuc>(BookingActivity.this, android.R.layout.simple_spinner_item, danhMucList) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(android.R.id.text1);
                            textView.setText(getItem(position).getTenDanhMuc());
                            return view;
                        }

                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View dropDownView = super.getDropDownView(position, convertView, parent);
                            TextView textView = (TextView) dropDownView.findViewById(android.R.id.text1);
                            textView.setText(getItem(position).getTenDanhMuc());
                            return dropDownView;
                        }
                    };

                    danhMucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDanhMuc.setAdapter(danhMucAdapter);

                    // Handle spinner item selection
                    spinnerDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            // Handle the selected danh muc
                            DanhMuc selectedDanhMuc = (DanhMuc) parentView.getSelectedItem();
                            loadDichVuList(selectedDanhMuc.getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // Do nothing here
                        }
                    });
                } else {
                    // Handle error
                    Toast.makeText(BookingActivity.this, "Error loading danh muc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DanhMuc>> call, Throwable t) {
                // Handle failure
                Toast.makeText(BookingActivity.this, "Failed to load danh muc", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(BookingActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
    private void loadNgay() {
        // Xóa các lựa chọn ngày hiện tại
        spinnerNgay.setAdapter(null);

        List<String> dateOptions = new ArrayList<>();

        // Populate the Spinner with date options (modify this logic as needed)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            dateOptions.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Tạo ArrayAdapter và cài đặt cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dateOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNgay.setAdapter(adapter);

        // Gán sự kiện chọn ngày
        spinnerNgay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedNgay = spinnerNgay.getSelectedItem().toString();
                loadGio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here for now
            }
        });
    }

    // Phương thức để load giờ dựa vào ngày đã chọn
    private void loadGio() {
        // Clear existing buttons in gridLayoutGio
        gridLayoutGio.removeAllViews();

        // Get selected date from Spinner
        String selectedDateStr = spinnerNgay.getSelectedItem().toString();
        SimpleDateFormat sdfSpinner = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar selectedDate = Calendar.getInstance();
        try {
            selectedDate.setTime(sdfSpinner.parse(selectedDateStr));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        for (int hour = 8; hour <= 22; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                // Tạo một đối tượng Calendar mới cho mỗi button
                Calendar buttonCalendar = Calendar.getInstance();
                buttonCalendar.setTime(selectedDate.getTime());

                // Set giờ và phút cho đối tượng Calendar mới
                buttonCalendar.set(Calendar.HOUR_OF_DAY, hour);
                buttonCalendar.set(Calendar.MINUTE, minute);

                boolean isPastTime = buttonCalendar.before(currentTime);
                boolean isToday = buttonCalendar.get(Calendar.DAY_OF_YEAR) == currentTime.get(Calendar.DAY_OF_YEAR);
                boolean isDisabled = isToday && isPastTime;

                Button button = new Button(this);
                button.setText(sdf.format(buttonCalendar.getTime()));
                button.setLayoutParams(new GridLayout.LayoutParams());
                button.setBackgroundResource(R.drawable.button_background);
                button.setEnabled(!isDisabled);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle button click event
                        selectedGio = sdf.format(buttonCalendar.getTime());
                        Log.d("ButtonClicked", "Selected Gio: " + selectedGio);
                        if (selectedTime != null) {
                            selectedTime.setBackgroundResource(R.drawable.button_background);
                        }

                        // Đặt màu nền mới cho button được chọn
                        button.setBackgroundResource(R.drawable.selected_button_background);

                        // Lưu trữ button mới được chọn
                        selectedTime = button;
                    }
                });

                // Add the button to the GridLayout
                gridLayoutGio.addView(button);
            }
        }
    }
    private void loadDichVuList(int idDanhMuc) {
        // Gọi API để lấy danh sách dịch vụ theo idDanhMuc và hiển thị danh sách dịch vụ
        DichVuApi dichVuApi = RetrofitClient.getClient().create(DichVuApi.class);
        Call<List<DichVu>> callDichVu = dichVuApi.getDichVuByDanhMuc(idDanhMuc);
        callDichVu.enqueue(new Callback<List<DichVu>>() {
            @Override
            public void onResponse(Call<List<DichVu>> call, Response<List<DichVu>> response) {
                if (response.isSuccessful()) {
                    List<DichVu> dichVuList = response.body();
                    LinearLayout linearLayoutDichVu = findViewById(R.id.linearLayoutDichVu);
                    linearLayoutDichVu.removeAllViews();
                    for (DichVu dichVu : dichVuList) {
                        Button button = new Button(BookingActivity.this);
                        button.setText(dichVu.getTenDichVu() + "\n" + dichVu.getMoTa() + "\nGia: " + dichVu.getGia());
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String logMessage = "Selected DichVu - ID: " + dichVu.getId() + ", TenDichVu: " + dichVu.getTenDichVu() + ", MoTa: " + dichVu.getMoTa() + ", Gia: " + dichVu.getGia();
                                Log.v("DichVu", logMessage);
                                // Lưu trữ giá trị idDichVu
                                idDichVu = dichVu.getId();
                                if (selectedDichVu != null) {
                                    selectedDichVu.setBackgroundResource(R.drawable.button_background);
                                }

                                // Đặt màu nền mới cho button được chọn
                                button.setBackgroundResource(R.drawable.selected_button_background);

                                // Lưu trữ button mới được chọn
                                selectedDichVu = button;
                            }
                        });

                        linearLayoutDichVu.addView(button);
                    }
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<DichVu>> call, Throwable t) {
                // Handle failure
            }
        });
    }
    // Update loadChiNhanhList method
    private void loadChiNhanhList(int idViTri) {
        // Gọi API để lấy danh sách chi nhánh theo idViTri
        ChiNhanhApi chiNhanhApi = RetrofitClient.getClient().create(ChiNhanhApi.class);
        Call<List<ChiNhanh>> callChiNhanh = chiNhanhApi.getChiNhanhByViTri(idViTri);
        callChiNhanh.enqueue(new Callback<List<ChiNhanh>>() {
            @Override
            public void onResponse(Call<List<ChiNhanh>> call, Response<List<ChiNhanh>> response) {
                if (response.isSuccessful()) {
                    List<ChiNhanh> chiNhanhList = response.body();
                    LinearLayout linearLayoutChiNhanh = findViewById(R.id.linearLayoutChiNhanh);

                    // Remove existing buttons
                    linearLayoutChiNhanh.removeAllViews();

                    for (ChiNhanh chiNhanh : chiNhanhList) {
                        Button button = new Button(BookingActivity.this);
                        button.setText(chiNhanh.getTenChiNhanh() + "\n" + chiNhanh.getDiaChi());
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String logMessage = "Selected ChiNhanh - ID: " + chiNhanh.getId() + ", TenChiNhanh: " + chiNhanh.getTenChiNhanh() + ", DiaChi: " + chiNhanh.getDiaChi();
                                Log.v("ChiNhanh", logMessage);
                                // Lưu trữ giá trị idChiNhanh và load danh sách Barber
                                idChiNhanh = chiNhanh.getId();
                                loadBarberList(idChiNhanh);
                                if (selectedChiNhanh != null) {
                                    selectedChiNhanh.setBackgroundResource(R.drawable.button_background);
                                }

                                // Đặt màu nền mới cho button được chọn
                                button.setBackgroundResource(R.drawable.selected_button_background);

                                // Lưu trữ button mới được chọn
                                selectedChiNhanh = button;
                            }
                        });

                        linearLayoutChiNhanh.addView(button);
                    }
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<ChiNhanh>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void loadBarberList(int chiNhanhId) {
        // Gọi API để lấy danh sách Barber theo chi nhánh
        BarberApi barberApi = RetrofitClient.getClient().create(BarberApi.class);
        Call<List<Barber>> callBarber = barberApi.getBarberByChiNhanh(chiNhanhId);
        callBarber.enqueue(new Callback<List<Barber>>() {
            @Override
            public void onResponse(Call<List<Barber>> call, Response<List<Barber>> response) {
                if (response.isSuccessful()) {
                    List<Barber> barberList = response.body();
                    LinearLayout linearLayoutBarber = findViewById(R.id.linearLayoutBarber);

                    // Remove existing buttons
                    linearLayoutBarber.removeAllViews();

                    for (Barber barber : barberList) {
                        Button button = new Button(BookingActivity.this);
                        button.setText(barber.getNameBarber());
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String logMessage = "Selected Barber - ID: " + barber.getId() + ", Name: " + barber.getNameBarber();
                                Log.v("Barber", logMessage);
                                // Lưu trữ giá trị idBarber
                                idBarber = barber.getId();
                                if (selectedBarber != null) {
                                    selectedBarber.setBackgroundResource(R.drawable.button_background);
                                }

                                // Đặt màu nền mới cho button được chọn
                                button.setBackgroundResource(R.drawable.selected_button_background);

                                // Lưu trữ button mới được chọn
                                selectedBarber = button;
                            }
                        });

                        linearLayoutBarber.addView(button);
                    }
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<Barber>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Xử lý khi nút quay lại được nhấn
        super.onBackPressed();
    }
}
