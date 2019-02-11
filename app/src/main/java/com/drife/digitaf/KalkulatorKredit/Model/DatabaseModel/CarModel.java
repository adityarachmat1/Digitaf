package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class CarModel {
    private String CarId;
    private String Name;
    private String Code;
    private KatagoriMobil KatagoriMobil;

    public CarModel() {
    }

    public CarModel(String id, String name, String code, KatagoriMobil katagoriMobil) {
        this.CarId = id;
        this.Name = name;
        this.Code = code;
        this.KatagoriMobil = katagoriMobil;
    }

    public String getCarId() {
        return CarId;
    }

    public void setCarId(String carId) {
        CarId = carId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public KatagoriMobil getKatagoriMobil() {
        return KatagoriMobil;
    }

    public void setKatagoriMobil(KatagoriMobil katagoriMobil) {
        this.KatagoriMobil = katagoriMobil;
    }
}
