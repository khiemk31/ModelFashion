package com.example.modelfashion.Activity.SignIn;

import static com.example.modelfashion.Utility.Constants.KEY_ACTIVE;
import static com.example.modelfashion.Utility.Constants.KEY_ADDRESS;
import static com.example.modelfashion.Utility.Constants.KEY_AVARTAR;
import static com.example.modelfashion.Utility.Constants.KEY_BIRTHDAY;
import static com.example.modelfashion.Utility.Constants.KEY_CHECK_BOX;
import static com.example.modelfashion.Utility.Constants.KEY_FULL_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_ID;
import static com.example.modelfashion.Utility.Constants.KEY_MAT_KHAU;
import static com.example.modelfashion.Utility.Constants.KEY_PHONE;
import static com.example.modelfashion.Utility.Constants.KEY_SEX;
import static com.example.modelfashion.Utility.Constants.KEY_TAI_KHOAN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Activity.ProfileActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Model.response.Login.LoginRequest;
import com.example.modelfashion.Model.response.Login.LoginResponse;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.network.ApiClient;
import com.example.modelfashion.network.ApiInterface;
import com.example.modelfashion.network.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.regex.Pattern;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity {

    ImageView btn_back;
    EditText edtAccount, edtPassword;
    Button btnLogin;
    TextView tvSignUp, tvForgotPassword;
    CheckBox cbSaveValue;
    PreferenceManager sharedPreferences;
    ApiInterface apiInterface;
    ProgressLoadingCommon progressLoadingCommon;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedPreferences = new PreferenceManager(this);
        viewHolder();
        setListener();
        layThongTinDangNhap();
    }

    // tham chiếu
    private void viewHolder() {
        btn_back = findViewById(R.id.btn_signIn_back);
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        cbSaveValue = findViewById(R.id.cbSaveValue);
        apiInterface = ApiClient.provideApiInterface(SignInActivity.this);
        progressLoadingCommon = new ProgressLoadingCommon();
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
    }

    private Boolean validate() {
        Pattern special = Pattern.compile("[!#$%&*^()_+=|<>?{}\\[\\]~-]");
        if (edtAccount.getText().toString().trim().isEmpty() || edtPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(SignInActivity.this, "Không để trống số điện thoại hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (special.matcher(edtAccount.getText().toString().trim()).find() || special.matcher(edtPassword.getText().toString().trim()).find()) {
            Toast.makeText(SignInActivity.this, "Không được viết kí tự đặc biệt", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtPassword.getText().toString().trim().length() < 6) {
            Toast.makeText(SignInActivity.this, "Mật khẩu ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtAccount.getText().toString().trim().length() > 10 || edtAccount.getText().toString().trim().length() < 10) {
            Toast.makeText(SignInActivity.this, "Số điện thoại có 10 kí tự!", Toast.LENGTH_SHORT).show();
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
                    login();
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

    private void login() {
        Repository repository = new Repository(this);
        disposable.add(repository.login(new LoginRequest(edtAccount.getText().toString().trim(),
                edtPassword.getText().toString().trim()))
                .doOnSubscribe(disposable -> {
                    // hien loading
                }).subscribe(loginResponse -> {
                    if (cbSaveValue.isChecked()) {
                        luuThongTinDangNhap();
                    } else {
                        sharedPreferences.clear();
                    }

                    sharedPreferences.putString(KEY_ID, loginResponse.getData());
                    sharedPreferences.putBoolean(Constants.KEY_CHECK_LOGIN, true);
                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    getUserDetail(loginResponse.getData());
                }, throwable -> {
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
                }));
    }

    private void luuThongTinDangNhap() {
        sharedPreferences.putString(KEY_TAI_KHOAN, edtAccount.getText().toString().trim());
        sharedPreferences.putString(KEY_MAT_KHAU, edtPassword.getText().toString().trim());
        sharedPreferences.putBoolean(KEY_CHECK_BOX, cbSaveValue.isChecked());
    }

    private void layThongTinDangNhap() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCE_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains(Constants.KEY_TAI_KHOAN)) {
            String user = sharedPreferences.getString(Constants.KEY_TAI_KHOAN, "not found.");
            edtAccount.setText(user);
        }
        if (sharedPreferences.contains(Constants.KEY_MAT_KHAU)) {
            String pass = sharedPreferences.getString(Constants.KEY_MAT_KHAU, "not found.");
            edtPassword.setText(pass);
        }
        if (sharedPreferences.contains(Constants.KEY_CHECK_BOX)) {
            Boolean check = sharedPreferences.getBoolean(Constants.KEY_CHECK_BOX, false);
            cbSaveValue.setChecked(check);
        }
    }

    private void getUserDetail(String userId) {
        Repository repository = new Repository(this);
        disposable.add(repository.getUserDetail(userId)
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(userDetailResponse -> {
                    sharedPreferences.putString(KEY_ID, userDetailResponse.getData().getUserId());
                    sharedPreferences.putString(KEY_AVARTAR, userDetailResponse.getData().getAvatar());
                    sharedPreferences.putString(KEY_FULL_NAME, userDetailResponse.getData().getUsername());
                    sharedPreferences.putInt(KEY_ACTIVE, userDetailResponse.getData().getActive());
                    sharedPreferences.putString(KEY_PHONE, userDetailResponse.getData().getPhone());
                    sharedPreferences.putString(KEY_BIRTHDAY, userDetailResponse.getData().getDateOfBirth());
                    sharedPreferences.putString(KEY_ADDRESS, userDetailResponse.getData().getAddress());
                    sharedPreferences.putInt(KEY_SEX, userDetailResponse.getData().getGender());

                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                }, throwable -> {
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
                }));
    }

    //chặn back
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // optional depending on your needs
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}