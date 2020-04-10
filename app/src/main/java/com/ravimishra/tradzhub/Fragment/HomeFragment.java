package com.ravimishra.tradzhub.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ravimishra.tradzhub.Activity.StoreActivity;
import com.ravimishra.tradzhub.Adapter.BannerAddapter;
import com.ravimishra.tradzhub.Adapter.OnEAdpater;
import com.ravimishra.tradzhub.Adapter.StoreAdapter;
import com.ravimishra.tradzhub.Adapter.TabAdapter;
import com.ravimishra.tradzhub.Adapter.TopMenuAdapter;
import com.ravimishra.tradzhub.Model.ProductModel;
import com.ravimishra.tradzhub.Model.TopMenuModel;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private static int currentPage = 0;
    ProgressBar simpleProgressBar;
    private BannerAddapter bannerAddapter;
    private ArrayList<Integer> topBannerList = new ArrayList<>();
    private static final String TAG = "HomeFragment";

    //Indicator
    //CirclePageIndicator indicator;
    TextView tvFilter, tvreset, tvnofilter;
    //ImageView
    ImageView ivcart, ivloader;
    //RelaytiveLayout
    RelativeLayout RlFilter, rllfilter;
    private RecyclerView topMenuRecyclerView,popularRecyclerView, recylerView2,recylerView3, storeRecyclerView;

    ProgressBar progresbar;
    //NestedScrollView
    NestedScrollView scrollView;
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    //ViewPager
    private ViewPager viewP;

    TabLayout tabs;
    ViewPager viewPager,viewPager2;
    Button popularBtn,featuredBtn,newArrivalBtn;

    private int[] drawone = {R.drawable.first, R.drawable.second, R.drawable.pf2, R.drawable.forth, R.drawable.fifth, R.drawable.pf1
            , R.drawable.seventh};

    private int[] TwoSet = {R.drawable.two1, R.drawable.two2, R.drawable.two3, R.drawable.two4, R.drawable.two5, R.drawable.two1
            , R.drawable.two2, R.drawable.two2};
    private int[] banner = {R.drawable.pf1, R.drawable.pf2, R.drawable.pf3};

    OnEAdpater bottomDealer;


    List<TopMenuModel> topModel = new ArrayList<>();

    // recylverview 1
    List<ProductModel> productModel = new ArrayList<>();
    // recylverview 1
    List<ProductModel> productModel2 = new ArrayList<>();
    // recylverview 1
    List<ProductModel> productModel3 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        topBannerList.clear();

        topBannerList.add(banner[0]);
        topBannerList.add(banner[1]);
        topBannerList.add(banner[2]);

        popularRecyclerView = view.findViewById(R.id.popularRecyclerView);
        topMenuRecyclerView = view.findViewById(R.id.topMenuRecylerView);
        recylerView2 = view.findViewById(R.id.recyclerView2);
        recylerView3 = view.findViewById(R.id.recyclerView3);

        //buttons
        newArrivalBtn = view.findViewById(R.id.newArrivalViewAllBtn);
        popularBtn = view.findViewById(R.id.popularViewAllBtn);
        featuredBtn = view.findViewById(R.id.featuredViewAllBtn);
        newArrivalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StoreActivity.class);
                i.putExtra("type",2);
                startActivity(i);
            }
        });

        popularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StoreActivity.class);
                i.putExtra("type",1);
                startActivity(i);
            }
        });

        featuredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StoreActivity.class);
                i.putExtra("type",3);
                startActivity(i);
            }
        });

        //recyclersecond = view.findViewById(R.id.bootomrecyclersecond);
//        recyclerthird = view.findViewById(R.id.recyclerViewthird);
        storeRecyclerView = view.findViewById(R.id.storeRecyclerView);



//        indicator = view.findViewById(R.id.indicator);
        viewP = view.findViewById(R.id.viewPager);

        // findViewById(view);

        inisilizerecycler();

        //setUpanner();
        topModel.add(new TopMenuModel("img1", "Deals"));
        topModel.add(new TopMenuModel("img1", "Electronics"));
        topModel.add(new TopMenuModel("img1", "Men"));
        topModel.add(new TopMenuModel("img1", "Cricket kit"));
        topModel.add(new TopMenuModel("img1", "Women"));
        topModel.add(new TopMenuModel("img1", "Appliance"));

        // RV1
        productModel.add(new ProductModel("Mackbook air", 1299,"img1"));
        productModel.add(new ProductModel("Nikon D5612", 399,"img2"));
        productModel.add(new ProductModel("iPhone 11", 599,"img3"));
        productModel.add(new ProductModel("Men's casual shirt", 29,"img14"));
        productModel.add(new ProductModel("Mackbook air", 1299,"img1"));
        productModel.add(new ProductModel("Nikon D5612", 399,"img2"));
        productModel.add(new ProductModel("iPhone 11", 599,"img3"));
        productModel.add(new ProductModel("Men's casual shirt", 29,"img14"));

        //RV2
        productModel2.add(new ProductModel("Men's shirt", 21,"img1"));
        productModel2.add(new ProductModel("Men's jeans black", 15,"img2"));
        productModel2.add(new ProductModel("Mi LED smart TV", 499,"img3"));
        productModel2.add(new ProductModel("Women's black top", 29,"img14"));
        productModel2.add(new ProductModel("Men's shirt", 21,"img1"));
        productModel2.add(new ProductModel("Men's jeans black", 15,"img2"));
        productModel2.add(new ProductModel("Mi LED smart TV", 499,"img3"));
        productModel2.add(new ProductModel("Women's black top", 29,"img14"));

        //RV2
        productModel3.add(new ProductModel("Mi LED smart TV", 499,"img3"));
        productModel3.add(new ProductModel("Women's black top", 29,"img14"));
        productModel3.add(new ProductModel("Men's shirt", 21,"img1"));
        productModel3.add(new ProductModel("Men's jeans black", 15,"img2"));
        productModel3.add(new ProductModel("Mi LED smart TV", 499,"img3"));

        tabs= view.findViewById(R.id.tabLayout);
        viewPager2 =  view.findViewById(R.id.viewpager2);
        setupViewPager(viewPager2);
        tabs.setupWithViewPager(viewPager2);


        return view;
    }


    //findViewById
//    private void findViewById(View view) {
//
//        //LinearLayout
//        ivcart = view.findViewById(R.id.ivcart);
//        scrollView = view.findViewById(R.id.scrollView);
//        progresbar = view.findViewById(R.id.progresbar1);
//
//        simpleProgressBar = view.findViewById(R.id.progresbar);
//
//
//        //ViewPager
//        //viewPager = view.findViewById(R.id.viewPager);
//
//        //Loader
//        ivloader = view.findViewById(R.id.ivloader);
//
//
//        //RelayTiveLayout
//        //   RlFilter = view.findViewById(R.id.RlFilter);
//
//
//
//
//        //   inisilizerecycler();
//
//    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getFragmentManager());
        adapter.addFragment(new JustInFragment(), "Just In");

       // adapter.addFragment(new FlashSaleFragment(), "Flash Sale");
        adapter.addFragment(new BestSellingFragment(), "Best Selling products");

        viewPager2.setAdapter(adapter);
    }

    private void inisilizerecycler() {

        Log.d(TAG, "inisilizerecycler: ");
        // Top menu RV
        topMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recylerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recylerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //recyclerthird.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        TopMenuAdapter topMenuAdapter = new TopMenuAdapter(getContext(),topModel);
        topMenuRecyclerView.setAdapter(topMenuAdapter);

        OnEAdpater onEAdpater = new OnEAdpater(getActivity(), productModel);
        popularRecyclerView.setAdapter(onEAdpater);

        OnEAdpater adapter2 = new OnEAdpater(getActivity(), productModel2);
        recylerView2.setAdapter(adapter2);
        OnEAdpater adapter3 = new OnEAdpater(getActivity(), productModel3);
        recylerView3.setAdapter(adapter3);





        StoreAdapter storeAdapter = new StoreAdapter(getActivity());
        storeRecyclerView.setAdapter(storeAdapter);

//        ThirdAdapter thirdAdapter = new ThirdAdapter(getActivity(), TwoSet);
//        recyclerthird.setAdapter(thirdAdapter);
////
//        CircleAdapter circleAdapter = new CircleAdapter(getActivity(),circle);
//        topCircle.setAdapter(circleAdapter);

        //SecondAdapter secondAdapter = new SecondAdapter(getActivity(),TwoSet);
        // recyclersecond.setAdapter(secondAdapter);


        //  fifAdpater fifAdpater = new fifAdpater(getActivity(),five);
        // recyclerfiffth.setAdapter(fifAdpater);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpanner();
    }

    private void setUpanner() {

        viewP.setAdapter(new BannerAddapter(getActivity(), topBannerList));
        // indicator.setViewPager(viewP);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        //  indicator.setRadius(6 * density);
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == topBannerList.size()) {
                    currentPage = 0;
                }
                viewP.setCurrentItem(currentPage++, true);

//                if(currentPage==0){
//                    viewP.setCurrentItem(0, true);
//                }else if(currentPage==1){
//                    viewP.setCurrentItem(1, true);
//                }else {
//
//                    viewP.setCurrentItem(2, true);
//                    currentPage=0;
//                }
            }
        };

        Timer swipeTimer = new Timer();

        swipeTimer.schedule(new TimerTask() {

            @Override

            public void run() {

                handler.post(Update);
            }
        }, 5000, 3000);

        // Pager listener over indicator


    }


}


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



