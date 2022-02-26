package com.example.modelfashion.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modelfashion.Adapter.ViewPagerMainFmAdapter;
import com.example.modelfashion.Adapter.VpSaleMainFmAdapter;
import com.example.modelfashion.Class.ItemSaleMain;
import com.example.modelfashion.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class MainFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 vpMain, vpSaleMain;
    private CircleIndicator3 ciSale;
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
        ArrayList<ItemSaleMain> arrItem = new ArrayList<>();
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        VpSaleMainFmAdapter vpSaleMainFmAdapter = new VpSaleMainFmAdapter(arrItem);
        vpSaleMain.setAdapter(vpSaleMainFmAdapter);

        ViewPagerMainFmAdapter viewPagerMainFmAdapter = new ViewPagerMainFmAdapter(getActivity());
        vpMain.setAdapter(viewPagerMainFmAdapter);
        ciSale.setViewPager(vpSaleMain);
        new TabLayoutMediator(tabLayout, vpMain, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Men");
                        break;
                    case 1:
                        tab.setText("Women");
                        break;
                }
            }
        }).attach();
        return view;
    }
}