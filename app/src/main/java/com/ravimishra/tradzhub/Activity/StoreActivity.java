package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.ravimishra.tradzhub.Adapter.TabAdapter;
import com.ravimishra.tradzhub.Fragment.StoreFollowersFragment;
import com.ravimishra.tradzhub.Fragment.StoreProductFragment;
import com.ravimishra.tradzhub.Fragment.StoreReviewFragment;
import com.ravimishra.tradzhub.Model.StoreModel;
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabs;
    RecyclerView recyclerView;
    List<TabRecyclerViewModel> tabRecyclerViewModel = new ArrayList<>();
    Bundle bundle;
    ImageView backImageBtn;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    StoreModel.ResponseData responseData;
    TradzHubProductModel productResponseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        responseData = (StoreModel.ResponseData) i.getSerializableExtra("STORE");

        setContentView(R.layout.activity_store);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        ImageView toolbarImageView = findViewById(R.id.toolbarImage);
        collapsingToolbarLayout.setTitle(responseData.storeName);

        backImageBtn = findViewById(R.id.backImageBtn);

        frameLayout = findViewById(R.id.mainFrame);
        progressBar = findViewById(R.id.progressbar);
        appBarLayout = findViewById(R.id.app_bar);

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
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);
        Glide.with(this).load(responseData.storeImage).apply(options).into(toolbarImageView);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        toolbar.setTitle(responseData.storeName);

        backImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoreActivity.this, MainPage.class);
                startActivity(i);
            }
        });
        tabs = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        bundle = new Bundle();
        showLoader();
    }

    private void setupViewPager(ViewPager viewPager) {

        int store_id = Integer.parseInt(responseData.storeID);

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        StoreProductFragment storeProductFragment = new StoreProductFragment(store_id);
        storeProductFragment.setArguments(bundle);

        adapter.addFragment(storeProductFragment, "Store Product");
        adapter.addFragment(new StoreReviewFragment(), "Store Review");
        adapter.addFragment(new StoreFollowersFragment(), "Followers");
        viewPager.setAdapter(adapter);
    }

    private void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
        appBarLayout.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        tabs.setVisibility(View.GONE);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(800);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            appBarLayout.setVisibility(View.VISIBLE);
                            frameLayout.setVisibility(View.VISIBLE);
                            tabs.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
