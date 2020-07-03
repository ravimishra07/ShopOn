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
import com.ravimishra.tradzhub.Activity.ItemDetailActivity;
import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.viewholder> {

    int[] mList;
    int[] imageArray = new int[6];
    List<CategoryModel.ResponseData> menuModel;
    private Context context;

    public SideMenuAdapter(Context context, List<CategoryModel.ResponseData> menuModel) {
        this.context = context;
        this.menuModel = menuModel;
    }

    @NonNull
    @Override
    public SideMenuAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_side_menu_row, parent, false);
        return new SideMenuAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        final CategoryModel.ResponseData model = menuModel.get(position);

        holder.sideMenuText.setText(model.categoryName);
        Log.v("side_nav", "cat id " + model.categoryID);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);

        Glide.with(context).load(model.getCategoryImage).apply(options).into(holder.img);
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ItemDetailActivity.class);
            i.putExtra("title", model.categoryName);
//            i.putExtra("PRODUCT", latestModel);
            i.putExtra("FROM", 1);
            i.putExtra("CATEGORY_ID", model.categoryID);
            //model.categoryID
            context.startActivity(i);
        });

    }


    @Override
    public int getItemCount() {
        Log.v("side_nav", "size ----> " + menuModel.size());

        return menuModel.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView sideMenuText;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.cat_img);
            sideMenuText = itemView.findViewById(R.id.catTitle);

        }
    }
}
