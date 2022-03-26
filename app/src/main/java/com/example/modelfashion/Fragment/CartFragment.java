package com.example.modelfashion.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Adapter.cart.CartAdapter;
import com.example.modelfashion.Interface.ApiRetrofit;
import com.example.modelfashion.Model.Product;
import com.example.modelfashion.Model.response.my_product.CartProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.Sizes;
import com.example.modelfashion.R;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    private View initView;
    private ArrayList<Product> productArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArrayList<MyProduct> arrProduct = new ArrayList<>();
    private ArrayList<CartProduct> arrCart = new ArrayList<>();
    private ArrayList<Sizes> arrSize = new ArrayList<>();
    private ArrayList<String> arr_size_id = new ArrayList<>();
    private ArrayList<String> arr_product_name = new ArrayList<>();
    private String user_id;
    private TextView tvTotal;

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = initView.findViewById(R.id.list_product_cart);
        tvTotal = initView.findViewById(R.id.total_money);
        Bundle info = getArguments();
        user_id = info.getString("user_id");
        getCart();

//        fakeDataProduct();
//        setAdapter();
        return initView;
    }

    private void getCart() {
        ApiRetrofit.apiRetrofit.GetCartProduct(user_id).enqueue(new Callback<ArrayList<CartProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<CartProduct>> call, Response<ArrayList<CartProduct>> response) {
                arrCart = response.body();
                for (int i = 0; i < arrCart.size(); i++) {
                    arr_product_name.add(arrCart.get(i).getProductName());
                    arr_size_id.add(arrCart.get(i).getSizeId());
                }
                JSONArray json_product_name = new JSONArray(arr_product_name);
                JSONArray json_size_id = new JSONArray(arr_size_id);
                getProductInfo(json_product_name, json_size_id);
                getAmountCart(json_product_name);
            }

            @Override
            public void onFailure(Call<ArrayList<CartProduct>> call, Throwable t) {

            }
        });
    }

    private void getProductInfo(JSONArray arr_product_name, JSONArray arr_size_id) {
        ApiRetrofit.apiRetrofit.GetProductByName(arr_product_name).enqueue(new Callback<ArrayList<MyProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<MyProduct>> call, Response<ArrayList<MyProduct>> response) {
                arrProduct = response.body();
                getSizeInfo(arr_size_id);
            }

            @Override
            public void onFailure(Call<ArrayList<MyProduct>> call, Throwable t) {

            }
        });
    }

    private void getSizeInfo(JSONArray arr_size_id) {
        ApiRetrofit.apiRetrofit.GetSizeById(arr_size_id).enqueue(new Callback<ArrayList<Sizes>>() {
            @Override
            public void onResponse(Call<ArrayList<Sizes>> call, Response<ArrayList<Sizes>> response) {
                arrSize = response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<Sizes>> call, Throwable t) {

            }
        });
    }

    private void getAmountCart(JSONArray arr_product_name) {
        ApiRetrofit.apiRetrofit.GetAmountCart(arr_product_name).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tvTotal.setText("Tổng tiền: " + response.body() + " VNĐ");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void setAdapter() {
        CartAdapter adapter = new CartAdapter(arrProduct, arrSize, getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void fakeDataProduct() {
        productArrayList.add(new Product(1, "Sản Phẩm 1", "", "650,000đ", "", "L", 1));
        productArrayList.add(new Product(2, "Sản Phẩm 2", "", "750,000đ", "", "M", 1));
        productArrayList.add(new Product(3, "Sản Phẩm 3", "", "850,000đ", "", "XL", 1));
        productArrayList.add(new Product(4, "Sản Phẩm 4", "", "950,000đ", "", "2XL", 1));
        productArrayList.add(new Product(5, "Sản Phẩm 5", "", "1050,000đ", "", "3XL", 1));
    }
}