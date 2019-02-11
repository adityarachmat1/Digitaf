package com.drife.digitaf.Module.Login.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.Firebase.Config;
import com.drife.digitaf.Firebase.NotificationUtils;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.PermissionUtility.PermissionUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Aktivasi.Activity.AktivasiActivity;
import com.drife.digitaf.Module.Dialog.FingerprintDialog.RequestFingerprintDialog;
import com.drife.digitaf.Module.Dialog.FingerprintDialog.TakeFingerprint;
import com.drife.digitaf.Module.Dialog.PopupLupaPassword.LupaPasswordSavedListener;
import com.drife.digitaf.Module.Dialog.PopupLupaPassword.PopupLupaPassword;
import com.drife.digitaf.Module.Loading.LoadingActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.OnBoardActivity.OnBoardContainerActivity;
import com.drife.digitaf.Module.SignUp.Activity.SignUpActivity;
import com.drife.digitaf.Module.SplashScreen.Activity.SplashScreenActivity;
import com.drife.digitaf.Module.UbahPassword.UbahPasswordActivity;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Controller.Outbox_submitController;
import com.drife.digitaf.ORM.Database.Outbox_submit;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Login.LoginPresenter;
import com.drife.digitaf.Service.Login.LoginCallback;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.Model.LoginResult;
import com.drife.digitaf.retrofitmodule.Model.User;
import com.google.android.gms.vision.L;
import com.google.firebase.messaging.FirebaseMessaging;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;
import com.orm.SugarContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginCallback,FingerPrintAuthCallback {
    private String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.tv_daftar)
    TextView tv_daftar;
    @BindView(R.id.tv_lupa_password)
    TextView tv_lupa_password;
    @BindView(R.id.tv_aktivasi)
    TextView tv_aktivasi;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.lyParent)
    FrameLayout lyParent;
    @BindView(R.id.iv_show_password)
    ImageView imgShowPass1;

    private int ACTIVATE_USER = 1;
    private ProgressDialog progressDialog;
    private FingerPrintAuthHelper fingerPrintAuthHelper;
    private boolean fingerprintAvailable = true;
    private UserSession userSession;
    private boolean showPassword1 = false;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String regId;
    private int REQUEST_CODE_CHANGE_PASSWORD = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SugarContext.init(LoginActivity.this);
//        PermissionUtility.permissionHandling(LoginActivity.this);
        initVariables();
        initListeners();
        callFunctions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        fingerPrintAuthHelper.startAuth();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        try {
            if(fingerPrintAuthHelper != null){
                fingerPrintAuthHelper.stopAuth();
            }
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.warningLog,"onPause",e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fingerPrintAuthHelper.stopAuth();
    }

    private void initVariables(){
        progressDialog = new ProgressDialog(this);
        fingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this,this);

        etPassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void initListeners(){

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        displayFirebaseRegId();

        tv_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        tv_aktivasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AktivasiActivity.class);
                startActivity(intent);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!username.equals("") && !password.equals("")) {
                    progressDialog.setTitle("");
                    progressDialog.setMessage("Logging in...");
                    progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    if (regId !=null){
                        Log.d("Reg Id", regId);
                        LoginPresenter.login(regId,username,password,LoginActivity.this);
                    }else {
                        Log.d("Reg Id", "null");
                        regId = "";
                        LoginPresenter.login(regId,username,password,LoginActivity.this);
                    }


                    /*Intent intent = new Intent(LoginActivity.this, Main.class);
                    startActivity(intent);
                    finish();*/

                } else if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.text_username_tidak_boleh_kosong), Toast.LENGTH_LONG).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.text_password_tidak_boleh_kosong), Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupLupaPassword.showPopup(LoginActivity.this, lyParent, new LupaPasswordSavedListener() {
                    @Override
                    public void isSent() {
                        String msg = getResources().getString(R.string.title_link_reset_password_signin);
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
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
                    public void isFailed() {
                        String msg = "Gagal reset password";
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("")
                                .setMessage(msg)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }});
                        builder.show();
                    }
                });
            }
        });

        imgShowPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword1) {
                    showPassword1 = false;
                    imgShowPass1.setImageResource(R.drawable.ic_hide);
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    showPassword1 = true;
                    imgShowPass1.setImageResource(R.drawable.ic_show);
                    etPassword.setTransformationMethod(null);
                }

                if (etPassword.length() > 0) {
                    etPassword.setSelection(etPassword.length());
                }
            }
        });
    }

    private void callFunctions() {
        showDialogFingerprint();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
    }

    @Override
    public void onSuccess(LoginResult data) {
        progressDialog.dismiss();
        if (data != null){
            userSession = new UserSession();
            userSession.setToken(data.getToken());
            userSession.setUser(data.getUser());

            LogUtility.logging(TAG,LogUtility.infoLog,"login","token",data.getToken());
            LogUtility.logging(TAG,LogUtility.infoLog,"login","token",JSONProcessor.toJSON(userSession.getUser().getResponseConfins()));

            if(data.getUser().getIs_password_expired() != null &&
                    data.getUser().getIs_password_expired().equals("true")){
                SharedPreferenceUtils.setToken(LoginActivity.this,userSession.getToken());
                Intent intent = new Intent(LoginActivity.this,UbahPasswordActivity.class);
                intent.putExtra("isPeriodically",true);
                startActivityForResult(intent,REQUEST_CODE_CHANGE_PASSWORD);
            }else{
                if(fingerprintAvailable){
                    if(SharedPreferenceUtils.getFingerprint(getApplicationContext())==null){
                        RequestFingerprintDialog dialog = new RequestFingerprintDialog(LoginActivity.this,userSession);
                        dialog.show();
                    }else{
                        if(userSession.getUser() != null){
                            SharedPreferenceUtils.saveSession(getApplicationContext(),userSession);
                            new insertCoverage().execute();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
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
                        //SharedPreferenceUtils.saveSession(getApplicationContext(),userSession);
                        /*if(userSession.getUser().getType().equals("so")){
                            Log.d("singo", "coverage insurance : "+JSONProcessor.toJSON(userSession.getUser().getPv_insco()));
                            //CoverageInsuranceController.removeAllCoverage();
                            saveDealers(userSession);
                            CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getPv_insco());
                        }else if(userSession.getUser().getType().equals("sales")){
                            saveCoverageInsurancePV(userSession);
                        }

                        switchSync();*/

                        //new insertCoverage().execute();
                    }
                }else{
                    /*SharedPreferenceUtils.saveSession(getApplicationContext(),userSession);
                    if(userSession.getUser().getType().equals("so")){
                        //CoverageInsuranceController.removeAllCoverage();
                        saveDealers(userSession);
                        CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getPv_insco());
                    }else if(userSession.getUser().getType().equals("sales")){
                        saveCoverageInsurancePV(userSession);
                    }

                    switchSync();*/

                    if(userSession.getUser() != null){
                        SharedPreferenceUtils.saveSession(getApplicationContext(),userSession);
                        new insertCoverage().execute();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
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
            }

        }
        //progressDialog.dismiss();
    }

    private void switchSync(){
        SharedPreferences preferences = getSharedPreferences("sync",MODE_PRIVATE);
        String stat = preferences.getString("sync", "");

        if(!compareLastSync()){
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            finish();
        }else{
            if(stat.equals("") || stat.equals("0")){
                Intent intent = new Intent(this, LoadingActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onFailed(String message) {
        progressDialog.dismiss();
        if(message == null || message.equals("")){
            message = "Gagal login. Periksa kembali koneksi anda";
        }
        AlertDialog.Builder builder = DialogUtility.createSingleOptionAlertDialog(LoginActivity.this, "", message, new DialogUtility.AlertDialogCallback() {
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
    public void onUserNotActive() {
        progressDialog.dismiss();
        Intent intent = new Intent(LoginActivity.this,AktivasiActivity.class);
        startActivityForResult(intent,ACTIVATE_USER);
    }

    @Override
    public void onTokenExpired() {
        //LoginPresenter.refreshToken(LoginActivity.this,LoginActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Failed login")
                .setMessage("Session timeout. Please re-login.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public void onTokenRefreshed(String token) {
        LoginPresenter.loginFingerprint(LoginActivity.this,LoginActivity.this);
    }

    @Override
    public void onPasswordExpired(final String token) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Password kadaluarsa")
                .setMessage("Password anda telah kadaluarsa, silahkan perbarui password Anda.")
                .setPositiveButton("Ganti Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferenceUtils.setToken(LoginActivity.this,token);
                        Intent intent = new Intent(LoginActivity.this, UbahPasswordActivity.class);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        LogUtility.logging(TAG,LogUtility.infoLog,"fingerprint","no hardware detected");
        fingerprintAvailable = false;
    }

    @Override
    public void onNoFingerPrintRegistered() {
        fingerprintAvailable = false;
    }

    @Override
    public void onBelowMarshmallow() {
        fingerprintAvailable = false;
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        LogUtility.logging(TAG,LogUtility.infoLog,"onAuthSuccess","fingerprint authenticated");
        if(SharedPreferenceUtils.getFingerprint(getApplicationContext())!=null && !SharedPreferenceUtils.getFingerprint(getApplicationContext()).equals("0")){
            progressDialog.setTitle("");
            progressDialog.setMessage("Logging in...");
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            LoginPresenter.loginFingerprint(LoginActivity.this,LoginActivity.this);
        }else if(SharedPreferenceUtils.getToken(LoginActivity.this) == null || SharedPreferenceUtils.getToken(LoginActivity.this).equals("")){
            if(SharedPreferenceUtils.getFirstTime(LoginActivity.this) != null && SharedPreferenceUtils.getFirstTime(LoginActivity.this).equals("1")){
                if(userSession.getUser() != null){
                    SharedPreferenceUtils.saveSession(LoginActivity.this,userSession);
                    SharedPreferenceUtils.saveFingerprint(LoginActivity.this,"1");
                    SharedPreferenceUtils.setFirstTime(LoginActivity.this,"0");
                    new insertCoverage().execute();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
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

                /*if(userSession.getUser().getType().equals("so")){
                    //CoverageInsuranceController.removeAllCoverage();
                    saveDealers(userSession);
                    CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getPv_insco());
                }else if(userSession.getUser().getType().equals("sales")){
                    saveCoverageInsurancePV(userSession);
                }*/

                //new insertCoverage().execute();

                //switchSync();
            }
        }
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {

    }

    private void showDialogFingerprint(){
        if(SharedPreferenceUtils.getToken(LoginActivity.this) != null){
            if(SharedPreferenceUtils.getFingerprint(getApplicationContext())!=null &&
                    !SharedPreferenceUtils.getFingerprint(getApplicationContext()).equals("0")){
                TakeFingerprint dialog = new TakeFingerprint(LoginActivity.this,null,false);
                if (!dialog.isShowing()){
                    dialog.show();
                }

            }
        }
    }

    public void saveCoverageInsurancePV(UserSession userSession){
        CoverageInsuranceController.removeAllCoverage();
        CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getDealer().getPv_insco());
    }

    public void saveDealers(UserSession userSession){
        CoverageInsuranceController.removeAllCoverage();
        DealerController.removeAllDealer();
        LogUtility.logging(TAG,LogUtility.infoLog,"saveDealers","dealers", JSONProcessor.toJSON(userSession.getUser().getBranch().getDealers()));
        DealerController.bulkInsertDealer(userSession.getUser().getBranch().getDealers());
    }

    private boolean compareLastSync(){
        boolean status = true;
        Calendar now = Calendar.getInstance();
        String lastSync = SharedPreferenceUtils.getLastSync(LoginActivity.this);
        if(lastSync != null && !lastSync.equals("")){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CHANGE_PASSWORD){
            if(resultCode== Activity.RESULT_OK){
                SharedPreferenceUtils.removeToken(LoginActivity.this);
            }
        }
    }

    class insertCoverage extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            progressDialog.setTitle("");
            progressDialog.setMessage("Sync coverage data");
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                }
            });

            try {
                if(userSession != null){
                    if(userSession.getUser().getType().equals("so")){
                        //CoverageInsuranceController.removeAllCoverage();
                        saveDealers(userSession);
                        //CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getPv_insco());
                    }else if(userSession.getUser().getType().equals("sales")){
                        saveCoverageInsurancePV(userSession);
                    }else{
                        saveCoverageInsurancePV(userSession);
                    }
                }
            }catch (Exception e){
                LogUtility.logging(TAG,LogUtility.warningLog,"insertCoverage",e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
            switchSync();
        }
    }
}
