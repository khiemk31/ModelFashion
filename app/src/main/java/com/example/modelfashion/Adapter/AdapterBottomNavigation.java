package com.example.modelfashion.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.modelfashion.Fragment.BlankFragment;
import com.example.modelfashion.Fragment.BlankFragment2;
import com.example.modelfashion.Fragment.BlankFragment3;
import com.example.modelfashion.Fragment.BlankFragment4;

public class AdapterBottomNavigation extends FragmentStatePagerAdapter {


    public AdapterBottomNavigation(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new BlankFragment();
            case 1:
                return new BlankFragment2();
            case 2:
                return new BlankFragment3();
            case 3:
                return new BlankFragment4();
            default:return new BlankFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
