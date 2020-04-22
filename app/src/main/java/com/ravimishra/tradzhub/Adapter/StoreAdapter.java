package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Activity.ProductActivity;
import com.ravimishra.tradzhub.Activity.StoreActivity;
import com.ravimishra.tradzhub.R;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.viewholder> {

    private Context context;
    int mList[];
    int[] imageArray = new int[6];

    public StoreAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public StoreAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        imageArray[0] = R.drawable.store1;
        imageArray[1] = R.drawable.store2;
        imageArray[2] = R.drawable.store3;
        imageArray[3] = R.drawable.store4;
        imageArray[4] = R.drawable.store5;
        imageArray[5] = R.drawable.store6;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_row, parent, false);
        return new StoreAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.img.setImageResource(imageArray[position]);
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, StoreActivity.class);
            i.putExtra("type",2);
            context.startActivity(i);
        });
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;


        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.storeImage);


        }
    }    }