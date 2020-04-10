package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ravimishra.tradzhub.Adapter.MainTabStoreAdapter;
import com.ravimishra.tradzhub.Model.ProductModel;
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel;
import com.ravimishra.tradzhub.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ItemDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    List<TabRecyclerViewModel> tabRecyclerViewModel = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ImageView backImageBtn;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get Image ftom bundle
        Bundle extras = getIntent().getExtras();
        String titleText = extras.getString("title");
        setContentView(R.layout.activity_item_detail);
        toolbar =findViewById(R.id.toolbar);
        toolbarTitle =findViewById(R.id.toolbar_title);
        toolbarTitle.setText(titleText);
        setSupportActionBar(toolbar);
        backImageBtn = findViewById(R.id.back);
        setUpViews();
    }

    private  void addFabButton(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUpViews(){
        recyclerView = findViewById(R.id.recyclerView);
        fab=findViewById(R.id.fab);
       backImageBtn.setOnClickListener(v -> {
           Intent i = new Intent(this, MainPage.class);
           startActivity(i);
       });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        MainTabStoreAdapter adapter = new MainTabStoreAdapter(this, tabRecyclerViewModel);
        recyclerView.setAdapter(adapter);
        tabRecyclerViewModel.clear();

        tabRecyclerViewModel.add(new TabRecyclerViewModel("iPhone 8", "199","(20%off)","4.1","img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Macbook Air", "499","(20%off)","4.1","img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Men's Shirt", "49","(20%off)","4.1","img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Women's Top", "99","(20%off)","4.1","img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Whirlpool Washing Machine", "149","(20%off)","4.1","img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Womens's Jacket", "499","(20%off)","4.1","img1"));

    }
}
