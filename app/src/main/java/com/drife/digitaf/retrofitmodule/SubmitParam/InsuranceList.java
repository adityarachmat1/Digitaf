package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InsuranceList implements Serializable {

    @SerializedName("year")
    private String year;

    @SerializedName("coverage")
    private String coverage;

    @SerializedName("payment_type")
    private String payment_type;

    @SerializedName("mainpremi")
    private String mainpremi;

    @SerializedName("additional_premi")
    private String additional_premi;

    @SerializedName("aditional_coverage")
    private List<AdditionalCoverage> aditional_coverage;

    public InsuranceList() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getMainpremi() {
        return mainpremi;
    }

    public void setMainpremi(String mainpremi) {
        this.mainpremi = mainpremi;
    }

    public String getAdditional_premi() {
        return additional_premi;
    }

    public void setAdditional_premi(String additional_premi) {
        this.additional_premi = additional_premi;
    }

    public List<AdditionalCoverage> getAditional_coverage() {
        return aditional_coverage;
    }

    public void setAditional_coverage(List<AdditionalCoverage> aditional_coverage) {
        this.aditional_coverage = aditional_coverage;
    }
}
