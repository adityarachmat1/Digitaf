package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.RateBunga;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.CarModel;

import java.util.ArrayList;
import java.util.List;

public class RateBungaMaster {
    private static List<RateBunga> rateBungas;
    private static List<Double> rateAvanza;
    private static List<Double> rateInnova;

    private static List<RateBunga> getRateBunga(){
        rateBungas = new ArrayList<>();
        rateAvanza = new ArrayList<>();
        rateInnova = new ArrayList<>();

        rateAvanza.add((double) 4);
        rateAvanza.add((double) 5);
        rateAvanza.add((double) 6);
        rateAvanza.add((double) 7);
        rateAvanza.add((double) 8);

        rateInnova.add((double) 3);
        rateInnova.add((double) 4);
        rateInnova.add((double) 5);
        rateInnova.add((double) 6);
        rateInnova.add((double) 7);

        List<CarModel> carModels = CarModelMaster.getCarModel();
        List<Tenor> tenors = TenorMaster.getTenor();

        for(int i=0; i<carModels.size(); i++){
            CarModel carModel = carModels.get(i);
            String model = carModel.getName();
            for (int j=0; j<tenors.size(); j++){
                Tenor tenor = tenors.get(j);
                if(model.equals("Avanza")){
                    RateBunga rateBunga = new RateBunga(null,carModel,tenor,null,rateAvanza.get(j));
                    rateBungas.add(rateBunga);
                }else if(model.equals("Innova")){
                    RateBunga rateBunga = new RateBunga(null,carModel,tenor,null,rateInnova.get(j));
                    rateBungas.add(rateBunga);
                }
            }
        }
        return rateBungas;
    }

    public static List<Double> rateAvanza(){
        List<Double> list = new ArrayList<>();
        list.add((double) 4);
        list.add((double) 5);
        list.add((double) 6);
        list.add((double) 7);
        list.add((double) 8);
        return list;
    }

    public static List<Double> rateInnova(){
        List<Double> list = new ArrayList<>();
        list.add((double) 3);
        list.add((double) 4);
        list.add((double) 5);
        list.add((double) 6);
        list.add((double) 7);
        return list;
    }
}
