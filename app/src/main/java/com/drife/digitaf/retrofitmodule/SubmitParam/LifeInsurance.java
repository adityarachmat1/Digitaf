package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LifeInsurance implements Serializable {

    @SerializedName("insurance_name")
    private String insurance_name;

    @SerializedName("insurance_premi")
    private String insurance_premi;

    @SerializedName("payment_type")
    private String payment_type;

    public LifeInsurance() {
    }

    public String getInsurance_name() {
        return insurance_name;
    }

    public void setInsurance_name(String insurance_name) {
        this.insurance_name = insurance_name;
    }

    public String getInsurance_premi() {
        return insurance_premi;
    }

    public void setInsurance_premi(String insurance_premi) {
        this.insurance_premi = insurance_premi;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
