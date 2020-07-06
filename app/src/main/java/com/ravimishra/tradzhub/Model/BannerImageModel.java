package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BannerImageModel {

    @SerializedName("status")
    public Integer status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")

    public List<BannerImageModel.ResponseData> data = null;

    public class ResponseData implements Serializable {
        @SerializedName("slides_image")
        public String slideImage;
    }

}
