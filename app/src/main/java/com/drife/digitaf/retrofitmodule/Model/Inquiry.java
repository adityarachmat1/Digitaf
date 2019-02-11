package com.drife.digitaf.retrofitmodule.Model;

import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Inquiry implements Serializable{
    @SerializedName("id")
    private String id;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("form_value")
    private InquiryParam inquiryParam;

    @SerializedName("status")
    private String status;

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

    @SerializedName("lead_id")
    private String lead_id;

    @SerializedName("dealer_id")
    private String dealer_id;

    @SerializedName("branch_id")
    private String branch_id;

    @SerializedName("images")
    private DocumentImage images;

    public Inquiry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public InquiryParam getInquiryParam() {
        return inquiryParam;
    }

    public void setInquiryParam(InquiryParam inquiryParam) {
        this.inquiryParam = inquiryParam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public DocumentImage getImages() {
        return images;
    }

    public void setImages(DocumentImage images) {
        this.images = images;
    }
}
