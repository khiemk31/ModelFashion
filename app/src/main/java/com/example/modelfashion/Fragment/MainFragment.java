package com.example.modelfashion.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modelfashion.Adapter.ViewPagerMainFmAdapter;
import com.example.modelfashion.Adapter.VpSaleMainFmAdapter;
import com.example.modelfashion.Model.ItemSaleMain;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.InnerTabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class MainFragment extends Fragment {
    private InnerTabLayout tabLayout;
    private ViewPager2 vpMain, vpSaleMain;
    private CircleIndicator3 ciSale;

    private List<CategoryFragment.TabFragment> fragmentList = new ArrayList<>();
    ArrayList<ItemSaleMain> arrItem = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = vpSaleMain.getCurrentItem();
            if (currentPosition == arrItem.size() - 1) {
                vpSaleMain.setCurrentItem(0);
            } else
                vpSaleMain.setCurrentItem(currentPosition + 1);
        }
    };

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tabLayout = view.findViewById(R.id.tablayout_main_fm);
        vpMain = view.findViewById(R.id.vp_main_fm);
        vpSaleMain = view.findViewById(R.id.vp_sale_main_fm);
        ciSale = view.findViewById(R.id.ci_sale_main_fm);

        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        VpSaleMainFmAdapter vpSaleMainFmAdapter = new VpSaleMainFmAdapter(arrItem);
        vpSaleMain.setAdapter(vpSaleMainFmAdapter);

        ViewPagerMainFmAdapter viewPagerMainFmAdapter = new ViewPagerMainFmAdapter(getActivity());
        vpMain.setAdapter(viewPagerMainFmAdapter);
        ciSale.setViewPager(vpSaleMain);

        fragmentList.add(new CategoryFragment.TabFragment(new MenPageFragment(), "Men"));
        fragmentList.add(new CategoryFragment.TabFragment(new WomenPageFragment(), "Women"));
        new TabLayoutMediator(tabLayout, vpMain, false, true, (tab, position) -> {
            tabLayout.setupTabLayout(tab, fragmentList.get(position));
        }).attach();


        vpMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        vpSaleMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunable);
                mHandler.postDelayed(mRunable, 2000);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunable);
        mRunable = null;
        mHandler = null;
    }
}