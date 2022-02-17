package com.example.modelfashion.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.modelfashion.Fragment.MenPageFragment;
import com.example.modelfashion.Fragment.WomenPageFragment;

import java.util.ArrayList;

public class ViewPagerMainFmAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> arrFragment = new ArrayList<>();
    MenPageFragment menPageFragment = new MenPageFragment();
    WomenPageFragment womenPageFragment = new WomenPageFragment();
    public ViewPagerMainFmAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        arrFragment.add(menPageFragment);
        arrFragment.add(womenPageFragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arrFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return arrFragment.size();
    }
}
