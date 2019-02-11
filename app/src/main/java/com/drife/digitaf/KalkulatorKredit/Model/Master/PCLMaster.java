package com.drife.digitaf.KalkulatorKredit.Model.Master;

import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Database.CoverageInsurance;

import java.util.ArrayList;
import java.util.List;

public class PCLMaster {

    public static List<Double> getPCL(){
        List<Double> list = new ArrayList<>();
       /* List<CoverageInsurance> coverageInsurances = CoverageInsuranceController.getAllCoverageInsurance();
        for(int i=0; i<coverageInsurances.size(); i++){
            String covRate = coverageInsurances.get(i).getRate();
            if(!covRate.equals("")){
                list.add(Double.parseDouble(covRate));
            }

        }*/

        /*list.add(1.75);
        list.add(1.75);
        list.add(1.75);
        list.add(1.75);
        list.add(1.75);*/
        return list;
    }
}
