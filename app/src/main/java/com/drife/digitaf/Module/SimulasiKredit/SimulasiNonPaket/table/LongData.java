package com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;

import java.io.Serializable;
import java.util.List;

public class LongData implements Serializable{
    private List<Long> TDPADDBLong;
    private List<Long> InstallmentADDBLong;
    private List<Long> TDPADDMLong;
    private List<Long> InstallmentADDMLong;
    private List<Double> BungaADDBLong;
    private List<Double> BungaADDMLong;

    public LongData() {
    }

    public LongData(List<Long> TDPADDBLong, List<Long> installmentADDBLong, List<Long> TDPADDMLong, List<Long> installmentADDMLong, List<Double> bungaADDBLong, List<Double> bungaADDMLong) {
        this.TDPADDBLong = TDPADDBLong;
        InstallmentADDBLong = installmentADDBLong;
        this.TDPADDMLong = TDPADDMLong;
        InstallmentADDMLong = installmentADDMLong;
        BungaADDBLong = bungaADDBLong;
        BungaADDMLong = bungaADDMLong;
    }

    public List<Long> getTDPADDBLong() {
        return TDPADDBLong;
    }

    public void setTDPADDBLong(List<Long> TDPADDBLong) {
        this.TDPADDBLong = TDPADDBLong;
    }

    public List<Long> getInstallmentADDBLong() {
        return InstallmentADDBLong;
    }

    public void setInstallmentADDBLong(List<Long> installmentADDBLong) {
        InstallmentADDBLong = installmentADDBLong;
    }

    public List<Long> getTDPADDMLong() {
        return TDPADDMLong;
    }

    public void setTDPADDMLong(List<Long> TDPADDMLong) {
        this.TDPADDMLong = TDPADDMLong;
    }

    public List<Long> getInstallmentADDMLong() {
        return InstallmentADDMLong;
    }

    public void setInstallmentADDMLong(List<Long> installmentADDMLong) {
        InstallmentADDMLong = installmentADDMLong;
    }

    public List<Double> getBungaADDBLong() {
        return BungaADDBLong;
    }

    public void setBungaADDBLong(List<Double> bungaADDBLong) {
        BungaADDBLong = bungaADDBLong;
    }

    public List<Double> getBungaADDMLong() {
        return BungaADDMLong;
    }

    public void setBungaADDMLong(List<Double> bungaADDMLong) {
        BungaADDMLong = bungaADDMLong;
    }
}
