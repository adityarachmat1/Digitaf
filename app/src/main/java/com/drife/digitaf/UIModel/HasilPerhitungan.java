package com.drife.digitaf.UIModel;

import java.io.Serializable;

public class HasilPerhitungan implements Serializable{
    private String ModelMobil;
    private String Otr;
    private String Paket;
    private String Tenor;
    private String Rate;
    private String Angsuran;
    private String DP;
    private String TDP;
    private String TotalHutang;
    private String PVPremiAmount;
    private String PCLPremiAmount;
    private String AdminFee;
    private String FirstInstallment;
    private String Type;

    public HasilPerhitungan() {
    }

    public HasilPerhitungan(String modelMobil, String otr, String paket, String tenor, String rate, String angsuran, String DP, String TDP) {
        ModelMobil = modelMobil;
        Otr = otr;
        Paket = paket;
        Tenor = tenor;
        Rate = rate;
        Angsuran = angsuran;
        this.DP = DP;
        this.TDP = TDP;
    }

    public HasilPerhitungan(String modelMobil, String otr, String paket, String tenor, String rate, String angsuran, String DP, String TDP, String totalHutang, String PVPremiAmount, String PCLPremiAmount, String adminFee) {
        ModelMobil = modelMobil;
        Otr = otr;
        Paket = paket;
        Tenor = tenor;
        Rate = rate;
        Angsuran = angsuran;
        this.DP = DP;
        this.TDP = TDP;
        TotalHutang = totalHutang;
        this.PVPremiAmount = PVPremiAmount;
        this.PCLPremiAmount = PCLPremiAmount;
        AdminFee = adminFee;
    }

    public HasilPerhitungan(String modelMobil, String otr, String paket, String tenor, String rate, String angsuran, String DP, String TDP, String totalHutang, String PVPremiAmount, String PCLPremiAmount, String adminFee, String firstInstallment, String type) {
        ModelMobil = modelMobil;
        Otr = otr;
        Paket = paket;
        Tenor = tenor;
        Rate = rate;
        Angsuran = angsuran;
        this.DP = DP;
        this.TDP = TDP;
        TotalHutang = totalHutang;
        this.PVPremiAmount = PVPremiAmount;
        this.PCLPremiAmount = PCLPremiAmount;
        AdminFee = adminFee;
        FirstInstallment = firstInstallment;
        Type = type;
    }

    public String getModelMobil() {
        return ModelMobil;
    }

    public void setModelMobil(String modelMobil) {
        ModelMobil = modelMobil;
    }

    public String getOtr() {
        return Otr;
    }

    public void setOtr(String otr) {
        Otr = otr;
    }

    public String getPaket() {
        return Paket;
    }

    public void setPaket(String paket) {
        Paket = paket;
    }

    public String getTenor() {
        return Tenor;
    }

    public void setTenor(String tenor) {
        Tenor = tenor;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getAngsuran() {
        return Angsuran;
    }

    public void setAngsuran(String angsuran) {
        Angsuran = angsuran;
    }

    public String getDP() {
        return DP;
    }

    public void setDP(String DP) {
        this.DP = DP;
    }

    public String getTDP() {
        return TDP;
    }

    public void setTDP(String TDP) {
        this.TDP = TDP;
    }

    public String getTotalHutang() {
        return TotalHutang;
    }

    public void setTotalHutang(String totalHutang) {
        TotalHutang = totalHutang;
    }

    public String getPVPremiAmount() {
        return PVPremiAmount;
    }

    public void setPVPremiAmount(String PVPremiAmount) {
        this.PVPremiAmount = PVPremiAmount;
    }

    public String getPCLPremiAmount() {
        return PCLPremiAmount;
    }

    public void setPCLPremiAmount(String PCLPremiAmount) {
        this.PCLPremiAmount = PCLPremiAmount;
    }

    public String getAdminFee() {
        return AdminFee;
    }

    public void setAdminFee(String adminFee) {
        AdminFee = adminFee;
    }

    public String getFirstInstallment() {
        return FirstInstallment;
    }

    public void setFirstInstallment(String firstInstallment) {
        FirstInstallment = firstInstallment;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
