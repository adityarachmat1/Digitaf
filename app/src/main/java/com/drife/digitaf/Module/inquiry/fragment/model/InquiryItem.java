package com.drife.digitaf.Module.inquiry.fragment.model;

import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class InquiryItem implements Serializable {
    @SerializedName("customer_name")
    public String customer_name = "";
    @SerializedName("id")
    public int id = 0;
    @SerializedName("status")
    public String status = "";
    @SerializedName("status_confins")
    public String status_confins = "";
    @SerializedName("created_at")
    public String created_at = "";
    @SerializedName("form_value")
    public FormValue form_value = new FormValue();
    /*@SerializedName("form_value")
    public InquiryParam inquiryParam = new InquiryParam();*/

    public String documentInfo = "";
    public String due = "";

    //Update 6 november 2018
    @SerializedName("user_id")
    public String user_id = "";
    @SerializedName("created_by")
    public String created_by = "";
    @SerializedName("modified_by")
    public String modified_by = "";
    @SerializedName("is_active")
    public String is_active = "";
    @SerializedName("is_active_confins")
    public String is_active_confins = "";
    @SerializedName("updated_at")
    public String updated_at = "";
    @SerializedName("deleted_at")
    public String deleted_at = "";
    @SerializedName("lead_id")
    public String lead_id = "";
    @SerializedName("dealer_id")
    public String dealer_id = "";
    @SerializedName("branch_id")
    public String branch_id = "";
    @SerializedName("status_txt")
    public String status_txt = "";
    @SerializedName("is_req_phone")
    public String is_req_phone = "";
    @SerializedName("images")
    public List<ImageItem> images = new ArrayList<>();
    @SerializedName("submit_status")
    public String submit_status = "";
    @SerializedName("confins_response")
    public String confins_response = "";

    public InquiryItem() {
    }

    public InquiryItem(String customer_name, String status, String documentInfo, String created_at, String due, int id) {
        this.customer_name = customer_name;
        this.status = status;
        this.documentInfo = documentInfo;
        this.created_at = created_at;
        this.due = due;
        this.id = id;
    }

    public String getIs_req_phone() {
        return is_req_phone;
    }

    public void setIs_req_phone(String is_req_phone) {
        this.is_req_phone = is_req_phone;
    }

    public String getStatus_txt() {
        return status_txt;
    }

    public void setStatus_txt(String status_txt) {
        this.status_txt = status_txt;
    }

    public String getName() {
        return customer_name;
    }

    public void setName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusConfins() {
        return status_confins;
    }

    public void setStatusConfins(String status_confins) {
        this.status_confins = status_confins;
    }

    public String getDocumentInfo() {
        return documentInfo;
    }

    public void setDocumentInfo(String documentInfo) {
        this.documentInfo = documentInfo;
    }

    public String getSent() {
        return created_at;
    }

    public void setSent(String created_at) {
        this.created_at = created_at;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public int getIdInquiry() {
        return id;
    }

    public void setIdInquiry(int id) {
        this.id = id;
    }

    public FormValue getFormValue() {
        return form_value;
    }

    public void setFormValue(FormValue form_value) {
        this.form_value = form_value;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getCreatedBy() {
        return created_by;
    }

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    public String getModifiedBy() {
        return modified_by;
    }

    public void setModifiedBy(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getIsActive() {
        return is_active;
    }

    public void setIsActive(String is_active) {
        this.is_active = is_active;
    }

    public String getIsActiveConfins() {
        return is_active_confins;
    }

    public void setIsActiveConfins(String is_active_confins) {
        this.is_active_confins = is_active_confins;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getLeadId() {
        return lead_id;
    }

    public void setLeadId(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getDealerId() {
        return dealer_id;
    }

    public void setDealerId(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getBranchId() {
        return branch_id;
    }

    public void setBranchId(String branch_id) {
        this.branch_id = branch_id;
    }

    public List<ImageItem> getImages() {
        return images;
    }

    public void setImages(List<ImageItem> images) {
        this.images = images;
    }

    public String getSubmit_status() {
        return submit_status;
    }

    public void setSubmit_status(String submit_status) {
        this.submit_status = submit_status;
    }

    public String getConfins_response() {
        return confins_response;
    }

    public void setConfins_response(String confins_response) {
        this.confins_response = confins_response;
    }
}