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

import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.Model.response.Register.VerifyOTPRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.network.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class EnterCapchaActivity extends AppCompatActivity {
    EditText edtCapcha;
    Button btnGoToChangePassword;
    ImageView imgBack;
    TextView tvCountDown, tvResentCapcha;
    String phone, otpToken;
    CompositeDisposable disposable = new CompositeDisposable();
    ProgressLoadingCommon progressLoadingCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_capcha);
        viewHolder();
        setListener();
        getData();
    }

    private void viewHolder() {
        edtCapcha = findViewById(R.id.edtCapchaFromEmail);
        btnGoToChangePassword = findViewById(R.id.btnGoToChangePassword);
        imgBack = findViewById(R.id.imgBackToGetCapcha);
        tvCountDown = findViewById(R.id.tvCountDownTime);
        tvResentCapcha = findViewById(R.id.tvResentCapcha);
        progressLoadingCommon = new ProgressLoadingCommon();
    }

    private void setListener() {
        btnGoToChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                   verifyOTP();
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
                getOTP();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            phone = bundle.getString("phoneForgotPass");
            getOTP();
        }
    }

    private void getOTP() {
        Repository repository = new Repository(this);
        disposable.add(repository.getOTP(new GetOTPRequest(phone))
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(getOTPResponse -> {
                    Toast.makeText(EnterCapchaActivity.this, "Đã gửi mã xác nhận!", Toast.LENGTH_SHORT).show();
                    otpToken = getOTPResponse.getOtpToken();
                    countDownTime();
                }, throwable -> {
                    Toast.makeText(EnterCapchaActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void verifyOTP() {
        Repository repository = new Repository(this);
        disposable.add(repository.verifyOTP(new VerifyOTPRequest(otpToken, edtCapcha.getText().toString()))
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(verifyOTP -> {
                    Toast.makeText(EnterCapchaActivity.this, verifyOTP.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EnterCapchaActivity.this, ChangePasswordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneForgotPass", phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }, throwable -> {
                    Toast.makeText(EnterCapchaActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
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
        CountDownTimer Timer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("Mã xác thực hết hạn trong " + millisUntilFinished / 1000 + " giây");
            }

            public void onFinish() {
                onBackPressed();
            }
        }.start();
    }
}