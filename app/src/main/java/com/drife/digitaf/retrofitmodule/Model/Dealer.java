package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class Dealer {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String code;
    @SerializedName("description")
    private String description;
    @SerializedName("dealer_group_id")
    private String dealer_group_id;
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
    @SerializedName("group")
    private Group group;
    @SerializedName("branch_id")
    private String branch_id;
    @SerializedName("asuransi_kendaraan_id")
    private String asuransi_kendaraan_id;
    @SerializedName("life_insurance_company_id")
    private String life_insurance_company_id;
    @SerializedName("branch")
    private Branch branch;
    @SerializedName("pv_insco")
    private PVInsco pv_insco;
    @SerializedName("pcl_insco")
    private PCLInsco pcl_insco;
    @SerializedName("brand")
    private Brand brand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDealer_group_id() {
        return dealer_group_id;
    }

    public void setDealer_group_id(String dealer_group_id) {
        this.dealer_group_id = dealer_group_id;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getAsuransi_kendaraan_id() {
        return asuransi_kendaraan_id;
    }

    public void setAsuransi_kendaraan_id(String asuransi_kendaraan_id) {
        this.asuransi_kendaraan_id = asuransi_kendaraan_id;
    }

    public String getLife_insurance_company_id() {
        return life_insurance_company_id;
    }

    public void setLife_insurance_company_id(String life_insurance_company_id) {
        this.life_insurance_company_id = life_insurance_company_id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public PVInsco getPv_insco() {
        return pv_insco;
    }

    public void setPv_insco(PVInsco pv_insco) {
        this.pv_insco = pv_insco;
    }

    public PCLInsco getPcl_insco() {
        return pcl_insco;
    }

    public void setPcl_insco(PCLInsco pcl_insco) {
        this.pcl_insco = pcl_insco;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
