package com.ravimishra.tradzhub.Activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ravimishra.tradzhub.Adapter.SideMenuAdapter
import com.ravimishra.tradzhub.Adapter.StoreAdapter
import com.ravimishra.tradzhub.Fragment.HomeFragment
import com.ravimishra.tradzhub.Fragment.ShowAllCategoryFragment
import com.ravimishra.tradzhub.Model.CategoryModel
import com.ravimishra.tradzhub.Model.Store
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.Utils.Constants
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.app_bar_main_page.*
import kotlinx.android.synthetic.main.content_main_page.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.ArrayList

class MainPage : AppCompatActivity(), View.OnClickListener {

    private lateinit var sideMenuRecyclerView: RecyclerView
    private var username = "Guest user"
    private var isLoggedIn = false
    private lateinit var sideMenuModel: CategoryModel
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainPage)
        val name = preferences.getString(Constants.SHARED_USERNAME, "")
        if (!name.equals("", ignoreCase = true) && name != null) {
            Log.v("email_tag", name)
            isLoggedIn = true
            username = name
        }

        rlHome.setOnClickListener(this)
        rlShopCategories.setOnClickListener(this)
        rlStore.setOnClickListener(this)
        ivCart.setOnClickListener(this)
        ivWishlist.setOnClickListener(this)

        if (isLoggedIn) {
            tvLogin.text = getString(R.string.logout)
            tvName.text = "Hello, $username"
            Toast.makeText(this, "Signed in as $username", Toast.LENGTH_SHORT).show()
        } else {
            tvLogin.text = getString(R.string.login_text)
            tvName.text = getString(R.string.guest)
            Toast.makeText(this, username, Toast.LENGTH_SHORT).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setUpFragment()
        showLoader()
        tvLogin!!.setOnClickListener(this)
        setStoreProduct()
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
    }


    private fun showLoader() {
        progressbar.visibility = View.VISIBLE
        container.visibility = View.GONE
        val myThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(800)
                    runOnUiThread {
                        progressbar.visibility = View.GONE
                        container.visibility = View.VISIBLE
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        myThread.start()
    }

    private fun setUpFragment() {
        fragmentManager = supportFragmentManager
        val fragment: Fragment = HomeFragment()
        fragmentManager.popBackStack(fragment.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val tx = fragmentManager.beginTransaction()
        tx.replace(R.id.container, fragment).addToBackStack(fragment.toString())
        tx.commit()
        //====to clear unused memory==
        System.gc()
    }

    private fun setUpCatFragment() {
        fragmentManager = supportFragmentManager
        val fragment: Fragment = ShowAllCategoryFragment()
        fragmentManager.popBackStack(fragment.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val tx = fragmentManager.beginTransaction()
        tx.replace(R.id.container, fragment).addToBackStack(fragment.toString())
        tx.commit()
        //====to clear unused memory==
        System.gc()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlHome -> {
                drawer_layout.closeDrawer(GravityCompat.START)
                finish()
                startActivity(intent)
            }
            R.id.rlShopCategories -> {
                drawer_layout.closeDrawer(GravityCompat.START)
                setUpCatFragment()
            }
            R.id.rlStore -> if (storeLinearLayout.visibility == View.VISIBLE) {
                storeArrow.rotation = 0f
                storeLinearLayout.visibility = View.GONE
            } else {
                storeArrow.rotation = 180f
                storeLinearLayout.visibility = View.VISIBLE
            }
            R.id.ivCart -> {
                val i = Intent(this, CartActivity::class.java)
                startActivity(i)
            }
            R.id.ivWishlist -> {
                val intent3 = Intent(this, WishlistActivtry::class.java)
                startActivity(intent3)
            }
            R.id.tvLogin -> if (isLoggedIn) {
                val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainPage)
                val editor = preferences.edit()
                editor.clear()
                editor.commit()
                isLoggedIn = false
                val intent = Intent(this, FirstActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val i2 = Intent(this, LogActivity::class.java)
                startActivity(i2)
            }
        }
    }
    private  fun setStoreProduct(){

        val database = FirebaseDatabase.getInstance(Constants.BASE_FIREBASE_URL)
        val myRef = database.getReference("store")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val storeArray: MutableList<Store> = ArrayList()
                for (ds in dataSnapshot.children) {
                    val id = ds.child("id").getValue(Long::class.java)!!
                    val name = ds.child("name").getValue(String::class.java)!!
                    val imgUrl = ds.child("imgUrl").getValue(String()::class.java)!!
                    val storeDesc = ds.child("storeDesc").getValue(String()::class.java)!!

                    val store =  Store(id,name,imgUrl,storeDesc)
                    storeArray.add(store)
                }
                storeListRecyclerView?.layoutManager = LinearLayoutManager(this@MainPage, LinearLayoutManager.VERTICAL, false)
                val adapter = SideMenuAdapter(this@MainPage,storeArray)
                storeListRecyclerView?.adapter = adapter


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("HomeFragment", "Failed to read value.", error.toException())
            }
        })

    }

    companion object {
        private const val TAG = "MainPage"
    }
}
