package com.ar.simplecommerce.data.api;

import com.ar.simplecommerce.model.api.ModelProductList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aderifaldi on 08/08/2016.
 */
public interface ApiService {

    @GET("products.json")
    Call<ModelProductList> getProductList();

}
