package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TradzHubProductModel implements Serializable {
    @SerializedName("status")
    public Integer status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<TradzHubProductModel.ResponseData> data = null;

    public class ResponseData implements Serializable {
        @SerializedName("product_id")
        public String productID;
        @SerializedName("title")
        public String title;

        @SerializedName("description")
        public String description;
        @SerializedName("sale_price")
        public String salePrice;

        @SerializedName("purchase_price")
        public String purchasePrice;
        @SerializedName("category")
        public String category;

        @SerializedName("sub_category")
        public String subCategory;
        @SerializedName("tag")
        public String tag;

        @SerializedName("brand")
        public String brand;
        @SerializedName("current_stock")
        public String currentStock;


        @SerializedName("stock_unit")
        public String stockUnit;
        @SerializedName("number_of_view")
        public String noOfViews;

        @SerializedName("discount")
        public String discount;
        @SerializedName("discount_type")
        public String discountType;

        @SerializedName("tax")
        public String tax;
        @SerializedName("tax_type")
        public String taxType;

        @SerializedName("available_color")
        public String availableColors;
        @SerializedName("shipping_cost")
        public String shippingCost;

        @SerializedName("product_image")
        public String productImage;

    }
}
