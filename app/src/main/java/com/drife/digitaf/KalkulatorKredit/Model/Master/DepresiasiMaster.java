package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Asuransi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.ORM.Controller.DepresiasiController;

import java.util.ArrayList;
import java.util.List;

public class DepresiasiMaster {
    private static List<Depresiasi> depresiasis;

    public static List<Depresiasi> getDepresiasi(){
        List<Tenor> tenors = TenorMaster.getTenor();
        //Dummy 1
        String id1 = "1";
        Tenor tenor1 = new Tenor("id","12");
        Asuransi asuransi1 = new Asuransi("id","Garda Oto", "Garda Oto");
        double percentage1 = 100;
        Depresiasi depresiasi12 = new Depresiasi(id1,tenors.get(0),asuransi1,percentage1);

        //Dummy 2
        String id2 = "2";
        Tenor tenor2 = new Tenor("id","24");
        Asuransi asuransi2 = new Asuransi("id","Garda Oto", "Garda Oto");
        double percentage2 = 80;
        Depresiasi depresiasi24 = new Depresiasi(id2,tenors.get(1),asuransi2,percentage2);

        //Dummy 3
        String id3 = "3";
        Tenor tenor3 = new Tenor("id","36");
        Asuransi asuransi3 = new Asuransi("id","Garda Oto", "Garda Oto");
        double percentage3 = 80;
        Depresiasi depresiasi36 = new Depresiasi(id3,tenors.get(2),asuransi3,percentage3);

        //Dummy 4
        String id4 = "4";
        Tenor tenor4 = new Tenor("id","48");
        Asuransi asuransi4 = new Asuransi("id","Garda Oto", "Garda Oto");
        double percentage4 = 70;
        Depresiasi depresiasi48 = new Depresiasi(id4,tenors.get(3),asuransi4,percentage4);

        //Dummy 5
        String id5 = "5";
        Tenor tenor5 = new Tenor("id","60");
        Asuransi asuransi5 = new Asuransi("id","Garda Oto", "Garda Oto");
        double percentage5 = 70;
        Depresiasi depresiasi60 = new Depresiasi(id5,tenors.get(4),asuransi5,percentage5);

        depresiasis = new ArrayList<>();
        depresiasis.add(depresiasi12);
        depresiasis.add(depresiasi24);
        depresiasis.add(depresiasi36);
        depresiasis.add(depresiasi48);
        depresiasis.add(depresiasi60);

        return depresiasis;
    }

    public static List<Depresiasi> getDepresiasi(List<Tenor> tenors){
        List<Depresiasi> list = new ArrayList<>();
        List<com.drife.digitaf.ORM.Database.Depresiasi> depresiasis = DepresiasiController.getAllDepresiasi();
        for (int i=0; i<tenors.size(); i++){
            com.drife.digitaf.ORM.Database.Depresiasi depresiasi = depresiasis.get(i);
            Depresiasi dep = new Depresiasi(depresiasi.getDepresiasiId(),tenors.get(i),null,Double.parseDouble(depresiasi.getPercentage()));
            list.add(dep);
        }

        return list;
    }
}
