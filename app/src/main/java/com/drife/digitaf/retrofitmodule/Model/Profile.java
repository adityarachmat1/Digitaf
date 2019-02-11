package com.drife.digitaf.retrofitmodule.Model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("address")
    private String address;
    @SerializedName("address_2")
    private String address_2;
    @SerializedName("phone")
    private String phone;
    @SerializedName("phone_alt")
    private String phone_alt;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("mobile_alt")
    private String mobile_alt;
    @SerializedName("fax")
    private String fax;
    @SerializedName("fax_alt")
    private String fax_alt;
    @SerializedName("info")
    private String info;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("full_phone")
    private String full_phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_alt() {
        return phone_alt;
    }

    public void setPhone_alt(String phone_alt) {
        this.phone_alt = phone_alt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile_alt() {
        return mobile_alt;
    }

    public void setMobile_alt(String mobile_alt) {
        this.mobile_alt = mobile_alt;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax_alt() {
        return fax_alt;
    }

    public void setFax_alt(String fax_alt) {
        this.fax_alt = fax_alt;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFull_phone() {
        return full_phone;
    }

    public void setFull_phone(String full_phone) {
        this.full_phone = full_phone;
    }
}
