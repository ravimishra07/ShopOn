package com.ravimishra.tradzhub.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.ravimishra.tradzhub.api.APIService
import com.ravimishra.tradzhub.api.APIUrl
import kotlinx.android.synthetic.main.content_cart.*
import kotlinx.android.synthetic.main.content_wishlist_activtry.*
import kotlinx.android.synthetic.main.content_wishlist_activtry.recyclerView
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
    val productArray: MutableList<Product> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_activtry)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        wishlistBack.setOnClickListener {
            finish()
        }
        //  callCartItemAPI(1)
        getWishListItems()
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

    fun getWishListItems() {

        val database = FirebaseDatabase.getInstance("https://tradzhub-58133-default-rtdb.firebaseio.com")
        val myRef = database.getReference("product")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {

                    for (value in ds.children) {
                        val productHashMap: HashMap<String, Any> = value.value as HashMap<String, Any>
                        for (product in productHashMap.keys) {
                            if (product == "wishlist") {
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
                wishListProgressbar.visibility = View.GONE
//                cartProgressbar.visibility = View.GONE
//                if (productArray.size == 0) {
//                    cartEmptyImg.visibility = View.VISIBLE
//                    cartEmptyText.visibility = View.VISIBLE
//                    cartSummaryView.visibility = View.GONE
//
//                } else {
//                    cartEmptyImg.visibility = View.GONE
//                    cartEmptyText.visibility = View.GONE
//                    cartSummaryView.visibility = View.VISIBLE
//
//                }

                setUpRecyclerview()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Tag", "Failed to read value.", error.toException())
            }
        })

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
        val adapter = ProductivityDetailAdapter(this, productArray, 1)
        recyclerView.adapter = adapter

    }
}