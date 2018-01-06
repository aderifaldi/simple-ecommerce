package com.ar.simplecommerce.data.api;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.ar.simplecommerce.data.cache.CacheDB;
import com.ar.simplecommerce.helper.AppUtility;
import com.ar.simplecommerce.model.api.ApiResponse;
import com.ar.simplecommerce.model.api.ModelProductList;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RadyaLabs PC on 29/11/2017.
 */

public abstract class GetProductList extends BaseApi {

    private ApiResponse<ModelProductList> apiResponse;
    private ModelProductList data;
    private String errorResponse;
    private CacheDB cacheDB;
    private int cacheType;
    private MutableLiveData<ApiResponse> liveData;

    protected GetProductList(final Activity activity, CacheDB cacheDB, int cacheType) {
        this.cacheType = cacheType;
        this.cacheDB = cacheDB;

        Call<ModelProductList> call = apiService.getProductList();
        apiResponse = new ApiResponse<>();
        liveData = new MutableLiveData<>();

        call.enqueue(new Callback<ModelProductList>() {
            @Override
            public void onResponse(Call<ModelProductList> call, Response<ModelProductList> response) {
                if (response.isSuccessful()) {
                    try {
                        data = response.body();
                    } catch (Exception e) {
                        e.printStackTrace();
                        data = null;
                    }
                } else {
                    try {
                        errorResponse = response.errorBody().string();
                        if (errorResponse != null) {
                            data = new GsonBuilder().create().fromJson(errorResponse, ModelProductList.class);
                        } else {
                            data = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        data = null;
                    }
                }

                apiResponse.setData(data);
                liveData.setValue(apiResponse);
                onFinishRequest(liveData);
            }

            @Override
            public void onFailure(Call<ModelProductList> call, Throwable t) {
                AppUtility.showToastConnectionProblem(activity);
                data = loadCache();

                if (data != null) {
                    apiResponse.setData(data);
                } else {
                    data = null;
                    apiResponse.setData(data);
                }

                liveData.setValue(apiResponse);
                onFinishRequest(liveData);
            }
        });
    }

    private ModelProductList loadCache() {
        try {
            JsonObject json = new JsonParser().parse(cacheDB.cacheDao().loadCacheById(cacheType).json).getAsJsonObject();
            return new GsonBuilder().create().fromJson(json, ModelProductList.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
