package com.drife.digitaf.Module.Dialog.FingerprintDialog;

import android.app.Activity;
import android.app.Dialog;
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

public class RequestFingerprintPermissionDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;

    @BindView(R.id.lyMengerti)
    LinearLayout lyMengerti;

    public RequestFingerprintPermissionDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_request_fingerprint_permission);
        ButterKnife.bind(this);
        lyMengerti.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyMengerti:

                break;
            default:
                break;
        }
        dismiss();
    }
}
