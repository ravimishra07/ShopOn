package com.ravimishra.tradzhub.api;

import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.Model.NewProductModel;
import com.ravimishra.tradzhub.Model.RegisterModel;
import com.ravimishra.tradzhub.Model.TopMenuModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    //The register call
    @FormUrlEncoded
    @POST("register")
    public abstract Call<RegisterModel> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String rePassword,
            @Field("address") String address,
            @Field("phone") String phNumber);


    /** Top menu */
    @FormUrlEncoded
    @POST("function.php")
    public abstract Call<CategoryModel> getCategory(
            @Field("allcategory") int allcategory);



    /** featured products */
    @FormUrlEncoded
    @POST("function.php")
    public abstract Call<TradzHubProductModel> getFeaturedProducts(
            @Field("featuredproduct") int featuredProduct,
            @Field("category") int category,
            @Field("subcategory") int subcategory);

    /** latest products */
    @FormUrlEncoded
    @POST("function.php")
    public abstract Call<TradzHubProductModel> getLatestProducts(
            @Field("latestproduct") int latestProduct,
            @Field("category") int category,
            @Field("subcategory") int subcategory);

    /** recently Viewed products */
    @FormUrlEncoded
    @POST("function.php")
    public abstract Call<TradzHubProductModel> getRecentlyViewedProducts(
            @Field("recentlyview") int recentlyViewedProduct,
            @Field("category") int category,
            @Field("subcategory") int subcategory);
    //mostlyview

    /** Mostly Viewed products */
    @FormUrlEncoded
    @POST("function.php")
    public abstract Call<TradzHubProductModel> getMostlyViewedProducts(
            @Field("mostlyview") int recentlyViewedProduct,
            @Field("category") int category,
            @Field("subcategory") int subcategory);
}
