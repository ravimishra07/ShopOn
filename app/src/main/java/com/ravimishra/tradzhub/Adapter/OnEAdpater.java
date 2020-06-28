package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.ravimishra.tradzhub.Model.NewProductModel;
import com.ravimishra.tradzhub.Model.ProductModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;

import java.io.Serializable;
import java.util.List;


public class OnEAdpater extends RecyclerView.Adapter<OnEAdpater.viewholder> {

    private Context context;
    int[] mList;
    List<TradzHubProductModel.ResponseData> productModel;

    public OnEAdpater(Context context, List<TradzHubProductModel.ResponseData> productModel) {
        this.context = context;
        this.productModel = productModel;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        return new viewholder(itemView);
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
            Intent i = new Intent(context, ProductActivity.class);
           // i.putExtra("PRODUCT", (Parcelable) productModel);
            i.putExtra("PRODUCT",  model);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return productModel.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView productName, productPice;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPice = itemView.findViewById(R.id.productPrice);


        }
    }
}
