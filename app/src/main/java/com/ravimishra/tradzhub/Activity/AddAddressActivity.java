package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ravimishra.tradzhub.Model.AuthModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.Utils.Constants;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAddressActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPhoneNumber, etAddress, etCountry, etZip, etState, etCity;
    private Button submitButton;
    private AuthModel authModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        etName = findViewById(R.id.etFullname);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etZip = findViewById(R.id.etZip);
        etCountry = findViewById(R.id.etCountry);
        etState = findViewById(R.id.etState);
        progressBar = findViewById(R.id.progresbar);
        submitButton = findViewById(R.id.saveAddressBtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                saveAddressApiCall();
            }
        });
    }

    private void saveAddressApiCall() {
        String fullName = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String zip = etZip.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String state = etState.getText().toString().trim();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString(Constants.SHARED_TOKEN, "");

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        /** defining the category api call */
        Call<AuthModel> callAddAddressApi = service.addUserAddress(
                1,
                token,
                fullName,
                email,
                phone,
                address,
                city,
                zip,
                country,
                state
        );

        callAddAddressApi.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                Log.v("TAG_API", response.body() + "callFeaturedProducts api");
                authModel = response.body();
                Toast.makeText(AddAddressActivity.this, "Address added Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(AddAddressActivity.this, AllAddressActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                Log.v("TAG_API", "Some error occured ");
                Toast.makeText(AddAddressActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
