package com.ravimishra.tradzhub.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private UserModel user;

    public RegisterModel(Boolean error, String message, UserModel user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public RegisterModel(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public UserModel getUser() {
        return user;
    }
}