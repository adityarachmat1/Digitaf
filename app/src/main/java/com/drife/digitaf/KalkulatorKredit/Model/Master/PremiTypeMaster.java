package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.PremiType;

import java.util.ArrayList;
import java.util.List;

public class PremiTypeMaster {
    private static List<PremiType> premiTypes;

    public static List<PremiType> getPremiTypes(){
        premiTypes = new ArrayList<>();
        /*dummy data 1*/
        PremiType premiType1 = new PremiType("id","name","description","code");
        premiTypes.add(premiType1);
        return premiTypes;
    }
}
