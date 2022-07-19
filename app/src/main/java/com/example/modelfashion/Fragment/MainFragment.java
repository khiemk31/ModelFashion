package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_CHECK_LOGIN;
import static com.example.modelfashion.Utility.Constants.KEY_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_TYPE;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Activity.NotifiActivity;
import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Activity.SeeAllActivity;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Activity.ViewSaleActivity;
import com.example.modelfashion.Adapter.ProductListAdapter;
import com.example.modelfashion.Adapter.ProductSaleAdapter;
import com.example.modelfashion.Adapter.VpSaleMainFmAdapter;
import com.example.modelfashion.Adapter.main_screen.ProductMainAdapter;
import com.example.modelfashion.History.ApiHistory.ApiHistory;
import com.example.modelfashion.Model.ItemSaleMain;
import com.example.modelfashion.Model.response.category.MyCategory;
import com.example.modelfashion.Model.response.main_screen.ProductMain;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.Model.sale.ProductSale;
import com.example.modelfashion.Model.sale.SaleModel;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.network.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {
    private ViewPager2 vpSaleMain;
    private CircleIndicator3 ciSale;
    private RecyclerView rcvProduct;
    Repository repository;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private ImageView avatar, img_notifi;
    private String user_id;
    ArrayList<ItemSaleMain> arrItem = new ArrayList<>();
    private TextView tvCurrentDate, tvGreeting;
    private PreferenceManager preferenceManager;
    RecyclerView rcl_product_sale;
    RelativeLayout rl_sale;
    ImageView icon_sale;
    TextView tv_viewall_sale;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    ProductListAdapter productListAdapter = new ProductListAdapter();
    public static ArrayList<ProductSale> productSales = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = vpSaleMain.getCurrentItem();
            if (currentPosition == arrItem.size() - 1) {
                vpSaleMain.setCurrentItem(0);
            } else
                vpSaleMain.setCurrentItem(currentPosition + 1);
        }
    };


    private int offset = 0;
    private int currentPage = 0;
    private int totalPage = 0;
    private Boolean canLoadMore = true;
    private RecyclerView rcv;

    private ProductMainAdapter productMainAdapter;

    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static MainFragment newInstance(String text) {

        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        vpSaleMain = view.findViewById(R.id.vp_sale_main_fm);
        ciSale = view.findViewById(R.id.ci_sale_main_fm);
        rcvProduct = view.findViewById(R.id.rv_men_page_fm);
        progressBar = view.findViewById(R.id.progress_bar);
        avatar = view.findViewById(R.id.img_user_avatar);
        img_notifi = view.findViewById(R.id.img_notifi);
        rcv = view.findViewById(R.id.rcv);
        tv_viewall_sale = view.findViewById(R.id.tv_viewall_sale);
        tv_viewall_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), ViewSaleActivity.class));
            }
        });
        repository = new Repository(requireContext());
        Bundle info = getArguments();
        user_id = info.getString("user_id");
//        try {
//            setUserAvatar(user_id);
//        } catch (Exception e) {
//        }

        productMainAdapter = new ProductMainAdapter();
        rcv.setAdapter(productMainAdapter);
        setUpRcvLoadData();

        tvCurrentDate = view.findViewById(R.id.tv_current_date);
        tvGreeting = view.findViewById(R.id.tv_greeting);
        preferenceManager = new PreferenceManager(requireContext());

        arrItem.add(new ItemSaleMain(R.drawable.banner1));
        arrItem.add(new ItemSaleMain(R.drawable.banner2));
        arrItem.add(new ItemSaleMain(R.drawable.banner3));
        VpSaleMainFmAdapter vpSaleMainFmAdapter = new VpSaleMainFmAdapter(arrItem);
        vpSaleMain.setAdapter(vpSaleMainFmAdapter);

        ciSale.setViewPager(vpSaleMain);

        vpSaleMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunable);
                mHandler.postDelayed(mRunable, 4000);
            }
        });
        initData();

        initHeader();
        initClickProfileAvatar();

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            showProgressBar(progressBar);
            getAllOffset(true);
            canLoadMore = true;
//            productListAdapter.clearAllData();
//            categoryList.clear();
//            getAllCategory();
        });

        img_notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), NotifiActivity.class));
            }
        });
        return view;
    }

    private void setUpRcvLoadData() {
        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView mRecyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = 0;
                    int lastVisibleItemPosition = 0;

                    RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }

                    if (firstVisibleItemPosition >= 0 && lastVisibleItemPosition == productMainAdapter.getItemCount() - 1) {
                        if (canLoadMore) {
                            getAllOffset(false);
                        }
                    }
                }


            }
        });
    }

    private void initClickProfileAvatar() {
        avatar.setOnClickListener(view -> {
            if (preferenceManager.getBoolean(KEY_CHECK_LOGIN)) {
                MainActivity.viewPager.setCurrentItem(3);
                MainActivity.navigationView.getMenu().getItem(3).setChecked(true);// dang nhap roi thi vao profile
            } else {
                startActivity(new Intent(requireContext(), SignInActivity.class)); // chua dang nhap thi vao dang nhap
            }
        });

    }

    private void getAllOffset(Boolean refresh) {
        if (refresh) {
            offset = 0;
            currentPage = 0;
        }
        compositeDisposable.add(repository.getAllProductOffset(offset)
                .doOnSubscribe(disposable -> {
                    showProgressBar(progressBar);
                }).subscribe(getAllResponse -> {
                    Log.d("ahihi", "getAllResponse: " + getAllResponse);
                    hideProgressBar(progressBar);
                    totalPage = getAllResponse.getTotalPage();
                    currentPage++;
                    offset += 10;
                    if (currentPage >= totalPage) {
                        canLoadMore = false;
                    }

                    // get data
                    if (!refresh){
                        productMainAdapter.addLoadMore(getAllResponse.getListProduct());
                    }else {
                        productMainAdapter.refreshList(getAllResponse.getListProduct());
                    }

                }, throwable -> {
                    Log.d("ahihi", "throwable: " + throwable.toString());
                    hideProgressBar(progressBar);
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                })
        );
    }

    private void initHeader() {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM yyyy");
        Calendar cal = Calendar.getInstance();

        tvCurrentDate.setText(dateFormat.format(cal.getTime()));

        if (cal.get(Calendar.HOUR_OF_DAY) < 12) {
            tvGreeting.setText("Chào buổi sáng");
        } else if (cal.get(Calendar.HOUR_OF_DAY) < 18) {
            tvGreeting.setText("Chào buổi chiều");
        } else {
            tvGreeting.setText("Chào buổi tối");
        }

        if (preferenceManager.getBoolean(KEY_CHECK_LOGIN)) {
            compositeDisposable.add(repository.getUserDetail(preferenceManager.getString(KEY_ID))
                    .subscribe(userDetailResponse -> {
                        Glide.with(requireContext()).load(userDetailResponse.getData().getAvatar()).placeholder(R.drawable.ic_profile).into(avatar);
                    }, throwable -> {
                        Log.d("ahuhu", "loadimage avatar mainfragment: error " + throwable.toString());
                    }));
        } else
            Glide.with(requireContext()).load("").placeholder(R.drawable.ic_profile).into(avatar);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {

        rcvProduct.setAdapter(productListAdapter);
        initListener();

        getAllOffset(false);
//        getAllCategory();

    }

    private final List<MyCategory> categoryList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllCategory() {
        compositeDisposable.add(repository.getAllCategory()
                .doOnSubscribe(disposable -> {
                    showProgressBar(progressBar);
                }).doFinally(() -> {
                }).subscribe(dataAllCategory -> {
                    hideProgressBar(progressBar);
                    if (dataAllCategory.getData().size() > 0) {
                        categoryList.addAll(dataAllCategory.getData());
//                        getAllProductByCategory();
                    }
                }, throwable -> {
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                    hideProgressBar(progressBar);
                }));
    }


    private void initListener() {
//        productListAdapter.onItemClickListener(new ProductListAdapter.OnItemClickListener() {
//            @Override
//            public void imgClick(int position, MyProductByCategory product) {
//                Intent intent = new Intent(requireActivity(), ProductDetailActivity.class);
//                intent.putExtra(KEY_PRODUCT_NAME, product.getProductName());
//                intent.putExtra(KEY_PRODUCT_ID, product.getProductId());
//                intent.putExtra("user_id", user_id);
//                startActivity(intent);
//            }
//
//            @Override
//            public void imgAddToCartClick(int position, MyProductByCategory product) {
//                // TODO add to cart
//            }
//
//            @Override
//            public void imgWatchAll(int position, MyCategory type) {
//                Intent intent = new Intent(requireContext(), SeeAllActivity.class);
//                intent.putExtra(KEY_PRODUCT_TYPE, type.getCategoryId());
//                startActivity(intent);
//            }
//        });

        productMainAdapter.setClickListener(new ProductMainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, ProductMain productMain) {
                Intent intent = new Intent(requireActivity(), ProductDetailActivity.class);
                intent.putExtra(KEY_PRODUCT_NAME, productMain.getProductName());
                intent.putExtra(KEY_PRODUCT_ID, productMain.getProductId());
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }


    private final ArrayList<MyProductByCategory> myProductByCategories = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllProductByCategory() {
        categoryList.forEach(myCategory -> {
            compositeDisposable.add(repository.getProductByCategory(myCategory.getCategoryId())
                    .doOnSubscribe(disposable -> {
                        showProgressBar(progressBar);
                    }).doFinally(() -> {
                    }).subscribe(dataProduct -> {
                        hideProgressBar(progressBar);
                        myProductByCategories.addAll(dataProduct.getData());
                        List<Pair<MyCategory, ArrayList<MyProductByCategory>>> data = new ArrayList<>();

                        data.add(new Pair<>(myCategory, dataProduct.getData()));
                        productListAdapter.addListProduct(data);

                    }, throwable -> {
                        hideProgressBar(progressBar);
                        String error = new Utils().getErrorBody(throwable).getMessage();
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                    }));
        });
    }

    void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunable);
        compositeDisposable.dispose();
        mRunable = null;
        mHandler = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_product_sale = view.findViewById(R.id.rcl_product_sale);
        rl_sale = view.findViewById(R.id.rl_sale);
        icon_sale = view.findViewById(R.id.icon_sale);
       // Glide.with(view).load(R.drawable.sale_home).into(icon_sale);
        rl_sale.setVisibility(View.GONE);
        getListProductSale();
    }

    private void getListProductSale(){

        ApiHistory.API_HISTORY.getListSale().enqueue(new Callback<SaleModel>() {
            @Override
            public void onResponse(Call<SaleModel> call, Response<SaleModel> response) {
                productSales.clear();
                if(response.body()!=null && response.body().getListProduct().size()>0){
                    productSales.addAll(response.body().getListProduct());

                }
                setRclProductSale();
            }

            @Override
            public void onFailure(Call<SaleModel> call, Throwable t) {

            }
        });
    }

    private void setRclProductSale(){
        if(productSales.size()>0){
            rl_sale.setVisibility(View.VISIBLE);
            ProductSaleAdapter productSaleAdapter = new ProductSaleAdapter(requireContext(),productSales);
            rcl_product_sale.setAdapter(productSaleAdapter);
        }else {
            rl_sale.setVisibility(View.GONE);
        }


    }
}