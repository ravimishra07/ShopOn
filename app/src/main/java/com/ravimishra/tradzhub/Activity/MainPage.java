package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Adapter.SideMenuAdapter;
import com.ravimishra.tradzhub.Fragment.HomeFragment;
import com.ravimishra.tradzhub.Fragment.ShowAllCategoryFragment;
import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.Utils.Constants;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPage extends AppCompatActivity implements View.OnClickListener {

    private Fragment fragment;
    ImageView ivborrowers, ivbranches, ivStoreCat, ivStore, ivCart, ivWishlist;
    private static final String TAG = "MainPage";
    RelativeLayout rlHome, rlStoreCat, rlstore, rlAboutUs, rlShare;
    LinearLayout shopCatLinearLayout, storeLinearLayout;
    RecyclerView sideMenuRecyclerView;
    TextView tvUsername, tvLogin, tvName;
    FrameLayout fragmentContainer;
    String username = "Guest user";
    ProgressBar progressBar;
    Boolean isLoggedIn = false;
    String loginText = "Login Here";
    private DrawerLayout drawer;
    private CategoryModel sideMenuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainPage.this);
        String name = preferences.getString(Constants.SHARED_USERNAME, "");

        if (!name.equalsIgnoreCase("") && name != null) {
            Log.v("email_tag", name);
            isLoggedIn = true;
            username = name;
        }
        findviewbyid();

        if (isLoggedIn) {
            tvLogin.setText("Logout");
            tvName.setText("Hello, " + username);
            Toast.makeText(this, "Signed in as " + username, Toast.LENGTH_SHORT).show();
        } else {
            tvLogin.setText("Login Here");
            tvName.setText("Guest user");

            Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        }

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setUpFragment();
        //loadSideMenu();
        showLoader();
        tvLogin.setOnClickListener(this);
    }

    private void findviewbyid() {

        //images
        ivStore = findViewById(R.id.storeArrow);

        //Linear
        storeLinearLayout = findViewById(R.id.storeLinearLayout);

        //relative  roots
        rlHome = findViewById(R.id.rlHome);
        rlHome.setOnClickListener(this);
        rlStoreCat = findViewById(R.id.rlShopCategories);
        rlStoreCat.setOnClickListener(this);
        rlstore = findViewById(R.id.rlStore);
        rlstore.setOnClickListener(this);

        //cart click
        ivCart = findViewById(R.id.ivCart);
        ivCart.setOnClickListener(this);
        ivWishlist = findViewById(R.id.ivWishlist);
        ivWishlist.setOnClickListener(this);

        // loader
        progressBar = findViewById(R.id.progressbar);
        fragmentContainer = findViewById(R.id.container);

        tvLogin = findViewById(R.id.tvLogin);
        tvName = findViewById(R.id.tvName);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_page, menu);
//        /*
//        if (isLoggedIn) {
//            menu.findItem(R.id.login).setTitle("Logout");
//
//        } else {
//            menu.findItem(R.id.login).setTitle("Login or Register");
//        }
//        */
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        if (id == R.id.wishlist) {
//            Intent i = new Intent(this, WishlistActivtry.class);
//            startActivity(i);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void loadSideMenu() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        //Defining the user object as we need to pass it with the call

        /** defining the category api call */
        Call<CategoryModel> callCategoryModel = service.getCategory(
                1
        );

        //calling the api
        callCategoryModel.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {

                Log.v("TAG_API", response.body().data.get(0).getCategoryImage + "msg");
                List<CategoryModel.ResponseData> responseData = response.body().data;
                sideMenuModel = new CategoryModel(1, "model", responseData);
                sideMenuRecyclerView.setLayoutManager(new LinearLayoutManager(MainPage.this, LinearLayoutManager.VERTICAL, false));
                SideMenuAdapter sideMenuAdapter = new SideMenuAdapter(MainPage.this, sideMenuModel.data);
                sideMenuRecyclerView.setAdapter(sideMenuAdapter);
//            progressBar.setVisibility(View.GONE);
                //inisilizerecycler();
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callCategoryModel");
            }
        });

    }

    private void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.GONE);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(800);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            fragmentContainer.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


    private void setUpFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new HomeFragment();
        fragmentManager.popBackStack(fragment.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.container, fragment).addToBackStack(fragment.toString());
        tx.commit();
        //====to clear unused memory==
        System.gc();
    }

    private void setUpCatFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new ShowAllCategoryFragment();
        fragmentManager.popBackStack(fragment.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.container, fragment).addToBackStack(fragment.toString());
        tx.commit();
        //====to clear unused memory==
        System.gc();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rlHome:
                drawer.closeDrawer(GravityCompat.START);
                setUpFragment();
                break;

            case R.id.rlShopCategories:
                drawer.closeDrawer(GravityCompat.START);
                setUpCatFragment();
                break;

            case R.id.rlStore:
                if (storeLinearLayout.getVisibility() == View.VISIBLE) {
                    ivStore.setRotation(0);
                    storeLinearLayout.setVisibility(View.GONE);
                } else {
                    ivStore.setRotation(180);
                    storeLinearLayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.ivCart:
                Intent i = new Intent(this, CartActivity.class);
                startActivity(i);
                break;
            case R.id.ivWishlist:
                Intent intent3 = new Intent(this, WishlistActivtry.class);
                startActivity(intent3);
                break;
            case R.id.tvLogin:
                if (isLoggedIn) {
                    //SharedPreferences preferences =getSharedPreferences(Constants.SHARED_EMAIL, Context.MODE_PRIVATE);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainPage.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    isLoggedIn = false;
                    Intent intent = new Intent(this, FirstActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent i2 = new Intent(this, LogActivity.class);
                    startActivity(i2);
                }
        }
    }
}
