package com.drife.digitaf.ORM.Controller;

import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.ORM.Database.CoverageInsurance;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.Dealer;
import com.drife.digitaf.retrofitmodule.Model.PVInsco;
import com.orm.query.Select;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

public class DealerController {
    public static String TAG = DealerController.class.getSimpleName();

    public static boolean bulkInsertDealer(List<Dealer> dealers){
        try {
            if(dealers != null){
                List<PVInsco> pvInscos = new ArrayList<>();

                for (int i=0; i<dealers.size(); i++){
                    Dealer dealer = dealers.get(i);
                    String DealerId = dealer.getId();
                    String DealerName = dealer.getName();
                    String DealerCode = dealer.getCode();
                    String Description = dealer.getDescription();
                    String DealerGroupId = dealer.getGroup().getId();
                    String DealerGroupName = dealer.getGroup().getName();
                    String DealerGroupCode = dealer.getGroup().getCode();
                    String BranchId = dealer.getBranch().getId();
                    String BranchName = dealer.getBranch().getName();
                    String BranchCode = dealer.getBranch().getCode();
                    String RateAreaId = dealer.getBranch().getRate_area().getId();
                    String RateAreaName = dealer.getBranch().getRate_area().getName();
                    String RateAreaCode = dealer.getBranch().getRate_area().getCode();
                    String InsuranceAreaId = dealer.getBranch().getInsurance_area().getId();
                    String InsuranceAreaName = dealer.getBranch().getInsurance_area().getName();
                    String InsuranceAreaCode = dealer.getBranch().getInsurance_area().getCode();
                    String PVInscoId = dealer.getPv_insco().getId();
                    String PVInscoName = dealer.getPv_insco().getName();
                    String PVInscoCode = dealer.getPv_insco().getCode();
                    String PCLInscoId = dealer.getPcl_insco().getId();
                    String PCLInscoName = dealer.getPcl_insco().getName();
                    String PCLInscoCode = dealer.getPcl_insco().getCode();
                    String PCLInscoDescription = dealer.getPcl_insco().getDescription();
                    String PCLPremi = dealer.getPcl_insco().getPcl_premi().getPremi_value();
                    String BrandId = dealer.getBrand().getId();
                    String BrandCode = dealer.getBrand().getCode();
                    String BrandName = dealer.getBrand().getName();

                    com.drife.digitaf.ORM.Database.Dealer dataDealer = new com.drife.digitaf.ORM.Database.Dealer(
                            DealerId,DealerName,DealerCode,Description,DealerGroupId,DealerGroupName,DealerGroupCode,
                            BranchId,BranchName,BranchCode,RateAreaId,RateAreaName,RateAreaCode,InsuranceAreaId,InsuranceAreaName,
                            InsuranceAreaCode,PVInscoId,PVInscoName,PVInscoCode,PCLInscoId,PCLInscoName,PCLInscoCode,PCLInscoDescription,
                            PCLPremi,BrandId,BrandCode,BrandName
                    );
                    long id = dataDealer.save();
                    LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertDealer","insert dealer : "+id);

                    if(dealer.getPv_insco().getPv_premi() != null && dealer.getPv_insco().getPv_premi().size()>0){
                        /*if(!pvInscos.contains(dealer.getPv_insco())){
                            pvInscos.add(dealer.getPv_insco());
                        }*/
                        //CoverageInsuranceController.bulkInsertCoverage(dealer.getPv_insco());
                        CoverageInsuranceController.bulkInsertCoverage(dealer);
                        //Log.d("singo", "index : "+i);
                        //pvInscos.add(dealer.getPv_insco());
                    }
                }

                /*for (int x=0; x<pvInscos.size(); x++){
                    CoverageInsuranceController.bulkInsertCoverage(pvInscos.get(x));
                }*/
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertDealer","success insert dealer");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertDealer","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllDealer(){
        try {
            com.drife.digitaf.ORM.Database.Dealer.deleteAll(com.drife.digitaf.ORM.Database.Dealer.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllDealer","success clear data");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllDealer","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Dealer> getAllDealer(){
        try {
            Select select = Select.from(com.drife.digitaf.ORM.Database.Dealer.class);

            List<com.drife.digitaf.ORM.Database.Dealer> resultList = select.list();
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllDealer","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllDealer","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Dealer> getDealer(String id){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("Dealer")+" where " +
                    NamingHelper.toSQLNameDefault("DealerId")+" = '"+id+"'";
            List<com.drife.digitaf.ORM.Database.Dealer> resultList =
                    com.drife.digitaf.ORM.Database.CarType.findWithQuery(com.drife.digitaf.ORM.Database.Dealer.class,query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getDealer","resultList",JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getDealer","Exception",e.getMessage());
            return null;
        }
    }
}
