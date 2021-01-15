package com.ravimishra.tradzhub.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Adapter.AddressAdapter;
import com.ravimishra.tradzhub.Model.AddressModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.Utils.Constants;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllAddressActivity extends AppCompatActivity {

    private Button _btnAddAddress;
    private Button _btnDeliverHere;
    private RecyclerView _rvAddress;
    private ProgressBar _progressBar;

    private List<AddressModel.ResponseData> addressModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        _btnAddAddress = findViewById(R.id.addAddressbtn);
        _btnDeliverHere = findViewById(R.id.deliverHereBtn);
        _rvAddress = findViewById(R.id.addressRecyclerView);
        _btnAddAddress = findViewById(R.id.addBtn);//addBtn
        _btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllAddressActivity.this, AddAddressActivity.class);
                startActivity(i);
            }
        });

        _progressBar = findViewById(R.id.pbShowAddress);
        _progressBar.setVisibility(View.VISIBLE);
        callApiForAddress();
    }

    private void callApiForAddress() {
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("token", "");

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        /** defining the category api call */
        Call<AddressModel> getAllAdressApi = service.getAllAddress(
                1,
                token
        );
        getAllAdressApi.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                Log.v("TAG_API", response.body() + "AllAddress Activity");
                Log.v("MY_TOKEN", token);

                try {
                    if (response.body().data.get(0).userEmail != null) {
                        addressModels = response.body().data;
                        _rvAddress.setLayoutManager(new LinearLayoutManager(AllAddressActivity.this, LinearLayoutManager.VERTICAL, false));
                        AddressAdapter addressAdapter = new AddressAdapter(AllAddressActivity.this, addressModels);
                        _rvAddress.setAdapter(addressAdapter);
                        _progressBar.setVisibility(View.GONE);
                    } else {
                        Log.e("TAG", "no address found");
                        Toast.makeText(AllAddressActivity.this, "No address found", Toast.LENGTH_SHORT).show();
                        _progressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Log.e("ERROR AddressActivtiy", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured ");
            }
        });
    }
}
