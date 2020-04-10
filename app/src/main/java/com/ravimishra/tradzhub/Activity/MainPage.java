package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.os.Bundle;



import android.util.Log;
import android.view.View;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ravimishra.tradzhub.Fragment.HomeFragment;
import com.ravimishra.tradzhub.R;

public class MainPage extends AppCompatActivity implements View.OnClickListener {

    private Fragment fragment;

    private static final String TAG = "MainPage";


    RelativeLayout rlHome,rlStoreCat,rlstore,rlAboutUs,rlShare;

    LinearLayout shopCatLinearLayout,storeLinearLayout;
    ImageView ivborrowers,ivbranches,ivStoreCat,ivStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findviewbyid();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        ivStoreCat.setRotation(180);
        findViewById();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            Intent i = new Intent(this, LogActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void findviewbyid() {

        //images
        ivStoreCat = findViewById(R.id.storeCatArrow);
        ivStore = findViewById(R.id.storeArrow);

        //Linear
        shopCatLinearLayout = findViewById(R.id.shopCatLinearLayout);
        storeLinearLayout = findViewById(R.id.storeLinearLayout);

        //relative  roots
        rlStoreCat = findViewById(R.id.rlShopCategories);
        rlStoreCat.setOnClickListener(this);
        rlstore = findViewById(R.id.rlStore);
        rlstore.setOnClickListener(this);

    }

    private void findViewById() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment = new HomeFragment();
        fragmentManager.popBackStack(fragment.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.container, fragment).addToBackStack(fragment.toString());
        tx.commit();
        // ====to clear unused memory==
        System.gc();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.rlShopCategories:

                if(shopCatLinearLayout.getVisibility()==View.VISIBLE)
                {
                    ivStoreCat.setRotation(0);
                    shopCatLinearLayout.setVisibility(View.GONE);
                }
                else
                {
                    ivStoreCat.setRotation(180);
                    shopCatLinearLayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.rlStore:

                if(storeLinearLayout.getVisibility()==View.VISIBLE)
                {
                    ivStore.setRotation(0);
                    storeLinearLayout.setVisibility(View.GONE);
                }
                else
                {
                    ivStore.setRotation(180);
                    storeLinearLayout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

}
