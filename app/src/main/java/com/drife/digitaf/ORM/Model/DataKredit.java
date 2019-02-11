package com.drife.digitaf.ORM.Model;

import java.io.Serializable;

public class DataKredit implements Serializable{
    private String ModelMobil;
    private String TipeMobil;
    private String Paket;
    private String HargaOtr;
    private String Rate;
    private String Dp;
    private String Tenor;
    private String JenisAngsuran;
    private String NamaAsuransi;
    private String AsuransiType;
    private String CoverageType;
    private String PCLType;
    private String Diskon;
    private String ProvisionAmount;
    private String ProvisionType;
    private String ServicePackageAmount;
    private String ServicePackageType;

    public DataKredit() {
    }

    public DataKredit(String modelMobil, String tipeMobil, String paket,
                      String hargaOtr, String rate, String dp, String tenor,
                      String jenisAngsuran, String namaAsuransi, String asuransiType,
                      String coverageType, String PCLType, String diskon, String provisionAmount,
                      String provisionType, String servicePackageAmount, String servicePackageType) {
        ModelMobil = modelMobil;
        TipeMobil = tipeMobil;
        Paket = paket;
        HargaOtr = hargaOtr;
        Rate = rate;
        Dp = dp;
        Tenor = tenor;
        JenisAngsuran = jenisAngsuran;
        NamaAsuransi = namaAsuransi;
        AsuransiType = asuransiType;
        CoverageType = coverageType;
        this.PCLType = PCLType;
        Diskon = diskon;
        ProvisionAmount = provisionAmount;
        ProvisionType = provisionType;
        ServicePackageAmount = servicePackageAmount;
        ServicePackageType = servicePackageType;
    }

    public String getModelMobil() {
        return ModelMobil;
    }

    public void setModelMobil(String modelMobil) {
        ModelMobil = modelMobil;
    }

    public String getTipeMobil() {
        return TipeMobil;
    }

    public void setTipeMobil(String tipeMobil) {
        TipeMobil = tipeMobil;
    }

    public String getPaket() {
        return Paket;
    }

    public void setPaket(String paket) {
        Paket = paket;
    }

    public String getHargaOtr() {
        return HargaOtr;
    }

    public void setHargaOtr(String hargaOtr) {
        HargaOtr = hargaOtr;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getDp() {
        return Dp;
    }

    public void setDp(String dp) {
        Dp = dp;
    }

    public String getTenor() {
        return Tenor;
    }

    public void setTenor(String tenor) {
        Tenor = tenor;
    }

    public String getJenisAngsuran() {
        return JenisAngsuran;
    }

    public void setJenisAngsuran(String jenisAngsuran) {
        JenisAngsuran = jenisAngsuran;
    }

    public String getNamaAsuransi() {
        return NamaAsuransi;
    }

    public void setNamaAsuransi(String namaAsuransi) {
        NamaAsuransi = namaAsuransi;
    }

    public String getAsuransiType() {
        return AsuransiType;
    }

    public void setAsuransiType(String asuransiType) {
        AsuransiType = asuransiType;
    }

    public String getCoverageType() {
        return CoverageType;
    }

    public void setCoverageType(String coverageType) {
        CoverageType = coverageType;
    }

    public String getPCLType() {
        return PCLType;
    }

    public void setPCLType(String PCLType) {
        this.PCLType = PCLType;
    }

    public String getDiskon() {
        return Diskon;
    }

    public void setDiskon(String diskon) {
        Diskon = diskon;
    }

    public String getProvisionAmount() {
        return ProvisionAmount;
    }

    public void setProvisionAmount(String provisionAmount) {
        ProvisionAmount = provisionAmount;
    }

    public String getProvisionType() {
        return ProvisionType;
    }

    public void setProvisionType(String provisionType) {
        ProvisionType = provisionType;
    }

    public String getServicePackageAmount() {
        return ServicePackageAmount;
    }

    public void setServicePackageAmount(String servicePackageAmount) {
        ServicePackageAmount = servicePackageAmount;
    }

    public String getServicePackageType() {
        return ServicePackageType;
    }

    public void setServicePackageType(String servicePackageType) {
        ServicePackageType = servicePackageType;
    }
}