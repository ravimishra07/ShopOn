package com.ravimishra.tradzhub.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Adapter.ProductDetailAdapter;
import com.ravimishra.tradzhub.Model.BannerImageModel;
import com.ravimishra.tradzhub.Model.CategoryModel;
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


public class JustInFragment extends Fragment {

    RecyclerView recyclerView;
    List<TradzHubProductModel> tradzHubProductModels = new ArrayList<>();

    List<TabRecyclerViewModel> tabRecyclerViewModel = new ArrayList<>();
    private TradzHubProductModel model;

    public JustInFragment() {
        // Required empty public constructor
    }

    public JustInFragment(List<TradzHubProductModel> tradzHubProductModels) {
        this.tradzHubProductModels = tradzHubProductModels;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_just_in, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        callApi();

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void callApi() {
        //building retrofit object
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

        /** calling  api to get banner images*/
        Call<BannerImageModel> callBannerImgApi = service.getBannerImages(
                1
        );

        /** defining the latest product api call */
        Call<TradzHubProductModel> callFeaturedProducts = service.getFeaturedProducts(
                1
        );

        /** defining the latest product api call */
        Call<TradzHubProductModel> callLatestProducts = service.getLatestProducts(
                1
        );

        /** defining the latest product api call */
        Call<TradzHubProductModel> callRecentlyViewedProducts = service.getRecentlyViewedProducts(
                1
        );

        /** defining mostly viewed(popular) product api call */
        Call<TradzHubProductModel> callMostlyViewedproducts = service.getMostlyViewedProducts(
                1
        );


        callFeaturedProducts.enqueue(new Callback<TradzHubProductModel>() {
            @Override
            public void onResponse(Call<TradzHubProductModel> call, Response<TradzHubProductModel> response) {
                Log.v("TAG_API", response.body() + "callFeaturedProducts api");
                model = response.body();
                ProductDetailAdapter adapter = new ProductDetailAdapter(getContext(), model.data);
                recyclerView.setAdapter(adapter);
                // featuredBtn.setEnabled(true);
                // featuredProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TradzHubProductModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured callFeaturedProducts");
            }
        });
    }
}

