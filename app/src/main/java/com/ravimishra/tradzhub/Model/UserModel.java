package com.ravimishra.tradzhub.Model;

public class UserModel {

    private int id;
    private String name;
    private String email;
    private String password;
    private String rePassword;
    private String phNumber;
    private String address;


    public UserModel(String name, String email, String password, String rePassword, String phNumber, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
        this.phNumber = phNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}