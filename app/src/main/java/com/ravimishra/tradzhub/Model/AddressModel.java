package com.ravimishra.tradzhub.Model;

public class AddressModel {
    String id;
    String username;
    String address;
    String city;
    String pinCode;
    String phoneNumber;

    public AddressModel(String username, String address, String city, String pinCode, String phoneNumber) {
        this.username = username;
        this.address = address;
        this.city = city;
        this.pinCode = pinCode;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
