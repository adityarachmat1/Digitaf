package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class Package {
    @SerializedName("id")
    private String id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
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
    @SerializedName("image")
    private String image;
    @SerializedName("is_non_package")
    private String is_non_package;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIs_non_package() {
        return is_non_package;
    }

    public void setIs_non_package(String is_non_package) {
        this.is_non_package = is_non_package;
    }
}
