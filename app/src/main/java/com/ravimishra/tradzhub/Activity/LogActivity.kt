package com.ravimishra.tradzhub.Activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ravimishra.tradzhub.Activity.MainPage
import com.ravimishra.tradzhub.Model.AuthModel
import com.ravimishra.tradzhub.R
import com.ravimishra.tradzhub.Utils.Constants
import com.ravimishra.tradzhub.api.APIService
import com.ravimishra.tradzhub.api.APIUrl
import kotlinx.android.synthetic.main.activity_log_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        btn_login.setOnClickListener {
            login()
        }
        tvForgotPassword.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@LogActivity, "click is working", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LogActivity, ForgotPassword::class.java)
            startActivity(intent)
        })
        link_signup.setOnClickListener {
            // Start the Signup activity
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
            finish()
        }
    }

    fun login() {
        Log.d(TAG, "Login")
        if (!validate()) {
            onLoginFailed()
            return
        }
        btn_login.isEnabled = false
        val progressDialog = ProgressDialog(this@LogActivity,
                R.style.AppTheme)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Authenticating...")
        progressDialog.show()
        val email = input_email.text.toString()
        val password = input_password.toString()

        // TODO: Implement your own authentication logic here.

        //building retrofit object
        val retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.NEW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //Defining retrofit api service
        val service = retrofit.create(APIService::class.java)

        //Defining the user object as we need to pass it with the call
        //UserModel user = new UserModel(name, email, password, rePassword, phNumber, address);

        //defining the call
        val call = service.userLogin(
                1,
                email,
                password
        )

        //calling the api
        call.enqueue(object : Callback<AuthModel> {
            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                //hiding progress dialog
                progressDialog.dismiss()
                val authModel = response.body()
                if (response.body()?.status === "0") {
                    input_email.error = "email or password is incorrect"
                    return
                }
                Log.v("successLogin", authModel!!.data[0].token)
                val username = authModel.data[0].username
                val preferences = PreferenceManager.getDefaultSharedPreferences(this@LogActivity)
                val editor = preferences.edit()
                editor.putString(Constants.SHARED_EMAIL, email)
                editor.putString(Constants.SHARED_USERNAME, username)
                editor.putString(Constants.SHARED_PASSWORD, password)
                editor.putString("token", authModel.data[0].token)
                editor.apply()
                val preferences2 = PreferenceManager.getDefaultSharedPreferences(this@LogActivity)
                val user_name = preferences2.getString(Constants.SHARED_USERNAME, "")
                if (!user_name.equals("", ignoreCase = true)) {
                    Log.v("email_tag", user_name!!)
                }
                val i = Intent(this@LogActivity, MainPage::class.java)
                startActivity(i)
            }

            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                progressDialog.dismiss()
                Log.v("failLogin", "failed to sign up" + t.message)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == Activity.RESULT_OK) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    fun onLoginSuccess() {
        btn_login.isEnabled = true
        finish()
    }

    fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        btn_login.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true
        val email = input_email.text.toString()
        val password = input_password.text.toString()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.error = "enter a valid email address"
            valid = false
        } else {
            input_email.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_password.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            input_password.error = null
        }
        return valid
    }

    companion object {
        private const val TAG = "LoginActivity"
        private const val REQUEST_SIGNUP = 0
    }
}