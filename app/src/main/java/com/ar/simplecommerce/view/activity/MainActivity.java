package com.ar.simplecommerce.view.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.ar.simplecommerce.R;
import com.ar.simplecommerce.data.cache.CacheDB;
import com.ar.simplecommerce.data.cache.CacheManager;
import com.ar.simplecommerce.helper.AppUtility;
import com.ar.simplecommerce.helper.Constant;
import com.ar.simplecommerce.helper.LinearLayoutManagerWithSmoothScroller;
import com.ar.simplecommerce.model.api.ApiResponse;
import com.ar.simplecommerce.model.api.ModelProductList;
import com.ar.simplecommerce.view.adapter.ProductListAdapter;
import com.ar.simplecommerce.viewmodel.MainVM;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //Todo: View binding use ButterKnife
    @BindView(R.id.scrollUpButon)
    FloatingActionButton scrollUpButon;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    //Todo: Declaration variable
    private RecyclerView listProduct;

    private ProductListAdapter adapter;
    private LinearLayoutManagerWithSmoothScroller linearLayoutManager;

    private MainVM viewModel;
    private CacheDB cacheDB;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Todo: Initialize variable
        activity = MainActivity.this;

        viewModel = ViewModelProviders.of(this).get(MainVM.class);
        cacheDB = CacheDB.getDatabase(this);

        listProduct = findViewById(R.id.listProduct);

        linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(activity);
        adapter = new ProductListAdapter(activity);

        //Todo: Set layout manager of RecyclerView
        listProduct.setLayoutManager(linearLayoutManager);

        //Todo: Set adapter of RecyclerView
        listProduct.setAdapter(adapter);

        //Todo: Execute method loadApiResponse
        loadApiResponse();

        //Todo: Set on item click listener of list
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModelProductList.Products itemData = adapter.getData().get(i);
                AppUtility.showToast(activity, itemData.getName());
            }
        });

        //Todo: Add scroll listener of RecyclerView
        listProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && scrollUpButon.getVisibility() == View.VISIBLE) {
                    //Hide floating button when scroll up
                    scrollUpButon.hide();
                } else if (dy < 0 && scrollUpButon.getVisibility() != View.VISIBLE) {
                    //Show floating button when scroll down
                    scrollUpButon.show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheDB.destroyInstance();
    }

    public void loadApiResponse() {

        viewModel.getProductList(activity, cacheDB, Constant.Cache.PRODUCT);
        viewModel.getData().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressBar.setVisibility(View.GONE);

                ModelProductList data = (ModelProductList) apiResponse.getData();

                if (data != null) {
                    if (data.getStatus().equals(Constant.Api.SUCCESS)) {

                        //Todo: Store data to list
                        storeDataToList(data);
                        //Todo: Hide progress bar
                        scrollUpButon.setVisibility(View.VISIBLE);

                    } else {
                        if (data.getMessage() != null) {
                            AppUtility.showToast(activity, data.getMessage());
                        } else {
                            AppUtility.showToastFailedGetingData(activity);
                        }
                    }
                } else {
                    AppUtility.showToastFailedGetingData(activity);
                }

            }
        });

    }

    private void storeDataToList(ModelProductList data) {
        //Todo: Clear list
        adapter.getData().clear();

        //Todo: Add data to adapter
        for (int i = 0; i < data.getProducts().size(); i++) {
            adapter.getData().add(data.getProducts().get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }

        //Todo: Notify adapter when data updated
        adapter.notifyDataSetChanged();

        //Todo: Store data to cache
        CacheManager.storeCache(cacheDB, Constant.Cache.PRODUCT, new Gson().toJson(data));
    }

    @OnClick(R.id.scrollUpButon)
    public void onViewClicked() {
        //Todo: Scroll RecyclerView to top
        listProduct.smoothScrollToPosition(0);
    }
}
