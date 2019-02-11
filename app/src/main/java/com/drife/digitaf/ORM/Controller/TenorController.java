package com.drife.digitaf.ORM.Controller;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.retrofitmodule.Model.Tenor;
import com.orm.util.NamingHelper;

import java.util.List;

public class TenorController {
    public static String TAG = TenorController.class.getSimpleName();

    public static boolean bulkInsertTenor(List<Tenor> tenors){
        try {
            if(tenors != null){
                for (int i=0; i<tenors.size(); i++){
                    Tenor tenor = tenors.get(i);
                    String IdTenor = tenor.getId();
                    String Code = tenor.getCode();
                    String CreatedBy = tenor.getCreated_by();
                    String ModifiedBy = tenor.getModified_by();
                    String IsActive = tenor.getIs_active();
                    String IsActiveConfins = tenor.getIs_active_confins();
                    String CreatedAt = tenor.getCreated_at();
                    String UpdatedAt = tenor.getUpdated_at();
                    String DeletedAt = tenor.getDeleted_at();

                    com.drife.digitaf.ORM.Database.Tenor ten = new com.drife.digitaf.ORM.Database.Tenor(IdTenor,
                            Code,CreatedBy,ModifiedBy,IsActive,IsActiveConfins,CreatedAt,UpdatedAt,DeletedAt);
                    ten.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertTenor","success insert tenor");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertTenor","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllTenor(){
        try {
            com.drife.digitaf.ORM.Database.Tenor.deleteAll(com.drife.digitaf.ORM.Database.Tenor.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllTenor","success clear tenor");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllTenor","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Tenor> getAllTenor(){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("Tenor");
            List<com.drife.digitaf.ORM.Database.Tenor> resultList =
                    com.drife.digitaf.ORM.Database.Tenor.findWithQuery(com.drife.digitaf.ORM.Database.Tenor.class, query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllTenor","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllTenor","Exception",e.getMessage());
            return null;
        }
    }
}
