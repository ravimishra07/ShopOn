package com.ravimishra.tradzhub.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.ravimishra.tradzhub.Activity.MainPage
import com.ravimishra.tradzhub.Adapter.TabAdapter
import com.ravimishra.tradzhub.Fragment.StoreFollowersFragment
import com.ravimishra.tradzhub.Fragment.StoreProductFragment
import com.ravimishra.tradzhub.Fragment.StoreReviewFragment
import com.ravimishra.tradzhub.Model.Store
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel
import com.ravimishra.tradzhub.Model.TradzHubProductModel
import com.ravimishra.tradzhub.R
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.content_store.*
import java.util.*

class StoreActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var recyclerView: RecyclerView? = null
    var tabRecyclerViewModel: List<TabRecyclerViewModel> = ArrayList()
    var bundle: Bundle? = null
    var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    var appBarLayout: AppBarLayout? = null
    var frameLayout: FrameLayout? = null
    var progressBar: ProgressBar? = null
    var responseData: Store? = null
    var productResponseData: TradzHubProductModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent
        responseData = i.getSerializableExtra("STORE") as Store
        setContentView(R.layout.activity_store)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout)
        val toolbarImageView = findViewById<ImageView>(R.id.toolbarImage)
       // toolbar_layout.setTitle(responseData!!.name)
      //  backImageBtn = findViewById(R.id.backImageBtn)
        frameLayout = findViewById(R.id.mainFrame)
        progressBar = findViewById(R.id.progressbar)
        appBarLayout = findViewById(R.id.app_bar)

//        if (value == 1) {
//            //bundle.putString("type", "3");
//            toolbarImageView.setImageDrawable(banner2);
//
//        } else if (value == 2) {
//            toolbarImageView.setImageDrawable(banner3);
//
//        } else if (value == 3) {
//            toolbarImageView.setImageDrawable(banner4);
//        } else {
//            toolbarImageView.setImageDrawable(banner3);
//        }
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
        Glide.with(this).load(responseData!!.ImgUrl).apply(options).into(toolbarImageView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view: View? ->
            Snackbar.make(view!!, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        toolbar.title = responseData!!.name
        backImageBtn.setOnClickListener(View.OnClickListener {
            val i = Intent(this@StoreActivity, MainPage::class.java)
            startActivity(i)
        })
        tabs = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewpager)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        bundle = Bundle()
        showLoader()
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val store_id = responseData!!.id
        val adapter = TabAdapter(supportFragmentManager)
        val storeProductFragment = StoreProductFragment(responseData!!.id)
        storeProductFragment.arguments = bundle
        adapter.addFragment(storeProductFragment, "Store Product")
        adapter.addFragment(StoreReviewFragment(), "Store Review")
        adapter.addFragment(StoreFollowersFragment(), "Followers")
        viewPager!!.adapter = adapter
    }

    private fun showLoader() {
        progressBar!!.visibility = View.VISIBLE
        appBarLayout!!.visibility = View.GONE
        frameLayout!!.visibility = View.GONE
        tabs!!.visibility = View.GONE
        val myThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(800)
                    runOnUiThread {
                        progressBar!!.visibility = View.GONE
                        appBarLayout!!.visibility = View.VISIBLE
                        frameLayout!!.visibility = View.VISIBLE
                        tabs!!.visibility = View.VISIBLE
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        myThread.start()
    }
}