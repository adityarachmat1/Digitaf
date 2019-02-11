package com.drife.digitaf.Module.SimulasiKredit.ResultSimulasi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.Module.InputKredit.InputItemKredit.Activity.InputItemKreditActivity;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.Adapter.AdapterADDB;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.Adapter.AdapterADDBLong;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.Adapter.AdapterADDM;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.Adapter.AdapterADDMLong;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.TableData;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.orm.SugarContext;

import org.apache.commons.math3.geometry.euclidean.twod.Line;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultSimulasiActivity extends AppCompatActivity {
    private String TAG = ResultSimulasiActivity.class.getSimpleName();

    @BindView(R.id.rv_onloan)
    RecyclerView rv_onloan;
    @BindView(R.id.rv_prepaid)
    RecyclerView rv_prepaid;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;
    @BindView(R.id.lyADDM)
    LinearLayout lyADDM;

    private TableData tableData;
    private AdapterADDB adapter;
    private AdapterADDM adapterPrepaid;
    private AdapterADDBLong adapterADDBLong;
    private AdapterADDMLong adapterADDMLong;

    int REQUEST_CODE_SIMULASI = 1;
    int isPaket = -1;
    String packageCode = "";
    String namaPaket = "";
    int isBudget = -1;
    int isTDP = -1;

    private UserSession userSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_simulasi);
        ButterKnife.bind(this);
        SugarContext.init(ResultSimulasiActivity.this);
        initVariables();
        initListeners();
        callFunctions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SIMULASI){
            if(resultCode == RESULT_OK){
                Intent intent = null;

                if (data != null) {
                    if (data.getBooleanExtra("is_draft", false)) {
                        intent = data;
                    }else if(data.getBooleanExtra("is_success_submit", false)){
                        intent = data;
                    }
                }

                if (intent != null) {
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_OK);
                }

//                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void initVariables(){
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

    private void initListeners(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callFunctions(){
        configureList();
    }

    private void configureList(){
        rv_onloan.setNestedScrollingEnabled(false);
        rv_onloan.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_onloan.setHasFixedSize(false);

        rv_prepaid.setNestedScrollingEnabled(false);
        rv_prepaid.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_prepaid.setHasFixedSize(false);

        setData();
    }

    private void getIntentData(){
        tableData = (TableData) getIntent().getSerializableExtra("data");
        isPaket = getIntent().getIntExtra("isPaket",-1);
        packageCode = getIntent().getStringExtra("packageCode");
        namaPaket = getIntent().getStringExtra("packageName");
        isBudget = getIntent().getIntExtra("isBudget",-1);
        isTDP = getIntent().getIntExtra("isTDP",-1);

        LogUtility.logging(TAG,LogUtility.infoLog,"getIntentData","tableData", JSONProcessor.toJSON(tableData));
    }

    private void setData(){
        getIntentData();
        List<Tenor> tenorList = tableData.getTenors();
        List<Double> bunga = tableData.getBunga();
        List<Double> bungaADDM = tableData.getBungaADDM();
        String selectedPackage = tableData.getSelectedPackage();
        String selectedPackageCode = tableData.getSelectedPackageCode();

        if(isBudget == 1){
            List<Long> listTdpADDB = tableData.getTDPADDBLong();
            List<Long> listInstallmentADDB = tableData.getInstallmentADDBLong();
            List<Long> listTdpADDM = tableData.getTDPADDMLong();
            List<Long> listInstallmentADDM = tableData.getInstallmentADDMLong();
            List<Long> listDpMurniADDB = tableData.getDpMurniADDB();
            List<Long> listDpMurniADDM = tableData.getDpMurniADDM();
            List<Double> listDpMurniPercentageADDB = tableData.getDpMurniPercentageADDB();
            List<Double> listDpMurniPercentageADDM = tableData.getDpMurniPercentageADDM();

            adapterADDBLong = new AdapterADDBLong(getApplicationContext(),listTdpADDB,listInstallmentADDB,tenorList,bunga,selectedPackage,selectedPackageCode,listDpMurniADDB,listDpMurniPercentageADDB);
            rv_onloan.setAdapter(adapterADDBLong);

            if(isTDP != 1){
                adapterADDMLong = new AdapterADDMLong(getApplicationContext(),listTdpADDM,listInstallmentADDM,tenorList,bungaADDM,selectedPackage,selectedPackageCode,listDpMurniADDM,listDpMurniPercentageADDM);
                rv_prepaid.setAdapter(adapterADDMLong);
            }else{
                lyADDM.setVisibility(View.GONE);
            }

        }else{
            List<Long> listTdpOnloan = tableData.getTDPOnloan();
            List<Long> listCicilanOnloan = tableData.getCicilanOnloan();
            List<Long> listTdpPrepaid = tableData.getTDPPrepaid();
            List<Long> listCicilanPrepaid = tableData.getCicilanPrepaid();

            Log.d("singo","list tdp onloan : "+JSONProcessor.toJSON(listTdpOnloan));
            Log.d("singo","list tdp onloan : "+JSONProcessor.toJSON(listCicilanOnloan));
            Log.d("singo","list tdp onloan : "+JSONProcessor.toJSON(tenorList));
            adapter = new AdapterADDB(getApplicationContext(),listTdpOnloan,listCicilanOnloan,tenorList,
                    bunga,selectedPackage,selectedPackageCode,Double.parseDouble(SelectedData.DpPercentage));
            rv_onloan.setAdapter(adapter);
            adapterPrepaid = new AdapterADDM(getApplicationContext(),listTdpPrepaid,listCicilanPrepaid,tenorList,
                    bungaADDM,selectedPackage,selectedPackageCode,Double.parseDouble(SelectedData.DpPercentage));
            rv_prepaid.setAdapter(adapterPrepaid);
        }
    }

    public void showInputData(){
        Intent intent = new Intent(ResultSimulasiActivity.this, InputItemKreditActivity.class);
        if(isPaket == 1){
            intent.putExtra("isPaket",1);
            intent.putExtra("packageCode",packageCode);
            intent.putExtra("packageName",namaPaket);
        }else if(isBudget == 1){
            intent.putExtra("isBudget",1);
        }
        startActivityForResult(intent,REQUEST_CODE_SIMULASI);
    }
}
