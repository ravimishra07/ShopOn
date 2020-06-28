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
import com.ravimishra.tradzhub.Activity.ProductActivity;
import com.ravimishra.tradzhub.Model.NewProductModel;
import com.ravimishra.tradzhub.Model.ProductModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;

import java.util.List;


public class OnEAdpater extends RecyclerView.Adapter<OnEAdpater.viewholder> {

    private Context context;
    int[] mList;
    int[] imageArray = new int[9];
    List<TradzHubProductModel.ResponseData> productModel;

    public OnEAdpater(Context context, List<TradzHubProductModel.ResponseData> productModel) {
        this.context = context;
        this.productModel = productModel;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        imageArray[0] = R.drawable.banner4;
        imageArray[1] = R.drawable.pf4;
        imageArray[2] = R.drawable.pf3;
        imageArray[3] = R.drawable.pf4;
        imageArray[4] = R.drawable.pf3;
        imageArray[5] = R.drawable.pf1;
        imageArray[6] = R.drawable.pf1;
        imageArray[7] = R.drawable.pf4;
        imageArray[8] = R.drawable.pf3;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final TradzHubProductModel.ResponseData model = productModel.get(position);

        holder.img.setImageResource(imageArray[position]);

        holder.productName.setText(model.title);
        holder.productPice.setText(model.salePrice);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);
        Glide.with(context).load(model.productImage).apply(options).into(holder.img);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ProductActivity.class);
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
