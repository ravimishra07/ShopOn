package com.ravimishra.tradzhub.Adapter

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ravimishra.tradzhub.Model.Product
import com.ravimishra.tradzhub.Model.TradzHubProductModel
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.Utils.Constants.SHARED_CART_ITEM

class ProductivityDetailAdapter(private val context: Context, var productModel: List<Product>, val type: Int) : RecyclerView.Adapter<ProductivityDetailAdapter.viewholder>() {
    private val inStockOptions = arrayOf(0,1,2)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val itemView: View
        if (type == 0) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_rowe_item, parent, false)
        } else {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_row, parent, false)
        }
        return viewholder(itemView)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val model = productModel[position]
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
        Glide.with(context).load(model.imgUrl).apply(options).into(holder.img)
        var oriPrice = model.price.toFloat()
        var discPercent = model.discount.toFloat()
        var discountedPrc : Float = oriPrice * (1 - discPercent / 100)

        var orignalPrice = oriPrice.toInt()
        var discountPercent = discPercent.toInt()
        var discountedPrice  = discountedPrc.toInt()

//        val orignalp = modelData.salePrice
//        val orignalPvalue = orignalp.toFloat()
//        val purchaseP = modelData.purchasePrice
//        val purchasePvalue = purchaseP.toFloat()
//        val discountPrice = (orignalPvalue - purchasePvalue).toInt()
//        val percentDiscount = ((purchasePvalue / orignalPvalue) * 100).toInt()
       // val stockCount = modelData.currentStock.toInt()
        if (inStockOptions.random() != 0) {
            holder.tvAvailabilityText.text = "In Stock"
            holder.tvAvailabilityText.setTextColor(context.resources.getColor(R.color.green))
        } else {
            holder.tvAvailabilityText.text = "Out of stock"
            holder.tvAvailabilityText.setTextColor(context.resources.getColor(R.color.red))
        }
        val priceDiff = orignalPrice - discountedPrice
            holder.tvDiscountPrice.text = "$ $priceDiff"
            holder.tvOffPrice.visibility = View.VISIBLE
            holder.tvOffPrice.text = "$discountPercent% off"

        holder.productName.text = model.name
        holder.productPice.text = "₹ $discountedPrice"
        holder.orignalPrice.text = "₹ $orignalPrice"
        holder.tvOffPrice.text = "$discountPercent% off"
        holder.plusBtn.setOnClickListener(View.OnClickListener {
            var cartCount = holder.cartCount.text.toString().trim { it <= ' ' }.toInt()
            cartCount = cartCount + 1
            holder.cartCount.text = cartCount.toString() + ""
        })
        holder.minusBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {


            }
        })
        holder.removeImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {

            }
        })
    }

    override fun getItemCount(): Int {
        return productModel.size
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var productName: TextView
        var productPice: TextView
        var orignalPrice: TextView
        var tvDiscountPrice: TextView
        var tvOffPrice: TextView
        var tvAvailabilityText: TextView
        var cartCount: TextView
        var minusBtn: ImageView
        var plusBtn: ImageView
        var removeImage: ImageView

        init {
            img = itemView.findViewById(R.id.cartImage)
            productName = itemView.findViewById(R.id.itemName)
            productPice = itemView.findViewById(R.id.tvPrice)
            orignalPrice = itemView.findViewById(R.id.productOrignalPrice)
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice)
            tvOffPrice = itemView.findViewById(R.id.tvOffPrice)
            tvAvailabilityText = itemView.findViewById(R.id.tvAvailabilityText)
            cartCount = itemView.findViewById(R.id.cartCount)
            minusBtn = itemView.findViewById(R.id.minusBtn)
            plusBtn = itemView.findViewById(R.id.plusBtn)
            removeImage = itemView.findViewById(R.id.removeItemImage)
        }
    }

    companion object {
        private fun removeLastChar(str: String): String {
            return str.substring(0, str.length - 2)
        }
    }


}