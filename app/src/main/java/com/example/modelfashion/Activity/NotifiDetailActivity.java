package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.modelfashion.R;

public class NotifiDetailActivity extends AppCompatActivity {
    ImageView img_notifi_detail_back;
    TextView tv_date_detail,tv_title_detail,tv_content_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi_detail);
        tv_date_detail = findViewById(R.id.tv_date_detail);
        tv_title_detail = findViewById(R.id.tv_title_detail);
        tv_content_detail = findViewById(R.id.tv_content_detail);
        img_notifi_detail_back = findViewById(R.id.img_notifi_detail_back);
        img_notifi_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");
        tv_title_detail.setText(title);
        tv_content_detail.setText(content);
        tv_date_detail.setText(date);
    }
}