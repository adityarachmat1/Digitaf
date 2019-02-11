package com.drife.digitaf.ORM.Database;

import android.support.annotation.NonNull;

import com.drife.digitaf.retrofitmodule.Model.PVPremi;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

public class Dealer extends SugarRecord implements Serializable {
    private String DealerId;
    private String DealerName;
    private String DealerCode;
    private String Description;
    private String DealerGroupId;
    private String DealerGroupName;
    private String DealerGroupCode;
    private String BranchId;
    private String BranchName;
    private String BranchCode;
    private String RateAreaId;
    private String RateAreaName;
    private String RateAreaCode;
    private String InsuranceAreaId;
    private String InsuranceAreaName;
    private String InsuranceAreaCode;
    private String PVInscoId;
    private String PVInscoName;
    private String PVInscoCode;
    private String PCLInscoId;
    private String PCLInscoName;
    private String PCLInscoCode;
    private String PCLInscoDescription;
    private String PCLPremi;
    private String BrandId;
    private String BrandCode;
    private String BrandName;

    public Dealer() {
    }

    public Dealer(String dealerId, String dealerName, String dealerCode, String description, String dealerGroupId, String dealerGroupName, String dealerGroupCode, String branchId, String branchName, String branchCode, String rateAreaId, String rateAreaName, String rateAreaCode, String insuranceAreaId, String insuranceAreaName, String insuranceAreaCode, String PVInscoId, String PVInscoName, String PVInscoCode, String PCLInscoId, String PCLInscoName, String PCLInscoCode, String PCLInscoDescription, String PCLPremi) {
        DealerId = dealerId;
        DealerName = dealerName;
        DealerCode = dealerCode;
        Description = description;
        DealerGroupId = dealerGroupId;
        DealerGroupName = dealerGroupName;
        DealerGroupCode = dealerGroupCode;
        BranchId = branchId;
        BranchName = branchName;
        BranchCode = branchCode;
        RateAreaId = rateAreaId;
        RateAreaName = rateAreaName;
        RateAreaCode = rateAreaCode;
        InsuranceAreaId = insuranceAreaId;
        InsuranceAreaName = insuranceAreaName;
        InsuranceAreaCode = insuranceAreaCode;
        this.PVInscoId = PVInscoId;
        this.PVInscoName = PVInscoName;
        this.PVInscoCode = PVInscoCode;
        this.PCLInscoId = PCLInscoId;
        this.PCLInscoName = PCLInscoName;
        this.PCLInscoCode = PCLInscoCode;
        this.PCLInscoDescription = PCLInscoDescription;
        this.PCLPremi = PCLPremi;
    }

    public Dealer(String dealerId, String dealerName, String dealerCode, String description, String dealerGroupId, String dealerGroupName, String dealerGroupCode, String branchId, String branchName, String branchCode, String rateAreaId, String rateAreaName, String rateAreaCode, String insuranceAreaId, String insuranceAreaName, String insuranceAreaCode, String PVInscoId, String PVInscoName, String PVInscoCode, String PCLInscoId, String PCLInscoName, String PCLInscoCode, String PCLInscoDescription, String PCLPremi, String brandId, String brandCode, String brandName) {
        DealerId = dealerId;
        DealerName = dealerName;
        DealerCode = dealerCode;
        Description = description;
        DealerGroupId = dealerGroupId;
        DealerGroupName = dealerGroupName;
        DealerGroupCode = dealerGroupCode;
        BranchId = branchId;
        BranchName = branchName;
        BranchCode = branchCode;
        RateAreaId = rateAreaId;
        RateAreaName = rateAreaName;
        RateAreaCode = rateAreaCode;
        InsuranceAreaId = insuranceAreaId;
        InsuranceAreaName = insuranceAreaName;
        InsuranceAreaCode = insuranceAreaCode;
        this.PVInscoId = PVInscoId;
        this.PVInscoName = PVInscoName;
        this.PVInscoCode = PVInscoCode;
        this.PCLInscoId = PCLInscoId;
        this.PCLInscoName = PCLInscoName;
        this.PCLInscoCode = PCLInscoCode;
        this.PCLInscoDescription = PCLInscoDescription;
        this.PCLPremi = PCLPremi;
        BrandId = brandId;
        BrandCode = brandCode;
        BrandName = brandName;
    }

    public String getDealerId() {
        return DealerId;
    }

    public void setDealerId(String dealerId) {
        DealerId = dealerId;
    }

    public String getDealerName() {
        return DealerName;
    }

    public void setDealerName(String dealerName) {
        DealerName = dealerName;
    }

    public String getDealerCode() {
        return DealerCode;
    }

    public void setDealerCode(String dealerCode) {
        DealerCode = dealerCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDealerGroupId() {
        return DealerGroupId;
    }

    public void setDealerGroupId(String dealerGroupId) {
        DealerGroupId = dealerGroupId;
    }

    public String getDealerGroupName() {
        return DealerGroupName;
    }

    public void setDealerGroupName(String dealerGroupName) {
        DealerGroupName = dealerGroupName;
    }

    public String getDealerGroupCode() {
        return DealerGroupCode;
    }

    public void setDealerGroupCode(String dealerGroupCode) {
        DealerGroupCode = dealerGroupCode;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getRateAreaId() {
        return RateAreaId;
    }

    public void setRateAreaId(String rateAreaId) {
        RateAreaId = rateAreaId;
    }

    public String getRateAreaName() {
        return RateAreaName;
    }

    public void setRateAreaName(String rateAreaName) {
        RateAreaName = rateAreaName;
    }

    public String getRateAreaCode() {
        return RateAreaCode;
    }

    public void setRateAreaCode(String rateAreaCode) {
        RateAreaCode = rateAreaCode;
    }

    public String getInsuranceAreaId() {
        return InsuranceAreaId;
    }

    public void setInsuranceAreaId(String insuranceAreaId) {
        InsuranceAreaId = insuranceAreaId;
    }

    public String getInsuranceAreaName() {
        return InsuranceAreaName;
    }

    public void setInsuranceAreaName(String insuranceAreaName) {
        InsuranceAreaName = insuranceAreaName;
    }

    public String getInsuranceAreaCode() {
        return InsuranceAreaCode;
    }

    public void setInsuranceAreaCode(String insuranceAreaCode) {
        InsuranceAreaCode = insuranceAreaCode;
    }

    public String getPCLInscoId() {
        return PCLInscoId;
    }

    public void setPCLInscoId(String PCLInscoId) {
        this.PCLInscoId = PCLInscoId;
    }

    public String getPCLInscoName() {
        return PCLInscoName;
    }

    public void setPCLInscoName(String PCLInscoName) {
        this.PCLInscoName = PCLInscoName;
    }

    public String getPCLInscoCode() {
        return PCLInscoCode;
    }

    public void setPCLInscoCode(String PCLInscoCode) {
        this.PCLInscoCode = PCLInscoCode;
    }

    public String getPCLInscoDescription() {
        return PCLInscoDescription;
    }

    public void setPCLInscoDescription(String PCLInscoDescription) {
        this.PCLInscoDescription = PCLInscoDescription;
    }

    public String getPCLPremi() {
        return PCLPremi;
    }

    public void setPCLPremi(String PCLPremi) {
        this.PCLPremi = PCLPremi;
    }

    public String getPVInscoId() {
        return PVInscoId;
    }

    public void setPVInscoId(String PVInscoId) {
        this.PVInscoId = PVInscoId;
    }

    public String getPVInscoName() {
        return PVInscoName;
    }

    public void setPVInscoName(String PVInscoName) {
        this.PVInscoName = PVInscoName;
    }

    public String getPVInscoCode() {
        return PVInscoCode;
    }

    public void setPVInscoCode(String PVInscoCode) {
        this.PVInscoCode = PVInscoCode;
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

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    @NonNull
    @Override
    public String toString() {
        return getDealerName();
    }
}
