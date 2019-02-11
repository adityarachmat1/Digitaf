package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class PVPremi {
    @SerializedName("id")
    private String id;
    @SerializedName("area_asuransi_id")
    private String area_asuransi_id;
    @SerializedName("pv_insco_id")
    private String pv_insco_id;
    @SerializedName("coverage_type_id")
    private String coverage_type_id;
    @SerializedName("coverage_min")
    private String coverage_min;
    @SerializedName("coverage_max")
    private String coverage_max;
    @SerializedName("rate")
    private String rate;
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
    @SerializedName("coverage_type")
    private CoverageType coverage_type;
    @SerializedName("area")
    private Area area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea_asuransi_id() {
        return area_asuransi_id;
    }

    public void setArea_asuransi_id(String area_asuransi_id) {
        this.area_asuransi_id = area_asuransi_id;
    }

    public String getPv_insco_id() {
        return pv_insco_id;
    }

    public void setPv_insco_id(String pv_insco_id) {
        this.pv_insco_id = pv_insco_id;
    }

    public String getCoverage_type_id() {
        return coverage_type_id;
    }

    public void setCoverage_type_id(String coverage_type_id) {
        this.coverage_type_id = coverage_type_id;
    }

    public String getCoverage_min() {
        return coverage_min;
    }

    public void setCoverage_min(String coverage_min) {
        this.coverage_min = coverage_min;
    }

    public String getCoverage_max() {
        return coverage_max;
    }

    public void setCoverage_max(String coverage_max) {
        this.coverage_max = coverage_max;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public CoverageType getCoverage_type() {
        return coverage_type;
    }

    public void setCoverage_type(CoverageType coverage_type) {
        this.coverage_type = coverage_type;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
