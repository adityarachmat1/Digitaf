package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class RateBunga {
    private String id;
    private CarModel carModel;
    private Tenor tenor;
    private AreaRate areaRate;
    private double rateBunga;

    public RateBunga() {
    }

    public RateBunga(String id, CarModel carModel, Tenor tenor, AreaRate areaRate, double rateBunga) {
        this.id = id;
        this.carModel = carModel;
        this.tenor = tenor;
        this.areaRate = areaRate;
        this.rateBunga = rateBunga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public Tenor getTenor() {
        return tenor;
    }

    public void setTenor(Tenor tenor) {
        this.tenor = tenor;
    }

    public AreaRate getAreaRate() {
        return areaRate;
    }

    public void setAreaRate(AreaRate areaRate) {
        this.areaRate = areaRate;
    }

    public double getRateBunga() {
        return rateBunga;
    }

    public void setRateBunga(double rateBunga) {
        this.rateBunga = rateBunga;
    }
}
