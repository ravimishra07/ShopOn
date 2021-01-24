package com.ravimishra.tradzhub.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ravimishra.tradzhub.Adapter.ProductivityDetailAdapter
import com.ravimishra.tradzhub.Model.ProductDetailModel
import com.ravimishra.tradzhub.Model.TradzHubProductModel
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.api.APIService
import com.ravimishra.tradzhub.api.APIUrl
import kotlinx.android.synthetic.main.content_wishlist_activtry.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class WishlistActivtry : AppCompatActivity() {
    var productDetailModels: MutableList<ProductDetailModel> = ArrayList()
    private lateinit var latestModel: TradzHubProductModel
    private var cartModelArray = mutableListOf<TradzHubProductModel>()
    private var cartArrayCount = 0
    private val cartItemArray: MutableList<Int> = ArrayList()
    private var totalPrice = 0.0
    private var diliveryCharge = 0.0
    private var itemCost = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_activtry)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        wishlistBack.setOnClickListener {
            finish()
        }
        callCartItemAPI(1)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

//        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        productDetailModels.add(ProductDetailModel("$ 799", "Macbook air", "128 ssd", "4 business days"))
//        productDetailModels.add(ProductDetailModel("$ 499", "iPhone 8", "32 gb", "3 business days"))
//        //productDetailModels.add(new ProductDetailModel("$ 499","iPhone 8","32 gb","3 business days"));
//        val adapter = WishListAdapter(this@WishlistActivtry, productDetailModels)
//        recyclerView.setAdapter(adapter)
//        val fab = findViewById<FloatingActionButton>(R.id.fab)
//        fab.setOnClickListener { view: View? ->
//            Snackbar.make(view!!, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    fun callCartItemAPI(productId: Int) {
        //building retrofit object
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        //Defining retrofit api service
        val service = retrofit.create(APIService::class.java)

        //defining the call
        val getcartItem = service.getWishlistData(
                1,
                productId
        )

        getcartItem.enqueue(object : Callback<TradzHubProductModel> {
            override fun onResponse(call: Call<TradzHubProductModel>, response: Response<TradzHubProductModel>) {
                Log.v("TAG_API", response.body().toString() + "callLatestProducts api")
                latestModel = response.body()
                cartModelArray.add(latestModel)
                if (cartArrayCount == cartItemArray.size) {
                    wishListProgressbar.visibility = View.GONE
                    setUpRecyclerview()
                }
            }

            override fun onFailure(call: Call<TradzHubProductModel>, t: Throwable) {
                Log.v("TAG_API", "Some error occured callLatestProducts")
            }
        })
    }

    fun setUpRecyclerview() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
//        val adapter = ProductivityDetailAdapter(this, cartModelArray, 1)
//        recyclerView.adapter = adapter

    }
}