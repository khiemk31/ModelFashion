package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_ACTIVE;
import static com.example.modelfashion.Utility.Constants.KEY_ADDRESS;
import static com.example.modelfashion.Utility.Constants.KEY_AVARTAR;
import static com.example.modelfashion.Utility.Constants.KEY_BIRTHDAY;
import static com.example.modelfashion.Utility.Constants.KEY_FULL_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PHONE;
import static com.example.modelfashion.Utility.Constants.KEY_SEX;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.modelfashion.Activity.SignIn.OTPPhoneActivity;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.Interface.ApiRetrofit;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.Model.response.User.UpdateUserRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.Utility.RealPathUtil;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.network.ApiClient;
import com.example.modelfashion.network.ApiInterface;
import com.example.modelfashion.network.Repository;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    PreferenceManager preferenceManager;
    ConstraintLayout layoutActProfileName, layoutActProfilePhone, layoutActProfileSex, layoutActProfileBirthday, layoutActProfileAddrest;
    RelativeLayout layoutActProfileAvatar;
    AppCompatImageView btnActProfileBack, btnActProfileCheck;
    TextView tvActProfileSex, tvActProfileName, tvActProfileAddress, tvActProfileBirthday, tvActProfilePhone;
    RoundedImageView imgActProfileAvatar;
    ProgressLoadingCommon progressLoadingCommon;
    ApiInterface apiInterface;
    String avtUrl;
    Uri uriUrl;
    String birthDay;
    CompositeDisposable disposable = new CompositeDisposable();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        apiInterface = ApiClient.provideApiInterface(ProfileActivity.this);

        viewHolder();
        setListener();
        preferenceManager.putBoolean(Constants.KEY_CHANGE_IMAGE, false);
        getData();
    }

    private void viewHolder() {
        layoutActProfileAvatar = findViewById(R.id.layout_act_profile_avatar);
        layoutActProfileName = findViewById(R.id.layout_act_Profile_Name);
        layoutActProfileSex = findViewById(R.id.layout_act_Profile_Sex);
        layoutActProfileBirthday = findViewById(R.id.layout_act_Profile_birthday);
        layoutActProfileAddrest = findViewById(R.id.layout_act_Profile_addrest);
        layoutActProfilePhone = findViewById(R.id.layout_act_Profile_Phone);

        tvActProfileSex = findViewById(R.id.tv_act_Profile_sex);
        tvActProfileName = findViewById(R.id.tv_act_Profile_name);
        tvActProfileAddress = findViewById(R.id.tv_act_Profile_address);
        tvActProfileBirthday = findViewById(R.id.tv_act_Profile_birthday);
        tvActProfilePhone = findViewById(R.id.tv_act_Profile_Phone);

        btnActProfileBack = findViewById(R.id.btn_act_profile_back);
        btnActProfileCheck = findViewById(R.id.btn_act_profile_check);

        imgActProfileAvatar = findViewById(R.id.img_act_profile_avatar);
        progressLoadingCommon = new ProgressLoadingCommon();
        preferenceManager = new PreferenceManager(this);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void getData() {
        tvActProfileName.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
        tvActProfilePhone.setText(preferenceManager.getString(Constants.KEY_PHONE));

        birthDay = preferenceManager.getString(Constants.KEY_BIRTHDAY);
        tvActProfileBirthday.setText(birthDay);
        String address = preferenceManager.getString(Constants.KEY_ADDRESS);
        tvActProfileAddress.setText(address.equals("null") ? "Trống" : address);
        putTVSex(preferenceManager.getInt(Constants.KEY_SEX));
        Glide.with(this)
                .load(preferenceManager.getString(Constants.KEY_AVARTAR))
                .into(imgActProfileAvatar);
    }

    // check thông tin giới tính
    private void putTVSex(int check) {
        if (check == 3) {
            tvActProfileSex.setText("Khác");
        } else if (check == 1) {
            tvActProfileSex.setText("Nam");
        } else if (check == 2) {
            tvActProfileSex.setText("Nữ");
        }
    }

    //thêm chức năng vào các nút bấm
    private void setListener() {
        layoutActProfileAvatar.setOnClickListener(v -> {
            RequestPermissions();

        });
        btnActProfileBack.setOnClickListener(v -> {
            onBackPressed();
        });
        layoutActProfileName.setOnClickListener(v -> {
            changeProfile(1);
        });
        layoutActProfileSex.setOnClickListener(v -> {
            changeSex(preferenceManager.getInt(Constants.KEY_SEX));
        });
        layoutActProfileBirthday.setOnClickListener(v -> {
            changeBirthDay();
        });
        layoutActProfileAddrest.setOnClickListener(v -> {
            changeProfile(2);
        });
        btnActProfileCheck.setOnClickListener(v -> {

            Dialog dialog = new Dialog(ProfileActivity.this);
            dialog.setContentView(R.layout.dialog_change_profile);
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.findViewById(R.id.tv_yes_change).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUser();
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.tv_no_change).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    private void updateUser() {
        Repository repository = new Repository(this);
        disposable.add(repository.updateUser(preferenceManager.getString(Constants.KEY_ID),
                new UpdateUserRequest(tvActProfileName.getText().toString().trim(),
                        preferenceManager.getInt(Constants.KEY_SEX),
                        tvActProfileBirthday.getText().toString().trim(),
                        preferenceManager.getBoolean(Constants.KEY_CHANGE_IMAGE) == true ? avtUrl : "",
                        tvActProfileAddress.getText().toString().trim()))
                .doOnSubscribe(disposable -> {
                    showProgressBar(progressBar);
                }).subscribe(updateResponse -> {
                    Toast.makeText(ProfileActivity.this, updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    getUserDetail();
                    hideProgressBar(progressBar);
                }, throwable -> {
                    hideProgressBar(progressBar);
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                }));
    }

    private void getUserDetail() {
        Repository repository = new Repository(this);
        String userId = preferenceManager.getString(Constants.KEY_ID);
        disposable.add(repository.getUserDetail(userId)
                .doOnSubscribe(disposable -> {
                    showProgressBar(progressBar);
                }).subscribe(registerResponse -> {
                    hideProgressBar(progressBar);
                    preferenceManager.putString(KEY_AVARTAR, registerResponse.getData().getAvatar());
                    preferenceManager.putString(KEY_FULL_NAME, registerResponse.getData().getUsername());
                    preferenceManager.putString(KEY_PHONE, registerResponse.getData().getPhone());
                    preferenceManager.putString(KEY_BIRTHDAY, registerResponse.getData().getDateOfBirth());
                    preferenceManager.putString(KEY_ADDRESS, registerResponse.getData().getAddress());
                    preferenceManager.putInt(KEY_SEX, registerResponse.getData().getGender());
                    onBackPressed();
                }, throwable -> {
                    hideProgressBar(progressBar);
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                }));
    }

    //dialog thay đổi tên, số điện thoại, địa chỉ
    private void changeProfile(int check) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_change_profile);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextView tvTitle = dialog.findViewById(R.id.tv_layout_dialog_changeProfile_title);
        TextView tvCancel = dialog.findViewById(R.id.tv_layout_dialog_changeProfile_cancel);
        TextView tvOK = dialog.findViewById(R.id.tv_layout_dialog_changeProfile_ok);
        EditText edt = dialog.findViewById(R.id.edt_tv_layout_dialog_changeProfile_title);

        if (check == 1) {
            tvTitle.setText("THAY ĐỔI HỌ VÀ TÊN");
            edt.setText(tvActProfileName.getText().toString().trim());
            tvOK.setOnClickListener(v -> {
                if (edt.getText().toString().trim().isEmpty()) {
                    tvActProfileName.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
                } else {
                    tvActProfileName.setText(edt.getText().toString().trim());
                }
                dialog.dismiss();
                btnActProfileCheck.setVisibility(View.VISIBLE);
            });
        } else if (check == 2) {
            edt.setTextSize(15);
            tvTitle.setText("THAY ĐỔI ĐỊA CHỈ");
            edt.setText(tvActProfileAddress.getText().toString().trim());
            tvOK.setOnClickListener(v -> {
                if (edt.getText().toString().trim().isEmpty()) {
                    tvActProfileAddress.setText(preferenceManager.getString(Constants.KEY_ADDRESS));
                } else {
                    tvActProfileAddress.setText(edt.getText().toString().trim());
                }
                dialog.dismiss();
                btnActProfileCheck.setVisibility(View.VISIBLE);
            });

        }
        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();

    }

    //dialog thay đổi lựa chọn ngày sinh nhật
    private void changeBirthDay() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_change_birthday);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextView tvCancel = dialog.findViewById(R.id.tv_layout_dialog_changebirthday_cancel);
        TextView tvOK = dialog.findViewById(R.id.tv_layout_dialog_changebirthday_ok);
        DatePicker datePicker = dialog.findViewById(R.id.datePicker_layout_dialog_changeBirthday);

        String date = preferenceManager.getString(Constants.KEY_BIRTHDAY);
        String[] date1 = date.split("-");
        datePicker.init(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]) - 1, Integer.parseInt(date1[0]), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });

        tvOK.setOnClickListener(v -> {
            int month1 = datePicker.getMonth() + 1;
            tvActProfileBirthday.setText(datePicker.getDayOfMonth() + "-" + month1 + "-" + datePicker.getYear());
            dialog.dismiss();
            btnActProfileCheck.setVisibility(View.VISIBLE);
        });

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    // dialog thay đổi lựa chọn giới tính
    private void changeSex(int check) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_change_sex);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextView tvCancel = dialog.findViewById(R.id.tv_layout_dialog_changeSex_cancel);
        TextView tvOK = dialog.findViewById(R.id.tv_layout_dialog_changeSex_ok);
        RadioButton rdo_khac = dialog.findViewById(R.id.radio_layout_dialog_changeSex_khac);
        RadioButton rdo_nam = dialog.findViewById(R.id.radio_layout_dialog_changeSex_nam);
        RadioButton rdo_nu = dialog.findViewById(R.id.radio_layout_dialog_changeSex_nu);


        if (check == 3) {
            rdo_khac.setChecked(true);
        } else if (check == 1) {
            rdo_nam.setChecked(true);
        } else if (check == 2) {
            rdo_nu.setChecked(true);
        }

        tvOK.setOnClickListener(v -> {
            if (rdo_khac.isChecked()) {
                preferenceManager.putInt(Constants.KEY_SEX, 3);
            } else if (rdo_nam.isChecked()) {
                preferenceManager.putInt(Constants.KEY_SEX, 1);
            } else if (rdo_nu.isChecked()) {
                preferenceManager.putInt(Constants.KEY_SEX, 2);
            }

            putTVSex(preferenceManager.getInt(Constants.KEY_SEX));
            dialog.dismiss();
            btnActProfileCheck.setVisibility(View.VISIBLE);
        });

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }




    // lấy ảnh từ máy và đưa lên màn hình
    // xin quyền truy cập máy ảnh và file để lấy ảnh
    private void RequestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //nếu đã cấp quyền thì truy cập vào bộ nhớ để lấy ảnh
                reQuestPermission();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ProfileActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    // truy cập bộ nhớ
    private void reQuestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //chọn ảnh
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ProfileActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    // CHọn ảnh và set vào giao diện
    private void openImagePicker() {
        TedBottomPicker.with(ProfileActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        imgActProfileAvatar.setImageURI(uri);
                        btnActProfileCheck.setVisibility(View.VISIBLE);
                        uriUrl = uri;
                        preferenceManager.putBoolean(Constants.KEY_CHANGE_IMAGE, true);
                        getImageUrl();
                    }
                });
    }

    private void getImageUrl() {
        MediaManager.get().upload(uriUrl).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                showProgressBar(progressBar);
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                avtUrl = String.valueOf(resultData.get("secure_url"));
                hideProgressBar(progressBar);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Toast.makeText(ProfileActivity.this, (CharSequence) error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Toast.makeText(ProfileActivity.this, (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }).dispatch();
    }

    void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}