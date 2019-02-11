package com.drife.digitaf.ORM.Controller;

import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.ORM.Database.CoverageInsurance;
import com.drife.digitaf.retrofitmodule.Model.Dealer;
import com.drife.digitaf.retrofitmodule.Model.PVInsco;
import com.drife.digitaf.retrofitmodule.Model.PVPremi;
import com.orm.query.Select;
import com.orm.util.NamingHelper;

import java.util.List;

public class CoverageInsuranceController {
    public static String TAG = CoverageInsuranceController.class.getSimpleName();

    public static boolean bulkInsertCoverage(PVInsco pvInsco){
        LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCoverage","pvInsco",JSONProcessor.toJSON(pvInsco));
        try {
            String PVInsco = pvInsco.getName();
            String PVInscoId = pvInsco.getId();
            String PVInscoCode = pvInsco.getCode();
            List<PVPremi> pvPremis = pvInsco.getPv_premi();
            if(pvPremis != null){
                //CoverageInsuranceController.removeAllCoverage();
                for (int i=0; i<pvPremis.size(); i++){
                    PVPremi pvPremi = pvPremis.get(i);
                    String InsuranceAreaId = pvPremi.getArea().getId();
                    String InsuranceAreaCode = pvPremi.getArea().getCode();
                    String InsuranceArea = pvPremi.getArea().getName();
                    String MinTSI = pvPremi.getCoverage_min();
                    String MaxTSI = pvPremi.getCoverage_max();
                    String CoverageTypeId = pvPremi.getCoverage_type().getId();
                    String CoverageTypeCode = pvPremi.getCoverage_type().getCode();
                    String CoverageType = pvPremi.getCoverage_type().getName();
                    String Rate = pvPremi.getRate();
                    CoverageInsurance coverageInsurance = new CoverageInsurance(PVInscoId,PVInscoCode,PVInsco,InsuranceAreaId,InsuranceAreaCode,InsuranceArea,
                            MinTSI,MaxTSI,CoverageTypeId,CoverageTypeCode,CoverageType,Rate);
                    long id = coverageInsurance.save();
                    //LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCoverage","insert premi : "+id);
                    Log.d("singo", "index : "+i);
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCoverage","success insert");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertCoverage","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean bulkInsertCoverage(Dealer dealer){
        //LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCoverage","pvInsco",JSONProcessor.toJSON(pvInsco));
        try {
            PVInsco pvInsco = dealer.getPv_insco();
            String PVInsco = pvInsco.getName();
            String PVInscoId = pvInsco.getId();
            String PVInscoCode = pvInsco.getCode();
            List<PVPremi> pvPremis = pvInsco.getPv_premi();
            if(pvPremis != null){
                //CoverageInsuranceController.removeAllCoverage();
                for (int i=0; i<pvPremis.size(); i++){
                    PVPremi pvPremi = pvPremis.get(i);
                    String InsuranceAreaId = pvPremi.getArea().getId();
                    String InsuranceAreaCode = pvPremi.getArea().getCode();
                    String InsuranceArea = pvPremi.getArea().getName();
                    String MinTSI = pvPremi.getCoverage_min();
                    String MaxTSI = pvPremi.getCoverage_max();
                    String CoverageTypeId = pvPremi.getCoverage_type().getId();
                    String CoverageTypeCode = pvPremi.getCoverage_type().getCode();
                    String CoverageType = pvPremi.getCoverage_type().getName();
                    String Rate = pvPremi.getRate();
                    CoverageInsurance coverageInsurance = new CoverageInsurance(PVInscoId,PVInscoCode,PVInsco,InsuranceAreaId,InsuranceAreaCode,InsuranceArea,
                            MinTSI,MaxTSI,CoverageTypeId,CoverageTypeCode,CoverageType,Rate,dealer.getId(),dealer.getCode(),dealer.getName());
                    long id = coverageInsurance.save();
                    //LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCoverage","insert premi : "+id);
                    Log.d("singo", "index : "+i);
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCoverage","success insert");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertCoverage","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllCoverage(){
        try {
            CoverageInsurance.deleteAll(CoverageInsurance.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllCoverage","success clear data");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllCoverage","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CoverageInsurance> getAllCoverageInsurance(){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("CoverageInsurance");
            List<CoverageInsurance> resultList =
                    com.drife.digitaf.ORM.Database.CoverageInsurance.listAll(CoverageInsurance.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllCoverageInsurance","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllCoverageInsurance","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CoverageInsurance> getTLO(){
        try {
            Select select = Select.from(com.drife.digitaf.ORM.Database.CoverageInsurance.class)
                    .where(NamingHelper.toSQLNameDefault("CoverageTypeCode") +" = "+"'TLO'");

            List<com.drife.digitaf.ORM.Database.CoverageInsurance> resultList = select.list();
            LogUtility.logging(TAG,LogUtility.infoLog,"getTLO","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getTLO","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CoverageInsurance> getTLO(String PVInscoCode, String InsuranceAreaCode, String DealerCode){
        LogUtility.logging(TAG,LogUtility.infoLog,"getTLO","PVInscoCode", PVInscoCode);
        try {
            Select select = Select.from(com.drife.digitaf.ORM.Database.CoverageInsurance.class)
                    .where(NamingHelper.toSQLNameDefault("CoverageTypeCode") +" = "+"'TLO' and " +
                            NamingHelper.toSQLNameDefault("PVInscoCode")+" = '"+PVInscoCode+"' and " +
                            NamingHelper.toSQLNameDefault("InsuranceAreaCode")+ " = '"+InsuranceAreaCode+"' and " +
                            NamingHelper.toSQLNameDefault("DealerCode")+ " = '"+DealerCode+"'");

            List<com.drife.digitaf.ORM.Database.CoverageInsurance> resultList = select.list();
            LogUtility.logging(TAG,LogUtility.infoLog,"getTLO","resultList", JSONProcessor.toJSON(resultList));
            Log.d("singo", "coverage size tlo: "+resultList.size());
            for (int i=0; i<resultList.size(); i++){
                Log.d("singo", "coverage tlo: "+resultList.get(i).getInsuranceArea());
            }
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getTLO","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CoverageInsurance> getCompre(){
        try {
            Select select = Select.from(com.drife.digitaf.ORM.Database.CoverageInsurance.class)
                    .where(NamingHelper.toSQLNameDefault("CoverageTypeCode") +" = "+"'ALL'");

            List<com.drife.digitaf.ORM.Database.CoverageInsurance> resultList = select.list();
            LogUtility.logging(TAG,LogUtility.infoLog,"getCompre","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getCompre","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CoverageInsurance> getCompre(String PVInscoCode, String InsuranceAreaCode, String DealerCode){
        LogUtility.logging(TAG,LogUtility.infoLog,"getCompre","PVInscoCode", PVInscoCode);
        try {
            Select select = Select.from(com.drife.digitaf.ORM.Database.CoverageInsurance.class)
                    .where(NamingHelper.toSQLNameDefault("CoverageTypeCode") +" = "+"'ALL' and " +
                            NamingHelper.toSQLNameDefault("PVInscoCode")+" = '"+PVInscoCode+"' and " +
                            NamingHelper.toSQLNameDefault("InsuranceAreaCode")+ " = '"+InsuranceAreaCode+"' and " +
                            NamingHelper.toSQLNameDefault("DealerCode")+ " = '"+DealerCode+"'");

            List<com.drife.digitaf.ORM.Database.CoverageInsurance> resultList = select.list();
            LogUtility.logging(TAG,LogUtility.infoLog,"getCompre","resultList", JSONProcessor.toJSON(resultList));

            /*String query = "select * from "+ NamingHelper.toSQLNameDefault("CoverageTypeCode")+" where " +
                    NamingHelper.toSQLNameDefault("CoverageTypeCode")+" = 'ALL' and " +
                    NamingHelper.toSQLNameDefault("PVInscoCode")+" = '"+PVInscoCode+"'";
            List<com.drife.digitaf.ORM.Database.CoverageInsurance> resultList =
                    com.drife.digitaf.ORM.Database.CarType.findWithQuery(com.drife.digitaf.ORM.Database.CoverageInsurance.class, query);*/
            Log.d("singo", "coverage size compre: "+resultList.size());
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getCompre","Exception",e.getMessage());
            return null;
        }
    }
}
