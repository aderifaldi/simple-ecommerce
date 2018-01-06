package com.ar.simplecommerce.view.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.ar.simplecommerce.R;
import com.ar.simplecommerce.data.cache.CacheDB;
import com.ar.simplecommerce.data.cache.CacheManager;
import com.ar.simplecommerce.helper.AppUtility;
import com.ar.simplecommerce.helper.Constant;
import com.ar.simplecommerce.model.api.ApiResponse;
import com.ar.simplecommerce.model.api.ModelProductList;
import com.ar.simplecommerce.view.adapter.ProductListAdapter;
import com.ar.simplecommerce.viewmodel.MainVM;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listProduct;
    private ProductListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private MainVM viewModel;
    private CacheDB cacheDB;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        viewModel = ViewModelProviders.of(this).get(MainVM.class);
        cacheDB = CacheDB.getDatabase(this);

        listProduct = findViewById(R.id.listProduct);

        linearLayoutManager = new LinearLayoutManager(activity);
        adapter = new ProductListAdapter(activity);

        listProduct.setLayoutManager(linearLayoutManager);
        listProduct.setAdapter(adapter);

        loadApiResponse();

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModelProductList.Products itemData = adapter.getData().get(i);
                AppUtility.showToast(activity, itemData.getName());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheDB.destroyInstance();
    }

    public void loadApiResponse() {

        viewModel.getProductList(activity, cacheDB, Constant.CACHE_PRODUCT);
        viewModel.getData().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                ModelProductList data = (ModelProductList) apiResponse.getData();

                if (data != null) {
                    if (data.getStatus().equals(Constant.SUCCESS)) {

                        for (int i = 0; i < data.getProducts().size(); i++) {
                            adapter.getData().add(data.getProducts().get(i));
                            adapter.notifyItemInserted(adapter.getData().size() - 1);
                        }

                        adapter.notifyDataSetChanged();

                        CacheManager.storeCache(cacheDB, Constant.CACHE_PRODUCT, new Gson().toJson(data));

                    } else {
                        if (data.getMessage() != null) {
                            AppUtility.showToast(activity, data.getMessage());
                        } else {
                            AppUtility.showToastFailedGetingData(activity);
                        }
                    }
                }else {
                    AppUtility.showToastFailedGetingData(activity);
                }

            }
        });

    }

}
