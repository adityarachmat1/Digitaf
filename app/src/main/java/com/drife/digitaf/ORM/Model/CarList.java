package com.drife.digitaf.ORM.Model;

import java.io.Serializable;
import java.util.List;

public class CarList implements Serializable{
    private List<Car> cars;

    public CarList() {
    }

    public CarList(List<Car> cars) {
        this.cars = cars;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
