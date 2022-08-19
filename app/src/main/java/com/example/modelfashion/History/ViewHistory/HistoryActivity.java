package com.example.modelfashion.History.ViewHistory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
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
import com.example.modelfashion.customview.MonthYearPickerDialog;
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

    public static ListView lv_history;
    private ImageView img_history_back;
    private RelativeLayout rl_filter_history;
    private TextView tv_status_history, tv_month_history;
    public static int numberStatus = 4;
    private String user_id = "USER1";
    private TextView tv_empty;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressBar progressBar;
    private ArrayList<Bill> bills;
    private HistoryAdapter historyAdapter;

    private Repository repository;
    public static RelativeLayout rl_load;
    private int month = 0;
    private int year = 0;
    private RelativeLayout rl_month_history;
    private SwipeRefreshLayout refresh_history;


    private BroadcastReceiver broadCastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            String check = intent.getStringExtra("action");
            if (check.matches("load")) {
                getAllBill();
                if (rl_load.getVisibility() == View.VISIBLE) {
                    rl_load.setVisibility(View.GONE);
                }
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiver, new IntentFilter("send_data_to_activity"));
        lv_history = findViewById(R.id.lv_history);
        img_history_back = findViewById(R.id.img_history_back);
        rl_filter_history = findViewById(R.id.rl_filter_history);
        tv_status_history = findViewById(R.id.tv_status_history);
        progressBar = findViewById(R.id.progress_bar_history);
        tv_month_history = findViewById(R.id.tv_month_history);
        rl_month_history = findViewById(R.id.rl_month_history);
        refresh_history = findViewById(R.id.refresh_history);
        tv_empty = findViewById(R.id.tv_empty);
        bills = new ArrayList<>();
        rl_load = findViewById(R.id.rl_load);
        repository = new Repository(HistoryActivity.this);
        Intent intent = getIntent();
        numberStatus = intent.getIntExtra("numberStatus", 4);
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
        rl_month_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthYearPickerDialog pd;
                if (month != 0 && year != 0) {
                    pd = MonthYearPickerDialog.newInstance(month, 1, year);
                } else {
                    pd = MonthYearPickerDialog.newInstance(1, 1, 2022);
                }
                //new MonthYearPickerDialog();

                pd.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        month = selectedMonth;
                        year = selectedYear;
                        setListBill(numberStatus, bills);

                    }
                });
                pd.show(getSupportFragmentManager(), "dialog");
            }
        });

//        listModelHistory = new ArrayList<>();
//        listModelHistoryNew = new ArrayList<>();
//        listProduct = new ArrayList<>();
//        fakeData();

        getAllBill();

        Log.e("id", String.valueOf(user_id));
        refresh_history.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_history.setRefreshing(false);
                setListBill(numberStatus, bills);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver((BroadcastReceiver) broadCastReceiver);
    }

    private String loadData(int i) {

        String status = "";
        switch (i) {
            case 1:
                status = "Chờ Xác Nhận";
                break;
            case 2:
                status = "Đang Chờ Xử Lý";
                break;
            case 3:
                status = "Đang Giao";
                break;
            case 4:
                status = "Hoàn Thành";
                break;
            case 5:
                status = "Thất Bại";
                break;
            case 6:
                status = "Từ Chối";
                break;

        }
        return status;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getAllBill() {
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
        rl_load.setVisibility(View.VISIBLE);
        ApiHistory.API_HISTORY.getBill(user_id).enqueue(new Callback<ArrayList<Bill>>() {
            @Override
            public void onResponse(Call<ArrayList<Bill>> call, Response<ArrayList<Bill>> response) {

                if (response.body() != null) {
                    bills.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        bills.add(response.body().get(i));
                    }
                    setListBill(numberStatus, bills);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Bill>> call, Throwable t) {

            }
        });

    }


    private void setListBill(int status, ArrayList<Bill> bills) {
        rl_load.setVisibility(View.VISIBLE);
        lv_history.setVisibility(View.INVISIBLE);
        tv_empty.setVisibility(View.INVISIBLE);

        ArrayList<Bill> subBill = new ArrayList<>();
        if (status < 6) {
            for (int i = 0; i < bills.size(); i++) {
                if (status == 1) {
                    if (bills.get(i).getStatus().matches("Chờ Xác Nhận")) {
                        subBill.add(bills.get(i));
                    }
                } else if (status == 2) {
                    if (bills.get(i).getStatus().matches("Yêu Cầu Hủy Đơn") || bills.get(i).getStatus().matches("Yêu Cầu Hoàn Đơn")) {
                        subBill.add(bills.get(i));
                    }
                } else if (status == 3) {
                    if (bills.get(i).getStatus().matches("Đang Giao")) {
                        subBill.add(bills.get(i));
                    }
                } else if (status == 4) {
                    if (bills.get(i).getStatus().matches("Hoàn Thành")) {
                        subBill.add(bills.get(i));
                    }
                } else if (status == 5) {
                    if (bills.get(i).getStatus().matches("Đã Hủy") || bills.get(i).getStatus().matches("Đã Hoàn")
                            || bills.get(i).getStatus().matches("Thất Bại") || bills.get(i).getStatus().matches("Từ Chối Đơn")) {
                        subBill.add(bills.get(i));
                    }
                }

            }
        } else {
            subBill.addAll(bills);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                historyAdapter = new HistoryAdapter(HistoryActivity.this, subBill, HistoryActivity.this);
                FindBillByMonth(month, year, subBill);
                lv_history.setAdapter(historyAdapter);
                Log.e("zzzz", String.valueOf(subBill.size()));
            }
        },2000);


    }

    public void FindBillByMonth(int month, int year, ArrayList<Bill> mybills) {

        ArrayList<Bill> billbymonth = new ArrayList<>();
        rl_load.setVisibility(View.INVISIBLE);
        if (bills != null) {

            if (month != 0 && year != 0) {
                tv_month_history.setText("T" + month + "-" + year);
                for (int i = 0; i < mybills.size(); i++) {
                    String date = mybills.get(i).getCreated_at();
                    String[] arr = date.split("-");
                    if (Integer.parseInt(arr[1]) == month && Integer.parseInt(arr[2]) == year) {
                        billbymonth.add(mybills.get(i));
                    }

                }
            } else {
                tv_month_history.setText("Tháng ?");
                billbymonth.addAll(mybills);
            }
            if (billbymonth.size() > 0) {
                tv_empty.setVisibility(View.GONE);
                lv_history.setVisibility(View.VISIBLE);
            } else {
                tv_empty.setVisibility(View.VISIBLE);
                lv_history.setVisibility(View.GONE);
            }


            historyAdapter.updatelist(billbymonth);
        }

    }


    private int filter_number = 0;

    private void showDialogFilter() {
        Dialog dialog = new Dialog(HistoryActivity.this);
        dialog.setContentView(R.layout.dialog_filter_history);
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView status1, status2, status3, status4, status5, status6;
        status1 = dialog.findViewById(R.id.status1);
        status2 = dialog.findViewById(R.id.status2);
        status3 = dialog.findViewById(R.id.status3);
        status4 = dialog.findViewById(R.id.status4);
        status5 = dialog.findViewById(R.id.status5);
        status6 = dialog.findViewById(R.id.status6);
        TextView filter_history_cancel, filter_history_ok;
        filter_history_cancel = dialog.findViewById(R.id.filter_history_cancel);
        filter_history_ok = dialog.findViewById(R.id.filter_history_ok);
        loadThemeFilterStatus(numberStatus, status1, status2, status3, status4, status5, status6);
        status1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 1;
                loadThemeFilterStatus(1, status1, status2, status3, status4, status5, status6);
            }
        });
        status2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 2;
                loadThemeFilterStatus(2, status1, status2, status3, status4, status5, status6);
            }
        });
        status3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 3;
                loadThemeFilterStatus(3, status1, status2, status3, status4, status5, status6);
            }
        });
        status4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 4;
                loadThemeFilterStatus(4, status1, status2, status3, status4, status5, status6);
            }
        });
        status5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 5;
                loadThemeFilterStatus(5, status1, status2, status3, status4, status5, status6);
            }
        });
        status6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_number = 6;
                loadThemeFilterStatus(6, status1, status2, status3, status4, status5, status6);
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
                loadThemeFilterStatus(numberStatus, status1, status2, status3, status4, status5, status6);
                loadTitleStatus(numberStatus);

                setListBill(numberStatus, bills);

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public static void Update() {

    }

    private void loadTitleStatus(int i) {
        if (i == 1) {
            tv_status_history.setText("Chờ Xác Nhận");
            tv_status_history.setTextColor(Color.parseColor("#FF0000"));
        } else if (i == 2) {
            tv_status_history.setText("Đang Chờ Xử Lý");
            tv_status_history.setTextColor(Color.parseColor("#ff9800"));
        } else if (i == 3) {
            tv_status_history.setText("Đang Giao");
            tv_status_history.setTextColor(Color.parseColor("#4caf50"));
        } else if (i == 4) {
            tv_status_history.setText("Hoàn Thành");
            tv_status_history.setTextColor(Color.parseColor("#4caf50"));
        } else if (i == 5) {
            tv_status_history.setText("Thất Bại");
            tv_status_history.setTextColor(Color.parseColor("#FF0000"));
        } else if (i == 6) {
            tv_status_history.setText("Tất cả");
            tv_status_history.setTextColor(Color.parseColor("#4caf50"));
        }
    }

    private void loadThemeFilterStatus(int i, TextView st1, TextView st2, TextView st3, TextView st4, TextView st5, TextView st6) {
        if (i == 1) {
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
            st6.setTextColor(Color.parseColor("#000000"));
            st6.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        } else if (i == 2) {
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
            st6.setTextColor(Color.parseColor("#000000"));
            st6.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        } else if (i == 3) {
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
            st6.setTextColor(Color.parseColor("#000000"));
            st6.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        } else if (i == 4) {
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
            st6.setTextColor(Color.parseColor("#000000"));
            st6.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        } else if (i == 5) {
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
            st6.setTextColor(Color.parseColor("#000000"));
            st6.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        } else if (i == 6) {
            st6.setTextColor(Color.parseColor("#FFFFFF"));
            st6.setBackground(getDrawable(R.drawable.bt_filter_history_select));
            st1.setTextColor(Color.parseColor("#000000"));
            st1.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st2.setTextColor(Color.parseColor("#000000"));
            st2.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st3.setTextColor(Color.parseColor("#000000"));
            st3.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st4.setTextColor(Color.parseColor("#000000"));
            st4.setBackground(getDrawable(R.drawable.bt_item_filter_history));
            st5.setTextColor(Color.parseColor("#000000"));
            st5.setBackground(getDrawable(R.drawable.bt_item_filter_history));
        }


    }


}