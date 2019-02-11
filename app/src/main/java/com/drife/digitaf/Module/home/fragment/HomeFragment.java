package com.drife.digitaf.Module.home.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.drife.digitaf.BuildConfig;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.Main.Utils.PopupUpdate;
import com.drife.digitaf.Module.Profile.Activity.ProfileActivity;
import com.drife.digitaf.Module.home.model.FilterHari;
import com.drife.digitaf.Module.home.model.Home_data;
import com.drife.digitaf.Module.Notification.NotificationActivity;
import com.drife.digitaf.Service.Config.ConfigCallback;
import com.drife.digitaf.Service.Config.ConfigPresenter;
import com.drife.digitaf.Service.Home.HomeCallback;
import com.drife.digitaf.Service.Home.HomePresenter;
import com.drife.digitaf.UIModel.ToDo;
import com.drife.digitaf.Module.home.Adapter.HomeAdapter;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeCallback {
    private String TAG = HomeFragment.class.getSimpleName();
    private View rootView;

    //Progress Dialog Object

    @BindView(R.id.rv_to_do)
    RecyclerView rv_to_do;
    @BindView(R.id.rv_application)
    RecyclerView rv_application;
    @BindView(R.id.lySetting)
    LinearLayout lySetting;
    @BindView(R.id.lyNotification)
    LinearLayout lyNotification;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_draft)
    TextView tv_draft;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;
    @BindView(R.id.spinnerDays)
    AutoCompleteTextView spinnerDays;
    @BindView(R.id.srlHome)
    SwipeRefreshLayout srlHome;
    @BindView(R.id.ly_to_do_list)
    LinearLayout ly_to_do_list;

    private HomeAdapter toDoAdapter;
    private HomeAdapter myApplicationAdapter;
    private List<ToDo> toDoList = new ArrayList<>();
    private List<ToDo> myApplicationList = new ArrayList<>();
    private Home_data homeData = new Home_data();

    private int REQUEST_SETTING = 1;

    private int REQUEST_NOTIFICATION = 2;
    boolean isVisibleFragment = false;
    private Handler mHandler;

    private UserSession userSession;

    public OnFragmentInteractionListener onFragmentInteractionListener;

    private String days = "7";
    private String spv = "spv";
    private String bm = "bm";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(0);

                checkConfig();
            }
        }

        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        userSession = SharedPreferenceUtils.getUserSession(getActivity());

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

        List<FilterHari> listFilterHari = new ArrayList<>();
        listFilterHari.add(new FilterHari("7 Hari Terakhir", "7"));
        listFilterHari.add(new FilterHari("Bulan ini", "30"));
        listFilterHari.add(new FilterHari("60 Hari Terakhir", "60"));
        spinnerDays.setAdapter(new ArrayAdapter<FilterHari>(getActivity(), android.R.layout.simple_list_item_1, listFilterHari));
    }

    private void initListeners(){
        lySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().startActivityForResult(intent,REQUEST_SETTING);
            }
        });

        lyNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                getActivity().startActivityForResult(intent,REQUEST_NOTIFICATION);
            }
        });

        spinnerDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDays.showDropDown();
            }
        });

        spinnerDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                srlHome.setRefreshing(true);

                FilterHari filterHari = (FilterHari)parent.getItemAtPosition(position);
                days = filterHari.getHari();
//                String query = "?days="+filterHari.getHari();
                String query = "?days="+days;
                HomePresenter.getHomeDataByQuery(getActivity(), query, HomeFragment.this);
            }
        });

        srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callFunctions();
            }
        });
    }

    private void callFunctions(){
        setHomeData(homeData);
        if(userSession.getUser().getType().equals(spv) || userSession.getUser().getType().equals(bm)){
            ly_to_do_list.setVisibility(View.GONE);
        }/*else{
            setToDoList();
        }*/

        setToDoList();
        setApplicationStatusList();
    }

    private void setToDoList(){
        srlHome.setRefreshing(true);

        HomePresenter.getHomeDataByQuery(getActivity(), "?days="+days, HomeFragment.this);
    }

    private void setApplicationStatusList(){

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisibleFragment = isVisibleToUser;

        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(0);
            }
        }
    }

    @Override
    public void onSuccessGetHome_data(Home_data homedata) {
        srlHome.setRefreshing(false);

        this.homeData = homedata;
        setHomeData(this.homeData);
    }

    @Override
    public void onFailedGetHome_data(String message) {
        srlHome.setRefreshing(false);
    }

    private void setHomeData(Home_data homedata) {
        tv_submit.setText(homedata.total_inquiry);
        tv_draft.setText(homedata.total_draft);
        toDoList.clear();
        ToDo toDo1 = new ToDo("1","Follow Up Document",homedata.getTotal_follow_up_doc(), "");
        ToDo toDo2 = new ToDo("2","Update Phone Number",homedata.getTotal_update_phone(), "");
        toDoList.add(toDo1);
        toDoList.add(toDo2);

        rv_to_do.setNestedScrollingEnabled(false);
        rv_to_do.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_to_do.setHasFixedSize(false);
        toDoAdapter = new HomeAdapter(getContext(),toDoList);
        rv_to_do.setAdapter(toDoAdapter);

        myApplicationList.clear();
        ToDo application1 = new ToDo("1","Valid",homedata.getTotal_go_live(), getResources().getString(R.string.tooltip_valid));
        ToDo application2 = new ToDo("2","Approve",homedata.getTotal_approve(), getResources().getString(R.string.tooltip_approve));
        ToDo application3 = new ToDo("3","Reject",homedata.getTotal_reject(), getResources().getString(R.string.tooltip_reject));
        ToDo application4 = new ToDo("4","Credit Review",homedata.getTotal_credit_review(), getResources().getString(R.string.tooltip_credit_review));
        ToDo application5 = new ToDo("5","Data Entry",homedata.getTotal_data_entry(), getResources().getString(R.string.tooltip_data_entry));
        ToDo application6 = new ToDo("6","Cancel",homedata.getTotal_cancel(), getResources().getString(R.string.tooltip_cancel));
        ToDo application7 = new ToDo("7","Wait",homedata.getTotal_wait(), getResources().getString(R.string.tooltip_wait));
        ToDo application8 = new ToDo("8","Request",homedata.getTotal_request(), getResources().getString(R.string.tooltip_request));
        ToDo application9 = new ToDo("9","Request Negotiation",homedata.getTotal_request_negotiation(), getResources().getString(R.string.tooltip_request_negotiation));
        ToDo application10 = new ToDo("10","Request Survey",homedata.getTotal_request_survey(), getResources().getString(R.string.tooltip_request_survey));
//        ToDo application9 = new ToDo("9","Re-Input",homedata.getTotal_reinput(), "");
        myApplicationList.add(application1);
        myApplicationList.add(application2);
        myApplicationList.add(application3);
        myApplicationList.add(application4);
        myApplicationList.add(application5);
        myApplicationList.add(application6);
        myApplicationList.add(application7);
        myApplicationList.add(application8);
        myApplicationList.add(application9);
        myApplicationList.add(application10);

        rv_application.setNestedScrollingEnabled(false);
        rv_application.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_application.setHasFixedSize(false);
        myApplicationAdapter = new HomeAdapter(getContext(),myApplicationList);
        rv_application.setAdapter(myApplicationAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            e.getMessage();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String status, int pos);
    }

    private void checkConfig() {
        ConfigPresenter.getConfig(getActivity(), new ConfigCallback() {
            @Override
            public void onSuccessGetConfig(JSONObject jsonObject) {
                if (!jsonObject.isNull("version")) {
                    try {
                        JSONObject version = jsonObject.getJSONObject("version");
                        String update_message = version.optString("update_message");
                        String is_force_update = version.optString("is_force_update");
                        String minor_patch = version.optString("minor_patch");

                        if (is_force_update.equalsIgnoreCase("1")) {
                            if (minor_patch.equals(BuildConfig.VERSION_NAME)){
                                Log.d("version", "nothing update app");
                            }else {
                                View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
                                PopupUpdate.showForceUpdate(getActivity(), rootView, update_message, true);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailedGetConfig(String message) {

            }
        });
    }
}
