package com.ravimishra.tradzhub.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ravimishra.tradzhub.Activity.ItemDetailActivity
import com.ravimishra.tradzhub.Model.Category
import com.ravimishra.tradzhub.R

class TopMenuAdapter(private val context: Context, var menuModel: List<Category>) : RecyclerView.Adapter<TopMenuAdapter.viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val itemView: View
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.top_menu_row, parent, false)
        return viewholder(itemView)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val (id, name, ImgUrl) = menuModel[position]
        holder.topMenuText.text = name
        Log.v("cat_id", "cat id $id")
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
        Glide.with(context).load(ImgUrl).apply(options).into(holder.img)
        val catId = id.toInt()
        holder.itemView.setOnClickListener { v: View? ->
            val i = Intent(context, ItemDetailActivity::class.java)
            i.putExtra("title", name)
            when (catId) {
                901 -> i.putExtra("category", "appliance")
                902 -> i.putExtra("category", "electronics")
                903 -> i.putExtra("category", "fashion")
                904 -> i.putExtra("category", "grocery")
                905 -> i.putExtra("category", "sports")
            }

            //model.categoryID
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return menuModel.size
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var topMenuText: TextView

        init {
            img = itemView.findViewById(R.id.topMenuImage)
            topMenuText = itemView.findViewById(R.id.topMenuName)
        }
    }
}