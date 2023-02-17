package com.ravimishra.tradzhub.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ravimishra.tradzhub.R;
//import com.razorpay.Checkout;
//import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PayementActivity extends AppCompatActivity {
    private static final String RAZORPAY_API_KEY = "rzp_test_AXtJiPK3oRVSCs";
    private Button buttonConfirmOrder;
    private EditText editTextPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);
        findViews();
        listeners();
    }

    public void findViews() {
        buttonConfirmOrder = findViewById(R.id.buttonConfirmOrder);
        editTextPayment = findViewById(R.id.editTextPayment);
    }

    public void listeners() {


        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPayment.getText().toString().equals("")) {
                    Toast.makeText(PayementActivity.this, "Please fill payment", Toast.LENGTH_LONG).show();
                    return;
                }
                startPayment();
            }
        });
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

      //  final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Trazhub payment");
            options.put("description", "pay ");
            //You can omit the image option to fetch the image from dashboard
            // options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            String payment = editTextPayment.getText().toString();

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "ravimishra1017.rm@gmail.com");
            preFill.put("contact", "8279965181");

            options.put("prefill", preFill);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
