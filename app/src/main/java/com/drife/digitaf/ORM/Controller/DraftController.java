package com.drife.digitaf.ORM.Controller;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.ORM.Database.Draft;
import com.orm.query.Select;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

public class DraftController {
    public static String TAG = DraftController.class.getSimpleName();

    public static boolean InsertDraft(List<Draft> drafts){
        try {
            if(drafts != null){
                for (int i=0; i<drafts.size(); i++){
                    Draft draft = drafts.get(i);
                    String CustomerId = draft.getCustomerId();
                    String NameCustomer = draft.getNamaCustomer();
                    String LastSaved = draft.getLastSaved();
                    String Data = draft.getData();
                    Boolean Npwp = draft.getNpwp();
                    String isNonPaket = draft.getIsNonPaket();
                    int paymenttype = draft.getPaymentType();
                    String pokokHutangAkhir = draft.getPokokHutangAkhir();
                    String otr = draft.getOtr();
                    String premiAmount = draft.getPvPremiAmount();
                    String user_id = draft.getUser_id();
                    String is_corporate = draft.getIs_corporate();
                    String is_simulasi_paket = draft.getIs_simulasi_paket();
                    String is_simulasi_budget = draft.getIs_simulasi_budget();
                    String is_non_paket = draft.getIs_non_paket();
                    String is_npwp = draft.getIs_npwp();
                    String payment_type_service_package = draft.getPayment_type_service_package();
                    String coverage_type = draft.getCoverage_type();

                    com.drife.digitaf.ORM.Database.Draft draft1 = new com.drife.digitaf.ORM.Database.Draft(CustomerId,
                            NameCustomer,LastSaved,Data, Npwp, isNonPaket, paymenttype, pokokHutangAkhir, otr, premiAmount, user_id, is_corporate,
                            is_simulasi_paket, is_simulasi_budget, is_non_paket, is_npwp, payment_type_service_package, coverage_type);
                    draft1.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"InsertDraft","success InsertOutboxSubmit");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"InsertDraft","Exception",e.getMessage());
            return false;
        }
    }

    public static List<Draft> getDraftAll(String CustomerId){
        List<Draft> draftList = new ArrayList<>();
        Select select = Select.from(com.drife.digitaf.ORM.Database.Draft.class)
                .where(NamingHelper.toSQLNameDefault("CustomerId")+" = "+"'"+CustomerId+"'");

        List<com.drife.digitaf.ORM.Database.Draft> resultList = select.list();

        for (int i=0; i<resultList.size(); i++){
            String categoryGroupId = resultList.get(i).getCustomerId();
            draftList.add(resultList.get(i));
        }
        LogUtility.logging(TAG,LogUtility.infoLog,"getDraft","getDraft", JSONProcessor.toJSON(draftList));

        return draftList;
    }

    public static List<com.drife.digitaf.ORM.Database.Draft> getAllDraft(){
        try {
            List<com.drife.digitaf.ORM.Database.Draft> draftList = com.drife.digitaf.ORM.Database.Draft.listAll(com.drife.digitaf.ORM.Database.Draft.class);
            return draftList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllDraft","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Draft> getDraftName(String CustomerId) {
        try {
            String query = "select * from " + NamingHelper.toSQLNameDefault("Draft") + " where " +
                    NamingHelper.toSQLNameDefault("CustomerId") + " = '" + CustomerId + "'";
            List<com.drife.digitaf.ORM.Database.Draft> resultList =
                    com.drife.digitaf.ORM.Database.Draft.findWithQuery(com.drife.digitaf.ORM.Database.Draft.class, query);
            return resultList;
        } catch (Exception e) {
            LogUtility.logging(TAG, LogUtility.errorLog, "getDraftName", "Exception", e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.Draft> searchDate(String LastSaved1, String LastSaved2){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("Draft")+" where " +
                    NamingHelper.toSQLNameDefault("LastSaved")+" BETWEEN '"+LastSaved1+"'"+" AND '"+LastSaved2+"'";
            List<com.drife.digitaf.ORM.Database.Draft> resultList =
                    com.drife.digitaf.ORM.Database.Draft.findWithQuery(com.drife.digitaf.ORM.Database.Draft.class,query);
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"searchDate","Exception",e.getMessage());
            return null;
        }
    }

}
