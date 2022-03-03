package com.example.modelfashion.network;

import com.example.modelfashion.Model.response.category.CategoryResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Single<CategoryResponse> getCategory(@Url String url);


}
