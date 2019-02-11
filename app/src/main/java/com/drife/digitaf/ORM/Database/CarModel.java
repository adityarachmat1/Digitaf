package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class CarModel extends SugarRecord implements Serializable {
    private String CarModelId;
    private String CarName;
    private String CarDescription;
    private String CarCode;
    private String BrandId;
    private String BrandCode;
    private String Brand;
    private String CategoryId;
    private String CategoryCode;
    private String Category;
    private String CategoryGroupId;
    private String CategoryGroupCode;
    private String CategoryGroup;

    public CarModel() {
    }

    public CarModel(String carModelId, String carName, String carDescription, String carCode, String brandId, String brandCode, String brand, String categoryId, String categoryCode, String category, String categoryGroupId, String categoryGroupCode, String categoryGroup) {
        CarModelId = carModelId;
        CarName = carName;
        CarDescription = carDescription;
        CarCode = carCode;
        BrandId = brandId;
        BrandCode = brandCode;
        Brand = brand;
        CategoryId = categoryId;
        CategoryCode = categoryCode;
        Category = category;
        CategoryGroupId = categoryGroupId;
        CategoryGroupCode = categoryGroupCode;
        CategoryGroup = categoryGroup;
    }

    public String getCarModelId() {
        return CarModelId;
    }

    public void setCarModelId(String carModelId) {
        CarModelId = carModelId;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getCarDescription() {
        return CarDescription;
    }

    public void setCarDescription(String carDescription) {
        CarDescription = carDescription;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
    }

    public String getBrandCode() {
        return BrandCode;
    }

    public void setBrandCode(String brandCode) {
        BrandCode = brandCode;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategoryGroupId() {
        return CategoryGroupId;
    }

    public void setCategoryGroupId(String categoryGroupId) {
        CategoryGroupId = categoryGroupId;
    }

    public String getCategoryGroupCode() {
        return CategoryGroupCode;
    }

    public void setCategoryGroupCode(String categoryGroupCode) {
        CategoryGroupCode = categoryGroupCode;
    }

    public String getCategoryGroup() {
        return CategoryGroup;
    }

    public void setCategoryGroup(String categoryGroup) {
        CategoryGroup = categoryGroup;
    }

    @Override
    public String toString() {
        return CarName;
    }
}
