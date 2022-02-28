package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.R;

public class SignInActivity extends AppCompatActivity {

    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        viewHolder();
        setListener();
    }

    // tham chiếu
    private void viewHolder() {
        btn_back=findViewById(R.id.btn_signIn_back);
    }

    //thêm chức năng vào các nút bấm
    private void setListener() {
        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    //chặn back
    @Override
    public void onBackPressed() {
    }
}