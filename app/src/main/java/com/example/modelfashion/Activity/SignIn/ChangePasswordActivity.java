package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Model.response.Login.ForgotPasswordRequest;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.network.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtNewPassword, edtConfirmPassword;
    Button btnChangePassword;
    ImageView imgBackToGetCapcha;
    String phone;
    CompositeDisposable disposable = new CompositeDisposable();
    ProgressLoadingCommon progressLoadingCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        viewHolder();
        setListener();
        getData();
    }

    private void viewHolder() {
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        imgBackToGetCapcha = findViewById(R.id.imgBackToGetCapcha);
        progressLoadingCommon = new ProgressLoadingCommon();
    }

    private void setListener() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    changePassword();
                }
            }
        });

        imgBackToGetCapcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            phone = bundle.getString("phoneForgotPass");
        }
    }

    private void changePassword() {
        Repository repository = new Repository(this);
        disposable.add(repository.changePassword(new ForgotPasswordRequest(phone, edtNewPassword.getText().toString()))
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(response -> {
                    Toast.makeText(ChangePasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePasswordActivity.this, SignInActivity.class));
                }, throwable -> {
                    Toast.makeText(ChangePasswordActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private Boolean validate() {
        if (edtNewPassword.getText().toString().trim().isEmpty() || edtConfirmPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(ChangePasswordActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtNewPassword.getText().toString().trim().length() < 6) {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!edtConfirmPassword.getText().toString().trim().equalsIgnoreCase(edtNewPassword.getText().toString().trim())) {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}