package com.ar.simplecommerce.view.adapter;

//http://stackoverflow.com/questions/26585941/recyclerview-header-and-footer

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.simplecommerce.R;
import com.ar.simplecommerce.helper.AppUtility;
import com.ar.simplecommerce.model.api.ModelProductList;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private ArrayList<ModelProductList.Products> data;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;

    public ProductListAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<ModelProductList.Products> getData() {
        return data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.product_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelProductList.Products item = data.get(position);
        holder.position = position;

        final long identity = System.currentTimeMillis();
        holder.identity = identity;

        holder.txt_product_title.setText(item.getName());
        holder.txt_product_price.setText(AppUtility.formatMoney("Rp", item.getPrice(), '.', ""));
        holder.txt_product_desc.setText(item.getDesc());

        Glide.with(context).load(item.getImages().get(0)).into(holder.img_product);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int position;
        long identity;

        ImageView img_product;
        TextView txt_product_title, txt_product_price, txt_product_desc;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            img_product = itemView.findViewById(R.id.img_product);
            txt_product_title = itemView.findViewById(R.id.txt_product_title);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            txt_product_desc = itemView.findViewById(R.id.txt_product_desc);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, v, position, 0);
            }
        }
    }
}
