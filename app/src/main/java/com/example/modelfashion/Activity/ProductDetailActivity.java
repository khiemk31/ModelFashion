package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.R;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView img_cover, img_back, img_cart, img_prev, img_next, img_product,
            img_size_s, img_size_m, img_size_l, img_size_xl;
    private TextView tv_price, tv_product_name, tv_product_category, tv_product_availability,
            btn_mua_ngay, btn_them_vao_gio_hang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initView();
        initData();
        initListener();
    }

    private void initData() {

    }

    private void initListener() {
        loadImageUrl("https://cf.shopee.vn/file/7624d506af9460c8f4e8e5a80c30b514",img_cover);
        img_back.setOnClickListener(view -> {
            finish();
        });
        img_cart.setOnClickListener(view -> {
            // TODO CART
        });
        img_prev.setOnClickListener(view -> {
            // TODO PREV
        });
        img_next.setOnClickListener(view -> {
            // TODO NEXT
        });
        loadImageUrl("https://cf.shopee.vn/file/7624d506af9460c8f4e8e5a80c30b514",img_product);
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
        img_cover = findViewById(R.id.img_cover);
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
    }

    private void loadImageUrl(String url, ImageView img){
        Glide.with(this).load(url).placeholder(R.drawable.logo_icon).into(img);
    }
}