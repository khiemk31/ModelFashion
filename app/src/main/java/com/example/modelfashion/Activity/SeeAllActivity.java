package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_TYPE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.modelfashion.Adapter.see_all.ProductAdapter;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.customview.SearchBar;
import com.example.modelfashion.customview.SpacesItemDecoration;
import com.example.modelfashion.network.Repository;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class SeeAllActivity extends AppCompatActivity {

    private SearchBar searchBar;
    private RecyclerView rcv;
    private ProgressBar progressBar;
    private int categoryId;
    private SwipeRefreshLayout refreshLayout;

    private ProductAdapter adapter;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Repository repository;

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
//        adapter.setClickListener(new ProductAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(int position, MyProductByCategory productPreview) {
//                Intent intent = new Intent(SeeAllActivity.this, ProductDetailActivity.class);
//                intent.putExtra(KEY_PRODUCT_ID, productPreview.getProductId());
//                intent.putExtra(KEY_PRODUCT_NAME, productPreview.getProductName());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onAddToCartClick(int position, MyProductByCategory productPreview) {
//
//            }
//        });

        searchBar.onSearchBarClick(new SearchBar.SearchListener() {
            @Override
            public void onClearClick() {

            }

            @Override
            public void afterTextChanged(String content) {

            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            showProgressBar(progressBar);
            refreshLayout.setRefreshing(false);
            searchBar.clearSearchContent();
            getProductByCategory();
        });

    }

    private void initView() {
        searchBar = findViewById(R.id.search_bar);
        rcv = findViewById(R.id.rcv_product_of_category);
        progressBar = findViewById(R.id.progress_bar);
        refreshLayout = findViewById(R.id.refresh_layout);

        repository = new Repository(this);
        adapter = new ProductAdapter();
        rcv.setAdapter(adapter);
        rcv.addItemDecoration(new SpacesItemDecoration(20));
    }

    private void initData() {
        getProductByCategory();
    }

    private void getProductByCategory() {
        compositeDisposable.add(repository.getProductByCategory(categoryId,
                        0,
                        1000000,
                        "DESC",
                        "DESC",
                        1)
                .doOnSubscribe(disposable -> {
                    showProgressBar(progressBar);
                })
                .doFinally(() -> {
                })
                .subscribe(productResponse -> {
                    adapter.addItems(productResponse.getListProduct());
                    hideProgressBar(progressBar);

                }, throwable -> {
                    hideProgressBar(progressBar);
                    Toast.makeText(this, new Utils().getErrorBody(throwable).toString(), Toast.LENGTH_SHORT).show();
                }));
    }

    private Boolean disableLoadMore;
    private Boolean isLoading;
    private void setupRcv() {
        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = 0;
                    int lastVisibleItemPosition = 0;

                    if (disableLoadMore || isLoading) {
                        return;
                    }

                    RecyclerView.LayoutManager layoutManager = rcv.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }
                    if (firstVisibleItemPosition > 0 && lastVisibleItemPosition == (adapter.getItemCount() - 1)) {
                        isLoading = true;
                        getProductByCategory();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}