package com.example.modelfashion.network;

import android.content.Context;

import com.example.modelfashion.Model.response.Login.LoginRequest;
import com.example.modelfashion.Model.response.Login.LoginResponse;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.Model.response.Register.GetOTPResponse;
import com.example.modelfashion.Model.response.Register.RegisterRequest;
import com.example.modelfashion.Model.response.Register.RegisterResponse;
import com.example.modelfashion.Model.response.Register.VerifyOTPRequest;
import com.example.modelfashion.Model.response.Register.VerifyOTPResponse;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.category.DataAllCategory;
import com.example.modelfashion.Model.response.my_product.DataProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    public Single<MyProduct> getProductById(String id, String name) {
        return apiInterface.getProductById(id, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ArrayList<MyProduct>> getProductByType(String type) {
        return apiInterface.getProductByType(type)
    public Single<DataProduct> getProductByCategory(String categoryId){
        return apiInterface.getProductByCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<LoginResponse> login(LoginRequest loginRequest) {
        return apiInterface.login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<GetOTPResponse> getOTP(GetOTPRequest request) {
        return apiInterface.getOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<VerifyOTPResponse> verifyOTP(VerifyOTPRequest request) {
        return apiInterface.verifyOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<RegisterResponse> register(RegisterRequest request) {
        return apiInterface.register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<DataAllCategory> getAllCategory() {
        return apiInterface.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
