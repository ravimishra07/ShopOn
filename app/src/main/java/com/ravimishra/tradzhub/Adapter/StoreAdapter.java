package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ravimishra.tradzhub.Activity.StoreActivity;
import com.ravimishra.tradzhub.Model.Store;
import com.ravimishra.tradzhub.Model.StoreModel;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.viewholder> {

    private Context context;
    int[] mList;
    int[] imageArray = new int[6];
    List<Store> responseData;

    public StoreAdapter(Context context, List<Store> response) {
        this.context = context;
        responseData = response;

    }

    @NonNull
    @Override
    public StoreAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_row, parent, false);
        return new StoreAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final Store model = responseData.get(position);

        holder.storeName.setText(model.getName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);
        Glide.with(context).load(model.getImgUrl()).apply(options).into(holder.img);
/*
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, StoreActivity.class);
            i.putExtra("type", 2);
            context.startActivity(i);
        });
        */
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, StoreActivity.class);
            i.putExtra("STORE", model);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return responseData.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView storeName;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.storeImage);
            storeName = itemView.findViewById(R.id.storeName);
        }
    }
}