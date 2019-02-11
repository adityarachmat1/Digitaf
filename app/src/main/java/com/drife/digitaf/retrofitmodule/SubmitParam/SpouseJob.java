package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpouseJob implements Serializable {
    @SerializedName("profession_name")
    private String profession_name; //Sync dengan confins
    @SerializedName("job_position")
    private String job_position; //Sync dengan confins
    @SerializedName("company_name")
    private String company_name;

    public SpouseJob() {
    }

    public SpouseJob(String profession_name, String job_position, String company_name) {
        this.profession_name = profession_name;
        this.job_position = job_position;
        this.company_name = company_name;
    }

    public String getProfession_name() {
        return profession_name;
    }

    public void setProfession_name(String profession_name) {
        this.profession_name = profession_name;
    }

    public String getJob_position() {
        return job_position;
    }

    public void setJob_position(String job_position) {
        this.job_position = job_position;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
