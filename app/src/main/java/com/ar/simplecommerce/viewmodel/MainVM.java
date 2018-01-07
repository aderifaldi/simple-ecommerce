package com.ar.simplecommerce.viewmodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.ar.simplecommerce.data.api.GetProductList;
import com.ar.simplecommerce.data.cache.CacheDB;
import com.ar.simplecommerce.helper.AppUtility;
import com.ar.simplecommerce.model.api.ApiResponse;

/**
 * Created by aderifaldi on 2018-01-05.
 */

public class MainVM extends ViewModel {

    private MediatorLiveData<ApiResponse> data;

    public LiveData<ApiResponse> getData() {
        return data;
    }

    public void getProductList(final Activity activity, final CacheDB cacheDB, final int cacheType) {

        data = new MediatorLiveData<>();

        new GetProductList(activity, cacheDB, cacheType) {
            @Override
            public void onFinishRequest(LiveData<ApiResponse> r) {

                data.setValue(r.getValue());

            }
        };

    }

}
