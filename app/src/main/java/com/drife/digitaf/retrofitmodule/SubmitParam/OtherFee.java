package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherFee implements Serializable {

    @SerializedName("admin_fee")
    private String admin_fee;

    public OtherFee() {
    }

    public String getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(String admin_fee) {
        this.admin_fee = admin_fee;
    }
}
