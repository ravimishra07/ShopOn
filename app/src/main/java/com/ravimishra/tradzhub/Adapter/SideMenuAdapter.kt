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
import com.ravimishra.tradzhub.Model.CategoryModel
import com.ravimishra.tradzhub.Model.Store
import com.ravimishra.tradzhub.R

class SideMenuAdapter(private val context: Context, var menuModel: List<Store>) : RecyclerView.Adapter<SideMenuAdapter.viewholder>() {
    var imageArray = IntArray(6)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val itemView: View
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.side_menu_store_row, parent, false)
        return viewholder(itemView)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val model = menuModel[position]
        holder.storeName.text = model.name

        holder.itemView.setOnClickListener { v: View? ->
//            val i = Intent(context, ItemDetailActivity::class.java)
//            i.putExtra("title", model.name)
//            //            i.putExtra("PRODUCT", latestModel);
//            i.putExtra("FROM", 1)
//            //model.categoryID
//            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        Log.v("side_nav", "size ----> " + menuModel.size)
        return menuModel.size
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var storeName: TextView

        init {
            storeName = itemView.findViewById(R.id.storeName)
        }
    }
}