package com.example.modelfashion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.view.MenuItem;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.modelfashion.Fragment.CartFragment;
import com.example.modelfashion.Fragment.CategoryFragment;
import com.example.modelfashion.Fragment.FragmentProfile;
import com.example.modelfashion.Fragment.MainFragment;
import com.example.modelfashion.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String user_id = "1";
    public static BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle info = new Bundle();
        info.putString("user_id",user_id);
        replaceFragment(new MainFragment());

        navigationView=findViewById(R.id.bottom_navigation_view_linear);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_item_home:
                        MainFragment mainFragment = new MainFragment();
                        mainFragment.setArguments(info);
                        replaceFragment(mainFragment);
                        break;
                    case R.id.main_item_cart:
                        CartFragment cartFragment = new CartFragment();
                        cartFragment.setArguments(info);
                        replaceFragment(cartFragment);
                        break;
                    case R.id.main_item_category:
                        CategoryFragment categoryFragment = new CategoryFragment();
                        categoryFragment.setArguments(info);
                        replaceFragment(categoryFragment);
                        break;
                    case R.id.main_item_profile:
                        FragmentProfile fragmentProfile = new FragmentProfile();
                        fragmentProfile.setArguments(info);
                        replaceFragment(fragmentProfile);
                        break;
                }
                return true;
            }
        });


    }
    private void replaceFragment(Fragment fm){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmlayout,fm);
        fragmentTransaction.commit();

    }

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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}