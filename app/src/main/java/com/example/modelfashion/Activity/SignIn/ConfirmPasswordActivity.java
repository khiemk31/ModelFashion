package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;

import java.util.regex.Pattern;

public class ConfirmPasswordActivity extends AppCompatActivity {
    EditText edtPass;
    Button btnContinue;
    TextView tvForgotPass;
    ImageView btnBack;
    PreferenceManager sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        edtPass = findViewById(R.id.edtCurrentPassword);
        btnContinue = findViewById(R.id.btnContinue);
        tvForgotPass = findViewById(R.id.tvBackForgotPassword);
        btnBack = findViewById(R.id.btnBack);
        sharedPreferences = new PreferenceManager(this);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtPass.getText().toString().trim().isEmpty()) {
                    if (!edtPass.getText().toString().trim().equalsIgnoreCase(sharedPreferences.getString(Constants.KEY_PASS))) {
                        Toast.makeText(ConfirmPasswordActivity.this, "Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(ConfirmPasswordActivity.this, ChangePasswordActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("phoneForgotPass", sharedPreferences.getString(Constants.KEY_USER));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(ConfirmPasswordActivity.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmPasswordActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}