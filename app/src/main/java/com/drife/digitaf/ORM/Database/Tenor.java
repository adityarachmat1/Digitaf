package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Tenor extends SugarRecord implements Serializable {
    private String IdTenor;
    private String Code;
    private String CreatedBy;
    private String ModifiedBy;
    private String IsActive;
    private String IsActiveConfins;
    private String CreatedAt;
    private String UpdatedAt;
    private String DeletedAt;

    public Tenor() {
    }

    public Tenor(String idTenor, String code, String createdBy, String modifiedBy, String isActive, String isActiveConfins, String createdAt, String updatedAt, String deletedAt) {
        IdTenor = idTenor;
        Code = code;
        CreatedBy = createdBy;
        ModifiedBy = modifiedBy;
        IsActive = isActive;
        IsActiveConfins = isActiveConfins;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        DeletedAt = deletedAt;
    }

    public String getIdTenor() {
        return IdTenor;
    }

    public void setIdTenor(String idTenor) {
        IdTenor = idTenor;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
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
}
