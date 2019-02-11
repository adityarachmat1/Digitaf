package com.drife.digitaf.Module.Dialog.FingerprintDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Loading.LoadingActivity;
import com.drife.digitaf.Module.Login.Activity.LoginActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.OnBoardActivity.OnBoardContainerActivity;
import com.drife.digitaf.Module.SplashScreen.Activity.SplashScreenActivity;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestFingerprintDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public UserSession userSession;

    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.lyLewati)
    LinearLayout lyLewati;
    @BindView(R.id.lyPakai)
    LinearLayout lyPakai;

    public RequestFingerprintDialog(Activity a, UserSession userSession) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.userSession = userSession;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_request_fingerprint);
        ButterKnife.bind(this);
        ivClose.setOnClickListener(this);
        lyLewati.setOnClickListener(this);
        lyPakai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyLewati:
                SharedPreferenceUtils.saveFingerprint(activity,"0");
                if(userSession.getUser() != null){
                    SharedPreferenceUtils.saveSession(activity,userSession);

                    if(userSession.getUser().getType().equals("so")){
                        Log.d("singo", "coverage insurance : "+ JSONProcessor.toJSON(userSession.getUser().getPv_insco()));
                        //CoverageInsuranceController.removeAllCoverage();
                        ((LoginActivity)activity).saveDealers(userSession);
                        CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getPv_insco());
                    }else if(userSession.getUser().getType().equals("sales")){
                        ((LoginActivity)activity).saveCoverageInsurancePV(userSession);
                    }

                    switchSync();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setTitle("Login Gagal")
                            .setMessage("Silahkan periksa kembali username dan password anda")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.show();
                }


                /*Intent intent = new Intent(activity, Main.class);
                activity.startActivity(intent);
                activity.finish();*/
                break;
            case R.id.lyPakai:
                /*SharedPreferenceUtils.saveSession(activity,userSession);
                SharedPreferenceUtils.saveFingerprint(activity,"1");*/
                /*intent = new Intent(activity, Main.class);
                activity.startActivity(intent);
                activity.finish();*/

                SharedPreferenceUtils.setFirstTime(activity,"1");
                TakeFingerprint dialog = new TakeFingerprint(activity,userSession,true);
                dialog.show();
                break;
            case R.id.ivClose:
                SharedPreferenceUtils.saveFingerprint(activity,"0");
//                intent = new Intent(activity, Main.class);
//                activity.startActivity(intent);
//                activity.finish();

                switchSync();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void switchSync(){
        SharedPreferences preferences = activity.getSharedPreferences("sync",activity.MODE_PRIVATE);
        String stat = preferences.getString("sync", "");

        if(stat.equals("") || stat.equals("0")){
            Intent intent = new Intent(activity, LoadingActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }else{
            Intent intent = new Intent(activity, Main.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
