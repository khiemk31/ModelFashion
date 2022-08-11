package com.example.modelfashion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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
        getFcmToken();

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

    void getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        PreferenceManager sharedPreferences = new PreferenceManager(SplashActivity.this);
                        sharedPreferences.putString(Constants.KEY_FCM_TOKEN, token);
                        Log.d("FCMToken", token);
                    }
                });
    }
}