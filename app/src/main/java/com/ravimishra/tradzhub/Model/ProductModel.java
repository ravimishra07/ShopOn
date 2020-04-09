package com.ravimishra.tradzhub.Model;


public class ProductModel {
    String productName;
    int productPrice;
    String imageName;

    public ProductModel(String productName, int productPrice, String imageName) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageName = imageName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
