package com.ravimishra.tradzhub.Activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.ravimishra.tradzhub.Adapter.ProductivityDetailAdapter;
import com.ravimishra.tradzhub.Adapter.WishListAdapter;
import com.ravimishra.tradzhub.Model.ProductDetailModel;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivtry extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ProductDetailModel> productDetailModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_activtry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        productDetailModels.add(new ProductDetailModel("$ 799", "Macbook air", "128 ssd", "4 business days"));
        productDetailModels.add(new ProductDetailModel("$ 499", "iPhone 8", "32 gb", "3 business days"));
        //  productDetailModels.add(new ProductDetailModel("$ 499","iPhone 8","32 gb","3 business days"));


        WishListAdapter adapter = new WishListAdapter(this, productDetailModels);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

}
