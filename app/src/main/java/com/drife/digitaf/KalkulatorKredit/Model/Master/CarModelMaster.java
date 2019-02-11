package com.drife.digitaf.KalkulatorKredit.Model.Master;

import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.CarModel;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.KatagoriMobil;
import com.drife.digitaf.ORM.Controller.CarController;

import java.util.ArrayList;
import java.util.List;

public class CarModelMaster {
    private static List<CarModel> carModels = new ArrayList<>();

    public static List<CarModel> getCarModel(){
        carModels.clear();
        List<com.drife.digitaf.ORM.Database.CarModel> carModelList = CarController.getAllCar();
        for(int i=0; i<carModelList.size(); i++){
            com.drife.digitaf.ORM.Database.CarModel model = carModelList.get(i);
            String CarId = model.getCarModelId();
            String Name = model.getCarName();
            String Code = model.getCarCode();
            KatagoriMobil KatagoriMobil = null;
            CarModel car = new CarModel(CarId,Name,Code,null);
            carModels.add(car);
        }
        /*CarModel carModel1 = new CarModel(null,"Avanza","Avanza",null);
        CarModel carModel2 = new CarModel(null,"Innova","Innova",null);

        carModels = new ArrayList<>();
        carModels.add(carModel1);
        carModels.add(carModel2);*/
        return carModels;
    }

    public static List<com.drife.digitaf.ORM.Database.CarModel> getCars(){
        List<com.drife.digitaf.ORM.Database.CarModel> carModelList = CarController.getAllCar();
        return carModelList;
    }
}
