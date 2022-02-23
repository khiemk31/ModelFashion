package com.example.modelfashion.History.ViewHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.History.ApdapterHistory.DetailHistoryAdapter;
import com.example.modelfashion.History.MHistory.ModelHistory;
import com.example.modelfashion.History.MHistory.ProductHistory;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.List;

public class DetailHistoryActivity extends AppCompatActivity {
    TextView tv_dh_detail_history,phoneNumber_detail_history,address_detail_history,
            date_detail_history,summoney_detail_history;
    List<ProductHistory> list;
    ListView lv_detail_history;
    ImageView back_detail_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        tv_dh_detail_history = findViewById(R.id.tv_dh_detail_history);
        phoneNumber_detail_history = findViewById(R.id.phoneNumber_detail_history);
        address_detail_history = findViewById(R.id.address_detail_history);
        date_detail_history = findViewById(R.id.date_detail_history);
        summoney_detail_history = findViewById(R.id.summoney_detail_history);
        lv_detail_history = findViewById(R.id.lv_detail_history);
        back_detail_history = findViewById(R.id.back_detail_history);
        back_detail_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",-1);
        ModelHistory modelHistory = HistoryActivity.listModelHistory.get(index);
        list = new ArrayList<>();
        list = modelHistory.getProductHistoryList();
        tv_dh_detail_history.setText("Đơn hàng : "+modelHistory.getmIDHistory());
        phoneNumber_detail_history.setText(modelHistory.getmPhoneNumber());
        address_detail_history.setText(modelHistory.getmAddress());
        date_detail_history.setText(modelHistory.getmTimeRecieve());
        summoney_detail_history.setText(modelHistory.getmSumPrice()+"đ");

        DetailHistoryAdapter historyAdapter = new DetailHistoryAdapter(DetailHistoryActivity.this,list);
        lv_detail_history.setAdapter(historyAdapter);

    }
}