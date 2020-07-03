package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StoreModel implements Serializable {
    @SerializedName("status")
    public Integer status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<ResponseData> data = null;

    public StoreModel(Integer status, String message, List<StoreModel.ResponseData> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public class ResponseData implements Serializable {


        @SerializedName("store_id")
        public String storeID;

        @SerializedName("store_name")
        public String storeName;
        @SerializedName("description")
        public String description;
        @SerializedName("store_img")
        public String storeImage;

    }
}
