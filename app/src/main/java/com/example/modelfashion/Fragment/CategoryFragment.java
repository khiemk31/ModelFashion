package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Adapter.category.CategoryAdapter;
import com.example.modelfashion.Adapter.category.ClothesAdapter;
import com.example.modelfashion.Model.response.category.MyCategory;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.SearchBar;
import com.example.modelfashion.customview.SpacesItemDecoration;
import com.example.modelfashion.network.Repository;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class CategoryFragment extends Fragment {
    SearchView searchView;
    private CategoryAdapter categoryAdapter;
    private ClothesAdapter clothesAdapter;
    private RecyclerView rcvCategory, rcvClothes;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private SearchBar searchBar;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int currentCategory = 0;
    Repository repository;

    private final ArrayList<MyProductByCategory> productArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);



        initData();
        initListener();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initListener() {
        searchBar.onSearchBarClick(new SearchBar.SearchListener() {
            @Override
            public void onClearClick() {
                getProductByCategory(repository, categoryAdapter.getMyCategory(currentCategory));
            }

            @Override
            public void afterTextChanged(String content) {
                ArrayList<MyProductByCategory> listSearch = new ArrayList<>();
                for (int i = 0; i < productArrayList.size(); i++) {
                    if (productArrayList.get(i).getProductName().toLowerCase().contains(content.toLowerCase())) {
                        listSearch.add(productArrayList.get(i));
                    }
                }
                clothesAdapter.setListProduct(listSearch);
            }
        });

        categoryAdapter.setClickListener((view, position) -> {
            currentCategory = position;
            categoryAdapter.highLightSelectedItem(position);
            getProductByCategory(repository, categoryAdapter.getMyCategory(currentCategory));
        });

        clothesAdapter.setClickListener((position, item) -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra(KEY_PRODUCT_ID, item.getProductId());
            intent.putExtra(KEY_PRODUCT_NAME, item.getProductName());
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(() -> {
            getProductByCategory(repository, categoryAdapter.getMyCategory(currentCategory));
            getCategory(repository);
            searchBar.clearSearchContent();
            refreshLayout.setRefreshing(false);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        repository = new Repository(requireContext());
        getCategory(repository);
    }

    private void initView(View view) {
        searchBar = view.findViewById(R.id.search_bar);
//        searchView = view.findViewById(R.id.search_view);
        categoryAdapter = new CategoryAdapter();
        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setAdapter(categoryAdapter);
        categoryAdapter.highLightSelectedItem(currentCategory);

        rcvClothes = view.findViewById(R.id.rcv_clothes);
        clothesAdapter = new ClothesAdapter();
        rcvClothes.setAdapter(clothesAdapter);
        rcvClothes.addItemDecoration(new SpacesItemDecoration(8));

        progressBar = view.findViewById(R.id.progress_bar);
        refreshLayout = view.findViewById(R.id.refresh_layout);

    }

    private void getProductByCategory(Repository repository, MyCategory category) {
        compositeDisposable.add(repository.getProductByCategory(category.getCategoryId()).doOnSubscribe(disposable -> {
            // show loading
            progressBar.setVisibility(View.VISIBLE);
        })
                .doFinally(() -> {
                    // hide loading
                    progressBar.setVisibility(View.GONE);
                })
                .subscribe(dataProduct -> {
                    productArrayList.clear();
                    clothesAdapter.setListProduct(dataProduct.getData());
                    productArrayList.addAll(dataProduct.getData());
                }, throwable -> {
                    Toast.makeText(requireContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCategory(Repository repository) {
        compositeDisposable.add(repository.getAllCategory()
                .doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                }).doFinally(() -> {
                    progressBar.setVisibility(View.GONE);
                }).subscribe(dataAllCategory -> {
                    categoryAdapter.setListCategory(dataAllCategory.getData());
                    getProductByCategory(repository, categoryAdapter.getMyCategory(currentCategory));
                }, throwable -> {

                }));

    }


    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}