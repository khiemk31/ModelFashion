package com.example.modelfashion.History.ViewHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.modelfashion.History.ApdapterHistory.HistoryAdapter;
import com.example.modelfashion.History.MHistory.ModelHistory;
import com.example.modelfashion.History.MHistory.ProductHistory;
import com.example.modelfashion.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    public static List<ModelHistory> listModelHistory ;
    private List<ProductHistory> listProduct;
    private ListView lv_history;
    private ImageView img_history_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lv_history = findViewById(R.id.lv_history);
        img_history_back = findViewById(R.id.img_history_back);
        img_history_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listModelHistory = new ArrayList<>();
        listProduct = new ArrayList<>();
        fakeData();
        HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this,listModelHistory);
        lv_history.setAdapter(historyAdapter);
        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HistoryActivity.this,DetailHistoryActivity.class);
                ModelHistory modelHistory = listModelHistory.get(i);
                intent.putExtra("index",i);
                intent.putExtra("view","History");
                startActivity(intent);

            }
        });


    }

    private void fakeData(){

        listProduct.add(new ProductHistory("1","a1","10000","L","https://i.pinimg.com/originals/ce/a7/5e/cea75e472d431e283a4a622ed1d7b155.png","10"));
        listProduct.add(new ProductHistory("2","a2","10000","L","https://i.pinimg.com/originals/ce/a7/5e/cea75e472d431e283a4a622ed1d7b155.png","10"));
        listProduct.add(new ProductHistory("3","a3","10000","L","https://i.pinimg.com/originals/ce/a7/5e/cea75e472d431e283a4a622ed1d7b155.png","10"));

        listModelHistory.add(new ModelHistory("HD1","0987563","ha noi","12/2 0:0",
                "13/2 0:0","Đang Đợi","100000",listProduct));
        listModelHistory.add(new ModelHistory("HD2","0987563","ha noi","12/2 0:0",
                "13/2 0:0","Giao Thanh Cong","100000",listProduct));
        listModelHistory.add(new ModelHistory("HD3","0987563","ha noi","12/2 0:0",
                "13/2 0:0","Giao Thanh Cong","100000",listProduct));


    }
}