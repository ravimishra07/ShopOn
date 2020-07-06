package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressModel {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String mesage;

    @SerializedName("data")
    public List<AddressModel.ResponseData> data = null;

    public class ResponseData {

        @SerializedName("ship_name")
        public String nameOfUserAtAddress;
        @SerializedName("ship_email")
        public String userEmail;

        @SerializedName("ship_phone")
        public String userPhoneNumber;
        @SerializedName("ship_address")
        public String userAddress;

        @SerializedName("ship_country")
        public String userCountry;
        @SerializedName("ship_state")
        public String shipState;

        @SerializedName("ship_city")
        public String userCity;
        @SerializedName("ship_pincode")
        public String userPinCode;

    }
}
