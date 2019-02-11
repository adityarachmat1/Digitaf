package com.drife.digitaf.Module.myapplication.model;

import com.drife.digitaf.Module.inquiry.fragment.model.ImageItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class DraftItem implements Serializable {
    @SerializedName("id")
    public int id = 0;
    @SerializedName("created_at")
    public String created_at = "";
    @SerializedName("form_value")
    public FormValue form_value = new FormValue();

    //Update 6 november 2018
    @SerializedName("user_id")
    public String user_id = "";
    @SerializedName("status")
    public String status = "";
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
    @SerializedName("images")
    public List<ImageItem> images = new ArrayList<>();

    @SerializedName("is_corporate")
    public String is_corporate = "";
    @SerializedName("is_simulasi_paket")
    public String is_simulasi_paket = "";
    @SerializedName("is_simulasi_budget")
    public String is_simulasi_budget = "";
    @SerializedName("is_non_paket")
    public String is_non_paket = "";
    @SerializedName("is_npwp")
    public String is_npwp ;
    @SerializedName("payment_type_service_package")
    public String payment_type_service_package = "";
    @SerializedName("coverage_type")
    public String coverage_type = "";

    public String getIs_corporate() {
        return is_corporate;
    }

    public void setIs_corporate(String is_corporate) {
        this.is_corporate = is_corporate;
    }

    public String getIs_simulasi_paket() {
        return is_simulasi_paket;
    }

    public void setIs_simulasi_paket(String is_simulasi_paket) {
        this.is_simulasi_paket = is_simulasi_paket;
    }

    public String getIs_simulasi_budget() {
        return is_simulasi_budget;
    }

    public void setIs_simulasi_budget(String is_simulasi_budget) {
        this.is_simulasi_budget = is_simulasi_budget;
    }

    public String getIs_non_paket() {
        return is_non_paket;
    }

    public void setIs_non_paket(String is_non_paket) {
        this.is_non_paket = is_non_paket;
    }

    public String getIs_npwp() {
        return is_npwp;
    }

    public void setIs_npwp(String is_npwp) {
        this.is_npwp = is_npwp;
    }

    public String getPayment_type_service_package() {
        return payment_type_service_package;
    }

    public void setPayment_type_service_package(String payment_type_service_package) {
        this.payment_type_service_package = payment_type_service_package;
    }

    public String getCoverage_type() {
        return coverage_type;
    }

    public void setCoverage_type(String coverage_type) {
        this.coverage_type = coverage_type;
    }

    public String getLastSaved() {
        return created_at;
    }

    public void setLastSaved(String lastSaved) {
        this.created_at = created_at;
    }

    public int getIdDraft() {
        return id;
    }

    public void setIdDraft(int idDraft) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
