package com.example.modelfashion.Activity.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignInActivity extends AppCompatActivity {

    ImageView btn_back;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView tvSignUp;
    CheckBox cbSaveValue;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        viewHolder();
        setListener();
        getPreferencesData();
    }

    // tham chiếu
    private void viewHolder() {
        btn_back = findViewById(R.id.btn_signIn_back);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        cbSaveValue = findViewById(R.id.cbSaveValue);
        sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
    }

    private Boolean validate() {
        Pattern special = Pattern.compile("[!#$%&*^()_+=|<>?{}\\[\\]~-]");
        if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
            Toast.makeText(SignInActivity.this, "Không để trống email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
            Toast.makeText(SignInActivity.this, "Sai định dạng email", Toast.LENGTH_SHORT).show();
            return  false;
        }

        if (special.matcher(edtEmail.getText().toString()).find() || special.matcher(edtPassword.getText().toString()).find()){
            Toast.makeText(SignInActivity.this, "Không được viết kí tự đặc biệt", Toast.LENGTH_SHORT).show();
            return  false;
        }

        if (edtPassword.getText().toString().length() < 8){
            Toast.makeText(SignInActivity.this, "Mật khẩu ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }

    //thêm chức năng vào các nút bấm
    private void setListener() {
        btn_back.setOnClickListener(v -> {
            onBackPressed();
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    if (cbSaveValue.isChecked()){
                        Boolean aBoolean = cbSaveValue.isChecked();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", edtEmail.getText().toString());
                        editor.putString("password", edtPassword.getText().toString());
                        editor.putBoolean("checkbox", aBoolean);
                        editor.apply();
                    }else {
                        sharedPreferences.edit().clear().apply();
                    }
                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    //chặn back
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // optional depending on your needs
    }

    public void getPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        if (sharedPreferences.contains("email")) {
            String user = sharedPreferences.getString("email", "not found.");
            edtEmail.setText(user);
        }
        if (sharedPreferences.contains("password")) {
            String pass = sharedPreferences.getString("password", "not found.");
            edtPassword.setText(pass);
        }
        if (sharedPreferences.contains("checkbox")) {
            Boolean check = sharedPreferences.getBoolean("checkbox", false);
            cbSaveValue.setChecked(check);
        }
    }
}