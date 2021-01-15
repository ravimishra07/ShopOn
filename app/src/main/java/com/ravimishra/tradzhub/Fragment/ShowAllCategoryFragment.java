package com.ravimishra.tradzhub.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Adapter.AllCategoryAdapter;
import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShowAllCategoryFragment extends Fragment {

    RecyclerView catRecyclerView;
    private CategoryModel menuData;
    private ProgressBar progressbar;

    public ShowAllCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_all_category, container, false);
        catRecyclerView = view.findViewById(R.id.allCategoryRecyclerview);
        progressbar = view.findViewById(R.id.progresbar);
       // callApiForCategories();
        return view;
    }

    private void callApiForCategories() {
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /** Defining retrofit api service */
        APIService service = retrofit.create(APIService.class);

        /** defining the category api call */
        Call<CategoryModel> callCategoryModel = service.getCategory(
                1
        );

        /**calling the api */
        callCategoryModel.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {

                Log.v("TAG_API", response.body().data.get(0).getCategoryImage + "msg");
                List<CategoryModel.ResponseData> responseData = response.body().data;
                menuData = new CategoryModel(1, "model", responseData);
                catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                AllCategoryAdapter showAllCategoryFragment = new AllCategoryAdapter(getContext(), menuData.data);
                catRecyclerView.setAdapter(showAllCategoryFragment);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.v("TAG_API_SHOW_ALL_CAT", "Some error occured show all cat");
            }
        });

    }
}
