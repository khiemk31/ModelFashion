package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Adapter.ViewPagerDetailProductAdapter;
import com.example.modelfashion.R;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView img_back, img_cart, img_prev, img_next, img_product,
            img_size_s, img_size_m, img_size_l, img_size_xl;
    private TextView tv_price, tv_product_name, tv_product_category, tv_product_availability,
            btn_mua_ngay, btn_them_vao_gio_hang;
    private ViewPager2 viewPager;
    private ViewPagerDetailProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra(KEY_PRODUCT_ID);
        // TODO use id to call detail product api

        initView();
        initData();
        initListener();
    }

    private void initData() {

    }

    private int currentCoverImage = 0;

    private void initListener() {
        img_back.setOnClickListener(view -> {
            finish();
        });
        img_cart.setOnClickListener(view -> {
            // TODO CART
        });
        img_prev.setOnClickListener(view -> {
            // TODO PREV
            if (viewPager.getCurrentItem() == 0) {
                currentCoverImage = adapter.getItemCount() - 1;
            } else {
                currentCoverImage = currentCoverImage - 1;
            }
            viewPager.setCurrentItem(currentCoverImage);
        });
        img_next.setOnClickListener(view -> {
            // TODO NEXT
            if (viewPager.getCurrentItem() == (adapter.getItemCount() - 1)) {
                currentCoverImage = 0;
            } else {
                currentCoverImage = currentCoverImage + 1;
            }
            viewPager.setCurrentItem(currentCoverImage);
        });
        loadImageUrl("https://cf.shopee.vn/file/7624d506af9460c8f4e8e5a80c30b514", img_product);
        img_size_s.setOnClickListener(view -> {
            // TODO SIZE S
        });
        img_size_m.setOnClickListener(view -> {
            // TODO SIZE M
        });
        img_size_l.setOnClickListener(view -> {
            // TODO SIZE L
        });
        img_size_xl.setOnClickListener(view -> {
            // TODO SIZE XL
        });

        tv_price.setText("650,000 VND");
        tv_product_name.setText("MARU BLAZER (WHITE)");
        tv_product_category.setText("Loại sản phẩm: MARU BLAZER");
        tv_product_availability.setText("Tình trạng: còn hàng");

        btn_mua_ngay.setOnClickListener(view -> {
            // TODO BUY
        });
        btn_them_vao_gio_hang.setOnClickListener(view -> {
            // TODO ADD ON CART
        });
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        img_back = findViewById(R.id.img_back);
        img_cart = findViewById(R.id.img_cart);
        img_prev = findViewById(R.id.img_prev);
        img_next = findViewById(R.id.img_next);
        img_product = findViewById(R.id.img_product);
        img_size_s = findViewById(R.id.img_size_s);
        img_size_m = findViewById(R.id.img_size_m);
        img_size_l = findViewById(R.id.img_size_l);
        img_size_xl = findViewById(R.id.img_size_xl);

        tv_price = findViewById(R.id.tv_price);
        tv_product_name = findViewById(R.id.tv_product_name);
        tv_product_category = findViewById(R.id.tv_product_category);
        tv_product_availability = findViewById(R.id.tv_product_availability);
        btn_mua_ngay = findViewById(R.id.btn_mua_ngay);
        btn_them_vao_gio_hang = findViewById(R.id.btn_them_vao_gio_hang);

        adapter = new ViewPagerDetailProductAdapter();
        adapter.setArrItem(listDetailImage());
        viewPager.setAdapter(adapter);
    }

    private ArrayList<String> listDetailImage() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://zunezx.com/upload/image/cache/data/banner/Tee/42BA878D-053A-4FA1-9444-1865E38690C4-a69-crop-550-550.jpeg");
        arrayList.add("https://zunezx.com/upload/image/cache/data/banner/Tee/CBF4903A-0C16-4F81-AEC5-4000A1D11120-470-crop-550-550.jpeg");
        arrayList.add("https://zunezx.com/upload/image/cache/data/banner/Tee/33DC813B-A4BF-4F91-8ADA-F2B0C85A225A-1e8-crop-550-550.jpeg");
        arrayList.add("https://zunezx.com/upload/image/data/banner/Tee/05D3F876-B270-4916-9FF0-FE48332E1DB3-da9.jpeg");
        return arrayList;
    }

    private void loadImageUrl(String url, ImageView img) {
        Glide.with(this).load(url).placeholder(R.drawable.logo_icon).into(img);
    }
}