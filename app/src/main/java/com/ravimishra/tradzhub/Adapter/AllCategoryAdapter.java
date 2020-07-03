package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.viewHolder> {
    private Context context;
    private List<CategoryModel.ResponseData> menuModel;

    public AllCategoryAdapter(Context context, List<CategoryModel.ResponseData> menuModel) {
        this.context = context;
        this.menuModel = menuModel;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_category_row, parent, false);
        return new AllCategoryAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final CategoryModel.ResponseData responseData = menuModel.get(position);
        holder.categoryName.setText(responseData.categoryName);
//        holder.subCategoryRecyclerView
    }

    @Override
    public int getItemCount() {
        return menuModel.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView directionName;
        RecyclerView subCategoryRecyclerView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tvProductname);
            directionName = itemView.findViewById(R.id.directionIcon);
            subCategoryRecyclerView = itemView.findViewById(R.id.subCatrecyclerView);
        }
    }
//    private  void callApiForSubCategory
}
