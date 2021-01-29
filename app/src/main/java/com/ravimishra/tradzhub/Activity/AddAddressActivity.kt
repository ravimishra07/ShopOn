package com.ravimishra.tradzhub.Activity

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import com.ravimishra.tradzhub.R
import kotlinx.android.synthetic.main.activity_add_address.*

class AddAddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        saveAddressBtn.setOnClickListener {
            address_progresbar.visibility = View.VISIBLE
            saveAddressApiCall()
        }
    }

    private fun saveAddressApiCall() {
        // call api to save address
    }
}