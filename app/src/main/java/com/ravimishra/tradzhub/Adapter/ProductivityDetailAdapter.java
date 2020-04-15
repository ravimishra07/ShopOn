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

import com.ravimishra.tradzhub.Model.ProductDetailModel;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class ProductivityDetailAdapter extends RecyclerView.Adapter<ProductivityDetailAdapter.viewholder> {

    private Context context;
    int[] mList;
    int[] imageArray = new int[9];
    List<ProductDetailModel> productModel;

    public ProductivityDetailAdapter(Context context, List<ProductDetailModel> productModel) {
        this.context = context;
        this.productModel = productModel;
    }

    @NonNull
    @Override
    public ProductivityDetailAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        imageArray[0] = R.drawable.pf1;
        imageArray[1] = R.drawable.pf4;
        imageArray[2] = R.drawable.pf3;
        imageArray[3] = R.drawable.pf4;
        imageArray[4] = R.drawable.pf3;
        imageArray[5] = R.drawable.pf1;
        imageArray[6] = R.drawable.pf1;
        imageArray[7] = R.drawable.pf4;
        imageArray[8] = R.drawable.pf3;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rowe_item, parent, false);
        return new ProductivityDetailAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final ProductDetailModel model = productModel.get(position);
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

            img = itemView.findViewById(R.id.cartImage);
            productName = itemView.findViewById(R.id.itemName);
            productPice = itemView.findViewById(R.id.productPrice);


        }
    }
}
