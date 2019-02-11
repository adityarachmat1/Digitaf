package com.drife.digitaf.Module.inquiry.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.RecyclerViewUtility.EndlessRecyclerViewScrollListener;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.inquiry.fragment.adapter.InquiryListAdapter;
import com.drife.digitaf.Module.inquiry.fragment.model.FilterStatus;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Database.Dealer;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Inquiry.InquiryCallback;
import com.drife.digitaf.Service.Inquiry.InquiryPresenter;
import com.drife.digitaf.UIModel.UserSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class InquiryFragment extends Fragment implements View.OnClickListener, InquiryCallback{
    private String TAG = InquiryFragment.class.getSimpleName();
    private View rootView;

    private ImageView imgSearch, imgSearchSO;
    private ImageView imgBack;
    private EditText edSearch, edSearchSO;
    private EditText edFrom;
    private EditText edTo;
    private AutoCompleteTextView edStatus;
    private RecyclerView rvInquiry;
    private SwipeRefreshLayout lyRefresh;
    private LinearLayout layoutDealerName;
    private AutoCompleteTextView spinDealer;
    private ProgressBar pbInquiry;
    private TextView txtName, txtDealerName;
    private NestedScrollView nestedScrollView;
    private RelativeLayout rv_search_SO, rv_search;

    private InquiryListAdapter inquiryListAdapter;

    private List<InquiryItem> listItems = new ArrayList<>();
    List<FilterStatus> listStatus;

    boolean isVisibleFragment = false;
    private ProgressDialog progressDialog;
    private Calendar myCalendar = Calendar.getInstance();
    private String[] arrayStatus = new String[]{"All", "Wait", "Complete", "Expired"};

    int REQUEST_CODE_UPDATE = 3;

    String query = "";
    public String status = "inquiry";
    public String status_app = "";
    private UserSession userSession;
    private Dealer dealer;
    private FilterStatus filterStatus;

    private EndlessRecyclerViewScrollListener scrollListener;
    private int limit = 5;
    private int offset = 0;
    private int lastSize = 0;

    private boolean isSearch = false;
    private boolean isLoading = false;

    private Call call;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.r_fragment_inquiry,container,false);

        initLayout(rootView);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_UPDATE){
            if(resultCode == Activity.RESULT_OK){
                //getInquiryList();
                getInquiryListOnSubmit();
            }
        }
    }

    private void initLayout(View view) {
        imgSearch = view.findViewById(R.id.imgSearch);
        edSearch = view.findViewById(R.id.edSearch);
        edFrom = view.findViewById(R.id.edFrom);
        edTo = view.findViewById(R.id.edTo);
        edStatus = view.findViewById(R.id.edStatus);
        rvInquiry = view.findViewById(R.id.rvInquiry);
        imgBack = view.findViewById(R.id.imgBack);
        lyRefresh = view.findViewById(R.id.lyRefresh);
        layoutDealerName = view.findViewById(R.id.layoutDealerName);
        spinDealer = view.findViewById(R.id.spinDealer);
        pbInquiry = view.findViewById(R.id.pbInquiry);
        txtName = view.findViewById(R.id.txtName);
        txtDealerName = view.findViewById(R.id.txtCabang);
        nestedScrollView = view.findViewById(R.id.scrollView);
        rv_search = view.findViewById(R.id.rv_Search);
        rv_search_SO = view.findViewById(R.id.rv_SearchSO);
        imgSearchSO = view.findViewById(R.id.imgSearchSO);
        edSearchSO = view.findViewById(R.id.edSearchSO);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(3);
            }
        }

        initVariables();
        initListners();
        callfunctions();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisibleFragment = isVisibleToUser;

        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(3);
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

        if (userSession.getUser().getType().equals("so")) {
            layoutDealerName.setVisibility(View.VISIBLE);

            if (DealerController.getAllDealer() != null) {
                if (DealerController.getAllDealer().size() > 0) {
                    spinDealer.setAdapter(new ArrayAdapter<Dealer>(getActivity(), android.R.layout.simple_list_item_1, DealerController.getAllDealer()));
                }
            }
        }

//        if (userSession.getUser().getType().equals("spv")) {
//            rv_search_SO.setVisibility(View.VISIBLE);
//            rv_search.setVisibility(View.GONE);
//        }
//
//        if (userSession.getUser().getType().equals("bm")) {
//            rv_search_SO.setVisibility(View.VISIBLE);
//            rv_search.setVisibility(View.GONE);
//        }
    }

    private void initListners(){
        imgBack.setOnClickListener(this);
        edSearch.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        edFrom.setOnClickListener(this);
        edTo.setOnClickListener(this);
        edStatus.setOnClickListener(this);

        lyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //new getData().execute();
                listItems.clear();
                getInquiryList();
            }
        });

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    lyRefresh.setRefreshing(true);
                    listItems.clear();
                    searchData();
                }

                return false;
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    listItems.clear();
                    getInquiryList();
                }
            }
        });

        spinDealer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    spinDealer.showDropDown();
                }
            }
        });

        spinDealer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0 && isAdded()) {
                    try {
                        spinDealer.showDropDown();
                    }catch (Exception e){
                        LogUtility.logging(TAG,LogUtility.errorLog,"spinDealer.addTextChangedListener","Exception", e.getMessage());
                    }
                }
            }
        });

        spinDealer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                dealer = (Dealer)adapterView.getItemAtPosition(pos);
                lyRefresh.setRefreshing(true);
                listItems.clear();
                searchData();
            }
        });

        edStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*pbInquiry.setVisibility(View.VISIBLE);
                rvInquiry.setVisibility(View.GONE);*/

                lyRefresh.setRefreshing(true);

                filterStatus = (FilterStatus)parent.getItemAtPosition(position);
                if (filterStatus.getName().equalsIgnoreCase("ALL")) {
                    getInquiryList();
                } else {
                    lyRefresh.setRefreshing(true);
                    listItems.clear();
                    filterStatus = listStatus.get(position);
                    searchData();
                }
            }
        });

        initSpinnerStatus();
    }

    private void callfunctions(){
        configRecyclerview();
        getInquiryList();
    }

    private void initSpinnerStatus() {
        edStatus.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        listStatus = new ArrayList<>();
        listStatus.add(new FilterStatus("ALL", "inquiry"));
        listStatus.add(new FilterStatus("APPROVE", FilterStatus.approve));
        listStatus.add(new FilterStatus("CANCEL", FilterStatus.cancel));
        listStatus.add(new FilterStatus("CREDIT REVIEW", FilterStatus.credit_review));
        listStatus.add(new FilterStatus("DATA ENTRY", FilterStatus.data_entry));
        listStatus.add(new FilterStatus("REJECT", FilterStatus.reject));
        //listStatus.add(new FilterStatus("RE-INPUT", FilterStatus.re_input));
        listStatus.add(new FilterStatus("REQUEST", FilterStatus.re_input));
        listStatus.add(new FilterStatus("VALID", FilterStatus.valid));
        listStatus.add(new FilterStatus("WAIT", FilterStatus.wait));

        if (userSession.getUser().getType().equals("so")) {
            listStatus.add(new FilterStatus("REQUEST DOC", FilterStatus.request_doc));
            listStatus.add(new FilterStatus("REQUEST NEGO", FilterStatus.request_nego));
            listStatus.add(new FilterStatus("REQUEST SURVEY", FilterStatus.request_survey));
        }

        edStatus.setAdapter(new ArrayAdapter<FilterStatus>(getActivity(), android.R.layout.simple_list_item_1, listStatus));
    }

    public void getInquiryList(){
        if(call != null){
            call.cancel();
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*pbInquiry.setVisibility(View.VISIBLE);
                rvInquiry.setVisibility(View.GONE);*/
                lyRefresh.setRefreshing(true);
            }
        });


        //listItems = new ArrayList<>();
        InquiryPresenter.getAllInquiry(getActivity(), this,limit,listItems.size());
    }

    public void getInquiryListOnSubmit(){
        Log.d("singo", "calledddddd");
        if(call != null){
            call.cancel();
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*pbInquiry.setVisibility(View.VISIBLE);
                rvInquiry.setVisibility(View.GONE);*/
                lyRefresh.setRefreshing(true);
            }
        });

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        listItems.clear();
                        getAllInquiry();
                    }
                },
                1000
        );
        //listItems = new ArrayList<>();
        //InquiryPresenter.getAllInquiry(getActivity(), this,limit,listItems.size());
    }

    private void getAllInquiry(){
        InquiryPresenter.getAllInquiry(getActivity(), this,limit,listItems.size());
    }

    public void searchData() {
        if(call != null){
            call.cancel();
        }
        /*if(isSearch == false){
            listItems.clear();
        }*/
        query = "";

        query = "?status="+status;

        if (filterStatus != null) {
            status_app = filterStatus.getStatusParam();
            query = query+"&status_app="+status_app;
        }

        String search1 = edSearch.getText().toString();
        if (search1.length() > 0) {
            query = query + "&search="+search1;
        }

        String search2 = edFrom.getText().toString();
        String search3 = edTo.getText().toString();

        if (dealer != null) {
            query = query + "&dealer_id=" + dealer.getDealerId();
        }

        if (search2.length() > 0 && search3.length() > 0) {
            if (!validateSearchByDate(search2, search3)) {
                Toast.makeText(getActivity(), "Tanggal from tidak boleh lebih dari tanggal to.", Toast.LENGTH_LONG).show();
            } else {
                if (search2.length() > 0) {
                    query = query + "&start_date=" + TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", search2);
                }

                if (search3.length() > 0) {
                    query = query + "&end_date=" + TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", search3);
                }
            }
        }

        Log.d(TAG, "Query Inquiry "+query);

        if (!query.equals("")) {
            /*pbInquiry.setVisibility(View.VISIBLE);
            rvInquiry.setVisibility(View.GONE);*/

            //lyRefresh.setRefreshing(true);
            isSearch = true;
            InquiryPresenter.getInquiryByQuery(getActivity(), query+"&limit="+limit+"&offset="+listItems.size(), InquiryFragment.this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                ((Main)getActivity()).view_pager.setCurrentItem(((Main)getActivity()).adapter.back());
                break;
            case R.id.edSearch:

                break;
            case R.id.imgSearch:
                lyRefresh.setRefreshing(true);
                listItems.clear();
                searchData();
                break;
            case R.id.edStatus:
                edStatus.showDropDown();
                break;
            case R.id.edFrom:
                showCalendar(edFrom);
                break;
            case R.id.edTo:
                showCalendar(edTo);
                break;

        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onSuccessGetInquiry(List<InquiryItem> inquiryItems) {
        lyRefresh.setRefreshing(false);
        /*pbInquiry.setVisibility(View.GONE);
        rvInquiry.setVisibility(View.VISIBLE);*/

        /*if (inquiryListAdapter != null) {
            inquiryListAdapter.clear();
        }*/

        if (inquiryItems.size() > 0) {
            /*if(listItems.size()==0){
                listItems = inquiryItems;
            }else{
                for (int i=0; i<inquiryItems.size(); i++){
                    listItems.add(inquiryItems.get(i));
                }
            }*/

            /*if(isSearch){
                listItems = inquiryItems;
            }else{
                for (int i=0; i<inquiryItems.size(); i++){
                    listItems.add(inquiryItems.get(i));
                }
            }*/

            for (int i=0; i<inquiryItems.size(); i++){
                listItems.add(inquiryItems.get(i));
            }

            lastSize = listItems.size();

            /*inquiryListAdapter = new InquiryListAdapter(getActivity(), listItems, this);
            //Set layout manager type for recyclerview
            //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rvInquiry.setLayoutManager(layoutManager);
            rvInquiry.setItemAnimator(new DefaultItemAnimator());
            rvInquiry.setNestedScrollingEnabled(false);
            rvInquiry.setAdapter(inquiryListAdapter);

            scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    loadNextDataFromApi();
                }
            };
            rvInquiry.setOnScrollListener(scrollListener);*/

            inquiryListAdapter.notifyDataSetChanged();
            //offset = listItems.size();
        } else {
            listItems.clear();
            inquiryListAdapter.notifyDataSetChanged();
        }
        isLoading = false;
    }

    @Override
    public void onFailedGetInquiry(String message) {
        lyRefresh.setRefreshing(false);
        Log.d("messageInquiry", message);
        /*pbInquiry.setVisibility(View.GONE);
        rvInquiry.setVisibility(View.VISIBLE);*/

        /*if (inquiryListAdapter != null) {
            inquiryListAdapter.clear();
        }*/
        isLoading = false;
    }

    public void showUploadPage(InquiryItem inquiryItem){
//        Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
//        intent.putExtra("data",inquiryItem);
//        intent.putExtra("isUpdate",1);
//        startActivityForResult(intent,REQUEST_CODE_UPDATE);
//    }
        userSession = SharedPreferenceUtils.getUserSession(getActivity());
        Log.d("user", userSession.getUser().getType());

        ArrayList<Integer> countDays = new ArrayList<>();
        ArrayList<Integer> promisedays = new ArrayList<>();
        if (inquiryItem.getFormValue().getDocument().getOptional() != null) {
            for (int i = 0; i < inquiryItem.getFormValue().getDocument().getOptional().size(); i++) {
                if (inquiryItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {

                } else {
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
                    String inputString1 = chageDateFormat(inquiryItem.getFormValue().getDocument().getOptional().get(i).getPromise_date(), "");
                    Log.d("inputString1", inputString1);
                    if (inputString1.equals("")) {

                    } else {
                        promisedays.add(i);
                        try {
                            Date date1 = myFormat.parse(inputString1);
                            Date date2 = myFormat.parse(myFormat.format(new Date()));
                            long diff = date1.getTime() - date2.getTime();
                            int Days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            if (Days < 0) {

                            } else {
                                countDays.add(Days);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (promisedays.isEmpty()){
                if (countDays.isEmpty()) {
                    countDays.clear();
                    promisedays.clear();
                }else {
                    countDays.clear();
                    promisedays.clear();
                    Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
                    intent.putExtra("data", inquiryItem);
                    intent.putExtra("isUpdate", 1);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE);
                }
            }else {
                if (countDays.isEmpty()){
                    if (userSession.getUser().getType().equals("so")){
                        countDays.clear();
                        promisedays.clear();
                        Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
                        intent.putExtra("data", inquiryItem);
                        intent.putExtra("isUpdate", 1);
                        startActivityForResult(intent, REQUEST_CODE_UPDATE);
                    }else {
                        countDays.clear();
                        promisedays.clear();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                .setMessage("Aplikasi ini telah melewati Tanggal Promise Date. Silahkan menghubungi Sales Officer TAF.")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.show();
                    }
                }else {
                    countDays.clear();
                    promisedays.clear();
                    Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
                    intent.putExtra("data", inquiryItem);
                    intent.putExtra("isUpdate", 1);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE);
                }
            }
        }else {

        }
    }

    class getData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getInquiryList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lyRefresh.setRefreshing(false);
        }
    }

    public void showCalendar(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!editText.getText().toString().equals("")) {
            year = Integer.valueOf(editText.getText().toString().split("/")[2]);
            month = Integer.valueOf(editText.getText().toString().split("/")[1])-1;
            day = Integer.valueOf(editText.getText().toString().split("/")[0]);
        }

        currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        editText.setText(date);
                        lyRefresh.setRefreshing(true);
                        listItems.clear();
                        searchData();
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(year, month, day)
                .setDateRange(null, null)
                .setDoneText("Yes")
                .setCancelText("No");
        cdp.show((getActivity().getSupportFragmentManager()),"Promise Date");
    }

    public boolean validateSearchByDate(String s1, String s2) {
        boolean condition1 = Integer.valueOf(s1.split("/")[0]) > Integer.valueOf(s2.split("/")[0]);
        boolean condition2 = Integer.valueOf(s1.split("/")[1]) > Integer.valueOf(s2.split("/")[1]);
        boolean condition3 = Integer.valueOf(s1.split("/")[2]) > Integer.valueOf(s2.split("/")[2]);

        if (condition3) {
            return false;
        } else if (condition2) {
            return false;
        } else if (condition1) {
            return false;
        }

        return true;
    }

    public void updateListByStatus (String status) {
        if (listStatus != null) {
            for (int i = 0;i < listStatus.size();i++) {
                if (listStatus.get(i).getStatusParam().equals(status)) {
                    filterStatus = listStatus.get(i);
                    edStatus.setText(filterStatus.getName());
                }
            }
        }

        /*pbInquiry.setVisibility(View.VISIBLE);
        rvInquiry.setVisibility(View.GONE);*/

        lyRefresh.setRefreshing(true);

        if (filterStatus.getName().equalsIgnoreCase("ALL")) {
            getInquiryList();
        } else {
            lyRefresh.setRefreshing(true);
            listItems.clear();
            searchData();
        }
    }

    public void loadNextDataFromApi() {
        InquiryPresenter.getAllInquiry(getActivity(), new InquiryCallback() {
            @Override
            public void onSuccessGetInquiry(List<InquiryItem> inquiryItems) {
                lyRefresh.setRefreshing(false);
                for (int i=0; i<inquiryItems.size(); i++){
                    listItems.add(inquiryItems.get(i));
                }
                //inquiryListAdapter.notifyItemRangeInserted(0,inquiryItems.size());
                inquiryListAdapter.notifyDataSetChanged();
                //offset = listItems.size();
                isLoading = false;
            }

            @Override
            public void onFailedGetInquiry(String message) {
                lyRefresh.setRefreshing(false);
                LogUtility.logging(TAG, LogUtility.errorLog,"loadNextDataFromApi",message);
                isLoading = false;
            }
        }, limit, listItems.size());
    }

    private void configRecyclerview(){
        inquiryListAdapter = new InquiryListAdapter(getActivity(), listItems, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvInquiry.setLayoutManager(layoutManager);
        rvInquiry.setItemAnimator(new DefaultItemAnimator());
        rvInquiry.setNestedScrollingEnabled(false);
        rvInquiry.setAdapter(inquiryListAdapter);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int scrollY, int i2, int oldScrollY) {

                if(nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null) {
                    if ((scrollY >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1).getMeasuredHeight() - nestedScrollView.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        if(!isLoading){
                            if(isSearch){
                                lyRefresh.setRefreshing(true);
                                searchData();
                            }else{
                                lyRefresh.setRefreshing(true);
                                loadNextDataFromApi();
                            }
                            isLoading = true;
                        }
                    }
                }
            }
        });
    }

    public String chageDateFormat(String date, String formatDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
        SimpleDateFormat newFormat = new SimpleDateFormat(formatDate.equals("sent") ? "dd MM yyyy" : "dd MM yyyy");

        try {
            return newFormat.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}