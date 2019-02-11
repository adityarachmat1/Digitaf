package com.drife.digitaf.Module.myapplication.childfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.inquiry.fragment.InquiryFragment;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.Module.myapplication.adapter.NeedActionListAdapter;
import com.drife.digitaf.Module.myapplication.adapter.OutboxListAdapter;
import com.drife.digitaf.Module.myapplication.model.NeedActionItem;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Inquiry.InquiryPresenter;
import com.drife.digitaf.Service.MyApplication.DraftPresenter;
import com.drife.digitaf.Service.MyApplication.NeedActionCallback;
import com.drife.digitaf.Service.MyApplication.NeedActionPresenter;
import com.drife.digitaf.UIModel.UserSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class NeedActionFragment extends Fragment implements NeedActionCallback, View.OnClickListener {
    private static final String TAG = NeedActionFragment.class.getSimpleName();

    private ImageView imgSearch;
    private EditText edSearch;
    private RecyclerView rvNeedAction;
    private ProgressBar pbNeedAction;
    private RelativeLayout lyFrom, lyTo;
    private TextView edFrom, edTo;
    private SwipeRefreshLayout lyRefresh;
    private NestedScrollView nestedScrollView;

    private UserSession userSession;
    private NeedActionListAdapter needActionListAdapter;

    private List<InquiryItem> listItems = new ArrayList<>();
    private List<InquiryItem> listFiltered = new ArrayList<>();

    String query = "";

    int REQUEST_CODE_UPDATE = 2;
    int limit = 5;
    private boolean isSearch = false;
    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.r_fragment_needaction, container, false);

        initLayout(view);
        initData();

        return view;
    }

    private void initLayout(View view) {
        imgSearch = view.findViewById(R.id.imgSearch);
        edSearch = view.findViewById(R.id.edSearch);
        rvNeedAction = view.findViewById(R.id.rvNeedAction);
        pbNeedAction = view.findViewById(R.id.pbNeedAction);
        lyFrom = view.findViewById(R.id.lyFrom);
        lyTo = view.findViewById(R.id.lyTo);
        edFrom = view.findViewById(R.id.edFrom);
        edTo = view.findViewById(R.id.edTo);
        lyRefresh = view.findViewById(R.id.lyRefresh);
        nestedScrollView = view.findViewById(R.id.scrollView);

        imgSearch.setOnClickListener(this);
        lyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItems.clear();
                initData();
            }
        });

        edFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                if (!edFrom.getText().toString().equals("")) {
                    year = Integer.valueOf(edFrom.getText().toString().split("/")[2]);
                    month = Integer.valueOf(edFrom.getText().toString().split("/")[1])-1;
                    day = Integer.valueOf(edFrom.getText().toString().split("/")[0]);
                }

                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                edFrom.setText(date);
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
        });

        edTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                if (!edTo.getText().toString().equals("")) {
                    year = Integer.valueOf(edTo.getText().toString().split("/")[2]);
                    month = Integer.valueOf(edTo.getText().toString().split("/")[1])-1;
                    day = Integer.valueOf(edTo.getText().toString().split("/")[0]);
                }

                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                edTo.setText(date);
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
                    initData();
                }
            }
        });
    }

    public void initData() {
        /*pbNeedAction.setVisibility(View.VISIBLE);
        rvNeedAction.setVisibility(View.GONE);*/
        lyRefresh.setRefreshing(false);

        configureRecyclerView();
        getNeedActionList();
    }

    public void searchData() {
        query = "";

        String search2 = edFrom.getText().toString();
        String search3 = edTo.getText().toString();

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

        Log.d(TAG, "Query "+query);

        if (!query.equals("")) {
            /*pbNeedAction.setVisibility(View.VISIBLE);
            rvNeedAction.setVisibility(View.GONE);*/

            isSearch = true;
            NeedActionPresenter.getNeedActionByQuery(getActivity(), query+"&limit="+limit+"&offset="+listItems.size(), NeedActionFragment.this);
        }
    }

    private void getNeedActionList(){
        lyRefresh.setRefreshing(true);
        NeedActionPresenter.getAllNeedAction(getActivity(), this,limit,listItems.size());
    }

    private void loadMoreNeedAction(){
        lyRefresh.setRefreshing(true);
        NeedActionPresenter.getAllNeedAction(getActivity(), new NeedActionCallback() {
            @Override
            public void onSuccessGetNeedAction(List<InquiryItem> needActionItems) {
                lyRefresh.setRefreshing(false);

                if (needActionItems.size() > 0) {
                    List<InquiryItem> ls = new ArrayList<>();
                    for (int i=0; i<needActionItems.size(); i++){
                        listItems.add(needActionItems.get(i));
                        ls.add(needActionItems.get(i));
                    }
                    filteringListItem(ls);
                    needActionListAdapter.notifyDataSetChanged();
                }
                isLoading = false;
            }

            @Override
            public void onFailedGetNeedAction(String message) {
                lyRefresh.setRefreshing(false);
                isLoading = false;
            }
        }, limit, listItems.size());
    }

    @Override
    public void onSuccessGetNeedAction(List<InquiryItem> needActionItems) {
        /*pbNeedAction.setVisibility(View.GONE);
        rvNeedAction.setVisibility(View.VISIBLE);*/
        lyRefresh.setRefreshing(false);

        if (needActionItems.size() > 0) {

            for (int i=0; i<needActionItems.size(); i++){
                listItems.add(needActionItems.get(i));
            }
            filteringListItem(listItems);
            needActionListAdapter.notifyDataSetChanged();
        }else{
            listItems.clear();
            listFiltered.clear();
            needActionListAdapter.notifyDataSetChanged();
        }
        isLoading = false;
    }

    @Override
    public void onFailedGetNeedAction(String message) {
        /*pbNeedAction.setVisibility(View.GONE);
        rvNeedAction.setVisibility(View.VISIBLE);*/
        lyRefresh.setRefreshing(false);
        isLoading = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edSearch:
                break;
            case R.id.imgSearch:
                listItems.clear();
                searchData();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_UPDATE){
            if(resultCode == Activity.RESULT_OK){
//                getNeedActionList();
                listItems.clear();
                initData();
            }
        }
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

    public void showUploadPage(InquiryItem inquiryItem) {

        userSession = SharedPreferenceUtils.getUserSession(getActivity());

        if (userSession.getUser().getType().equals("so")) {

        } else {

        }

        ArrayList<Integer> countDays = new ArrayList<>();
        if (inquiryItem.getFormValue().getDocument().getOptional() != null) {
            for (int i = 0; i < inquiryItem.getFormValue().getDocument().getOptional().size(); i++) {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
                String inputString1 = chageDateFormat(inquiryItem.getFormValue().getDocument().getOptional().get(i).getPromise_date(), "");
                Log.d("inputString1", inputString1);
                if (inputString1.equals("")){

                }else {
                    try {
                        Date date1 = myFormat.parse(inputString1);
                        Date date2 = myFormat.parse(myFormat.format(new Date()));
                        long diff = date1.getTime() - date2.getTime();
                        int Days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                        System.out.println("Days: " + Days);
                        if (Days < 0) {

                        }else {
                            countDays.add(Days);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (countDays.isEmpty()){
                if (userSession.getUser().getType().equals("so")) {
                    countDays.clear();
                    Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
                    intent.putExtra("data", inquiryItem);
                    intent.putExtra("isUpdate", 1);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE);
                }else {
                    countDays.clear();
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
                Log.d("countdaysSize", String.valueOf(countDays.size()));
                countDays.clear();
                Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
                intent.putExtra("data", inquiryItem);
                intent.putExtra("isUpdate", 1);
                startActivityForResult(intent, REQUEST_CODE_UPDATE);
            }
        }else {

        }
    }

    private void configureRecyclerView(){
        needActionListAdapter = new NeedActionListAdapter(getActivity(), listFiltered,this);
        //Set layout manager type for recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvNeedAction.setLayoutManager(layoutManager);
        rvNeedAction.setItemAnimator(new DefaultItemAnimator());
        rvNeedAction.setNestedScrollingEnabled(false);
        rvNeedAction.setAdapter(needActionListAdapter);

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
                                loadMoreNeedAction();
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

    private void filteringListItem(List<InquiryItem> list){
        for(int i=0; i<list.size(); i++){
            InquiryItem inquiryItem = list.get(i);
            String status = inquiryItem.getSubmit_status();
            if(!status.equalsIgnoreCase("failed")){
                listFiltered.add(inquiryItem);
            }
        }
    }
}
