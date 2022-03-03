package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.databinding.ActivityProfileBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Calendar;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ProfileActivity extends AppCompatActivity {


    PreferenceManager preferenceManager;
    ConstraintLayout layoutActProfileName,layoutActProfilePhone,layoutActProfileSex,layoutActProfileBirthday,layoutActProfileAddrest,layoutActProfileTenDangNhap,layoutActProfileEmail;
    RelativeLayout layoutActProfileAvatar;
    AppCompatImageView btnActProfileBack,btnActProfileCheck;
    TextView tvActProfileSex,tvActProfileName,tvActProfilePhone,tvActProfileAddress,tvActProfileBirthday;
    RoundedImageView imgActProfileAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferenceManager = new PreferenceManager(getApplicationContext());

        viewHolder();
        loadDetails();
        setListener();
    }

    private void viewHolder() {
        layoutActProfileAvatar=findViewById(R.id.layout_act_profile_avatar);
        layoutActProfileName=findViewById(R.id.layout_act_Profile_Name);
        layoutActProfilePhone=findViewById(R.id.layout_act_Profile_phone);
        layoutActProfileSex=findViewById(R.id.layout_act_Profile_Sex);
        layoutActProfileBirthday=findViewById(R.id.layout_act_Profile_birthday);
        layoutActProfileAddrest=findViewById(R.id.layout_act_Profile_addrest);
        layoutActProfileTenDangNhap=findViewById(R.id.layout_act_Profile_TenDangNhap);
        layoutActProfileEmail=findViewById(R.id.layout_act_Profile_Email);

        tvActProfileSex = findViewById(R.id.tv_act_Profile_sex);
        tvActProfileName=findViewById(R.id.tv_act_Profile_name);
        tvActProfilePhone=findViewById(R.id.tv_act_Profile_phone);
        tvActProfileAddress=findViewById(R.id.tv_act_Profile_address);
        tvActProfileBirthday=findViewById(R.id.tv_act_Profile_birthday);

        btnActProfileBack=findViewById(R.id.btn_act_profile_back);
        btnActProfileCheck=findViewById(R.id.btn_act_profile_check);

        imgActProfileAvatar=findViewById(R.id.img_act_profile_avatar);
    }

//    kiểm tra dữ liệu có thay đổi không
    private void checkChange(){
        btnActProfileCheck.setVisibility(View.VISIBLE);

        btnActProfileCheck.setOnClickListener(v -> {
            startActivity( new Intent(this,MainActivity.class));
        });
    }

    //load dữ liệu lên màn hình
    private void loadDetails() {

        preferenceManager.putString(Constants.KEY_PROFILE_SEX, "0");
        putTVSex(preferenceManager.getString(Constants.KEY_PROFILE_SEX));


        profileAnimation();
    }

    // check thông tin giới tính
    private void putTVSex(String check) {
        if (check.equals("0")) {
            tvActProfileSex.setText("Khác");
        } else if (check.equals("1")) {
            tvActProfileSex.setText("Nam");
        } else if (check.equals("2")) {
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
        layoutActProfilePhone.setOnClickListener(v -> {
            changeProfile(2);
        });
        layoutActProfileSex.setOnClickListener(v -> {
            changeSex(preferenceManager.getString(Constants.KEY_PROFILE_SEX));
        });
        layoutActProfileBirthday.setOnClickListener(v -> {
            changeBirthDay();
        });
        layoutActProfileAddrest.setOnClickListener(v -> {
            changeProfile(3);
        });
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
            edt.setText(tvActProfileName.getText().toString());
            tvOK.setOnClickListener(v -> {
                tvActProfileName.setText(edt.getText());
                Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
                checkChange();
                dialog.dismiss();
            });
        } else if (check == 2) {
            edt.setInputType(InputType.TYPE_CLASS_PHONE);

            tvTitle.setText("THAY ĐỔI SỐ ĐIỆN THOẠI");
            edt.setText(tvActProfilePhone.getText().toString());
            tvOK.setOnClickListener(v -> {
                tvActProfilePhone.setText(edt.getText());
                Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
                checkChange();
                dialog.dismiss();
            });

        } else if (check == 3) {
            edt.setTextSize(15);
            tvTitle.setText("THAY ĐỔI ĐỊA CHỈ");
            edt.setText(tvActProfileAddress.getText().toString() + "\n");
            tvOK.setOnClickListener(v -> {
                tvActProfileAddress.setText(edt.getText());
                Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
                checkChange();
                dialog.dismiss();
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

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());


        tvOK.setOnClickListener(v -> {
            String day = String.valueOf(datePicker.getDayOfMonth());
            String month = String.valueOf(datePicker.getMonth());
            String year = String.valueOf(datePicker.getYear());

            preferenceManager.putString(Constants.KEY_PROFILE_BIRTHDAY_DAY, day);
            preferenceManager.putString(Constants.KEY_PROFILE_BIRTHDAY_MONTH, month);
            preferenceManager.putString(Constants.KEY_PROFILE_BIRTHDAY_YEAR, year);

            // binding.tvActProfileBirthday.setText(preferenceManager.getString(Constants.KEY_PROFILE_BIRTHDAY_DAY) +" - "+preferenceManager.getString(Constants.KEY_PROFILE_BIRTHDAY_MONTH) +" - "+ preferenceManager.getString(Constants.KEY_PROFILE_BIRTHDAY_YEAR));
            tvActProfileBirthday.setText(day + " - " + month + " - " + year);

            Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
            checkChange();
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    // dialog thay đổi lựa chọn giới tính
    private void changeSex(String check) {
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


        if (check.equals("0")) {
            rdo_khac.setChecked(true);
        } else if (check.equals("1")) {
            rdo_nam.setChecked(true);
        } else if (check.equals("2")) {
            rdo_nu.setChecked(true);
        }

        tvOK.setOnClickListener(v -> {
            if (rdo_khac.isChecked()) {
                preferenceManager.putString(Constants.KEY_PROFILE_SEX, "0");
            } else if (rdo_nam.isChecked()) {
                preferenceManager.putString(Constants.KEY_PROFILE_SEX, "1");
            } else if (rdo_nu.isChecked()) {
                preferenceManager.putString(Constants.KEY_PROFILE_SEX, "2");
            }


            putTVSex(preferenceManager.getString(Constants.KEY_PROFILE_SEX));
            Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
            checkChange();
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    //animation
    private void profileAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
        Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
        Animation animation5 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
        Animation animation6 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);


        layoutActProfileTenDangNhap.setVisibility(View.VISIBLE);
        layoutActProfileTenDangNhap.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutActProfileEmail.setVisibility(View.VISIBLE);
                layoutActProfileEmail.startAnimation(animation1);
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutActProfileName.setVisibility(View.VISIBLE);
                layoutActProfileName.startAnimation(animation2);
            }
        }, 400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutActProfilePhone.setVisibility(View.VISIBLE);
                layoutActProfilePhone.startAnimation(animation3);
            }
        }, 600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutActProfileSex.setVisibility(View.VISIBLE);
                layoutActProfileSex.startAnimation(animation4);
            }
        }, 800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutActProfileBirthday.setVisibility(View.VISIBLE);
                layoutActProfileBirthday.startAnimation(animation5);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutActProfileAddrest.setVisibility(View.VISIBLE);
                layoutActProfileAddrest.startAnimation(animation6);
            }
        }, 1200);
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
                        // here is selected image uri
                        imgActProfileAvatar.setImageURI(uri);
                        checkChange();
                    }
                });

    }
}