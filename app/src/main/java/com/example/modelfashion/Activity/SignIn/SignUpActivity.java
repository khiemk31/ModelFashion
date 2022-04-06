package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.R;
import com.example.modelfashion.network.ApiClient;
import com.example.modelfashion.network.ApiInterface;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ImageView imgBack;
    EditText edtAccountSu, edtPassword, edtConfirmPassword;
    Button btnSignUp;
    CheckBox cbRules;
    TextView tvSignIn;
    ApiInterface apiInterface;
    ProgressLoadingCommon progressLoadingCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        viewHolder();
        setListener();
    }

    //tham chiếu
    private void viewHolder() {
        imgBack = findViewById(R.id.imgBack);
        edtAccountSu = findViewById(R.id.edtAccountSu);
        edtPassword = findViewById(R.id.edtPasswordSu);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        cbRules = findViewById(R.id.cbRules);
        tvSignIn = findViewById(R.id.tvSignIn);
        apiInterface = ApiClient.provideApiInterface(SignUpActivity.this);
        progressLoadingCommon = new ProgressLoadingCommon();
    }

    // bắt sự kiện
    private void setListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    progressLoadingCommon.showProgressLoading(SignUpActivity.this);
                    insertUser();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }

    private void insertUser() {
        apiInterface.insertUser(edtAccountSu.getText().toString(), edtPassword.getText().toString())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.code() == 200) {
                            //progressLoadingCommon.hideProgressLoading(SignUpActivity.this);
                            Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Log.e("123", String.valueOf(response));
                            onBackPressed();
                        }else {

                            Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Toast.makeText(SignUpActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // validate
    private Boolean validate() {
        Pattern special = Pattern.compile("[!#$%&*^()_+=|<>?{}\\[\\]~-]");
        if (edtAccountSu.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtConfirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Không để trống", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (special.matcher(edtAccountSu.getText().toString()).find() || special.matcher(edtPassword.getText().toString()).find()) {
            Toast.makeText(SignUpActivity.this, "Không được viết kí tự đặc biệt", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtPassword.getText().toString().length() < 8) {
            Toast.makeText(SignUpActivity.this, "Mật khẩu ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!edtConfirmPassword.getText().toString().equalsIgnoreCase(edtPassword.getText().toString())) {
            Toast.makeText(SignUpActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!cbRules.isChecked()) {
            Toast.makeText(SignUpActivity.this, "Vui lòng đọc và đồng ý với chính sách bảo mật", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //chặn back
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // optional depending on your needs
    }

}