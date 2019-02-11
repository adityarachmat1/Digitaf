package com.drife.digitaf.GeneralUtility.SharedPreferenceUtil;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.Model.Area;
import com.drife.digitaf.retrofitmodule.Model.Branch;
import com.drife.digitaf.retrofitmodule.Model.Brand;
import com.drife.digitaf.retrofitmodule.Model.Dealer;
import com.drife.digitaf.retrofitmodule.Model.Group;
import com.drife.digitaf.retrofitmodule.Model.InsuranceArea;
import com.drife.digitaf.retrofitmodule.Model.PCLInsco;
import com.drife.digitaf.retrofitmodule.Model.PCLPremi;
import com.drife.digitaf.retrofitmodule.Model.PVInsco;
import com.drife.digitaf.retrofitmodule.Model.Profile;
import com.drife.digitaf.retrofitmodule.Model.RateArea;
import com.drife.digitaf.retrofitmodule.Model.ResponseConfins;
import com.drife.digitaf.retrofitmodule.Model.User;

public class SharedPreferenceUtils {
    private static String TAG = SharedPreferenceUtils.class.getSimpleName();
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mSharedPreferencesEditor;

    /*user data*/
    public static final String USER_PREFERENCE = "user_session";
    public static final String USER_FINGERPRINT = "user_fingerprint";

    private static String Token = "token";
    private static String Email = "email";
    private static String Image = "image";
    private static String Name = "name";
    private static String UserType = "user_type";
    private static String ManagerName = "manager_name";
    private static String Position = "position";
    private static String Npk = "npk";
    private static String Nik = "nik";
    private static String DealerName = "dealer_name";
    private static String DealerCode = "dealer_code";
    private static String DealerId = "dealer_id";
    private static String DealerGroupName = "dealer_group_name";
    private static String DealerGroupCode = "dealer_group_code";
    private static String DealerGroupId = "dealer_group_id";
    private static String BranchName = "branch_name";
    private static String BranchCode = "branch_code";
    private static String BranchId = "branch_id";
    private static String AreaName = "area_name";
    private static String AreaCode = "area_code";
    private static String AreaId = "area_id";
    private static String InsuranceAreaName = "insurance_area_name";
    private static String InsuranceAreaCode = "insurance_area_code";
    private static String InsuranceAreaId = "insurance_area_id";
    private static String TAVPInsco = "tavp_insco";
    private static String TAVPInscoCode = "tavp_insco_code";
    private static String TAVPInscoId = "tavp_insco_id";
    private static String PCLInsco = "pcl_insco";
    private static String PCLInscoCode = "pcl_insco_code";
    private static String PCLInscoId = "pcl_insco_id";
    private static String PCLPremi = "pcl_premi";
    private static String InsuranceGroupName = "insurance_group_name";
    private static String InsuranceGroupCode =  "insurance_group_code";
    private static String InsuranceGroupId = "insurance_group_id";
    private static String BrandName = "brand_name";
    private static String BrandCode = "brand_code";
    private static String BrandId = "brand_id";
    private static String IdUser = "IdUser";

    private static String UseFingerprint = "use_fingerprint"; //0|1; 1==yes
    private static String IsFirstTime = "first_time";//0|1; 1==yes

    private static String EmpNo = "emp_no"; //0|1; 1==yes
    private static String DealerNameConfins = "dealer_name_confins";//0|1; 1==yes

    /*sync status flag*/
    private static String SYNC_STATUS_PREFERENCE = "sync_status";
    private static String SYNC_STATUS = "sync_status";
    private static String LAST_SYNC = "last_sync";
    private static String FIRSTINSTALL_PREFERENCE = "first_install";
    private static String FIRSTTUTORIAL_PREFERENCE = "first_tutorial";

    /*boolean value*/
    public static boolean IS_EXIST = true;
    public static boolean IS_NOT_EXIST = false;

    /*save to preference*/
    public static boolean saveSession(Context context, UserSession userSession){
        LogUtility.logging(TAG,LogUtility.infoLog,"saveSession","userSession",JSONProcessor.toJSON(userSession));
        try {
            mSharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putString(Token,userSession.getToken());
            mSharedPreferencesEditor.putString(Email,userSession.getUser().getEmail());
            mSharedPreferencesEditor.putString(Image,userSession.getUser().getImage());

            if (userSession.getUser().getType().equals("so")) {
                mSharedPreferencesEditor.putString(EmpNo,userSession.getUser().getResponseConfins().getEmployeeNo());
                mSharedPreferencesEditor.putString(DealerNameConfins,userSession.getUser().getResponseConfins().getBranchName());
            } else {
                mSharedPreferencesEditor.putString(DealerNameConfins,userSession.getUser().getResponseConfins().getDealerName());
            }

            mSharedPreferencesEditor.putString(Name,userSession.getUser().getProfile().getFullname());
            mSharedPreferencesEditor.putString(UserType,userSession.getUser().getType());
            mSharedPreferencesEditor.putString(ManagerName,"");
            mSharedPreferencesEditor.putString(Position,"");
            mSharedPreferencesEditor.putString(Npk,userSession.getUser().getNpk_no());
            mSharedPreferencesEditor.putString(Nik,userSession.getUser().getKtp_no());
            mSharedPreferencesEditor.putString(DealerId,userSession.getUser().getDealer().getId());
            mSharedPreferencesEditor.putString(IdUser, userSession.getUser().getId());
            if(!userSession.getUser().getDealer().getId().equals("0")){
                mSharedPreferencesEditor.putString(DealerName,userSession.getUser().getDealer().getName());
                mSharedPreferencesEditor.putString(DealerCode,userSession.getUser().getDealer().getCode());
                mSharedPreferencesEditor.putString(DealerGroupName,userSession.getUser().getDealer().getGroup().getName());
                mSharedPreferencesEditor.putString(DealerGroupCode,userSession.getUser().getDealer().getGroup().getCode());
                mSharedPreferencesEditor.putString(DealerGroupId,userSession.getUser().getDealer().getGroup().getId());
                mSharedPreferencesEditor.putString(BranchName,userSession.getUser().getDealer().getBranch().getName());
                mSharedPreferencesEditor.putString(BranchCode,userSession.getUser().getDealer().getBranch().getCode());
                mSharedPreferencesEditor.putString(BranchId,userSession.getUser().getDealer().getBranch().getId());
                mSharedPreferencesEditor.putString(AreaName,userSession.getUser().getDealer().getBranch().getRate_area().getName());
                mSharedPreferencesEditor.putString(AreaCode,userSession.getUser().getDealer().getBranch().getRate_area().getCode());
                mSharedPreferencesEditor.putString(AreaId,userSession.getUser().getDealer().getBranch().getRate_area().getId());
                mSharedPreferencesEditor.putString(InsuranceAreaName,userSession.getUser().getDealer().getBranch().getInsurance_area().getName());
                mSharedPreferencesEditor.putString(InsuranceAreaCode,userSession.getUser().getDealer().getBranch().getInsurance_area().getCode());
                mSharedPreferencesEditor.putString(InsuranceAreaId,userSession.getUser().getDealer().getBranch().getInsurance_area().getId());
                mSharedPreferencesEditor.putString(TAVPInsco,userSession.getUser().getDealer().getPv_insco().getName());
                mSharedPreferencesEditor.putString(TAVPInscoCode,userSession.getUser().getDealer().getPv_insco().getCode());
                mSharedPreferencesEditor.putString(TAVPInscoId,userSession.getUser().getDealer().getPv_insco().getId());
                mSharedPreferencesEditor.putString(PCLInsco,userSession.getUser().getDealer().getPcl_insco().getName());
                mSharedPreferencesEditor.putString(PCLInscoCode,userSession.getUser().getDealer().getPcl_insco().getCode());
                mSharedPreferencesEditor.putString(PCLInscoId,userSession.getUser().getDealer().getPcl_insco().getId());
                mSharedPreferencesEditor.putString(PCLPremi,userSession.getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value());
                mSharedPreferencesEditor.putString(InsuranceGroupName,"");
                mSharedPreferencesEditor.putString(InsuranceGroupCode,"");
                mSharedPreferencesEditor.putString(InsuranceGroupId,"");
                //Log.d("singo", "name : "+userSession.getUser().getDealer().getBrand().getName());
                mSharedPreferencesEditor.putString(BrandName,userSession.getUser().getDealer().getBrand().getName());
                mSharedPreferencesEditor.putString(BrandCode,userSession.getUser().getDealer().getBrand().getCode());
                mSharedPreferencesEditor.putString(BrandId,userSession.getUser().getDealer().getBrand().getId());
            }else{
                mSharedPreferencesEditor.putString(AreaName,userSession.getUser().getBranch().getRate_area().getName());
                mSharedPreferencesEditor.putString(AreaCode,userSession.getUser().getBranch().getRate_area().getCode());
                mSharedPreferencesEditor.putString(AreaId,userSession.getUser().getBranch().getRate_area().getId());
                mSharedPreferencesEditor.putString(InsuranceAreaName,userSession.getUser().getBranch().getInsurance_area().getName());
                mSharedPreferencesEditor.putString(InsuranceAreaCode,userSession.getUser().getBranch().getInsurance_area().getCode());
                mSharedPreferencesEditor.putString(InsuranceAreaId,userSession.getUser().getBranch().getInsurance_area().getId());
                mSharedPreferencesEditor.putString(BranchName,userSession.getUser().getBranch().getName());
                mSharedPreferencesEditor.putString(BranchCode,userSession.getUser().getBranch().getCode());
                mSharedPreferencesEditor.putString(BranchId,userSession.getUser().getBranch().getId());
            }

            mSharedPreferencesEditor.commit();
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"saveSession",e.getMessage());
            return false;
        }
    }

    /*get session*/
    public static UserSession getUserSession(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
            UserSession userSession = new UserSession();
            if(mSharedPreferences.getString(Email,"") == null || mSharedPreferences.getString(Email,"").equals("")){
                return null;
            }else{
                userSession.setToken(mSharedPreferences.getString(Token,""));
                User user = new User();
                user.setEmail(mSharedPreferences.getString(Email,""));
                user.setImage(mSharedPreferences.getString(Image,""));
                user.setId(mSharedPreferences.getString(IdUser,""));

                Profile profile = new Profile();
                profile.setFullname(mSharedPreferences.getString(Name,""));
                user.setProfile(profile);

                user.setType(mSharedPreferences.getString(UserType,""));
                //user.setEmail(mSharedPreferences.getString(ManagerName,""));
                //user.setEmail(mSharedPreferences.getString(Position,""));
                user.setNpk_no(mSharedPreferences.getString(Npk,""));
                user.setKtp_no(mSharedPreferences.getString(Nik,""));

                Dealer dealer = new Dealer();
                dealer.setName(mSharedPreferences.getString(DealerName,""));
                dealer.setCode(mSharedPreferences.getString(DealerCode,""));
                dealer.setId(mSharedPreferences.getString(DealerId,""));

                Group group = new Group();
                group.setName(mSharedPreferences.getString(DealerGroupName,""));
                group.setCode(mSharedPreferences.getString(DealerGroupCode,""));
                group.setId(mSharedPreferences.getString(DealerGroupId,""));
                dealer.setGroup(group);

                Branch branch = new Branch();
                branch.setName(mSharedPreferences.getString(BranchName,""));
                branch.setCode(mSharedPreferences.getString(BranchCode,""));
                branch.setId(mSharedPreferences.getString(BranchId,""));
                dealer.setBranch(branch);

                RateArea rateArea = new RateArea();
                rateArea.setName(mSharedPreferences.getString(AreaName,""));
                rateArea.setCode(mSharedPreferences.getString(AreaCode,""));
                rateArea.setId(mSharedPreferences.getString(AreaId,""));

                InsuranceArea insuranceArea = new InsuranceArea();
                insuranceArea.setName(mSharedPreferences.getString(InsuranceAreaName,""));
                insuranceArea.setCode(mSharedPreferences.getString(InsuranceAreaCode,""));
                insuranceArea.setId(mSharedPreferences.getString(InsuranceAreaId,""));
                branch.setRate_area(rateArea);
                branch.setInsurance_area(insuranceArea);
                dealer.setBranch(branch);

                PVInsco pvInsco = new PVInsco();
                pvInsco.setName(mSharedPreferences.getString(TAVPInsco,""));
                pvInsco.setCode(mSharedPreferences.getString(TAVPInscoCode,""));
                pvInsco.setId(mSharedPreferences.getString(TAVPInscoId,""));
                dealer.setPv_insco(pvInsco);

                com.drife.digitaf.retrofitmodule.Model.PCLInsco pcl = new PCLInsco();
                pcl.setName(mSharedPreferences.getString(PCLInsco,""));
                pcl.setCode(mSharedPreferences.getString(PCLInscoCode,""));
                pcl.setId(mSharedPreferences.getString(PCLInscoId,""));

                com.drife.digitaf.retrofitmodule.Model.PCLPremi premipcl = new PCLPremi();
                premipcl.setPremi_value(mSharedPreferences.getString(PCLPremi,""));
                pcl.setPcl_premi(premipcl);
                dealer.setPcl_insco(pcl);

                Log.d(TAG, "Coba "+mSharedPreferences.getString(DealerNameConfins, ""));

                ResponseConfins responseConfins = new ResponseConfins();
                if (user.getType().equals("so")) {
                    responseConfins.setEmployeeNo(mSharedPreferences.getString(EmpNo, ""));
                    responseConfins.setBranchName(mSharedPreferences.getString(DealerNameConfins, ""));
                } else {
                    responseConfins.setDealerName(mSharedPreferences.getString(DealerNameConfins, ""));
                }

                user.setResponseConfins(responseConfins);

                Brand brand = new Brand();
                if(user.getType().equals("so")){

                }else{
                    brand.setName(mSharedPreferences.getString(BrandName,""));
                    brand.setCode(mSharedPreferences.getString(BrandCode,""));
                    brand.setId(mSharedPreferences.getString(BrandId,""));
                }
                dealer.setBrand(brand);

                //user.setEmail(mSharedPreferences.getString(InsuranceGroupName,""));
                //user.setEmail(mSharedPreferences.getString(InsuranceGroupCode,""));
                //user.setEmail(mSharedPreferences.getString(InsuranceGroupId,""));

                user.setDealer(dealer);
                userSession.setUser(user);
                return userSession;
            }
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getUserSession","Exception", e.getMessage());
            return null;
        }
    }

    /*save to preference*/
    public static boolean saveFingerprint(Context context, String status){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_FINGERPRINT, Context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putString(UseFingerprint,status);
            mSharedPreferencesEditor.commit();
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"saveFingerprint",e.getMessage());
            return false;
        }
    }

    public static String getFingerprint(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_FINGERPRINT, Context.MODE_PRIVATE);
            String fingerprint = "";
            if(mSharedPreferences.getString(UseFingerprint,"") == null || mSharedPreferences.getString(UseFingerprint,"").equals("")){
                return null;
            }else{
                fingerprint = mSharedPreferences.getString(UseFingerprint,"");
                return fingerprint;
            }
        }catch (Exception e){
            return null;
        }
    }

    public static boolean setFirstTime(Context context, String status){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_FINGERPRINT, Context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putString(IsFirstTime,status);
            mSharedPreferencesEditor.commit();
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"setFirstTime",e.getMessage());
            return false;
        }
    }

    public static String getFirstTime(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_FINGERPRINT, Context.MODE_PRIVATE);
            String firsttime = "";
            if(mSharedPreferences.getString(IsFirstTime,"") == null || mSharedPreferences.getString(IsFirstTime,"").equals("")){
                return null;
            }else{
                firsttime = mSharedPreferences.getString(IsFirstTime,"");
                return firsttime;
            }
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getFirstTime",e.getMessage());
            return null;
        }
    }

    public static boolean removeSession(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putString(Email,"");

            mSharedPreferencesEditor.commit();
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeSession",e.getMessage());
            return false;
        }
    }

    public static boolean setToken(Context context, String token){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putString(Token,token);
            mSharedPreferencesEditor.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean removeToken(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putString(Token,"");
            mSharedPreferencesEditor.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static String getToken(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
            String token = "";
            if(mSharedPreferences.getString(Token,"") == null || mSharedPreferences.getString(Token,"").equals("")){
                return null;
            }else{
                token = mSharedPreferences.getString(Token,"");
                return token;
            }
        }catch (Exception e){
            return null;
        }
    }

    /*save sync status*/
    public static boolean saveLastSync(Context context, String lastSync){
        try {
            mSharedPreferences = context.getSharedPreferences(SYNC_STATUS_PREFERENCE,context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putString(LAST_SYNC,lastSync);
            mSharedPreferencesEditor.commit();
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"saveSyncStatus",e.getMessage());
            return false;
        }
    }

    /*get sync status*/
    public static String getLastSync(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(SYNC_STATUS_PREFERENCE,context.MODE_PRIVATE);
            String status = mSharedPreferences.getString(LAST_SYNC,"");
            return status;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getLastSync",e.getMessage());
            return null;
        }
    }

    /*save sync status*/
    public static void saveFirstInstall(Context context, boolean firstInstall){
        try {
            mSharedPreferences = context.getSharedPreferences(FIRSTINSTALL_PREFERENCE,context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putBoolean(FIRSTINSTALL_PREFERENCE,firstInstall);
            mSharedPreferencesEditor.commit();
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"saveFirstInstall",e.getMessage());
        }
    }

    /*get sync status*/
    public static boolean getFirstInstall(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(FIRSTINSTALL_PREFERENCE,context.MODE_PRIVATE);
            boolean status = mSharedPreferences.getBoolean(FIRSTINSTALL_PREFERENCE,true);
            return status;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getFirstInstall",e.getMessage());
            return false;
        }
    }

    /*save sync status*/
    public static void saveFirstTutorial(Context context, boolean firstInstall){
        try {
            mSharedPreferences = context.getSharedPreferences(FIRSTTUTORIAL_PREFERENCE,context.MODE_PRIVATE);
            mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putBoolean(FIRSTTUTORIAL_PREFERENCE,firstInstall);
            mSharedPreferencesEditor.commit();
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"saveFirstTutorial",e.getMessage());
        }
    }

    /*get sync status*/
    public static boolean getFirstTutorial(Context context){
        try {
            mSharedPreferences = context.getSharedPreferences(FIRSTTUTORIAL_PREFERENCE,context.MODE_PRIVATE);
            boolean status = mSharedPreferences.getBoolean(FIRSTTUTORIAL_PREFERENCE,true);
            return status;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getFirstTutorial",e.getMessage());
            return false;
        }
    }
}