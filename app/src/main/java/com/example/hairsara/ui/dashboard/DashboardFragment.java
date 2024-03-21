package com.example.hairsara.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairsara.Adapter.ChiNhanhAdapter;
import com.example.hairsara.Adapter.DichVuAdapter;
import com.example.hairsara.Api.Api;
import com.example.hairsara.R;
import com.example.hairsara.Models.ChiNhanh;
import com.example.hairsara.Models.DichVu;
import com.example.hairsara.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private DichVuAdapter dichVuAdapter;
    private Api apiService,apiService1,apiService2;
    private RecyclerView recyclerView1, recyclerView2;
    private ChiNhanhAdapter chiNhanhAdapter;

    private Retrofit retrofit = RetrofitClient.getClient();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.rcvDichvu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dichVuAdapter = new DichVuAdapter();
        recyclerView.setAdapter(dichVuAdapter);

        recyclerView1 = view.findViewById(R.id.rcv_chinhanh);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        chiNhanhAdapter = new ChiNhanhAdapter();
        recyclerView1.setAdapter(chiNhanhAdapter);

        // Khởi tạo ApiService
        apiService =  retrofit.create(Api.class);
        apiService1 = retrofit.create(Api.class);

        // Gọi API và cập nhật dữ liệu trong Adapter
        loadDataFromApi1();
        loadDataFromApi2();
        return view;
    }

    private void loadDataFromApi1() {
        Call<List<DichVu>> call = apiService.getDichVu();
        call.enqueue(new Callback<List<DichVu>>() {
            @Override
            public void onResponse(Call<List<DichVu>> call, Response<List<DichVu>> response) {
                if (response.isSuccessful()) {
                    // Xử lý phản hồi thành công
                    List<DichVu> dichVuList = response.body();
                    dichVuAdapter.setDichVuList(dichVuList);
                    dichVuAdapter.notifyDataSetChanged(); // Đảm bảo gọi thông báo thay đổi ở đây
                } else {
                    showAlertDialog("API Call Failed", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DichVu>> call, Throwable t) {
                showAlertDialog("API Call Failed", "Error: " + t.getMessage());
            }
        });
    }
    private void loadDataFromApi2() {
        Call<List<ChiNhanh>> call = apiService1.getChiNhanh();
        call.enqueue(new Callback<List<ChiNhanh>>() {
            @Override
            public void onResponse(Call<List<ChiNhanh>> call, Response<List<ChiNhanh>> response) {
                if (response.isSuccessful()) {
                    // Xử lý phản hồi thành công
                    List<ChiNhanh> chiNhanhList = response.body();
                    chiNhanhAdapter.setchiNhanhList(chiNhanhList);
                    chiNhanhAdapter.notifyDataSetChanged();
                } else {
                    showAlertDialog("API Call Failed", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ChiNhanh>> call, Throwable t) {
                showAlertDialog("API Call Failed", "Error: " + t.getMessage());
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}