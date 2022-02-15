package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.modelfashion.R;
import com.example.modelfashion.databinding.ActivityProfileBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadDetails();
        setListener();
    }

    //thêm chức năng vào các nút bấm
    private void setListener() {
        binding.actProfileImgAvatar.setOnClickListener(v -> {
            RequestPermissions();
        });
        binding.actProfileBtnBack.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    //load dữ liệu lên màn hình
    private void loadDetails() {

        profileAnimation();
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


        binding.actProfileLayoutTenDangNhap.setVisibility(View.VISIBLE);
        binding.actProfileLayoutTenDangNhap.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.actProfileLayoutEmail.setVisibility(View.VISIBLE);
                binding.actProfileLayoutEmail.startAnimation(animation1);
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.actProfileLayoutName.setVisibility(View.VISIBLE);
                binding.actProfileLayoutName.startAnimation(animation2);
            }
        }, 400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.actProfileLayoutPhone.setVisibility(View.VISIBLE);
                binding.actProfileLayoutPhone.startAnimation(animation3);
            }
        }, 600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.actProfileLayoutSex.setVisibility(View.VISIBLE);
                binding.actProfileLayoutSex.startAnimation(animation4);
            }
        }, 800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.actProfileLayoutBirthday.setVisibility(View.VISIBLE);
                binding.actProfileLayoutBirthday.startAnimation(animation5);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.actProfileLayoutAddrest.setVisibility(View.VISIBLE);
                binding.actProfileLayoutAddrest.startAnimation(animation6);
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
                        binding.actProfileImgAvatar.setImageURI(uri);
                    }
                });

    }

}