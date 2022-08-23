package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.modelfashion.Adapter.AllSaleAdapter;
import com.example.modelfashion.Adapter.see_all.PageAdapter;
import com.example.modelfashion.Fragment.MainFragment;
import com.example.modelfashion.History.ApiHistory.ApiHistory;
import com.example.modelfashion.Model.sale.ProductSale;
import com.example.modelfashion.Model.sale.SaleModel;
import com.example.modelfashion.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.transform.sax.SAXResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSaleActivity extends AppCompatActivity {
    private RecyclerView rcl_view_sale;
    private ArrayList<ProductSale> productSaleSearch = new ArrayList<>();
    private ArrayList<ProductSale> productSaleFilter = new ArrayList<>();
    private ArrayList<ProductSale> productSaleAll = new ArrayList<>();
    private String content = "";
    private EditText edt_search_sale;
    private ImageView filter_sale;
    private LinearLayout layout_filter_sale;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout ll_a_z,ll_all,ll_z_a,ll_500,ll_500_1000,ll_1000,ll_tall_low,ll_low_tall;
    AllSaleAdapter allSaleAdapter;
    private ImageView img_tick_1,img_tick_2,img_tick_3,img_tick_4,img_tick_5,img_tick_6,img_tick_7,img_tick_8;
    private SaleModel saleModel;
    private int page = 1;
    private ProgressBar progress_sale;
    private RecyclerView rcv_pages;
    private PageAdapter pageAdapter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        init();
        onClick();


    }
    private void init(){
        rcl_view_sale = findViewById(R.id.rcl_view_sale);
        edt_search_sale = findViewById(R.id.edt_search_sale);
        filter_sale = findViewById(R.id.filter_sale);
        layout_filter_sale = findViewById(R.id.layout_filter_sale);
        ll_all = findViewById(R.id.ll_all);
        ll_a_z = findViewById(R.id.ll_a_z);
        ll_z_a = findViewById(R.id.ll_z_a);
        ll_500 = findViewById(R.id.ll_500);
        ll_500_1000 = findViewById(R.id.ll_500_1000);
        ll_1000 = findViewById(R.id.ll_1000);
        ll_tall_low = findViewById(R.id.ll_tall_low);
        ll_low_tall = findViewById(R.id.ll_low_tall);
        img_tick_1=findViewById(R.id.img_tick_1);
        img_tick_2=findViewById(R.id.img_tick_2);
        img_tick_3=findViewById(R.id.img_tick_3);
        img_tick_4=findViewById(R.id.img_tick_4);
        img_tick_5=findViewById(R.id.img_tick_5);
        img_tick_6=findViewById(R.id.img_tick_6);
        img_tick_7=findViewById(R.id.img_tick_7);
        img_tick_8=findViewById(R.id.img_tick_8);
        refreshLayout = findViewById(R.id.refresh_layout);
        progress_sale = findViewById(R.id.progress_sale);
        progress_sale.setVisibility(View.GONE);
        rcv_pages = findViewById(R.id.rcv_pages);
        pageAdapter = new PageAdapter();
        rcv_pages.setAdapter(pageAdapter);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            edt_search_sale.setText("");
            getListByFilter(10000,10000000,"DESC","ASC",page);

        });

        pageAdapter.setOnItemClickListener(position -> {
            page = position + 1;
            pageAdapter.setColor(position);
            getListByFilter(10000,10000000,"DESC","ASC",page);
        });

        getListByFilter(10000,10000000,"DESC","ASC",page);
        bottomSheetBehavior = BottomSheetBehavior.from(layout_filter_sale);

        allSaleAdapter = new AllSaleAdapter(ViewSaleActivity.this, productSaleAll);
        rcl_view_sale.setAdapter(allSaleAdapter);
        edt_search_sale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                content = s.toString().trim().toLowerCase();
                SearchSaleAsyncTask searchSaleAsyncTask = new SearchSaleAsyncTask();
                searchSaleAsyncTask.execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        findViewById(R.id.back_view_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edt_search_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

    }

    private void loadSearch(){
        allSaleAdapter.setList(productSaleSearch);
    }
    private void getListSearch(){
        productSaleSearch.clear();
        for (int i=0;i<productSaleAll.size();i++){
            if(productSaleAll.get(i).getProduct_name().toLowerCase().contains(content.toLowerCase())){
                productSaleSearch.add(productSaleAll.get(i));
            }
        }

    }
    private void hidekeyboard(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getListByFilter(int price1,int price2,String sortPrice,String sortDiscount,int pageNumber){

        ApiHistory.API_HISTORY.getProductSaleByCategory(price1,price2,sortPrice,sortDiscount,pageNumber).enqueue(new Callback<SaleModel>() {
            @Override
            public void onResponse(Call<SaleModel> call, Response<SaleModel> response) {
                if (response.body()!=null){
                    if (response.body().getListProduct().size()>0) {
                        saleModel = response.body();
                        pageAdapter.setPageCount(saleModel.getTotalPage());
                        setListFilter();
                    }else {
                        Toast.makeText(ViewSaleActivity.this,"Không tìm thấy sản phẩm phù hợp",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<SaleModel> call, Throwable t) {

            }
        });
    }

    private void setListFilter(){
        progress_sale.setVisibility(View.VISIBLE);
        rcl_view_sale.setVisibility(View.INVISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(saleModel.getListProduct().size()>0){
                    allSaleAdapter = new AllSaleAdapter(ViewSaleActivity.this, saleModel.getListProduct());
                    rcl_view_sale.setAdapter(allSaleAdapter);
                    productSaleAll.addAll(saleModel.getListProduct());
                }else {
                    Toast.makeText(ViewSaleActivity.this,"Không tìm thấy sản phẩm phù hợp",Toast.LENGTH_SHORT).show();
                }
                progress_sale.setVisibility(View.GONE);
                rcl_view_sale.setVisibility(View.VISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        },1000);

    }

    private void onClick(){

        filter_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hidekeyboard();
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }
        });
        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListByFilter(10000,10000000,"DESC","ASC",page);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                loadFilter(1);


            }
        });

        ll_a_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allSaleAdapter.sortAToZ();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                loadFilter(2);

            }
        });

        ll_z_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allSaleAdapter.sortZToA();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                loadFilter(3);

            }
        });
        ll_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getListByFilter(10000,500000,"DESC","ASC",page);
                loadFilter(4);
            }
        });
        ll_500_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getListByFilter(500000,1000000,"DESC","ASC",page);
                loadFilter(5);
            }
        });
        ll_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getListByFilter(1000000,10000000,"DESC","ASC",page);
                loadFilter(6);


            }
        });
        ll_tall_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getListByFilter(10000,10000000,"DESC","ASC",page);
                loadFilter(8);

            }
        });
        ll_low_tall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getListByFilter(10000,10000000,"ASC","ASC",page);
                loadFilter(7);

            }
        });
    }

    private int getPrice(int price , int discount){
        int p =price-(price*discount/100);

        return p;
    }


    private void loadFilter(int i){
        if(i==1){
            img_tick_1.setVisibility(View.VISIBLE);
            img_tick_2.setVisibility(View.GONE);
            img_tick_3.setVisibility(View.GONE);
            img_tick_4.setVisibility(View.GONE);
            img_tick_5.setVisibility(View.GONE);
            img_tick_6.setVisibility(View.GONE);
            img_tick_7.setVisibility(View.GONE);
            img_tick_8.setVisibility(View.GONE);

        }else if (i==2){
            img_tick_1.setVisibility(View.GONE);
            img_tick_2.setVisibility(View.VISIBLE);
            img_tick_3.setVisibility(View.GONE);
            img_tick_4.setVisibility(View.GONE);
            img_tick_5.setVisibility(View.GONE);
            img_tick_6.setVisibility(View.GONE);
            img_tick_7.setVisibility(View.GONE);
            img_tick_8.setVisibility(View.GONE);
        }else if (i==3){
            img_tick_1.setVisibility(View.GONE);
            img_tick_2.setVisibility(View.GONE);
            img_tick_3.setVisibility(View.VISIBLE);
            img_tick_4.setVisibility(View.GONE);
            img_tick_5.setVisibility(View.GONE);
            img_tick_6.setVisibility(View.GONE);
            img_tick_7.setVisibility(View.GONE);
            img_tick_8.setVisibility(View.GONE);
        }else if (i==4){
            img_tick_1.setVisibility(View.GONE);
            img_tick_2.setVisibility(View.GONE);
            img_tick_3.setVisibility(View.GONE);
            img_tick_4.setVisibility(View.VISIBLE);
            img_tick_5.setVisibility(View.GONE);
            img_tick_6.setVisibility(View.GONE);
            img_tick_7.setVisibility(View.GONE);
            img_tick_8.setVisibility(View.GONE);
        }else if (i==5){
            img_tick_1.setVisibility(View.GONE);
            img_tick_2.setVisibility(View.GONE);
            img_tick_3.setVisibility(View.GONE);
            img_tick_4.setVisibility(View.GONE);
            img_tick_5.setVisibility(View.VISIBLE);
            img_tick_6.setVisibility(View.GONE);
            img_tick_7.setVisibility(View.GONE);
            img_tick_8.setVisibility(View.GONE);
        }else if (i==6){
            img_tick_1.setVisibility(View.GONE);
            img_tick_2.setVisibility(View.GONE);
            img_tick_3.setVisibility(View.GONE);
            img_tick_4.setVisibility(View.GONE);
            img_tick_5.setVisibility(View.GONE);
            img_tick_6.setVisibility(View.VISIBLE);
            img_tick_7.setVisibility(View.GONE);
            img_tick_8.setVisibility(View.GONE);
        }else if (i==7){
            img_tick_1.setVisibility(View.GONE);
            img_tick_2.setVisibility(View.GONE);
            img_tick_3.setVisibility(View.GONE);
            img_tick_4.setVisibility(View.GONE);
            img_tick_5.setVisibility(View.GONE);
            img_tick_6.setVisibility(View.GONE);
            img_tick_7.setVisibility(View.VISIBLE);
            img_tick_8.setVisibility(View.GONE);
        }else if (i==8){
            img_tick_1.setVisibility(View.GONE);
            img_tick_2.setVisibility(View.GONE);
            img_tick_3.setVisibility(View.GONE);
            img_tick_4.setVisibility(View.GONE);
            img_tick_5.setVisibility(View.GONE);
            img_tick_6.setVisibility(View.GONE);
            img_tick_7.setVisibility(View.GONE);
            img_tick_8.setVisibility(View.VISIBLE);
        }
    }



    public class SearchSaleAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getListSearch();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            loadSearch();
        }
    }
}