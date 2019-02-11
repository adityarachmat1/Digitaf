package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AreaAsuransi;

import java.util.ArrayList;
import java.util.List;

public class AreaAsuransiMaster {
    private static List<AreaAsuransi> areaAsuransis;

    public static List<AreaAsuransi> getAreaAsuransi(){
        areaAsuransis = new ArrayList<>();
        /*dummy data 1*/
        AreaAsuransi areaAsuransi1 = new AreaAsuransi("id","Wilayah 2","Jakarta, Jabar, dan Banten", "code");
        areaAsuransis.add(areaAsuransi1);
        return areaAsuransis;
    }
}
