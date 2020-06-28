package com.ravimishra.tradzhub.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ravimishra.tradzhub.Adapter.BannerAddapter;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    TextView[] bottomBars;
    LinearLayout Layout_bars;
    MyViewPagerAdapter myvpAdapter;
    Drawable banner2, banner3, banner4;

    NestedScrollView scrollView;
    LinearLayout bottomLL;
    ProgressBar progressBar;

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            ColoredBars(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private int[] banner = {R.drawable.pf1, R.drawable.pf2, R.drawable.pf3};
    private ViewPager viewPager;
    private ArrayList<Drawable> topBannerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        Layout_bars = findViewById(R.id.layoutBars);

        banner2 = getResources().getDrawable(R.drawable.pf1);
        banner3 = getResources().getDrawable(R.drawable.pf2);
        banner4 = getResources().getDrawable(R.drawable.pf3);

        topBannerList.add(banner2);
        topBannerList.add(banner3);
        topBannerList.add(banner4);

        bottomLL = findViewById(R.id.bottomLL);
        progressBar = findViewById(R.id.progressbar);
        scrollView = findViewById(R.id.scrollView);

        myvpAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myvpAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        ColoredBars(0);
        showLoader();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void ColoredBars(int thisScreen) {
        int[] colorsInactive = getResources().getIntArray(R.array.dot_on_page_not_active);
        int[] colorsActive = getResources().getIntArray(R.array.dot_on_page_active);
        bottomBars = new TextView[3];

        Layout_bars.removeAllViews();
        for (int i = 0; i < bottomBars.length; i++) {
            bottomBars[i] = new TextView(this);
            bottomBars[i].setTextSize(50);
            bottomBars[i].setText(Html.fromHtml("&#8226;"));
            Layout_bars.addView(bottomBars[i]);
            bottomBars[i].setTextColor(colorsInactive[thisScreen]);
        }
        if (bottomBars.length > 0)
            bottomBars[thisScreen].setTextColor(colorsActive[thisScreen]);
    }

    private void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        bottomLL.setVisibility(View.GONE);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(800);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                            bottomLL.setVisibility(View.VISIBLE);
                        }
                    });
                    ;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater = (LayoutInflater) getSystemService(ProductActivity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.pager_item, container, false);
            ImageView img = view.findViewById(R.id.productImage);
            img.setImageDrawable(topBannerList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }

        @Override
        public boolean isViewFromObject(View v, Object object) {
            return v == object;
        }
    }

}
