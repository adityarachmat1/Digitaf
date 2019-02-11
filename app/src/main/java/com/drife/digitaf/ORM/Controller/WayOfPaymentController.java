package com.drife.digitaf.ORM.Controller;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.retrofitmodule.Model.WayOfPayment;
import com.orm.util.NamingHelper;

import java.util.List;

public class WayOfPaymentController {
    private static String TAG = WayOfPaymentController.class.getSimpleName();

    public static boolean bulkInsertWOP(List<WayOfPayment> wayOfPayments){
        try {
            if(wayOfPayments != null){
                for (int i=0; i<wayOfPayments.size(); i++){
                    WayOfPayment wayOfPayment = wayOfPayments.get(i);
                    String WayOfPaymentId = wayOfPayment.getId();
                    String Code = wayOfPayment.getCode();
                    String Name = wayOfPayment.getName();
                    String Description = wayOfPayment.getDescription();
                    String CreatedBy = wayOfPayment.getCreated_by();
                    String ModifiedBy = wayOfPayment.getModified_by();
                    String IsActive = wayOfPayment.getIs_active();
                    String IsActiveConfins = wayOfPayment.getIs_active_confins();
                    String CreatedAt = wayOfPayment.getCreated_at();
                    String UpdatedAt = wayOfPayment.getUpdated_at();
                    String DeletedAt = wayOfPayment.getDeleted_at();

                    com.drife.digitaf.ORM.Database.WayOfPayment payment = new com.drife.digitaf.ORM.Database.WayOfPayment(WayOfPaymentId,
                            Code,Name,Description,CreatedBy,ModifiedBy,IsActive,IsActiveConfins,CreatedAt,UpdatedAt,DeletedAt);
                    payment.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertWOP","success insert WOP");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertWOP","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllWOP(){
        try {
            com.drife.digitaf.ORM.Database.WayOfPayment.deleteAll(com.drife.digitaf.ORM.Database.WayOfPayment.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllWOP","success clear WOP");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllWOP","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.WayOfPayment> getAllWOP(){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("WayOfPayment");
            List<com.drife.digitaf.ORM.Database.WayOfPayment> resultList =
                    com.drife.digitaf.ORM.Database.WayOfPayment.findWithQuery(com.drife.digitaf.ORM.Database.WayOfPayment.class, query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllWOP","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllWOP","Exception",e.getMessage());
            return null;
        }
    }
}
