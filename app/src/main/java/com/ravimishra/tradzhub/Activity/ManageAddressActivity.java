package com.ravimishra.tradzhub.Activity;


import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Adapter.AddressAdapter;
import com.ravimishra.tradzhub.Model.AddressModel;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ManageAddressActivity extends AppCompatActivity {

    @BindView(R.id.addAddressbtn)
    Button _btnAddAddress;

    @BindView(R.id.deliverHereBtn)
    Button _btnDeliverHere;

    @BindView(R.id.addressRecyclerView)
    RecyclerView _rvAddress;

    private List<AddressModel> addressModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        _btnAddAddress = findViewById(R.id.addAddressbtn);
        _btnDeliverHere = findViewById(R.id.deliverHereBtn);
        _rvAddress = findViewById(R.id.addressRecyclerView);
        AddressModel addressModel = new AddressModel("Ravi Mishra", "8/75, Vikas Nagar, near Power house", "Lucknow", "226022", "+91 82788999");
        AddressModel addressMode2 = new AddressModel("Anil Mishra", "7, Vikas Enclave, near Power house", "Lucknow", "226022", "+91 98288999");
        addressModels.add(addressModel);
        addressModels.add(addressMode2);
        _rvAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AddressAdapter addressAdapter = new AddressAdapter(ManageAddressActivity.this, addressModels);
        _rvAddress.setAdapter(addressAdapter);

    }
}
