package com.drife.digitaf.ORM.Controller;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.retrofitmodule.Model.Religion;
import com.orm.util.NamingHelper;

import java.util.List;

public class ReligionController {
    private static String TAG = ReligionController.class.getSimpleName();

    public static boolean bulkInsertReligion(List<Religion> wayOfPayments){
        try {
            if(wayOfPayments != null){
                for (int i=0; i<wayOfPayments.size(); i++){
                    Religion wayOfPayment = wayOfPayments.get(i);
                    String ReligionId = wayOfPayment.getId();
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

                    com.drife.digitaf.ORM.Database.Religion payment = new com.drife.digitaf.ORM.Database.Religion(ReligionId,
                            Code,Name,Description,CreatedBy,ModifiedBy,IsActive,IsActiveConfins,CreatedAt,UpdatedAt,DeletedAt);
                    payment.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertReligion","success insert Religion");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertReligion","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllReligion(){
        try {
            com.drife.digitaf.ORM.Database.Religion.deleteAll(com.drife.digitaf.ORM.Database.Religion.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllReligion","success clear Religion");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllReligion","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Religion> getAllReligion(){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("Religion")+" where is_active = 1 AND is_active_confins = 1";
            List<com.drife.digitaf.ORM.Database.Religion> resultList =
                    com.drife.digitaf.ORM.Database.Religion.findWithQuery(com.drife.digitaf.ORM.Database.Religion.class, query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllReligion","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllReligion","Exception",e.getMessage());
            return null;
        }
    }
}
