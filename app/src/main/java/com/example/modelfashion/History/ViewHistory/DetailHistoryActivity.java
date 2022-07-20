package com.example.modelfashion.History.ViewHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.modelfashion.History.ApdapterHistory.DetailHistoryAdapter;
import com.example.modelfashion.History.ApiHistory.ApiHistory;
import com.example.modelfashion.Interface.ApiRetrofit;
import com.example.modelfashion.Model.response.bill.BillDetail;
import com.example.modelfashion.Model.response.bill.BillProducts;
import com.example.modelfashion.Model.response.bill.ContentBill;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.R;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHistoryActivity extends AppCompatActivity {
    TextView tv_dh_detail_history,phoneNumber_detail_history,address_detail_history,
            date_detail_history,summoney_detail_history,title_date_detail_history;
//    List<ProductHistory> list;
    ListView lv_detail_history;
    ImageView back_detail_history;
    String bill_id, date_shipped, amount,status;
    public static String user_id;
    ArrayList<ContentBill> arr_bill_detail = new ArrayList<>();
    ArrayList<BillProducts> arr_my_product = new ArrayList<>();
    private TextView contentCancelBill;

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
        contentCancelBill = findViewById(R.id.contentCancelBill);
        back_detail_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent = getIntent();
        bill_id = intent.getStringExtra("bill_id");
        getInfoBill();

        title_date_detail_history.setText("Ngày mua hàng:");

        //Set data bill detail





    }

    private void getInfoBill(){
        ApiHistory.API_HISTORY.getBillDetail(bill_id).enqueue(new Callback<BillDetail>() {
            @Override
            public void onResponse(Call<BillDetail> call, Response<BillDetail> response) {
                for (int i = 0;i<response.body().getBill().size();i++){
                    arr_bill_detail.add(response.body().getBill().get(i));
                }
                for (int i = 0;i<response.body().getListProduct().size();i++){
                    arr_my_product.add(response.body().getListProduct().get(i));
                }
                setInfoBill();
            }

            @Override
            public void onFailure(Call<BillDetail> call, Throwable t) {

            }
        });
    }
    private void setInfoBill(){
        DecimalFormat format = new DecimalFormat("###,###,###");
        ContentBill contentBill = arr_bill_detail.get(0);
        tv_dh_detail_history.setText("Mã Đơn: "+contentBill.getBill_id());
        phoneNumber_detail_history.setText(contentBill.getPhone());
        address_detail_history.setText(contentBill.getAddress());
        date_detail_history.setText(contentBill.getCreated_at().substring(0,10));
        summoney_detail_history.setText(format.format(Double.valueOf(contentBill.getTotal_price()))+" đ");
        DetailHistoryAdapter historyAdapter = new DetailHistoryAdapter(DetailHistoryActivity.this,arr_my_product);
        lv_detail_history.setAdapter(historyAdapter);

        if(contentBill.getCancellation_reason() == null && contentBill.getFeedback()==null && contentBill.getReturn_request()==null){
            contentCancelBill.setVisibility(View.GONE);
        }else {
            contentCancelBill.setVisibility(View.VISIBLE);
            if(contentBill.getCancellation_reason()!=null){
                contentCancelBill.setText("Lý do hủy: "+contentBill.getCancellation_reason());
            }else if(contentBill.getFeedback()!=null){
                contentCancelBill.setText("Phản hồi: "+contentBill.getFeedback());
            }else if(contentBill.getReturn_request()!=null){
                contentCancelBill.setText("Lý do trả hàng: "+contentBill.getReturn_request());
            }
        }

    }




}