package com.ravimishra.tradzhub.api;

import com.ravimishra.tradzhub.Model.RegisterModel;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

}
