package com.ravimishra.tradzhub.Activity

import androidx.appcompat.app.AppCompatActivity
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel
import com.ravimishra.tradzhub.Model.TradzHubProductModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView
import android.os.Bundle
import com.ravimishra.tradzhub.R
import com.google.android.material.snackbar.Snackbar

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ravimishra.tradzhub.Adapter.ProductDetailAdapter
import com.ravimishra.tradzhub.Model.Product
import com.ravimishra.tradzhub.Utils.Constants
import retrofit2.Retrofit
import com.ravimishra.tradzhub.api.APIUrl
import retrofit2.converter.gson.GsonConverterFactory
import com.ravimishra.tradzhub.api.APIService
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.content_item_detail.*

import java.util.ArrayList

class ItemDetailActivity : AppCompatActivity() {
   // var toolbar: Toolbar? = null
    var tabRecyclerViewModel: List<TabRecyclerViewModel> = ArrayList()
    private var responseData: TradzHubProductModel? = null
  //  private var recyclerView: RecyclerView? = null
//    private var fab: FloatingActionButton? = null
//    private var backImageBtn: ImageView? = null
//    private var toolbarTitle: TextView? = null
//    private var cartImage: ImageView? = null

    //private int categoryID = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get Image ftom bundle
        val extras = intent.extras
        val titleText = extras!!.getString("title")
        setContentView(R.layout.activity_item_detail)
        //toolbar = findViewById(R.id.toolbar)
      //  toolbarTitle = findViewById(R.id.toolbar_title)
        toolbar_title.text = titleText
        setSupportActionBar(toolbar)
        toolbar?.title = ""

        val category = extras.getString("category")
        category?.let { setData(it) }

    }
    private fun setData(category: String) {

        val database = FirebaseDatabase.getInstance(Constants.BASE_FIREBASE_URL)
        val myRef = database.getReference("product").child(category)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val productArray: MutableList<Product> = ArrayList()
                for (ds in dataSnapshot.children) {
                    val id = ds.child("id").getValue(Long::class.java)!!
                    val name = ds.child("name").getValue(String::class.java)!!
                    val imgUrl = ds.child("img_url").getValue(String()::class.java)!!
                    val price = ds.child("price").getValue(Int::class.java)!!
                    val discount = ds.child("discount").getValue(Int::class.java)!!
                    val desc = ds.child("desc").getValue(String()::class.java)!!

                    val cart = ds.child("cart").getValue(String()::class.java)!!
                    val wishlist = ds.child("wishlist").getValue(String()::class.java)!!

                    val category = ds.child("category").getValue(String()::class.java)!!
                    val product =  Product(id,name,price,discount,imgUrl,desc,wishlist,cart,category)
                    productArray.add(product)
                }
                val gridLayoutManager = GridLayoutManager(this@ItemDetailActivity, 2, LinearLayoutManager.VERTICAL, false)
                recyclerView.layoutManager = gridLayoutManager
                val adapter = ProductDetailAdapter(this@ItemDetailActivity, productArray)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

    }

    private fun addFabButton() {
        fab!!.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

//    private fun setUpViews() {
//        recyclerView = findViewById(R.id.recyclerView)
//        fab = findViewById(R.id.fab)
//        cart!!.setOnClickListener { v: View? ->
//            val i = Intent(this, MainPage::class.java)
//            startActivity(i)
//        }
//        cartImage!!.setOnClickListener {
//            val i = Intent(this@ItemDetailActivity, CartActivity::class.java)
//            startActivity(i)
//        }
//        val gridLayoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
//        recyclerView?.setLayoutManager(gridLayoutManager)
//
////        ProductDetailAdapter adapter = new ProductDetailAdapter(this, responseData.data);
////        recyclerView.setAdapter(adapter);
//    }

}