package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Adapter.category.CategoryAdapter;
import com.example.modelfashion.Adapter.category.ClothesAdapter;
import com.example.modelfashion.Model.response.category.Category;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.product.ProductPreview;
import com.example.modelfashion.Model.response.product.ProductResponse;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.SearchBar;
import com.example.modelfashion.customview.SpacesItemDecoration;
import com.example.modelfashion.network.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class CategoryFragment extends Fragment {
    SearchBar searchBar;
    private CategoryAdapter categoryAdapter;
    private ClothesAdapter clothesAdapter;
    private RecyclerView rcvCategory, rcvClothes;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int currentCategory = 0;
    Repository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        searchBar.onSearchBarClick(content -> {

        });

        categoryAdapter.setClickListener((view, position) -> {
            currentCategory = position;
            categoryAdapter.highLightSelectedItem(position);
            getProductByCategory(repository, categoryAdapter.getCategory(currentCategory).getId());
        });

        clothesAdapter.setClickListener((position, item) -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra(KEY_PRODUCT_ID, item.getId());
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(() -> {
            getProductByCategory(repository, categoryAdapter.getCategory(currentCategory).getId());
            getCategory(repository);
            refreshLayout.setRefreshing(false);
        });
    }

    private void initData() {
        repository = new Repository(requireContext());
        getCategory(repository);
    }

    private void initView(View view) {
        searchBar = view.findViewById(R.id.search_bar);

        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setListCategory(listCategory1());
        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setAdapter(categoryAdapter);
        categoryAdapter.highLightSelectedItem(currentCategory);

        rcvClothes = view.findViewById(R.id.rcv_clothes);
        clothesAdapter = new ClothesAdapter();
        clothesAdapter.setListProduct(listProduct());
        rcvClothes.setAdapter(clothesAdapter);
        rcvClothes.addItemDecoration(new SpacesItemDecoration(8));

        progressBar = view.findViewById(R.id.progress_bar);
        refreshLayout = view.findViewById(R.id.refresh_layout);
    }

    private void getProductByCategory(Repository repository, String id) {
        Single<ProductResponse> productByCategory = repository.getProductByCategory(id);
        compositeDisposable.add(productByCategory.doOnSubscribe(disposable -> {
            // show loading
            progressBar.setVisibility(View.VISIBLE);
        })
                .doFinally(() -> {
                    // hide loading
                    progressBar.setVisibility(View.GONE);
                })
                .subscribe(productResponse -> {
                    clothesAdapter.setListProduct(productResponse.getData().getResults());
                }, throwable -> {
                    Toast.makeText(requireContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void getCategory(Repository repository) {
        Single<CategoryResponse> categoryResponseSingle = repository.getCategory();
        compositeDisposable.add(categoryResponseSingle.doOnSubscribe(disposable -> {
            // show loading
            progressBar.setVisibility(View.VISIBLE);
        })
                .doFinally(() -> {
                    // hide loading
                    progressBar.setVisibility(View.GONE);
                })
                .subscribe(catogoryResponse -> {
                    categoryAdapter.setListCategory(catogoryResponse.getData().getResults());
                    getProductByCategory(repository, categoryAdapter.getCategory(currentCategory).getId());
                }, throwable -> {
                    Toast.makeText(requireContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
                }));
    }

    private List<Category> listCategory1() {
        ArrayList<Category> list = new ArrayList();
        list.add(new Category("1", "Ba lô", 30));
        list.add(new Category("2", "Quần", 30));
        list.add(new Category("3", "Áo", 30));
        list.add(new Category("4", "Giày", 30));
        list.add(new Category("5", "Đồ bộ", 30));
        return list;
    }

    private List<ProductPreview> listProduct() {
        ArrayList<ProductPreview> list = new ArrayList();
        list.add(new ProductPreview("1", "KIDO SHIRT - BLACK", 21321.0, "", "https://zunezx.com/upload/image/cache/data/banner/Tee/47CC5493-74D4-4164-8454-67A648B99FEA-9d1-crop-400-400.jpeg", "S", 10, 6));
        list.add(new ProductPreview("2", "TOSHIRO JACKET", 21321.0, "", "https://zunezx.com/upload/image/cache/data/banner/---bAnnEr-tU-chE/2438672C-DE86-413E-8DFA-8B254077B672-0ac-crop-400-400.jpeg", "S", 20, 1));
        list.add(new ProductPreview("3", "GD - BLACK", 21321.0, "", "", "M", 0, 30));
        list.add(new ProductPreview("4", "GD - WHITE", 21321.0, "", "", "M", 0, 30));
        list.add(new ProductPreview("5", "GD - BLACK", 21321.0, "", "", "M", 0, 30));
        list.add(new ProductPreview("6", "Quần 2", 21321.0, "", "", "M", 0, 30));
        list.add(new ProductPreview("7", "Quần 3", 21321.0, "", "", "M", 0, 30));
        list.add(new ProductPreview("8", "Ba lô 1", 21321.0, "", "", "M", 0, 30));
        return list;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}