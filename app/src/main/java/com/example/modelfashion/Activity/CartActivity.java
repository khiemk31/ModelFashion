package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.modelfashion.Fragment.CartFragment;
import com.example.modelfashion.Fragment.CategoryFragment;
import com.example.modelfashion.R;

public class CartActivity extends AppCompatActivity {
    private Bundle info;
    private ImageView img_back_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
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


}