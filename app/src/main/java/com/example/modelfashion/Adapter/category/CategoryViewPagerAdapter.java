package com.example.modelfashion.Adapter.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.modelfashion.Fragment.category.CategoryMenFragment;
import com.example.modelfashion.Fragment.category.CategoryWomenFragment;

public class CategoryViewPagerAdapter extends FragmentStateAdapter {

    public CategoryViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public CategoryViewPagerAdapter(FragmentManager supportFragmentManager, Lifecycle lifecycle) {
        super(supportFragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new CategoryMenFragment();
        } else if (position == 1) {
            return new CategoryWomenFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
