package com.drife.digitaf.Service.SyncMasterData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Loading.LoadingActivity;
import com.drife.digitaf.Module.Login.Activity.LoginActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.OnBoardActivity.OnBoardContainerActivity;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.ORM.Controller.DepresiasiController;
import com.drife.digitaf.ORM.Controller.MaritalStatusController;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Controller.ReligionController;
import com.drife.digitaf.ORM.Controller.TenorController;
import com.drife.digitaf.ORM.Controller.WayOfPaymentController;
import com.drife.digitaf.Service.Car.CarCallback;
import com.drife.digitaf.Service.Car.CarPresenter;
import com.drife.digitaf.Service.Depresiasi.DepresiasiCallback;
import com.drife.digitaf.Service.Depresiasi.DepresiasiPresenter;
import com.drife.digitaf.Service.Marital.MaritalCallback;
import com.drife.digitaf.Service.Marital.MaritalPresenter;
import com.drife.digitaf.Service.PackageRule.PackageRuleCallback;
import com.drife.digitaf.Service.PackageRule.PackageRulePresenter;
import com.drife.digitaf.Service.Religion.ReligionCallback;
import com.drife.digitaf.Service.Religion.ReligionPresenter;
import com.drife.digitaf.Service.Tenor.TenorCallback;
import com.drife.digitaf.Service.Tenor.TenorPresenter;
import com.drife.digitaf.Service.WayOfPayment.WayOfPaymentCallback;
import com.drife.digitaf.Service.WayOfPayment.WayOfPaymentPresenter;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.Depresiasi;
import com.drife.digitaf.retrofitmodule.Model.MaritalStatus;
import com.drife.digitaf.retrofitmodule.Model.PackageRule;
import com.drife.digitaf.retrofitmodule.Model.Religion;
import com.drife.digitaf.retrofitmodule.Model.Tenor;
import com.drife.digitaf.retrofitmodule.Model.WayOfPayment;

import java.util.Calendar;
import java.util.List;

public class SyncData {
    private static String TAG = SyncData.class.getSimpleName();
    private static Activity actv;
    private static ProgressDialog pDialog;

    private static void checkSession(Activity activity){
        UserSession userSession = SharedPreferenceUtils.getUserSession(activity);
        if(userSession != null){
            Intent intent = new Intent(activity, Main.class);
            activity.startActivity(intent);
            activity.finish();
        }else{
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public static void Sync(final Activity activity, ProgressDialog progressDialog){
        /*if(activity != null){
            context = activity.getApplicationContext();
        }*/

        if(progressDialog != null){
            pDialog = progressDialog;
        }

        getAllCars(activity);
    }

    /*get all car*/
    private static void getAllCars(final Activity activity){
        CarPresenter.getAllCars(activity, new CarCallback() {
            @Override
            public void onSuccessGetCarModel(List<CarModel> carModels) {
               /* if(activity.getClass().getSimpleName().equals(SplashScreenActivity.class.getSimpleName())){
                    actv = activity;
                    new bulkInsertCar().execute(carModels);
                }*/

                actv = activity;
                new bulkInsertCar().execute(carModels);
            }

            @Override
            public void onFailedGetCarModel(String message) {
                LogUtility.logging(TAG,LogUtility.errorLog,"Sync","onFailedGetCarModel",message);
                //pDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("Sync Failed")
                        .setMessage("Sync data master gagal, silahkan login dan sync kembali")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
                builder.show();
            }
        });
    }

    static class bulkInsertCar extends AsyncTask<List<CarModel>,Void,Void>{
        List<CarModel> carModels;
        @Override
        protected Void doInBackground(List<CarModel>... lists) {
            carModels = lists[0];
            if(carModels != null){
                CarController.bulkInsertCar(carModels);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new bulkInsertCarType().execute(carModels);
        }
    }

    static class bulkInsertCarType extends AsyncTask<List<CarModel>,Void,Void>{

        @Override
        protected Void doInBackground(List<CarModel>... lists) {
            List<CarModel> carModels = lists[0];
            if(carModels != null){
                CarController.bulkInsertCarType(carModels);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getAllTenor(actv);
        }
    }

    /* get all tenor */
    private static void getAllTenor(final Activity activity){
        TenorPresenter.getAllTenor(activity, new TenorCallback() {
            @Override
            public void onSuccessGetTenor(List<Tenor> tenors) {
                /*if(activity.getClass().getSimpleName().equals(SplashScreenActivity.class.getSimpleName())){
                    actv = activity;
                    new bulkInsertTenor().execute(tenors);
                }*/
                actv = activity;
                new bulkInsertTenor().execute(tenors);
            }

            @Override
            public void onFailedGetTenor(String message) {
                LogUtility.logging(TAG,LogUtility.errorLog,"getAllTenor","onFailedGetTenor",message);
                //pDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("Sync Failed")
                        .setMessage("Sync data master gagal, silahkan login dan sync kembali")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
                builder.show();
            }
        });
    }

    static class bulkInsertTenor extends AsyncTask<List<Tenor>,Void,Void>{
        List<Tenor> tenors;
        @Override
        protected Void doInBackground(List<Tenor>... lists) {
            tenors = lists[0];
            if(tenors != null){
                TenorController.bulkInsertTenor(tenors);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getAllDepresiasi(actv);
        }
    }

    /* get all depresiasi*/
    private static void getAllDepresiasi(final Activity activity){
        DepresiasiPresenter.getAllDepresiasi(activity, new DepresiasiCallback() {
            @Override
            public void onSuccessGetDepresiasi(List<Depresiasi> depresiasis) {
                /*if(activity.getClass().getSimpleName().equals(SplashScreenActivity.class.getSimpleName())){
                    actv = activity;
                    new bulkInsertDepresiasi().execute(depresiasis);
                }*/
                actv = activity;
                new bulkInsertDepresiasi().execute(depresiasis);
            }

            @Override
            public void onFailedGetDepresiasi(String message) {
                LogUtility.logging(TAG,LogUtility.errorLog,"getAllDepresiasi","onFailedGetDepresiasi",message);
                //pDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("Sync Failed")
                        .setMessage("Sync data master gagal, silahkan login dan sync kembali")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
                builder.show();
            }
        });
    }

    static class bulkInsertDepresiasi extends AsyncTask<List<Depresiasi>,Void,Void>{
        List<Depresiasi> depresiasis;
        @Override
        protected Void doInBackground(List<Depresiasi>... lists) {
            depresiasis = lists[0];
            if(depresiasis != null){
                DepresiasiController.bulkInsertDepresiasi(depresiasis);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //checkSession(actv);
            getAllPackageRule(actv);
            //pDialog.dismiss();
        }
    }

    /*get all package rule*/
    private static void getAllPackageRule(final Activity activity){

        PackageRulePresenter.getAllPackageRule(activity, new PackageRuleCallback() {
            @Override
            public void onSuccessGetPackageRule(List<PackageRule> packageRules) {
                /*if(activity.getClass().getSimpleName().equals(SplashScreenActivity.class.getSimpleName())){
                    actv = activity;
                    new bulkInsertPackageRule().execute(packageRules);
                }*/
                actv = activity;
                new bulkInsertPackageRule().execute(packageRules);
            }

            @Override
            public void onFailedGetModePackageRule(String message) {
                LogUtility.logging(TAG,LogUtility.errorLog,"getAllPackageRule","onFailedGetModePackageRule",message);
                //pDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("Sync Failed")
                        .setMessage("Sync data master gagal, silahkan login dan sync kembali")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
                builder.show();
            }
        });
    }

    static class bulkInsertPackageRule extends AsyncTask<List<PackageRule>,Void,Void>{
        List<PackageRule> packageRules;
        @Override
        protected Void doInBackground(List<PackageRule>... lists) {
            packageRules = lists[0];
            if(packageRules != null){
                PackageRuleController.bulkInsertPackageRule(packageRules);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //pDialog.dismiss();
            getWOP(actv);
        }
    }

    /*get wayofpayment*/
    private static void getWOP(final Activity activity){
        WayOfPaymentPresenter.getAllWOP(activity, new WayOfPaymentCallback() {
            @Override
            public void onSuccessGetWOP(List<WayOfPayment> wayOfPayments) {
                actv = activity;
                new bulkInsertWOP().execute(wayOfPayments);
            }

            @Override
            public void onFailedGetWOP(String message) {
                LogUtility.logging(TAG,LogUtility.errorLog,"getWOP","onFailedGetWOP",message);
                //pDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("Sync Failed")
                        .setMessage("Sync data master gagal, silahkan login dan sync kembali")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
                builder.show();
            }
        });
    }

    static class bulkInsertWOP extends AsyncTask<List<WayOfPayment>,Void,Void>{
        List<WayOfPayment> wayOfPayments;
        @Override
        protected Void doInBackground(List<WayOfPayment>... lists) {
            wayOfPayments = lists[0];
            if(wayOfPayments != null){
                WayOfPaymentController.bulkInsertWOP(wayOfPayments);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getReligion(actv);
        }
    }

    /*get wayofpayment*/
    private static void getReligion(final Activity activity){
        ReligionPresenter.getAllReligion(activity, new ReligionCallback() {
            @Override
            public void onSuccessGetReligion(List<Religion> religions) {
                actv = activity;
                new bulkInsertReligion().execute(religions);
            }

            @Override
            public void onFailedGetReligion(String message) {
                LogUtility.logging(TAG,LogUtility.errorLog,"getReligion","onFailedGetReligion",message);
                //pDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("Sync Failed")
                        .setMessage("Sync data master gagal, silahkan login dan sync kembali")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
                builder.show();
            }
        });
    }

    static class bulkInsertReligion extends AsyncTask<List<Religion>,Void,Void>{
        List<Religion> religions;
        @Override
        protected Void doInBackground(List<Religion>... lists) {
            religions = lists[0];
            if(religions != null){
                ReligionController.bulkInsertReligion(religions);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getMaritalStatus(actv);
        }
    }

    /*get wayofpayment*/
    private static void getMaritalStatus(final Activity activity){
        MaritalPresenter.getAllMarital(activity, new MaritalCallback() {
            @Override
            public void onSuccessGetMarital(List<MaritalStatus> wayOfPayments) {
                actv = activity;
                new bulkInsertMaritalStatus().execute(wayOfPayments);
            }

            @Override
            public void onFailedGetMarital(String message) {
                LogUtility.logging(TAG,LogUtility.errorLog,"getMaritalStatus","onFailedGetMarital",message);
                //pDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("Sync Failed")
                        .setMessage("Sync data master gagal, silahkan login dan sync kembali")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
                builder.show();
            }
        });
    }

    static class bulkInsertMaritalStatus extends AsyncTask<List<MaritalStatus>,Void,Void>{
        List<MaritalStatus> maritalStatuses;
        @Override
        protected Void doInBackground(List<MaritalStatus>... lists) {
            maritalStatuses = lists[0];
            if(maritalStatuses != null){
                MaritalStatusController.bulkInsertMaritalStatus(maritalStatuses);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //checkSession(actv);
            pDialog.dismiss();
            SharedPreferences preferences = actv.getSharedPreferences("sync",Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("sync","1");
            edit.commit();

            Calendar calendar = Calendar.getInstance();
            String cal = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);

            SharedPreferenceUtils.saveLastSync(actv,cal);
            boolean firstInstall = SharedPreferenceUtils.getFirstInstall(actv);

            if (firstInstall) {
                SharedPreferenceUtils.saveFirstInstall(actv,false);
                Intent intent = new Intent(actv, OnBoardContainerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                actv.startActivity(intent);
                actv.finish();
            } else {
                Intent intent = new Intent(actv, Main.class);
                actv.startActivity(intent);
                actv.finish();
            }

//            Intent intent = new Intent(actv, Main.class);
//            actv.startActivity(intent);
//            actv.finish();
        }
    }

}
