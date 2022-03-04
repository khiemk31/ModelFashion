package com.example.modelfashion.History.ViewHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.modelfashion.History.ApdapterHistory.DetailHistoryAdapter;
import com.example.modelfashion.Model.MHistory.ModelHistory;
import com.example.modelfashion.Model.MHistory.ProductHistory;
import com.example.modelfashion.OrderStatus.ViewOrderStatus.OrderStatusActivity;
import com.example.modelfashion.R;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailHistoryActivity extends AppCompatActivity {
    TextView tv_dh_detail_history,phoneNumber_detail_history,address_detail_history,
            date_detail_history,summoney_detail_history,title_date_detail_history;
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
        title_date_detail_history = findViewById(R.id.title_date_detail_history);
        lv_detail_history = findViewById(R.id.lv_detail_history);
        back_detail_history = findViewById(R.id.back_detail_history);
        back_detail_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",-1);
        String view = intent.getStringExtra("view");
        if(view.matches("History")) {
            ModelHistory modelHistory = HistoryActivity.listModelHistory.get(index);
            list = new ArrayList<>();
            list = modelHistory.getProductHistoryList();
            tv_dh_detail_history.setText("Mã đơn : "+modelHistory.getmIDHistory());
            phoneNumber_detail_history.setText(modelHistory.getmPhoneNumber());
            address_detail_history.setText(" - "+modelHistory.getmAddress());
            title_date_detail_history.setText("Ngày nhận hàng: ");
            date_detail_history.setText(modelHistory.getmTimeRecieve());
            date_detail_history.setTextColor(Color.parseColor("#000000"));
            String summoney = sumPrice(list);
            summoney_detail_history.setText(summoney);
            DetailHistoryAdapter historyAdapter = new DetailHistoryAdapter(DetailHistoryActivity.this,list);
            lv_detail_history.setAdapter(historyAdapter);
        }else if(view.matches("OrderStatus")){
            ModelHistory modelHistory = OrderStatusActivity.listModelOrderStatus.get(index);
            list = new ArrayList<>();
            list = modelHistory.getProductHistoryList();
            tv_dh_detail_history.setText("Mã đơn : "+modelHistory.getmIDHistory());
            phoneNumber_detail_history.setText(modelHistory.getmPhoneNumber());
            address_detail_history.setText(" - "+modelHistory.getmAddress());
            title_date_detail_history.setText("Tình trạng đơn: ");
            date_detail_history.setText(modelHistory.getmStatus());
            if(modelHistory.getmStatus().matches("Đang Chờ")){
                date_detail_history.setTextColor(Color.parseColor("#FF0000"));
            }else if(modelHistory.getmStatus().matches("Đang Giao")){
                date_detail_history.setTextColor(Color.parseColor("#008E06"));
            }
            String summoney = sumPrice(list);
            summoney_detail_history.setText(summoney);
            DetailHistoryAdapter historyAdapter = new DetailHistoryAdapter(DetailHistoryActivity.this,list);
            lv_detail_history.setAdapter(historyAdapter);
        }



    }
    private String sumPrice(List<ProductHistory> list){
        int sum = 0;
        for (int i = 0;i<list.size();i++){
            sum+=Integer.parseInt(list.get(i).getmPriceProduct())*Integer.parseInt(list.get(i).getmSumProduct());
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        String sumP = numberFormat.format(Double.parseDouble(String.valueOf(sum)));
        return sumP;
    }
}