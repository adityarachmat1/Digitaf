package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AreaRate;

import java.util.ArrayList;
import java.util.List;

public class AreaRateMaster {
    private static List<AreaRate> areaRates;

    public static void getAreaRates(){
        AreaRate areaRate1 = new AreaRate(null,"DKI","DKI","DKI");

        areaRates = new ArrayList<>();
        areaRates.add(areaRate1);
    }
}
