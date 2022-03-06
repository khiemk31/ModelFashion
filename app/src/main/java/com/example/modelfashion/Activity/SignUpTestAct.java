package com.example.modelfashion.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.modelfashion.Interface.ApiRetrofit;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.RealPathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpTestAct extends AppCompatActivity {
    ImageView imgDk;
    EditText edtTk, edtMk;
    Button btnDk;
    private final int Request_Code = 1;
    String real_path = "";
    String taikhoan = "";
    String matkhau = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_test);
        imgDk = findViewById(R.id.img_dangky);
        edtTk = findViewById(R.id.edt_taikhoan);
        edtMk = findViewById(R.id.edt_matkhau);
        btnDk = findViewById(R.id.btn_dangki_tk);
        imgDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickRequestPermission();
            }
        });
        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edtTk.getText().toString();
                matkhau = edtMk.getText().toString();
                String baseUrl = "https://cuongb2k53lvt.000webhostapp.com/FashionShop/user_avatar/";
                File file = new File(real_path);
                String file_path = file.getAbsolutePath();
                //Cắt tên file
                String[] tenfile = file_path.split("\\.");
                //nối thêm thời gian upload vào tên file để không bị trùng ảnh
                file_path = tenfile[0] + System.currentTimeMillis() + "." + tenfile[1];
                //chuyển file về dạng multipart/form-data
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);
                ApiRetrofit.apiRetrofit.uploadAvatar(body).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response != null){
                            String message = response.body();
                            if(message.length()>0){
                                ApiRetrofit.apiRetrofit.InsertUser(taikhoan,matkhau,baseUrl+response.body().trim()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if(response.body().trim().equals("Success")){
                                            Toast.makeText(SignUpTestAct.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(SignUpTestAct.this, "Đăng kí thất bại"+ response.body().trim(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void OnClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,Request_Code);
        }else {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_Code);
            }else {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission,Request_Code);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Request_Code && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            real_path = RealPathUtil.getRealPath(SignUpTestAct.this, uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgDk.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}