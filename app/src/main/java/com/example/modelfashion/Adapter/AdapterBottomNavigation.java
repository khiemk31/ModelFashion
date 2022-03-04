package com.example.modelfashion.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.modelfashion.Fragment.CartFragment;
import com.example.modelfashion.Fragment.CategoryFragment;
import com.example.modelfashion.Fragment.FragmentProfile;
import com.example.modelfashion.Fragment.MainFragment;

public class AdapterBottomNavigation extends FragmentStatePagerAdapter {


    public AdapterBottomNavigation(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new FragmentProfile();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
