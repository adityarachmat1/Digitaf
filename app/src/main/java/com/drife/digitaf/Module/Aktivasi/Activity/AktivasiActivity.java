package com.drife.digitaf.Module.Aktivasi.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Otp.OtpCallback;
import com.drife.digitaf.Service.Otp.OtpPresenter;
import com.drife.digitaf.retrofitmodule.Model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AktivasiActivity extends AppCompatActivity implements OtpCallback{
    private String TAG = AktivasiActivity.class.getSimpleName();

    @BindView(R.id.etOtp)
    EditText etOtp;
    @BindView(R.id.btLanjutkan)
    Button btLanjutkan;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.tvResend)
    TextView tvResend;
    @BindView(R.id.tvBack)
    TextView tvBack;

    private ProgressDialog progressDialog;
    String otpCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        otpCode = getIntent().getStringExtra("otp");
    }

    private void initListeners(){
        btLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etOtp.getText().toString();
                if(otp != null && !otp.equals("")){
                    progressDialog = DialogUtility.createProgressDialog(AktivasiActivity.this,"Validate OTP","Please wait...");
                    progressDialog.show();
                    OtpPresenter.validateOTP(otp,AktivasiActivity.this);
                }else{
                    etOtp.setError("Fied is empty");
                }
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                if(!email.equals("")){
                    if(TextUtility.isEmailFormat(email)){
                        progressDialog = DialogUtility.createProgressDialog(AktivasiActivity.this,"Resend OTP", "Please wait...");
                        progressDialog.show();
                        OtpPresenter.resendOTP_v2(email, AktivasiActivity.this);
                    }else{
                        String msg = "Email address is not valid";
                        AlertDialog.Builder builder = new AlertDialog.Builder(AktivasiActivity.this)
                                .setTitle("")
                                .setMessage(msg)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }});
                        builder.show();
                    }
                }else{
                    String msg = "Email can not be empty";
                    AlertDialog.Builder builder = new AlertDialog.Builder(AktivasiActivity.this)
                            .setTitle("")
                            .setMessage(msg)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }});
                    builder.show();
                }
            }
        });
    }

    private void callFunctions(){
    }

    @Override
    public void onSuccess() {
        progressDialog.dismiss();
        String message = getResources().getString(R.string.title_akun_berhasil_aktivasi);
        AlertDialog.Builder builder = new AlertDialog.Builder(AktivasiActivity.this)
                .setTitle("")
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }});
        builder.show();
    }

    @Override
    public void onFailed(String message) {
        progressDialog.dismiss();
        //String msg = "OTP is not valid";
        //Toast.makeText(AktivasiActivity.this,msg,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(AktivasiActivity.this)
                .setTitle("")
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }});
        builder.show();
    }

    @Override
    public void onResend(User user) {
        //Toast.makeText(this, "Kode OTP berhasil dikirimkan.", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        String message = "OTP sent. Please check Your email";
        AlertDialog.Builder builder = new AlertDialog.Builder(AktivasiActivity.this)
                .setTitle("")
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        etEmail.setText("");
                        dialog.dismiss();
                    }});
        builder.show();
    }

    @Override
    public void onFailedResend(String message) {
        progressDialog.dismiss();

        AlertDialog.Builder builder = DialogUtility.createSingleOptionAlertDialog(AktivasiActivity.this, "Failed send OTP", message, new DialogUtility.AlertDialogCallback() {
            @Override
            public void onPositive() {

            }

            @Override
            public void onNegative() {

            }
        },"Ok");
        builder.show();
    }

    @Override
    public void onResponseError() {
        progressDialog.dismiss();
    }
}
