package com.example.modelfashion.network;

import android.content.Context;

import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.category.DataAllCategory;
import com.example.modelfashion.Model.response.my_product.DataProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

public final class Repository {
    private ApiInterface apiInterface;

    public Repository(Context context) {
        this.apiInterface = ApiClient.provideApiInterface(context);
    }

    public Single<CategoryResponse> getCategory() {
        return apiInterface.getCategory("https://test-api-spring-boot.herokuapp.com/api/category/categories")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<DataProduct> getAllProduct() {
        return apiInterface.getAllProduct()
                .subscribeOn(Schedulers.io()) 
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MyProduct> getProductById(String id, String name){
        return apiInterface.getProductById(id, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<DataProduct> getProductByCategory(String categoryId){
        return apiInterface.getProductByCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Unit> login(String tk, String mk){
        return apiInterface.login(tk, mk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<DataAllCategory> getAllCategory() {
        return apiInterface.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
