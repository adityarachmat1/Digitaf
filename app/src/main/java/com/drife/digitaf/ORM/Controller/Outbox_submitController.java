package com.drife.digitaf.ORM.Controller;

import android.util.Log;

import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.ORM.Database.Outbox_submit;
import com.orm.util.NamingHelper;

import java.util.List;

public class Outbox_submitController {
    public static String TAG = Outbox_submitController.class.getSimpleName();

    public static boolean InsertOutboxSubmit(List<Outbox_submit> outbox_submits){
        try {
            if(outbox_submits != null){
                for (int i=0; i<outbox_submits.size(); i++){
                    Outbox_submit outbox_submit = outbox_submits.get(i);
                    String OutboxId = outbox_submit.getOutboxId();
                    String Form = outbox_submit.getForm();
                    String Attach = outbox_submit.getAttach();
                    String CustomName = outbox_submit.getCustomerName();
                    String Date = outbox_submit.getDate();
                    String Status = outbox_submit.getStatus();
                    String User_id = outbox_submit.getUser_id();
                    String AttachPDF = outbox_submit.getAttachPDF();
                    String Dp_percentage = outbox_submit.getDp_percentage();

                    Log.d(TAG, "Att pdf "+AttachPDF);
                    Log.d(TAG, "UserId "+User_id);

//                    com.drife.digitaf.ORM.Database.Outbox_submit outboxSubmit = new com.drife.digitaf.ORM.Database.Outbox_submit(OutboxId,
//                            Form,Attach,CustomName,Date, Status);

                    com.drife.digitaf.ORM.Database.Outbox_submit outboxSubmit = new com.drife.digitaf.ORM.Database.Outbox_submit(OutboxId,
                            //Form,Attach,CustomName,Date, Status, User_id);
                            Form,Attach,CustomName,Date, Status, User_id, AttachPDF, Dp_percentage);
                    outboxSubmit.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"InsertOutboxSubmit","success InsertOutboxSubmit");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"InsertOutboxSubmit","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Outbox_submit> getOutboxsumbit(){
        try {
            List<com.drife.digitaf.ORM.Database.Outbox_submit> outboxSubmits = com.drife.digitaf.ORM.Database.Outbox_submit.listAll(com.drife.digitaf.ORM.Database.Outbox_submit.class);
            return outboxSubmits;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getOutbbox","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Outbox_submit> getOutboxId(String OutboxId, String Status){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("Outbox_submit")+" where " +
                    NamingHelper.toSQLNameDefault("OutboxId")+" = '"+OutboxId+"'" +" and " +
                    NamingHelper.toSQLNameDefault("Status")+" = '"+Status+"'";
            List<com.drife.digitaf.ORM.Database.Outbox_submit> resultList =
                    com.drife.digitaf.ORM.Database.Outbox_submit.findWithQuery(com.drife.digitaf.ORM.Database.Outbox_submit.class,query);
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getName","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Outbox_submit> delete_outbox(String Form){
        try {
            String query = "delete from "+ NamingHelper.toSQLNameDefault("Outbox_submit")+" where " +
                    NamingHelper.toSQLNameDefault("Form")+" = '"+Form+"'";
            List<com.drife.digitaf.ORM.Database.Outbox_submit> resultList =
                    com.drife.digitaf.ORM.Database.Outbox_submit.findWithQuery(com.drife.digitaf.ORM.Database.Outbox_submit.class,query);
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"deleteName","Exception",e.getMessage());
            return null;
        }
    }
}
