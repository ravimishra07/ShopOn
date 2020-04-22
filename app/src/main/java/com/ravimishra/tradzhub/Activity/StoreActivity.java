package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.ravimishra.tradzhub.Adapter.MainTabStoreAdapter;
import com.ravimishra.tradzhub.Adapter.TabAdapter;
import com.ravimishra.tradzhub.Adapter.TabRecyclerViewAdapter;
import com.ravimishra.tradzhub.Fragment.BestSellingFragment;
import com.ravimishra.tradzhub.Fragment.JustInFragment;
import com.ravimishra.tradzhub.Fragment.StoreFollowersFragment;
import com.ravimishra.tradzhub.Fragment.StoreProductFragment;
import com.ravimishra.tradzhub.Fragment.StoreReviewFragment;
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel;
import com.ravimishra.tradzhub.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Image ftom bundle
        Bundle extras = getIntent().getExtras();
        int value = extras.getInt("type");

        setContentView(R.layout.activity_store);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        ImageView toolbarImageView = findViewById(R.id.toolbarImage);
        Drawable banner2 = getResources().getDrawable(R.drawable.banner2);

        Drawable banner3 = getResources().getDrawable(R.drawable.banner3);
        Drawable banner4 = getResources().getDrawable(R.drawable.banner4);
        backImageBtn = findViewById(R.id.backImageBtn);

        frameLayout = findViewById(R.id.mainFrame);
        progressBar = findViewById(R.id.progressbar);
        appBarLayout = findViewById(R.id.app_bar);

        if (value == 1) {
            //bundle.putString("type", "3");
            toolbarImageView.setImageDrawable(banner2);

        }else if (value == 2){
            toolbarImageView.setImageDrawable(banner3);

        }else if (value == 3 ) {
            toolbarImageView.setImageDrawable(banner4);
        }else{
            toolbarImageView.setImageDrawable(banner3);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        backImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoreActivity.this, MainPage.class);
                startActivity(i);
            }
        });
        tabs= findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
         bundle = new Bundle();
        showLoader();
    }
    private void setupViewPager(ViewPager viewPager) {


        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        StoreProductFragment storeProductFragment = new StoreProductFragment();
        storeProductFragment.setArguments(bundle);

        adapter.addFragment(storeProductFragment, "Store Product");
        adapter.addFragment(new StoreReviewFragment(), "Store Review");
        adapter.addFragment(new StoreFollowersFragment(), "Followers");
        viewPager.setAdapter(adapter);
    }
    private  void showLoader(){
        progressBar.setVisibility(View.VISIBLE);
        appBarLayout.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        tabs.setVisibility(View.GONE);
        Thread myThread = new Thread()
        {
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
                    ;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
