package com.ravimishra.tradzhub.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.ravimishra.tradzhub.Model.AddressModel;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.viewHolder> {
    private Context context;
    private List<AddressModel> addressModels;

    public AddressAdapter(Context context, List<AddressModel> addressModels) {
        this.context = context;
        this.addressModels = addressModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final AddressModel addressModel = addressModels.get(position);
        holder.tvAddress.setText(addressModel.getAddress());
        holder.tvName.setText(addressModel.getUsername());
        holder.tvPhoneNumber.setText(addressModel.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvPhoneNumber;
        MaterialRadioButton materialRadioButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvName = itemView.findViewById(R.id.addressName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhone);
            materialRadioButton = itemView.findViewById(R.id.addressRadioBtn);


        }
    }
}
