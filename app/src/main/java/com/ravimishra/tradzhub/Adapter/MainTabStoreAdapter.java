package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ravimishra.tradzhub.Activity.ProductActivity;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class MainTabStoreAdapter extends RecyclerView.Adapter<MainTabStoreAdapter.viewholder> {

    private Context context;
    int[] mList;
    int[] imageArray = new int[9];
    List<TradzHubProductModel.ResponseData> productModel;

    public MainTabStoreAdapter(Context context, List<TradzHubProductModel.ResponseData> productModel) {
        this.context = context;
        this.productModel = productModel;
    }

    @NonNull
    @Override
    public MainTabStoreAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_store_row, parent, false);
        // itemView.setOnClickListener(mOnClickListener);
        return new MainTabStoreAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final TradzHubProductModel.ResponseData model = productModel.get(position);
        holder.productName.setText(model.title);
        holder.productPice.setText(model.salePrice);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);
        Glide.with(context).load(model.productImage).apply(options).into(holder.img);
        holder.itemView.setOnClickListener(v -> {
            Log.v("PRODUCT_TAG", "");
            Intent i = new Intent(context, ProductActivity.class);
            i.putExtra("PRODUCT", model);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return productModel.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView productName, productPice, productRating;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPice = itemView.findViewById(R.id.productPrice);
            // productRating = itemView.findViewById(R.id.ratingId);
        }
    }
}
