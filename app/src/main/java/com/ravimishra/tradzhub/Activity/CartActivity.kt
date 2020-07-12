package com.ravimishra.tradzhub.Activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ravimishra.tradzhub.Adapter.ProductivityDetailAdapter
import com.ravimishra.tradzhub.Model.ProductDetailModel
import com.ravimishra.tradzhub.Model.TradzHubProductModel
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.Utils.Constants
import com.ravimishra.tradzhub.api.APIService
import com.ravimishra.tradzhub.api.APIUrl
import kotlinx.android.synthetic.main.content_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class CartActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var latestModel: TradzHubProductModel
    private var cartModelArray = mutableListOf<TradzHubProductModel>()
    private var cartArrayCount = 0
    private val cartItemArray: MutableList<Int> = ArrayList()
    private val productDetailModels: MutableList<ProductDetailModel> = ArrayList()
    private var totalPrice = 0.0
    private var diliveryCharge = 0.0
    private var itemCost = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartSummaryView.visibility = View.GONE
        addAddressbtn.setOnClickListener(this)
        cartBack.setOnClickListener(this)
        getCartItems()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.addAddressbtn -> {
                val intent = Intent(this@CartActivity, AddAddressActivity::class.java)
                startActivity(intent)
            }
            R.id.cartBack -> {
                finish()

            }
        }
    }

    fun setUpRecyclerview() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val adapter = ProductivityDetailAdapter(this, cartModelArray, 0)
        recyclerView.adapter = adapter
        cartSummaryView.visibility = View.VISIBLE
        tvShippingCost.text = diliveryCharge.toString()
        tvTotalCost.text = itemCost.toString()
        tvGrandTotal.text = (itemCost + diliveryCharge).toString()
    }

    fun getCartItems() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val cartString = preferences.getString(Constants.SHARED_CART_ITEM, "")
        val cartArray = cartString!!.split(",").toTypedArray()

        for (i in cartArray.indices) {
            if (cartArray[i] != "") {
                val item = cartArray[i].toInt()

                if (item > 0) {
                    if (!cartItemArray.contains(item)) {
                        cartItemArray.add(item)
                        callCartItemAPI(item)
                    }
                } else {
                    cartProgressbar.visibility = View.GONE
                }
            }
        }
        if (cartItemArray.size == 0) {
            cartProgressbar.visibility = View.GONE
            cartEmptyImg.visibility = View.VISIBLE
            cartEmptyText.visibility = View.VISIBLE
            //Toast.makeText(this@CartActivity, "Cart is empty", Toast.LENGTH_SHORT).show()
        } else {
            cartEmptyImg.visibility = View.GONE
            cartEmptyText.visibility = View.GONE
        }
    }

    fun callCartItemAPI(productId: Int) {
        cartArrayCount++
        //building retrofit object
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        //Defining retrofit api service
        val service = retrofit.create(APIService::class.java)

        //defining the call
        val getcartItem = service.getItemByProductID(
                1,
                productId
        )

        getcartItem.enqueue(object : Callback<TradzHubProductModel> {
            override fun onResponse(call: Call<TradzHubProductModel>, response: Response<TradzHubProductModel>) {
                Log.v("TAG_API", response.body().toString() + "callLatestProducts api")
                latestModel = response.body()
                var resData = response.body().data
                if (resData != null) {
                    var purchasePrice: Double? = latestModel.data[0].purchasePrice.toDouble()
                    var shippingCost: Double? = latestModel.data[0].shippingCost.toDouble()
                    if (purchasePrice != null) {
                        itemCost += purchasePrice
                    }
                    if (shippingCost != null) {
                        diliveryCharge += shippingCost
                    }
                    cartModelArray.add(latestModel)
                }

                if (cartArrayCount == cartItemArray.size) {
                    cartProgressbar.visibility = View.GONE
                    setUpRecyclerview()
                }
            }

            override fun onFailure(call: Call<TradzHubProductModel>, t: Throwable) {
                Log.v("TAG_API", "Some error occured callLatestProducts")
            }
        })
    }


}