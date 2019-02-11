package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class Depresiasi {
    private String Id;
    private Tenor tenor;
    private Asuransi asuransi;
    private double Percentage;


    public Depresiasi() {
    }

    public Depresiasi(String id, Tenor tenor, Asuransi asuransi, double percentage) {
        Id = id;
        this.tenor = tenor;
        this.asuransi = asuransi;
        Percentage = percentage;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Tenor getTenor() {
        return tenor;
    }

    public void setTenor(Tenor tenor) {
        this.tenor = tenor;
    }

    public Asuransi getAsuransi() {
        return asuransi;
    }

    public void setAsuransi(Asuransi asuransi) {
        this.asuransi = asuransi;
    }

    public double getPercentage() {
        return Percentage;
    }

    public void setPercentage(double percentage) {
        Percentage = percentage;
    }
}
