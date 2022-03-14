package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Adapter.ProductListAdapter;
import com.example.modelfashion.Adapter.ViewPagerMainFmAdapter;
import com.example.modelfashion.Adapter.VpSaleMainFmAdapter;
import com.example.modelfashion.Model.ItemSaleMain;
import com.example.modelfashion.Model.Product;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.InnerTabLayout;
import com.example.modelfashion.customview.SearchBar;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class MainFragment extends Fragment {
    private ViewPager2 vpSaleMain;
    private CircleIndicator3 ciSale;
    private SearchBar searchBar;
    private RecyclerView rcvProduct;

    ArrayList<ItemSaleMain> arrItem = new ArrayList<>();

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
        // Required empty public constructor
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
        searchBar = view.findViewById(R.id.search_bar);
        vpSaleMain = view.findViewById(R.id.vp_sale_main_fm);
        ciSale = view.findViewById(R.id.ci_sale_main_fm);
        rcvProduct = view.findViewById(R.id.rv_men_page_fm);

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

        searchBar.onSearchBarClick(contentSearch -> {
            // TODO Search
        });

        initRcvProduct();

        return view;
    }

    private void initListener() {

    }

    private void initRcvProduct() {
        ArrayList<Product> arrProduct = new ArrayList<>();
        ArrayList<String> arrProductType = new ArrayList<String>(Arrays.asList("Áo", "Quần", "Ba Lô"));
        arrProduct.add(new Product(1, "Áo 1", "", "100.000 đ", "", "Áo", 0));
        arrProduct.add(new Product(2, "Áo 2", "", "200.000 đ", "", "Áo", 0));
        arrProduct.add(new Product(3, "Quần 1", "", "100.000 đ", "", "Quần", 0));
        arrProduct.add(new Product(4, "Quần 2", "", "200.000 đ", "", "Quần", 0));
        arrProduct.add(new Product(5, "Quần 3", "", "300.000 đ", "", "Quần", 0));
        arrProduct.add(new Product(6, "Ba lô 1", "", "100.000 đ", "", "Ba Lô", 0));
        ProductListAdapter productListAdapter = new ProductListAdapter(requireContext(), arrProductType, arrProduct);
        rcvProduct.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        rcvProduct.setAdapter(productListAdapter);
        productListAdapter.onItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void imgClick(int position, Product product) {
                Intent intent = new Intent(requireActivity(), ProductDetailActivity.class);
                intent.putExtra(KEY_PRODUCT_ID, product.getProductId());
                startActivity(intent);
            }

            @Override
            public void imgAddToCartClick(int position, Product product) {
                // TODO add to cart
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunable);
        mRunable = null;
        mHandler = null;
    }
}