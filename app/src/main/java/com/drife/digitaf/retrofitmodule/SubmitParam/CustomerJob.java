package com.drife.digitaf.retrofitmodule.SubmitParam;

import java.io.Serializable;

public class CustomerJob implements Serializable {
    private String profession_name; //Sync dengan confins
    private String job_position; //Sync dengan confins
    private String company_name;

    public CustomerJob() {
    }

    public CustomerJob(String profession_name, String job_position, String company_name) {
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
