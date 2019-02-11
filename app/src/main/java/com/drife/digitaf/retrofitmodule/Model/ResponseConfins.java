package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseConfins {
    @SerializedName("EmployeeNo")
    private String EmployeeNo;
    @SerializedName("BranchName")
    private String BranchName;
    @SerializedName("BranchCode")
    private String BranchCode;
    @SerializedName("ReportTo")
    private String ReportTo;
    @SerializedName("ReportToIdNo")
    private String ReportToIdNo;
    @SerializedName("LogErrorMsg")
    private String LogErrorMsg;
    @SerializedName("ErrorCode")
    private String ErrorCode;

    @SerializedName("KtpNo")
    private String KtpNo;
    @SerializedName("DealerName")
    private String DealerName;
    @SerializedName("DealerCode")
    private String DealerCode;
    @SerializedName("DealerGroupCode")
    private String DealerGroupCode;
    @SerializedName("PositionAtDealer")
    private String PositionAtDealer;

    public ResponseConfins() {
    }

    public String getEmployeeNo() {
        return EmployeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        EmployeeNo = employeeNo;
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

    public String getReportTo() {
        return ReportTo;
    }

    public void setReportTo(String reportTo) {
        ReportTo = reportTo;
    }

    public String getReportToIdNo() {
        return ReportToIdNo;
    }

    public void setReportToIdNo(String reportToIdNo) {
        ReportToIdNo = reportToIdNo;
    }

    public String getKtpNo() {
        return KtpNo;
    }

    public void setKtpNo(String ktpNo) {
        KtpNo = ktpNo;
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

    public String getDealerGroupCode() {
        return DealerGroupCode;
    }

    public void setDealerGroupCode(String dealerGroupCode) {
        DealerGroupCode = dealerGroupCode;
    }

    public String getPositionAtDealer() {
        return PositionAtDealer;
    }

    public void setPositionAtDealer(String positionAtDealer) {
        PositionAtDealer = positionAtDealer;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getLogErrorMsg() {
        return LogErrorMsg;
    }

    public void setLogErrorMsg(String logErrorMsg) {
        LogErrorMsg = logErrorMsg;
    }
}
