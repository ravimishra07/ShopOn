package com.ravimishra.tradzhub.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ravimishra.tradzhub.Adapter.AllCategoryAdapter
import com.ravimishra.tradzhub.Adapter.TopMenuAdapter
import com.ravimishra.tradzhub.Model.Category
import com.ravimishra.tradzhub.Model.CategoryModel
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.api.APIService
import com.ravimishra.tradzhub.api.APIUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class ShowAllCategoryFragment : Fragment() {
    var catRecyclerView: RecyclerView? = null
    private var menuData: CategoryModel? = null
    private var progressbar: ProgressBar? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_show_all_category, container, false)
        catRecyclerView = view.findViewById(R.id.allCategoryRecyclerview)
        progressbar = view.findViewById(R.id.progresbar)
        // callApiForCategories();
        return view
    }
    private fun setCategoryData(){
        val database = FirebaseDatabase.getInstance("https://tradzhub-58133-default-rtdb.firebaseio.com")
        val myRef = database.getReference("categories")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val catModel: MutableList<Category> = ArrayList()
                for (ds in dataSnapshot.children) {
                    val id = ds.child("id").getValue(Long::class.java)!!
                    val name = ds.child("name").getValue(String::class.java)!!
                    val imgUrl = ds.child("imgUrl").getValue(String::class.java)!!
                    val cat = Category(id, name, imgUrl)
                    catModel.add(cat)
                }

                catRecyclerView!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                val showAllCategoryFragment = context?.let { AllCategoryAdapter(it, catModel) }
                catRecyclerView!!.adapter = showAllCategoryFragment
                progressbar!!.visibility = View.GONE

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e("ShowAllCategoryFragment", "Failed to read value.", error.toException())
            }
        })
    }
}