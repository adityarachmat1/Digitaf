package com.drife.digitaf.ORM.Database;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Religion extends SugarRecord implements Serializable{
    private String WayOfPaymentId;
    private String Code;
    private String Name;
    private String Description;
    private String CreatedBy;
    private String ModifiedBy;
    private String IsActive;
    private String IsActiveConfins;
    private String CreatedAt;
    private String UpdatedAt;
    private String DeletedAt;

    public Religion() {
    }

    public Religion(String name) {
        Name = name;
    }

    public Religion(String wayOfPaymentId, String code, String name, String description, String createdBy, String modifiedBy, String isActive, String isActiveConfins, String createdAt, String updatedAt, String deletedAt) {
        WayOfPaymentId = wayOfPaymentId;
        Code = code;
        Name = name;
        Description = description;
        CreatedBy = createdBy;
        ModifiedBy = modifiedBy;
        IsActive = isActive;
        IsActiveConfins = isActiveConfins;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        DeletedAt = deletedAt;
    }

    public String getWayOfPaymentId() {
        return WayOfPaymentId;
    }

    public void setWayOfPaymentId(String wayOfPaymentId) {
        WayOfPaymentId = wayOfPaymentId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getIsActiveConfins() {
        return IsActiveConfins;
    }

    public void setIsActiveConfins(String isActiveConfins) {
        IsActiveConfins = isActiveConfins;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        DeletedAt = deletedAt;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
