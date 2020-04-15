package com.ravimishra.tradzhub.Model;

public class ProductDetailModel {
    String price, name, info, diliveryTime;

    public ProductDetailModel(String price, String name, String info, String diliveryTime) {
        this.price = price;
        this.name = name;
        this.info = info;
        this.diliveryTime = diliveryTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDiliveryTime() {
        return diliveryTime;
    }

    public void setDiliveryTime(String diliveryTime) {
        this.diliveryTime = diliveryTime;
    }
}
