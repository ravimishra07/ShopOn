package com.ravimishra.tradzhub.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ravimishra.tradzhub.Activity.ItemDetailActivity;
import com.ravimishra.tradzhub.Activity.ProductActivity;
import com.ravimishra.tradzhub.Adapter.BannerAddapter;
import com.ravimishra.tradzhub.Adapter.OnEAdpater;
import com.ravimishra.tradzhub.Adapter.StoreAdapter;
import com.ravimishra.tradzhub.Adapter.TabAdapter;
import com.ravimishra.tradzhub.Adapter.TopMenuAdapter;
import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.Model.ProductModel;
import com.ravimishra.tradzhub.Model.StoreModel;
import com.ravimishra.tradzhub.Model.TopMenuModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static int currentPage = 0;
    private ArrayList<Integer> topBannerList = new ArrayList<>();
    private static final String TAG = "HomeFragment";

    private Button popularBtn, featuredBtn, newArrivalBtn, storeBtn;
    //RelaytiveLayout

    private RecyclerView topMenuRecyclerView, popularRecyclerView, recylerView2, recylerView3, storeRecyclerView;
    //ViewPager
    private ViewPager viewP;
    private TabLayout tabs;
    private ViewPager viewPager2;
    ProgressBar progressBar,featuredProgressBar,popularProgressbar,pbNewArrivals, pbPopularStories;
    private  TopMenuModel topMenuModel;

    Timer swipeTimer;
    private int[] banner = {R.drawable.banner4, R.drawable.banner3, R.drawable.banner2, R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner1};

    private List<TopMenuModel> topModel = new ArrayList<>();

    private CategoryModel topMenu;
    private TradzHubProductModel popularModel;
    private TradzHubProductModel featuredModel;
    private TradzHubProductModel latestModel;
    private StoreModel storeModel;

    // recylverview 1
    private List<ProductModel> productModel = new ArrayList<>();

    // recylverview 2
    private List<ProductModel> productModel2 = new ArrayList<>();

    // recylverview 3
    private List<ProductModel> productModel3 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.pbTopMenur);
        pbNewArrivals = view.findViewById(R.id.pbNewArrivals);
        featuredProgressBar = view.findViewById(R.id.pbFeaturedProducts);
        popularProgressbar = view.findViewById(R.id.pbPopularProducts);
        pbPopularStories = view.findViewById(R.id.pbPopularStores);

        topBannerList.clear();

        topBannerList.add(banner[0]);
        topBannerList.add(banner[1]);
        topBannerList.add(banner[2]);
        ApiCall();
        popularRecyclerView = view.findViewById(R.id.popularRecyclerView);
        topMenuRecyclerView = view.findViewById(R.id.topMenuRecylerView);
        recylerView2 = view.findViewById(R.id.recyclerView2);
        recylerView3 = view.findViewById(R.id.recyclerView3);

        //buttons
        newArrivalBtn = view.findViewById(R.id.newArrivalViewAllBtn);
        popularBtn = view.findViewById(R.id.popularViewAllBtn);
        featuredBtn = view.findViewById(R.id.featuredViewAllBtn);
        storeBtn = view.findViewById(R.id.storeBtn);

        newArrivalBtn.setEnabled(false);
        popularBtn.setEnabled(false);
        featuredBtn.setEnabled(false);


        // initialize the button and sets click listeners
        setUpButtons();
        storeRecyclerView = view.findViewById(R.id.storeRecyclerView);
        viewP = view.findViewById(R.id.viewPager);

        setUpanner();
        tabs = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewpager2);
        setupViewPager(viewPager2);
        tabs.setupWithViewPager(viewPager2);
        inisilizerecycler();

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getFragmentManager());
        adapter.addFragment(new JustInFragment(), "Just In");

        adapter.addFragment(new BestSellingFragment(), "Best Selling");
        adapter.addFragment(new FlashDealFragment(), "Flash Sale");
        viewPager2.setAdapter(adapter);
    }

    public void setUpButtons() {
        newArrivalBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ItemDetailActivity.class);
            i.putExtra("title", "New Arrivals");
            i.putExtra("PRODUCT", latestModel);
            i.putExtra("FROM", 0);
            i.putExtra("CATEGORY_ID", -1);
            swipeTimer.cancel();
            startActivity(i);
        });

        popularBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ItemDetailActivity.class);
            i.putExtra("title", "Popular");
            i.putExtra("PRODUCT", popularModel);
            i.putExtra("FROM", 0);
            i.putExtra("CATEGORY_ID", -1);


            swipeTimer.cancel();
            startActivity(i);
        });

        featuredBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ItemDetailActivity.class);
            i.putExtra("title", "Featured");
            i.putExtra("FROM", 0);
            i.putExtra("CATEGORY_ID", -1);


            Bundle bundle = new Bundle();
            bundle.putSerializable("PRODUCT", featuredModel);
            i.putExtras(bundle);
            swipeTimer.cancel();

            startActivity(i);
        });
        storeBtn.setOnClickListener(v -> {
//            Intent i = new Intent(getActivity(), StoreActivity.class);
//            i.putExtra("type", 2);
//            swipeTimer.cancel();
//
//            startActivity(i);
        });
        popularRecyclerView.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ProductActivity.class);
            swipeTimer.cancel();

            startActivity(i);
        });
        recylerView2.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ProductActivity.class);
            startActivity(i);
        });
        recylerView3.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ProductActivity.class);
            swipeTimer.cancel();

            startActivity(i);
        });
    }

    private void ApiCall() {
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        //Defining the user object as we need to pass it with the call

        /** defining the category api call */
        Call<CategoryModel>callCategoryModel = service.getCategory(
                1
        );

        /** defining the latest product api call */
        Call<TradzHubProductModel>callFeaturedProducts = service.getFeaturedProducts(
                1
        );

        /** defining the latest product api call */
        Call<TradzHubProductModel>callLatestProducts = service.getLatestProducts(
                1
        );

        /** defining the latest product api call */
        Call<TradzHubProductModel>callRecentlyViewedProducts = service.getRecentlyViewedProducts(
                1
        );

        /** defining mostly viewed(popular) product api call */
        Call<TradzHubProductModel>callMostlyViewedproducts = service.getMostlyViewedProducts(
                1
        );

        Call<StoreModel> callStoreApi = service.getStores(
                1
        );


        //calling the api
        callCategoryModel.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {

                Log.v("TAG_API", response.body().data.get(0).getCategoryImage + "msg");
                List<CategoryModel.ResponseData> responseData = response.body().data;
                topMenu = new CategoryModel(1, "model", responseData);
                topMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                TopMenuAdapter topMenuAdapter = new TopMenuAdapter(getContext(), topMenu.data);
                topMenuRecyclerView.setAdapter(topMenuAdapter);
                progressBar.setVisibility(View.GONE);
                //inisilizerecycler();
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callCategoryModel");
            }
        });

        callFeaturedProducts.enqueue(new Callback<TradzHubProductModel>() {
            @Override
            public void onResponse(Call<TradzHubProductModel> call, Response<TradzHubProductModel> response) {
                Log.v("TAG_API", response.body() + "callFeaturedProducts api");
                featuredModel = response.body();
                recylerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                OnEAdpater adapter3 = new OnEAdpater(getActivity(), featuredModel.data);
                recylerView3.setAdapter(adapter3);
                featuredBtn.setEnabled(true);
                featuredProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TradzHubProductModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callFeaturedProducts");
            }
        });

        callLatestProducts.enqueue(new Callback<TradzHubProductModel>() {
            @Override
            public void onResponse(Call<TradzHubProductModel> call, Response<TradzHubProductModel> response) {
                Log.v("TAG_API", response.body() + "callLatestProducts api");
                latestModel = response.body();
                recylerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                OnEAdpater adapter2 = new OnEAdpater(getActivity(), latestModel.data);
                recylerView2.setAdapter(adapter2);
                newArrivalBtn.setEnabled(true);
                pbNewArrivals.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TradzHubProductModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callLatestProducts");

            }
        });

        callMostlyViewedproducts.enqueue(new Callback<TradzHubProductModel>() {
            @Override
            public void onResponse(Call<TradzHubProductModel> call, Response<TradzHubProductModel> response) {
                Log.v("TAG_API", response.body() + "call mostly viewed api");
                popularModel = response.body();
                popularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                OnEAdpater onEAdpater = new OnEAdpater(getActivity(), popularModel.data);
                popularRecyclerView.setAdapter(onEAdpater);
                popularBtn.setEnabled(true);
                popularProgressbar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<TradzHubProductModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callMostlyViewedproducts");

            }
        });
        callStoreApi.enqueue(new Callback<StoreModel>() {
            @Override
            public void onResponse(Call<StoreModel> call, Response<StoreModel> response) {
                storeModel = response.body();

                storeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                StoreAdapter storeAdapter = new StoreAdapter(getContext(), storeModel.data);
                storeRecyclerView.setAdapter(storeAdapter);
                pbPopularStories.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<StoreModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callStoreApi");

            }
        });
    }

    private void inisilizerecycler() {

        Log.d(TAG, "inisilizerecycler: ");

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setUpanner() {

        viewP.setAdapter(new BannerAddapter(getActivity(), topBannerList));

        final float density = getResources().getDisplayMetrics().density;

        final Handler handler = new Handler();
        final Runnable Update = () -> {

            if (currentPage == topBannerList.size()) {
                currentPage = 0;
            }
            viewP.setCurrentItem(currentPage++, true);
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);


    }
}
//                if(currentPage==0){
//                    viewP.setCurrentItem(0, true);
//                }else if(currentPage==1){
//                    viewP.setCurrentItem(1, true);
//                }else {
//
//                    viewP.setCurrentItem(2, true);
//                    currentPage=0;
//                }
/*

    private int circle[] = {R.drawable.first,R.drawable.second,R.drawable.third,R.drawable.forth,R.drawable.fifth,R.drawable.sixth
            ,R.drawable.seventh};

    private int forth[] = {R.drawable.pf1,R.drawable.pf2,R.drawable.pf3,R.drawable.pf4,R.drawable.pf5,R.drawable.pf6
            ,R.drawable.pf1,R.drawable.pf2};

    private int thirdset[] = {R.drawable.thirdone,R.drawable.thirdtwo,R.drawable.thirdthree,R.drawable.thirdfor,R.drawable.thirdfif,
            R.drawable.thirdsix,R.drawable.thirdseven,R.drawable.thirdeight};

        private int five[] = {R.drawable.one1,R.drawable.one2,R.drawable.one3,R.drawable.one4,R.drawable.one5,R.drawable.one6
            ,R.drawable.one7,R.drawable.one8};
 */



   /*
        topModel.add(new TopMenuModel("img1", "Deals"));


        topModel.add(new TopMenuModel("img1", "Electronics"));
        topModel.add(new TopMenuModel("img1", "Men"));
       topModel.add(new TopMenuModel("img1", "Cricket kit"));
        topModel.add(new TopMenuModel("img1", "Women"));
       topModel.add(new TopMenuModel("img1", "Appliance"));



        // RV1
        productModel.add(new ProductModel("Macbook air", 1299, "img1"));
        productModel.add(new ProductModel("Nikon D5612", 399, "img2"));
        productModel.add(new ProductModel("iPhone 11", 599, "img3"));
        productModel.add(new ProductModel("Men's casual shirt", 29, "img14"));
        productModel.add(new ProductModel("Macbook air", 1299, "img1"));
        productModel.add(new ProductModel("Nikon D5612", 399, "img2"));
        productModel.add(new ProductModel("iPhone 11", 599, "img3"));
        productModel.add(new ProductModel("Men's casual shirt", 29, "img14"));

        //RV2
        productModel2.add(new ProductModel("Men's shirt", 21, "img1"));
        productModel2.add(new ProductModel("Men's jeans black", 15, "img2"));
        productModel2.add(new ProductModel("Mi LED smart TV", 499, "img3"));
        productModel2.add(new ProductModel("Women's black top", 29, "img14"));
        productModel2.add(new ProductModel("Men's shirt", 21, "img1"));
        productModel2.add(new ProductModel("Men's jeans black", 15, "img2"));
        productModel2.add(new ProductModel("Mi LED smart TV", 499, "img3"));
        productModel2.add(new ProductModel("Women's black top", 29, "img14"));

        //RV2
        productModel3.add(new ProductModel("Mi LED smart TV", 499, "img3"));
        productModel3.add(new ProductModel("Women's black top", 29, "img14"));
        productModel3.add(new ProductModel("Men's shirt", 21, "img1"));
        productModel3.add(new ProductModel("Men's jeans black", 15, "img2"));
        productModel3.add(new ProductModel("Mi LED smart TV", 499, "img3"));
*/