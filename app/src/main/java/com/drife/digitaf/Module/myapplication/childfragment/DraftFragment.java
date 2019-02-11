package com.drife.digitaf.Module.myapplication.childfragment;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.ViewPager;
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
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
        import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
        import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
        import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
        import com.drife.digitaf.KalkulatorKredit.SelectedData;
        import com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity.HasilPerhitungandraft;
        import com.drife.digitaf.Module.myapplication.adapter.DraftListAdapter;
        import com.drife.digitaf.Module.myapplication.model.DraftItem;
        import com.drife.digitaf.ORM.Controller.DealerController;
        import com.drife.digitaf.ORM.Controller.DraftController;
        import com.drife.digitaf.ORM.Controller.PackageRuleController;
        import com.drife.digitaf.ORM.Database.Draft;
        import com.drife.digitaf.ORM.Database.PackageRule;
        import com.drife.digitaf.R;
        import com.drife.digitaf.Service.Inquiry.InquiryPresenter;
        import com.drife.digitaf.Service.MyApplication.DraftCallback;
        import com.drife.digitaf.Service.MyApplication.DraftPresenter;
        import com.drife.digitaf.UIModel.UserSession;
        import com.drife.digitaf.retrofitmodule.Model.Dealer;
        import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
        import com.google.gson.Gson;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.Serializable;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.Date;
        import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class DraftFragment extends Fragment implements View.OnClickListener, DraftCallback {
    private static final String TAG = DraftFragment.class.getSimpleName();

    private ImageView imgSearch;
    private EditText edSearch;
    private RecyclerView rvDraft;
    private ProgressBar pbDraft;
    private RelativeLayout lyFrom, lyTo;
    private EditText edFrom, edTo;
    private SwipeRefreshLayout lyRefresh;

    List<PackageRule> packageRules = new ArrayList<>();
    List<com.drife.digitaf.ORM.Database.Dealer> dealers = new ArrayList<>();
    private DraftListAdapter draftListAdapter;

    private List<Draft> listItems = new ArrayList<>();
    private List<Draft> drafts = new ArrayList<>();
    private List<DraftItem> draftItemss = new ArrayList<>() ;
    private com.drife.digitaf.ORM.Database.Dealer dealer;

    UserSession userSession;

    String from = "", to = "";

    int REQUEST_CODE_UPDATE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.r_fragment_draft, container, false);

        initLayout(view);
        initData();

        return view;
    }

    private void initLayout(View view) {
        imgSearch = view.findViewById(R.id.imgSearch);
        edSearch = view.findViewById(R.id.edSearch);
        rvDraft = view.findViewById(R.id.rvDraft);
        pbDraft = view.findViewById(R.id.pbDraft);
        lyFrom = view.findViewById(R.id.lyFrom);
        lyTo = view.findViewById(R.id.lyTo);
        lyRefresh = view.findViewById(R.id.lyRefresh);
        edFrom = view.findViewById(R.id.edFrom);
        edTo = view.findViewById(R.id.edTo);

        imgSearch.setOnClickListener(this);
        edSearch.setOnClickListener(this);
        edFrom.setOnClickListener(this);
        edTo.setOnClickListener(this);

        userSession = SharedPreferenceUtils.getUserSession(getActivity());

        lyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                edTo.setText("");
                edSearch.setText("");
                edFrom.setText("");
                listItems.clear();
                getDraftList();
            }
        });

//        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_DONE) {
//                    searchData();
//                }
//
//                return false;
//            }
//        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    final String query = s.toString().toLowerCase().trim();
                    final ArrayList<Draft> filteredList = new ArrayList<>();

                    if(listItems != null){
                        for (int i = 0; i < listItems.size(); i++) {

                            final String text = listItems.get(i).getNamaCustomer().toLowerCase();
                            if (text.contains(query)) {
                                filteredList.add(listItems.get(i));
                            }
                        }
                    }

                draftListAdapter = new DraftListAdapter(getActivity(), filteredList, DraftFragment.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                ((LinearLayoutManager) layoutManager).setReverseLayout(true);
                ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
                rvDraft.setLayoutManager(layoutManager);
                rvDraft.setItemAnimator(new DefaultItemAnimator());
                rvDraft.setNestedScrollingEnabled(false);
                rvDraft.setAdapter(draftListAdapter);
                draftListAdapter.notifyDataSetChanged();
                }
        });
    }

    public void initData() {
        getDraftList();
    }

    public void searchData() {
            if (!edFrom.getText().toString().isEmpty() && !edTo.getText().toString().isEmpty()){

                drafts = DraftController.searchDate(from +" 00:00:00.000", to+" 23:59:59.999");
                for (int i = 0; i < drafts.size();i++){
                    Draft draft = drafts.get(i);
                    Log.d("resultByDate", draft.getLastSaved());
                }
            }

            listItems = drafts;
            draftListAdapter = new DraftListAdapter(getActivity(), listItems, DraftFragment.this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rvDraft.setLayoutManager(layoutManager);
            rvDraft.setItemAnimator(new DefaultItemAnimator());
            rvDraft.setNestedScrollingEnabled(false);
            rvDraft.setAdapter(draftListAdapter);

        }

    public void showCalendarFROM(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!editText.getText().toString().equals("")) {
            year = Integer.valueOf(editText.getText().toString().split("-")[2]);
            month = Integer.valueOf(editText.getText().toString().split("-")[1])-1;
            day = Integer.valueOf(editText.getText().toString().split("-")[0]);
        }

        currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear+1;
                        String date = "";
                        if (dayOfMonth < 10){
                            if (month < 10){
                                date = "0"+dayOfMonth+"-"+"0"+month+"-"+year;
                                from = year+"-"+"0"+month+"-"+"0"+dayOfMonth;
                            }else {
                                date = "0"+dayOfMonth+"-"+month+"-"+year;
                                from = year+"-"+month+"-"+"0"+dayOfMonth;
                            }
                        }else {
                            if (month < 10){
                                date = dayOfMonth+"-"+"0"+month+"-"+year;
                                from = year+"-"+"0"+month+"-"+dayOfMonth;
                            }else {
                                date = dayOfMonth+"-"+month+"-"+year;
                                from = year+"-"+month+"-"+dayOfMonth;
                            }
                        }
                        editText.setText(date);

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

    public void showCalendarTO(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!editText.getText().toString().equals("")) {
            year = Integer.valueOf(editText.getText().toString().split("-")[2]);
            month = Integer.valueOf(editText.getText().toString().split("-")[1])-1;
            day = Integer.valueOf(editText.getText().toString().split("-")[0]);
        }

        currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear+1;
                        String date = "";
//                        if (month < 10){
//                            date = dayOfMonth+"-"+"0"+month+"-"+year;
//                            to = year+"-"+"0"+month+"-"+dayOfMonth;
//                        }else {
//                            date = dayOfMonth+"-"+month+"-"+year;
//                            to = year+"-"+month+"-"+dayOfMonth;
//                        }
//                        editText.setText(date);
//
//                        searchData();
                        if (dayOfMonth < 10){
                            if (month < 10){
                                date = "0"+dayOfMonth+"-"+"0"+month+"-"+year;
                                to = year+"-"+"0"+month+"-"+"0"+dayOfMonth;
                            }else {
                                date = "0"+dayOfMonth+"-"+month+"-"+year;
                                to = year+"-"+month+"-"+"0"+dayOfMonth;
                            }
                        }else {
                            if (month < 10){
                                date = dayOfMonth+"-"+"0"+month+"-"+year;
                                to = year+"-"+"0"+month+"-"+dayOfMonth;
                            }else {
                                date = dayOfMonth+"-"+month+"-"+year;
                                to = year+"-"+month+"-"+dayOfMonth;
                            }
                        }
                        editText.setText(date);

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

    private void getDraftList(){
        drafts.clear();
        lyRefresh.setRefreshing(false);
        pbDraft.setVisibility(View.VISIBLE);
        rvDraft.setVisibility(View.GONE);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

        DraftPresenter.getAllDraft(getActivity(), this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edSearch:
                break;
//            case R.id.imgSearch:
//                searchData();
//                break;
            case R.id.edFrom:
                showCalendarFROM(edFrom);
                break;
            case R.id.edTo:
                showCalendarTO(edTo);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_UPDATE){
            if(resultCode == Activity.RESULT_OK){
                getDraftList();
            }
        }
    }

    public void showUploadPage(Draft draft, int pos) {
        Intent intent = new Intent(getActivity(), HasilPerhitungandraft.class);
        JSONObject jsonObject = null;
        InquiryParam inquiryParam = null;
        try {
            jsonObject = new JSONObject(draft.getData());
            Gson gson = new Gson();
            Log.d("json", draft.getData());
            inquiryParam= gson.fromJson(jsonObject.toString(), InquiryParam.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        packageRules = PackageRuleController.getPackageRulesCode(inquiryParam.getProduct_offering_code());
        if (packageRules.isEmpty()){
            Toast.makeText(getActivity(), "PackageRules Empty", Toast.LENGTH_SHORT).show();
        }else {
            if (userSession.getUser().getType().equals("so")){
                dealers = DealerController.getDealer(draftItemss.get(pos).dealer_id);
//                dealer = SelectedData.SelectedDealer;
                SelectedData.SelectedDealer = dealers.get(0);
                dealer = SelectedData.SelectedDealer;
                Log.d("dealer_id", dealer.getDealerGroupName());
            }
            intent.putExtra("data", (Serializable) inquiryParam);
            intent.putExtra("id", draft.getId());
            intent.putExtra("user_id", String.valueOf(draft.getUser_id()));
            intent.putExtra("draft", (Serializable) draft);
            Log.d("draft", draft.getIs_simulasi_budget());
            startActivityForResult(intent,REQUEST_CODE_UPDATE);
        }
    }

    @Override
    public void onSuccessGetDraft(List<DraftItem> draftItems) {
        Collections.reverse(draftItems);
        draftItemss = draftItems;
        listItems = DraftController.getDraftAll(""+userSession.getUser().getId());
        if (listItems.size() == 0) {
            int size_listitem = listItems.size() - 1;
            for (int i = 0; i < draftItems.size(); i++) {
                if (!draftItems.isEmpty()) {
                    if (i > size_listitem) {
                                Draft draft = new Draft();
                                draft.setNamaCustomer(draftItems.get(i).form_value.getCustomerName());
                                String param = JSONProcessor.toJSON(draftItems.get(i).form_value);
                                draft.setData(param);
                                draft.setCustomerId(""+userSession.getUser().getId());
                                draft.setLastSaved(draftItems.get(i).getUpdatedAt());
                                draft.setUser_id(String.valueOf(draftItems.get(i).getIdDraft()));
                                draft.setIs_corporate(draftItems.get(i).getIs_corporate());
                                draft.setIs_simulasi_paket(draftItems.get(i).getIs_simulasi_paket());
                                draft.setIs_simulasi_budget(draftItems.get(i).getIs_simulasi_budget());
                                draft.setIs_non_paket(draftItems.get(i).getIs_non_paket());
                                draft.setIs_npwp(draftItems.get(i).getIs_npwp());
                                draft.setPayment_type_service_package(draftItems.get(i).getPayment_type_service_package());
                                draft.setCoverage_type(draftItems.get(i).getCoverage_type());
                                drafts.add(draft);
                    } else {

                    }
                } else {
                    Log.d("draft", "Null Draft");
                }
            }
        }else {

        }
        new Insert_submit().execute(drafts);
    }

    @Override
    public void onFailedGetDraft(String message) {
        pbDraft.setVisibility(View.GONE);
            new Insert_submit().execute(drafts);
    }

    class Insert_submit extends AsyncTask<List<Draft>,Void,Void> {
        @Override
        protected Void doInBackground(List<Draft>... lists) {
            drafts = lists[0];
            if(drafts != null){
                DraftController.InsertDraft(drafts);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (userSession.getUser().getId() != null) {
                drafts = DraftController.getDraftName(""+userSession.getUser().getId());
            }else {
                drafts = DraftController.getDraftName("");
            }

            if (drafts != null) {
                pbDraft.setVisibility(View.GONE);
                rvDraft.setVisibility(View.VISIBLE);
                listItems = drafts;
                Collections.sort(listItems, new Comparator<Draft>() {
                    @Override
                    public int compare(Draft o1, Draft o2) {
                        return o1.getLastSaved().compareTo(o2.getLastSaved());
                    }
                });
                draftListAdapter = new DraftListAdapter(getActivity(), listItems, DraftFragment.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                ((LinearLayoutManager) layoutManager).setReverseLayout(true);
                ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
                rvDraft.setLayoutManager(layoutManager);
                rvDraft.setItemAnimator(new DefaultItemAnimator());
                rvDraft.setNestedScrollingEnabled(false);

                rvDraft.setAdapter(draftListAdapter);
            }
        }
    }

}
