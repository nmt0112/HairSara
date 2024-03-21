package com.example.hairsara.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hairsara.Models.XacNhanAdapter;
import com.example.hairsara.R;
import com.example.hairsara.databinding.FragmentNotificationsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NotificationsFragment extends Fragment {

    public FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TabLayout tabLayout = root.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = root.findViewById(R.id.viewPager2);
        // Tạo adapter và thiết lập cho ViewPager2
        XacNhanAdapter adapter = new XacNhanAdapter(requireActivity());
        viewPager2.setAdapter(adapter);

        // Kết nối ViewPager2 với TabLayout
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            // Thiết lập tiêu đề cho từng tab
            switch (position) {
                case 0:
                    tab.setText("Chưa Xác nhận");
                    break;
                case 1:
                    tab.setText("Đã Xác nhận");
                    break;
                // thêm các trường hợp khác nếu cần
            }
        }).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}