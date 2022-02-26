package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.databinding.ActivityProfileBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.Calendar;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        loadDetails();
        setListener();
    }

    //load dữ liệu lên màn hình
    private void loadDetails() {

        preferenceManager.putString(Constants.KEY_PROFILE_SEX,"0");
        putTVSex(preferenceManager.getString(Constants.KEY_PROFILE_SEX));


        profileAnimation();
    }

    // check thông tin giới tính
    private void putTVSex(String check){
        if (check.equals("0")){
            binding.tvActProfileSex.setText("Khác");
        }else
        if (check.equals("1")){
            binding.tvActProfileSex.setText("Nam");
        }else
        if (check.equals("2")){
            binding.tvActProfileSex.setText("Nữ");
        }
    }

    //thêm chức năng vào các nút bấm
    private void setListener() {
        binding.layoutActProfileAvatar.setOnClickListener(v -> {
            RequestPermissions();
        });
        binding.btnActProfileBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.layoutActProfileName.setOnClickListener(v -> {
            changeProfile(1);
        });
        binding.layoutActProfilePhone.setOnClickListener(v -> {
            changeProfile(2);
        });
        binding.layoutActProfileSex.setOnClickListener(v -> {
            changeSex(preferenceManager.getString(Constants.KEY_PROFILE_SEX));
        });
        binding.layoutActProfileBirthday.setOnClickListener(v -> {
            changeBirthDay();
        });
        binding.layoutActProfileAddrest.setOnClickListener(v -> {
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
            edt.setText(binding.tvActProfileName.getText().toString());
            tvOK.setOnClickListener(v -> {
                binding.tvActProfileName.setText(edt.getText());
                Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        } else if (check == 2) {
            edt.setInputType(InputType.TYPE_CLASS_PHONE);

            tvTitle.setText("THAY ĐỔI SỐ ĐIỆN THOẠI");
            edt.setText(binding.tvActProfilePhone.getText().toString());
            tvOK.setOnClickListener(v -> {
                binding.tvActProfilePhone.setText(edt.getText());
                Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

        } else if (check == 3) {
            edt.setTextSize(15);
            tvTitle.setText("THAY ĐỔI ĐỊA CHỈ");
            edt.setText(binding.tvActProfileAddress.getText().toString() + "\n");
            tvOK.setOnClickListener(v -> {
                binding.tvActProfileAddress.setText(edt.getText());
                Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
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
            String day=String.valueOf(datePicker.getDayOfMonth());
            String month=String.valueOf(datePicker.getMonth());
            String year=String.valueOf(datePicker.getYear());

            preferenceManager.putString(Constants.KEY_PROFILE_BIRTHDAY_DAY, day);
            preferenceManager.putString(Constants.KEY_PROFILE_BIRTHDAY_MONTH, month);
            preferenceManager.putString(Constants.KEY_PROFILE_BIRTHDAY_YEAR, year);

           // binding.tvActProfileBirthday.setText(preferenceManager.getString(Constants.KEY_PROFILE_BIRTHDAY_DAY) +" - "+preferenceManager.getString(Constants.KEY_PROFILE_BIRTHDAY_MONTH) +" - "+ preferenceManager.getString(Constants.KEY_PROFILE_BIRTHDAY_YEAR));
            binding.tvActProfileBirthday.setText(day+" - " + month + " - " + year);

            Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
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


        if (check.equals("0")){
            rdo_khac.setChecked(true);
        }else
        if (check.equals("1")){
            rdo_nam.setChecked(true);
        }else
        if (check.equals("2")){
            rdo_nu.setChecked(true);
        }

        tvOK.setOnClickListener(v -> {
            if (rdo_khac.isChecked()){
                preferenceManager.putString(Constants.KEY_PROFILE_SEX,"0");
            } else
            if (rdo_nam.isChecked()){
                preferenceManager.putString(Constants.KEY_PROFILE_SEX,"1");
            } else
            if (rdo_nu.isChecked()){
                preferenceManager.putString(Constants.KEY_PROFILE_SEX,"2");
            }


            putTVSex(preferenceManager.getString(Constants.KEY_PROFILE_SEX));
            Toast.makeText(getApplicationContext(), "đã xong !", Toast.LENGTH_SHORT).show();
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


        binding.layoutActProfileTenDangNhap.setVisibility(View.VISIBLE);
        binding.layoutActProfileTenDangNhap.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutActProfileEmail.setVisibility(View.VISIBLE);
                binding.layoutActProfileEmail.startAnimation(animation1);
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutActProfileName.setVisibility(View.VISIBLE);
                binding.layoutActProfileName.startAnimation(animation2);
            }
        }, 400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutActProfilePhone.setVisibility(View.VISIBLE);
                binding.layoutActProfilePhone.startAnimation(animation3);
            }
        }, 600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutActProfileSex.setVisibility(View.VISIBLE);
                binding.layoutActProfileSex.startAnimation(animation4);
            }
        }, 800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutActProfileBirthday.setVisibility(View.VISIBLE);
                binding.layoutActProfileBirthday.startAnimation(animation5);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutActProfileAddrest.setVisibility(View.VISIBLE);
                binding.layoutActProfileAddrest.startAnimation(animation6);
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
                        binding.imgActProfileAvatar.setImageURI(uri);
                    }
                });

    }

}