package com.example.modelfashion.Activity.SignIn;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Fragment.FragmentProfile;
import com.example.modelfashion.Model.response.User.User;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.network.ApiClient;
import com.example.modelfashion.network.ApiInterface;
import com.google.gson.Gson;

import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ImageView btn_back;
    EditText edtAccount, edtPassword;
    Button btnLogin;
    TextView tvSignUp, tvForgotPassword;
    CheckBox cbSaveValue;
    SharedPreferences sharedPreferences;
    ApiInterface apiInterface;
    ProgressLoadingCommon progressLoadingCommon;

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
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        cbSaveValue = findViewById(R.id.cbSaveValue);
        sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        apiInterface = ApiClient.provideApiInterface(SignInActivity.this);
        progressLoadingCommon = new ProgressLoadingCommon();
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
    }

    private Boolean validate() {
        Pattern special = Pattern.compile("[!#$%&*^()_+=|<>?{}\\[\\]~-]");
        if (edtAccount.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
            Toast.makeText(SignInActivity.this, "Không để trống email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (special.matcher(edtAccount.getText().toString()).find() || special.matcher(edtPassword.getText().toString()).find()) {
            Toast.makeText(SignInActivity.this, "Không được viết kí tự đặc biệt", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtPassword.getText().toString().length() < 8) {
            Toast.makeText(SignInActivity.this, "Mật khẩu ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
            return false;
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
                    progressLoadingCommon.showProgressLoading(SignInActivity.this);
                    checkLogin();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void checkLogin() {
        apiInterface.checkLogin(edtAccount.getText().toString(), edtPassword.getText().toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200) {
                            if (cbSaveValue.isChecked()) {
                                Boolean aBoolean = cbSaveValue.isChecked();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("account", edtAccount.getText().toString());
                                editor.putString("password", edtPassword.getText().toString());
                                editor.putBoolean("checkbox", aBoolean);
                                editor.apply();
                            } else {
                                sharedPreferences.edit().clear().apply();
                            }

                            SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SAVE_USER,MODE_MULTI_PROCESS);
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                            prefsEditor.putString(Constants.KEY_GET_USER, response.body().toString());
                            prefsEditor.putBoolean(Constants.KEY_CHECK_LOGIN, true);
                            prefsEditor.apply();

                            Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                            onBackPressed();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignInActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("DangNhap",t.toString());
                        Toast.makeText(SignInActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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
        if (sharedPreferences.contains("account")) {
            String user = sharedPreferences.getString("account", "not found.");
            edtAccount.setText(user);
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

//    public void DangKy(View view) {
//        Intent intent = new Intent(SignInActivity.this, SignUpTestAct.class);
//        startActivity(intent);
//    }

//    public void DangNhap(View view) {
//        tk = edt_tk.getText().toString();
//        mk = edt_mk.getText().toString();
//        ApiRetrofit.apiRetrofit.GetUser(tk,mk).enqueue(new Callback<ArrayList<User>>() {
//            @Override
//            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
//                ArrayList<User> arrUser = response.body();
//                Toast.makeText(SignInActivity.this, response.body()+"", Toast.LENGTH_LONG).show();
//                if(arrUser.size()>0){
//                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
//                    Glide.with(SignInActivity.this).load(arrUser.get(0).getAvatar()).into(user_avatar);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
//
//            }
//        });
//    }
}