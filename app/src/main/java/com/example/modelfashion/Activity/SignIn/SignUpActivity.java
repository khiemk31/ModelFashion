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

    //tham chiếu
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
            Toast.makeText(SignUpActivity.this, "Không để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (special.matcher(edtNameSu.getText().toString().trim()).find() || special.matcher(edtPassword.getText().toString().trim()).find()) {
                Toast.makeText(SignUpActivity.this, "Không được viết kí tự đặc biệt", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (edtPassword.getText().toString().trim().length() < 6) {
                Toast.makeText(SignUpActivity.this, "Mật khẩu ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (edtPhoneSu.getText().toString().trim().length() > 10 || edtPhoneSu.getText().toString().trim().length() < 10) {
                Toast.makeText(SignUpActivity.this, "Số điện thoại có 10 kí tự!", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!edtConfirmPassword.getText().toString().trim().equalsIgnoreCase(edtPassword.getText().toString().trim())) {
                Toast.makeText(SignUpActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!cbRules.isChecked()) {
                Toast.makeText(SignUpActivity.this, "Vui lòng đọc và đồng ý với chính sách bảo mật", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

        // Set Title and Message:
        builder.setTitle("Chính sách bảo mật.").setMessage("Chúng tôi thu thập, lưu trữ và xử lý thông tin của bạn cho quá trình mua hàng, cho những thông báo sau này và để cung cấp dịch vụ. Chúng tôi không giới hạn thông tin cá nhân: danh hiệu, tên, giới tính, ngày sinh, email, địa chỉ, địa chỉ giao hàng, số điện thoại, fax, chi tiết thanh toán, chi tiết thanh toán bằng thẻ hoặc chi tiết tài khoản ngân hàng.\n" +
                "\n" +
                "Chúng tôi sẽ dùng thông tin bạn đã cung cấp để xử lý đơn đặt hàng, cung cấp các dịch vụ và thông tin yêu cầu thông qua trang web và theo yêu cầu của bạn. Chúng tôi có thể chuyển tên và địa chỉ cho bên thứ ba để họ giao hàng cho bạn (ví dụ cho bên chuyển phát nhanh hoặc nhà cung cấp).");

        //
        builder.setCancelable(true);

        // Create "Positive" button with OnClickListener.
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                builder.create().dismiss();
            }
        });
        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }

    //chặn back
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // optional depending on your needs
    }

}