package com.ravimishra.tradzhub.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ravimishra.tradzhub.Adapter.ProductDetailAdapter
import com.ravimishra.tradzhub.Model.Product
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel
import com.ravimishra.tradzhub.Model.TradzHubProductModel
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.Utils.Constants
import java.util.*

class StoreProductFragment : Fragment {
    var recyclerView: RecyclerView? = null
    var tabRecyclerViewModel: MutableList<TabRecyclerViewModel> = ArrayList()
    var productResponseData: TradzHubProductModel? = null
    var storeID: Long? = null

    constructor() {
        // Required empty public constructor
    }

    constructor(storeId: Long?) {
        storeID = storeId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_product, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        getData()
        return view
    }

    fun getData() {

        val database = FirebaseDatabase.getInstance(Constants.BASE_FIREBASE_URL)
        val myRef = database.getReference("product").child("justin")
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
                    val product = Product(id, name, price, discount, imgUrl, desc, wishlist, cart, category)
                    productArray.add(product)
                }
                val gridLayoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)

                recyclerView?.layoutManager = gridLayoutManager//LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                val adapter = ProductDetailAdapter(activity, productArray)
                recyclerView?.adapter = adapter


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })

    }
/*
    private fun getStoreProducts(storeID: Int) {
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //Defining retrofit api service
        val service = retrofit.create(APIService::class.java)
        //Defining the user object as we need to pass it with the call
        /** defining the category api call  */
        val callProductByCat = service.getStoreProducts(
                1,
                storeID
        )
        callProductByCat.enqueue(object : Callback<TradzHubProductModel> {
            override fun onResponse(call: Call<TradzHubProductModel>, response: Response<TradzHubProductModel>) {
                Log.v("TAG_API", response.body().toString() + "callFeaturedProducts api")
                productResponseData = response.body()
                val gridLayoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
                recyclerView!!.layoutManager = gridLayoutManager
                val adapter = MainTabStoreAdapter(activity, productResponseData.data)
                recyclerView!!.adapter = adapter
                tabRecyclerViewModel.clear()
            }

            override fun onFailure(call: Call<TradzHubProductModel>, t: Throwable) {
                Log.v("TAG_API", "Some error occured callFeaturedProducts")
            }
        })
    }
    */
}