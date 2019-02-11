package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class User{
    @SerializedName("id")
    private String id;
    @SerializedName("npk_no")
    private String npk_no;
    @SerializedName("dealer_id")
    private String dealer_id;
    @SerializedName("email")
    private String email;
    @SerializedName("gender")
    private String gender;
    @SerializedName("dob")
    private String dob;
    @SerializedName("ktp_no")
    private String ktp_no;
    @SerializedName("image")
    private String image;
    @SerializedName("activation_code")
    private String activation_code;
    @SerializedName("fp_code")
    private String fp_code;
    @SerializedName("fp_temp")
    private String fp_temp;
    @SerializedName("fp_datetime")
    private String fp_datetime;
    @SerializedName("fp_attemp")
    private String fp_attemp;
    @SerializedName("otp_code")
    private String otp_code;
    @SerializedName("otp_date")
    private String otp_date;
    @SerializedName("type")
    private String type;
    @SerializedName("settings")
    private String settings;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("is_suspended")
    private String is_suspended;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("profile")
    private Profile profile;
    @SerializedName("dealer")
    private Dealer dealer;
    @SerializedName("branch")
    private Branch branch;
    @SerializedName("response_confins")
    private ResponseConfins responseConfins;
    @SerializedName("pv_insco")
    private PVInsco pv_insco;
    @SerializedName("is_password_expired")
    private String is_password_expired;

    public User() {
    }

    public User(String id, String npk_no, String dealer_id, String email, String gender, String dob, String ktp_no, String image, String activation_code, String fp_code, String fp_temp, String fp_datetime, String fp_attemp, String otp_code, String otp_date, String type, String settings, String created_by, String is_suspended, String is_active, String created_at, String updated_at) {
        this.id = id;
        this.npk_no = npk_no;
        this.dealer_id = dealer_id;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.ktp_no = ktp_no;
        this.image = image;
        this.activation_code = activation_code;
        this.fp_code = fp_code;
        this.fp_temp = fp_temp;
        this.fp_datetime = fp_datetime;
        this.fp_attemp = fp_attemp;
        this.otp_code = otp_code;
        this.otp_date = otp_date;
        this.type = type;
        this.settings = settings;
        this.created_by = created_by;
        this.is_suspended = is_suspended;
        this.is_active = is_active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNpk_no() {
        return npk_no;
    }

    public void setNpk_no(String npk_no) {
        this.npk_no = npk_no;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getKtp_no() {
        return ktp_no;
    }

    public void setKtp_no(String ktp_no) {
        this.ktp_no = ktp_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(String activation_code) {
        this.activation_code = activation_code;
    }

    public String getFp_code() {
        return fp_code;
    }

    public void setFp_code(String fp_code) {
        this.fp_code = fp_code;
    }

    public String getFp_temp() {
        return fp_temp;
    }

    public void setFp_temp(String fp_temp) {
        this.fp_temp = fp_temp;
    }

    public String getFp_datetime() {
        return fp_datetime;
    }

    public void setFp_datetime(String fp_datetime) {
        this.fp_datetime = fp_datetime;
    }

    public String getFp_attemp() {
        return fp_attemp;
    }

    public void setFp_attemp(String fp_attemp) {
        this.fp_attemp = fp_attemp;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }

    public String getOtp_date() {
        return otp_date;
    }

    public void setOtp_date(String otp_date) {
        this.otp_date = otp_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getIs_suspended() {
        return is_suspended;
    }

    public void setIs_suspended(String is_suspended) {
        this.is_suspended = is_suspended;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public ResponseConfins getResponseConfins() {
        return responseConfins;
    }

    public void setResponseConfins(ResponseConfins responseConfins) {
        this.responseConfins = responseConfins;
    }

    public PVInsco getPv_insco() {
        return pv_insco;
    }

    public void setPv_insco(PVInsco pv_insco) {
        this.pv_insco = pv_insco;
    }

    public String getIs_password_expired() {
        return is_password_expired;
    }

    public void setIs_password_expired(String is_password_expired) {
        this.is_password_expired = is_password_expired;
    }
}
