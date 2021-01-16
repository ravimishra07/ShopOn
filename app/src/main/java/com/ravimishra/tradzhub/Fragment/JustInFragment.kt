package com.ravimishra.tradzhub.Fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.ravimishra.tradzhub.Adapter.OnEAdpater
import com.ravimishra.tradzhub.Adapter.ProductDetailAdapter
import com.ravimishra.tradzhub.Adapter.TopMenuAdapter
import com.ravimishra.tradzhub.Model.Category
import com.ravimishra.tradzhub.Model.Product
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel
import com.ravimishra.tradzhub.Model.TradzHubProductModel
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.Utils.Constants
import com.ravimishra.tradzhub.api.APIService
import com.ravimishra.tradzhub.api.APIUrl
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class JustInFragment : Fragment {
    var recyclerView: RecyclerView? = null
    var tradzHubProductModels: List<TradzHubProductModel> = ArrayList()
    var tabRecyclerViewModel: List<TabRecyclerViewModel> = ArrayList()
    private var model: TradzHubProductModel? = null
    private var root: View? = null

    constructor() {
        // Required empty public constructor
    }

    constructor(tradzHubProductModels: List<TradzHubProductModel>) {
        this.tradzHubProductModels = tradzHubProductModels
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_just_in, container, false)
        recyclerView = root?.findViewById(R.id.recyclerView)
        setData()
        return root
    }
    private fun setData(){

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
                    val product =  Product(id,name,price,discount,imgUrl)
                    productArray.add(product)
                }
                val gridLayoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)

                recyclerView?.layoutManager = gridLayoutManager//LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                val adapter = ProductDetailAdapter(activity, productArray)
                recyclerView?.adapter = adapter
                root?.popularViewAllBtn?.isEnabled = true

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })

    }
/*
    private fun callApi() {
        //building retrofit object
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //Defining retrofit api service
        val service = retrofit.create(APIService::class.java)
        //Defining the user object as we need to pass it with the call
        /** defining the category api call  */
        val callCategoryModel = service.getCategory(
                1
        )
        /** calling  api to get banner images */
        val callBannerImgApi = service.getBannerImages(
                1
        )
        /** defining the latest product api call  */
        val callFeaturedProducts = service.getFeaturedProducts(
                1
        )
        /** defining the latest product api call  */
        val callLatestProducts = service.getLatestProducts(
                1
        )
        /** defining the latest product api call  */
        val callRecentlyViewedProducts = service.getRecentlyViewedProducts(
                1
        )
        /** defining mostly viewed(popular) product api call  */
        val callMostlyViewedproducts = service.getMostlyViewedProducts(
                1
        )
        callFeaturedProducts.enqueue(object : Callback<TradzHubProductModel> {
            override fun onResponse(call: Call<TradzHubProductModel>, response: Response<TradzHubProductModel>) {
                Log.v("TAG_API", response.body().toString() + "callFeaturedProducts api")
                model = response.body()
                val adapter = ProductDetailAdapter(context, model.data)
                recyclerView!!.adapter = adapter
                // featuredBtn.setEnabled(true);
                // featuredProgressBar.setVisibility(View.GONE);
            }

            override fun onFailure(call: Call<TradzHubProductModel>, t: Throwable) {
                Log.v("TAG_API", "Some error occured callFeaturedProducts")
            }
        })
    }
    */
}