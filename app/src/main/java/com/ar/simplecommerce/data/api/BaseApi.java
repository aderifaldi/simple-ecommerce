package com.ar.simplecommerce.data.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.ar.simplecommerce.helper.Constant;
import com.ar.simplecommerce.model.api.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RadyaLabs PC on 29/11/2017.
 */

public abstract class BaseApi {

    ApiService apiService;

    BaseApi() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(Constant.Api.TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(Constant.Api.TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(logInterceptor);

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();

        apiService = retrofit.create(ApiService.class);
    }

    abstract public void onFinishRequest(LiveData<ApiResponse> r);

}
