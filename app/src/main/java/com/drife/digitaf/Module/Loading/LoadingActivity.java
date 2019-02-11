package com.drife.digitaf.Module.Loading;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.PermissionUtility.PermissionUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Login.Activity.LoginActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Controller.DepresiasiController;
import com.drife.digitaf.ORM.Controller.MaritalStatusController;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Controller.ReligionController;
import com.drife.digitaf.ORM.Controller.TenorController;
import com.drife.digitaf.ORM.Controller.WayOfPaymentController;
import com.drife.digitaf.ORM.Database.WayOfPayment;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.SyncMasterData.SyncData;
import com.drife.digitaf.UIModel.UserSession;
import com.orm.SugarContext;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.imgLoading)
    public ImageView imgLoading;
    @BindView(R.id.pbLoading)
    public ProgressBar pbLoading;

    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        SugarContext.init(LoadingActivity.this);
        initVariables();
        callFunctions();
    }

    private void initVariables() {
        imgLoading.setBackgroundResource(R.drawable.animation_loading);
        animationDrawable = (AnimationDrawable)imgLoading.getBackground();
        animationDrawable.start();
    }

    private void callFunctions(){
        if(Build.VERSION.SDK_INT>= 23) {
            PermissionUtility.permissionHandling(this, new PermissionUtility.PermissionsCallback() {
                @Override
                public void onRequestGranted() {
                    switchSync();
                }
            });
        } else {
            switchSync();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length == 4) {
                switchSync();
            }
        }
    }

    private void switchSync(){
        SharedPreferences preferences = getSharedPreferences("sync",MODE_PRIVATE);
        String stat = preferences.getString("sync", "");

        /*if(stat.equals("") || stat.equals("0")){
//            UserSession userSession = SharedPreferenceUtils.getUserSession(this);
//            if (userSession.getUser() != null) {
//                CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getDealer().getPv_insco());
//            }
            new removeMaster().execute();
        }else{
//            UserSession userSession = SharedPreferenceUtils.getUserSession(this);
//            if (userSession.getUser() != null) {
//                CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getDealer().getPv_insco());
//            }

            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
            finish();
        }*/

        new removeMaster().execute();
    }

    class removeMaster extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //CoverageInsuranceController.removeAllCoverage();
            CarController.removeAllCar();
            CarController.removeAllCarType();
            TenorController.removeAllTenor();
            DepresiasiController.removeAllDepresiasi();
            PackageRuleController.removeAllPackageRule();
            WayOfPaymentController.removeAllWOP();
            ReligionController.removeAllReligion();
            MaritalStatusController.removeAllMaritalStatus();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TimerTask task = new TimerTask() {
                public void run() {
                    syncData();
                }
            };
            Timer timer = new Timer("Timer");

            long delay = 100;
            timer.schedule(task, delay);
        }
    }

    private void syncData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                   SyncData.Sync(LoadingActivity.this,new ProgressDialog(LoadingActivity.this));
                }catch (Exception e){

                }
            }
        });
    }

    private boolean compareLastSync(){
        boolean status = true;
        Calendar now = Calendar.getInstance();
        String lastSync = SharedPreferenceUtils.getLastSync(LoadingActivity.this);
        if(lastSync != null){
            Log.d("singo", "Compare date : null");
            String[] sync = lastSync.split("-");
            Calendar last = Calendar.getInstance();
            last.set(Integer.parseInt(sync[0]),Integer.parseInt(sync[1]),Integer.parseInt(sync[2]));
            Log.d("singo", "Compare date : "+last.compareTo(now));

            if(last.compareTo(now)<0){
                status = false;
            }
        }else{
            status = false;
            Log.d("singo", "Compare date : not null");
        }
        return status;
    }
}
