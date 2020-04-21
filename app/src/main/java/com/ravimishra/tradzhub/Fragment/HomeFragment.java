package com.ravimishra.tradzhub.Fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.tabs.TabLayout;
import com.ravimishra.tradzhub.Activity.ItemDetailActivity;
import com.ravimishra.tradzhub.Activity.ProductActivity;
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
    private ArrayList<Integer> topBannerList = new ArrayList<>();
    private static final String TAG = "HomeFragment";

   private Button popularBtn,featuredBtn,newArrivalBtn, storeBtn;
    //RelaytiveLayout

    private RecyclerView topMenuRecyclerView,popularRecyclerView, recylerView2,recylerView3, storeRecyclerView;
    //ViewPager
    private ViewPager viewP;
   private TabLayout tabs;
   private ViewPager viewPager2;

    private int[] banner = {R.drawable.banner4, R.drawable.banner3, R.drawable.banner2, R.drawable.banner1,R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner1};

   private List<TopMenuModel> topModel = new ArrayList<>();

    // recylverview 1
   private List<ProductModel> productModel = new ArrayList<>();
    // recylverview 1
  private   List<ProductModel> productModel2 = new ArrayList<>();
    // recylverview 1
   private List<ProductModel> productModel3 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        topBannerList.clear();

        topBannerList
                .add(banner[0]);
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
        storeBtn = view.findViewById(R.id.storeBtn);

        // initilaszes the button and sets click listenners
        setUpButtons();
        storeRecyclerView = view.findViewById(R.id.storeRecyclerView);
        viewP = view.findViewById(R.id.viewPager);

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

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getFragmentManager());
        adapter.addFragment(new JustInFragment(), "Just In");

        adapter.addFragment(new BestSellingFragment(), "Best Selling");
        adapter.addFragment(new FlashDealFragment(), "Flash Sale");
        viewPager2.setAdapter(adapter);
    }

    public void setUpButtons(){
        newArrivalBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ItemDetailActivity.class);
            i.putExtra("title","New Arrivals");
            startActivity(i);
        });

        popularBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ItemDetailActivity.class);
            i.putExtra("title","Popular");
            startActivity(i);
        });

        featuredBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ItemDetailActivity.class);
            i.putExtra("title","Featured");
            startActivity(i);
        });
        storeBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), StoreActivity.class);
            i.putExtra("type",2);
            startActivity(i);
        });
        popularRecyclerView.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ProductActivity.class);
            startActivity(i);
        });
        recylerView2.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ProductActivity.class);
            startActivity(i);
        });
        recylerView3.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ProductActivity.class);
            startActivity(i);
        });
    }
    private void inisilizerecycler() {

        Log.d(TAG, "inisilizerecycler: ");

        topMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recylerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recylerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

       // settings up adapters
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
        }, 3000, 3000);

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



