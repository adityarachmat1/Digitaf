package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class PackageRule extends SugarRecord implements Serializable {
    private String PackageRuleId;
    private String PackageId;
    private String PackageCode;
    private String PackageName;
    private String CategoryGroupId;
    private String CategoryGroupCode;
    private String CategoryGroup;
    private String CarModelId;
    private String CarModelCode;
    private String CarModel;
    private String RateAreaId;
    private String RateAreaCode;
    private String RateArea;
    private String InstallmentTypeId;
    private String InstallmentTypeCode;
    private String InstallmentType;
    private String TenorId;
    private String TenorCode;
    private String Tenor;
    private String MinDp;
    private String BaseDp;
    private String MaxDp;
    private String MinRate;
    private String BaseRate;
    private String MaxRate;
    private String AdminFee;
    private String Provision; //0|1; 1==yes
    private String MinProvision;
    private String BaseProvision;
    private String MaxProvision;
    private String ProvisionPaymentId;
    private String ProvisionPaymentCode;
    private String ProvisionPayment;
    private String TAVPCoverageId;
    private String TAVPCoverageCode;
    private String TAVPCoverage;
    private String TAVPPaymentId;
    private String TAVPPaymentCode;
    private String TAVPPayment;
    private String PCL; //0|1; 1==yes
    private String PCLPaymentId;
    private String PCLPaymentCode;
    private String PCLPayment;
    private String PackageImage; //image url
    private String IsNonPackage; //0|1; 1=yes

    public PackageRule() {
    }

    public PackageRule(String packageRuleId, String packageId, String packageCode, String packageName, String categoryGroupId, String categoryGroupCode, String categoryGroup, String carModelId, String carModelCode, String carModel, String rateAreaId, String rateAreaCode, String rateArea, String installmentTypeId, String installmentTypeCode, String installmentType, String tenorId, String tenorCode, String tenor, String minDp, String baseDp, String maxDp, String minRate, String baseRate, String maxRate, String adminFee, String provision, String minProvision, String baseProvision, String maxProvision, String provisionPaymentId, String provisionPaymentCode, String provisionPayment, String TAVPCoverageId, String TAVPCoverageCode, String TAVPCoverage, String TAVPPaymentId, String TAVPPaymentCode, String TAVPPayment, String PCL, String PCLPaymentId, String PCLPaymentCode, String PCLPayment) {
        PackageRuleId = packageRuleId;
        PackageId = packageId;
        PackageCode = packageCode;
        PackageName = packageName;
        CategoryGroupId = categoryGroupId;
        CategoryGroupCode = categoryGroupCode;
        CategoryGroup = categoryGroup;
        CarModelId = carModelId;
        CarModelCode = carModelCode;
        CarModel = carModel;
        RateAreaId = rateAreaId;
        RateAreaCode = rateAreaCode;
        RateArea = rateArea;
        InstallmentTypeId = installmentTypeId;
        InstallmentTypeCode = installmentTypeCode;
        InstallmentType = installmentType;
        TenorId = tenorId;
        TenorCode = tenorCode;
        Tenor = tenor;
        MinDp = minDp;
        BaseDp = baseDp;
        MaxDp = maxDp;
        MinRate = minRate;
        BaseRate = baseRate;
        MaxRate = maxRate;
        AdminFee = adminFee;
        Provision = provision;
        MinProvision = minProvision;
        BaseProvision = baseProvision;
        MaxProvision = maxProvision;
        ProvisionPaymentId = provisionPaymentId;
        ProvisionPaymentCode = provisionPaymentCode;
        ProvisionPayment = provisionPayment;
        this.TAVPCoverageId = TAVPCoverageId;
        this.TAVPCoverageCode = TAVPCoverageCode;
        this.TAVPCoverage = TAVPCoverage;
        this.TAVPPaymentId = TAVPPaymentId;
        this.TAVPPaymentCode = TAVPPaymentCode;
        this.TAVPPayment = TAVPPayment;
        this.PCL = PCL;
        this.PCLPaymentId = PCLPaymentId;
        this.PCLPaymentCode = PCLPaymentCode;
        this.PCLPayment = PCLPayment;
    }

    public PackageRule(String packageRuleId, String packageId, String packageCode, String packageName, String categoryGroupId, String categoryGroupCode, String categoryGroup, String carModelId, String carModelCode, String carModel, String rateAreaId, String rateAreaCode, String rateArea, String installmentTypeId, String installmentTypeCode, String installmentType, String tenorId, String tenorCode, String tenor, String minDp, String baseDp, String maxDp, String minRate, String baseRate, String maxRate, String adminFee, String provision, String minProvision, String baseProvision, String maxProvision, String provisionPaymentId, String provisionPaymentCode, String provisionPayment, String TAVPCoverageId, String TAVPCoverageCode, String TAVPCoverage, String TAVPPaymentId, String TAVPPaymentCode, String TAVPPayment, String PCL, String PCLPaymentId, String PCLPaymentCode, String PCLPayment, String packageImage, String isNonPackage) {
        PackageRuleId = packageRuleId;
        PackageId = packageId;
        PackageCode = packageCode;
        PackageName = packageName;
        CategoryGroupId = categoryGroupId;
        CategoryGroupCode = categoryGroupCode;
        CategoryGroup = categoryGroup;
        CarModelId = carModelId;
        CarModelCode = carModelCode;
        CarModel = carModel;
        RateAreaId = rateAreaId;
        RateAreaCode = rateAreaCode;
        RateArea = rateArea;
        InstallmentTypeId = installmentTypeId;
        InstallmentTypeCode = installmentTypeCode;
        InstallmentType = installmentType;
        TenorId = tenorId;
        TenorCode = tenorCode;
        Tenor = tenor;
        MinDp = minDp;
        BaseDp = baseDp;
        MaxDp = maxDp;
        MinRate = minRate;
        BaseRate = baseRate;
        MaxRate = maxRate;
        AdminFee = adminFee;
        Provision = provision;
        MinProvision = minProvision;
        BaseProvision = baseProvision;
        MaxProvision = maxProvision;
        ProvisionPaymentId = provisionPaymentId;
        ProvisionPaymentCode = provisionPaymentCode;
        ProvisionPayment = provisionPayment;
        this.TAVPCoverageId = TAVPCoverageId;
        this.TAVPCoverageCode = TAVPCoverageCode;
        this.TAVPCoverage = TAVPCoverage;
        this.TAVPPaymentId = TAVPPaymentId;
        this.TAVPPaymentCode = TAVPPaymentCode;
        this.TAVPPayment = TAVPPayment;
        this.PCL = PCL;
        this.PCLPaymentId = PCLPaymentId;
        this.PCLPaymentCode = PCLPaymentCode;
        this.PCLPayment = PCLPayment;
        PackageImage = packageImage;
        IsNonPackage = isNonPackage;
    }

    public String getPackageRuleId() {
        return PackageRuleId;
    }

    public void setPackageRuleId(String packageRuleId) {
        PackageRuleId = packageRuleId;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getCategoryGroup() {
        return CategoryGroup;
    }

    public void setCategoryGroup(String categoryGroup) {
        CategoryGroup = categoryGroup;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String carModel) {
        CarModel = carModel;
    }

    public String getRateArea() {
        return RateArea;
    }

    public void setRateArea(String rateArea) {
        RateArea = rateArea;
    }

    public String getInstallmentTypeId() {
        return InstallmentTypeId;
    }

    public void setInstallmentTypeId(String installmentTypeId) {
        InstallmentTypeId = installmentTypeId;
    }

    public String getInstallmentTypeCode() {
        return InstallmentTypeCode;
    }

    public void setInstallmentTypeCode(String installmentTypeCode) {
        InstallmentTypeCode = installmentTypeCode;
    }

    public String getInstallmentType() {
        return InstallmentType;
    }

    public void setInstallmentType(String installmentType) {
        InstallmentType = installmentType;
    }

    public String getTenor() {
        return Tenor;
    }

    public void setTenor(String tenor) {
        Tenor = tenor;
    }

    public String getMinDp() {
        return MinDp;
    }

    public void setMinDp(String minDp) {
        MinDp = minDp;
    }

    public String getBaseDp() {
        return BaseDp;
    }

    public void setBaseDp(String baseDp) {
        BaseDp = baseDp;
    }

    public String getMaxDp() {
        return MaxDp;
    }

    public void setMaxDp(String maxDp) {
        MaxDp = maxDp;
    }

    public String getMinRate() {
        return MinRate;
    }

    public void setMinRate(String minRate) {
        MinRate = minRate;
    }

    public String getBaseRate() {
        return BaseRate;
    }

    public void setBaseRate(String baseRate) {
        BaseRate = baseRate;
    }

    public String getMaxRate() {
        return MaxRate;
    }

    public void setMaxRate(String maxRate) {
        MaxRate = maxRate;
    }

    public String getAdminFee() {
        return AdminFee;
    }

    public void setAdminFee(String adminFee) {
        AdminFee = adminFee;
    }

    public String getProvision() {
        return Provision;
    }

    public void setProvision(String provision) {
        Provision = provision;
    }

    public String getMinProvision() {
        return MinProvision;
    }

    public void setMinProvision(String minProvision) {
        MinProvision = minProvision;
    }

    public String getBaseProvision() {
        return BaseProvision;
    }

    public void setBaseProvision(String baseProvision) {
        BaseProvision = baseProvision;
    }

    public String getMaxProvision() {
        return MaxProvision;
    }

    public void setMaxProvision(String maxProvision) {
        MaxProvision = maxProvision;
    }

    public String getProvisionPayment() {
        return ProvisionPayment;
    }

    public void setProvisionPayment(String provisionPayment) {
        ProvisionPayment = provisionPayment;
    }

    public String getTAVPCoverage() {
        return TAVPCoverage;
    }

    public void setTAVPCoverage(String TAVPCoverage) {
        this.TAVPCoverage = TAVPCoverage;
    }

    public String getTAVPPayment() {
        return TAVPPayment;
    }

    public void setTAVPPayment(String TAVPPayment) {
        this.TAVPPayment = TAVPPayment;
    }

    public String getPCL() {
        return PCL;
    }

    public void setPCL(String PCL) {
        this.PCL = PCL;
    }

    public String getPCLPayment() {
        return PCLPayment;
    }

    public void setPCLPayment(String PCLPayment) {
        this.PCLPayment = PCLPayment;
    }

    public String getPackageId() {
        return PackageId;
    }

    public void setPackageId(String packageId) {
        PackageId = packageId;
    }

    public String getPackageCode() {
        return PackageCode;
    }

    public void setPackageCode(String packageCode) {
        PackageCode = packageCode;
    }

    public String getCategoryGroupId() {
        return CategoryGroupId;
    }

    public void setCategoryGroupId(String categoryGroupId) {
        CategoryGroupId = categoryGroupId;
    }

    public String getCarModelId() {
        return CarModelId;
    }

    public void setCarModelId(String carModelId) {
        CarModelId = carModelId;
    }

    public String getCarModelCode() {
        return CarModelCode;
    }

    public void setCarModelCode(String carModelCode) {
        CarModelCode = carModelCode;
    }

    public String getRateAreaId() {
        return RateAreaId;
    }

    public void setRateAreaId(String rateAreaId) {
        RateAreaId = rateAreaId;
    }

    public String getRateAreaCode() {
        return RateAreaCode;
    }

    public void setRateAreaCode(String rateAreaCode) {
        RateAreaCode = rateAreaCode;
    }

    public String getTenorId() {
        return TenorId;
    }

    public void setTenorId(String tenorId) {
        TenorId = tenorId;
    }

    public String getTenorCode() {
        return TenorCode;
    }

    public void setTenorCode(String tenorCode) {
        TenorCode = tenorCode;
    }

    public String getTAVPCoverageId() {
        return TAVPCoverageId;
    }

    public void setTAVPCoverageId(String TAVPCoverageId) {
        this.TAVPCoverageId = TAVPCoverageId;
    }

    public String getTAVPCoverageCode() {
        return TAVPCoverageCode;
    }

    public void setTAVPCoverageCode(String TAVPCoverageCode) {
        this.TAVPCoverageCode = TAVPCoverageCode;
    }

    public String getTAVPPaymentId() {
        return TAVPPaymentId;
    }

    public void setTAVPPaymentId(String TAVPPaymentId) {
        this.TAVPPaymentId = TAVPPaymentId;
    }

    public String getTAVPPaymentCode() {
        return TAVPPaymentCode;
    }

    public void setTAVPPaymentCode(String TAVPPaymentCode) {
        this.TAVPPaymentCode = TAVPPaymentCode;
    }

    public String getPCLPaymentId() {
        return PCLPaymentId;
    }

    public void setPCLPaymentId(String PCLPaymentId) {
        this.PCLPaymentId = PCLPaymentId;
    }

    public String getPCLPaymentCode() {
        return PCLPaymentCode;
    }

    public void setPCLPaymentCode(String PCLPaymentCode) {
        this.PCLPaymentCode = PCLPaymentCode;
    }

    public String getCategoryGroupCode() {
        return CategoryGroupCode;
    }

    public void setCategoryGroupCode(String categoryGroupCode) {
        CategoryGroupCode = categoryGroupCode;
    }

    public String getProvisionPaymentId() {
        return ProvisionPaymentId;
    }

    public void setProvisionPaymentId(String provisionPaymentId) {
        ProvisionPaymentId = provisionPaymentId;
    }

    public String getProvisionPaymentCode() {
        return ProvisionPaymentCode;
    }

    public void setProvisionPaymentCode(String provisionPaymentCode) {
        ProvisionPaymentCode = provisionPaymentCode;
    }

    public String getPackageImage() {
        return PackageImage;
    }

    public void setPackageImage(String packageImage) {
        PackageImage = packageImage;
    }

    public String getIsNonPackage() {
        return IsNonPackage;
    }

    public void setIsNonPackage(String isNonPackage) {
        IsNonPackage = isNonPackage;
    }
}
