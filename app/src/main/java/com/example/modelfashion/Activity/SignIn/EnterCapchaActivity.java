package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.R;

public class EnterCapchaActivity extends AppCompatActivity {
    EditText edtCapcha;
    Button btnGoToChangePassword;
    ImageView imgBack;
    TextView tvCountDown, tvResentCapcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_capcha);
        viewHolder();
        setListener();
        countDownTime();
    }

    private void viewHolder() {
        edtCapcha = findViewById(R.id.edtCapchaFromEmail);
        btnGoToChangePassword = findViewById(R.id.btnGoToChangePassword);
        imgBack = findViewById(R.id.imgBackToGetCapcha);
        tvCountDown = findViewById(R.id.tvCountDownTime);
        tvResentCapcha = findViewById(R.id.tvResentCapcha);
    }

    private void setListener() {
        btnGoToChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    startActivity(new Intent(EnterCapchaActivity.this, ChangePasswordActivity.class));
                    finish();
                }
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvResentCapcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // resent capcha here
            }
        });
    }

    private Boolean validate() {
        // validate capcha
        if (edtCapcha.getText().toString().isEmpty()) {
            Toast.makeText(EnterCapchaActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void countDownTime() {
        CountDownTimer Timer = new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("Mã xác thực hết hạn trong " + millisUntilFinished / 1000 + " giây");
            }

            public void onFinish() {
                onBackPressed();
            }
        }.start();
    }
}