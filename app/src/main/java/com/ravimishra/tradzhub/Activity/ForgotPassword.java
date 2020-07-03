package com.ravimishra.tradzhub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ravimishra.tradzhub.Model.AuthModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPassword extends AppCompatActivity {


    private EditText _email;
    private Button _sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        _email = findViewById(R.id.input_email);
        _sendOtp = findViewById(R.id.btn_send_otp);
        _sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    callApi();
                } else {
                    onLoginFailed();
                }
            }
        });
    }

    public void callApi() {
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //defining the call
        String email = _email.getText().toString().trim();
        Call<AuthModel> call = service.forgotPassword(
                1,
                email
        );

        //calling the api
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                //hiding progress dialog
                // progressDialog.dismiss();
                AuthModel authModel = response.body();
                Log.v("forgotPasswordSuccess", "response " + authModel.mesage);
                Intent i = new Intent(ForgotPassword.this, ResetPassword.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                // progressDialog.dismiss();
                Log.v("forgotPasswordSuccess", "api call failed ");
                Toast.makeText(ForgotPassword.this, "Some error occured. Please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        //  _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _email.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("enter a valid email address");
            valid = false;
        } else {
            _email.setError(null);
        }


        return valid;
    }
}
