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
import com.like.LikeButton;
import com.ravimishra.tradzhub.Activity.ProductActivity;
import com.ravimishra.tradzhub.Model.AuthModel;
import com.ravimishra.tradzhub.Model.Product;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.viewholder> {

    List<Product> menuModel;
    private Context context;

    public ProductDetailAdapter(Context context, List<Product> menuModel) {
        this.context = context;
        this.menuModel = menuModel;
    }

    @NonNull
    @Override
    public ProductDetailAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_store_row, parent, false);
        return new ProductDetailAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final Product model = menuModel.get(position);

        holder.productName.setText(model.getName());
        holder.productPrice.setText(model.getPrice()+"");

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);
        Glide.with(context).load(model.getImgUrl()).apply(options).into(holder.productImage);
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ProductActivity.class);
            i.putExtra("PRODUCT", model);
            context.startActivity(i);
        });
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // addDataToWishlist()
               //  holder.likeButton.setEnabled(false);
                if (holder.likeButton.isLiked()) {
                    holder.likeButton.setLiked(false);
                } else {
                    holder.likeButton.setLiked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuModel.size();
    }

    public void addDataToWishlist(int productId, int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        //Defining the user object as we need to pass it with the call

        /** defining the category api call */
        Call<AuthModel> callProductByCat = service.saveItemToWishlist(
                1,
                userId,
                productId
        );
        callProductByCat.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                Log.v("TAG_API", response.body() + "callFeaturedProducts api");
                String responseData = response.body().status;
                Log.v("WISHLIST_TAG", "status" + responseData);
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callFeaturedProducts");
            }
        });

    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productPrice;
        LikeButton likeButton;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            likeButton = itemView.findViewById(R.id.likeButton);

        }
    }
}

