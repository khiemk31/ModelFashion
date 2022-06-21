package com.example.modelfashion.Utility;

import android.view.View;
import android.widget.ProgressBar;

import com.example.modelfashion.Model.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class Utils {

    public BaseResponse getErrorBody(Throwable throwable) {
        if (throwable instanceof HttpException) {
            ResponseBody body = Objects.requireNonNull(((HttpException) throwable).response()).errorBody();
            Gson gson = new Gson();
            TypeAdapter<BaseResponse> adapter = gson.getAdapter(BaseResponse.class);
            try {
                return adapter.fromJson(body.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new BaseResponse();
    }
}
