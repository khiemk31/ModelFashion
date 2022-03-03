package com.example.modelfashion.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modelfashion.Adapter.category.CategoryViewPagerAdapter;
import com.example.modelfashion.Fragment.category.CategoryMenFragment;
import com.example.modelfashion.Fragment.category.CategoryWomenFragment;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.InnerTabLayout;
import com.example.modelfashion.customview.SearchBar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    CategoryViewPagerAdapter viewPagerAdapter;
    SearchBar searchBar;
    InnerTabLayout tabLayout;
    ViewPager2 viewPager2;

    private List<TabFragment> fragmentList = new ArrayList<>();

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
            if (viewPager2.getCurrentItem() == 0) {
                // TODO Men search
            } else if (viewPager2.getCurrentItem() == 1) {
                // TODO Women search
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

        fragmentList.add(new TabFragment(new CategoryMenFragment(), "Men"));
        fragmentList.add(new TabFragment(new CategoryWomenFragment(), "Women"));
        new TabLayoutMediator(tabLayout, viewPager2, false, true, (tab, position) -> {
            tabLayout.setupTabLayout(tab, fragmentList.get(position));
        }).attach();
    }

    public static class TabFragment {
        private Fragment fragment;
        private String title;

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public TabFragment(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }
    }
}