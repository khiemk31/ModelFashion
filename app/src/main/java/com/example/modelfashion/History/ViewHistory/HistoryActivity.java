package com.example.modelfashion.History.ViewHistory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.History.ApdapterHistory.HistoryAdapter;
import com.example.modelfashion.History.ApiHistory.ApiHistory;
import com.example.modelfashion.Interface.ApiRetrofit;
import com.example.modelfashion.Model.response.bill.Bill;
import com.example.modelfashion.Model.response.bill.BillDetail;
import com.example.modelfashion.Model.response.my_product.MyProduct;

import com.example.modelfashion.R;
import com.example.modelfashion.network.Repository;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONArray;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
//    public static List<ModelHistory> listModelHistory ;
//    private List<ModelHistory> listModelHistoryNew;
//    private List<ProductHistory> listProduct;
    private ArrayList<Bill> arr_bill = new ArrayList<>();
    private ArrayList<BillDetail> arr_bill_detail = new ArrayList<>();
    private ArrayList<String> arr_detail_id = new ArrayList<>();
    private ArrayList<MyProduct> arr_my_product = new ArrayList<>();
    public static ListView lv_history;
    private ImageView img_history_back;
    private RelativeLayout rl_filter_history;
    private TextView tv_status_history;
    public static int numberStatus = 4;
    private String user_id = "1" ;
    private TextView tv_empty;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressBar progressBar;
    private ArrayList<Bill> bills;

    private Repository repository ;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lv_history = findViewById(R.id.lv_history);
        img_history_back = findViewById(R.id.img_history_back);
        rl_filter_history = findViewById(R.id.rl_filter_history);
        tv_status_history = findViewById(R.id.tv_status_history);
        progressBar = findViewById(R.id.progress_bar_history);
        tv_empty = findViewById(R.id.tv_empty);
        bills = new ArrayList<>();
        repository = new Repository(HistoryActivity.this);
        Intent intent = getIntent();
        numberStatus = intent.getIntExtra("numberStatus",4);
        user_id = intent.getStringExtra("user_id");
        loadTitleStatus(numberStatus);
       // Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();
        img_history_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_filter_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFilter();
            }
        });

//        listModelHistory = new ArrayList<>();
//        listModelHistoryNew = new ArrayList<>();
//        listProduct = new ArrayList<>();
//        fakeData();

        getAllBill();

        Log.e("id", String.valueOf(user_id));

    }


    private String loadData(int i){

        String status = "";
        switch (i) {
            case 1: status = "Đang Chờ";
                    break;
            case 2: status = "Hoàn Thành";
                    break;
            case 3: status = "Đang Giao";
                    break;
            case 4: status = "Đã Giao";
                    break;
            case 5: status = "Đã Hủy";
                break;

        }
        return status;

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getAllBill(){
//        compositeDisposable.add(repository.getAllBill(user_id)
//                .doOnSubscribe(disposable -> {
//                    progressBar.setVisibility(View.VISIBLE);
//                }).doFinally(() -> {
//                    progressBar.setVisibility(View.GONE);
//                }).subscribe(dataBill -> {
//                    bills.clear();
//                    bills = dataBill.getBills();
//                    Log.e("list", String.valueOf(bills.size()));
//
//                }, throwable -> {
//                    Log.e("err", String.valueOf(bills.size()));
//                }));
        ApiHistory.API_HISTORY.getBill(user_id).enqueue(new Callback<ArrayList<Bill>>() {
            @Override
            public void onResponse(Call<ArrayList<Bill>> call, Response<ArrayList<Bill>> response) {
                for (int i = 0;i<response.body().size();i++){
                    bills.add(response.body().get(i));
                }
                setListBill(loadData(numberStatus),bills);



            }

            @Override
            public void onFailure(Call<ArrayList<Bill>> call, Throwable t) {

            }
        });

    }




    private void setListBill(String status,ArrayList<Bill> bills){
        ArrayList<Bill> subBill = new ArrayList<>();
        for (int i = 0;i<bills.size();i++){
            if(bills.get(i).getStatus().matches(status)){
                subBill.add(bills.get(i));
            }
        }
        HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this,subBill);
        lv_history.setAdapter(historyAdapter);
    }


    private int filter_number = 0;
    private void showDialogFilter(){
        Dialog dialog = new Dialog(HistoryActivity.this);
        dialog.setContentView(R.layout.dialog_filter_history);
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView status1,status2,status3,status4,status5;
        status1 = dialog.findViewById(R.id.status1);
        status2 = dialog.findViewById(R.id.status2);
        status3 = dialog.findViewById(R.id.status3);
        status4 = dialog.findViewById(R.id.status4);
        status5 = dialog.findViewById(R.id.status5);
        TextView filter_history_cancel,filter_history_ok;
        filter_history_cancel = dialog.findViewById(R.id.filter_history_cancel);
        filter_history_ok = dialog.findViewById(R.id.filter_history_ok);
        loadThemeFilterStatus(numberStatus,status1,status2,status3,status4,status5);
        status1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 1;
                loadThemeFilterStatus(1,status1,status2,status3,status4,status5);
            }
        });
        status2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 2;
                loadThemeFilterStatus(2,status1,status2,status3,status4,status5);
            }
        });
        status3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 3;
                loadThemeFilterStatus(3,status1,status2,status3,status4,status5);
            }
        });
        status4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 4;
                loadThemeFilterStatus(4,status1,status2,status3,status4,status5);
            }
        });
        status5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 5;
                loadThemeFilterStatus(5,status1,status2,status3,status4,status5);
            }
        });
        filter_history_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 0;
                dialog.dismiss();
            }
        });
        filter_history_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberStatus = filter_number;
                loadThemeFilterStatus(numberStatus,status1,status2,status3,status4,status5);
                loadTitleStatus(numberStatus);

                setListBill(loadData(numberStatus),bills);
                dialog.dismiss();

            }
        });

        dialog.show();
    }
    public static void Update(){

    }
    private void loadTitleStatus(int i){
        if (i == 1){
            tv_status_history.setText("Chờ xác nhận");
            tv_status_history.setTextColor(Color.parseColor("#FF0000"));
        }else if (i == 2){
            tv_status_history.setText("Hoàn thành");
            tv_status_history.setTextColor(Color.parseColor("#ff9800"));
        }
        else if (i == 3){
            tv_status_history.setText("Đang giao");
            tv_status_history.setTextColor(Color.parseColor("#4caf50"));
        }
        else if (i == 4){
            tv_status_history.setText("Đã giao");
            tv_status_history.setTextColor(Color.parseColor("#4caf50"));
        }
        else if (i == 5){
            tv_status_history.setText("Đã hủy");
            tv_status_history.setTextColor(Color.parseColor("#FF0000"));
        }
    }
    private void loadThemeFilterStatus(int i,TextView st1,TextView st2,TextView st3,TextView st4,TextView st5){
        if (i == 1){
            st1.setTextColor(Color.parseColor("#FFFFFF"));
            st1.setBackground(getDrawable(R.drawable.bt_filter_history_select));
            st2.setTextColor(Color.parseColor("#000000"));
            st2.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st3.setTextColor(Color.parseColor("#000000"));
            st3.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st4.setTextColor(Color.parseColor("#000000"));
            st4.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st5.setTextColor(Color.parseColor("#000000"));
            st5.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        }else if (i == 2){
            st2.setTextColor(Color.parseColor("#FFFFFF"));
            st2.setBackground(getDrawable(R.drawable.bt_filter_history_select));
            st1.setTextColor(Color.parseColor("#000000"));
            st1.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st3.setTextColor(Color.parseColor("#000000"));
            st3.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st4.setTextColor(Color.parseColor("#000000"));
            st4.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st5.setTextColor(Color.parseColor("#000000"));
            st5.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        }
        else if (i == 3){
            st3.setTextColor(Color.parseColor("#FFFFFF"));
            st3.setBackground(getDrawable(R.drawable.bt_filter_history_select));
            st1.setTextColor(Color.parseColor("#000000"));
            st1.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st2.setTextColor(Color.parseColor("#000000"));
            st2.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st4.setTextColor(Color.parseColor("#000000"));
            st4.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st5.setTextColor(Color.parseColor("#000000"));
            st5.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        }
        else if (i == 4){
            st4.setTextColor(Color.parseColor("#FFFFFF"));
            st4.setBackground(getDrawable(R.drawable.bt_filter_history_select));
            st1.setTextColor(Color.parseColor("#000000"));
            st1.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st2.setTextColor(Color.parseColor("#000000"));
            st2.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st3.setTextColor(Color.parseColor("#000000"));
            st3.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st5.setTextColor(Color.parseColor("#000000"));
            st5.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        }
        else if (i == 5){
            st5.setTextColor(Color.parseColor("#FFFFFF"));
            st5.setBackground(getDrawable(R.drawable.bt_filter_history_select));
            st1.setTextColor(Color.parseColor("#000000"));
            st1.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st2.setTextColor(Color.parseColor("#000000"));
            st2.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st3.setTextColor(Color.parseColor("#000000"));
            st3.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st4.setTextColor(Color.parseColor("#000000"));
            st4.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        }


    }
}