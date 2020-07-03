package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthModel {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String mesage;

    @SerializedName("data")
    public List<AuthModel.ResponseData> data = null;

    public class ResponseData {


        @SerializedName("token")
        public String token;
        @SerializedName("username")
        public String username;

    }
}
