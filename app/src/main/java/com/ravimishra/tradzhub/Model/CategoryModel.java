package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryModel implements Serializable {

    @SerializedName("status")
    public Integer status;
    @SerializedName("message")
    public String message;

    public CategoryModel(Integer status, String message, List<ResponseData> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @SerializedName("data")
    public List<ResponseData> data = null;

    public class ResponseData implements Serializable {


        @SerializedName("category_name")
        public String categoryName;

        @SerializedName("cat_image")
        public String getCategoryImage;
        @SerializedName("category_id")
        public String categoryID;

    }

    /*
    String categoryName;
    String categoryImage;

    public CategoryModel(String categoryName, String categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
    */

}
