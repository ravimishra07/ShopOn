package com.ravimishra.tradzhub.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.Utils.Constants;

import java.util.List;

public class ProductivityDetailAdapter extends RecyclerView.Adapter<ProductivityDetailAdapter.viewholder> {

    List<TradzHubProductModel> productModel;
    private Context context;
    private int type = 0;

    public ProductivityDetailAdapter(Context context, List<TradzHubProductModel> productModel, int type) {
        this.context = context;
        this.productModel = productModel;
        this.type = type;
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 2);
    }

    @NonNull
    @Override
    public ProductivityDetailAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (type == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rowe_item, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_row, parent, false);
        }
        return new ProductivityDetailAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final TradzHubProductModel model = productModel.get(position);

        TradzHubProductModel.ResponseData modelData = model.data.get(0);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);
        Glide.with(context).load(model.data.get(0).productImage).apply(options).into(holder.img);

        String orignalp = modelData.salePrice;
        Float orignalPvalue = Float.parseFloat(orignalp);
        String purchaseP = modelData.purchasePrice;
        Float purchasePvalue = Float.parseFloat(purchaseP);

        int discountPrice = (int) (orignalPvalue - purchasePvalue);
        int percentDiscount = (int) ((purchasePvalue / orignalPvalue) * 100);
        int stockCount = Integer.parseInt(modelData.currentStock);
        if (stockCount != 0) {
            holder.tvAvailabilityText.setText("In Stock");
            holder.tvAvailabilityText.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.tvAvailabilityText.setText("Out of stock");
            holder.tvAvailabilityText.setTextColor(context.getResources().getColor(R.color.red));
        }

        if (discountPrice > 0) {
            holder.tvDiscountPrice.setText("$ " + discountPrice);
            holder.tvOffPrice.setVisibility(View.VISIBLE);
            holder.tvOffPrice.setText(percentDiscount + "% off");
        } else {
            holder.tvDiscountPrice.setText("$0");
            holder.tvOffPrice.setVisibility(View.GONE);
        }

        holder.productName.setText(modelData.title);
        holder.productPice.setText("$ " + modelData.purchasePrice);
        holder.orignalPrice.setText("$ " + modelData.salePrice);
        holder.tvOffPrice.setText(percentDiscount + "% off");
        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(holder.cartCount.getText().toString().trim());
                cartCount = cartCount + 1;
                holder.cartCount.setText(cartCount + "");
            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(holder.cartCount.getText().toString().trim());
                if (cartCount != 0) {
                    cartCount = cartCount - 1;
                }
                holder.cartCount.setText(cartCount + "");
            }
        });
        holder.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = Integer.parseInt(modelData.productID);
                String cartItem;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                cartItem = preferences.getString(Constants.INSTANCE.getSHARED_CART_ITEM(), "");
                SharedPreferences.Editor editor = preferences.edit();

                String cartString = preferences.getString(Constants.INSTANCE.getSHARED_CART_ITEM(), "");
                String updatedCart = cartString.replace(modelData.productID, "");
                String updatedCartString = updatedCart.replace(",,", ",");
                String lastItem = updatedCartString.substring(updatedCartString.length() - 1);
                if (lastItem == ",") {
                    updatedCartString = removeLastChar(updatedCartString);
                }
                editor.putString(Constants.INSTANCE.getSHARED_CART_ITEM(), updatedCartString);
                editor.apply();
                Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(0, 0);
                context.startActivity(((Activity) context).getIntent());
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModel.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView productName, productPice, orignalPrice, tvDiscountPrice, tvOffPrice, tvAvailabilityText, cartCount;
        ImageView minusBtn, plusBtn, removeImage;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.cartImage);
            productName = itemView.findViewById(R.id.itemName);
            productPice = itemView.findViewById(R.id.tvPrice);
            orignalPrice = itemView.findViewById(R.id.productOrignalPrice);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            tvOffPrice = itemView.findViewById(R.id.tvOffPrice);
            tvAvailabilityText = itemView.findViewById(R.id.tvAvailabilityText);
            cartCount = itemView.findViewById(R.id.cartCount);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            removeImage = itemView.findViewById(R.id.removeItemImage);
        }
    }
}
