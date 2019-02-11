package com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;

import java.io.Serializable;
import java.util.List;

public class TableData extends LongData implements Serializable{
    private List<Long> TDPOnloan;
    private List<Long> CicilanOnloan;
    private List<Long> TDPPrepaid;
    private List<Long> CicilanPrepaid;
    private List<Tenor> tenors;
    private List<Double> bunga;
    private List<Double> bungaADDM;
    private String selectedPackage;
    private String selectedPackageCode;
    private List<Long> dpMurniADDB;
    private List<Long> dpMurniADDM;
    private List<Double> dpMurniPercentageADDB;
    private List<Double> dpMurniPercentageADDM;

    public TableData() {
    }

    public TableData(List<Long> TDPOnloan, List<Long> cicilanOnload, List<Long> TDPPrepaid, List<Long> cicilanPrepaid) {
        this.TDPOnloan = TDPOnloan;
        CicilanOnloan = cicilanOnload;
        this.TDPPrepaid = TDPPrepaid;
        CicilanPrepaid = cicilanPrepaid;
    }

    public TableData(List<Long> TDPOnloan, List<Long> cicilanOnload, List<Long> TDPPrepaid, List<Long> cicilanPrepaid, List<Tenor> tenors) {
        this.TDPOnloan = TDPOnloan;
        CicilanOnloan = cicilanOnload;
        this.TDPPrepaid = TDPPrepaid;
        CicilanPrepaid = cicilanPrepaid;
        this.tenors = tenors;
    }

    public List<Long> getTDPOnloan() {
        return TDPOnloan;
    }

    public void setTDPOnloan(List<Long> TDPOnloan) {
        this.TDPOnloan = TDPOnloan;
    }

    public List<Long> getCicilanOnloan() {
        return CicilanOnloan;
    }

    public void setCicilanOnloan(List<Long> cicilanOnloan) {
        CicilanOnloan = cicilanOnloan;
    }

    public List<Long> getTDPPrepaid() {
        return TDPPrepaid;
    }

    public void setTDPPrepaid(List<Long> TDPPrepaid) {
        this.TDPPrepaid = TDPPrepaid;
    }

    public List<Long> getCicilanPrepaid() {
        return CicilanPrepaid;
    }

    public void setCicilanPrepaid(List<Long> cicilanPrepaid) {
        CicilanPrepaid = cicilanPrepaid;
    }

    public List<Tenor> getTenors() {
        return tenors;
    }

    public void setTenors(List<Tenor> tenors) {
        this.tenors = tenors;
    }

    public List<Double> getBunga() {
        return bunga;
    }

    public void setBunga(List<Double> bunga) {
        this.bunga = bunga;
    }

    public List<Double> getBungaADDM() {
        return bungaADDM;
    }

    public void setBungaADDM(List<Double> bungaADDM) {
        this.bungaADDM = bungaADDM;
    }

    public String getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(String selectedPackage) {
        this.selectedPackage = selectedPackage;
    }

    public String getSelectedPackageCode() {
        return selectedPackageCode;
    }

    public void setSelectedPackageCode(String selectedPackageCode) {
        this.selectedPackageCode = selectedPackageCode;
    }

    public List<Long> getDpMurniADDB() {
        return dpMurniADDB;
    }

    public void setDpMurniADDB(List<Long> dpMurniADDB) {
        this.dpMurniADDB = dpMurniADDB;
    }

    public List<Long> getDpMurniADDM() {
        return dpMurniADDM;
    }

    public void setDpMurniADDM(List<Long> dpMurniADDM) {
        this.dpMurniADDM = dpMurniADDM;
    }

    public List<Double> getDpMurniPercentageADDB() {
        return dpMurniPercentageADDB;
    }

    public void setDpMurniPercentageADDB(List<Double> dpMurniPercentageADDB) {
        this.dpMurniPercentageADDB = dpMurniPercentageADDB;
    }

    public List<Double> getDpMurniPercentageADDM() {
        return dpMurniPercentageADDM;
    }

    public void setDpMurniPercentageADDM(List<Double> dpMurniPercentageADDM) {
        this.dpMurniPercentageADDM = dpMurniPercentageADDM;
    }
}
