package com.ravimishra.tradzhub.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ravimishra.tradzhub.Model.AuthModel;
import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.Utils.Constants;
import com.ravimishra.tradzhub.api.APIService;
import com.ravimishra.tradzhub.api.APIUrl;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_last_name)
    EditText _lastName;
    @BindView(R.id.input_email)
    EditText _inputEmail;
    @BindView(R.id.input_mobile)
    EditText _inputMobile;
    @BindView(R.id.input_address)
    EditText _addressText;
    @BindView(R.id.input_city)
    EditText _inputCity;
    @BindView(R.id.input_zip_code)
    EditText _inputZip;
    @BindView(R.id.input_password)
    EditText _inputPassword;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // signup();
                userSignUp();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LogActivity.class);
                startActivity(intent);
                finish();
                //  overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainPage.this);
//        String name = preferences.getString(Constants.SHARED_EMAIL, "");        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String lastName = _nameText.getText().toString();
        String email = _inputEmail.getText().toString();
        String mobile = _inputMobile.getText().toString();
        String address = _addressText.getText().toString();
        String city = _inputCity.getText().toString();
        String zipCode = _inputZip.getText().toString();
        String password = _inputPassword.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        userSignUp();


    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String lastName = _nameText.getText().toString();
        String email = _inputEmail.getText().toString();
        String mobile = _inputMobile.getText().toString();
        String address = _addressText.getText().toString();
        String city = _inputCity.getText().toString();
        String zipCode = _inputZip.getText().toString();
        String password = _inputPassword.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (zipCode.isEmpty() || zipCode.length() < 6 || zipCode.length() > 8 ){
            _inputZip.setError("Enter valid zip code");
        }
        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _inputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            _inputEmail.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            _inputMobile.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _inputMobile.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _inputPassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }


    private void userSignUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        //getting the user values
        //final RadioButton radioSex = (RadioButton) findViewById(radioGender.getCheckedRadioButtonId());
        String name = _nameText.getText().toString();
        String lastName = _lastName.getText().toString();
        String email = _inputEmail.getText().toString();
        String mobile = _inputMobile.getText().toString();
        String address = _addressText.getText().toString();
        String city = _inputCity.getText().toString();
        String zipCode = _inputZip.getText().toString();
        String password = _inputPassword.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // String gender = radioSex.getText().toString();


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        //UserModel user = new UserModel(name, email, password, rePassword, phNumber, address);

        //defining the call
        Call<AuthModel> call = service.createUser(
                1,
                name,
                lastName,
                email,
                mobile,
                address,
                address,
                city,
                zipCode,
                password,
                "India",
                "India"
        );

        //calling the api
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                AuthModel authModel = response.body();
                if (response.body().status == "0") {
                    _inputEmail.setError("Email already exist");
                    return;
                }
                Log.v("successRegister", authModel.data.get(0).token);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.INSTANCE.getSHARED_EMAIL(), email);
                editor.putString(Constants.INSTANCE.getSHARED_USERNAME(), name);
                editor.putString(Constants.INSTANCE.getSHARED_PASSWORD(), password);
                editor.putString("token", authModel.data.get(0).token);
                editor.apply();

                SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                String email1 = preferences2.getString("email", "");
                if(!email1.equalsIgnoreCase("")) {
                    Log.v("email_tag",email1);
                }

                Intent i = new Intent(RegisterActivity.this, MainPage.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.v("failRegister", "failed to sign up"+t.getMessage());

            }
        });
    }
}