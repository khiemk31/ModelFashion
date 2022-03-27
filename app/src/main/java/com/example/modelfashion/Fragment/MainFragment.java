package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_PRICE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Adapter.ProductListAdapter;
import com.example.modelfashion.Adapter.VpSaleMainFmAdapter;
import com.example.modelfashion.Model.ItemSaleMain;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.network.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import me.relex.circleindicator.CircleIndicator3;


public class MainFragment extends Fragment {
    private ViewPager2 vpSaleMain;
    private CircleIndicator3 ciSale;
    private SearchView searchView;
    private RecyclerView rcvProduct;
    Repository repository;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    ArrayList<ItemSaleMain> arrItem = new ArrayList<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    ProductListAdapter productListAdapter = new ProductListAdapter();

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

    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        searchView = view.findViewById(R.id.search_view);
        vpSaleMain = view.findViewById(R.id.vp_sale_main_fm);
        ciSale = view.findViewById(R.id.ci_sale_main_fm);
        rcvProduct = view.findViewById(R.id.rv_men_page_fm);
        progressBar = view.findViewById(R.id.progress_bar);
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        arrItem.add(new ItemSaleMain(R.drawable.test_img));
        VpSaleMainFmAdapter vpSaleMainFmAdapter = new VpSaleMainFmAdapter(arrItem);
        vpSaleMain.setAdapter(vpSaleMainFmAdapter);

        ciSale.setViewPager(vpSaleMain);

        vpSaleMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunable);
                mHandler.postDelayed(mRunable, 2000);
            }
        });
        initData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

//            @Override
//            public boolean onQueryTextChange(String s) {
//                ArrayList<Product> filteredProduct =new ArrayList<Product>( );
//                for (Product product: productListAdapter)
//                return false;
//            }
        });


        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.VISIBLE);
            getAllProduct(repository);
        });

        return view;
    }

    private void initData() {
        repository = new Repository(requireContext());
        getAllProduct(repository);

    }

    private void initListener() {
        productListAdapter.onItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void imgClick(int position, MyProduct product) {
                Intent intent = new Intent(requireActivity(), ProductDetailActivity.class);
                intent.putExtra(KEY_PRODUCT_NAME, product.getProduct_name());
                intent.putExtra(KEY_PRODUCT_PRICE, product.getPrice());
                startActivity(intent);
            }

            @Override
            public void imgAddToCartClick(int position, MyProduct product) {
                // TODO add to cart
            }
        });
    }



    private void getAllProduct(Repository repository) {
        Single<ArrayList<MyProduct>> products = repository.getAllProduct();
        compositeDisposable.add(products.doOnSubscribe(disposable -> {
            // show loading
            progressBar.setVisibility(View.VISIBLE);
        })
                .doFinally(() -> {
                    // hide loading
                    progressBar.setVisibility(View.GONE);
                })
                .subscribe(it -> {

                    HashMap<String, String> hmType = new HashMap<>();
                    for (int i = 0; i < it.size(); i++) {
                        hmType.put(it.get(i).getType(), it.get(i).getType());
                    }
                    Set<String> key = hmType.keySet();
                    ArrayList<String> arrProductType = new ArrayList<>(key);

                    productListAdapter = new ProductListAdapter(requireContext(), arrProductType, it);
                    rcvProduct.setAdapter(productListAdapter);
                    initListener();
                }, throwable -> {
                    Log.d(Constants.ERROR_MESSAGE, throwable.toString());
                }));
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunable);
        compositeDisposable.dispose();
        mRunable = null;
        mHandler = null;
    }
}