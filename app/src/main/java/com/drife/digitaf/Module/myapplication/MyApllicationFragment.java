package com.drife.digitaf.Module.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.Offline.SubmitService;
import com.drife.digitaf.Module.myapplication.adapter.MyApplicationTabAdapter;
import com.drife.digitaf.ORM.Controller.Outbox_submitController;
import com.drife.digitaf.ORM.Database.Outbox_submit;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.UIModel.UserSession;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class MyApllicationFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = MyApllicationFragment.class.getSimpleName();

    private ImageView imgBack;
    private LinearLayout llUploadAll;
    private TextView txtName, txtCabang;
    private TabLayout tabLayout;
    ViewPager viewPager;

    private MyApplicationTabAdapter myApplicationTabAdapter;

    boolean isVisibleFragment = false;
    UserSession userSession;
    private int index = 0;
    private Long id;
    List<Outbox_submit> outbox_submits = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ArrayList<String> arraySuccess = new ArrayList<>();
    private ArrayList<String> arrayFailed = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.r_fragment_myapplication, container, false);
        progressDialog = DialogUtility.createProgressDialog(getActivity(),"","Please wait...");
        initLayout(view);
        initTab();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(2);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisibleFragment = isVisibleToUser;

        if (isAdded()) {
            if (isVisibleFragment) {
                Log.i("IsRefresh", "Yes");
                ((Main)getActivity()).adapter.forward(2);
            }
        }
    }

    private void initLayout(View view) {
        imgBack = view.findViewById(R.id.imgBack);
        llUploadAll = view.findViewById(R.id.llUploadAll);
        txtName = view.findViewById(R.id.txtName);
        txtCabang = view.findViewById(R.id.txtCabang);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        userSession = SharedPreferenceUtils.getUserSession(getActivity());

        if (userSession.getUser().getProfile().getFullname() != null) {
            txtName.setText(userSession.getUser().getProfile().getFullname());
        }

        if (userSession.getUser().getType().equals("so")) {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getBranchName() != null) {
                    txtCabang.setText(userSession.getUser().getResponseConfins().getBranchName());
                }
            }
        } else {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getDealerName() != null) {
                    txtCabang.setText(userSession.getUser().getResponseConfins().getDealerName());
                }
            }
        }

        imgBack.setOnClickListener(this);
        llUploadAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                ((Main)getActivity()).view_pager.setCurrentItem(((Main)getActivity()).adapter.back());
                break;
            case R.id.llUploadAll:
//                addResultValues();
                break;
        }
    }

    private void initTab() {
        myApplicationTabAdapter = new MyApplicationTabAdapter(getActivity().getSupportFragmentManager(), getActivity());
        viewPager.setAdapter(myApplicationTabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(myApplicationTabAdapter.getTabView(i));

            TextView txtCountNotif = tabLayout.getTabAt(i).getCustomView().findViewById(R.id.txtCountNotif);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    llUploadAll.setVisibility(View.VISIBLE);
                } else {
                    llUploadAll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setCurrentPage (int pos) {
        if (viewPager != null) {
            viewPager.setCurrentItem(pos);
        }
    }

    private void addResultValues() {
        progressDialog.show();
        outbox_submits = Outbox_submitController.getOutboxsumbit();
        if (outbox_submits.size()>0){
            Log.d("getUser_id", outbox_submits.get(0).getUser_id());
            sendInquiry(index);
        }else {
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setMessage("Data upload kosong")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }

    }

    private void sendInquiry(final int indexSend) {
        if (outbox_submits.size() > 0 && indexSend < outbox_submits.size()) {
            SubmitPresenter.submit(getActivity(), new SubmitCallback() {
                        @Override
                        public void onSuccessSubmit(String formId) {
                            id = outbox_submits.get(indexSend).getId();
                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, id);
                            outbox_submit1.delete();
                            index = index + 1;

                            arraySuccess.add("1");
                            sendInquiry(index);
                        }

                        @Override
                        public void onFailedSubmit(String message) {
//                            Toast.makeText(SubmitService.this,"Failed submit", Toast.LENGTH_SHORT).show();
                            Long ids = outbox_submits.get(indexSend).getId();
                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, ids);
                            outbox_submit1.setStatus("failed");
                            Log.d("failedSumbit", message);
                            arrayFailed.add("1");
                            index = index + 1;
                            sendInquiry(index);
                        }
                    },
                    "inquiry", outbox_submits.get(indexSend).getForm(),  outbox_submits.get(indexSend).getAttach(), outbox_submits.get(indexSend).getUser_id(),outbox_submits.get(indexSend).getAttachPDF());
        } else {
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setMessage("Sukses Upload : "+ arraySuccess.size() + "\n"
                    + "Gagal upload : " + arrayFailed.size()+ "\n"
                    +"Refresh Outbox. Terima kasih")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            arraySuccess.clear();
                            arrayFailed.clear();
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
            index = 0;
        }
    }
}
