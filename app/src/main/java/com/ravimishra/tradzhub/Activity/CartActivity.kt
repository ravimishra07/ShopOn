package com.ravimishra.tradzhub.Activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ravimishra.tradzhub.Adapter.ProductivityDetailAdapter
import com.ravimishra.tradzhub.Model.Product
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
    private var totalPrice = 0
    private var diliveryCharge = 5.0
    private var itemCost = 0.0
    val productArray: MutableList<Product> = ArrayList()


    private var productModel: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartSummaryView.visibility = View.GONE
        addAddressbtn.setOnClickListener(this)
        cartBack.setOnClickListener(this)

        getCartProducts()
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
        val adapter = ProductivityDetailAdapter(this, productArray, 0)
        recyclerView.adapter = adapter
        tvShippingCost.text = "₹ 50"
        tvTotalCost.text = "₹ $totalPrice"
        tvGrandTotal.text = "₹"+(totalPrice + 50).toString()
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

    private fun getCartProducts() {
//        val intent = intent
//        productModel = intent.getSerializableExtra("PRODUCT") as Product
//        val category = productModel?.category
        // val categoryList = arrayOf("appliances","bestselling","electronics","fashion","featured","flashSale","grocery","justin","new","popular","sports")
//        if (category != null) {
        val database = FirebaseDatabase.getInstance("https://tradzhub-58133-default-rtdb.firebaseio.com")
        val myRef = database.getReference("product")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {

                    for (value in ds.children) {
                        val productHashMap: HashMap<String, Any> = value.value as HashMap<String, Any>
                        for (product in productHashMap.keys) {
                            if (product == "cart") {
                                if (productHashMap[product] == "1") {
                                    val id = productHashMap["id"] as Long
                                    val name = productHashMap["name"] as String
                                    val imgUrl = productHashMap["img_url"] as String
                                    val price = productHashMap["price"] as Long

                                    val discount = productHashMap["discount"] as Long
                                    val desc = productHashMap["desc"] as String
                                    val cart = productHashMap["cart"] as String
                                    val wishlist = productHashMap["wishlist"] as String
                                    val category = productHashMap["category"] as String


                                    var oriPrice = price.toFloat()
                                    var discPercent = discount.toFloat()
                                    var discountedPrc: Float = oriPrice * (1 - discPercent / 100)

                                    var orignalPrice = oriPrice.toInt()
                                    var discountPercent = discPercent.toInt()
                                    var discountedPrice = discountedPrc.toInt()



                                    totalPrice += discountedPrice
                                    val product = Product(id, name, price.toInt(), discount.toInt(), imgUrl, desc, wishlist, cart, category)
                                    productArray.add(product)

                                }

                                //   Toast.makeText(applicationContext, "Item added to cart", Toast.LENGTH_SHORT)
                            }
                        }
                    }
                }
                cartProgressbar.visibility = View.GONE
                if (productArray.size == 0) {
                    cartEmptyImg.visibility = View.VISIBLE
                    cartEmptyText.visibility = View.VISIBLE
                    cartSummaryView.visibility = View.GONE

                } else {
                    cartEmptyImg.visibility = View.GONE
                    cartEmptyText.visibility = View.GONE
                    cartSummaryView.visibility = View.VISIBLE

                }

                setUpRecyclerview()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Tag", "Failed to read value.", error.toException())
            }
        })

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
                latestModel = response.body()!!
                var resData = response.body()!!.data
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