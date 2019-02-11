package com.drife.digitaf.Module.Login.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.Firebase.Config;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.Module.SplashScreen.Activity.SplashScreenActivity;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Change_number.Change_numberCallback;
import com.drife.digitaf.Service.Change_number.Change_numberPresenter;
import com.drife.digitaf.Service.Login.LoginPresenter;
import com.drife.digitaf.retrofitmodule.Model.LoginResult;
import com.orm.SugarContext;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Change_number extends AppCompatActivity implements Change_numberCallback {
    private String TAG = Change_number.class.getSimpleName();

    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.etChange_number)
    EditText etChange_number;
    @BindView(R.id.etOld_number)
    EditText etOld_number;
    @BindView(R.id.etNameCustomer)
    EditText etNameCustomer;

    private ProgressDialog progressDialog;
    String lead_id;
    String phoneNumber ;
    String name_customer ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_number);
        ButterKnife.bind(this);
        SugarContext.init(Change_number.this);

        lead_id = getIntent().getStringExtra("lead_id");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        name_customer = getIntent().getStringExtra("name_customer");
        etNameCustomer.setText(name_customer);
        etOld_number.setText(phoneNumber);

        initVariables();
        initListeners();
    }


    private void initVariables(){
        progressDialog = new ProgressDialog(this);
    }

    private void initListeners(){

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String change_number = etChange_number.getText().toString();

                if (!change_number.equals("")){

                    progressDialog.setTitle("");
                    progressDialog.setMessage("Change Number...");
                    progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Change_numberPresenter.ChangeNumber(Change_number.this,lead_id,change_number,Change_number.this);

                } else if (change_number.equals("")) {
                    Toast.makeText(Change_number.this, "input new number", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onSuccessGetChange_number() {
        Log.d("sukses", "sukses");
        progressDialog.dismiss();
        String msg = "Success Change Number";
        AlertDialog.Builder builder = new AlertDialog.Builder(Change_number.this)
                .setTitle("")
                .setMessage(msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SplashScreenActivity.notif="";
                        SplashScreenActivity.lead_id="";
                        dialog.dismiss();
                        finish();
                    }});
        builder.show();
    }

    @Override
    public void onFailedGetChange_number() {
        Log.d("failed", "failed");
        progressDialog.dismiss();
        String msg = "Failed Change Number";
        AlertDialog.Builder builder = new AlertDialog.Builder(Change_number.this)
                .setTitle("")
                .setMessage(msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }});
        builder.show();
    }

    @Override
    public void onLoopEnd() {

    }

    public void onBackPressed() {
        SplashScreenActivity.notif="";
        SplashScreenActivity.lead_id="";
        finish();
    }
}
