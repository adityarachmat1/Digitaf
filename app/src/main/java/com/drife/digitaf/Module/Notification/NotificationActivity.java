package com.drife.digitaf.Module.Notification;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Login.Activity.Change_number;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Inquiry.InquiryCallback;
import com.drife.digitaf.Service.Inquiry.InquiryPresenter;
import com.drife.digitaf.Service.Notification.NotificationItem;
import com.drife.digitaf.Service.Notification.Notificationcallback;
import com.drife.digitaf.Service.Notification.Notificationpresenter;
import com.drife.digitaf.UIModel.UserSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, Notificationcallback {
    private ImageView imgBack;
    private RecyclerView rvInquiry;
    private SwipeRefreshLayout lyRefresh;
    private ProgressBar pbInquiry;
    private TextView txtName, txtDealerName, txtToolbarHeader;

    private NotifListAdapter notifListAdapter;

    private List<NotificationItem> listItems;

    private UserSession userSession;
    public static String st_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_notification_layout);
        ButterKnife.bind(this);
        initLayout();
        initListners();
        callFunctions();
    }

    private void initLayout() {
        rvInquiry = findViewById(R.id.rvInquiry);
        imgBack = findViewById(R.id.imgBack);
        lyRefresh = findViewById(R.id.lyRefresh);
        pbInquiry = findViewById(R.id.pbInquiry);
        txtName = findViewById(R.id.txtName);
        txtDealerName = findViewById(R.id.txtCabang);
        txtToolbarHeader = findViewById(R.id.txtToolbarHeader);

        if (getIntent().getStringExtra("update_phone")!=null){
            st_phone = "1";
            txtToolbarHeader.setText("Update Phone Number");
            Log.d("updatePhone", st_phone);
        }else {
            st_phone = "0";
            txtToolbarHeader.setText("Notification");
            Log.d("updatePhone", st_phone);
        }

        userSession = SharedPreferenceUtils.getUserSession(this);

        if (userSession.getUser().getProfile().getFullname() != null) {
            txtName.setText(userSession.getUser().getProfile().getFullname());
        }

        if (userSession.getUser().getType().equals("so")) {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getBranchName() != null) {
                    txtDealerName.setText(userSession.getUser().getResponseConfins().getBranchName());
                }
            }
        } else {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getDealerName() != null) {
                    txtDealerName.setText(userSession.getUser().getResponseConfins().getDealerName());
                }
            }
        }
    }

    private void initListners(){
        imgBack.setOnClickListener(this);

        lyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotificationList();
            }
        });
    }

    private void callFunctions(){
        getNotificationList();
    }


    private void getNotificationList(){
        lyRefresh.setRefreshing(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pbInquiry.setVisibility(View.VISIBLE);
                rvInquiry.setVisibility(View.GONE);
            }
        });


        listItems = new ArrayList<>();
        Notificationpresenter.getAllnotification(getApplicationContext(), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }

    @Override
    public void onSuccessGetNotification(List<NotificationItem> notificationItems) {
        listItems.clear();
        Collections.reverse(notificationItems);
        pbInquiry.setVisibility(View.GONE);
        rvInquiry.setVisibility(View.VISIBLE);

        if (notifListAdapter != null) {
            notifListAdapter.clear();
        }

        if (notificationItems.size() > 0) {
            if (st_phone.equals("1")) {
                for (int i = 0; i < notificationItems.size(); i++) {
                    if (notificationItems.get(i).getNotificationParam().getTitle_message().equals("Request Phone Number")) {
                        listItems.add(notificationItems.get(i));
                    }
                }
                Log.d("listItem1", String.valueOf(listItems.size()));
            }else {
                listItems = notificationItems;
                Log.d("listItem0", String.valueOf(listItems.size()));
            }

            notifListAdapter = new NotifListAdapter(getApplicationContext(), listItems, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            rvInquiry.setLayoutManager(layoutManager);
            rvInquiry.setItemAnimator(new DefaultItemAnimator());
            rvInquiry.setNestedScrollingEnabled(false);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvInquiry.getContext(),
                    ((LinearLayoutManager) layoutManager).getOrientation());
            rvInquiry.addItemDecoration(dividerItemDecoration);

            rvInquiry.setAdapter(notifListAdapter);
        } else {

        }
    }

    @Override
    public void onFailedGetNotification(String message) {
        pbInquiry.setVisibility(View.GONE);
        rvInquiry.setVisibility(View.VISIBLE);

        if (notifListAdapter != null) {
            notifListAdapter.clear();
        }

    }

    public void showUploadPage(NotificationItem notificationItem){
        Log.d("StatusPhone", notificationItem.getInquiryItem().is_req_phone);
        if (notificationItem.getNotificationParam().getTitle_message().equals("Request Phone Number")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");

            try {
                Date date1 = sdf.parse(notificationItem.getCreated_at());
                Date date2 = sdf.parse(sdf.format(new Date()));

                long different = date1.getTime() - date2.getTime();

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;

                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;

                long elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;

                long elapsedMinutes = different / minutesInMilli;

//                if (elapsedDays < 0){
//                    Log.d("elapsedDays < 0", elapsedDays+" days , " + notificationItem.getCreated_at() + ", "+sdf.format(new Date()));
//                }else {
                    Log.d("elapsedDays", elapsedDays+" days , " + notificationItem.getCreated_at()+ ", "+sdf.format(new Date()));
                    Intent intent = new Intent(getApplicationContext(),Change_number.class);
                    intent.putExtra("lead_id", notificationItem.getInquiryItem().lead_id);
                    intent.putExtra("phoneNumber", notificationItem.getInquiryItem().form_value.getMobilePhone());
                    intent.putExtra("name_customer", notificationItem.getInquiryItem().getFormValue().getCustomerName());
                    startActivity(intent);
                    finish();
//                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if (notificationItem.getNotificationParam().getTitle_message().equals("Application Wait Status")
                || notificationItem.getNotificationParam().getTitle_message().equals("Request Additional Document")){
            Intent intent = new Intent();
            intent.putExtra(Main.POS,2);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else {
            Intent intent = new Intent();
            intent.putExtra(Main.POS,2);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    class getData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getNotificationList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lyRefresh.setRefreshing(false);
        }
    }
}
