package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;

import java.util.ArrayList;
import java.util.List;

public class TenorMaster {
    private static List<Tenor> tenors;

    public static List<Tenor> getTenor(){
        Tenor tenor1 = new Tenor("id","12");
        Tenor tenor2 = new Tenor("id","24");
        Tenor tenor3 = new Tenor("id","36");
        Tenor tenor4 = new Tenor("id","48");
        Tenor tenor5 = new Tenor("id","60");

        tenors = new ArrayList<>();
        tenors.add(tenor1);
        tenors.add(tenor2);
        tenors.add(tenor3);
        tenors.add(tenor4);
        tenors.add(tenor5);
        return tenors;
    }
}
