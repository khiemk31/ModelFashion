package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Adapter.ViewPagerDetailProductAdapter;
import com.example.modelfashion.AmimationView.CircleAnimationUtil;
import com.example.modelfashion.Model.response.MyProductDetail;
import com.example.modelfashion.Model.response.my_product.Sizes;
import com.example.modelfashion.R;
import com.example.modelfashion.database.AppDatabase;
import com.example.modelfashion.database.MyProductCart;
import com.example.modelfashion.network.Repository;
import com.example.modelfashion.rx.RxBus;
import com.example.modelfashion.rx.RxEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator3;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView img_back, img_cart, img_prev, img_next, img_product,
            img_size_s, img_size_m, img_size_l, img_size_xl,img_product_sub;
    private TextView tv_price, tv_product_name, tv_product_category, tv_product_availability,
            btn_mua_ngay, btn_them_vao_gio_hang, tv_price_discount;
    private ViewPager2 viewPager;
    private ViewPagerDetailProductAdapter adapter;
    private Repository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ProgressBar progressBar;
    private CircleIndicator3 ci_detail_fm;

    ArrayList<Sizes> arr_size = new ArrayList<>();
    String size_id, price;
    String user_id = "";
    String productId = "";
    String productName = "";
    private int index = 0;
    private TextView tv_discount_sale;

    private MyProductDetail myProductDetail;
    private String size = "not specified";

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
                    showProgressBar(progressBar);
                }).subscribe(myProductDetail -> {
                    this.myProductDetail = myProductDetail;
                    hideProgressBar(progressBar);
                    setData(myProductDetail);
                }, throwable -> {
                    hideProgressBar(progressBar);
                }));
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData(MyProductDetail myProductDetail) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        List<String> images = new ArrayList<>();
        images.add(myProductDetail.getProduct().get(0).getProductBgr1());
        images.add(myProductDetail.getProduct().get(0).getProductBgr2());
        images.add(myProductDetail.getProduct().get(0).getProductBgr3());
        adapter.setArrItem(images);
        ci_detail_fm.setViewPager(viewPager);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CountDownTimer timer = new CountDownTimer(12000,3000) {
                    @Override
                    public void onTick(long l) {

                        index = viewPager.getCurrentItem()+1;
                        if (index==3){
                            viewPager.setCurrentItem(0);
                        }else {
                            viewPager.setCurrentItem(index);
                        }

                    }

                    @Override
                    public void onFinish() {
                        start();
                    }
                }.start();
            }
        },1000);

        price = format.format(myProductDetail.getProduct().get(0).getPrice());
        tv_product_name.setText(myProductDetail.getProduct().get(0).getProductName());



        if (myProductDetail.getProduct().get(0).getDiscount() == 0) {
            tv_price_discount.setVisibility(View.GONE);
            tv_price.setText(price+"đ");
            findViewById(R.id.rl_discount).setVisibility(View.INVISIBLE);
        } else {
            int discountPrice = myProductDetail.getProduct().get(0).getPrice() - ((myProductDetail.getProduct().get(0).getPrice() * myProductDetail.getProduct().get(0).getDiscount()) / 100);
            tv_price.setText(format.format(discountPrice)  + " đ");
            tv_price_discount.setText(price+" đ");
            tv_discount_sale.setText(myProductDetail.getProduct().get(0).getDiscount()+"%");
        }


        for (int i = 0; i < myProductDetail.getListSize().size(); i++) {
            if (myProductDetail.getListSize().get(i).getQuantity() > 0) {
                tv_product_availability.setText("Tình trạng: còn hàng");
                break;
            }
        }

        Glide.with(this).load(myProductDetail.getProduct().get(0).getProductImage()).placeholder(R.drawable.test_img2).into(img_product);
        Glide.with(this).load(myProductDetail.getProduct().get(0).getProductImage()).placeholder(R.drawable.test_img2).into(img_product_sub);

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
            size = "S";
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s_red).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);


        });
        img_size_m.setOnClickListener(view -> {
            // TODO SIZE M
            size = "M";
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m_red).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);


        });
        img_size_l.setOnClickListener(view -> {
            // TODO SIZE L
            size = "L";
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l_red).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);

        });
        img_size_xl.setOnClickListener(view -> {
            // TODO SIZE XL
            size = "XL";
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl_z).into(img_size_xl);


        });

//        tv_price.setText("650,000 VND");
//        tv_product_name.setText("MARU BLAZER (WHITE)");
////        tv_product_category.setText("Loại sản phẩm: MARU BLAZER");
//        tv_product_availability.setText("Tình trạng: còn hàng");




        btn_mua_ngay.setOnClickListener(view -> {
            int priceSale;
            if (myProductDetail.getProduct().get(0).getDiscount() == 0) {
                priceSale = myProductDetail.getProduct().get(0).getPrice();
            } else {
                priceSale = (int) (myProductDetail.getProduct().get(0).getPrice() * (1 - (myProductDetail.getProduct().get(0).getDiscount() / 100f)));
            }
            MyProductCart myProductCart = new MyProductCart(
                    myProductDetail.getProduct().get(0).getProductId(),
                    myProductDetail.getProduct().get(0).getProductName(),
                    myProductDetail.getProduct().get(0).getPrice(),
                    size,
                    1,
                    myProductDetail.getProduct().get(0).getProductImage(),
                    priceSale,
                    myProductDetail.getProduct().get(0).getDiscount()
            );
            // them vao gio hang + sang man gio hang
            if (isExist(myProductCart)) {
                // sang man gio hang
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                intent.putExtra("use_id", user_id);
                startActivity(intent);
            } else {
                if (!(Objects.equals(size, "not specified"))){
                    insertProduct(myProductCart);
                    // sang man gio hang
                    Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                    intent.putExtra("use_id", user_id);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Bạn chưa chọn size cho sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_them_vao_gio_hang.setOnClickListener(view -> {
            int priceSale;
            if (myProductDetail.getProduct().get(0).getDiscount() == 0) {
                priceSale = myProductDetail.getProduct().get(0).getPrice();
            } else {
                priceSale = (int) (myProductDetail.getProduct().get(0).getPrice() * (1 - (myProductDetail.getProduct().get(0).getDiscount() / 100f)));
            }

            MyProductCart myProductCart = new MyProductCart(
                    myProductDetail.getProduct().get(0).getProductId(),
                    myProductDetail.getProduct().get(0).getProductName(),
                    myProductDetail.getProduct().get(0).getPrice(),
                    size,
                    1,
                    myProductDetail.getProduct().get(0).getProductImage(),
                    priceSale,
                    myProductDetail.getProduct().get(0).getDiscount()
            );
            // them vao gio hang
            if (isExist(myProductCart)) {
                Toast.makeText(this, "Trong giỏ hàng đã có sản phẩm này rồi", Toast.LENGTH_SHORT).show();
            } else {
                if (!(Objects.equals(size, "not specified"))){
                    makeFlyAnimation(img_product_sub,myProductCart);
                } else
                    Toast.makeText(this, "Bạn chưa chọn size cho sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void makeFlyAnimation(ImageView targetView,MyProductCart myProductCart) {

        ImageView destView = (ImageView) findViewById(R.id.img_cart);

        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(1000).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                showProgressBar(progressBar);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                insertProduct(myProductCart);
                hideProgressBar(progressBar);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();


    }

    private void insertProduct(MyProductCart myProductCart) {
        disposable.add(AppDatabase.getInstance(this).cartDao().insertProductToCart(myProductCart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(disposable1 -> {
            btn_mua_ngay.setEnabled(false);
            btn_them_vao_gio_hang.setEnabled(false);
        }).doFinally(() -> {}).subscribe(aLong -> {
                    Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    btn_mua_ngay.setEnabled(true);
                    btn_them_vao_gio_hang.setEnabled(true);
                    RxBus.publish(RxEvent.addItemToCart);
                },throwable -> {
                    btn_mua_ngay.setEnabled(true);
                    btn_them_vao_gio_hang.setEnabled(true);
                }));
    }

    private boolean isExist(MyProductCart myProductCart) {
        int count = AppDatabase.getInstance(this).cartDao().countProduct(myProductCart.getProductId(), myProductCart.getProductSize());
        if (count == 0) {
            return false;
        } else return true;
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
        ci_detail_fm = findViewById(R.id.cir_3);
        tv_discount_sale = findViewById(R.id.tv_discount_sale);
        img_product_sub = findViewById(R.id.img_product_sub);


        tv_price = findViewById(R.id.tv_price);
        tv_price_discount = findViewById(R.id.tv_price_discount);
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

    public static String formatString(int number) {
        DecimalFormat df = new DecimalFormat(",###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        return df.format(number);
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