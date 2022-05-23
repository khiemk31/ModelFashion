package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Model.response.Login.LoginRequest;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.Model.response.Register.RegisterRequest;
import com.example.modelfashion.Model.response.Register.VerifyOTPRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.network.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class OTPPhoneActivity extends AppCompatActivity {

    EditText edtOTP;
    Button btnRegister;
    ImageView imgBack;
    TextView tvCountDown, tvResentOTP;
    CompositeDisposable disposable = new CompositeDisposable();
    ProgressLoadingCommon progressLoadingCommon;
    String otpToken, phone, userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpphone);
        viewHolder();
        setListener();
        getData();
        getOTP();
    }

    private void viewHolder() {
        edtOTP = findViewById(R.id.edtOTP);
        btnRegister = findViewById(R.id.btnDoneRegister);
        imgBack = findViewById(R.id.imgBackToRegisterForm);
        tvCountDown = findViewById(R.id.tvCountDownTimeOTP);
        tvResentOTP = findViewById(R.id.tvResentOTP);
        progressLoadingCommon = new ProgressLoadingCommon();
    }

    private void setListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvResentOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOTP();
                hideResent();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    verifyOTP();
                }
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            phone = bundle.getString("phone");
            userName = bundle.getString("name");
            password = bundle.getString("password");
        }
    }

    private void getOTP() {
        Repository repository = new Repository(this);
        disposable.add(repository.getOTP(new GetOTPRequest(phone))
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(getOTPResponse -> {
                    Toast.makeText(OTPPhoneActivity.this, "Đã gửi mã xác nhận!", Toast.LENGTH_SHORT).show();
                    otpToken = getOTPResponse.getOtpToken();
                    countDownTime();
                }, throwable -> {
                    Toast.makeText(OTPPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void verifyOTP() {
        Repository repository = new Repository(this);
        disposable.add(repository.verifyOTP(new VerifyOTPRequest(otpToken, edtOTP.getText().toString()))
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(verifyOTP -> {
                    register();
                }, throwable -> {
                    Toast.makeText(OTPPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void register() {
        Repository repository = new Repository(this);
        disposable.add(repository.register(new RegisterRequest(phone, userName, password, 1))
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(registerResponse -> {
                    Toast.makeText(OTPPhoneActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OTPPhoneActivity.this, MainActivity.class));
                }, throwable -> {
                    Toast.makeText(OTPPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private Boolean validate() {
        // validate capcha
        if (edtOTP.getText().toString().isEmpty()) {
            Toast.makeText(OTPPhoneActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
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

    private void hideResent() {
        CountDownTimer Timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvResentOTP.setVisibility(View.GONE);
            }

            public void onFinish() {
                tvResentOTP.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
