package com.drife.digitaf.Service.Car;

import com.drife.digitaf.retrofitmodule.Model.CarModel;

import java.util.List;

public interface CarCallback {
    void onSuccessGetCarModel(List<CarModel> carModels);
    void onFailedGetCarModel(String message);
}
