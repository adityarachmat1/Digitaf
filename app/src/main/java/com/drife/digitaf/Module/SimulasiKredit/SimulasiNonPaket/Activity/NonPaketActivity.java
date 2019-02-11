package com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.Currency.CurrencyFormat;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AsuransiPremi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.CarModel;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.Master.AsuransiPremiMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.BiayaTambahanMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.CarModelMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.DepresiasiMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.PCLMaster;
import com.drife.digitaf.KalkulatorKredit.Model.OTRDepresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.SimulasiKredit.ResultSimulasi.Activity.ResultSimulasiActivity;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.TableData;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiPaket.Activity.SimulasiPaketActivity;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Controller.TenorController;
import com.drife.digitaf.ORM.Database.CoverageInsurance;
import com.drife.digitaf.ORM.Database.Dealer;
import com.drife.digitaf.ORM.Database.PackageRule;
import com.drife.digitaf.ORM.Model.Car;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.google.gson.Gson;
import com.orm.SugarContext;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NonPaketActivity extends AppCompatActivity{
    private String TAG = NonPaketActivity.class.getSimpleName();

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.bt_simulasi)
    Button btSimulasi;
    @BindView(R.id.spinModel)
    Spinner spinModel;
    @BindView(R.id.etDp)
    EditText etDp;
    @BindView(R.id.etPercentage)
    EditText etPercentage;
    @BindView(R.id.spinCovInsurance)
    Spinner spinCovInsurance;
    @BindView(R.id.etOtr)
    EditText etOtr;
    @BindView(R.id.atModel)
    AutoCompleteTextView atModel;
    @BindView(R.id.atDealer)
    AutoCompleteTextView atDealer;
    @BindView(R.id.lyDealerName)
    LinearLayout lyDealerName;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;

    /*Data*/
    List<CarModel> carMaster = new ArrayList<>();
    List<com.drife.digitaf.ORM.Database.CarModel> cars = new ArrayList<>();
    List<String> carModel = new ArrayList<>();

    List<String> coverage = new ArrayList<>();
    List<Depresiasi> depresiasis = new ArrayList<>();
    //List<com.drife.digitaf.ORM.Database.Depresiasi> depresiasis = new ArrayList<>();
    //List<AsuransiPremi> asuransiPremis;
    List<Tenor> tenor = new ArrayList<>();
    //List<Double> PCL = new ArrayList<>();
    double PCL = 0.0;
    List<Integer> biayaAdmin = new ArrayList<>();
    List<Integer> biayaAdminADDM = new ArrayList<>();
    List<Integer> polis = new ArrayList<>();
    String coverageAsuransi = "";

    List<OTRDepresiasi> otrDepresiasis = new ArrayList<>();
    List<Double> premiAmountPercentage;
    List<Long> premiAmount;
    List<Long> premiAmountSumUp;
    List<Long> pokokHutang;
    List<Double> bungaADDB = new ArrayList<>();
    List<Double> bungaADDM = new ArrayList<>();
    List<Long> bungaAmount;
    List<Long> totalHutang;
    List<Long> installment1;
    List<Long> totalHutangRounding;
    List<Long> premiAmountPCL;
    List<Long> pokokHutangPCL;
    List<Long> bungaAmountPCL;
    List<Long> totalHutangPCL;
    List<Long> installment2;
    List<Long> TDP;
    List<CoverageInsurance> coverageInsurancesTLO = new ArrayList<>();
    List<CoverageInsurance> coverageInsurancesCOMPRE = new ArrayList<>();
    List<Long> TDPNonPcl = new ArrayList<>();

    List<Long> pokokHutangPrepaid = new ArrayList<>();
    List<Long> bungaAmountPrepaid = new ArrayList<>();
    List<Long> totalHutangPrepaid = new ArrayList<>();
    List<Long> installment1Prepaid = new ArrayList<>();
    List<Long> totalHutangRoundingPrepaid = new ArrayList<>();
    List<Long> premiAmountPCLPrepaid = new ArrayList<>();
    List<Long> pokokHutangPCLPrepaid = new ArrayList<>();
    List<Long> bungaAmountPCLPrepaid = new ArrayList<>();
    List<Long> totalHutangPCLPrepaid = new ArrayList<>();
    List<Long> installment2Prepaid = new ArrayList<>();
    List<Long> TDPPrepaid;
    List<Long> TDPPrepaidNonPcl = new ArrayList<>();

    private boolean percentageChange = false;
    private boolean amountChange = false;
    private long inputOtr = 0;
    private int tjh3 = 100000;
    private long premiAmountSumUpCurrentValue = 0;
    private long DP = 0;
    private long PHAwal = 0;
    private String selectedCar = "";
    private int selectedCarIndex = -1;
    private boolean inputDpSelected = false;
    private boolean inputPercentageSelected = false;
    private double DPPercentage = 0.0;

    public List<Double> getBunga() {
        return bungaADDB;
    }

    public void setBunga(List<Double> bunga) {
        this.bungaADDB = bunga;
    }

    int REQUEST_CODE_SIMULASI = 1;

    double dpMin = 0.0;
    double dpMax = 0.0;
    double dbBase = 0.0;
    double dpMinValue = 0.0;
    double dpMaxValue = 0.0;

    private String selectedPackage = "RCFCVNONPAKET";
    private String nonPackageToyota = "RCFCVNONPAKET";
    private String nonPackageDaihatsu = "RCFCVDNONPAKET";
    private String brandCodeToyota = "001";
    private String brandCodeDaihatsu = "004";
    private String current = "";
    private long minDp = 0, maxDp = 0;
    private String addb = "ADDB";
    private String addm = "ADDM";
    private String allrisk = "ALL RISK";
    private String comprehensive = "COMPREHENSIVE";
    private String tlo = "TLO";
    private String combine = "COMBINATION";

    private List<PackageRule> nonPackageGrouping = new ArrayList<>();
    private List<PackageRule> packageRules = new ArrayList<>();
    
    private UserSession userSession;
    private Dealer dealer;
    private com.drife.digitaf.ORM.Database.CarModel selectedCarModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_non_kredit);
        ButterKnife.bind(this);
        SugarContext.init(NonPaketActivity.this);
        initVariables();
        initListeners();
        callfunctions();
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

        btSimulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {
                    double dpPercentage = Double.parseDouble(etPercentage.getText().toString());
                    reset();
                    getBungaMobil(dpPercentage);
                    if(validasi()){
                        LogUtility.logging(TAG,LogUtility.infoLog,"bunga addb", JSONProcessor.toJSON(bungaADDB));
                        LogUtility.logging(TAG,LogUtility.infoLog,"bunga addm", JSONProcessor.toJSON(bungaADDM));

                        String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                        String strdp = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");

                        if(!strotr.equals("") && !strdp.equals("")){
                            inputOtr = Integer.parseInt(strotr);
                            DP = Integer.parseInt(strdp);

                            PHAwal = countPHAwal(inputOtr,DP);
                            countOtrDepresiasi(inputOtr);

                            setSelectedData();
                            SelectedData.SelectedPackageCode = selectedPackage;

                            Intent intent = new Intent(NonPaketActivity.this, ResultSimulasiActivity.class);
                            TableData tableData = new TableData(TDP,installment2,TDPPrepaid,installment2Prepaid,tenor);
                            tableData.setBunga(bungaADDB);
                            tableData.setBungaADDM(bungaADDM);
                            tableData.setSelectedPackage("NONPAKET");
                            tableData.setSelectedPackageCode(selectedPackage);
                            intent.putExtra("data", tableData);
                            startActivityForResult(intent,REQUEST_CODE_SIMULASI);

                        } else{
                            Toast.makeText(getApplicationContext(),"Data anda belum lengkap", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"btSimulasi.setOnClickListener",e.getMessage());
                }*/

                KalkulatorUtility.resetGlobalData();
                String strDpPercentage = etPercentage.getText().toString().equals("") ? "0" : etPercentage.getText().toString();
                double dpPercentage = Double.parseDouble(strDpPercentage);
                reset();
                if(selectedCarModel != null){
                    getBungaMobil(dpPercentage);
                    if(validasi()){
                        LogUtility.logging(TAG,LogUtility.infoLog,"bunga addb", JSONProcessor.toJSON(bungaADDB));
                        LogUtility.logging(TAG,LogUtility.infoLog,"bunga addm", JSONProcessor.toJSON(bungaADDM));

                        String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                        String strdp = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");

                        if(!strotr.equals("") && !strdp.equals("")){
                            inputOtr = Long.parseLong(strotr);
                            DP = Long.parseLong(strdp);

                            PHAwal = countPHAwal(inputOtr,DP);
                            countOtrDepresiasi(inputOtr);

                            setSelectedData();
                            String brand = "";
                            if(userSession.getUser().getType().equalsIgnoreCase("so")){
                                brand = dealer.getBrandCode();

                            }else{
                                brand = userSession.getUser().getDealer().getBrand().getCode();
                            }

                            if(brand != null && !brand.equals("")){
                                if(brand.equalsIgnoreCase(brandCodeToyota)){
                                    selectedPackage = nonPackageToyota;
                                }else{
                                    selectedPackage = nonPackageDaihatsu;
                                }
                            }
                            SelectedData.SelectedPackageCode = selectedPackage;

                            Intent intent = new Intent(NonPaketActivity.this, ResultSimulasiActivity.class);
                            TableData tableData = new TableData(TDP,installment2,TDPPrepaid,installment2Prepaid,tenor);
                            tableData.setBunga(bungaADDB);
                            tableData.setBungaADDM(bungaADDM);
                            tableData.setSelectedPackage("NON PAKET");
                            tableData.setSelectedPackageCode(selectedPackage);
                            intent.putExtra("data", tableData);
                            startActivityForResult(intent,REQUEST_CODE_SIMULASI);

                        } else{
                            Toast.makeText(getApplicationContext(),"Data Anda belum lengkap", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NonPaketActivity.this)
                            .setTitle("Field required")
                            .setMessage("Pilih model mobil terlebih dahulu!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.show();
                }

            }
        });

        etDp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String stramount = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(stramount.equals("")){
                                stramount = "0";
                            }

                            long otr = Long.parseLong(strotr);
                            long amount = Long.parseLong(stramount);
                            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            etPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");

                            DPPercentage = percentage;
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etDp.setOnKeyListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }
                }

                return false;
            }
        });

        etDp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String stramount = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(stramount.equals("")){
                                stramount = "0";
                            }

                            long otr = Long.parseLong(strotr);
                            long amount = Long.parseLong(stramount);
                            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            etPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");

                            DPPercentage = percentage;
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etDp.setOnFocusChangeListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }

                }
            }
        });

        etPercentage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String strpercentage = etPercentage.getText().toString();
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(strpercentage.equals("")){
                                strpercentage = "0";
                            }

                            //float otr = Float.parseFloat(strotr);
                            long otr = Long.parseLong(strotr);
                            double percentage = Double.parseDouble(strpercentage);
                            long amount = 0;

                            if (percentage != Double.parseDouble(KalkulatorUtility.setRoundDecimalPlace(DPPercentage))) {
                                DPPercentage = percentage;

                                if (percentage <= 100) {
                                    //amount = KalkulatorUtility.countAmountFloat(otr,percentage);
                                    amount = KalkulatorUtility.countAmountLong(otr, percentage);
                                }

                                //etDp.setText(KalkulatorUtility.floadToInt(amount)+"");
                                long amt = amount;
                                etDp.setText(amt+"");
                                if (strpercentage.equals("0")) {
                                    etDp.setText("");
                                }
                            }

//                            if(percentage<=100){
//                                //amount = KalkulatorUtility.countAmountFloat(otr,percentage);
//                                amount = KalkulatorUtility.countAmountLong(otr,percentage);
//                            }
//
//                            //etDp.setText(KalkulatorUtility.floadToInt(amount)+"");
//                            long amt = amount;
//                            etDp.setText(amt+"");
//                            if (strpercentage.equals("0")) {
//                                etDp.setText("");
//                            }
                            return false;
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etPercentage.setOnKeyListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }
                }
                return false;
            }
        });

        etPercentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!etOtr.getText().toString().equals("")){
                    try {
                        String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                        String strpercentage = etPercentage.getText().toString();
                        if(strotr.equals("")){
                            strotr = "0";
                        }

                        if(strpercentage.equals("")){
                            strpercentage = "0";
                        }

                        //float otr = Float.parseFloat(strotr);
                        long otr = Long.parseLong(strotr);
                        double percentage = Double.parseDouble(strpercentage);
                        long amount = 0;

                        if (percentage != Double.parseDouble(KalkulatorUtility.setRoundDecimalPlace(DPPercentage))) {
                            DPPercentage = percentage;

                            if (percentage <= 100) {
                                //amount = KalkulatorUtility.countAmountFloat(otr,percentage);
                                amount = KalkulatorUtility.countAmountLong(otr, percentage);
                            }

                            //etDp.setText(KalkulatorUtility.floadToInt(amount)+"");
                            long amt = amount;
                            etDp.setText(amt+"");
                            if (strpercentage.equals("0")) {
                                etDp.setText("");
                            }
                        }

//                        if(percentage<=100){
//                            //amount = KalkulatorUtility.countAmountFloat(otr,percentage);
//                            amount = KalkulatorUtility.countAmountLong(otr,percentage);
//                        }
//
//                        //etDp.setText(KalkulatorUtility.floadToInt(amount)+"");
//                        long amt = amount;
//                        etDp.setText(amt+"");
//                        if (strpercentage.equals("0")) {
//                            etDp.setText("");
//                        }
                    }catch (Exception e){
                        LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etPercentage.setOnFocusChangeListener",e.getMessage());
                    }
                }else {
                    etOtr.setError("OTR harus diisi");
                }

            }
        });

        spinCovInsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coverageAsuransi = spinCovInsurance.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*spinModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCar = spinModel.getSelectedItem().toString();
                selectedCarIndex = position;
                //getBungaMobil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        etOtr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CurrencyFormat.changeFormat(this, s, etOtr);

                if(!etPercentage.getText().toString().equals("")){
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String strpercentage = etPercentage.getText().toString();
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(strpercentage.equals("")){
                                strpercentage = "0";
                            }

                            //float otr = Float.parseFloat(strotr);
                            long otr = Long.parseLong(strotr);
                            double percentage = Double.parseDouble(strpercentage);
                            long amount = 0;
                            if(percentage<=100){
                                //amount = KalkulatorUtility.countAmountFloat(otr,percentage);
                                amount = KalkulatorUtility.countAmountLong(otr,percentage);
                            }

                            //etDp.setText(KalkulatorUtility.floadToInt(amount)+"");
                            long amt = amount;
                            etDp.setText(amt+"");
                            if (strpercentage.equals("0")) {
                                etDp.setText("");
                            }
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etPercentage.setOnKeyListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }

                }else if(!etDp.getText().toString().equals("")){
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String stramount = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(stramount.equals("")){
                                stramount = "0";
                            }

                            long otr = Long.parseLong(strotr);
                            long amount = Long.parseLong(stramount);
                            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            etPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etDp.setOnKeyListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }
                }

                if (s.length() == 2) {
                    etDp.setText("");
                    etPercentage.setText("");
                }
            }
        });

        etDp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String original = s.toString().replaceAll("Rp", "").replaceAll(",", "");
                String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");

                if (s.length() > 2 && strotr.length() > 0) {
                    Long otr = Long.parseLong(strotr.length() > 0 ? strotr : "0");
                    Long dp = Long.parseLong(original);

                    if (dp > otr) {
                        Toast.makeText(NonPaketActivity.this, "Nominal DP tidak boleh lebih besar atau sama dengan OTR.", Toast.LENGTH_LONG).show();
                    }
                }

                CurrencyFormat.changeFormat(this, s, etDp);
                if (s.length() == 2) {
                    etPercentage.setText("");
                }
            }
        });

        etPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CurrencyFormat.formatPercentage(s);
            }
        });

        /*atDealer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    atDealer.showDropDown();
                }
            }
        });*/

        atDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atDealer.showDropDown();
            }
        });

        atDealer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (dealer != null && i1 == 1) {
                    dealer = null;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    atDealer.showDropDown();
                }
            }
        });

        atDealer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                dealer = (Dealer)adapterView.getItemAtPosition(pos);
                SelectedData.SelectedDealer = dealer;

                String brandCode = dealer.getBrandCode();
                setCarList(brandCode);
                atModel.setText("");

                coverageInsurancesTLO = CoverageInsuranceController.getTLO(dealer.getPVInscoCode(), dealer.getInsuranceAreaCode(),dealer.getDealerCode());
                coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre(dealer.getPVInscoCode(),dealer.getInsuranceAreaCode(),dealer.getDealerCode());
            }
        });

        /*atModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    atModel.showDropDown();
                }
            }
        });*/

        atModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atModel.showDropDown();
            }
        });

        atModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //selectedCarIndex = i;
                selectedCarModel = (com.drife.digitaf.ORM.Database.CarModel) adapterView.getItemAtPosition(i);
                selectedCar = selectedCarModel.getCarName();
            }
        });
    }

    private void callfunctions(){
        setDealerList();
        getPaket();

        /*if(userSession.getUser().getType().equals("sales")){
            Log.d("singo","user type : "+JSONProcessor.toJSON(userSession.getUser().getDealer()));
            String brandCode = userSession.getUser().getDealer().getBrand().getCode();
            setCarList(brandCode);
        }else{
            setCarList();
        }*/

        if(userSession.getUser().getType().equals("so")){
            setCarList();
        }else{
            Log.d("singo","user type : "+JSONProcessor.toJSON(userSession.getUser().getDealer()));
            String brandCode = userSession.getUser().getDealer().getBrand().getCode();
            setCarList(brandCode);
        }

        setCoverage();
        setMaster();
    }

    private void setDealerList() {
        if (userSession.getUser().getType().equals("so")) {
            lyDealerName.setVisibility(View.VISIBLE);

            if (DealerController.getAllDealer() != null) {
                if (DealerController.getAllDealer().size() > 0) {
                    atDealer.setAdapter(new ArrayAdapter<Dealer>(this, android.R.layout.simple_list_item_1, DealerController.getAllDealer()));
                }
            }
        }
    }

    private void setCarList(){

        //carMaster = CarModelMaster.getCarModel();
        //cars = CarController.getAllCar();
        List<com.drife.digitaf.ORM.Database.CarModel> models = new ArrayList<>();

        if(nonPackageGrouping != null && nonPackageGrouping.size()>0){
            //cars = CarController.getAllAvailableCarInPackage(nonPackageGrouping.get(0).getCategoryGroupId());
            for (int i=0; i<nonPackageGrouping.size(); i++){
                List<com.drife.digitaf.ORM.Database.CarModel> carModels = CarController.getAllAvailableCarInPackage(nonPackageGrouping.get(i).getCategoryGroupId());
                for (int j=0; j<carModels.size(); j++){
                    com.drife.digitaf.ORM.Database.CarModel model = carModels.get(j);
                    models.add(model);
                }
            }
        }

        TextUtility.sortCar(models);

        for (int i=0; i<models.size(); i++){
            com.drife.digitaf.ORM.Database.CarModel model = models.get(i);
            String car = model.getCarName();
            if(!carModel.contains(car)){
                carModel.add(car);
                cars.add(model);
            }
        }

        /*for (int i=0; i<cars.size(); i++){
            com.drife.digitaf.ORM.Database.CarModel carModel = cars.get(i);
            String model = carModel.getCarName();
            this.carModel.add(model);
        }*/

        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));

        //SpinnerUtility.setSpinnerItem(getApplicationContext(), spinModel, this.carModel);
    }

    private void setCarList(String brandCode){
        Log.d("singo", "brandCode : "+brandCode);

        carModel.clear();
        cars.clear();
        List<com.drife.digitaf.ORM.Database.CarModel> models = new ArrayList<>();

        if(nonPackageGrouping != null && nonPackageGrouping.size()>0){
            for (int i=0; i<nonPackageGrouping.size(); i++){
                List<com.drife.digitaf.ORM.Database.CarModel> carModels = CarController.getAllAvailableCarInPackage(nonPackageGrouping.get(i).getCategoryGroupId());
                for (int j=0; j<carModels.size(); j++){
                    com.drife.digitaf.ORM.Database.CarModel model = carModels.get(j);
                    models.add(model);
                }
            }
        }

        TextUtility.sortCar(models);

        for (int i=0; i<models.size(); i++){
            com.drife.digitaf.ORM.Database.CarModel model = models.get(i);
            String car = model.getCarName();
            String brand = model.getBrandCode();
            if(brand.equals(brandCode)){
                if(!carModel.contains(car)){
                    carModel.add(car);
                    cars.add(model);
                }
            }
        }

        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
    }

    private void setCoverage(){
        coverage.add(comprehensive);
        //coverage.add(tlo);
        coverage.add(combine);
        SpinnerUtility.setSpinnerItem(getApplicationContext(),spinCovInsurance,coverage);
    }

    private void setMaster(){
        setTenor();
        depresiasis = DepresiasiMaster.getDepresiasi(tenor);
        //depresiasis = DepresiasiMaster.getDepresiasi();
        //getDepresiasi();
        //asuransiPremis = AsuransiPremiMaster.getAsuransiPremis();
        coverageInsurancesTLO = CoverageInsuranceController.getTLO();
        coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre();
        LogUtility.logging(TAG,LogUtility.infoLog,"setMaster","coverageInsurancesTLO",JSONProcessor.toJSON(coverageInsurancesTLO));
        LogUtility.logging(TAG,LogUtility.infoLog,"setMaster","coverageInsurancesCOMPRE",JSONProcessor.toJSON(coverageInsurancesCOMPRE));
        //coverageInsurances = CoverageInsuranceController.getAllCoverageInsurance();
        //tenor = TenorMaster.getTenor();
        //PCL = PCLMaster.getPCL();
        String pclStr = SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
        //LogUtility.logging(TAG,LogUtility.infoLog,"PCL","",SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getName());
        if(!pclStr.equals("")){
            PCL = Double.parseDouble(pclStr);
            LogUtility.logging(TAG,LogUtility.infoLog,"PCL","PCL Rate",PCL+"");
            LogUtility.logging(TAG,LogUtility.infoLog,"PCL","PCL Name",SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getName());
        }

        //biayaAdmin = BiayaTambahanMaster.getBiayaAdmin();
        polis = BiayaTambahanMaster.getPolis();
    }

    private long countPHAwal(long otrAwal, long dp){
        long ph = otrAwal - dp;
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal","otrAwal",otrAwal+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal","dp",dp+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal","ph",ph+"");
        return ph;
    }

    private void countOtrDepresiasi(long otr){
        otrDepresiasis = KalkulatorUtility.getOTRAfterDepresiasi(otr,depresiasis);
        //LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi",JSONProcessor.toJSON(otrDepresiasis));
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            long otrdep = otrDepresiasi.getOTR();
            //LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi",otrdep+"");
        }
        if(otrDepresiasis != null){
            getAsuransiPremiPercentage(otrDepresiasis);
            LogUtility.logging(TAG,LogUtility.debugLog,"premiAmountPercentage",new Gson().toJson(premiAmountPercentage));
            getPremiAmount(otrDepresiasis,premiAmountPercentage,tjh3);
            LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi","pokokHutang",JSONProcessor.toJSON(pokokHutang));
            getBungaAmount(pokokHutang,bungaADDB,tenor);
            getTotalHutangRounding(installment1,tenor,pokokHutang,bungaADDB);
            getTDP();

            getBungaAmountPrepaid(pokokHutangPrepaid,bungaADDM,tenor);
            getTotalHutangRoundingPrepaid(installment1Prepaid,tenor,pokokHutangPrepaid,bungaADDM);
            getTDPPrepaid();
        }
    }

    private void getAsuransiPremiPercentage(List<OTRDepresiasi> otrDepresiasis){
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","coverageAsuransi",coverageAsuransi);
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","otrDepresiasis",JSONProcessor.toJSON(otrDepresiasis));
        premiAmountPercentage = new ArrayList<>();

        List<CoverageInsurance> selectedCoverage = new ArrayList<>();
        List<CoverageInsurance> firstYearCoverage = new ArrayList<>();
        if(coverageAsuransi.equals(comprehensive)){
            selectedCoverage = coverageInsurancesCOMPRE;
        }else if(coverageAsuransi.equals(combine)){
            firstYearCoverage = coverageInsurancesCOMPRE;
            selectedCoverage = coverageInsurancesTLO;
        }else{
            selectedCoverage = coverageInsurancesTLO;
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","selectedCoverage",JSONProcessor.toJSON(selectedCoverage));
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","selectedCoverage Name",selectedCoverage.get(0).getPVInsco());

        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            long otr = otrDepresiasi.getOTR();

            if(coverageAsuransi.equals(combine) && i==0){
                for(int j=0; j<firstYearCoverage.size(); j++){
                    CoverageInsurance coverageInsurance = firstYearCoverage.get(j);
                    double min = Double.parseDouble(coverageInsurance.getMinTSI());
                    double max = Double.parseDouble(coverageInsurance.getMaxTSI());
                    double rate = Double.parseDouble(coverageInsurance.getRate());
                    if((double)otr>min && (double)otr<=max){
                        premiAmountPercentage.add(rate);
                    }
                }
            }else{
                for(int j=0; j<selectedCoverage.size(); j++){
                    CoverageInsurance coverageInsurance = selectedCoverage.get(j);
                    double min = Double.parseDouble(coverageInsurance.getMinTSI());
                    double max = Double.parseDouble(coverageInsurance.getMaxTSI());
                    double rate = Double.parseDouble(coverageInsurance.getRate());
                    if((double)otr>min && (double)otr<=max){
                        premiAmountPercentage.add(rate);
                    }
                }
            }
        }

        //LogUtility.logging(TAG,LogUtility.infoLog,"PV","premiAmountPercentage",JSONProcessor.toJSON(premiAmountPercentage));
    }

    private void getPremiAmount(List<OTRDepresiasi> otrDepresiasis, List<Double> premiAmountPercentage, int tjh3){
        LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","otrDepresiasis",JSONProcessor.toJSON(otrDepresiasis));
        LogUtility.logging(TAG,LogUtility.infoLog,"PV","premiAmountPercentage",JSONProcessor.toJSON(premiAmountPercentage));
        premiAmount = new ArrayList<>();
        premiAmountSumUp = new ArrayList<>();
        pokokHutang = new ArrayList<>();
        pokokHutangPrepaid = new ArrayList<>();
        try {
            for (int i=0; i<otrDepresiasis.size(); i++){
                OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
                Double percentage = premiAmountPercentage.get(i);
                long otr = otrDepresiasi.getOTR();
                long amount = Math.round((float) otr*percentage/100);
                //long amount = new Double((float) otr*percentage/100).longValue();
                //Log.d("singo", "coverage : "+coverageAsuransi);
                long amt = 0;

                if(coverageAsuransi.equals(combine)){
                    if(i==0){
                        amt = amount+tjh3;
                    }else{
                        amt = amount;
                    }
                }else{
                    if(coverageAsuransi.equals(comprehensive)){
                        //Log.d("coverageAsuransi","all risk");
                        //Log.d("coverageAsuransi","amount = "+amount+" , tjh3 = "+tjh3);
                        amt = amount+tjh3;
                        //Log.d("coverageAsuransi","amt = "+amt);
                    }else{
                        //Log.d("coverageAsuransi","tlo");
                        amt = amount;
                        //Log.d("coverageAsuransi","amt = "+amt);
                    }
                }
                premiAmount.add(amt);

                /*insert data to premiAmountSum to handle asuransi cicil or TDP*/
                premiAmountSumUpCurrentValue = premiAmountSumUpCurrentValue+amt;
                premiAmountSumUp.add(premiAmountSumUpCurrentValue);
                /*set ph */
                processOnLoanData(PHAwal,premiAmountSumUpCurrentValue);
                processPrepaidData(PHAwal,premiAmountSumUpCurrentValue);
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"PV premiAmount","premiAmount",JSONProcessor.toJSON(premiAmount));
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getPremiAmount","Exception",e.getMessage());
        }
    }

    private void processOnLoanData(long phAwal, long premiAmountSumUpCurrentValue){
        //pokokHutang = new ArrayList<>();
        long count = phAwal+premiAmountSumUpCurrentValue;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processOnLoanData","count",count+"");
        pokokHutang.add(count);
    }

    /*private void processPrepaidData(int phAwal){
        //pokokHutangPrepaid = new ArrayList<>();
        int count = phAwal;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processPrepaidData","count",count+"");
        pokokHutangPrepaid.add(count);
    }*/

    private void processPrepaidData(long phAwal, long premiAmountSumUpCurrentValue){
        //pokokHutangPrepaid = new ArrayList<>();
        long count = phAwal+premiAmountSumUpCurrentValue;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processPrepaidData","count",count+"");
        pokokHutangPrepaid.add(count);
    }

    private void getBungaMobil(final double dpPercentage){
        bungaADDB.clear();
        bungaADDM.clear();
        biayaAdmin.clear();
        biayaAdminADDM.clear();
        packageRules.clear();
        //String carCode = carMaster.get(selectedCarIndex).getCode();
        String carCode = selectedCarModel.getCarCode();
        String carModel = carCode;
        String categoryGroupId = selectedCarModel.getCategoryGroupId();

        final String code_nonpaket = "RCFCVNONPAKET";
        final int isNonPackage = 1;
        packageRules = new ArrayList<>();
        packageRules = PackageRuleController.getPackageRules(isNonPackage,
                carModel,null,
                SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(isNonPackage,
                    null,categoryGroupId,
                    SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }

        if(packageRules.size() != 0){
            /*String min = packageRules.get(0).getMinDp();
            String max = packageRules.get(0).getMaxDp();
            if(!min.equals("")){
                dpMin = Double.parseDouble(min);
            }

            if(!max.equals("")){
                dpMax = Double.parseDouble(max);
            }*/

            for(int i = 0; i< packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);
                double minDp = Double.parseDouble(packageRule.getMinDp());
                double maxDp = Double.parseDouble(packageRule.getMaxDp());
                double adminFee = Double.parseDouble(packageRule.getAdminFee());
                if(packageRule.getInstallmentType().equals(addm)){
                    if(dpPercentage>=minDp && dpPercentage<=maxDp){
                        bungaADDM.add(Double.parseDouble(packageRule.getBaseRate()));
                        biayaAdminADDM.add((int) adminFee);
                    }
                }else if(packageRule.getInstallmentType().equals(addb)){
                    if(dpPercentage>=minDp && dpPercentage<=maxDp){
                        bungaADDB.add(Double.parseDouble(packageRule.getBaseRate()));
                        biayaAdmin.add((int) adminFee);
                    }
                }
            }
        }else{
            Toast.makeText(NonPaketActivity.this,"This model is not available for NON PAKET",Toast.LENGTH_SHORT).show();
        }
    }

    private void getBungaAmount(List<Long> pokokHutang,List<Double> bungas,List<Tenor> tenors){
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","pokokHutang onloan",JSONProcessor.toJSON(pokokHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungas singo",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","tenors",JSONProcessor.toJSON(tenors));
        bungaAmount = new ArrayList<>();
        totalHutang = new ArrayList<>();
        installment1 = new ArrayList<>();
        for (int i=0; i<bungas.size(); i++){
            long ph = pokokHutang.get(i);
            double bunga = bungas.get(i);
            Tenor tenor = tenors.get(i);
            int ten = Integer.parseInt(tenor.getName());
            int t = ten/12;
            long a = new Double((float)ph*bunga/100).longValue();
            long value = a*t;

            Log.d("bungaHutang","bunga onloan: "+bunga);
            Log.d("bungaHutang","pokokHutang onloan: "+ph);
            Log.d("bungaHutang","tenor onloan: "+t);
            Log.d("bungaHutang","pokok hutang x bunga onloan: "+a);

            /*total hutang*/
            long total = ph+value;
            /*installment1*/
            long ori = total/ten;
            //int ins1 = (int) Precision.round((total/ten),-3);
            long round = KalkulatorUtility.roundUp(ori);

            //LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmount","value","ph : "+ph+" : bunga : "+bunga+" : "+t+" : ori : "+ori+" : ins : "+round);
            bungaAmount.add(value);
            totalHutang.add(total);
            installment1.add(round);

            long tdp1 = DP+biayaAdmin.get(i);
            TDPNonPcl.add(tdp1);
        }
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungas onloan",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungaAmount onloan",JSONProcessor.toJSON(bungaAmount));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","AR non PCL onloan",JSONProcessor.toJSON(totalHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","Installment non PCL onloan",JSONProcessor.toJSON(installment1));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","TDPNonPcl non PCL onloan",JSONProcessor.toJSON(TDPNonPcl));
    }

    private void getBungaAmountPrepaid(List<Long> pokokHutang,List<Double> bungas,List<Tenor> tenors){
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","pokokHutang Prepaid",JSONProcessor.toJSON(pokokHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","bungas Prepaid",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","tenors Prepaid",JSONProcessor.toJSON(tenors));
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","DP",DP+"");
        bungaAmountPrepaid = new ArrayList<>();
        totalHutangPrepaid = new ArrayList<>();
        installment1Prepaid = new ArrayList<>();
        for (int i=0; i<bungas.size(); i++){
            long ph = pokokHutang.get(i);
            double bunga = bungas.get(i);
            Tenor tenor = tenors.get(i);
            int ten = Integer.parseInt(tenor.getName());
            int t = ten/12;
            long a = new Double((float)ph*bunga/100).longValue();
            long value = a*t;
            Log.d("bungaHutang","bunga prepaid: "+bunga);
            Log.d("bungaHutang","pokokHutang prepaid: "+ph);
            Log.d("bungaHutang","tenor prepaid: "+t);
            Log.d("bungaHutang","pokok hutang x bunga prepaid: "+a);
            Log.d("bungaHutang","value: "+value);

            /*total hutang*/
            long total = ph+value;
            /*installment1*/
            long ori = total/ten;
            //int ins1 = (int) Precision.round((total/ten),-3);
            long round = KalkulatorUtility.roundUp(ori);

            //LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","value","ph : "+ph+" : bunga : "+bunga+" : "+t+" : ori : "+ori+" : ins : "+round);
            bungaAmountPrepaid.add(value);
            totalHutangPrepaid.add(total);
            installment1Prepaid.add(round);

            long tdp1 = DP+biayaAdminADDM.get(i)+round;
            TDPPrepaidNonPcl.add(tdp1);
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungas prepaid",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungaAmountPrepaid prepaid",JSONProcessor.toJSON(bungaAmountPrepaid));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","AR non PCL Prepaid",JSONProcessor.toJSON(totalHutangPrepaid));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","Installment non PCL Prepaid",JSONProcessor.toJSON(installment1Prepaid));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","TDPPrepaidNonPcl non PCL Prepaid",JSONProcessor.toJSON(TDPPrepaidNonPcl));
    }

    private void getTotalHutangRounding(List<Long> installment1,List<Tenor> tenor,
                                        List<Long> pokokHutang, List<Double> bungas){
        //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","size",installment1.size()+"");
        totalHutangRounding = new ArrayList<>();
        premiAmountPCL = new ArrayList<>();
        pokokHutangPCL = new ArrayList<>();
        bungaAmountPCL = new ArrayList<>();
        totalHutangPCL = new ArrayList<>();
        installment2 = new ArrayList<>();

        List<Long> tRound = new ArrayList<>();
        List<Long> pAmount = new ArrayList<>();
        List<Long> pHutang = new ArrayList<>();
        List<Long> bAmount = new ArrayList<>();
        List<Long> listpokHutang = new ArrayList<>();

        for (int i=0; i<installment1.size(); i++){
            long ins = installment1.get(i);
            Tenor tenor1 = tenor.get(i);
            //double pcl = pcls.get(i);
            //String pclStr = SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
            //double pcl = Double.parseDouble(pclStr);
            double pcl = 0.0;

            if(userSession.getUser().getType().equals("so")){
                pcl = Double.parseDouble(dealer.getPCLPremi());
            }else if(userSession.getUser().getType().equals("sales")){
                String pclStr = SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
                pcl = Double.parseDouble(pclStr);
            }else{
                String pclStr = SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
                pcl = Double.parseDouble(pclStr);
            }
            long pokHutang = pokokHutang.get(i);
            double bunga = bungas.get(i);

            int ten = Integer.parseInt(tenor1.getName());
            long totHRounding = ins*ten;
            long premiAmount = new Double((float) totHRounding*pcl/100).longValue();
            //long pkkHtng = Math.round(pokHutang+premiAmount);
            long pkkHtng = pokHutang+premiAmount;

            int t = ten/12;
            //long a = Math.round((pkkHtng*bunga)/100);
            long a = new Double((float)pkkHtng*bunga/100).longValue();
            long bungaAmount = a*t;

            tRound.add(totHRounding);
            pAmount.add(premiAmount);
            pHutang.add(pkkHtng);
            bAmount.add(bungaAmount);
            listpokHutang.add(pokHutang);

            long totHutPCL = pkkHtng+bungaAmount;

            long round = KalkulatorUtility.roundUp(totHutPCL/ten);

            //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","value",round+"");
            totalHutangRounding.add(totHRounding);
            premiAmountPCL.add(premiAmount);
            pokokHutangPCL.add(pkkHtng);
            bungaAmountPCL.add(bungaAmount);
            totalHutangPCL.add(totHutPCL);
            installment2.add(round);
        }

        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","premiAmountPCL : Onloan", JSONProcessor.toJSON(premiAmountPCL));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","totalHutangPCL (AR) : Onloan", JSONProcessor.toJSON(totalHutangPCL));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","installment2 : Onloan", JSONProcessor.toJSON(installment2));

        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","tRound : Onloan", JSONProcessor.toJSON(tRound));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","pAmount : Onloan", JSONProcessor.toJSON(pAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","pHutang : Onloan", JSONProcessor.toJSON(pHutang));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","bAmount : Onloan", JSONProcessor.toJSON(bAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","pokHutang : Onloan", JSONProcessor.toJSON(listpokHutang));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","bungas : Onloan", JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","tenor : Onloan", JSONProcessor.toJSON(tenor));
    }

    private void getTotalHutangRoundingPrepaid(List<Long> installment1,List<Tenor> tenor,
                                               List<Long> pokokHutang, List<Double> bungas){
        //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","size",installment1.size()+"");
        totalHutangRoundingPrepaid = new ArrayList<>();

        List<Long> tRound = new ArrayList<>();
        List<Long> pAmount = new ArrayList<>();
        List<Long> pHutang = new ArrayList<>();
        List<Long> bAmount = new ArrayList<>();
        List<Integer> listpokHutang = new ArrayList<>();
        List<Long> tHutangPCL = new ArrayList<>();
        List<Long> installment2 = new ArrayList<>();

        /*String pclStr = SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
        if(!pclStr.equals("")){
            PCL = Double.parseDouble(pclStr);
            LogUtility.logging(TAG,LogUtility.infoLog,"ADDM PCL","PCL Rate",PCL+"");
            LogUtility.logging(TAG,LogUtility.infoLog,"ADDM PCL","PCL Name",SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getName());
        }*/

        if(userSession.getUser().getType().equals("so")){
            PCL = Double.parseDouble(dealer.getPCLPremi());
        }else if(userSession.getUser().getType().equals("sales")){
            String pclStr = SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
            if(!pclStr.equals("")){
                PCL = Double.parseDouble(pclStr);
            }
        }

        for (int i=0; i<installment1.size(); i++){
            long ins = installment1.get(i);
            Tenor tenor1 = tenor.get(i);
            double pcl = this.PCL;
            long pokHutang = pokokHutang.get(i);
            double bunga = bungas.get(i);

            int ten = Integer.parseInt(tenor1.getName());
            long totHRounding = ins*ten;
            //long premiAmount = Math.round((totHRounding*pcl)/100);
            long premiAmount = new Double((float)totHRounding*pcl/100).longValue();
            long pkkHtng = pokHutang+premiAmount;

            int t = ten/12;
            long a = new Double((float)pkkHtng*bunga/100).longValue();
            long bungaAmount = a*t;

            //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","value",round+"");
            totalHutangRoundingPrepaid.add(totHRounding);
            tRound.add(totHRounding);
            pAmount.add(premiAmount);
            pHutang.add(pkkHtng);
            bAmount.add(bungaAmount);

            long totHutPCL = pkkHtng+bungaAmount;
            tHutangPCL.add(totHutPCL);

            long round = KalkulatorUtility.roundUp(totHutPCL/ten);
            installment2.add(round);

            premiAmountPCLPrepaid.add(premiAmount);
            pokokHutangPCLPrepaid.add(pkkHtng);
            bungaAmountPCLPrepaid.add(bungaAmount);
            totalHutangPCLPrepaid.add(totHutPCL);
            installment2Prepaid.add(round);
        }
        //premiAmountPCLPrepaid = KalkulatorUtility.getPremiAmountPCLPrepaid(this.totalHutangRoundingPrepaid,this.PCL);
        //pokokHutangPCLPrepaid = KalkulatorUtility.getPokokHutangPCLPrepaid(this.pokokHutangPrepaid,this.premiAmountPCLPrepaid);
        //bungaAmountPCLPrepaid = KalkulatorUtility.getBungaAmountPCLPrepaid(this.pokokHutangPCLPrepaid,this.bungaADDM,this.tenor);
        //totalHutangPCLPrepaid = KalkulatorUtility.getTotalHutangPCLPrepaid(this.pokokHutangPCLPrepaid,this.bungaAmountPCLPrepaid);
        //installment2Prepaid = KalkulatorUtility.getInstallmentFinal(this.totalHutangPCLPrepaid,this.tenor);

        //LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","premiAmountPCL", JSONProcessor.toJSON(premiAmountPCL));
        //LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","totalHutangPCL (AR)", JSONProcessor.toJSON(totalHutangPCL));
        //LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","installment2", JSONProcessor.toJSON(installment2Prepaid));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","totalHutangRoundingPrepaid", JSONProcessor.toJSON(totalHutangRoundingPrepaid));
        LogUtility.logging(TAG,LogUtility.infoLog,"ADDM PCL","PCL Rate",PCL+"");
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","pclAmount", JSONProcessor.toJSON(pAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","pkkHtng", JSONProcessor.toJSON(pHutang));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","bAmount", JSONProcessor.toJSON(bAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","tHutangPCL", JSONProcessor.toJSON(tHutangPCL));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","installment2", JSONProcessor.toJSON(installment2));
    }

    /*final result for TDP Onloan*/
    private void getTDP(){
        TDP = KalkulatorUtility.getTDP_ADDB(DP,biayaAdmin,polis,tenor, bungaADDB.size());
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP","TDP",JSONProcessor.toJSON(TDP));
    }

    /*final result for TDP Prepaid*/
    private void getTDPPrepaid(){
        TDPPrepaid = KalkulatorUtility.getTDP_ADDM(DP,biayaAdminADDM,polis,installment2Prepaid,premiAmountSumUp,
                "ONLOAN",tenor,bungaADDM.size());
        LogUtility.logging(TAG,LogUtility.infoLog,"TDPPrepaid","TDPPrepaid",JSONProcessor.toJSON(TDPPrepaid));
    }

    /*reset value*/
    private void reset(){
        try {
            otrDepresiasis.clear();
            premiAmountPercentage.clear();
            premiAmount.clear();
            premiAmountSumUp.clear();
            pokokHutang.clear();
            //bunga.clear();
            bungaAmount.clear();
            totalHutang.clear();
            installment1.clear();
            totalHutangRounding.clear();
            premiAmountPCL.clear();
            pokokHutangPCL.clear();
            bungaAmountPCL.clear();
            totalHutangPCL.clear();
            installment2.clear();
            TDP.clear();

            pokokHutangPrepaid.clear();
            //bungaAmountPrepaid.clear();
            totalHutangPrepaid.clear();
            installment1Prepaid.clear();
            totalHutangRoundingPrepaid.clear();
            premiAmountPCLPrepaid.clear();
            pokokHutangPCLPrepaid.clear();
            bungaAmountPCLPrepaid.clear();
            totalHutangPCLPrepaid.clear();
            installment2Prepaid.clear();
            TDPPrepaid.clear();

            PHAwal = 0;
            premiAmountSumUpCurrentValue = 0;

            bungaADDB.clear();
            bungaADDM.clear();
            biayaAdmin.clear();
            biayaAdminADDM.clear();
            packageRules.clear();

            TDPNonPcl.clear();
            TDPPrepaidNonPcl.clear();
        }catch (Exception e){

        }
    }

    private void setSelectedData(){
        //SelectedData.Car = selectedCarIndex;
        SelectedData.selectedCarModel = selectedCarModel;
        SelectedData.Otr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
        SelectedData.Dp = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
        SelectedData.DpPercentage = etPercentage.getText().toString();
        SelectedData.CoverageType = spinCovInsurance.getSelectedItemPosition();
        SelectedData.CategoryGroupId = selectedCarModel.getCategoryGroupId();

        /*SelectedData.Tenor = "";
        SelectedData.Rate = "";
        SelectedData.JenisAngsuran = "";
        SelectedData.NamaPaket = "";
        SelectedData.InsuranceName = "";
        SelectedData.CarType = 0;
        SelectedData.PaymentType = 0;*/
    }

    private void setTenor(){
        List<com.drife.digitaf.ORM.Database.Tenor> tenors = TenorController.getAllTenor();
        if(tenors != null){
            for (int i=0; i<tenors.size(); i++){
                com.drife.digitaf.ORM.Database.Tenor tenor = tenors.get(i);
                Tenor ten = new Tenor(tenor.getIdTenor(),tenor.getCode());
                this.tenor.add(ten);
            }
        }
    }

    /*private boolean validasiDp(int otr, int dp){
        double minValue = (dpMin*(double) otr)/100;
        double maxValue = (dpMax*(double) otr)/100;

        if(dp<=maxValue && dp>=minValue){
            return true;
        }else{
            return false;
        }
    }*/

    /*private void checkAvailabilityNonPacket(int index, String carName){
        String carCode = carMaster.get(index).getCode();
        String carModel = carCode;

        String code_nonpaket = "RCFCVNONPAKET";
        List<PackageRule> packageRules;
        packageRules = PackageRuleController.getPackageRules(code_nonpaket,
                carModel,null,
                SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(code_nonpaket,
                    null,cars.get(index).getCategoryGroupId(),
                    SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }

        if(packageRules.size() != 0){
            this.carModel.add(carName);
        }
    }*/

    /*public class sortCarListModel extends AsyncTask<Void, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NonPaketActivity.this);
            progressDialog.setMessage("Get car list model..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            carMaster = CarModelMaster.getCarModel();
            cars = CarController.getAllCar();

            for (int i=0; i<cars.size(); i++){
                com.drife.digitaf.ORM.Database.CarModel carModel = cars.get(i);
                String model = carModel.getCarName();
                checkAvailabilityNonPacket(i, model);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            SpinnerUtility.setSpinnerItem(getApplicationContext(), spinModel, NonPaketActivity.this.carModel);
        }
    }*/

    private void getPaket(){
        try {
            nonPackageGrouping.clear();
            nonPackageGrouping = PackageRuleController.getNonPackage();
            //List<PackageRule> test = PackageRuleController.getNonPackage("RUSH");
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getPaket","Exception",e.getMessage());
        }
    }

   private boolean validasi(){
       boolean status = true;

       if (dealer == null && userSession.getUser().getType().equals("so")){
           status = false;
           AlertDialog.Builder builder = new AlertDialog.Builder(NonPaketActivity.this)
                   .setTitle("Field required")
                   .setMessage("Pilih dealer terlebih dahulu.")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
           builder.show();
       } else if(etOtr.getText().toString().equals("")){
           status = false;
           AlertDialog.Builder builder = new AlertDialog.Builder(NonPaketActivity.this)
                   .setTitle("Field required")
                   .setMessage("Harga OTR belum diisi")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
           builder.show();
       }else if(etDp.getText().toString().equals("")){
           status = false;
           AlertDialog.Builder builder = new AlertDialog.Builder(NonPaketActivity.this)
                   .setTitle("Field required")
                   .setMessage("DP belum diisi")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
           builder.show();
       }else if(etPercentage.getText().toString().equals("")){
           status = false;
           AlertDialog.Builder builder = new AlertDialog.Builder(NonPaketActivity.this)
                   .setTitle("DP out of range")
                   .setMessage("Hasil tidak tersedia untuk besar DP yang Anda inputkan")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
           builder.show();
       }else if(bungaADDM.size()==0){
           status = false;
           AlertDialog.Builder builder = new AlertDialog.Builder(NonPaketActivity.this)
                   .setTitle("DP out of range")
                   .setMessage("Hasil tidak tersedia untuk besar DP yang Anda inputkan")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
           builder.show();
       }else if(bungaADDB.size()==0){
           status = false;
       }else if(atModel.getText().toString().equals("")){
           status = false;
       }
       return status;
   }

   private void countBunga(double dpPercentage){
       String min = packageRules.get(0).getMinDp();
       String max = packageRules.get(0).getMaxDp();
       if(!min.equals("")){
           dpMin = Double.parseDouble(min);
       }

       if(!max.equals("")){
           dpMax = Double.parseDouble(max);
       }

       for(int i = 0; i< packageRules.size(); i++){
           PackageRule packageRule = packageRules.get(i);
           double minDp = Double.parseDouble(packageRule.getMinDp());
           double maxDp = Double.parseDouble(packageRule.getMaxDp());
           double adminFee = Double.parseDouble(packageRule.getAdminFee());
           if(packageRule.getInstallmentType().equals(addm)){
               if(dpPercentage<minDp && dpPercentage<=maxDp){
                   bungaADDM.add(Double.parseDouble(packageRule.getBaseRate()));
                   biayaAdmin.add((int) adminFee);
               }
           }else if(packageRule.getInstallmentType().equals(addb)){
               if(dpPercentage<minDp && dpPercentage<=maxDp){
                   bungaADDB.add(Double.parseDouble(packageRule.getBaseRate()));
                   biayaAdmin.add((int) adminFee);
               }
           }
       }
   }

   private void countDpAmount(){
       if(!etOtr.getText().toString().equals("")){
           try {
               String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
               String strpercentage = etPercentage.getText().toString();
               if(strotr.equals("")){
                   strotr = "0";
               }

               if(strpercentage.equals("")){
                   strpercentage = "0";
               }

               //float otr = Float.parseFloat(strotr);
               long otr = Long.parseLong(strotr);
               double percentage = Double.parseDouble(strpercentage);
               long amount = 0;

               if (percentage != Double.parseDouble(KalkulatorUtility.setRoundDecimalPlace(DPPercentage))) {
                   DPPercentage = percentage;

                   if (percentage <= 100) {
                       //amount = KalkulatorUtility.countAmountFloat(otr,percentage);
                       amount = KalkulatorUtility.countAmountLong(otr, percentage);
                   }

                   //etDp.setText(KalkulatorUtility.floadToInt(amount)+"");
                   long amt = amount;
                   etDp.setText(amt+"");
                   if (strpercentage.equals("0")) {
                       etDp.setText("");
                   }
               }

           }catch (Exception e){
               LogUtility.logging(TAG,LogUtility.errorLog,"countDpAmount","Exception",e.getMessage());
           }
       }else{
           etOtr.setError("OTR harus diisi");
       }
   }
}