package com.drife.digitaf.Module.SplashScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.drife.digitaf.BuildConfig;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Login.Activity.Change_number;
import com.drife.digitaf.Module.Login.Activity.LoginActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.Notification.NotificationActivity;
import com.drife.digitaf.Module.OnBoardActivity.OnBoardContainerActivity;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.orm.SugarContext;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
    private String TAG = SplashScreenActivity.class.getSimpleName();

    @BindView(R.id.tvVersion)
    TextView tvVersion;

    public static String notif = "";
    public static String lead_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        SugarContext.init(SplashScreenActivity.this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        tvVersion.setText(BuildConfig.VERSION_NAME);
    }

    private void initListeners(){
        //Crashlytics.getInstance().crash();
    }

    private void callFunctions(){
        TimerTask task = new TimerTask() {
            public void run() {
                checkSession();
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 3000;
        timer.schedule(task, delay);

        /*if(SharedPreferenceUtils.getToken(SplashScreenActivity.this) == null){
            TimerTask task = new TimerTask() {
                public void run() {
                    checkSession();
                }
            };
            Timer timer = new Timer("Timer");

            long delay = 3000;
            timer.schedule(task, delay);
        }else{
            try {
                if(ConnectionUtility.isConnected()){
                    SyncData.Sync(SplashScreenActivity.this,null);
                }else{
                    TimerTask task = new TimerTask() {
                        public void run() {
                            checkSession();
                        }
                    };
                    Timer timer = new Timer("Timer");

                    long delay = 3000;
                    timer.schedule(task, delay);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private void checkSession(){
//        boolean firstInstall = SharedPreferenceUtils.getFirstInstall(this);
//
//        if (firstInstall) {
//            SharedPreferenceUtils.saveFirstInstall(this,false);
//            Intent intent = new Intent(SplashScreenActivity.this, OnBoardContainerActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//        } else {
            UserSession userSession = SharedPreferenceUtils.getUserSession(getApplicationContext());
            if(userSession != null){
                if (getIntent().getStringExtra("notif") !=null) {
                    notif = getIntent().getStringExtra("notif");
                    lead_id = getIntent().getStringExtra("lead_id");

                    Intent intent = new Intent(SplashScreenActivity.this, Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreenActivity.this, Main.class);
                    startActivity(intent);
                    finish();
                }
            }else{
                if (getIntent().getStringExtra("notif") !=null) {
                    notif = getIntent().getStringExtra("notif");
                    lead_id = getIntent().getStringExtra("lead_id");

                    Log.d("notif + LeadId", notif + ", "+lead_id);
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
//        }
    }

    private void checkFirstTime(){
        if(SharedPreferenceUtils.getFirstTime(SplashScreenActivity.this)==null){
            SharedPreferenceUtils.setFirstTime(SplashScreenActivity.this,"1");
        }
    }
}
