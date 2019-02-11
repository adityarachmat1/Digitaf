package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Insurance implements Serializable {

    @SerializedName("insurance_name")
    private String insurance_name;

    @SerializedName("insurance_fee")
    private String insurance_fee;

    @SerializedName("insurance_list")
    private List<InsuranceList> insurance_list;

    public Insurance() {
    }

    public String getInsurance_name() {
        return insurance_name;
    }

    public void setInsurance_name(String insurance_name) {
        this.insurance_name = insurance_name;
    }

    public String getInsurance_fee() {
        return insurance_fee;
    }

    public void setInsurance_fee(String insurance_fee) {
        this.insurance_fee = insurance_fee;
    }

    public List<InsuranceList> getInsurance_list() {
        return insurance_list;
    }

    public void setInsurance_list(List<InsuranceList> insurance_list) {
        this.insurance_list = insurance_list;
    }
}
