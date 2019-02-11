package com.drife.digitaf.ORM.Controller;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.retrofitmodule.Model.MaritalStatus;
import com.orm.util.NamingHelper;

import java.util.List;

public class MaritalStatusController {
    private static String TAG = MaritalStatusController.class.getSimpleName();

    public static boolean bulkInsertMaritalStatus(List<MaritalStatus> wayOfPayments){
        try {
            if(wayOfPayments != null){
                for (int i=0; i<wayOfPayments.size(); i++){
                    MaritalStatus wayOfPayment = wayOfPayments.get(i);
                    String MaritalStatusId = wayOfPayment.getId();
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

                    com.drife.digitaf.ORM.Database.MaritalStatus payment = new com.drife.digitaf.ORM.Database.MaritalStatus(MaritalStatusId,
                            Code,Name,Description,CreatedBy,ModifiedBy,IsActive,IsActiveConfins,CreatedAt,UpdatedAt,DeletedAt);
                    payment.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertMaritalStatus","success insert MaritalStatus");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertMaritalStatus","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllMaritalStatus(){
        try {
            com.drife.digitaf.ORM.Database.MaritalStatus.deleteAll(com.drife.digitaf.ORM.Database.MaritalStatus.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllMaritalStatus","success clear MaritalStatus");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllMaritalStatus","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.MaritalStatus> getAllMaritalStatus(){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("MaritalStatus")+" where is_active = 1 AND is_active_confins = 1";
            List<com.drife.digitaf.ORM.Database.MaritalStatus> resultList =
                    com.drife.digitaf.ORM.Database.MaritalStatus.findWithQuery(com.drife.digitaf.ORM.Database.MaritalStatus.class, query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllMaritalStatus","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllMaritalStatus","Exception",e.getMessage());
            return null;
        }
    }
}
