package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_TYPE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Activity.SeeAllActivity;
import com.example.modelfashion.Adapter.category.CategoryAdapter;
import com.example.modelfashion.Adapter.category.ClothesAdapter;
import com.example.modelfashion.Adapter.category.MyCategoryAdapter;
import com.example.modelfashion.History.ApiHistory.ApiHistory;
import com.example.modelfashion.Model.Category;
import com.example.modelfashion.Model.CategoryModel;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {



    private ArrayList<Category> myCategories ;
    private RecyclerView rcv_mycategory;

    private ArrayList<MyProductByCategory> productArrayList = new ArrayList<>();

    public static CategoryFragment newInstance(String text) {

        CategoryFragment f = new CategoryFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rcv_mycategory = view.findViewById(R.id.rcv_mycategory);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListCategory();
    }

    private void getListCategory(){
        ApiHistory.API_HISTORY.getListCategory().enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                myCategories = new ArrayList<>();
                if(response.body()!=null){
                    CategoryModel categoryModel = response.body();
                    myCategories.addAll(categoryModel.getData());
                    Log.e("aaa", String.valueOf(myCategories.size()));
                    setListStyle();
                }


            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

            }
        });

    }

    private void setListStyle(){
        MyCategoryAdapter myCategoryAdapter = new MyCategoryAdapter(requireContext(),myCategories);
        rcv_mycategory.setAdapter(myCategoryAdapter);

        myCategoryAdapter.setOnItemClickListener((position, myCategory) -> {
            Intent intent = new Intent(requireContext(), SeeAllActivity.class);
            intent.putExtra(KEY_PRODUCT_TYPE, myCategory.getCategory_id());
            startActivity(intent);
        });
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}