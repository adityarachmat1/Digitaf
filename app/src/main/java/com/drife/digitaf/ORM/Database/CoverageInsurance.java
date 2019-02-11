package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class CoverageInsurance extends SugarRecord implements Serializable {
    private String PVInscoId;
    private String PVInscoCode;
    private String PVInsco;
    private String InsuranceAreaId;
    private String InsuranceAreaCode;
    private String InsuranceArea;
    private String MinTSI;
    private String MaxTSI;
    private String CoverageTypeId;
    private String CoverageTypeCode;
    private String CoverageType;
    private String Rate;
    private String DealerId;
    private String DealerCode;
    private String Dealer;

    public CoverageInsurance() {
    }

    /*public CoverageInsurance(String PVInsco, String insuranceArea, String minTSI, String maxTSI, String coverageType, String rate) {
        this.PVInsco = PVInsco;
        InsuranceArea = insuranceArea;
        MinTSI = minTSI;
        MaxTSI = maxTSI;
        CoverageType = coverageType;
        Rate = rate;
    }*/

    public CoverageInsurance(String PVInscoId, String PVInscoCode, String PVInsco, String insuranceAreaId, String insuranceAreaCode, String insuranceArea, String minTSI, String maxTSI, String coverageTypeId, String coverageTypeCode, String coverageType, String rate) {
        this.PVInscoId = PVInscoId;
        this.PVInscoCode = PVInscoCode;
        this.PVInsco = PVInsco;
        InsuranceAreaId = insuranceAreaId;
        InsuranceAreaCode = insuranceAreaCode;
        InsuranceArea = insuranceArea;
        MinTSI = minTSI;
        MaxTSI = maxTSI;
        CoverageTypeId = coverageTypeId;
        CoverageTypeCode = coverageTypeCode;
        CoverageType = coverageType;
        Rate = rate;
    }

    public CoverageInsurance(String PVInscoId, String PVInscoCode, String PVInsco, String insuranceAreaId, String insuranceAreaCode, String insuranceArea, String minTSI, String maxTSI, String coverageTypeId, String coverageTypeCode, String coverageType, String rate, String dealerId, String dealerCode, String dealer) {
        this.PVInscoId = PVInscoId;
        this.PVInscoCode = PVInscoCode;
        this.PVInsco = PVInsco;
        InsuranceAreaId = insuranceAreaId;
        InsuranceAreaCode = insuranceAreaCode;
        InsuranceArea = insuranceArea;
        MinTSI = minTSI;
        MaxTSI = maxTSI;
        CoverageTypeId = coverageTypeId;
        CoverageTypeCode = coverageTypeCode;
        CoverageType = coverageType;
        Rate = rate;
        DealerId = dealerId;
        DealerCode = dealerCode;
        Dealer = dealer;
    }

    public String getPVInsco() {
        return PVInsco;
    }

    public void setPVInsco(String PVInsco) {
        this.PVInsco = PVInsco;
    }

    public String getInsuranceArea() {
        return InsuranceArea;
    }

    public void setInsuranceArea(String insuranceArea) {
        InsuranceArea = insuranceArea;
    }

    public String getMinTSI() {
        return MinTSI;
    }

    public void setMinTSI(String minTSI) {
        MinTSI = minTSI;
    }

    public String getMaxTSI() {
        return MaxTSI;
    }

    public void setMaxTSI(String maxTSI) {
        MaxTSI = maxTSI;
    }

    public String getCoverageType() {
        return CoverageType;
    }

    public void setCoverageType(String coverageType) {
        CoverageType = coverageType;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getPVInscoId() {
        return PVInscoId;
    }

    public void setPVInscoId(String PVInscoId) {
        this.PVInscoId = PVInscoId;
    }

    public String getPVInscoCode() {
        return PVInscoCode;
    }

    public void setPVInscoCode(String PVInscoCode) {
        this.PVInscoCode = PVInscoCode;
    }

    public String getInsuranceAreaId() {
        return InsuranceAreaId;
    }

    public void setInsuranceAreaId(String insuranceAreaId) {
        InsuranceAreaId = insuranceAreaId;
    }

    public String getInsuranceAreaCode() {
        return InsuranceAreaCode;
    }

    public void setInsuranceAreaCode(String insuranceAreaCode) {
        InsuranceAreaCode = insuranceAreaCode;
    }

    public String getCoverageTypeId() {
        return CoverageTypeId;
    }

    public void setCoverageTypeId(String coverageTypeId) {
        CoverageTypeId = coverageTypeId;
    }

    public String getCoverageTypeCode() {
        return CoverageTypeCode;
    }

    public void setCoverageTypeCode(String coverageTypeCode) {
        CoverageTypeCode = coverageTypeCode;
    }

    public String getDealerId() {
        return DealerId;
    }

    public void setDealerId(String dealerId) {
        DealerId = dealerId;
    }

    public String getDealerCode() {
        return DealerCode;
    }

    public void setDealerCode(String dealerCode) {
        DealerCode = dealerCode;
    }

    public String getDealer() {
        return Dealer;
    }

    public void setDealer(String dealer) {
        Dealer = dealer;
    }
}
