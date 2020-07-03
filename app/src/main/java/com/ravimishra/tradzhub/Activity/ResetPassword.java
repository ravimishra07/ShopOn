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

public class ResetPassword extends AppCompatActivity {
    EditText _oldPasswod, _newPassword;
    Button _submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        _oldPasswod = findViewById(R.id.old_password);
        _newPassword = findViewById(R.id.new_password);
        _submitButton = findViewById(R.id.btn_submit);
        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onLoginFailed();
                } else {
                    resetPassword();
                }
            }
        });
    }

    public void resetPassword() {

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //defining the call
        String oldPass = _oldPasswod.getText().toString().trim();
        String newPass = _newPassword.getText().toString().trim();

        Call<AuthModel> call = service.resetPassword(
                1,
                oldPass,
                newPass
        );

        //calling the api
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                //hiding progress dialog
                // progressDialog.dismiss();
                AuthModel authModel = response.body();
                Log.v("resetPasswordSuccess", "response " + authModel.mesage);
                Intent i = new Intent(ResetPassword.this, MainPage.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                // progressDialog.dismiss();
                Log.v("forgotPasswordSuccess", "api call failed ");
                Toast.makeText(ResetPassword.this, "Some error occured. Please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        // _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String oldPassword = _oldPasswod.getText().toString();
        String newPassord = _newPassword.getText().toString();


        if (oldPassword.isEmpty() || oldPassword.length() < 4 || oldPassword.length() > 10) {
            _oldPasswod.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _oldPasswod.setError(null);
        }
        if (newPassord.isEmpty() || newPassord.length() < 4 || newPassord.length() > 10) {
            _newPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _newPassword.setError(null);
        }


        return valid;
    }
}
