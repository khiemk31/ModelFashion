package com.example.modelfashion.network;

import android.content.Context;

import com.example.modelfashion.Model.request.CreateBillRequest;
import com.example.modelfashion.Model.request.GetProductByPriceRequest;
import com.example.modelfashion.Model.request.UseVoucherRequest;
import com.example.modelfashion.Model.response.BaseResponse;
import com.example.modelfashion.Model.response.Login.ForgotPasswordRequest;
import com.example.modelfashion.Model.response.Login.ForgotPasswordResponse;
import com.example.modelfashion.Model.response.Login.LoginRequest;
import com.example.modelfashion.Model.response.Login.LoginResponse;
import com.example.modelfashion.Model.response.MyProductDetail;
import com.example.modelfashion.Model.response.Register.CheckUserRequest;
import com.example.modelfashion.Model.response.Register.CheckUserResponse;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.Model.response.Register.GetOTPResponse;
import com.example.modelfashion.Model.response.Register.RegisterRequest;
import com.example.modelfashion.Model.response.Register.RegisterResponse;
import com.example.modelfashion.Model.response.Register.VerifyOTPRequest;
import com.example.modelfashion.Model.response.Register.VerifyOTPResponse;
import com.example.modelfashion.Model.response.User.CheckUserActiveRequest;
import com.example.modelfashion.Model.response.User.CheckUserActiveResponse;
import com.example.modelfashion.Model.response.User.UpdateUserRequest;
import com.example.modelfashion.Model.response.User.UpdateUserResponse;
import com.example.modelfashion.Model.response.User.UserDetailResponse;
import com.example.modelfashion.Model.response.bill.Bill;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.category.DataAllCategory;
import com.example.modelfashion.Model.response.main_screen.GetAllProductByCategoryResponse;
import com.example.modelfashion.Model.response.main_screen.GetAllResponse;
import com.example.modelfashion.Model.response.my_product.DataProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.Model.response.see_all.GetProductByCategoryResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Single<GetProductByCategoryResponse> getProductByCategory(int categoryId, int price1, int price2, String sortPrice, String sortDiscount, int pageNumber) {
        return apiInterface.getProductByCategory(categoryId, price1, price2, sortPrice, sortDiscount, pageNumber)
                .delay(1000, TimeUnit.MILLISECONDS)
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

    public Single<UserDetailResponse> getUserDetail(String userID) {
        return apiInterface.getUserDetail(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<UpdateUserResponse> updateUser(String userID, UpdateUserRequest request) {
        return apiInterface.updateUser(userID, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ForgotPasswordResponse> changePassword(ForgotPasswordRequest request) {
        return apiInterface.changePassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<DataAllCategory> getAllCategory() {
        return apiInterface.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<Bill> getAllBill(String userID) {
        return apiInterface.getAllBill(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<MyProductDetail> getProductDetail(String productId) {
        return apiInterface.getProductDetail(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ForgotPasswordResponse> createBill(CreateBillRequest request) {
        return apiInterface
                .createBill(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<CheckUserResponse> checkUser(CheckUserRequest request) {
        return apiInterface
                .checkUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<DataProduct> getAll() {
        return apiInterface.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<MyProductByCategory>> getProductByPrice(GetProductByPriceRequest request) {
        return apiInterface.getProductByPrice(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<GetAllResponse> getAllProductOffset(int offset) {
        return apiInterface.getAllProduct(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<GetAllProductByCategoryResponse> getAllProductByCategory() {
        return apiInterface.getAllProductByCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<CheckUserActiveResponse> checkUserActive(CheckUserActiveRequest request) {
        return apiInterface.checkUserActive(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<BaseResponse> useVoucher(UseVoucherRequest request) {
        return apiInterface.useVoucher(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<GetProductByCategoryResponse> getProductSearch(int id) {
        return apiInterface.getProductSearch(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
