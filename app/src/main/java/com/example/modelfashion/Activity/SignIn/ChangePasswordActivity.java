package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.modelfashion.R;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtNewPassword, edtConfirmPassword;
    Button btnChangePassword;
    ImageView imgBackToGetCapcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        viewHolder();
        setListener();
    }

    private  void viewHolder() {
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        imgBackToGetCapcha = findViewById(R.id.imgBackToGetCapcha);
    }
    private void setListener() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgBackToGetCapcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private Boolean validate() {
        if (edtNewPassword.getText().toString().isEmpty() || edtConfirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(ChangePasswordActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!edtConfirmPassword.getText().toString().equalsIgnoreCase(edtNewPassword.getText().toString())) {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}