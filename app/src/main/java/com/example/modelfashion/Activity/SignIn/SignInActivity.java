package com.example.modelfashion.Activity.SignIn;

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
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Model.response.Login.LoginRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.network.ApiClient;
import com.example.modelfashion.network.ApiInterface;
import com.example.modelfashion.network.Repository;

import java.util.regex.Pattern;

import io.reactivex.disposables.CompositeDisposable;
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
    PreferenceManager preferenceManager;
    ApiInterface apiInterface;
    ProgressLoadingCommon progressLoadingCommon;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        preferenceManager = new PreferenceManager(getApplicationContext());
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

        if (edtPassword.getText().toString().length() < 6) {
            Toast.makeText(SignInActivity.this, "Mật khẩu ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
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
        Repository repository = new Repository(this);
        disposable.add(repository.login(new LoginRequest(edtAccount.getText().toString(), edtPassword.getText().toString())) // truyen phone va password vao day
                .doOnSubscribe(disposable -> {
                    // hien loading
                }).subscribe(loginResponse -> {
                    if (cbSaveValue.isChecked()) {
                        Log.d("User123", String.valueOf(loginResponse.getData()));
                        Boolean aBoolean = cbSaveValue.isChecked();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.KEY_SAVE_USER_INFO, edtAccount.getText().toString());
                        editor.putString(Constants.KEY_SAVE_PASSWORD_INFO, edtPassword.getText().toString());

                        preferenceManager.putString(Constants.KEY_ID, loginResponse.getData());
                        editor.putBoolean(Constants.KEY_SAVE_CHECK_BOX, aBoolean);
                        editor.apply();

                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SAVE_USER, MODE_MULTI_PROCESS);
                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                        prefsEditor.putBoolean(Constants.KEY_CHECK_LOGIN, true);
                        preferenceManager.putBoolean(Constants.KEY_CHECK_LOGIN, true);
                        prefsEditor.apply();

                        //lưu trạng thái đã đăng nhập.
                        preferenceManager.putBoolean(Constants.KEY_LOGIN_STARUS, true);
                        Toast.makeText(SignInActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                            onBackPressed();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        sharedPreferences.edit().clear().apply();
                    }
                }, throwable -> {
                    // neu co loi thi debug o day nhe
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

    public void getPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        if (sharedPreferences.contains(Constants.KEY_SAVE_USER_INFO)) {
            String user = sharedPreferences.getString(Constants.KEY_SAVE_USER_INFO, "not found.");
            edtAccount.setText(user);
        }
        if (sharedPreferences.contains(Constants.KEY_SAVE_PASSWORD_INFO)) {
            String pass = sharedPreferences.getString(Constants.KEY_SAVE_PASSWORD_INFO, "not found.");
            edtPassword.setText(pass);
        }
        if (sharedPreferences.contains(Constants.KEY_SAVE_CHECK_BOX)) {
            Boolean check = sharedPreferences.getBoolean(Constants.KEY_SAVE_CHECK_BOX, false);
            cbSaveValue.setChecked(check);
        }
    }
}