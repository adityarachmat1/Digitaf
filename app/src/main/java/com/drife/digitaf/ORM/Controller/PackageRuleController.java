package com.drife.digitaf.ORM.Controller;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.Activity.NonPaketActivity;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.PackageRule;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

public class PackageRuleController {
    public static String TAG = PackageRuleController.class.getSimpleName();

    public interface Callback{
        void onSuccessLoadData(List<com.drife.digitaf.ORM.Database.PackageRule> packageRules);
        void onFailedLoadData();
        void onEmptyResult();
    }

    public static boolean bulkInsertPackageRule(List<PackageRule> packageRules){
        try {
            if(packageRules != null){
                for (int i=0; i<packageRules.size(); i++){
                    PackageRule packageRule = packageRules.get(i);
                    String PackageRuleId = packageRule.getId();
                    String PackageId = packageRule.getaPackage().getId();
                    String PackageCode = packageRule.getaPackage().getCode();
                    String PackageName = packageRule.getaPackage().getName();
                    String CategoryGroupId = packageRule.getCategory_group().getId();
                    String CategoryGroupCode = packageRule.getCategory_group().getCode();
                    String CategoryGroup = packageRule.getCategory_group().getName();
                    String CarModelId = packageRule.getCar_model().getId();
                    String CarModelCode = packageRule.getCar_model().getCode();
                    String CarModel = packageRule.getCar_model().getName();
                    String RateAreaId = packageRule.getRate_area().getId();
                    String RateAreaCode = packageRule.getRate_area().getCode();
                    String RateArea = packageRule.getRate_area().getName();
                    String InstallmentTypeId = packageRule.getInstallment_type().getId();
                    String InstallmentTypeCode = packageRule.getInstallment_type().getCode();
                    String InstallmentType = packageRule.getInstallment_type().getName();
                    String TenorId = "";
                    String TenorCode = "";
                    String Tenor = packageRule.getTenor_code();
                    String MinDp = packageRule.getMin_dp();
                    String BaseDp = packageRule.getBase_dp();
                    String MaxDp = packageRule.getMax_dp();
                    String MinRate = packageRule.getMin_rate();
                    String BaseRate = packageRule.getBase_rate();
                    String MaxRate = packageRule.getMax_rate();
                    String AdminFee = packageRule.getAdmin_fee();
                    String Provision = packageRule.getProvision();
                    String MinProvision = packageRule.getProvision_min();
                    String BaseProvision = packageRule.getProvision_base();
                    String MaxProvision = packageRule.getProvision_max();
                    String ProvisionPaymentId = packageRule.getProvision_payment().getId();
                    String PrivisionPaymentCode = packageRule.getProvision_payment().getCode();
                    String ProvisionPayment = packageRule.getProvision_payment().getName();
                    String TAVPCoverageId = packageRule.getTavp_coverage().getId();
                    String TAVPCoverageCode = packageRule.getTavp_coverage().getCode();
                    String TAVPCoverage = packageRule.getTavp_coverage().getName();
                    String TAVPPaymentId = packageRule.getTavp_payment().getId();
                    String TAVPPaymentCode = packageRule.getTavp_payment().getCode();
                    String TAVPPayment = packageRule.getTavp_payment().getName();
                    String PCL = packageRule.getPcl_type().getName();
                    Log.d("singo", "PCL insert : "+PCL);
                    String PCLPaymentId = packageRule.getPcl_payment().getId();
                    String PCLPaymentCode = packageRule.getPcl_payment().getCode();
                    String PCLPayment = packageRule.getPcl_payment().getName();
                    String PackageImage = packageRule.getaPackage().getImage();
                    String IsNonPackage = packageRule.getaPackage().getIs_non_package();

                    com.drife.digitaf.ORM.Database.PackageRule rule = new com.drife.digitaf.ORM.Database.PackageRule(PackageRuleId,
                            PackageId,PackageCode,PackageName,CategoryGroupId,CategoryGroupCode,CategoryGroup,CarModelId,CarModelCode,
                            CarModel,RateAreaId,RateAreaCode,RateArea,InstallmentTypeId,InstallmentTypeCode,InstallmentType,TenorId,
                            TenorCode,Tenor,MinDp,BaseDp,MaxDp,MinRate,BaseRate,MaxRate,AdminFee,Provision,MinProvision,BaseProvision,
                            MaxProvision,ProvisionPaymentId,PrivisionPaymentCode,ProvisionPayment,TAVPCoverageId,TAVPCoverageCode,
                            TAVPCoverage,TAVPPaymentId,TAVPPaymentCode,TAVPPayment,PCL,PCLPaymentId,PCLPaymentCode,PCLPayment,
                            PackageImage,IsNonPackage);

                    rule.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertPackageRule","success insert package rule");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertPackageRule","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllPackageRule(){
        try {
            com.drife.digitaf.ORM.Database.PackageRule.deleteAll(com.drife.digitaf.ORM.Database.PackageRule.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllPackageRule","success clear package rule");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllPackageRule","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackageRules(String selectedPackage, String selectedCarModel, String selectedCategoryGroup, String selectedRatearea, List<String> TOPs, List<String> tenors){
        String query = "";
        if(selectedCarModel != null){
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("PackageCode")+" = '"+selectedPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CarModelCode")+" = '"+selectedCarModel+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"')";
        }else{
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("PackageCode")+" = '"+selectedPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CategoryGroupId")+" = '"+selectedCategoryGroup+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"')";
        }

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRules","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRules","resultList", JSONProcessor.toJSON(resultList));
        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackageRules(int isNonPackage, String selectedCarModel, String selectedCategoryGroup, String selectedRatearea, List<String> TOPs, List<String> tenors){
        String query = "";
        if(selectedCarModel != null){
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("IsNonPackage")+" = '"+isNonPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CarModelCode")+" = '"+selectedCarModel+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"')";
        }else{
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("IsNonPackage")+" = '"+isNonPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CategoryGroupId")+" = '"+selectedCategoryGroup+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"')";
        }

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRules","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRules","resultList", JSONProcessor.toJSON(resultList));
        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackageRulesCode(String PackageCode){
        String query = "";
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("PackageCode")+" = '"+PackageCode+"')";
        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
       LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRules","resultList", JSONProcessor.toJSON(resultList));
        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackageRules(String selectedPackage, String selectedCarModel, String selectedCategoryGroup, String selectedRatearea, List<String> TOPs,
                                                                                   List<String> tenors, String orderBy, String sortType){
        String query = "";
        if(selectedCarModel != null){
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("PackageCode")+" = '"+selectedPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CarModelCode")+" = '"+selectedCarModel+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"') " +
                    "order by "+orderBy+" "+sortType;
        }else{
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("PackageCode")+" = '"+selectedPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CategoryGroupId")+" = '"+selectedCategoryGroup+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"') " +
                    "order by "+orderBy+" "+sortType;
        }

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRules","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRules","resultList", JSONProcessor.toJSON(resultList));
        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackageRulesByTenor(String selectedPackage, String selectedCarModel,
                                                                                          String selectedCategoryGroup, String selectedRatearea, String tenor,
                                                                                          String orderBy, String sortType){
        String query = "";
        if(selectedCarModel != null){
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("PackageCode")+" = '"+selectedPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CarModelCode")+" = '"+selectedCarModel+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"'and " +
                    NamingHelper.toSQLNameDefault("Tenor")+" = '"+tenor+"') " +
                    "order by "+orderBy+" "+sortType;
        }else{
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("PackageCode")+" = '"+selectedPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CategoryGroupId")+" = '"+selectedCategoryGroup+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"' and " +
                    NamingHelper.toSQLNameDefault("Tenor")+" = '"+tenor+"') " +
                    "order by "+orderBy+" "+sortType;
        }

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRulesByTenor","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRulesByTenor","resultList", JSONProcessor.toJSON(resultList));
        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackageRulesByTenor(int isNonPackage, String selectedCarModel,
                                                                                          String selectedCategoryGroup, String selectedRatearea, String tenor,
                                                                                          String orderBy, String sortType){
        String query = "";
        if(selectedCarModel != null){
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("IsNonPackage")+" = '"+isNonPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CarModelCode")+" = '"+selectedCarModel+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"'and " +
                    NamingHelper.toSQLNameDefault("Tenor")+" = '"+tenor+"') " +
                    "order by "+orderBy+" "+sortType;
        }else{
            query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                    NamingHelper.toSQLNameDefault("IsNonPackage")+" = '"+isNonPackage+"' and " +
                    NamingHelper.toSQLNameDefault("CategoryGroupId")+" = '"+selectedCategoryGroup+"' and "+
                    NamingHelper.toSQLNameDefault("RateAreaId")+" = '"+selectedRatearea+"' and " +
                    NamingHelper.toSQLNameDefault("Tenor")+" = '"+tenor+"') " +
                    "order by "+orderBy+" "+sortType;
        }

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRulesByTenor","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRulesByTenor","resultList", JSONProcessor.toJSON(resultList));
        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> test(String selectedPackage,String selectedCarModel){
        /*String query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule")+" where (" +
                NamingHelper.toSQLNameDefault("PackageCode")+" = '"+selectedPackage+"' and " +
                NamingHelper.toSQLNameDefault("CarModelCode")+" = '"+selectedCarModel+"')";*/

        String query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule");

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackage","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackage","resultList", JSONProcessor.toJSON(resultList));
        return resultList;
    }

    /*public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackage(String groupBy){
        String query = "";
        query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule");

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList =
                com.drife.digitaf.ORM.Database.PackageRule.findWithQuery(com.drife.digitaf.ORM.Database.PackageRule.class, query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackage","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackage","resultList", JSONProcessor.toJSON(resultList));

        return resultList;
    }*/

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getPackage(){
        String query = "";
        //query = "select * from "+NamingHelper.toSQLNameDefault("PackageRule") +" GROUP BY "+NamingHelper.toSQLNameDefault(groupBy);

        Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("PackageCode") +" != "+"'RCFCVNONPAKET' and "+NamingHelper.toSQLNameDefault("PackageCode") + " != 'RCFCVDNONPAKET'")
                .groupBy(NamingHelper.toSQLNameDefault("PackageCode"));

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList = select.list();
        //LogUtility.logging(TAG,LogUtility.infoLog,"getPackage","query", query);
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackage","resultList", JSONProcessor.toJSON(resultList));

        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getNonPackage(){
        /*Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("PackageCode") +" = "+"'RCFCVNONPAKET'")
                .groupBy(NamingHelper.toSQLNameDefault("PackageCode"));*/

        /*Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("PackageCode") +" = "+"'RCFCVNONPAKET'")
                .groupBy(NamingHelper.toSQLNameDefault("CategoryGroupId"));*/

        Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("IsNonPackage") +" = "+"'1'")
                .groupBy(NamingHelper.toSQLNameDefault("CategoryGroupId"));

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList = select.list();
        LogUtility.logging(TAG,LogUtility.infoLog,"getNonPackage","resultList", JSONProcessor.toJSON(resultList));

        return resultList;
    }

    /*public static void getPackageRulesByCarCode(final Context context, final String packageCode, final String carCode, final Callback callback){
        class load extends AsyncTask<Void,Void,Void>{
            List<com.drife.digitaf.ORM.Database.PackageRule> packageRules = new ArrayList<>();
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    packageRules = PackageRuleController.getPackageRules(packageCode,
                            carCode,null,
                            SharedPreferenceUtils.getUserSession(context).getUser().getDealer().getBranch().getRate_area().getId(),
                            null,null);
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getPackageRulesByCarCode","Exception",e.getMessage());
                    callback.onFailedLoadData();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(packageRules.size()>0){
                    callback.onSuccessLoadData(packageRules);
                }else{
                    callback.onEmptyResult();
                }
            }
        }
        new load().execute();
    }*/

    /*public static void getPackageRulesByCarCategoryGroup(final Context context, final String packageCode, final String carCategoryGroupId, final Callback callback){
        class load extends AsyncTask<Void,Void,Void>{
            List<com.drife.digitaf.ORM.Database.PackageRule> packageRules = new ArrayList<>();
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    packageRules = PackageRuleController.getPackageRules(packageCode,
                            null,carCategoryGroupId,
                            SharedPreferenceUtils.getUserSession(context).getUser().getDealer().getBranch().getRate_area().getId(),
                            null,null);
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getPackageRulesByCarCategoryGroup","Exception",e.getMessage());
                    callback.onFailedLoadData();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(packageRules.size()>0){
                    callback.onSuccessLoadData(packageRules);
                }else{
                    callback.onEmptyResult();
                }
            }
        }
        new load().execute();
    }*/

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getNonPackage(String name){
        Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("IsNonPackage") +" = "+"'1' AND " +
                        NamingHelper.toSQLNameDefault("CarModel")+" = '"+name+"'");

        /*Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("CarModel") +" = "+"'"+name+"'");*/

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList = select.list();
        LogUtility.logging(TAG,LogUtility.infoLog,"getNonPackage","size", resultList.size()+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"getNonPackage","resultList", JSONProcessor.toJSON(resultList));

        return resultList;
    }

    public static List<com.drife.digitaf.ORM.Database.PackageRule> getNonPackage(String PackageCode, String CarModel){
        Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("IsNonPackage") +" = "+"'1' AND " +
                        NamingHelper.toSQLNameDefault("CarModel")+" = '"+CarModel+"'");

        /*Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("CarModel") +" = "+"'"+name+"'");*/

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList = select.list();
        LogUtility.logging(TAG,LogUtility.infoLog,"getNonPackage","size", resultList.size()+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"getNonPackage","resultList", JSONProcessor.toJSON(resultList));

        return resultList;
    }

    public static List<Long> getAdminFee(){
        Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .groupBy(NamingHelper.toSQLNameDefault("Tenor"))
                .orderBy(NamingHelper.toSQLNameDefault("Tenor"));

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList = select.list();
        LogUtility.logging(TAG,LogUtility.infoLog,"getAdminFee","size", resultList.size()+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"getAdminFee","resultList", JSONProcessor.toJSON(resultList));

        List<Long> adminFee = new ArrayList<>();
        for (int i=0; i<resultList.size(); i++){
            Long admin = 0l;
            String adm = resultList.get(i).getAdminFee();
            if(adm != null && !adm.equals("")){
                admin = Long.parseLong(adm);
            }
            adminFee.add(admin);
        }
        return adminFee;
    }
}
