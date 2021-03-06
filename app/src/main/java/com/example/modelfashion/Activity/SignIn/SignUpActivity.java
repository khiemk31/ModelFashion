package com.example.modelfashion.Activity.SignIn;

import static com.example.modelfashion.Utility.Constants.KEY_ACTIVE;
import static com.example.modelfashion.Utility.Constants.KEY_ADDRESS;
import static com.example.modelfashion.Utility.Constants.KEY_AVARTAR;
import static com.example.modelfashion.Utility.Constants.KEY_BIRTHDAY;
import static com.example.modelfashion.Utility.Constants.KEY_FULL_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PHONE;
import static com.example.modelfashion.Utility.Constants.KEY_SEX;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Activity.ProfileActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Model.response.Register.CheckUserRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.network.ApiClient;
import com.example.modelfashion.network.ApiInterface;
import com.example.modelfashion.network.Repository;

import java.util.regex.Pattern;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ImageView imgBack;
    EditText edtPhoneSu, edtPassword, edtConfirmPassword, edtNameSu;
    Button btnSignUp;
    CheckBox cbRules;
    TextView tvSignIn, tvRules;
    ApiInterface apiInterface;
    ProgressLoadingCommon progressLoadingCommon;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        viewHolder();
        setListener();
    }

    //tham chi???u
    private void viewHolder() {
        imgBack = findViewById(R.id.imgBack);
        edtPhoneSu = findViewById(R.id.edtPhoneSu);
        edtPassword = findViewById(R.id.edtPasswordSu);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtNameSu = findViewById(R.id.edtNameSu);
        btnSignUp = findViewById(R.id.btnSignUp);
        cbRules = findViewById(R.id.cbRules);
        tvSignIn = findViewById(R.id.tvSignIn);
        apiInterface = ApiClient.provideApiInterface(SignUpActivity.this);
        progressLoadingCommon = new ProgressLoadingCommon();
        tvRules = findViewById(R.id.tvRules);
    }

    // b???t s??? ki???n
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
                    checkUser();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        tvRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void insertUser() {
        Intent intent = new Intent(this, OTPPhoneActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone", edtPhoneSu.getText().toString().trim());
        bundle.putString("name", edtNameSu.getText().toString().trim());
        bundle.putString("password", edtPassword.getText().toString().trim());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void checkUser() {
        Repository repository = new Repository(this);
        disposable.add(repository.checkUser(new CheckUserRequest(edtPhoneSu.getText().toString().trim()))
                .doOnSubscribe(disposable -> {
                    progressLoadingCommon.showProgressLoading(this);
                }).subscribe(checkUserResponse -> {
                    insertUser();
                }, throwable -> {
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
                }));
    }

    // validate
    private Boolean validate() {
        Pattern special = Pattern.compile("[!#$%&*^()_+=|<>?{}\\[\\]~-]");
        if (edtPhoneSu.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtConfirmPassword.getText().toString().isEmpty() || edtNameSu.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Kh??ng ????? tr???ng", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (special.matcher(edtNameSu.getText().toString().trim()).find() || special.matcher(edtPassword.getText().toString().trim()).find()) {
                Toast.makeText(SignUpActivity.this, "Kh??ng ???????c vi???t k?? t??? ?????c bi???t", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (edtPassword.getText().toString().trim().length() < 6) {
                Toast.makeText(SignUpActivity.this, "M???t kh???u ??t nh???t 6 k?? t???", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (edtPhoneSu.getText().toString().trim().length() > 10 || edtPhoneSu.getText().toString().trim().length() < 10) {
                Toast.makeText(SignUpActivity.this, "S??? ??i???n tho???i c?? 10 k?? t???!", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!edtConfirmPassword.getText().toString().trim().equalsIgnoreCase(edtPassword.getText().toString().trim())) {
                Toast.makeText(SignUpActivity.this, "M???t kh???u kh??ng gi???ng nhau", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!cbRules.isChecked()) {
                Toast.makeText(SignUpActivity.this, "Vui l??ng ?????c v?? ?????ng ?? v???i ch??nh s??ch b???o m???t", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

        // Set Title and Message:
        builder.setTitle("Ch??nh s??ch b???o m???t.").setMessage("Ch??ng t??i thu th???p, l??u tr??? v?? x??? l?? th??ng tin c???a b???n cho qu?? tr??nh mua h??ng, cho nh???ng th??ng b??o sau n??y v?? ????? cung c???p d???ch v???. Ch??ng t??i kh??ng gi???i h???n th??ng tin c?? nh??n: danh hi???u, t??n, gi???i t??nh, ng??y sinh, email, ?????a ch???, ?????a ch??? giao h??ng, s??? ??i???n tho???i, fax, chi ti???t thanh to??n, chi ti???t thanh to??n b???ng th??? ho???c chi ti???t t??i kho???n ng??n h??ng.\n" +
                "\n" +
                "Ch??ng t??i s??? d??ng th??ng tin b???n ???? cung c???p ????? x??? l?? ????n ?????t h??ng, cung c???p c??c d???ch v??? v?? th??ng tin y??u c???u th??ng qua trang web v?? theo y??u c???u c???a b???n. Ch??ng t??i c?? th??? chuy???n t??n v?? ?????a ch??? cho b??n th??? ba ????? h??? giao h??ng cho b???n (v?? d??? cho b??n chuy???n ph??t nhanh ho???c nh?? cung c???p).");

        //
        builder.setCancelable(true);

        // Create "Positive" button with OnClickListener.
        builder.setPositiveButton("????ng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                builder.create().dismiss();
            }
        });
        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }

    //ch???n back
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // optional depending on your needs
    }

}