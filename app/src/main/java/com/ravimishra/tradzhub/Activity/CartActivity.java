package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ravimishra.tradzhub.Adapter.ProductivityDetailAdapter;
import com.ravimishra.tradzhub.Model.ProductDetailModel;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button addAddressBtn;
    private List<ProductDetailModel> productDetailModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        addAddressBtn = findViewById(R.id.addAddressbtn);
        addAddressBtn.setOnClickListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        productDetailModels.add(new ProductDetailModel("$ 799", "Macbook air", "128 ssd", "4 business days"));
        productDetailModels.add(new ProductDetailModel("$ 499", "iPhone 8", "32 gb", "3 business days"));
        //  productDetailModels.add(new ProductDetailModel("$ 499","iPhone 8","32 gb","3 business days"));


        ProductivityDetailAdapter adapter = new ProductivityDetailAdapter(this, productDetailModels);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAddressbtn:
                Intent intent = new Intent(CartActivity.this, AddAddressActivity.class);
                startActivity(intent);
        }
    }
}
