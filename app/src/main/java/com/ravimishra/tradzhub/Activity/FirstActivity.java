package com.ravimishra.tradzhub.Activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ravimishra.tradzhub.R;
import com.ravimishra.tradzhub.Utils.Constants;

public class FirstActivity extends AppCompatActivity {
    ImageView logoImage;
    CardView cardView;
    TextView logoTv;
    String username = "";
    Boolean isLoggedin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        logoImage = findViewById(R.id.logoImage);
        cardView = findViewById(R.id.cv);
        logoTv = findViewById(R.id.logoText);

        animateLogo(-1000f, 0f, cardView);
        animateLogo(800f, 0f, logoTv);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FirstActivity.this);
        String email = preferences.getString(Constants.INSTANCE.getSHARED_EMAIL(), "");
        String password = preferences.getString(Constants.INSTANCE.getSHARED_PASSWORD(), "");
        String token = preferences.getString("token", "");
        if (email != null && password != null && token != null && !email.equalsIgnoreCase("")
                && !password.equalsIgnoreCase("") && !token.equalsIgnoreCase("")) {

            Log.v("email_tag", email);
            Log.v("email_tag", password);
            Log.v("email_tag", token);
            isLoggedin = true;
        }


        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1200);
                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                    intent.putExtra("username", email);
                    intent.putExtra("isLoggedin", isLoggedin);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();


        cardView.setOnClickListener(v -> {
//            Intent i = new Intent(FirstActivity.this, MainPage.class);
//            startActivity(i);
            // animateLogo();
        });
    }

    private void animateLogo(float from, float to, View view) {
        ValueAnimator va = ValueAnimator.ofFloat(from, to);
        int mDuration = 1000; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationY((float) animation.getAnimatedValue());
            }
        });
//        va.setRepeatCount(1);
        va.start();

    }

}
