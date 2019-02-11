package com.drife.digitaf.Module.dashboard.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.CarModel;
import com.drife.digitaf.KalkulatorKredit.Model.Master.CarModelMaster;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.dashboard.fragment.adapter.LegendAdapter;
import com.drife.digitaf.Module.dashboard.fragment.model.LegendItem;
import com.drife.digitaf.Module.dashboard.fragment.model.ReportSalesByPackage;
import com.drife.digitaf.Module.home.fragment.HomeFragment;
import com.drife.digitaf.Module.home.model.Home_data;
import com.drife.digitaf.Module.inquiry.fragment.model.FilterStatus;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.Service.Dashboard.DashboardCallback;
import com.drife.digitaf.Service.Dashboard.DashboardPresenter;
import com.drife.digitaf.Service.Home.HomeCallback;
import com.drife.digitaf.Service.Home.HomePresenter;
import com.drife.digitaf.UIModel.ToDo;
import com.drife.digitaf.Module.home.Adapter.HomeAdapter;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.eastside.chart.MPAndroidChart.ChartUtility;
import com.eastside.chart.MPAndroidChart.Model.ChartData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment implements View.OnClickListener, HomeCallback, DashboardCallback {
    private String TAG = DashboardFragment.class.getSimpleName();
    private View rootView;

    @BindView(R.id.rv_application)
    RecyclerView rvApplication;
    @BindView(R.id.chart)
    BarChart chart;
    @BindView(R.id.iv_back)
    ImageView imgBack;
    @BindView(R.id.atSelectMonth)
    AutoCompleteTextView atSelectMonth;
    @BindView(R.id.atSelectYear)
    AutoCompleteTextView atSelectYear;
    @BindView(R.id.atSelectPeriode)
    AutoCompleteTextView atSelectPeriode;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;
    @BindView(R.id.textNoChart)
    TextView textNoChart;
    @BindView(R.id.lyCustomFilter)
    LinearLayout lyCustomFilter;
    @BindView(R.id.btEmailIncentiveReport)
    RelativeLayout btEmailIncentiveReport;
    @BindView(R.id.srlDashboard)
    SwipeRefreshLayout srlDashboard;
    @BindView(R.id.rvLegend)
    RecyclerView rvLegend;

    private List<ToDo> myApplicationList = new ArrayList<>();
    private HomeAdapter myApplicationAdapter;
    private Home_data homeData = new Home_data();
    private List<LegendItem> listLegend = new ArrayList<>();
    private LegendAdapter legendAdapter;

    boolean isVisibleFragment = false;

    private UserSession userSession;
    private FilterStatus selectedYear, selectedMonth;

    private ProgressDialog progressDialog;
    public HomeFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard,container,false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(4);
            }
        }

        initVariables();
        initListeners();
        callFunctions();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisibleFragment = isVisibleToUser;

        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(4);
            }
        }
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
    }

    private void initListeners(){
        imgBack.setOnClickListener(this);

        atSelectYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atSelectYear.showDropDown();
            }
        });

        atSelectYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = (FilterStatus) parent.getItemAtPosition(position);
                getDataFromCustomFilter();
            }
        });

        atSelectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atSelectMonth.showDropDown();
            }
        });

        atSelectMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = (FilterStatus) parent.getItemAtPosition(position);
                getDataFromCustomFilter();
            }
        });

        atSelectPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atSelectPeriode.showDropDown();
            }
        });

        atSelectPeriode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilterStatus filterStatus = (FilterStatus)parent.getItemAtPosition(position);
                if (filterStatus.getName().equalsIgnoreCase("Custom")) {
                    lyCustomFilter.setVisibility(View.VISIBLE);
                } else {
                    lyCustomFilter.setVisibility(View.GONE);

                    setApplicationStatusList();
                }
            }
        });

        btEmailIncentiveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Sending email...");
                progressDialog.show();

                String month = selectedMonth != null ? selectedMonth.getStatusParam() : String.valueOf(getMonth());
                String year = selectedYear != null ? selectedYear.getStatusParam() : String.valueOf(getYear());

                DashboardPresenter.getAllIncentive(getActivity(), "?month="+month+"&year="+year,DashboardFragment.this);
            }
        });

        srlDashboard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callFunctions();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ((Main)getActivity()).view_pager.setCurrentItem(((Main)getActivity()).adapter.back());
                break;
        }
    }

    private void callFunctions(){
        setApplicationStatusList();
        setPeriodeList();
    }

    private void setApplicationStatusList(){
        srlDashboard.setRefreshing(true);

        String queryPackage = "?start_month="+ getMonth() +"&end_month="+ getMonth() +"&year="+getYear();
        DashboardPresenter.getReportSalesByPackage(getActivity(), queryPackage, DashboardFragment.this);
//
//        String query = "?year="+getYear()+"&month="+getMonth();
//        HomePresenter.getHomeDataByQuery(getActivity(), query, DashboardFragment.this);
    }

    private void sampleSingleXMultipleItem(List<ReportSalesByPackage> list) {
        listLegend.clear();

        if (list.isEmpty()) {
            rvLegend.setVisibility(View.GONE);
            chart.setVisibility(View.GONE);
            textNoChart.setVisibility(View.VISIBLE);
        } else {
            textNoChart.setVisibility(View.GONE);
            rvLegend.setVisibility(View.VISIBLE);
            chart.setVisibility(View.VISIBLE);
            /*add single xAxis*/
            ArrayList<String> xAxisData = new ArrayList<>();
            xAxisData.add("");

            /*array dataset*/
            /*can contain multiple dataset*/
            /*dataset contain: title, arraylist of BarEntry, and id of color asset*/
            ArrayList<ChartData> dataSets = new ArrayList<>();

            for (int x = 0; x < list.size(); x++) {
                String title = list.get(x).getPackaged();
                ArrayList<BarEntry> valueSet = new ArrayList<>();
                BarEntry entry = new BarEntry(Integer.parseInt(list.get(x).unit), 0);
                valueSet.add(entry);
//            int color = getResources().getColor(R.color.warning_color);
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                ChartData chartData = new ChartData(title, valueSet, color);

                listLegend.add(new LegendItem(title, color));

                dataSets.add(chartData);
                Log.d("dataset", String.valueOf(dataSets.size()));
            }

            /*build BarData and view on chart*/
            Log.d("BarData", "X " + xAxisData.size() + ", Dataset " + dataSets.size());
            BarData data = new BarData(ChartUtility.getXAxisValues(xAxisData), ChartUtility.getDataSet(dataSets));
            chart.setData(data);
            chart.setDescription("");
            chart.animateXY(2000, 2000);
            chart.getLegend().setEnabled(false);
            chart.invalidate();

            rvLegend.setNestedScrollingEnabled(false);
            rvLegend.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvLegend.setHasFixedSize(false);
            legendAdapter = new LegendAdapter(getContext(), listLegend);
            rvLegend.setAdapter(legendAdapter);
        }
    }

//    private void sampleSingleXMultipleItem(){
//        /*add single xAxis*/
//        ArrayList<String> xAxisData = new ArrayList<>();
//        xAxisData.add("");
//
//        /*array dataset*/
//        /*can contain multiple dataset*/
//        /*dataset contain: title, arraylist of BarEntry, and id of color asset*/
//        ArrayList<ChartData> dataSets = new ArrayList<>();
//
////        /*dataset 1*/
////        String title1 = "Paket 1";
////        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
////        BarEntry entry1 = new BarEntry(500, 0);
////        valueSet1.add(entry1);
////        int color1 = getResources().getColor(R.color.colorPrimary);
////        ChartData chartData1 = new ChartData(title1,valueSet1,color1);
////
////        /*dataset 2*/
////        String title2 = "Paket 2";
////        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
////        BarEntry entry2 = new BarEntry(250, 0);
////        valueSet2.add(entry2);
////        int color2 = getResources().getColor(R.color.colorAccent);
////        ChartData chartData2 = new ChartData(title2,valueSet2,color2);
////
////        /*dataset 3*/
////        String title3 = "Paket 3";
////        ArrayList<BarEntry> valueSet3 = new ArrayList<>();
////        BarEntry entry3 = new BarEntry(427, 0);
////        valueSet3.add(entry3);
////        int color3 = getResources().getColor(R.color.selectedText);
////        ChartData chartData3 = new ChartData(title3,valueSet3,color3);
////
////        /*dataset 4*/
////        String title4 = "Paket 4";
////        ArrayList<BarEntry> valueSet4 = new ArrayList<>();
////        BarEntry entry4 = new BarEntry(325, 0);
////        valueSet4.add(entry4);
////        int color4 = getResources().getColor(R.color.warning_color);
////        ChartData chartData4 = new ChartData(title4,valueSet4,color4);
//
//        /*insert to dataset arraylist*/
////        dataSets.add(chartData1);
////        dataSets.add(chartData2);
////        dataSets.add(chartData3);
////        dataSets.add(chartData4);
//
//        String title = "Paket 4";
//        ArrayList<BarEntry> valueSet = new ArrayList<>();
//        BarEntry entry = new BarEntry(325, 0);
//        valueSet.add(entry);
//        int color = getResources().getColor(R.color.warning_color);
//        ChartData chartData = new ChartData(title,valueSet,color);
//
//        dataSets.add(chartData);
//
//        /*build BarData and view on chart*/
//        BarData data = new BarData(ChartUtility.getXAxisValues(xAxisData), ChartUtility.getDataSet(dataSets));
//        chart.setData(data);
//        chart.setDescription("");
//        chart.animateXY(2000, 2000);
//        chart.invalidate();
//    }

    @Override
    public void onSuccessGetReportSalesByPackage(List<ReportSalesByPackage> listReportSalesByPackage) {
        srlDashboard.setRefreshing(false);

        sampleSingleXMultipleItem(listReportSalesByPackage);
    }

    @Override
    public void onFailedGetReportSalesByPackage(String message) {
        srlDashboard.setRefreshing(false);
    }

    @Override
    public void onSuccessGetAllIncentive(Object object) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        Toast.makeText(getActivity(), "Sukses mengirim, silahkan cek email anda.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedGetAllIncentive(String message) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        Toast.makeText(getActivity(), "Gagal mengirim email.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessGetHome_data(Home_data homedata) {
        srlDashboard.setRefreshing(false);

        this.homeData = homedata;
        setDashboardData(this.homeData);
    }

    @Override
    public void onFailedGetHome_data(String message) {
        srlDashboard.setRefreshing(false);
    }

    private void setDashboardData(Home_data homedata) {
        myApplicationList.clear();
        ToDo application1 = new ToDo("1","Valid",homedata.total_go_live);
        ToDo application2 = new ToDo("2","Approve",homedata.total_approve);
        ToDo application3 = new ToDo("3","Reject",homedata.total_reject);
        ToDo application4 = new ToDo("4","Credit Review",homedata.total_credit_review);
        ToDo application5 = new ToDo("5","Data Entry",homedata.total_data_entry);
        ToDo application6 = new ToDo("6","Cancel",homedata.total_cancel);
        ToDo application7 = new ToDo("7","Wait",homedata.total_wait);
        ToDo application8 = new ToDo("8","Request",homedata.total_request);
        ToDo application9 = new ToDo("9","Re-Input",homedata.total_request);
        myApplicationList.add(application1);
        myApplicationList.add(application2);
        myApplicationList.add(application3);
        myApplicationList.add(application4);
        myApplicationList.add(application5);
        myApplicationList.add(application6);
        myApplicationList.add(application7);
        myApplicationList.add(application8);
        myApplicationList.add(application9);

        rvApplication.setNestedScrollingEnabled(false);
        rvApplication.setLayoutManager(new LinearLayoutManager(getContext()));
        rvApplication.setHasFixedSize(false);
        myApplicationAdapter = new HomeAdapter(getContext(),myApplicationList);
        rvApplication.setAdapter(myApplicationAdapter);
    }

    private void setPeriodeList(){
        int yearInt = getYear();
        int monthInt = getMonth();

        List<FilterStatus> filterStatusPeriode = new ArrayList<>();
        filterStatusPeriode.add(new FilterStatus("Bulan ini", String.valueOf(yearInt+"-"+monthInt)));
        filterStatusPeriode.add(new FilterStatus("Custom", ""));
        atSelectPeriode.setAdapter(new ArrayAdapter<FilterStatus>(getActivity(), android.R.layout.simple_list_item_1, filterStatusPeriode));
        atSelectPeriode.setSelection(0);

        List<FilterStatus> filterStatusYear = new ArrayList<>();
        filterStatusYear.add(new FilterStatus(String.valueOf(yearInt-1), String.valueOf(yearInt-1)));
        filterStatusYear.add(new FilterStatus(String.valueOf(yearInt), String.valueOf(yearInt)));
        atSelectYear.setAdapter(new ArrayAdapter<FilterStatus>(getActivity(), android.R.layout.simple_list_item_1, filterStatusYear));

        List<FilterStatus> filterStatusMonth = new ArrayList<FilterStatus>();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            String month = months[i];
            filterStatusMonth.add(new FilterStatus(month, String.valueOf(i+1)));
        }
        atSelectMonth.setAdapter(new ArrayAdapter<FilterStatus>(getActivity(), android.R.layout.simple_list_item_1, filterStatusMonth));
    }

    private void getDataFromCustomFilter() {
        if (selectedMonth != null && selectedYear != null) {
            srlDashboard.setRefreshing(true);

            String queryPackage = "?start_month="+ selectedMonth.getStatusParam() +"&end_month="+ selectedMonth.getStatusParam() +"&year="+selectedYear.getStatusParam();
            DashboardPresenter.getReportSalesByPackage(getActivity(), queryPackage, DashboardFragment.this);

//            String query = "?year="+selectedYear.getStatusParam()+"&month="+selectedMonth.getStatusParam();
//            HomePresenter.getHomeDataByQuery(getActivity(), query, DashboardFragment.this);
        }
    }

    private int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    private int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentInteractionListener = (HomeFragment.OnFragmentInteractionListener) context;
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
}
