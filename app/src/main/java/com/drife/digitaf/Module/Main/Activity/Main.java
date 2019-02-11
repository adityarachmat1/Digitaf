package com.drife.digitaf.Module.Main.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.Firebase.Config;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Login.Activity.LoginActivity;
import com.drife.digitaf.Module.Notification.NotificationActivity;
import com.drife.digitaf.Module.SimulasiKredit.Fragment.SimulasiKreditFragment;
import com.drife.digitaf.Module.SplashScreen.Activity.SplashScreenActivity;
import com.drife.digitaf.Module.myapplication.MyApllicationFragment;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Controller.DepresiasiController;
import com.drife.digitaf.ORM.Controller.DraftController;
import com.drife.digitaf.ORM.Controller.MaritalStatusController;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Controller.ReligionController;
import com.drife.digitaf.ORM.Controller.TenorController;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.R;
import com.drife.digitaf.Module.Main.Adapter.SimpleFragmentPagerAdapter;
import com.drife.digitaf.Module.dashboard.fragment.DashboardFragment;
import com.drife.digitaf.Module.home.fragment.HomeFragment;
import com.drife.digitaf.Module.inquiry.fragment.InquiryFragment;
import com.drife.digitaf.Service.SyncMasterData.SyncData;
import com.drife.digitaf.UIModel.UserSession;
import com.github.florent37.tutoshowcase.TutoShowcase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orm.SugarContext;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener {
    private String TAG = Main.class.getSimpleName();
    public  static final String POS = "pos";
    /*@BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;*/
    @BindView(R.id.view_pager)
    public ViewPager view_pager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private HomeFragment homeFragment;
    private InquiryFragment inquiryFragment;
    private DashboardFragment dashboardFragment;
    private SimulasiKreditFragment simulasiKreditFragment;
    private MyApllicationFragment myApllicationFragment; //added 01 okt 2018

    private MenuItem prevMenuItem;
    public SimpleFragmentPagerAdapter adapter;
    private int menuSelected = -1;
    private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;

    int pos = 0;
    TutoShowcase tutoShowcase;
    TutoShowcase tutoShowcase1;

    UserSession userSession;

    private String[] listTitles = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_2);
        ButterKnife.bind(this);
        SugarContext.init(Main.this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        userSession = SharedPreferenceUtils.getUserSession(this);
        if (userSession.getUser().getType().equals("spv") || userSession.getUser().getType().equals("bm")) {
            listTitles = new String[]{"<b>Home</b> untuk summary aplikasi yang diajukan ke TAF", "<b>Calculator</b> untuk melakukan Simulasi Kredit melalui Paket / Non Paket / Budget customer", "Anda dapat melihat status aplikasi kredit yang sudah Anda submit di menu <b>Inquiry</b>", "Anda dapat melihat pencapaian penjualan Anda dan mengirimkan email report Incentive ke email Anda di menu <b>Dashboard</b>",
                    "<b>To Do List</b> adalah reminder untuk pending aplikasi yang perlu di Follow Up.<br>Status aplikasi Anda dengan mudah dapat dilihat di <b>My Application Status</b>", "Atur profile dan password Anda termasuk mendaftarkan login sidik jari melalui menu <b>Settings</b>.<br>Notifikasi untuk status aplikasi Anda bisa dilihat dengan tap tombol <b>Notifications</b>"};
        } else {
            listTitles = new String[]{"<b>Home</b> untuk summary aplikasi yang diajukan ke TAF", "<b>Calculator</b> untuk melakukan Simulasi Kredit melalui Paket / Non Paket / Budget customer", "Check Aplikasi Anda di <b>My Application</b> untuk melihat pending document atau aplikasi yang belum disubmit (Draft)",
                    "Anda dapat melihat status aplikasi kredit yang sudah Anda submit di menu <b>Inquiry</b>", "Anda dapat melihat pencapaian penjualan Anda dan mengirimkan email report Incentive ke email Anda di menu <b>Dashboard</b>",
                    "<b>To Do List</b> adalah reminder untuk pending aplikasi yang perlu di Follow Up.<br>Status aplikasi Anda dengan mudah dapat dilihat di <b>My Application Status</b>", "Atur profile dan password Anda termasuk mendaftarkan login sidik jari melalui menu <b>Settings</b>.<br>Notifikasi untuk status aplikasi Anda bisa dilihat dengan tap tombol <b>Notifications</b>"};
        }

//        if (Outbox_submitController.getOutboxsumbit() != null) {
//            if (Outbox_submitController.getOutboxsumbit().size() > 0) {
//                Log.d(TAG, "Data " + Outbox_submitController.getOutboxsumbit().get(0).getForm());
//                Intent service = new Intent(getApplicationContext(), SubmitService.class);
//                startService(service);
//            }
//        }

        Log.e(TAG, "Firebase reg id: " + regId+" "+ FirebaseInstanceId.getInstance().getToken());

//        Log.d(TAG, "MaritalStatus " + JSONProcessor.toJSON(MaritalStatusController.getAllMaritalStatus()));
//        Log.d(TAG, "Religion " + JSONProcessor.toJSON(ReligionController.getAllReligion()));

        configureFragment();
    }

    private void initListeners(){
        /*bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        view_pager.setCurrentItem(0);
                        break;
                    case R.id.action_kalkulator:
                        view_pager.setCurrentItem(1);
                        break;
                    case R.id.action_draft:
                        view_pager.setCurrentItem(2);
                        break;
                    case R.id.action_inquiry:
                        view_pager.setCurrentItem(3);
                        break;
                    case R.id.action_dashboard:
                        view_pager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });*/
    }

    private void callFunctions(){
        setupViewPager(view_pager);
//        PermissionUtility.permissionHandling(Main.this);
        //syncData();
        /*progressDialog = DialogUtility.createProgressDialog(Main.this,"","Please wait...");
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }*/

//        switchSync();
        //new removeMaster().execute();

        /*TimerTask task = new TimerTask() {
            public void run() {
                syncData();
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 100;
        timer.schedule(task, delay);*/

        //view_pager.setCurrentItem(1);
        //bottom_navigation.setItemIconTintList(null);
        //Log.d("singo","user session : "+ JSONProcessor.toJSON(SharedPreferenceUtils.getUserSession(Main.this)));

        boolean firstTutorial = SharedPreferenceUtils.getFirstTutorial(this);

        if (firstTutorial) {
            SharedPreferenceUtils.saveFirstTutorial(this,false);
            showTutoShowcase(tabLayout.getTabAt(Main.this.pos).getCustomView(), R.layout.custom_tutorial, pos);
        }
    }

    class removeMaster extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //CoverageInsuranceController.removeAllCoverage();
            CarController.removeAllCar();
            CarController.removeAllCarType();
            TenorController.removeAllTenor();
            DepresiasiController.removeAllDepresiasi();
            PackageRuleController.removeAllPackageRule();
            Draft.deleteAll(Draft.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean isDraft = false;
        boolean isLogout = false;
        boolean isSuccessSubmit = false;

        if(requestCode==1){

            if (data != null) {
                if (data.getBooleanExtra("is_draft", false)) {
                    isDraft = true;
                } else if (data.getBooleanExtra("is_logout", false)) {
                    isLogout = true;
                }else if(data.getBooleanExtra("is_success_submit", false)){
                    isSuccessSubmit = true;
                }
            }

            if(resultCode== Activity.RESULT_OK){
                if (isDraft) {
                    view_pager.setCurrentItem(2);
                } else if (isLogout) {
                    SharedPreferenceUtils.removeSession(Main.this);
                    SharedPreferences preferences = getSharedPreferences("sync", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("sync", "");
                    editor.commit();
                    Intent intent = new Intent(Main.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(isSuccessSubmit){
                    view_pager.setCurrentItem(3);
                    inquiryFragment.getInquiryListOnSubmit();
                }
            }
        }else if(requestCode==2){
            if(resultCode== Activity.RESULT_OK){
                if (data !=null){
                    int pos = data.getIntExtra(POS, 0);
                    view_pager.setCurrentItem(pos);
                    ((MyApllicationFragment)adapter.getItem(2)).setCurrentPage(2);
                }
            }
        }else if(requestCode==3){
            if(resultCode== Activity.RESULT_OK){
                if (data !=null){
                    if(data.getBooleanExtra("is_success_submit", false)){
                        isSuccessSubmit = true;
                    }

                    if(isSuccessSubmit){
                        view_pager.setCurrentItem(3);
                        inquiryFragment.getInquiryListOnSubmit();
                    }
                }
            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SharedPreferenceUtils.removeSession(Main.this);
//        CoverageInsuranceController.removeAllCoverage();
//        DealerController.removeAllDealer();
//        /*CarController.removeAllCar();
//        CarController.removeAllCarType();
//        TenorController.removeAllTenor();
//        DepresiasiController.removeAllDepresiasi();
//        PackageRuleController.removeAllPackageRule();
//        WayOfPaymentController.removeAllWOP();*/
//    }

    private void configureFragment(){
        homeFragment = new HomeFragment();
        myApllicationFragment = new MyApllicationFragment();
        inquiryFragment = new InquiryFragment();
        dashboardFragment = new DashboardFragment();
        simulasiKreditFragment = new SimulasiKreditFragment();
    }

    private void setupViewPager(ViewPager viewPager){

        try {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    /*if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    }
                    else
                    {
                        bottom_navigation.getMenu().getItem(0).setChecked(false);
                    }
                    bottom_navigation.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = bottom_navigation.getMenu().getItem(position);*/
                    tabLayout.getTabAt(position).select();
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.warning_color));
                    tabLayout.setSelectedTabIndicatorGravity(TabLayout.INDICATOR_GRAVITY_TOP);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext());
            adapter.addTab(homeFragment,getResources().getString(R.string.nav_home),R.drawable.ic_home);
            adapter.addTab(simulasiKreditFragment,getResources().getString(R.string.nav_calculator),R.drawable.ic_calculator);
            if (!userSession.getUser().getType().equals("spv") && !userSession.getUser().getType().equals("bm")) {
                adapter.addTab(myApllicationFragment, getResources().getString(R.string.nav_my_application), R.drawable.ic_my_app);
            }
//            adapter.addTab(myApllicationFragment, getResources().getString(R.string.nav_my_application), R.drawable.ic_my_app);
            adapter.addTab(inquiryFragment,getResources().getString(R.string.nav_inquiry),R.drawable.ic_inquiry);
            adapter.addTab(dashboardFragment,getResources().getString(R.string.nav_dashboard),R.drawable.ic_dashboard);
//            viewPager.setOffscreenPageLimit(4);
            viewPager.setOffscreenPageLimit(!userSession.getUser().getType().equals("spv") || !userSession.getUser().getType().equals("bm") ? 4 : 3);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            createTabIcons();

            if (SplashScreenActivity.notif.equals("")){

            }else {
                SplashScreenActivity.notif = "";
                view_pager.setCurrentItem(0);
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivityForResult(intent,2);
            }
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"setupViewPager",e.getMessage());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void createTabIcons() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        /*tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_calculator);

        tabLayout.getTabAt(2).setIcon(R.drawable.ic_my_app);

        tabLayout.getTabAt(3).setIcon(R.drawable.ic_inquiry);

        tabLayout.getTabAt(4).setIcon(R.drawable.ic_dashboard);*/
    }

    private void syncData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*try {
                    if(ConnectionUtility.isConnected()){
                        SyncData.Sync(Main.this,progressDialog);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                try {
                    /*UserSession userSession = SharedPreferenceUtils.getUserSession(Main.this);
                    CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getDealer().getPv_insco());*/
                    SyncData.Sync(Main.this,progressDialog);
                }catch (Exception e){

                }
            }
        });
    }

    private void switchSync(){
        SharedPreferences preferences = getSharedPreferences("sync",MODE_PRIVATE);
        String stat = preferences.getString("sync", "");
        if(stat.equals("") || stat.equals("0")){
            progressDialog = DialogUtility.createProgressDialog(Main.this,"","Please wait...");
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
            UserSession userSession = SharedPreferenceUtils.getUserSession(Main.this);
            if (userSession.getUser() != null) {
                CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getDealer().getPv_insco());
            }
            new removeMaster().execute();
        }else{
            UserSession userSession = SharedPreferenceUtils.getUserSession(Main.this);
            if (userSession.getUser() != null) {
                CoverageInsuranceController.bulkInsertCoverage(userSession.getUser().getDealer().getPv_insco());
            }
        }
    }

    @Override
    public void onFragmentInteraction(String status, int pos) {
        Log.d("type", userSession.getUser().getType());
        if (!userSession.getUser().getType().equals("spv") && !userSession.getUser().getType().equals("bm")) {
            if(pos == 3) {
                ((InquiryFragment)adapter.getItem(3)).updateListByStatus(status);
            } else if (pos == 2) {
                ((MyApllicationFragment)adapter.getItem(2)).setCurrentPage(2);
            }
        } else {
            ((InquiryFragment)adapter.getItem(2)).updateListByStatus(status);
        }
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            SharedPreferenceUtils.removeSession(Main.this);
            CoverageInsuranceController.removeAllCoverage();
            DealerController.removeAllDealer();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void showTutoShowcase(View pointView, int customView, final int pos) {
        final int indexTab = tabLayout.getTabCount()-1;

        tutoShowcase = TutoShowcase.from(this)
                .setContentView(customView)
                .setBackgroundColor(Color.parseColor("#CCFFFFFF"))
                .onClickContentView(R.id.btnNext, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tutoShowcase.dismiss();
                        Main.this.pos = Main.this.pos + 1;

                        if (Main.this.pos < tabLayout.getTabCount()) {
                            for (int i = 0;i < tabLayout.getTabCount();i++) {
                                if (Main.this.pos == i) {
                                    tabLayout.getTabAt(i).getCustomView().findViewById(R.id.lyTab).setBackground(getResources().getDrawable(R.drawable.dotted_line_shape));
                                } else {
                                    tabLayout.getTabAt(i).getCustomView().findViewById(R.id.lyTab).setBackground(null);
                                }
                            }

                            tutoShowcase = null;
                            showTutoShowcase(tabLayout.getTabAt(Main.this.pos).getCustomView(), R.layout.custom_tutorial, Main.this.pos);
                        } else if (Main.this.pos == indexTab+1) {
                            tabLayout.getTabAt(indexTab).getCustomView().findViewById(R.id.lyTab).setBackground(null);

                            showTutoShowcase(findViewById(R.id.lyList), R.layout.custom_tutorial6, Main.this.pos);
                        } else if (Main.this.pos == indexTab+2) {
                            findViewById(R.id.ly_option).setBackground(getResources().getDrawable(R.drawable.dotted_line_shape1));

                            showTutoShowcase(findViewById(R.id.ly_option), R.layout.custom_tutorial6, Main.this.pos);
                        } else {
                            tutoShowcase.dismiss();
                        }
                    }
                })
                .onClickContentView(R.id.btnSkip, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tutoShowcase.dismiss();
                        for (int i = 0;i < tabLayout.getTabCount();i++) {
                            tabLayout.getTabAt(i).getCustomView().findViewById(R.id.lyTab).setBackground(null);
                        }
                        findViewById(R.id.ly_option).setBackground(null);
                    }
                })
                .setListener(new TutoShowcase.Listener() {
                    @Override
                    public void onDismissed() {
                        if (((ViewGroup) Main.this.findViewById(android.R.id.content)).getChildCount() == 1) {
                            for (int i = 0;i < tabLayout.getTabCount();i++) {
                                tabLayout.getTabAt(i).getCustomView().findViewById(R.id.lyTab).setBackground(null);
                            }
                            findViewById(R.id.ly_option).setBackground(null);
                        }
                    }
                })
                .on(pointView)
                .addRoundRect()
                .show();

        for (int i = 0;i < tabLayout.getTabCount();i++) {
            if (Main.this.pos == i) {
                tabLayout.getTabAt(i).getCustomView().findViewById(R.id.lyTab).setBackground(getResources().getDrawable(R.drawable.dotted_line_shape));
            } else {
                tabLayout.getTabAt(i).getCustomView().findViewById(R.id.lyTab).setBackground(null);
            }
        }

        int index = ((ViewGroup) this.findViewById(android.R.id.content)).getChildCount() == 2 ? 1 : 2;
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(index);
        TextView txtView = ((TextView)((ViewGroup)((ViewGroup)((ViewGroup)viewGroup.getChildAt(1)).getChildAt(3)).getChildAt(0)).getChildAt(0));
        txtView.setText(Html.fromHtml(listTitles[pos]));
        if (Main.this.pos == indexTab+2) {
            Button btnSkip = (Button) ((ViewGroup) viewGroup.getChildAt(1)).getChildAt(1);
            Button btnNext = (Button) ((ViewGroup) viewGroup.getChildAt(1)).getChildAt(2);

            btnSkip.setVisibility(View.INVISIBLE);
            btnNext.setText("DONE");
        }
    }
}