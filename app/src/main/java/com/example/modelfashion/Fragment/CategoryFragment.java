package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Adapter.category.CategoryAdapter;
import com.example.modelfashion.Adapter.category.ClothesAdapter;
import com.example.modelfashion.Model.request.GetProductByPriceRequest;
import com.example.modelfashion.Model.response.category.MyCategory;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.DialogCategory;
import com.example.modelfashion.customview.SearchBar;
import com.example.modelfashion.customview.SpacesItemDecoration;
import com.example.modelfashion.network.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class CategoryFragment extends Fragment {
    SearchView searchView;
    private CategoryAdapter categoryAdapter;
    private ClothesAdapter clothesAdapter;
    private RecyclerView rcvClothes;
    private RecyclerView rcvCategoryF;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private SearchBar searchBar;
    private Dialog dialog;
    ImageView filter_category;
    private DialogCategory dialogCategory;


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int currentCategory = 0;
    Repository repository;

    private ArrayList<MyProductByCategory> productArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        repository = new Repository(requireContext());


        initData();
        initListener();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initListener() {
        searchBar.onSearchBarClick(new SearchBar.SearchListener() {
            @Override
            public void onClearClick() {

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

        clothesAdapter.setClickListener((position, item) -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra(KEY_PRODUCT_ID, item.getProductId());
            intent.putExtra(KEY_PRODUCT_NAME, item.getProductName());
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(() -> {
            getAll();
            searchBar.clearSearchContent();
            refreshLayout.setRefreshing(false);
        });


    }

    private void getProductFilter(String categoryName, long price1, long price2, String sortOrder) {
        compositeDisposable.add(repository.getProductByPrice(new GetProductByPriceRequest(categoryName, price1, price2, sortOrder))
                .doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                }).doFinally(() -> {
                })
                .subscribe(myProductByPriceResponses -> {
                    progressBar.setVisibility(View.GONE);
                    clothesAdapter.setListProduct(myProductByPriceResponses);


                }, throwable -> {
                    progressBar.setVisibility(View.GONE);
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {

        getCategory();
        getAll();
    }

    private void getAll() {
        compositeDisposable.add(repository.getAll()
                .doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                }).doFinally(() -> {
                })
                .subscribe(myProductByCategories -> {
                    clothesAdapter.setListProduct(myProductByCategories.getData());
                    productArrayList.clear();
                    productArrayList = myProductByCategories.getData();
                    progressBar.setVisibility(View.GONE);

                }, throwable -> {
                    progressBar.setVisibility(View.GONE);
                }));
    }

    private void initDialogClickListener() {
        dialogCategory.setClickListener(new DialogCategory.DialogClickInterface() {
            @Override
            public void confirmClickListener(String categoryName, long price1, long price2, String sortOrder) {
                // call api with those params
                getProductFilter(categoryName, price1, price2, sortOrder);
            }

            @Override
            public void viewProductByCategoryOnly(String categoryId) {
                viewProductByCategory(categoryId);
            }

            @Override
            public void viewAllNoFilter() {
                // call api getall
                getAll();
            }
        });
    }

    private void viewProductByCategory(String categoryId) {
        compositeDisposable.add(repository.getProductByCategory(categoryId)
                .doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                }).subscribe(dataProduct -> {
                    progressBar.setVisibility(View.GONE);
                    clothesAdapter.setListProduct(dataProduct.getData());
                    if (dataProduct.getData().size() == 0) {

                    }
                }, throwable -> {
                    progressBar.setVisibility(View.GONE);
                }));
    }

    private void initView(View view) {
        searchBar = view.findViewById(R.id.search_bar);
        filter_category = view.findViewById(R.id.filter_category);
//        searchView = view.findViewById(R.id.search_view);
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.highLightSelectedItem(currentCategory);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_filter_category);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rcvCategoryF = dialog.findViewById(R.id.rcv_category_f);
        TextView tv_close_filterr = dialog.findViewById(R.id.tv_close_filterr);
        rcvCategoryF.setAdapter(categoryAdapter);
        tv_close_filterr.setOnClickListener(view1 -> dialog.dismiss());
//        filter_category.setOnClickListener(view12 -> dialog.show());
        filter_category.setOnClickListener(view1 -> {
            dialogCategory = new DialogCategory(categoryListFinal);
            dialogCategory.show(getChildFragmentManager(), dialogCategory.getTag());

            initDialogClickListener();
        });


        rcvClothes = view.findViewById(R.id.rcv_clothes);
        clothesAdapter = new ClothesAdapter();
        rcvClothes.setAdapter(clothesAdapter);
        rcvClothes.addItemDecoration(new SpacesItemDecoration(8));

        progressBar = view.findViewById(R.id.progress_bar);
        refreshLayout = view.findViewById(R.id.refresh_layout);

        filter_category.setEnabled(false);
    }


    private final List<MyCategory> categoryListFinal = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCategory() {
        compositeDisposable.add(repository.getAllCategory()
                .doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                }).doFinally(() -> {
                    progressBar.setVisibility(View.GONE);
                }).subscribe(dataAllCategory -> {
                    progressBar.setVisibility(View.GONE);
                    categoryListFinal.addAll(dataAllCategory.getData());
                    filter_category.setEnabled(true);
                }, throwable -> {
                    progressBar.setVisibility(View.GONE);
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
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}