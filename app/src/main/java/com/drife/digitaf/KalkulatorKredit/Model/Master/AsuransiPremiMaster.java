package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AsuransiPremi;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Database.CoverageInsurance;

import java.util.ArrayList;
import java.util.List;

public class AsuransiPremiMaster {
    private static List<AsuransiPremi> asuransiPremis;

    public static List<AsuransiPremi> getAsuransiPremis(){
        /*premiType should be basic and tjh3*/
        /*jika premiType = basic, tjh3 = 0; jika premiType tjh3, rateCompre dan rateTLO = 0*/
        asuransiPremis = new ArrayList<>();
        AsuransiPremi asuransiPremi1 = new AsuransiPremi(null,null,null,0,125000000,3.44,1.14,null,0);
        AsuransiPremi asuransiPremi2 = new AsuransiPremi(null,null,null,125000000,200000000,2.97,1.02,null,0);
        AsuransiPremi asuransiPremi3 = new AsuransiPremi(null,null,null,200000000,400000000,2.55,0.84,null,0);
        AsuransiPremi asuransiPremi4 = new AsuransiPremi(null,null,null,400000000,800000000,2.12,0.79,null,0);
        AsuransiPremi asuransiPremi5 = new AsuransiPremi(null,null,null,800000000,0,2.12,0.79,null,0);

        AsuransiPremi asuransiPremi6 = new AsuransiPremi(null,null,null,800000000,0,2.12,0.79,null,0);
        asuransiPremis.add(asuransiPremi1);
        asuransiPremis.add(asuransiPremi2);
        asuransiPremis.add(asuransiPremi3);
        asuransiPremis.add(asuransiPremi4);
        asuransiPremis.add(asuransiPremi5);
        return asuransiPremis;
    }

    public static List<AsuransiPremi> getAsuransiPremi(){
        List<AsuransiPremi> asuransiPremis = new ArrayList<>();
        List<CoverageInsurance> coverageInsurances = CoverageInsuranceController.getAllCoverageInsurance();
        List<Double> tlo = new ArrayList<>();
        List<Double> compre = new ArrayList<>();
        for(int i=0; i<coverageInsurances.size(); i++){
            CoverageInsurance coverageInsurance = coverageInsurances.get(i);
            String covRate = coverageInsurance.getRate();
            String type = coverageInsurance.getCoverageType();
            if(!covRate.equals("")){
                if(type.toUpperCase().equals("ALL")){

                }else{

                }
            }

        }
        return asuransiPremis;
    }
}
