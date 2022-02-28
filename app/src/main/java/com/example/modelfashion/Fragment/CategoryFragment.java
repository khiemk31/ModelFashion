package com.example.modelfashion.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modelfashion.Adapter.category.CategoryViewPagerAdapter;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.SearchBar;
import com.google.android.material.tabs.TabLayout;

public class CategoryFragment extends Fragment {
    CategoryViewPagerAdapter viewPagerAdapter;
    SearchBar searchBar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        searchBar.onSearchBarClick(() -> {
            if (viewPager2.getCurrentItem() == 0){
                // TODO Men search
            }else if (viewPager2.getCurrentItem() == 1){
                // TODO Women search
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void initData() {

    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tab_layout_category);
        viewPager2 = view.findViewById(R.id.view_pager_category);
        searchBar = view.findViewById(R.id.search_bar);
        viewPagerAdapter = new CategoryViewPagerAdapter(getChildFragmentManager(), getLifecycle());

        viewPager2.setAdapter(viewPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Men"));
        tabLayout.addTab(tabLayout.newTab().setText("Women"));
    }
}