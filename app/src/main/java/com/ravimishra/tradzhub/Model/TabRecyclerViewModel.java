package com.ravimishra.tradzhub.Model;


public class TabRecyclerViewModel {
    String productName;
    String proctPrice;
    String offer;
    String rating;
    String productImage;

    public TabRecyclerViewModel(String productName, String proctPrice, String offer, String rating, String productImage) {
        this.productName = productName;
        this.proctPrice = proctPrice;
        this.offer = offer;
        this.rating = rating;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProctPrice() {
        return proctPrice;
    }

    public void setProctPrice(String proctPrice) {
        this.proctPrice = proctPrice;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
