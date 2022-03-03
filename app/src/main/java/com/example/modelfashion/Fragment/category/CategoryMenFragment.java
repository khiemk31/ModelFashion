package com.example.modelfashion.Fragment.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
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
import com.example.modelfashion.R;
import com.example.modelfashion.customview.SpacesItemDecoration;
import com.example.modelfashion.Model.Product;
import com.example.modelfashion.network.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;


public class CategoryMenFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_category_men, container, false);

        initView(view);
        initData();
        initListener();

        return view;
    }

    private void initData() {
        repository = new Repository(requireContext());
        getCategory(repository);

    }

    private void initListener() {
        categoryAdapter.setClickListener((view, position) -> {
            currentCategory = position;
            categoryAdapter.highLightSelectedItem(position);
            // TODO category

        });

        clothesAdapter.setClickListener((view, position) -> {
            // TODO clothes
            startActivity(new Intent(getActivity(), ProductDetailActivity.class));
        });

        refreshLayout.setOnRefreshListener(() -> {
            getCategory(repository);
            refreshLayout.setRefreshing(false);
        });
    }

    private void initView(View view) {
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
                    currentCategory = 0;
                    categoryAdapter.highLightSelectedItem(currentCategory);
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

    private List<Product> listProduct() {
        ArrayList<Product> list = new ArrayList();
        list.add(new Product(1, "KIDO SHIRT - BLACK", "", "450,000đ", "https://zunezx.com/upload/image/cache/data/banner/Tee/47CC5493-74D4-4164-8454-67A648B99FEA-9d1-crop-400-400.jpeg", "Áo", 0));
        list.add(new Product(2, "TOSHIRO JACKET", "", "450,000đ", "https://zunezx.com/upload/image/cache/data/banner/---bAnnEr-tU-chE/2438672C-DE86-413E-8DFA-8B254077B672-0ac-crop-400-400.jpeg", "Áo", 0));
        list.add(new Product(3, "GD - BLACK", "", "450.000 đ", "", "Áo", 0));
        list.add(new Product(4, "GD - WHITE", "", "420.000 đ", "", "Áo", 0));
        list.add(new Product(5, "GD - BLACK", "", "100.000 đ", "", "Quần", 0));
        list.add(new Product(6, "Quần 2", "", "420.000 đ", "", "Quần", 0));
        list.add(new Product(7, "Quần 3", "", "300.000 đ", "", "Quần", 0));
        list.add(new Product(8, "Ba lô 1", "", "100.000 đ", "", "Ba Lô", 0));
        return list;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}