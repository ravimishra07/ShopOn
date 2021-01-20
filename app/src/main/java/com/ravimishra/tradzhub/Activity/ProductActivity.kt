package com.ravimishra.tradzhub.Activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ravimishra.tradzhub.Model.CategoryModel
import com.ravimishra.tradzhub.Model.Product
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.Utils.Constants.SHARED_CART_ITEM
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.cart_rowe_item.*
import kotlinx.android.synthetic.main.cart_rowe_item.cartImage
import kotlinx.android.synthetic.main.content_product.*
import kotlinx.android.synthetic.main.wishlist_row.*
import java.util.*

class ProductActivity : AppCompatActivity(), View.OnClickListener {
    var mDatabase: SQLiteDatabase? = null
    private var bottomBars: Array<TextView?>? = null

    private var Layout_bars: LinearLayout? = null
   // private var llAddToCart: LinearLayout? = null
    private var myvpAdapter: MyViewPagerAdapter? = null
    private val banner2: Drawable? = null
    private val banner3: Drawable? = null
    private val banner4: Drawable? = null
    private var tvProductName: TextView? = null
    private var tvProductPrice: TextView? = null
    private var tvProductDescription: TextView? = null
    private var tvInStock: TextView? = null
    private var tvOutStock: TextView? = null
    private var tvOrignalPrice: TextView? = null
    private var tvOffPrice: TextView? = null
    private var tvShippingCharges: TextView? = null
    private var scrollView: NestedScrollView? = null
    private var bottomLL: LinearLayout? = null
    private var progressBar: ProgressBar? = null
    private var productModel: Product? = null
    private val catResponseData: CategoryModel.ResponseData? = null
    private var value = 0
    private lateinit var stockArray: Array<Boolean>

    private val viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            ColoredBars(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }
//    private var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
       // viewPager = findViewById(R.id.viewPager)
//    stockArray[0] = true
//    stockArray[1] = false

    Layout_bars = findViewById(R.id.layoutBars)
        tvOrignalPrice = findViewById(R.id.productOrignalPrice)
        tvOffPrice = findViewById(R.id.tvOffPrice)
        tvInStock = findViewById(R.id.tvInstock)
        tvOutStock = findViewById(R.id.tvOutstock)
        tvShippingCharges = findViewById(R.id.tvShippingCharges)
        tvOffPrice = findViewById(R.id.tvOffPrice)

//        cartImage.setOnClickListener(this)
//        wishlistbtns.setOnClickListener(this)
//        llBuyNow.setOnClickListener(this)
//        llAddToCart.setOnClickListener(this)
//        backImageBtn.setOnClickListener(this)
//        tvBuyNow.setOnClickListener(this)
        bottomLL = findViewById(R.id.bottomLL)
        progressBar = findViewById(R.id.progressbar)
        scrollView = findViewById(R.id.scrollView)
        tvProductName = findViewById(R.id.productName)
        tvProductPrice = findViewById(R.id.productPrice)
        tvProductDescription = findViewById(R.id.productDescription)
        val extras = intent.extras
        value = extras!!.getInt("FROM")
        // if (value == 1) {
        setData()
        //  }
        myvpAdapter = MyViewPagerAdapter()
        viewPager.adapter = myvpAdapter
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        ColoredBars(1)
        showLoader()
    }

    private fun ColoredBars(thisScreen: Int) {
        val colorsInactive = resources.getIntArray(R.array.dot_on_page_not_active)
        val colorsActive = resources.getIntArray(R.array.dot_on_page_active)
        bottomBars = arrayOfNulls(3)
        Layout_bars!!.removeAllViews()
        for (i in bottomBars!!.indices) {
            bottomBars!![i] = TextView(this)
            bottomBars!![i]?.textSize = 40f
            bottomBars!![i]?.text = Html.fromHtml("&#8226;")
            Layout_bars!!.addView(bottomBars!![i])
            bottomBars!![i]?.setTextColor(colorsInactive[thisScreen])
        }
        if (bottomBars!!.isNotEmpty()) bottomBars!![thisScreen]?.setTextColor(colorsActive[thisScreen])
    }

    private fun showLoader() {
        progressBar!!.visibility = View.VISIBLE
        scrollView!!.visibility = View.GONE
        bottomLL!!.visibility = View.GONE
        val myThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(800)
                    runOnUiThread {
                        progressBar!!.visibility = View.GONE
                        scrollView!!.visibility = View.VISIBLE
                        bottomLL!!.visibility = View.VISIBLE
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        myThread.start()
    }

    private fun setData() {
        val intent = intent
        productModel = intent.getSerializableExtra("PRODUCT") as Product

        var orignalPrice = productModel?.price
        var discountPercent = productModel?.discount
        var discountedPrice = orignalPrice!!*(1-discountPercent!!/100)
        tvProductName!!.text = productModel!!.name
        tvProductPrice!!.text = "₹ $discountedPrice"
        tvOrignalPrice!!.text = "$ $orignalPrice"
        tvOffPrice?.text = "$discountPercent% off"
//        if (stockArray.random()) {
//            tvInStock!!.visibility = View.GONE
//            tvOutStock!!.visibility = View.VISIBLE
//        } else {
            tvOutStock!!.visibility = View.GONE
            tvInStock!!.visibility = View.VISIBLE
       // }
    }

    private fun getItem(i: Int): Int {
        return viewPager!!.currentItem + i
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> finish()
            R.id.llAddToCart -> addItemToCart()
            R.id.cart -> {
                val intent = Intent(this@ProductActivity, CartActivity::class.java)
                startActivity(intent)
            }
            R.id.ivWishlist -> {
                val intent1 = Intent(this@ProductActivity, WishlistActivtry::class.java)
                startActivity(intent1)
            }
        }
    }

    private fun addItemToCart() {
//        var cartItem: String
//        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
//        cartItem = preferences.getString(SHARED_CART_ITEM, "")
//        val editor = preferences.edit()
//        cartItem = cartItem + "," + responseData.productID
//        cartItem = cartItem.replace(",,", ",")
//        editor.putString(SHARED_CART_ITEM, cartItem)
//        editor.apply()
//        val cartString = preferences.getString(SHARED_CART_ITEM, "")
//        val cartArray = cartString!!.split(",").toTypedArray()
//        val cartItemArray: MutableList<Int> = ArrayList()
//        for (i in cartArray.indices) {
//            val item = cartArray[i].toInt()
//            if (!cartItemArray.contains(item)) {
//                cartItemArray.add(item)
//            }
       // }
        Toast.makeText(this, "Item added to cart!", Toast.LENGTH_SHORT).show()
        // Toast.makeText(this, "Added to cart "+cartString+ "converted string"+cartItemArray, Toast.LENGTH_SHORT).show();
    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        private var inflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater!!.inflate(R.layout.pager_item, container, false)
            val img = view.findViewById<ImageView>(R.id.productImage)
            //            if (value == 1) {
            val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.place_holder_image)
                    .error(R.drawable.place_holder_image)
            Glide.with(this@ProductActivity).load(productModel?.imgUrl).apply(options).into(img)
            //            }
            //  img.setImageDrawable(topBannerList.get(position));
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return 3
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val v = `object` as View
            container.removeView(v)
        }

        override fun isViewFromObject(v: View, `object`: Any): Boolean {
            return v === `object`
        }
    }

    companion object {
        const val DATABASE_NAME = "cartdatabase"
    }
}