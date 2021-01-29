package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.viewholder> {

    int[] mList;
    int[] imageArray = new int[6];
    List<CategoryModel.ResponseData> menuModel;
    private final Context context;

    public SubCategoryAdapter(Context context, List<CategoryModel.ResponseData> menuModel) {
        this.context = context;
        this.menuModel = menuModel;
    }

    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_row, parent, false);
        return new SubCategoryAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.viewholder holder, int position) {
        final CategoryModel.ResponseData model = menuModel.get(position);
    }

    @Override
    public int getItemCount() {
        Log.v("side_nav", "size ----> " + menuModel.size());
        return menuModel.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        TextView tvSubCat;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvSubCat = itemView.findViewById(R.id.tvProductname);
        }
    }
}
