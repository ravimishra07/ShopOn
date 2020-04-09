package com.ravimishra.tradzhub.Activity;

import android.os.Bundle;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabs;
    RecyclerView recyclerView;
    List<TabRecyclerViewModel> tabRecyclerViewModel = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tabs= findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);



    }
    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new StoreProductFragment(), "Store Product");
        adapter.addFragment(new StoreReviewFragment(), "Store Review");
        adapter.addFragment(new StoreFollowersFragment(), "Followers");
        viewPager.setAdapter(adapter);
    }
}
