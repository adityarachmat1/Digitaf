package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class PCL {
    private Tenor tenor;
    private double premi;

    public PCL() {
    }

    public PCL(Tenor tenor, double premi) {
        this.tenor = tenor;
        this.premi = premi;
    }

    public Tenor getTenor() {
        return tenor;
    }

    public void setTenor(Tenor tenor) {
        this.tenor = tenor;
    }

    public double getPremi() {
        return premi;
    }

    public void setPremi(double premi) {
        this.premi = premi;
    }
}
