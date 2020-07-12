package com.ravimishra.tradzhub.api;

import com.ravimishra.tradzhub.Model.AddressModel;
import com.ravimishra.tradzhub.Model.AuthModel;
import com.ravimishra.tradzhub.Model.BannerImageModel;
import com.ravimishra.tradzhub.Model.CategoryModel;
import com.ravimishra.tradzhub.Model.StoreModel;
import com.ravimishra.tradzhub.Model.TradzHubProductModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    /**
     * register call
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<AuthModel> createUser(
            @Field("register") int register,
            @Field("username") String username,

            @Field("surname") String surname,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("address1") String address1,

            @Field("address2") String address2,
            @Field("city") String city,
            @Field("zip") String zip,

            @Field("password") String password,
            @Field("country") String country,
            @Field("state") String state
    );

    /**
     * forgot password call
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<AuthModel> forgotPassword(
            @Field("forgetpassword") int forgetpassword,
            @Field("email") String email
    );

    /**
     * change password call
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<AuthModel> resetPassword(
            @Field("changepassword") int changepassword,
            @Field("oldpassword") String oldpassword,
            @Field("newpassword") String newPassword);


    /**
     * login call
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<AuthModel> userLogin(
            @Field("login") int register,
            @Field("email") String email,
            @Field("password") String password
    );


    /**
     * Top menu
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<CategoryModel> getCategory(
            @Field("allcategory") int allcategory);

    /**
     * featured products
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getCategoryDetail(
            @Field("productbycategory") int productbycategory,
            @Field("category") int categoryID);

           /* @Field("category") int category,
            @Field("subcategory") int subcategory
    */

    /**
     * featured products
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getFeaturedProducts(
            @Field("featuredproduct") int featuredProduct

           /* @Field("category") int category,
            @Field("subcategory") int subcategory
    */
    );

    /**
     * latest products
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getLatestProducts(
            @Field("latestproduct") int latestProduct
            /*,
            @Field("category") int category,
            @Field("subcategory") int subcategory

             */
    );

    /**
     * recently Viewed products
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getRecentlyViewedProducts(
            @Field("recentlyview") int recentlyViewedProduct
            /*,
            @Field("category") int category,
            @Field("subcategory") int subcategory
            */
    );

    //mostlyview

    /**
     * Mostly Viewed products
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getMostlyViewedProducts(
            @Field("mostlyview") int recentlyViewedProduct
            /*,
            @Field("category") int category,
            @Field("subcategory") int subcategory
    */
    );

    /**
     * get stores
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<StoreModel> getStores(
            @Field("getallstore") int getStores

    );

    /**
     * get stores
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getStoreProducts(
            @Field("getallproductbystore") int getStoresProducts,
            @Field("store_id") int storeID
    );

    /**
     * get banner images
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<BannerImageModel> getBannerImages(
            @Field("sliderbtn") int bannerID);

    /**
     * add user address in database
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<AuthModel> addUserAddress(
            @Field("addressbtn") int addressKey,
            @Field("user_id") String token,

            @Field("ship_name") String name,
            @Field("ship_email") String email,
            @Field("ship_phone") String phone,
            @Field("ship_address") String address,
            @Field("ship_city") String city,
            @Field("ship_pincode") String zip,
            @Field("ship_country") String country,
            @Field("ship_state") String state
    );

    /**
     * get all address of user
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<AddressModel> getAllAddress(
            @Field("getaddress") int getAddress,
            @Field("user_id") String userId
    );

    /**
     * get cart item detail by passing product id
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getItemByProductID(
            @Field("singleproductbtn") int getProduct,
            @Field("product_id") int productId
    );

    /**
     * save item to widhlist
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<AuthModel> saveItemToWishlist(
            @Field("wishlistbtn") int getProduct,
            @Field("user_id") int userid,
            @Field("product_id") int productId
    );

    /**
     * save item to widhlist
     */
    @FormUrlEncoded
    @POST("function.php")
    Call<TradzHubProductModel> getWishlistData(
            @Field("getwishlistbtn") int getProduct,
            @Field("user_id") int userid
    );
}
