package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Activity.SignUpTestAct;
import com.example.modelfashion.Interface.ApiRetrofit;
import com.example.modelfashion.Model.User;
import com.example.modelfashion.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ImageView btn_back, user_avatar;
    EditText edt_tk, edt_mk;
    String tk = "";
    String mk = "";
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
        user_avatar = findViewById(R.id.img_user_avatar);
        edt_tk = findViewById(R.id.edtEmail);
        edt_mk = findViewById(R.id.edtPassword);
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

    public void DangKy(View view) {
        Intent intent = new Intent(SignInActivity.this, SignUpTestAct.class);
        startActivity(intent);
    }

    public void DangNhap(View view) {
        tk = edt_tk.getText().toString();
        mk = edt_mk.getText().toString();
        ApiRetrofit.apiRetrofit.GetUser(tk,mk).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                ArrayList<User> arrUser = response.body();
                Toast.makeText(SignInActivity.this, response.body()+"", Toast.LENGTH_LONG).show();
                if(arrUser.size()>0){
                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    Glide.with(SignInActivity.this).load(arrUser.get(0).getAvatar()).into(user_avatar);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }
}