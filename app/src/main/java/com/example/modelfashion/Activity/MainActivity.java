package com.example.modelfashion.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;


import com.cloudinary.android.MediaManager;
import com.example.modelfashion.Fragment.CartFragment;
import com.example.modelfashion.Fragment.CategoryFragment;
import com.example.modelfashion.Fragment.FragmentProfile;
import com.example.modelfashion.Fragment.MainFragment;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.KeyboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class MainActivity extends AppCompatActivity {
    String user_id;
    Boolean isLogin;
    Bundle info;
    public static BottomNavigationView navigationView;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = new Bundle();
        getUserData();
        info.putString("user_id", user_id);
        initConfig();
        //      replaceFragment(new MainFragment());
       navigationView=findViewById(R.id.bottom_navigation_view_linear);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_item_home:
                       viewPager.setCurrentItem(0);
                        break;
                    case R.id.main_item_cart:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.main_item_category:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.main_item_profile:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
        viewPager = findViewById(R.id.viewpager_main);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().getItem(0).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().getItem(1).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().getItem(3).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        KeyboardUtils.addKeyboardToggleListener(this, isVisible -> {
            if (!isVisible) {
                showBottomNavigation();
            } else {
                hideBottomNavigation();
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position) {

                case 0: return MainFragment.newInstance("FirstFragment, Instance 1");
                case 1: return CategoryFragment.newInstance("SecondFragment, Instance 2");
                case 2: return CartFragment.newInstance("ThirdFragment, Instance 3");
                case 3: return FragmentProfile.newInstance("FourthFragment, Instance 4");

                default: return FragmentProfile.newInstance("FourthFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    private void getUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SAVE_USER, Context.MODE_MULTI_PROCESS);
        isLogin = sharedPreferences.getBoolean(Constants.KEY_CHECK_LOGIN, true);
        if (isLogin == false) {
//            User user = new User("", "", "", "", "", "", "");
//            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
//            prefsEditor.putString("user", user.toString());
//            prefsEditor.apply();
            user_id = "null";
        } else {
            if (sharedPreferences.contains(Constants.KEY_GET_USER)) {
                String userData = sharedPreferences.getString(Constants.KEY_GET_USER, "");
                try {
                    JSONObject obj = new JSONObject(userData);
                    user_id = obj.getString(Constants.KEY_ID);
                    Log.d("My App", obj.toString() + user_id);

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + userData + "\"");
                }
            }
        }
    }

//    private void replaceFragment(Fragment fm){
//        FragmentManager fragmentManager= getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fm.setArguments(info);
//        fragmentTransaction.replace(R.id.frmlayout,fm);
//        fragmentTransaction.commit();
//
//    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void showBottomNavigation() {
        navigationView.setVisibility(View.VISIBLE);
    }

    public void hideBottomNavigation() {
        navigationView.setVisibility(View.GONE);
    }

//    public void moveToFragmentProfile() {
//        FragmentProfile fragmentProfile = new FragmentProfile();
//        replaceFragment(fragmentProfile);
//        navigationView.setSelectedItemId(R.id.main_item_profile);
//    }

//    public void moveToCartFragment() {
//        CartFragment cartFragment = new CartFragment();
//        replaceFragment(cartFragment);
//        navigationView.setSelectedItemId(R.id.main_item_cart);
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // tra Result cua momo
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {


                    if (data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:
                        senDataToActivity();
                        Log.e("check", "Thanh cong");

                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    Log.e("check", "That bai");
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";

                } else if (data.getIntExtra("status", -1) == 2) {

                } else {

                }
            } else {

            }
        } else {
        }
    }

    private void senDataToActivity() {
        Intent intent = new Intent("send_data_to_fragment");
        intent.putExtra("action", "addbill");
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
    }

    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "cde");
        config.put("api_key", "537853312614449");
        config.put("api_secret", "__Rb7zY3SQzgNSdlzh3PLP0Jz8Y");
        MediaManager.init(this, config);
    }
}