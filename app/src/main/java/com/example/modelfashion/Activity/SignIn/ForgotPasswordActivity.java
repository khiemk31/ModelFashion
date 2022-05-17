package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.network.Repository;

import java.util.Random;

import io.reactivex.disposables.CompositeDisposable;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtPhone;
    TextView  tvBacktoLogin;
    ImageView imgBack;
    Button btnGetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        viewHolder();
        setListener();
    }

    private void viewHolder() {
        edtPhone = findViewById(R.id.edtPhone);
        tvBacktoLogin = findViewById(R.id.tvBackToLogin);
        btnGetPassword = findViewById(R.id.btnGetPassword);
        imgBack = findViewById(R.id.btnBack);
    }

    private void setListener() {
        btnGetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, EnterCapchaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneForgotPass", edtPhone.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        tvBacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private Boolean validate() {
        if (edtPhone.getText().toString().isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtPhone.getText().toString().length() > 10) {
            Toast.makeText(ForgotPasswordActivity.this, "Sai định dạng số điện thoại", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}