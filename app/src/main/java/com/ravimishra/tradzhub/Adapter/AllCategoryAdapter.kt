package com.ravimishra.tradzhub.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ravimishra.tradzhub.Model.Category
import com.ravimishra.tradzhub.R

class AllCategoryAdapter(private val context: Context, private val menuModel: List<Category>) : RecyclerView.Adapter<AllCategoryAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView: View
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_row, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val model = menuModel.get(position)

        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
        Glide.with(context).load(model.ImgUrl).apply(options).into(holder.categoryImage)
        holder.categoryName.text = model.name
    }

    override fun getItemCount(): Int {
        return menuModel.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView
        var categoryImage: ImageView

        init {
            categoryName = itemView.findViewById(R.id.catName)
            categoryImage = itemView.findViewById(R.id.catImage)
        }
    }
}