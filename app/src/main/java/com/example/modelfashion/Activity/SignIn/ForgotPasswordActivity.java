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

import com.example.modelfashion.R;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtEmail, edtCapcha;
    TextView tvCapcha, tvBacktoLogin;
    ImageView imgRefresh, imgBack;
    Button btnGetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        viewHolder();
        setListener();
        tvCapcha.setText(getRandomCapcha());
    }

    private void viewHolder() {
        edtEmail = findViewById(R.id.edtEmail);
        edtCapcha = findViewById(R.id.edtCapcha);
        tvCapcha = findViewById(R.id.tvCapcha);
        tvBacktoLogin = findViewById(R.id.tvBackToLogin);
        imgRefresh = findViewById(R.id.img_refresh);
        btnGetPassword = findViewById(R.id.btnGetPassword);
        imgBack = findViewById(R.id.btnBack);
    }

    private void setListener() {
        btnGetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    startActivity(new Intent(ForgotPasswordActivity.this, EnterCapchaActivity.class));
                }
            }
        });

        tvBacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCapcha.setText(getRandomCapcha());
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private String getRandomCapcha() {
        Random random = new Random();
        int result = 1000 + random.nextInt(8999);
        return String.valueOf(result);
    }

    private Boolean validate() {
        if (edtEmail.getText().toString().isEmpty() || edtCapcha.getText().toString().isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!edtCapcha.getText().toString().equalsIgnoreCase(tvCapcha.getText().toString())) {
            Toast.makeText(ForgotPasswordActivity.this, "Mã xác thực không chính xác", Toast.LENGTH_SHORT).show();
            tvCapcha.setText(getRandomCapcha());
            return false;
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString()) && !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            Toast.makeText(ForgotPasswordActivity.this, "Sai định dạng email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvCapcha.setText(getRandomCapcha());
    }
}