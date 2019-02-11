package com.drife.digitaf.ORM.Model;

import com.drife.digitaf.ORM.Database.CarModel;
import com.drife.digitaf.ORM.Database.CarType;

import java.io.Serializable;

public class Car implements Serializable{
    private CarModel carModel;
    private CarType carType;

    public Car() {
    }

    public Car(CarModel carModel, CarType carType) {
        this.carModel = carModel;
        this.carType = carType;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
}
