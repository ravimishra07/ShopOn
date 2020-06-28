package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewProductModel {

    @SerializedName("product_id")
    public String productID;
    @SerializedName("title")
    public String tltle;

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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getTltle() {
        return tltle;
    }

    public void setTltle(String tltle) {
        this.tltle = tltle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(String currentStock) {
        this.currentStock = currentStock;
    }

    public String getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    public String getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(String noOfViews) {
        this.noOfViews = noOfViews;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getAvailableColors() {
        return availableColors;
    }

    public void setAvailableColors(String availableColors) {
        this.availableColors = availableColors;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
