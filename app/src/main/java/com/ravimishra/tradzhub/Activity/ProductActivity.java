package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.Utils.Constants;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATABASE_NAME = "cartdatabase";
    SQLiteDatabase mDatabase;

    private TextView[] bottomBars;
    private ImageView backImage;
    private LinearLayout Layout_bars, llAddToCart, llBuyNow;
    private MyViewPagerAdapter myvpAdapter;
    private Drawable banner2, banner3, banner4;
    private TextView tvProductName, tvProductPrice, tvProductDescription, tvInStock, tvOutStock, tvOrignalPrice, tvOffPrice, tvShippingCharges;
    private NestedScrollView scrollView;
    private LinearLayout bottomLL;
    private ProgressBar progressBar;
    private TradzHubProductModel.ResponseData responseData;
    private LinearLayout tvBuyNow;
    private CategoryModel.ResponseData catResponseData;

    private int value = 0;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

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
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        Layout_bars = findViewById(R.id.layoutBars);
        tvBuyNow = findViewById(R.id.tvBuyNow);
        tvOrignalPrice = findViewById(R.id.productOrignalPrice);

        tvOffPrice = findViewById(R.id.tvOffPrice);
        tvInStock = findViewById(R.id.tvInstock);
        tvOutStock = findViewById(R.id.tvOutstock);
        tvShippingCharges = findViewById(R.id.tvShippingCharges);
        tvOffPrice = findViewById(R.id.tvOffPrice);
        llBuyNow = findViewById(R.id.llBuyNow);
        llAddToCart = findViewById(R.id.llAddToCart);
        backImage = findViewById(R.id.back);
        llBuyNow.setOnClickListener(this);
        llAddToCart.setOnClickListener(this);
        backImage.setOnClickListener(this);
        tvBuyNow.setOnClickListener(this);

        bottomLL = findViewById(R.id.bottomLL);
        progressBar = findViewById(R.id.progressbar);
        scrollView = findViewById(R.id.scrollView);

        tvProductName = findViewById(R.id.productName);
        tvProductPrice = findViewById(R.id.productPrice);
        tvProductDescription = findViewById(R.id.productDescription);
        Bundle extras = getIntent().getExtras();
        value = extras.getInt("FROM");
        if (value == 1) {
            setData();
        }

        myvpAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myvpAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        ColoredBars(1);
        showLoader();

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

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    private void setData() {
        Intent i = getIntent();
        responseData = (TradzHubProductModel.ResponseData) i.getSerializableExtra("PRODUCT");
        int stockValue = Integer.parseInt(responseData.currentStock);
        float purchasedValue = Float.parseFloat(responseData.purchasePrice);
        float saleValue = Float.parseFloat(responseData.salePrice);
        float percentOff = (1 - (purchasedValue / saleValue)) * 100;
        int percentAbsolutePrice = (int) percentOff;

        tvProductName.setText(responseData.title);
        tvProductPrice.setText("$ " + responseData.purchasePrice);
        tvProductDescription.setText(responseData.description);
        tvOrignalPrice.setText("$ " + responseData.salePrice);
        tvOffPrice.setText(percentAbsolutePrice + "% off");
        tvShippingCharges.setText("$ " + responseData.shippingCost);


        if (stockValue <= 1) {
            tvInStock.setVisibility(View.GONE);
            tvOutStock.setVisibility(View.VISIBLE);
        } else {
            tvOutStock.setVisibility(View.GONE);
            tvInStock.setVisibility(View.VISIBLE);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.llAddToCart:
                addItemToCart();
                break;

        }
    }

    private void addItemToCart() {
        String cartItem;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cartItem = preferences.getString(Constants.SHARED_CART_ITEM, "");
        SharedPreferences.Editor editor = preferences.edit();
        cartItem = cartItem + "," + responseData.productID;
        editor.putString(Constants.SHARED_CART_ITEM, cartItem);
        editor.apply();


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
            if (value == 1) {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.place_holder_image)
                        .error(R.drawable.place_holder_image);
                Glide.with(ProductActivity.this).load(responseData.productImage).apply(options).into(img);
            }
            //  img.setImageDrawable(topBannerList.get(position));
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
