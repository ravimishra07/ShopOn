package com.ravimishra.tradzhub.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ravimishra.tradzhub.R;

public class FirstActivity extends AppCompatActivity {
    ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        logoImage = findViewById(R.id.logoImage);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1200);
                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
        logoImage.setOnClickListener(v -> {
//            Intent i = new Intent(FirstActivity.this, MainPage.class);
//            startActivity(i);
        });
    }
}
