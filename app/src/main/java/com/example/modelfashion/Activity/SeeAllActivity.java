package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_TYPE;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.modelfashion.Adapter.see_all.PageAdapter;
import com.example.modelfashion.Adapter.see_all.ProductAdapter;
import com.example.modelfashion.Model.response.main_screen.Product;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.customview.SearchBar;
import com.example.modelfashion.customview.SpacesItemDecoration;
import com.example.modelfashion.network.Repository;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SeeAllActivity extends AppCompatActivity {

    private SearchBar searchBar;
    private RecyclerView rcv;
    private RecyclerView rcv_pages;
    private ProgressBar progressBar;
    private int categoryId;
    private SwipeRefreshLayout refreshLayout;

    private ProductAdapter adapter;
    private PageAdapter pageAdapter;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Repository repository;


    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout layout_filter_sale;
    private LinearLayout ll_a_z, ll_z_a, ll_500, ll_500_1000, ll_1000, ll_low_tall, ll_tall_low, ll_all;
    private ImageView img_tick_1, img_tick_2, img_tick_3, img_tick_4, img_tick_5, img_tick_6, img_tick_7, img_tick_8;
    private List<ImageView> listTick = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

//        categoryId = getIntent().getStringExtra(KEY_PRODUCT_TYPE);
        categoryId = getIntent().getIntExtra(KEY_PRODUCT_TYPE, 0);

        initView();
        initData();
        initListener();
    }

    private void initListener() {


        pageAdapter.setOnItemClickListener(position -> {
            pageNumber = position + 1;
            pageAdapter.setColor(position);
            getProductByCategory();
        });

        adapter.setClickListener((position, productPreview) -> {
            Intent intent = new Intent(SeeAllActivity.this, ProductDetailActivity.class);
            intent.putExtra(KEY_PRODUCT_NAME, productPreview.getProductName());
            intent.putExtra(KEY_PRODUCT_ID, productPreview.getProductId());
            startActivity(intent);
        });

        searchBar.onSearchBarClick(new SearchBar.SearchListener() {
            @Override
            public void onClearClick() {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(String content) {
                adapter.search(content);
            }

            @Override
            public void rightIconClick() {
                hidekeyboard();
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            showProgressBar(progressBar);
            refreshLayout.setRefreshing(false);
            searchBar.clearSearchContent();

            getProductByCategory();
            adapter.clearItems();
        });

        ll_all.setOnClickListener(v -> {
            removeAllTick();
            showTick(1);
            price1 = 0;
            price2 = 1000000000;
            getProductByCategory();
            hideBottom();
        });
        //        ll_a_z = findViewById(R.id.ll_a_z);
//        ll_z_a = findViewById(R.id.ll_z_a);
        ll_a_z.setOnClickListener(v -> {
            removeAllTick();
            showTick(2);
            adapter.sortAToZ();
            hideBottom();
        });
        ll_z_a.setOnClickListener(v -> {
            removeAllTick();
            showTick(3);
            adapter.sortZToA();
            hideBottom();
        });
        ll_500.setOnClickListener(v -> {
            removeAllTick();
            showTick(4);
            hideBottom();
            price1 = 0;
            price2 = 499999;
            getProductByCategory();
        });
        ll_500_1000.setOnClickListener(v -> {
            removeAllTick();
            showTick(5);
            hideBottom();
            price1 = 500000;
            price2 = 1000000;
            getProductByCategory();
        });
        ll_1000.setOnClickListener(v -> {
            removeAllTick();
            showTick(6);
            hideBottom();
            price1 = 1000001;
            price2 = 1000000000;
            getProductByCategory();
        });
        ll_low_tall.setOnClickListener(v -> {
            removeAllTick();
            showTick(7);
            hideBottom();
            sortPrice = "ASC";
            price1=0;price2=100000000;
            getProductByCategory();
        });
        ll_tall_low.setOnClickListener(v -> {
            removeAllTick();
            showTick(8);
            hideBottom();
            sortPrice = "DESC";
            price1=0;price2=100000000;
            getProductByCategory();
        });

    }

    private void initView() {
        searchBar = findViewById(R.id.search_bar);
        rcv = findViewById(R.id.rcv_product_of_category);
        rcv_pages = findViewById(R.id.rcv_pages);
        progressBar = findViewById(R.id.progress_bar);
        refreshLayout = findViewById(R.id.refresh_layout);

        repository = new Repository(this);
        adapter = new ProductAdapter();
        pageAdapter = new PageAdapter();
        rcv.setAdapter(adapter);
        rcv.addItemDecoration(new SpacesItemDecoration(20));

        rcv_pages.setAdapter(pageAdapter);


        layout_filter_sale = findViewById(R.id.layout_filter_sale);
        bottomSheetBehavior = BottomSheetBehavior.from(layout_filter_sale);

        ll_a_z = findViewById(R.id.ll_a_z);
        ll_z_a = findViewById(R.id.ll_z_a);
        ll_500 = findViewById(R.id.ll_500);
        ll_500_1000 = findViewById(R.id.ll_500_1000);
        ll_1000 = findViewById(R.id.ll_1000);
        ll_low_tall = findViewById(R.id.ll_low_tall);
        ll_tall_low = findViewById(R.id.ll_tall_low);
        ll_all = findViewById(R.id.ll_all);

        // img_tick_1, img_tick_2, img_tick_3, img_tick_4, img_tick_5, img_tick_6, img_tick_7, img_tick_8;
        img_tick_1 = findViewById(R.id.img_tick_1);   listTick.add(img_tick_1);
        img_tick_2 = findViewById(R.id.img_tick_2);   listTick.add(img_tick_2);
        img_tick_3 = findViewById(R.id.img_tick_3);   listTick.add(img_tick_3);
        img_tick_4 = findViewById(R.id.img_tick_4);   listTick.add(img_tick_4);
        img_tick_5 = findViewById(R.id.img_tick_5);   listTick.add(img_tick_5);
        img_tick_6 = findViewById(R.id.img_tick_6);   listTick.add(img_tick_6);
        img_tick_7 = findViewById(R.id.img_tick_7);   listTick.add(img_tick_7);
        img_tick_8 = findViewById(R.id.img_tick_8);   listTick.add(img_tick_8);

    }

    private void initData() {
        getProductByCategory();
    }

    private int pageNumber = 1;
    private int price1 = 0, price2 = 10000000;
    private String sortPrice = "DESC", sortDiscount = "DESC";

    private void getProductByCategory() {
        compositeDisposable.add(repository.getProductByCategory(categoryId,
                        price1,
                        price2,
                        sortPrice,
                        sortDiscount,
                        pageNumber)
                .doOnSubscribe(disposable -> {
                    showProgressBar(progressBar);
                })
                .doFinally(() -> {
                })
                .subscribe(productResponse -> {
                    adapter.setListProduct(productResponse.getListProduct());

                    pageAdapter.setPageCount(productResponse.getTotalPage());
                    hideProgressBar(progressBar);

                }, throwable -> {
                    hideProgressBar(progressBar);
                    Toast.makeText(this, new Utils().getErrorBody(throwable).toString(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void removeAllTick() {
        for (int i = 0; i < listTick.size(); i++) {
            listTick.get(i).setVisibility(View.GONE);
        }
    }

    private void showTick(int tick) {
        listTick.get(tick - 1).setVisibility(View.VISIBLE);
    }

    void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hidekeyboard(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void hideBottom() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
    private void expandBottom() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}