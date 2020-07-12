package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ravimishra.tradzhub.Adapter.ProductDetailAdapter;
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    List<TabRecyclerViewModel> tabRecyclerViewModel = new ArrayList<>();
    private TradzHubProductModel responseData;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ImageView backImageBtn;
    private TextView toolbarTitle;
    private ImageView cartImage;
    //private int categoryID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get Image ftom bundle
        Bundle extras = getIntent().getExtras();
        String titleText = extras.getString("title");

        setContentView(R.layout.activity_item_detail);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(titleText);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        backImageBtn = findViewById(R.id.back);
        cartImage = findViewById(R.id.cart);

        int fromActivity = extras.getInt("FROM");
        if (fromActivity == 1) {
            String cat_id = extras.getString("CATEGORY_ID");
            int catID = -1;
            try {
                catID = Integer.parseInt(cat_id);
                callApiForCategory(catID);
            } catch (NumberFormatException nfe) {
                Log.v("cannot parse", "msg" + nfe);
            }
        } else {
            responseData = (TradzHubProductModel) extras.getSerializable("PRODUCT");
            setUpViews();
        }
    }

    private void addFabButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUpViews() {
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        backImageBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainPage.class);
            startActivity(i);
        });
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemDetailActivity.this, CartActivity.class);
                startActivity(i);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        ProductDetailAdapter adapter = new ProductDetailAdapter(this, responseData.data);
        recyclerView.setAdapter(adapter);
    }

    private void callApiForCategory(int categoryID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        //Defining the user object as we need to pass it with the call

        /** defining the category api call */
        Call<TradzHubProductModel> callProductByCat = service.getCategoryDetail(
                1,
                categoryID
        );

        callProductByCat.enqueue(new Callback<TradzHubProductModel>() {
            @Override
            public void onResponse(Call<TradzHubProductModel> call, Response<TradzHubProductModel> response) {
                Log.v("TAG_API", response.body() + "callFeaturedProducts api");
                responseData = response.body();
                recyclerView = findViewById(R.id.recyclerView);
                fab = findViewById(R.id.fab);
                backImageBtn.setOnClickListener(v -> {
                    Intent i = new Intent(ItemDetailActivity.this, MainPage.class);
                    startActivity(i);
                });

                GridLayoutManager gridLayoutManager = new GridLayoutManager(ItemDetailActivity.this, 2, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(gridLayoutManager);

                ProductDetailAdapter adapter = new ProductDetailAdapter(ItemDetailActivity.this, responseData.data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TradzHubProductModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callFeaturedProducts");
            }
        });

    }
}
