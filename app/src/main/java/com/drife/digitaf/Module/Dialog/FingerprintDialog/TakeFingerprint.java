package com.drife.digitaf.Module.Dialog.FingerprintDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakeFingerprint extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public UserSession userSession;
    public boolean firstTime;

    @BindView(R.id.ivClose)
    ImageView ivClose;

    public TakeFingerprint(Activity a,UserSession userSession, boolean firstTime) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.userSession = userSession;
        this.firstTime = firstTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_request_fingerprint_takefingerprint);
        ButterKnife.bind(this);
        ivClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClose:
                if(firstTime){
                    if(userSession.getUser() != null){
                        SharedPreferenceUtils.saveSession(activity,userSession);
                        SharedPreferenceUtils.saveFingerprint(activity,"0");
                        Intent intent = new Intent(activity, Main.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }else{
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
                }
                dismiss();
                break;
            default:
                break;
        }
        //dismiss();
    }
}
