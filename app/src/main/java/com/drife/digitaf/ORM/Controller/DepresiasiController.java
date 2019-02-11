package com.drife.digitaf.ORM.Controller;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.retrofitmodule.Model.Depresiasi;

import java.util.List;

public class DepresiasiController {
    public static String TAG = DepresiasiController.class.getSimpleName();

    public static boolean bulkInsertDepresiasi(List<Depresiasi> depresiasis){
        try {
            if(depresiasis != null){
                for (int i=0; i<depresiasis.size(); i++){
                    Depresiasi depresiasi = depresiasis.get(i);
                    String DepresiasiId = depresiasi.getId();
                    /*String CategoryId = depresiasi.getCar_category().getId();
                    String CategoryCode = depresiasi.getCar_category().getCode();
                    String Category = depresiasi.getCar_category().getName();*/
                    String TenorId = depresiasi.getTenor().getId();
                    String TenorCode = depresiasi.getTenor().getCode();
                    String Tenor = depresiasi.getTenor().getCode();
                    String Percentage = depresiasi.getPercentage();

                    /*com.drife.digitaf.ORM.Database.Depresiasi dep = new com.drife.digitaf.ORM.Database.Depresiasi(DepresiasiId,
                            CategoryId,CategoryCode,Category,TenorId,TenorCode,Tenor,Percentage);*/
                    com.drife.digitaf.ORM.Database.Depresiasi dep = new com.drife.digitaf.ORM.Database.Depresiasi(DepresiasiId,
                            TenorId,TenorCode,Tenor,Percentage);
                    dep.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertDepresiasi","success insert depresiasi");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertDepresiasi","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllDepresiasi(){
        try {
            com.drife.digitaf.ORM.Database.Depresiasi.deleteAll(com.drife.digitaf.ORM.Database.Depresiasi.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllDepresiasi","success clear depresiasi");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllDepresiasi","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Depresiasi> getAllDepresiasi(){
        List<com.drife.digitaf.ORM.Database.Depresiasi> depresiasis = com.drife.digitaf.ORM.Database.Depresiasi.listAll(com.drife.digitaf.ORM.Database.Depresiasi.class);
        LogUtility.logging(TAG,LogUtility.infoLog,"getAllDepresiasi","getAllDepresiasi", JSONProcessor.toJSON(depresiasis));
        return depresiasis;
    }
}
