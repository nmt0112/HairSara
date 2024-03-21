package com.example.hairsara.Models;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hairsara.ui.notifications.FragmentOne;
import com.example.hairsara.ui.notifications.FragmentTwo;

public class XacNhanAdapter extends FragmentStateAdapter {

    public XacNhanAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            default:
                return new FragmentOne();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Adjust the count based on the number of fragments
    }
}



