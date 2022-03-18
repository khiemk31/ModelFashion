package com.example.modelfashion.network;

import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.product.ProductResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Single<CategoryResponse> getCategory(@Url String url);

    @GET("/api/products/categorys/{id}")
    Single<ProductResponse> getProductByCategory(@Path("id") String id);

    @GET("get_all_products.php")
    Single<ArrayList<MyProduct>> getAllProduct();
}
