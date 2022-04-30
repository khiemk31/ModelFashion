package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.modelfashion.Adapter.NotifiAdapter;
import com.example.modelfashion.Model.Notifi;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.List;

public class NotifiActivity extends AppCompatActivity {
    RecyclerView rcl_notifi;
    List<Notifi> notifiList;
    TextView notifi_emty;
    ImageView img_notifi_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi);
        rcl_notifi = findViewById(R.id.rcl_notifi);
        notifi_emty = findViewById(R.id.notifi_emty);
        img_notifi_back = findViewById(R.id.img_notifi_back);
        img_notifi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        notifiList = new ArrayList<>();
        fakeData();
        if (notifiList.size() >0 ){
            notifi_emty.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(NotifiActivity.this);
            rcl_notifi.setLayoutManager(layoutManager);
            NotifiAdapter notifiAdapter = new NotifiAdapter(NotifiActivity.this,notifiList);
            rcl_notifi.setAdapter(notifiAdapter);
        }else {
            notifi_emty.setVisibility(View.VISIBLE);
        }

    }

    private void fakeData(){
        Notifi notifi1 = new Notifi("1","title1","content1","date1");
        Notifi notifi2 = new Notifi("2","title2","content2","date2");
        notifiList.add(notifi1);
        notifiList.add(notifi2);
        notifiList.add(notifi1);
        notifiList.add(notifi2);
    }
}