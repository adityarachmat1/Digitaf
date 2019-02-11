package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {
    @SerializedName("id_no")
    private String id_no;
    @SerializedName("id_expire_date")
    private String id_expire_date; //String kosong
    @SerializedName("gender")
    private String gender; //M == male | F == Female
    @SerializedName("birth_place")
    private String birth_place;
    @SerializedName("birth_date")
    private String birth_date; //datetime
    @SerializedName("marital_status")
    private String marital_status; //MAR | DIN | DIV
    @SerializedName("religion")
    private String religion; //KATOLIK | HINDU | KONFUCHU | ISLAM | KRISTEN | BUDHA

    public Customer() {
    }

    public Customer(String id_no, String id_expire_date, String gender, String birth_place, String birth_date, String marital_status, String religion) {
        this.id_no = id_no;
        this.id_expire_date = id_expire_date;
        this.gender = gender;
        this.birth_place = birth_place;
        this.birth_date = birth_date;
        this.marital_status = marital_status;
        this.religion = religion;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getId_expire_date() {
        return id_expire_date;
    }

    public void setId_expire_date(String id_expire_date) {
        this.id_expire_date = id_expire_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
}
