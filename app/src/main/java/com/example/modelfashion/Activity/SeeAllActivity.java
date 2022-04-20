package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_TYPE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.modelfashion.Adapter.see_all.ProductAdapter;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.SearchBar;
import com.example.modelfashion.network.Repository;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class SeeAllActivity extends AppCompatActivity {

    private ImageView imgBack;
    private SearchBar searchBar;
    private RecyclerView rcv;
    private ProgressBar progressBar;
    private String type;
    private SwipeRefreshLayout refreshLayout;
    private final ArrayList<MyProduct> productArrayList = new ArrayList<>();

    private ProductAdapter adapter;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        type = getIntent().getStringExtra(KEY_PRODUCT_TYPE);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        adapter.setClickListener(new ProductAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, MyProduct productPreview) {
                Intent intent = new Intent(SeeAllActivity.this, ProductDetailActivity.class);
                intent.putExtra(KEY_PRODUCT_ID, productPreview.getId());
                intent.putExtra(KEY_PRODUCT_NAME, productPreview.getProduct_name());
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(int position, MyProduct productPreview) {
                Log.d("vcxvcx", "onAddToCartClick: position" + position + " product: " + productPreview.toString());
            }
        });

        searchBar.onSearchBarClick(new SearchBar.SearchListener() {
            @Override
            public void onClearClick() {
                getProductByCategory(repository, type);
            }

            @Override
            public void afterTextChanged(String content) {
                ArrayList<MyProduct> listSearch = new ArrayList<>();
                for (int i = 0; i < productArrayList.size(); i++) {
                    if (productArrayList.get(i).getProduct_name().toLowerCase().contains(content.toLowerCase())) {
                        listSearch.add(productArrayList.get(i));
                    }
                }
                adapter.setListProduct(listSearch);
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            getProductByCategory(repository, type);
            progressBar.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
            searchBar.clearSearchContent();
        });

        imgBack.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void initView() {
        imgBack = findViewById(R.id.img_back);
        searchBar = findViewById(R.id.search_bar);
        rcv = findViewById(R.id.rcv_product);
        progressBar = findViewById(R.id.progress_bar);
        refreshLayout = findViewById(R.id.refresh_layout);

        repository = new Repository(this);
        adapter = new ProductAdapter();
        rcv.setAdapter(adapter);
    }

    private void initData() {
        getProductByCategory(repository, type);
    }

    private void getProductByCategory(Repository repository, String type) {
        compositeDisposable.add(repository.getProductByType(type).doOnSubscribe(disposable -> {
            // show loading
            progressBar.setVisibility(View.VISIBLE);
        })
                .doFinally(() -> {
                    // hide loading
                    progressBar.setVisibility(View.GONE);
                })
                .subscribe(productResponse -> {
                    productArrayList.clear();
                    adapter.setListProduct(productResponse);
                    productArrayList.addAll(productResponse);
                }, throwable -> {
                    Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
                }));
    }
}