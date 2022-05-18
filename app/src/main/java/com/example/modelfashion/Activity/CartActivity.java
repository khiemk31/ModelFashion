package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Fragment.CartFragment;
import com.example.modelfashion.Fragment.CategoryFragment;
import com.example.modelfashion.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import vn.momo.momo_partner.AppMoMoLib;

public class CartActivity extends AppCompatActivity {
    private Bundle info;
    private ImageView img_back_cart;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        img_back_cart = findViewById(R.id.img_back_cart);
        img_back_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("use_id");
        info = new Bundle();
        info.putString("user_id",user_id);
        CartFragment cartFragment = new CartFragment();
        cartFragment.setArguments(info);
        replaceFragment(cartFragment);

    }

    private void replaceFragment(Fragment fm){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fm.setArguments(info);
        fragmentTransaction.replace(R.id.fl_cart,fm);
        fragmentTransaction.commit();


    }

    // tra Result cua momo
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("check", String.valueOf(requestCode));

        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {


                    if (data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:
                        //check_out();
                       Log.e("check", "Thanh cong");

                    } else {
                        Log.e("check", "that bai");

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





}