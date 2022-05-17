package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Adapter.ViewPagerDetailProductAdapter;
import com.example.modelfashion.Model.response.MyProductDetail;
import com.example.modelfashion.Model.response.my_product.Sizes;
import com.example.modelfashion.R;
import com.example.modelfashion.network.Repository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView img_back, img_cart, img_prev, img_next, img_product,
            img_size_s, img_size_m, img_size_l, img_size_xl;
    private TextView tv_price, tv_product_name, tv_product_category, tv_product_availability,
            btn_mua_ngay, btn_them_vao_gio_hang;
    private ViewPager2 viewPager;
    private ViewPagerDetailProductAdapter adapter;
    private Repository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ProgressBar progressBar;

    ArrayList<Sizes> arr_size = new ArrayList<>();
    String size_id, price;
    String user_id = "";
    String productId = "";
    String productName = "";
    private MyProductDetail myProductDetail = new MyProductDetail();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        productName = intent.getStringExtra(KEY_PRODUCT_NAME);
        productId = intent.getStringExtra(KEY_PRODUCT_ID);
        Log.e("t1", user_id + " ");

        repository = new Repository(this);

        initView();
        initData();
        initListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        disposable.add(
                repository.getProductDetail(productId).doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                }).subscribe(myProductDetail -> {
                    this.myProductDetail = myProductDetail;
                    progressBar.setVisibility(View.GONE);
                    setData(myProductDetail);
                    Log.d("ahuhu", "getProductDetail: success");
                }, throwable -> {
                    Log.d("ahuhu", "getProductDetail: error: " + throwable.toString());
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData(MyProductDetail myProductDetail) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        List<String> images = new ArrayList<>();
        images.add(myProductDetail.getListImage().get(0).getImage1());
        images.add(myProductDetail.getListImage().get(0).getImage2());
        images.add(myProductDetail.getListImage().get(0).getImage3());
        adapter.setArrItem(images);
        price = format.format(myProductDetail.getProduct().get(0).getPrice());
        tv_product_name.setText(myProductDetail.getProduct().get(0).getProductName());
        tv_price.setText(String.valueOf(myProductDetail.getProduct().get(0).getPrice()) + " VNĐ");
//        tv_product_category.setText("Loại sản phẩm: " );

        Glide.with(this).load(myProductDetail.getProduct().get(0).getProductImage()).placeholder(R.drawable.test_img2).into(img_product);
    }

    private int currentCoverImage = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initListener() {
        img_back.setOnClickListener(view -> {
            finish();
        });
        img_cart.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            intent.putExtra("use_id", user_id);
            startActivity(intent);
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
        img_size_s.setOnClickListener(view -> {
            // TODO SIZE S

            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s_red).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);


        });
        img_size_m.setOnClickListener(view -> {
            // TODO SIZE M
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m_red).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);


        });
        img_size_l.setOnClickListener(view -> {
            // TODO SIZE L
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l_red).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);

        });
        img_size_xl.setOnClickListener(view -> {
            // TODO SIZE XL
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl_z).into(img_size_xl);


        });

        tv_price.setText("650,000 VND");
        tv_product_name.setText("MARU BLAZER (WHITE)");
//        tv_product_category.setText("Loại sản phẩm: MARU BLAZER");
        tv_product_availability.setText("Tình trạng: còn hàng");

        btn_mua_ngay.setOnClickListener(view -> {

        });
        btn_them_vao_gio_hang.setOnClickListener(view -> {

        });
    }


    private void initView() {
        progressBar = findViewById(R.id.progress_bar);
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
//        tv_product_category = findViewById(R.id.tv_product_category);
        tv_product_availability = findViewById(R.id.tv_product_availability);
        btn_mua_ngay = findViewById(R.id.btn_mua_ngay);
        btn_them_vao_gio_hang = findViewById(R.id.btn_them_vao_gio_hang);

        adapter = new ViewPagerDetailProductAdapter();

        viewPager.setAdapter(adapter);
    }


    private void loadImageUrl(String url, ImageView img) {
        Glide.with(this).load(url).placeholder(R.drawable.ic_logo).into(img);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void showDialogLogIn() {
        Dialog dialog = new Dialog(ProductDetailActivity.this);
        dialog.setContentView(R.layout.dialog_quest_login);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_yes_login, tv_no_login;
        tv_yes_login = dialog.findViewById(R.id.tv_yes_login);
        tv_no_login = dialog.findViewById(R.id.tv_no_login);
        tv_no_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, SignInActivity.class));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}