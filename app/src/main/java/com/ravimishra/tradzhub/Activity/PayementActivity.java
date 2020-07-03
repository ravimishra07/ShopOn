package com.ravimishra.tradzhub.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ravimishra.tradzhub.R;

public class PayementActivity extends AppCompatActivity {
    private static final String RAZORPAY_API_KEY = "rzp_test_AXtJiPK3oRVSCs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);
    }
}
