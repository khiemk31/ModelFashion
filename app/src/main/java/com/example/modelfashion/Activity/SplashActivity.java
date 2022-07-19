package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.example.modelfashion.R;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initConfig();
        goToMain();
    }

    private void goToMain() {
        CountDownTimer countDownTimer = new CountDownTimer(2000L, 1000L) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }.start();
    }

    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "cde");
        config.put("api_key", "537853312614449");
        config.put("api_secret", "__Rb7zY3SQzgNSdlzh3PLP0Jz8Y");
        MediaManager.init(this, config);
    }
}