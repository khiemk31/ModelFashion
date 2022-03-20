package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.R;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    ImageView imgBack;
    EditText edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignUp;
    CheckBox cbRules;
    TextView tvSignIn;

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
        edtEmail = findViewById(R.id.edtEmailSu);
        edtPassword = findViewById(R.id.edtPasswordSu);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        cbRules = findViewById(R.id.cbRules);
        tvSignIn = findViewById(R.id.tvSignIn);
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
                    Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
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

    // validate
    private Boolean validate() {
        Pattern special = Pattern.compile("[!#$%&*^()_+=|<>?{}\\[\\]~-]");
        if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtConfirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Không để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            Toast.makeText(SignUpActivity.this, "Sai định dạng email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (special.matcher(edtEmail.getText().toString()).find() || special.matcher(edtPassword.getText().toString()).find()) {
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