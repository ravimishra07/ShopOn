package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Model.ProductModel;
import com.ravimishra.tradzhub.R;

import java.util.List;


public class OnEAdpater extends RecyclerView.Adapter<OnEAdpater.viewholder> {

    private Context context;
    int mList[];
    int[] imageArray = new int[9];
    List<ProductModel> productModel;
    public OnEAdpater(Context context, List<ProductModel> productModel) {
        this.context = context;
        this.productModel = productModel;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final ProductModel model = productModel.get(position);

        holder.img.setImageResource(imageArray[position]);
        int price = model.getProductPrice();
        String priceString = String.valueOf(price);
        holder.productName.setText(model.getProductName());
        holder.productPice.setText(priceString);


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
