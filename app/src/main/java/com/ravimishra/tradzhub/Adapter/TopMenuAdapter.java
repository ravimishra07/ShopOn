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
import com.ravimishra.tradzhub.Model.Category;
import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.R;

import java.util.List;


public class TopMenuAdapter extends RecyclerView.Adapter<TopMenuAdapter.viewholder> {

    private Context context;
    List<Category> menuModel;

    public TopMenuAdapter(Context context,  List<Category> menuModel) {
        this.context = context;
        this.menuModel = menuModel;
    }

    @NonNull
    @Override
    public TopMenuAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_menu_row, parent, false);
        return new TopMenuAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final Category model = menuModel.get(position);

        holder.topMenuText.setText(model.getName());
        Log.v("cat_id", "cat id " + model.getId());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);

        Glide.with(context).load(model.getImgUrl()).apply(options).into(holder.img);
        int catId = (int) model.getId();
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ItemDetailActivity.class);
            i.putExtra("title", model.getName());
            switch (catId){
                case 901:
                    i.putExtra("category", "appliance");
                    break;
                case 902:
                    i.putExtra("category", "electronics");
                    break;
                case 903:
                    i.putExtra("category", "fashion");
                    break;
                case 904:
                    i.putExtra("category", "grocery");
                    break;
                case 905:
                    i.putExtra("category", "sports");
                    break;
            }

            //model.categoryID
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return menuModel.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView topMenuText;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.topMenuImage);
            topMenuText = itemView.findViewById(R.id.topMenuName);

        }
    }
}
