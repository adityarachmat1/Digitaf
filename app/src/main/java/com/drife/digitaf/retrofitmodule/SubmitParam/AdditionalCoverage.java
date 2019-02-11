package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdditionalCoverage implements Serializable {
    @SerializedName("type")
    private String type;

    @SerializedName("nilai_premi")
    private String nilai_premi;

    public AdditionalCoverage() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNilai_premi() {
        return nilai_premi;
    }

    public void setNilai_premi(String nilai_premi) {
        this.nilai_premi = nilai_premi;
    }
}
