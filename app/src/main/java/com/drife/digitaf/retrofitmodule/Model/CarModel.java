package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarModel {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("code")
    private String code;
    @SerializedName("car_brand_id")
    private String car_brand_id;
    @SerializedName("car_category_id")
    private String car_category_id;
    @SerializedName("car_category_group_id")
    private String car_category_group_id;
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
    @SerializedName("brand")
    private Brand brand;
    @SerializedName("category_group")
    private CategoryGroup category_group;
    @SerializedName("category")
    private Category category;
    @SerializedName("car_type")
    private List<CarType> car_type;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCar_brand_id() {
        return car_brand_id;
    }

    public void setCar_brand_id(String car_brand_id) {
        this.car_brand_id = car_brand_id;
    }

    public String getCar_category_id() {
        return car_category_id;
    }

    public void setCar_category_id(String car_category_id) {
        this.car_category_id = car_category_id;
    }

    public String getCar_category_group_id() {
        return car_category_group_id;
    }

    public void setCar_category_group_id(String car_category_group_id) {
        this.car_category_group_id = car_category_group_id;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public CategoryGroup getCategory_group() {
        return category_group;
    }

    public void setCategory_group(CategoryGroup category_group) {
        this.category_group = category_group;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CarType> getCar_type() {
        return car_type;
    }

    public void setCar_type(List<CarType> car_type) {
        this.car_type = car_type;
    }
}
