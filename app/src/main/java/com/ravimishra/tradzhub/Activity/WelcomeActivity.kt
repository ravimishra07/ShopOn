package com.ravimishra.tradzhub.Activity

import com.ravimishra.tradzhub.Utils.Constants.SHARED_CART_ITEM
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import android.widget.LinearLayout
import android.widget.TextView
import android.os.Bundle
import android.content.SharedPreferences
import android.os.Build
import com.ravimishra.tradzhub.R
import android.text.Html
import android.content.Intent
import android.graphics.Color
import com.ravimishra.tradzhub.Activity.FirstActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import android.view.WindowManager
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ravimishra.tradzhub.Utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private lateinit var dots: Array<TextView?>
    private lateinit var layouts: IntArray
    private var prefManager: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Checking for first time launch - before calling setContentView()
        prefManager = PreferenceManager(this)
        val preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val cartString = preferences.getString(SHARED_CART_ITEM, "")
        if (cartString === "0" || cartString === "") {
            val editor = preferences.edit()
            editor.putString(SHARED_CART_ITEM, "0")
            editor.apply()
        }
        if (!prefManager!!.isFirstTimeLaunch) {
            launchHomeScreen()
            finish()
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_welcome)


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4)

        // adding bottom dots
        addBottomDots(0)

        // making notification bar transparent
        changeStatusBarColor()
        myViewPagerAdapter = MyViewPagerAdapter()
        view_pager.setAdapter(myViewPagerAdapter)
        view_pager.addOnPageChangeListener(viewPagerPageChangeListener)
        btn_skip.setOnClickListener(View.OnClickListener { launchHomeScreen() })
        btn_next.setOnClickListener(View.OnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts.size) {
                // move to next screen
                view_pager.setCurrentItem(current)
            } else {
                launchHomeScreen()
            }
        })
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        layoutDots?.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            layoutDots?.addView(dots[i])
        }
        if (dots.size > 0) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun getItem(i: Int): Int {
        return view_pager!!.currentItem + i
    }

    private fun launchHomeScreen() {
        prefManager!!.isFirstTimeLaunch = false
        startActivity(Intent(this@WelcomeActivity, FirstActivity::class.java))
        finish()
    }

    //  viewpager change listener
    var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size - 1) {
                // last page. make button text to GOT IT
                btn_next!!.text = getString(R.string.start)
                btn_skip!!.visibility = View.GONE
            } else {
                // still pages are left
                btn_next!!.text = getString(R.string.next)
                btn_skip!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater!!.inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}