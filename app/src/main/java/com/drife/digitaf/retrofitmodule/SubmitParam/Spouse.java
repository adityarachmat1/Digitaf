package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Spouse implements Serializable {
    private String spouse_name;
    private String spouse_id_no;
    private String spouse_birth_place;
    private String spouse_gender; //M || F
    private String spouse_birth_date;
    private String spouse_address;
    private String spouse_rt;
    private String spouse_rw;
    private String spouse_kelurahan;
    private String spouse_kecamatan;
    private String spouse_zipcode;
    private String spouse_city;
    private String spouse_religion;
    @SerializedName("spouse_customer_model")
    private String spouse_job; //PROF | EMP | SME

    public Spouse() {
    }

    public Spouse(String spouse_name, String spouse_id_no, String spouse_birth_place, String spouse_gender, String spouse_birth_date, String spouse_address, String spouse_rt, String spouse_rw, String spouse_kelurahan, String spouse_kecamatan, String spouse_zipcode, String spouse_city, String spouse_religion, String spouse_job) {
        this.spouse_name = spouse_name;
        this.spouse_id_no = spouse_id_no;
        this.spouse_birth_place = spouse_birth_place;
        this.spouse_gender = spouse_gender;
        this.spouse_birth_date = spouse_birth_date;
        this.spouse_address = spouse_address;
        this.spouse_rt = spouse_rt;
        this.spouse_rw = spouse_rw;
        this.spouse_kelurahan = spouse_kelurahan;
        this.spouse_kecamatan = spouse_kecamatan;
        this.spouse_zipcode = spouse_zipcode;
        this.spouse_city = spouse_city;
        this.spouse_religion = spouse_religion;
        this.spouse_job = spouse_job;
    }

    public String getSpouse_name() {
        return spouse_name;
    }

    public void setSpouse_name(String spouse_name) {
        this.spouse_name = spouse_name;
    }

    public String getSpouse_id_no() {
        return spouse_id_no;
    }

    public void setSpouse_id_no(String spouse_id_no) {
        this.spouse_id_no = spouse_id_no;
    }

    public String getSpouse_birth_place() {
        return spouse_birth_place;
    }

    public void setSpouse_birth_place(String spouse_birth_place) {
        this.spouse_birth_place = spouse_birth_place;
    }

    public String getSpouse_gender() {
        return spouse_gender;
    }

    public void setSpouse_gender(String spouse_gender) {
        this.spouse_gender = spouse_gender;
    }

    public String getSpouse_birth_date() {
        return spouse_birth_date;
    }

    public void setSpouse_birth_date(String spouse_birth_date) {
        this.spouse_birth_date = spouse_birth_date;
    }

    public String getSpouse_address() {
        return spouse_address;
    }

    public void setSpouse_address(String spouse_address) {
        this.spouse_address = spouse_address;
    }

    public String getSpouse_rt() {
        return spouse_rt;
    }

    public void setSpouse_rt(String spouse_rt) {
        this.spouse_rt = spouse_rt;
    }

    public String getSpouse_rw() {
        return spouse_rw;
    }

    public void setSpouse_rw(String spouse_rw) {
        this.spouse_rw = spouse_rw;
    }

    public String getSpouse_kelurahan() {
        return spouse_kelurahan;
    }

    public void setSpouse_kelurahan(String spouse_kelurahan) {
        this.spouse_kelurahan = spouse_kelurahan;
    }

    public String getSpouse_kecamatan() {
        return spouse_kecamatan;
    }

    public void setSpouse_kecamatan(String spouse_kecamatan) {
        this.spouse_kecamatan = spouse_kecamatan;
    }

    public String getSpouse_zipcode() {
        return spouse_zipcode;
    }

    public void setSpouse_zipcode(String spouse_zipcode) {
        this.spouse_zipcode = spouse_zipcode;
    }

    public String getSpouse_city() {
        return spouse_city;
    }

    public void setSpouse_city(String spouse_city) {
        this.spouse_city = spouse_city;
    }

    public String getSpouse_religion() {
        return spouse_religion;
    }

    public void setSpouse_religion(String spouse_religion) {
        this.spouse_religion = spouse_religion;
    }

    public String getSpouse_job() {
        return spouse_job;
    }

    public void setSpouse_job(String spouse_job) {
        this.spouse_job = spouse_job;
    }


}
