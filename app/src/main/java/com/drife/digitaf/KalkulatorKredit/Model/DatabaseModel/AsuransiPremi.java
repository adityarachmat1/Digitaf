package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class AsuransiPremi {
    private String Id;
    private Asuransi Asuransi;
    private AreaAsuransi AreaAsuransi;
    private int CoverageMin;
    private int CoverageMax;
    private double RateCompre;
    private double RateTLO;
    private PremiType PremiType;
    private double RateTjh;

    public AsuransiPremi() {
    }

    public AsuransiPremi(String id, com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Asuransi asuransi, com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AreaAsuransi areaAsuransi, int coverageMin, int coverageMax, double rateCompre, double rateTLO, com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.PremiType premiType, double rateTjh) {
        Id = id;
        Asuransi = asuransi;
        AreaAsuransi = areaAsuransi;
        CoverageMin = coverageMin;
        CoverageMax = coverageMax;
        RateCompre = rateCompre;
        RateTLO = rateTLO;
        PremiType = premiType;
        RateTjh = rateTjh;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Asuransi getAsuransi() {
        return Asuransi;
    }

    public void setAsuransi(com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Asuransi asuransi) {
        Asuransi = asuransi;
    }

    public com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AreaAsuransi getAreaAsuransi() {
        return AreaAsuransi;
    }

    public void setAreaAsuransi(com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AreaAsuransi areaAsuransi) {
        AreaAsuransi = areaAsuransi;
    }

    public int getCoverageMin() {
        return CoverageMin;
    }

    public void setCoverageMin(int coverageMin) {
        CoverageMin = coverageMin;
    }

    public int getCoverageMax() {
        return CoverageMax;
    }

    public void setCoverageMax(int coverageMax) {
        CoverageMax = coverageMax;
    }

    public double getRateCompre() {
        return RateCompre;
    }

    public void setRateCompre(double rateCompre) {
        RateCompre = rateCompre;
    }

    public double getRateTLO() {
        return RateTLO;
    }

    public void setRateTLO(double rateTLO) {
        RateTLO = rateTLO;
    }

    public com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.PremiType getPremiType() {
        return PremiType;
    }

    public void setPremiType(com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.PremiType premiType) {
        PremiType = premiType;
    }

    public double getRateTjh() {
        return RateTjh;
    }

    public void setRateTjh(double rateTjh) {
        RateTjh = rateTjh;
    }
}
