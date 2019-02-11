package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class Depresiasi {
    @SerializedName("id")
    private String id;
    /*@SerializedName("car_category_id")
    private String car_category_id;*/
    @SerializedName("tenor_code")
    private String tenor_code;
    @SerializedName("percentage")
    private String percentage;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("modified_by")
    private String modified_by;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("is_active_confins")
    private String is_active_confins;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("deleted_at")
    private String deleted_at;
    /*@SerializedName("car_category")
    private Category car_category;*/
    @SerializedName("tenor")
    private Tenor tenor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public String getCar_category_id() {
        return car_category_id;
    }

    public void setCar_category_id(String car_category_id) {
        this.car_category_id = car_category_id;
    }*/

    public String getTenor_code() {
        return tenor_code;
    }

    public void setTenor_code(String tenor_code) {
        this.tenor_code = tenor_code;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_active_confins() {
        return is_active_confins;
    }

    public void setIs_active_confins(String is_active_confins) {
        this.is_active_confins = is_active_confins;
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

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    /*public Category getCar_category() {
        return car_category;
    }

    public void setCar_category(Category car_category) {
        this.car_category = car_category;
    }*/

    public Tenor getTenor() {
        return tenor;
    }

    public void setTenor(Tenor tenor) {
        this.tenor = tenor;
    }
}
