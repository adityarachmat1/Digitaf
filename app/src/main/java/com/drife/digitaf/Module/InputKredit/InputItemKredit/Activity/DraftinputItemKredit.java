package com.drife.digitaf.Module.InputKredit.InputItemKredit.Activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.Currency.CurrencyFormat;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.Model.Master.BiayaTambahanMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.DepresiasiMaster;
import com.drife.digitaf.KalkulatorKredit.Model.OTRDepresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.PremiAmount;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity.HasilPerhitungandraft;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Controller.TenorController;
import com.drife.digitaf.ORM.Database.CarType;
import com.drife.digitaf.ORM.Database.CoverageInsurance;
import com.drife.digitaf.ORM.Database.Dealer;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.ORM.Database.PackageRule;
import com.drife.digitaf.ORM.Model.DataKredit;
import com.drife.digitaf.ORM.Temporary.TemporaryValue;
import com.drife.digitaf.UIModel.HasilPerhitungan;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.SubmitParam.AdditionalCoverage;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.Insurance;
import com.drife.digitaf.retrofitmodule.SubmitParam.InsuranceList;
import com.drife.digitaf.retrofitmodule.SubmitParam.LifeInsurance;
import com.drife.digitaf.retrofitmodule.SubmitParam.SubmitParameters;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftinputItemKredit extends AppCompatActivity {
    private String TAG = DraftinputItemKredit.class.getSimpleName();

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btnLanjutkan)
    LinearLayout btnLanjutkan;
    @BindView(R.id.cbProvision)
    CheckBox cbProvision;
    @BindView(R.id.lyProvision)
    LinearLayout lyProvision;
    @BindView(R.id.cbServicePackage)
    CheckBox cbServicePackage;
    @BindView(R.id.lyServicePackage)
    LinearLayout lyServicePackage;
    @BindView(R.id.spinnerModel)
    Spinner spinnerModel;
    @BindView(R.id.spinnerTenor)
    Spinner spinnerTenor;
    @BindView(R.id.spinnerCoverageType)
    Spinner spinnerCoverageType;
    @BindView(R.id.spinnerPaymentType)
    Spinner spinnerPaymentType;
    @BindView(R.id.spinnerProvisionType)
    Spinner spinnerProvisionType;
    @BindView(R.id.spinnerAddOnPaymentType)
    Spinner spinnerAddOnPaymentType;
    @BindView(R.id.rbADDM)
    RadioButton rbADDM;
    @BindView(R.id.rbADDB)
    RadioButton rbADDB;
    @BindView(R.id.etOtr)
    EditText etOtr;
    @BindView(R.id.etDp)
    EditText etDp;
    @BindView(R.id.etDpPercentage)
    EditText etDpPercentage;
    @BindView(R.id.etRate)
    EditText etRate;
    @BindView(R.id.spinnerTACP)
    Spinner spinnerTACP;
    @BindView(R.id.etProvisionAmount)
    EditText etProvisionAmount;
    @BindView(R.id.etPackageAmount)
    EditText etPackageAmount;
    @BindView(R.id.etDiskon)
    EditText etDiskon;
    @BindView(R.id.etNamaPaket)
    EditText etNamaPaket;
    @BindView(R.id.etInsuranceName)
    EditText etInsuranceName;
    @BindView(R.id.spinnerType)
    Spinner spinnerType;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;
    @BindView(R.id.atModel)
    AutoCompleteTextView atModel;
    @BindView(R.id.atType)
    AutoCompleteTextView atType;
    @BindView(R.id.etPCLName)
    EditText etPCLName;
    @BindView(R.id.btCountDp)
    Button btCountDp;

    @BindView(R.id.etProvisionPercentage)
    EditText etProvisionPercentage;

    @BindView(R.id.iv_tooltipPaymentType)
    ImageView iv_tooltipPaymentType;
    @BindView(R.id.iv_tooltipCoverageType)
    ImageView iv_tooltipCoverageType;
    @BindView(R.id.iv_tooltipAsuransiJiwaKredit)
    ImageView iv_tooltipAsuransiJiwaKredit;

    List<Tenor> tenorMaster = new ArrayList<>();
    List<String> tenorList = new ArrayList<>();
    List<String> coverageType = new ArrayList<>();
    List<String> paymentType = new ArrayList<>();
    List<String> provisionType = new ArrayList<>();
    List<String> addOnPaymentType = new ArrayList<>();
    List<String> typeTACP = new ArrayList<>();

    /*Data*/
    //List<CarModel> carMaster = new ArrayList<>();
    List<String> carModel = new ArrayList<>();
    List<Depresiasi> depresiasis = new ArrayList<>();
    //List<AsuransiPremi> asuransiPremis;
    //List<Double> PCL;
    double PCL = 0.0;
    List<Integer> biayaAdmin = new ArrayList<>();
    List<Integer> biayaAdminADDM = new ArrayList<>();
    List<Integer> polis = new ArrayList<>();
    String coverageAsuransi = "";
    List<String> carTypesList = new ArrayList<>();
    List<CarType> carTypes = new ArrayList<>();
    List<String> tenors = new ArrayList<>();

    List<OTRDepresiasi> otrDepresiasis = new ArrayList<>();
    List<Double> premiAmountPercentage = new ArrayList<>();
    List<Long> premiAmount = new ArrayList<>();
    List<Long> premiAmountSumUp = new ArrayList<>();
    List<Long> pokokHutang;
    List<Double> bungaADDB = new ArrayList<>();
    List<Double> bungaADDM = new ArrayList<>();
    List<Long> pokokHutangPrepaid;
    List<com.drife.digitaf.ORM.Database.CarModel> cars = new ArrayList<>();
    List<PackageRule> packageRules = new ArrayList<>();
    List<PackageRule> packageRulesADDM = new ArrayList<>();
    List<PackageRule> packageRulesADDB = new ArrayList<>();

    List<PremiAmount> premiAmounts = new ArrayList<>();
    List<Long> asuransiCicil = new ArrayList<>();
    List<Long> asuransiTDP = new ArrayList<>();
    List<Long> asuransiCicilSumUp = new ArrayList<>();
    List<Long> asuransiTDPSumUp = new ArrayList<>();
    List<Long> biayaAsuransiKendaraan = new ArrayList<>();
    long pokokHutangNonPCL = 0;
    long installmentNonPCL = 0;
    long pokokHutangWithPCL = 0;
    long installmentWithPCL = 0;
    int addOn = 0;
    long pokokHutangWithAddOn = 0;
    long installmentWithAddOn = 0;
    long pclPrepaid = 0;
    long provision = 0;
    long paketServis = 0;
    long tdpAkhir = 0;
    long totalHutangFinal = 0;
    long totalHutangAfterDiskon = 0;
    double rateBungaAfterDiskon = 0;
    double RATE = 0.0;
    long pokokHutangAkhir = 0l;
    long AR = 0l;
    long totalHutangNonPCL = 0l;

    List<Double> minRateADDM = new ArrayList<>();
    List<Double> maxRateADDM = new ArrayList<>();
    List<Double> baseRateADDM = new ArrayList<>();
    List<Double> dpMinADDM = new ArrayList<>();
    List<Double> dpMaxADDM = new ArrayList<>();
    List<Double> dbBaseADDM = new ArrayList<>();
    List<Double> dpMinValueADDM = new ArrayList<>();
    List<Double> dpMaxValueADDM = new ArrayList<>();
    List<Double> dpBaseValueADDM = new ArrayList<>();
    List<Double> provisiMinADDM = new ArrayList<>();
    List<Double> provisiMaxADDM = new ArrayList<>();
    List<Double> provisiBaseADDM = new ArrayList<>();
    List<Double> provisiMinValueADDM = new ArrayList<>();
    List<Double> provisiMaxValueADDM = new ArrayList<>();
    List<Double> provisiBaseValueADDM = new ArrayList<>();
    List<Long> totalARADDM = new ArrayList<>();

    List<Double> minRateADDB = new ArrayList<>();
    List<Double> maxRateADDB = new ArrayList<>();
    List<Double> baseRateADDB = new ArrayList<>();
    List<Double> dpMinADDB = new ArrayList<>();
    List<Double> dpMaxADDB = new ArrayList<>();
    List<Double> dbBaseADDB = new ArrayList<>();
    List<Double> dpMinValueADDB = new ArrayList<>();
    List<Double> dpMaxValueADDB = new ArrayList<>();
    List<Double> dpBaseValueADDB = new ArrayList<>();
    List<Double> provisiMinADDB = new ArrayList<>();
    List<Double> provisiMaxADDB = new ArrayList<>();
    List<Double> provisiBaseADDB = new ArrayList<>();
    List<Double> provisiMinValueADDB = new ArrayList<>();
    List<Double> provisiMaxValueADDB = new ArrayList<>();
    List<Double> provisiBaseValueADDB = new ArrayList<>();
    List<Long> totalARADDB = new ArrayList<>();

    List<CoverageInsurance> coverageInsurancesTLO = new ArrayList<>();
    List<CoverageInsurance> coverageInsurancesCOMPRE = new ArrayList<>();


    private long inputOtr = 0;
    private int tjh3 = 100000;
    private long premiAmountSumUpCurrentValue = 0;
    private long DP = 0;
    private double DPPercentage = 0.0;
    private long PHAwal = 0;
    private String selectedCar = "";
    private String prepaid = "PREPAID";
    private String onloan = "ONLOAN";
    private String no = "NO";
    private String addm = "ADDM";
    private String addb = "ADDB";
    private String selectedType = "";
    //private int selectedCarIndex = -1;
    private String allrisk = "ALL RISK";
    private String comprehensive = "COMPREHENSIVE";
    private String tlo = "TLO";
    private String combine = "COMBINATION";
    private int selectedTenorIndex = -1;

    int REQUEST_CODE_RESULT = 2;
    String packageCode = "";

    boolean provisiStatus = true;

    private List<String> categoryGroupInPackage = new ArrayList<>();
    private List<String> carInPackage = new ArrayList<>();
    private boolean isSimulasiPaket = false;
    private String selectedPackageCode = "";
    private UserSession userSession;
    private Dealer dealer;
    private boolean isSimulasiBudget = false;
    private com.drife.digitaf.ORM.Database.CarModel selectedCarModel;
    private CarType selectedCarType;
    private String pvInscoCode;
    private String pclInscoCode;

    private String so = "so";
    private String sd = "sales";
    private double selectedDpPercentage = 0.0;
    //private long DPRound = 0l;
    private InquiryParam inquiryParam;
    Long id ;
    String user_id, car_name, car_code;
    private Draft draft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kredit);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_RESULT){
            if(resultCode == RESULT_OK){
                Intent intent = null;

                if (data != null) {
                    if (data.getBooleanExtra("is_draft", false)) {
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
        TemporaryValue.resetData();
        TemporaryValue.CustomerId = TextUtility.randomString(10);
        userSession = SharedPreferenceUtils.getUserSession(this);
        dealer = SelectedData.SelectedDealer;

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
        selectedCarModel = SelectedData.selectedCarModel;
    }

    private void initListeners(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cbProvision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyProvision.setVisibility(View.VISIBLE);
                    //etRate.setEnabled(false);
                    if (inquiryParam.getProvision_fee().equals("0")){

                    }else {
                        etProvisionAmount.setText(inquiryParam.getProvision_fee());
                    }
                }else{
                    lyProvision.setVisibility(View.GONE);
                    if (inquiryParam.getProvision_fee().equals("0")){
                        etProvisionAmount.setText("");
                        etProvisionPercentage.setText("");
                        provisiStatus = true;
                    }else {
                        etProvisionAmount.setText(inquiryParam.getProvision_fee());
                        etProvisionAmount.setText("");
                        provisiStatus = true;
                    }
                    //etRate.setEnabled(true);
                }
            }
        });

//        if (inquiryParam.getPackage_service_price().equals("0")){
//
//        }else {
//            etPackageAmount.setText(inquiryParam.getPackage_service_price());
//        }

        cbServicePackage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyServicePackage.setVisibility(View.VISIBLE);
                }else{
                    lyServicePackage.setVisibility(View.GONE);
                }
            }
        });

        btnLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset();
                String paymentAsuransi = spinnerPaymentType.getSelectedItem().toString();
                String coverageAsuransi = spinnerCoverageType.getSelectedItem().toString();
                int indexTenor = spinnerTenor.getSelectedItemPosition();
                selectedTenorIndex = indexTenor;

                boolean hasPCL = false;
                String pclType = spinnerTACP.getSelectedItem().toString();
                if(!pclType.equals(no)){
                    hasPCL = true;
                }

                String provisionType = null;
                int provisionAmount = 0;
                if(cbProvision.isChecked()){
                    provisionType = spinnerProvisionType.getSelectedItem().toString();
                    if(!etProvisionAmount.getText().toString().equals("")){
                        provisionAmount = Integer.parseInt(etProvisionAmount.getText().toString().replaceAll("Rp", "").replaceAll(",", ""));
                    }
                }

                String paketServiceType = null;
                int paketServiceAmount = 0;
                if(cbServicePackage.isChecked()){
                    paketServiceType = spinnerAddOnPaymentType.getSelectedItem().toString();
                }
                if(!etPackageAmount.getText().toString().equals("")){
                    paketServiceAmount = Integer.parseInt(etPackageAmount.getText().toString().replaceAll("Rp", "").replaceAll(",", ""));
                }

                String typeAngsuran = "";
                boolean stat = true;
                if(rbADDM.isChecked()){
                    typeAngsuran = addm;
                    SelectedData.JenisAngsuran = addm;
                }else{
                    typeAngsuran = addb;
                    SelectedData.JenisAngsuran = addb;
                }

                String rate = etRate.getText().toString();
                if(!rate.equals("")){
                    RATE = Double.parseDouble(rate);
                }

                long diskon = 0l;
                String strDiskon = etDiskon.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                if(strDiskon != null && !strDiskon.equals("")){
                    diskon = Long.parseLong(strDiskon);
                }

                reset();
                String otr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                String dp = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                if (otr.equals("")) {
                    otr = "0";
                }

                if (dp.equals("")) {
                    dp = "0";
                }

                DP = Long.parseLong(dp);
                //new asyncGetData().execute();

                double dpValue = Double.parseDouble(dp);
                double dpMin = 0.0;
                double dpMax = 0.0;
                double rateMin = 0.0;
                double rateMax = 0.0;
                double provisionMin = 0.0;
                double provisionMax = 0.0;

                try {
                    countPaketServis();
                    inputOtr = inputOtr+Long.parseLong(otr);
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"btnLanjutkan.setOnClickListener","input otr",e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                            .setMessage("OTR yang di inputkan terlalu besar")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }

                double recountDpPercentage = countDpPercentage(inputOtr,DP);
                //double dpPercentage = Double.parseDouble(etDpPercentage.getText().toString());
                //double dpPercentage = Math.round(recountDpPercentage);

                if(cbServicePackage.isChecked()){
                    String otrInput = CurrencyFormat.formatNumber(etOtr.getText().toString());

                    //recountDpPercentage = countDpPercentage(Long.parseLong(otrInput),DP);
                    recountDpPercentage = Double.parseDouble(etDpPercentage.getText().toString());
                    /*String a = CurrencyFormat.formatNumber(etOtr.getText().toString());
                    String b = CurrencyFormat.formatNumber(etDp.getText().toString());
                    recountDpPercentage = countDpPercentage(Long.parseLong(a),Long.parseLong(b));*/

                }
                double dpPercentage = recountDpPercentage;

                /*count new dp based on new otr*/
                //DP = new Double(inputOtr*dpPercentage/100).longValue();
                Log.d("singo", "old DP : "+DP);
                if(cbServicePackage.isChecked()){
                    DP = new Double((float)inputOtr*dpPercentage/100).longValue();
                    if(spinnerAddOnPaymentType.getSelectedItem().toString().equals(prepaid)){
                        String a = CurrencyFormat.formatNumber(etDp.getText().toString());
                        DP = Long.parseLong(a);
                    }
                    //DPRound = Math.round(DP);
                    Log.d("singo", "new DP : "+DP);
                }
                PHAwal = countPHAwal(inputOtr,DP);

                if(cbServicePackage.isChecked()){
                    if(spinnerAddOnPaymentType.getSelectedItem().toString().equals(prepaid)){
//                        int a = Integer.parseInt(inquiryParam.getPackage_service_price());
//                        int b = 0;
//                        if (etPackageAmount.getText().toString().equals("")){
//                            b = Integer.parseInt(CurrencyFormat.formatNumber("0"));
//                        }else {
//                         b = Integer.parseInt(CurrencyFormat.formatNumber(etPackageAmount.getText().toString()));
//                        }
//                        if(a == b){
//
//                        }else if (a > b){
//                            int c = a-b;
//                            long d = inputOtr - c;
//                            dpPercentage = countDpPercentage(d,DP);
//                        } else if (a < b){
//                            int c = b - a;
//                            long d = inputOtr - c;
                            dpPercentage = countDpPercentage(inputOtr,DP);
//                        }
                    }
                }

                Log.d("singo", "inputotr : "+inputOtr);
                Log.d("singo", "new percentage dp : "+dpPercentage);
                getBungaMobil(Double.parseDouble(KalkulatorUtility.setRoundDecimalPlace(dpPercentage)));

                boolean status = true;

                try{
                    if(rbADDM.isChecked()){
                        dpMin = dpMinValueADDM.get(selectedTenorIndex);
                        dpMax = dpMaxValueADDM.get(selectedTenorIndex);
                        rateMin = minRateADDM.get(selectedTenorIndex);
                        rateMax = maxRateADDM.get(selectedTenorIndex);
                        provisionMin = provisiMinADDM.get(selectedTenorIndex);
                        provisionMax = provisiMaxADDM.get(selectedTenorIndex);
                    }else if(rbADDB.isChecked()){
                        dpMin = dpMinValueADDB.get(selectedTenorIndex);
                        dpMax = dpMaxValueADDB.get(selectedTenorIndex);
                        rateMin = minRateADDB.get(selectedTenorIndex);
                        rateMax = maxRateADDB.get(selectedTenorIndex);
                        provisionMin = provisiMinADDB.get(selectedTenorIndex);
                        provisionMax = provisiMaxADDB.get(selectedTenorIndex);
                    }
                }catch (Exception e){
                    status = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                            .setTitle("DP out of range")
                            .setMessage("DP yang Anda inputkan diluar range. Cek kembali OTR dan DP yang Anda inputkan.")
                            .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.show();
                }

                if (!strDiskon.isEmpty() && !otr.isEmpty()) {
                    if (Long.parseLong(strDiskon) >= Long.parseLong(otr)) {
                        status = false;
                        AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                                .setMessage("Diskon tidak boleh lebih besar atau sama dengan OTR.")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.show();
                    }
                }

                if(status){
                    try {
                        double rateValue = Double.parseDouble(!etRate.getText().toString().equals("") ? etRate.getText().toString() : "0");

                        if(validasiPCL(indexTenor)){
                            if(validasi() && validateMandatory(etOtr) && validateMandatory(etRate)
                                    && validateMandatory(etDp) && validateMandatory(etDpPercentage)){
                                if(userSession.getUser().getType().equals("sales")){
                                    if(rateValue>= rateMin && rateValue<= rateMax){
                                        RATE = rateValue;

                                        countKredit(Long.parseLong(otr),paymentAsuransi,indexTenor,pclType,hasPCL,provisionType,provisionAmount,paketServiceType,paketServiceAmount,
                                                typeAngsuran, diskon,coverageAsuransi,provisionMin,provisionMax,rateValue);

                                        int ten = Integer.parseInt(tenorMaster.get(indexTenor).getName());
                                        //AR = installmentWithAddOn*ten;
                                        AR = installmentWithPCL*ten;

                                /*HasilPerhitungan hasilPerhitungan = new HasilPerhitungan(selectedCar, inputOtr+"", SelectedData.SelectedPackage, tenorMaster.get(indexTenor).getName(), RATE+"",
                                        installmentWithAddOn+"", DP+"", tdpAkhir+"",AR+"",premiAmountSumUp.get(selectedTenorIndex)+"",
                                        SelectedData.pclPremiAmount,SelectedData.AdminFee, SelectedData.JenisAngsuran,spinnerType.getSelectedItem().toString());*/

                                    /*DP = KalkulatorUtility.roundUp(DP);
                                    tdpAkhir = KalkulatorUtility.roundUp(tdpAkhir);
                                    AR = KalkulatorUtility.roundUp(AR);*/

                                    /*DP = Math.round(DP);
                                    tdpAkhir = Math.round(tdpAkhir);
                                    AR = Math.round(AR);*/

                                        if(validasi()){

                                            HasilPerhitungan hasilPerhitungan = new HasilPerhitungan(selectedCar, inputOtr+"", SelectedData.SelectedPackage, tenorMaster.get(indexTenor).getName(), RATE+"",
                                                    installmentWithPCL+"", DP+"", tdpAkhir+"",AR+"",premiAmountSumUp.get(selectedTenorIndex)+"",
                                                    SelectedData.pclPremiAmount,SelectedData.AdminFee, SelectedData.JenisAngsuran,selectedCarType.getName());
                                            SelectedData.PokokHutangAkhir = pokokHutangAkhir+"";
                                            SelectedData.pvPremiSumUp = premiAmountSumUp.get(selectedTenorIndex)+"";
                                            SelectedData.pvInscoCode = pvInscoCode;
                                            SelectedData.pclInscoCode = pclInscoCode;
                                            SelectedData.selectedCarModel = selectedCarModel;
                                            if(spinnerPaymentType.getSelectedItem().toString().equals(prepaid)){
                                                SelectedData.pvPaymentType = prepaid;
                                            }else{
                                                SelectedData.pclPaymentType = onloan;
                                            }

                                            if(spinnerTACP.getSelectedItem().toString().equals(prepaid)){
                                                SelectedData.pclPaymentType = prepaid;
                                            }else{
                                                SelectedData.pclPaymentType = onloan;
                                            }

                                            if(provisiStatus){
                                                saveTemporaryParams();
                                                //Log.d("singo", "inputOTR before intent : "+inputOtr);
//                                                Intent intent = new Intent(DraftinputItemKredit.this, HasilPerhitunganActivity.class);
//                                                intent.putExtra("data",hasilPerhitungan);
                                                Intent intent = new Intent(DraftinputItemKredit.this, HasilPerhitungandraft.class);
//                                                intent.putExtra("data",hasilPerhitungan);
                                                intent.putExtra("data", (Serializable) inquiryParam);
                                                intent.putExtra("id", id);
                                                intent.putExtra("user_id", user_id);
                                                intent.putExtra("draft", (Serializable) draft);
                                                startActivityForResult(intent,REQUEST_CODE_RESULT);
                                                finish();
                                            }else{
                                                AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                                                        .setTitle("Provisi tidak valid")
                                                        .setMessage("Provisi yang di inputkan harus pada rentang "+provisionMin+"-"+provisionMax)
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                dialogInterface.dismiss();
                                                            }
                                                        });
                                                builder.show();
                                            }

                                        }else{
                                            Log.d("singo","kondisi 1");
                                            Toast.makeText(DraftinputItemKredit.this,"Field belum lengkap",Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                                                .setTitle("Rate tidak valid")
                                                .setMessage("Rate yang di inputkan harus pada rentang "+rateMin+"-"+rateMax)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                });
                                        builder.show();
                                    }
                                }else{
                                    if(!isSimulasiPaket){
                                        RATE = rateValue;

                                        countKredit(Long.parseLong(otr),paymentAsuransi,indexTenor,pclType,hasPCL,provisionType,provisionAmount,paketServiceType,paketServiceAmount,
                                                typeAngsuran, diskon,coverageAsuransi,provisionMin,provisionMax,rateValue);

                                        int ten = Integer.parseInt(tenorMaster.get(indexTenor).getName());
                                        //AR = installmentWithAddOn*ten;
                                        AR = installmentWithPCL*ten;

                                        if(validasi()){
                                            HasilPerhitungan hasilPerhitungan = new HasilPerhitungan(selectedCar, inputOtr+"", SelectedData.SelectedPackage, tenorMaster.get(indexTenor).getName(), RATE+"",
                                                    installmentWithPCL+"", DP+"", tdpAkhir+"",AR+"",premiAmountSumUp.get(selectedTenorIndex)+"",
                                                    SelectedData.pclPremiAmount,SelectedData.AdminFee, SelectedData.JenisAngsuran,selectedCarType.getName());
                                            SelectedData.PokokHutangAkhir = pokokHutangAkhir+"";
                                            SelectedData.pvPremiSumUp = premiAmountSumUp.get(selectedTenorIndex)+"";
                                            SelectedData.pvInscoCode = pvInscoCode;
                                            SelectedData.pclInscoCode = pclInscoCode;
                                            SelectedData.selectedCarModel = selectedCarModel;
                                            if(spinnerPaymentType.getSelectedItem().toString().equals(prepaid)){
                                                SelectedData.pvPaymentType = prepaid;
                                            }else{
                                                SelectedData.pclPaymentType = onloan;
                                            }

                                            if(spinnerTACP.getSelectedItem().toString().equals(prepaid)){
                                                SelectedData.pclPaymentType = prepaid;
                                            }else{
                                                SelectedData.pclPaymentType = onloan;
                                            }

                                            if(provisiStatus){
                                                saveTemporaryParams();
                                                //Log.d("singo", "inputOTR before intent : "+inputOtr);
                                                Intent intent = new Intent(DraftinputItemKredit.this, HasilPerhitungandraft.class);
//                                                intent.putExtra("data",hasilPerhitungan);
                                                intent.putExtra("data", (Serializable) inquiryParam);
                                                intent.putExtra("id", id);
                                                intent.putExtra("user_id", user_id);
                                                intent.putExtra("draft", (Serializable) draft);
                                                startActivityForResult(intent,REQUEST_CODE_RESULT);
                                                finish();
                                            }else{
                                                AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                                                        .setTitle("Provisi tidak valid")
                                                        .setMessage("Provisi yang di inputkan harus pada rentang "+provisionMin+"-"+provisionMax)
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                dialogInterface.dismiss();
                                                            }
                                                        });
                                                builder.show();
                                            }

                                        }else{
                                            Log.d("singo","kondisi 1");
                                            Toast.makeText(DraftinputItemKredit.this,"Field belum lengkap",Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        if(rateValue>= rateMin && rateValue<= rateMax){
                                            RATE = rateValue;

                                            countKredit(Long.parseLong(otr),paymentAsuransi,indexTenor,pclType,hasPCL,provisionType,provisionAmount,paketServiceType,paketServiceAmount,
                                                    typeAngsuran, diskon,coverageAsuransi,provisionMin,provisionMax,rateValue);

                                            int ten = Integer.parseInt(tenorMaster.get(indexTenor).getName());
                                            //AR = installmentWithAddOn*ten;
                                            AR = installmentWithPCL*ten;

                                            HasilPerhitungan hasilPerhitungan = new HasilPerhitungan(selectedCar, inputOtr+"", SelectedData.SelectedPackage, tenorMaster.get(indexTenor).getName(), RATE+"",
                                                    installmentWithPCL+"", DP+"", tdpAkhir+"",AR+"",premiAmountSumUp.get(selectedTenorIndex)+"",
                                                    SelectedData.pclPremiAmount,SelectedData.AdminFee, SelectedData.JenisAngsuran,selectedCarType.getName());
                                            SelectedData.PokokHutangAkhir = pokokHutangAkhir+"";
                                            SelectedData.pvPremiSumUp = premiAmountSumUp.get(selectedTenorIndex)+"";
                                            SelectedData.pvInscoCode = pvInscoCode;
                                            SelectedData.pclInscoCode = pclInscoCode;
                                            SelectedData.selectedCarModel = selectedCarModel;

                                            if(validasi()){
                                                if(provisiStatus){
                                                    saveTemporaryParams();
                                                    //Log.d("singo", "inputOTR before intent : "+inputOtr);
                                                    Intent intent = new Intent(DraftinputItemKredit.this, HasilPerhitungandraft.class);
//                                                    intent.putExtra("data",hasilPerhitungan);
                                                    intent.putExtra("data", (Serializable) inquiryParam);
                                                    intent.putExtra("id", id);
                                                    intent.putExtra("user_id", user_id);
                                                    intent.putExtra("draft", (Serializable) draft);
                                                    startActivityForResult(intent,REQUEST_CODE_RESULT);
                                                    finish();
                                                }else{
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                                                            .setTitle("Provisi tidak valid")
                                                            .setMessage("Provisi yang di inputkan harus pada rentang "+provisionMin+"-"+provisionMax)
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    dialogInterface.dismiss();
                                                                }
                                                            });
                                                    builder.show();
                                                }

                                            }else{
                                                Log.d("singo","kondisi 1");
                                                Toast.makeText(DraftinputItemKredit.this,"Field belum lengkap",Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                                                    .setTitle("Rate tidak valid")
                                                    .setMessage("Rate yang di inputkan harus pada rentang "+rateMin+"-"+rateMax)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.dismiss();
                                                        }
                                                    });
                                            builder.show();
                                        }
                                    }
                                }
                            } else if(etDp.getText().toString().equals("") || etDpPercentage.getText().toString().equals("")){
                                Toast.makeText(DraftinputItemKredit.this,"Inputkan DP terlebih dahulu.",Toast.LENGTH_SHORT).show();
                            }else if (validasi()) {
                                Toast.makeText(DraftinputItemKredit.this,"Pilih tipe mobil terlebih dahulu.",Toast.LENGTH_SHORT).show();
                            } else{
                                Log.d("singo","kondisi 2");
                                //reset();
                                Toast.makeText(DraftinputItemKredit.this,"Field belum lengkap",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        LogUtility.logging(TAG,LogUtility.errorLog,"btnLanjutkan.setOnClickListener","Exception",e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                                .setTitle("DP out of range")
                                .setMessage("DP yang Anda inputkan diluar range. NOTE : Jika Anda menggunakan paket servis, besar DP amount dan percentage akan di hitung ulang")
                                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.show();
                    }
                }

            }
        });

        spinnerPaymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedData.PaymentType = spinnerPaymentType.getSelectedItemPosition();
                SelectedData.PVPaymentType = spinnerPaymentType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCoverageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coverageAsuransi = spinnerCoverageType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCar = spinnerModel.getSelectedItem().toString();
                selectedCarIndex = position;
                String carCode = cars.get(selectedCarIndex).getCarCode();
                //getBungaMobil();
                if(!isSimulasiPaket){
                    getPackageRulesNonPaket(carCode);
                }else{
                    getPackageRules(carCode);
                }
                setCarType();
                setPaymentTypeList();
                setCoverageTypeList();
                setTACPList();
                setProvisionTypeList();
                //Log.d("singo","minDP : "+JSONProcessor.toJSON(dpMinADDM));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

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
                selectedCarModel = (com.drife.digitaf.ORM.Database.CarModel) adapterView.getItemAtPosition(i);
                selectedCar = selectedCarModel.getCarName();
                //selectedCarIndex = position;
                /*String carCode = selectedCarModel.getCarCode();
                if(!isSimulasiPaket){
                    getPackageRulesNonPaket(carCode);
                }else{
                    getPackageRules(carCode);
                }
                setCarType();
                setPaymentTypeList();
                setCoverageTypeList();
                setTACPList();
                setProvisionTypeList();*/

                setDataMobil();
            }
        });

        /*spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = spinnerType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        /*atType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    atType.showDropDown();
                }
            }
        });*/

        atType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atType.showDropDown();
            }
        });

        atType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCarType = (CarType) adapterView.getItemAtPosition(i);
                selectedType = selectedCarType.getName();
            }
        });

        spinnerTenor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*setPaymentTypeList();
                setCoverageTypeList();
                setTACPList();
                setProvisionTypeList();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rbADDM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SelectedData.JenisAngsuran = addm;
                    /*setPaymentTypeList();
                    setCoverageTypeList();
                    setTACPList();
                    setProvisionTypeList();*/
                }
            }
        });

        rbADDB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SelectedData.JenisAngsuran = addb;
                    /*setPaymentTypeList();
                    setCoverageTypeList();
                    setTACPList();
                    setProvisionTypeList();*/
                }
            }
        });

        etRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if(!etRate.getText().toString().equals(SelectedData.Rate)){
                    cbProvision.setChecked(false);
                    cbProvision.setEnabled(false);
                }else{
                    cbProvision.setEnabled(true);
                }*/

                CurrencyFormat.formatPercentage(s);
            }
        });

        etOtr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                CurrencyFormat.changeFormat(this, editable, etOtr);

                if(!etDpPercentage.getText().toString().equals("")){
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String strpercentage = etDpPercentage.getText().toString();
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(strpercentage.equals("")){
                                strpercentage = "0";
                            }

                            //float otr = Float.parseFloat(strotr);
                            long otr = Long.parseLong(strotr);
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
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
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
                            long amount = Long.parseLong(stramount);
                            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            etDpPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etDp.setOnKeyListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }
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
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
                            long amount = Long.parseLong(stramount);
                            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            //Log.d("singo", "DP sebelum di roundup : "+percentage);
                            etDpPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");

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
                /*if(hasFocus){

                }*/

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
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
                            long amount = Long.parseLong(stramount);
                            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }

                            etDpPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");

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

        etDpPercentage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String strpercentage = etDpPercentage.getText().toString();
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(strpercentage.equals("")){
                                strpercentage = "0";
                            }

                            //float otr = Float.parseFloat(strotr);
                            long otr = Long.parseLong(strotr);
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
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

        etDpPercentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String strpercentage = etDpPercentage.getText().toString();
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(strpercentage.equals("")){
                                strpercentage = "0";
                            }

                            //float otr = Float.parseFloat(strotr);
                            long otr = Long.parseLong(strotr);
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
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
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etPercentage.setOnFocusChangeListener",e.getMessage());
                        }
                    }else {
                        etOtr.setError("OTR harus diisi");
                    }
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
            public void afterTextChanged(Editable editable) {
                CurrencyFormat.changeFormat(this, editable, etDp);
            }
        });

        etDpPercentage.addTextChangedListener(new TextWatcher() {
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

        etDiskon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                CurrencyFormat.changeFormat(this, editable, etDiskon);
            }
        });

        etProvisionAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!etProvisionAmount.getText().toString().equals("")){
                    Log.d("singo", "amount "+etProvisionAmount.getText().toString());
                    String s = etProvisionAmount.getText().toString();
                    CurrencyFormat.changeFormat(this, s, etProvisionAmount);
                    //etProvisionPercentage.setText("");
                    //etProvisionPercentage.setEnabled(false);
                }else{
                    Log.d("singo", "amount empty");
                    etProvisionPercentage.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!etProvisionAmount.getText().toString().equals("") && !etProvisionAmount.getText().equals("Rp")){
                    //Log.d("singo", "amount "+etProvisionAmount.getText().toString());
                    //String s = etProvisionAmount.getText().toString();
                    //CurrencyFormat.changeFormat(this, s, etProvisionAmount);
                    //etProvisionPercentage.setText("");
                    etProvisionPercentage.setEnabled(false);
                }else{
                    Log.d("singo", "amount empty");
                    etProvisionPercentage.setEnabled(true);
                }

            }
        });

        etProvisionPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etProvisionPercentage.getText().toString().equals("")){
                    CurrencyFormat.formatPercentage(s);
                    //etProvisionAmount.setText("");
                    etProvisionAmount.setEnabled(false);
                }else{
                    Log.d("singo", "percentage empty");
                    etProvisionAmount.setEnabled(true);
                }
            }
        });

        etPackageAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                CurrencyFormat.changeFormat(this, editable, etPackageAmount);
            }
        });

        /*etProvisionAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String stramount = etProvisionAmount.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(stramount.equals("")){
                                stramount = "0";
                            }

                            int otr = Integer.parseInt(strotr);
                            int amount = Integer.parseInt(stramount);
                            double percentage = KalkulatorUtility.countPercentage(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            etProvisionPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");
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

        etProvisionAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String stramount = etProvisionAmount.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(stramount.equals("")){
                                stramount = "0";
                            }

                            int otr = Integer.parseInt(strotr);
                            int amount = Integer.parseInt(stramount);
                            double percentage = KalkulatorUtility.countPercentage(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            etProvisionPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etDp.setOnFocusChangeListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }

                }
            }
        });

        etProvisionPercentage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String strpercentage = etProvisionPercentage.getText().toString();
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(strpercentage.equals("")){
                                strpercentage = "0";
                            }

                            //int otr = Integer.parseInt(strotr);
                            double percentage = Double.parseDouble(strpercentage);
                            float amount = 0;
                            if(percentage<=100){
                                amount = KalkulatorUtility.countAmount(pokokHutangWithPCL,percentage);
                            }

                            etProvisionAmount.setText(KalkulatorUtility.floadToInt(amount)+"");
                            if (strpercentage.equals("0")) {
                                etProvisionAmount.setText("");
                            }
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

        etProvisionPercentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!etOtr.getText().toString().equals("")){
                    try {
                        String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                        String strpercentage = etProvisionPercentage.getText().toString();
                        if(strotr.equals("")){
                            strotr = "0";
                        }

                        if(strpercentage.equals("")){
                            strpercentage = "0";
                        }

                        //int otr = Integer.parseInt(strotr);
                        double percentage = Double.parseDouble(strpercentage);
                        float amount = 0;
                        if(percentage<=100){
                            amount = KalkulatorUtility.countAmount(pokokHutangWithPCL,percentage);
                        }
                        etProvisionAmount.setText(KalkulatorUtility.floadToInt(amount)+"");
                        if (strpercentage.equals("0")) {
                            etProvisionAmount.setText("");
                        }
                    }catch (Exception e){
                        LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etPercentage.setOnFocusChangeListener",e.getMessage());
                    }
                }else {
                    etOtr.setError("OTR harus diisi");
                }

            }
        });*/

        btCountDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etDpPercentage.getText().toString().equals("")){
                    if(!etOtr.getText().toString().equals("")){
                        try {
                            String strotr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
                            String strpercentage = etDpPercentage.getText().toString();
                            if(strotr.equals("")){
                                strotr = "0";
                            }

                            if(strpercentage.equals("")){
                                strpercentage = "0";
                            }

                            //float otr = Float.parseFloat(strotr);
                            long otr = Long.parseLong(strotr);
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
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
                            /*if(cbServicePackage.isChecked()){
                                otr = otr+paketServis;
                            }*/
                            long amount = Long.parseLong(stramount);
                            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
                            if(percentage< 0.01){
                                percentage = 0;
                            }
                            etDpPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");
                        }catch (Exception e){
                            LogUtility.logging(TAG,LogUtility.errorLog,"initListeners","etDp.setOnKeyListener",e.getMessage());
                        }
                    }else{
                        etOtr.setError("OTR harus diisi");
                    }
                }
            }
        });

        iv_tooltipPaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerPaymentType.getSelectedItem().toString().equals(prepaid)){
                    showToolTip("Prepaid artinya Premi dibayarkan tunai (masuk ke dalam TDP)");
                }else if(spinnerPaymentType.getSelectedItem().toString().equals(onloan)){
                    showToolTip("Onloan artinya Premi dibayarkan secara kredit (masuk ke Pokok Hutang)");
                }
            }
        });

        iv_tooltipCoverageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTip("Combination adalah coverage asuransi dengan tahun pertama Comprehensive dan tahun selanjutnya TLO.");
            }
        });

        iv_tooltipAsuransiJiwaKredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTip("Asuransi yang memberikan jaminan pelunasan sisa hutang apabila tertanggung meninggal dunia atau mengalami ketidakmampuan tetap sesuai ketentuan yang belaku.");
            }
        });
    }

    private void callFunctions(){
        configureDefaultData();
        setMaster();
    }

    private void configureDefaultData(){
        //setTenor();
        selectedDpPercentage = Double.parseDouble(SelectedData.DpPercentage);

        int simPaket = 0;
        int simBudget = 0;
        inquiryParam = (InquiryParam) getIntent().getSerializableExtra("data");
        draft = (Draft) getIntent().getSerializableExtra("draft");
        id = getIntent().getLongExtra("id",1);
        user_id = getIntent().getStringExtra("user_id");

        if (inquiryParam.getProvision_fee().equals("0")){
            cbProvision.setChecked(false);
        }else {
            cbProvision.setChecked(true);
        }

        if (inquiryParam.getPackage_service_price() !=null){
            if (inquiryParam.getPackage_service_price().equals("0")){
                cbServicePackage.setChecked(false);
            }else {
                cbServicePackage.setChecked(true);
                etPackageAmount.setText(inquiryParam.getPackage_service_price());
            }
        }

        if (inquiryParam.getFirst_installment_type().equals("AD")){
            rbADDB.setChecked(false);
            rbADDM.setChecked(true);
        }else {
            rbADDB.setChecked(true);
            rbADDM.setChecked(false);
        }

        if (draft.getIs_simulasi_paket() !=null) {
            if (draft.getIs_simulasi_paket().equals("1")) {
                simPaket = 1;
            }
        }

        if (draft.getIs_simulasi_budget() !=null) {
            if (draft.getIs_simulasi_budget().equals("1")) {
                simBudget = 1;
            }
        }


        List<CarType> cartype = new ArrayList<>();
        cartype = CarController.getAllCarTypeName(inquiryParam.getType());
        CarType carType = cartype.get(0);
        String type = carType.getName();
        atType.setText(type);
        selectedCarType = carType;
        selectedType = type;

//        car_name = getIntent().getStringExtra("car_name");
//        car_code = getIntent().getStringExtra("car_code");

        if(simPaket==1){
            Log.d("draft_simulasi", simPaket+"");
            isSimulasiPaket = true;
            selectedPackageCode = inquiryParam.getProduct_offering_code();
            SelectedData.SelectedPackageCode = inquiryParam.getProduct_offering_code();
        }

        if(simBudget == 1){
            Log.d("draft_simulasi", simBudget+"");
            isSimulasiBudget = true;
        }

        if(isSimulasiPaket){
            Log.d("singo", "is simulasi paket");
            //setCarListSimulatiPaket();
            if(userSession.getUser().equals(so)){
                setCarListSimulatiPaket(dealer.getBrandCode());
            }else{
                setCarListSimulatiPaket();
            }

            etDp.setText(SelectedData.BaseDP);
            spinnerTenor.setEnabled(false);
            atModel.setText(SelectedData.selectedCarModel.getCarName());
            setDataMobil();
            //spinnerType.setEnabled(false);
            //spinnerCoverageType.setEnabled(false);
            //spinnerTACP.setEnabled(false);

            /*if(SelectedData.PVPaymentType.equals(allrisk)){

            }
            spinnerType.setSelection();
            SelectedData.PVPaymentType = selectedPackageRule.getTAVPPayment();
            SelectedData.PVCoverageType = selectedPackageRule.getTAVPCoverage();
            SelectedData.PCLPaymentType = selectedPackageRule.getPCLPayment();*/
        }else{
            Log.d("singo", "is not simulasi paket");
            //setCarList();
            if(userSession.getUser().getType().equals(so)){
                setCarList(dealer.getBrandCode());
            }else{
                setCarList(userSession.getUser().getDealer().getBrand().getCode());
            }
//            String car_name = getIntent().getStringExtra("carname");
            atModel.setText(selectedCarModel.getCarName());
            setDataMobil();

        }
        //setTenorList();
        //setCoverageTypeList();
        //setPaymentTypeList();
        //setProvisionTypeList();
        setAddOnPaymentTypeList();
        setJenisAngsuran();
        setEditTextData();
        //setTACPList();
        setCarType();
        etNamaPaket.setText(SelectedData.SelectedPackage);
        //setTenor();
        if(userSession.getUser().getType().equals("so")){
            etInsuranceName.setText(dealer.getPVInscoName());
        }else{
            etInsuranceName.setText(SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getPv_insco().getName());
        }
        //etInsuranceName.setText(CoverageInsuranceController.getAllCoverageInsurance().get(0).getPVInsco());
        //Log.d("singo", "allCoverage : "+ JSONProcessor.toJSON(CoverageInsuranceController.getAllCoverageInsurance()));
    }

    /*private void setCarList(){
        try {
            //carMaster = CarModelMaster.getCarModel();

            //cars = CarController.getAllCar();
            cars = CarController.getAllAvailableCarInPackage(SelectedData.CategoryGroupId);
            TextUtility.sortCar(cars);

            for (int i=0; i<cars.size(); i++){
                com.drife.digitaf.ORM.Database.CarModel carModel = cars.get(i);
                String model = carModel.getCarName();
                this.carModel.add(model);
            }

            SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerModel, this.carModel);
            spinnerModel.setSelection(SelectedData.Car);
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"setCarList","Exception",e.getMessage());
        }
    }*/

    private void setCarList(){

        List<com.drife.digitaf.ORM.Database.CarModel> models = new ArrayList<>();

        List<PackageRule> nonPackageGrouping = new ArrayList<>();
        nonPackageGrouping.clear();
        nonPackageGrouping = PackageRuleController.getNonPackage();

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

        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
        //SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerModel, this.carModel);
        //spinnerModel.setSelection(SelectedData.Car);
    }

    private void setCarList(String brandCode){

        carModel.clear();
        cars.clear();
        List<com.drife.digitaf.ORM.Database.CarModel> models = new ArrayList<>();

        List<PackageRule> nonPackageGrouping = new ArrayList<>();
        nonPackageGrouping.clear();
        nonPackageGrouping = PackageRuleController.getNonPackage();

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
            String brand = model.getBrandCode();
            if(brand.equals(brandCode)){
                if(!carModel.contains(car)){
                    carModel.add(car);
                    cars.add(model);
                }
            }
        }

        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
        //SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerModel, this.carModel);
        //spinnerModel.setSelection(SelectedData.Car);
    }

    /*private void setTenorList(){
        tenorMaster = TenorMaster.getTenor();

        for (int i=0; i<tenorMaster.size(); i++){
            String ten = tenorMaster.get(i).getName();
            this.tenorList.add(ten);
        }
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerTenor, this.tenorList);
        spinnerTenor.setSelection(SelectedData.Tenor);
    }*/

    /*private void setCoverageTypeList(){
        coverageType.add(allrisk);
        coverageType.add(tlo);
        //coverageType.add("COMBINATION");
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerCoverageType, this.coverageType);
        spinnerCoverageType.setSelection(SelectedData.CoverageType);
    }*/

    private void setCoverageTypeList(){
        coverageType.clear();
        List<String> covType = new ArrayList<>();
        if(packageRules != null && packageRules.size() != 0){
            for (int i=0; i<packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);
                LogUtility.logging(TAG,LogUtility.infoLog,"setCoverageTypeList","packageRule",JSONProcessor.toJSON(packageRules));
                LogUtility.logging(TAG,LogUtility.infoLog,"setCoverageTypeList","SelectedData.JenisAngsuran",SelectedData.JenisAngsuran);
                LogUtility.logging(TAG,LogUtility.infoLog,"setCoverageTypeList","packageRule.getTAVPCoverage()",packageRule.getTAVPCoverage());
                LogUtility.logging(TAG,LogUtility.infoLog,"setCoverageTypeList","tenor 1",packageRule.getTenor());
                LogUtility.logging(TAG,LogUtility.infoLog,"setCoverageTypeList","tenor 2",spinnerTenor.getSelectedItem().toString());
                if(packageRule.getTenor().equals(spinnerTenor.getSelectedItem().toString()) &&
                        packageRule.getInstallmentType().equals(SelectedData.JenisAngsuran)){
                    //String jenis = packageRule.getTAVPCoverage().toUpperCase();
                    String jenis = "";
                    if(packageRule.getTAVPCoverageCode() == null){
                        jenis = "ALLL";
                    }else{
                        jenis = packageRule.getTAVPCoverageCode().toUpperCase().toUpperCase();
                    }

                    if(!jenis.equals("ALLL")){
                        if(jenis.equals("ALL")){
                            covType.add(comprehensive);
                        }else{
                            covType.add(packageRule.getTAVPCoverage().toUpperCase());
                        }
                    }else{
                        covType.add(comprehensive);
                        //covType.add(tlo);
                        covType.add(combine);
                    }
                }
            }
            for (int i=0; i<covType.size(); i++){
                String val = covType.get(i);
                if(!coverageType.contains(val)){
                    coverageType.add(val);
                }
            }
        }
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerCoverageType, this.coverageType);
        int selectedCoverage = SelectedData.CoverageType;

        if (draft.getCoverage_type() !=null) {
            if (draft.getCoverage_type().equals(comprehensive)) {
                spinnerCoverageType.setSelection(0);
            } else {
                spinnerCoverageType.setSelection(1);
            }
        }else {
            spinnerCoverageType.setSelection(0);
        }

    }

    /*private void setPaymentTypeList(){
        paymentType.add(prepaid);
        paymentType.add(onloan);
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerPaymentType, this.paymentType);
    }*/

    private void setPaymentTypeList(){
        paymentType.clear();
        List<String> payType = new ArrayList<>();
        //Log.d("singo","packageRules : "+JSONProcessor.toJSON(packageRules));
        LogUtility.logging(TAG,LogUtility.infoLog,"setPaymentTypeList","packageRules",JSONProcessor.toJSON(packageRules));
        if(packageRules != null && packageRules.size() != 0){
            for (int i=0; i<packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);

                if(packageRule.getTenor().equals(spinnerTenor.getSelectedItem().toString()) &&
                        packageRule.getInstallmentType().equals(SelectedData.JenisAngsuran)){
                    String jenis = packageRule.getTAVPPayment().toUpperCase();

                    /*if(!jenis.equals("ALL")){
                        payType.add(packageRule.getTAVPPayment().toUpperCase());
                    }else{
                        payType.add(prepaid);
                        payType.add(onloan);
                    }*/

                    Log.d("singo","jenis : "+jenis);
                    if(jenis.equals("ALL")){
                        payType.add(prepaid);
                        payType.add(onloan);
                    }else{
                        payType.add(packageRule.getTAVPPayment().toUpperCase());
                    }
                }
            }

            for (int i=0; i<payType.size(); i++){
                String val = payType.get(i);
                if(!paymentType.contains(val)){
                    paymentType.add(val);
                }
            }
        }
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerPaymentType, this.paymentType);

        if(isSimulasiPaket){
            if (paymentType.size()>1){
                if (inquiryParam.getInsurance().getInsurance_list().get(0).getPayment_type().equals("FO")){
                    spinnerPaymentType.setSelection(1);
                }else {
                    spinnerPaymentType.setSelection(0);
                }
            }
        }else if(isSimulasiBudget){
            if (paymentType.size()>1){
                if (inquiryParam.getInsurance().getInsurance_list().get(0).getPayment_type().equals("FO")){
                    spinnerPaymentType.setSelection(1);
                }else {
                    spinnerPaymentType.setSelection(0);
                }
            }
        }else{
            if (paymentType.size()>1){
                if (inquiryParam.getInsurance().getInsurance_list().get(0).getPayment_type().equals("FO")){
                    spinnerPaymentType.setSelection(1);
                }else {
                    spinnerPaymentType.setSelection(0);
                }
            }
        }
    }

    /*private void setProvisionTypeList(){
        provisionType.add(prepaid);
        provisionType.add(onloan);
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerProvisionType, this.provisionType);
    }*/

    private void setProvisionTypeList(){
        provisionType.clear();
        List<String> provType = new ArrayList<>();
        if(packageRules != null && packageRules.size() != 0){
            for (int i=0; i<packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);
                if(packageRule.getTenor().equals(spinnerTenor.getSelectedItem().toString()) &&
                        packageRule.getInstallmentType().equals(SelectedData.JenisAngsuran)){
                    String jenis = packageRule.getProvisionPayment().toUpperCase();
                    Log.d("singo", "jenis prov : "+jenis);
                    if(!jenis.equals("ALL")){
                        provType.add(packageRule.getProvisionPayment().toUpperCase());
                    }else{
                        provType.add(prepaid);
                        provType.add(onloan);
                    }
                }
            }

            for (int i=0; i<provType.size(); i++){
                String val = provType.get(i);
                if(!provisionType.contains(val)){
                    provisionType.add(val);
                }
            }
            if (inquiryParam.getProvision_payment_type().equals("PREPAID")){
                SpinnerUtility.setSpinnerItemDraft(getApplicationContext(), spinnerProvisionType, this.provisionType, prepaid);
            }else {
                SpinnerUtility.setSpinnerItemDraft(getApplicationContext(), spinnerProvisionType, this.provisionType, onloan);
            }
        }
    }

    private void setAddOnPaymentTypeList(){
        addOnPaymentType.add(prepaid);
        addOnPaymentType.add(onloan);
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerAddOnPaymentType, this.addOnPaymentType);
        if (draft.getPayment_type_service_package() !=null) {
            if (draft.getPayment_type_service_package().equals(prepaid)) {
                spinnerAddOnPaymentType.setSelection(0);
            } else if (draft.getPayment_type_service_package().equals(onloan)) {
                spinnerAddOnPaymentType.setSelection(1);
            }
        }
    }

    private void setJenisAngsuran(){
        String jenis = SelectedData.JenisAngsuran;spinnerCoverageType.setSelection(0);
        if(jenis.equals(addm)){
            rbADDM.setChecked(true);
        }else if(jenis.equals(addb)){
            rbADDB.setChecked(true);
        }
        if(isSimulasiPaket){
//            rbADDB.setEnabled(false);
//            rbADDM.setEnabled(false);
            rbADDB.setClickable(false);
            rbADDM.setClickable(false);
        }
    }

    private void setEditTextData(){
        String dp = String.valueOf(SelectedData.Dp);
        String dppercentage = String.valueOf(SelectedData.DpPercentage);
        String otr = String.valueOf(SelectedData.Otr);

        //selectedDpPercentage = Double.parseDouble(dppercentage);

        if(isSimulasiBudget){
            etOtr.setText(SelectedData.Otr);
            //etDp.setText(dp);
            //Log.d("singo", "roundup : "+SelectedData.DpPercentage);
            double dpPercenRoundUp = KalkulatorUtility.roundUpDecimal(Double.parseDouble(dppercentage));
            double dpPercen = selectedDpPercentage;
            //etDpPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(Double.parseDouble(SelectedData.DpPercentage)));

            //dp = String.valueOf(KalkulatorUtility.countAmountLong(Long.parseLong(SelectedData.Otr),dpPercen));
            etDp.setText(dp);
            //etDpPercentage.setText(dpPercen+"");
            etDpPercentage.setText(dpPercenRoundUp+"");
            etRate.setText(SelectedData.Rate);
        }else{
            long amount = Long.parseLong(dp);
            double percentage = KalkulatorUtility.countPercentageLong(Long.parseLong(otr),amount);
            if(percentage< 0.01){
                percentage = 0;
            }

            etDpPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");
            etOtr.setText(SelectedData.Otr);
            //double dpPercen = KalkulatorUtility.roundUpDecimal(Double.parseDouble(dppercentage));
            //etDpPercentage.setText(KalkulatorUtility.setRoundDecimalPlace(Double.parseDouble(SelectedData.DpPercentage)));

            //dp = String.valueOf(KalkulatorUtility.countAmountLong(Long.parseLong(SelectedData.Otr),dpPercen));
            etDp.setText(dp);
            //etDpPercentage.setText(dpPercen+"");
            etRate.setText(SelectedData.Rate);
        }

        if(userSession.getUser().getType().equals(so)){
            etPCLName.setText(dealer.getPCLInscoName());
        }else{
            etPCLName.setText(userSession.getUser().getDealer().getPcl_insco().getName());
        }
    }

    /*private void setTACPList(){
        typeTACP.add(prepaid);
        typeTACP.add(onloan);
        typeTACP.add("NO");
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerTACP, this.typeTACP);
    }*/

    private void setTACPList(){
        typeTACP.clear();
        List<String> tacp = new ArrayList<>();
        PackageRule rule;
        if(packageRules != null && packageRules.size() != 0){
            for (int i=0; i<packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);
                if(packageRule.getTenor().equals(spinnerTenor.getSelectedItem().toString()) &&
                        packageRule.getInstallmentType().equals(SelectedData.JenisAngsuran)){
                    String jenis = packageRule.getPCLPayment();
                    String isPCL = packageRule.getPCL();

                    Log.d("isPCL", isPCL);

                    Log.d("TACP", packageRule.getTenor());
                    Log.d("TACP", spinnerTenor.getSelectedItem().toString());
                    Log.d("TACP", packageRule.getInstallmentType());
                    Log.d("TACP", SelectedData.JenisAngsuran);

                    String mn = packageRule.getMinDp();
                    String mx = packageRule.getMaxDp();
                    double min = 0.0;
                    double max = 0.0;

                    if(mn != null && mx != null){
                        min = Double.parseDouble(packageRule.getMinDp());
                        max = Double.parseDouble(packageRule.getMaxDp());
                    }

                    Log.d("jenis", packageRule.getPCLPayment());

                    if(selectedDpPercentage >= min && selectedDpPercentage <= max){
                        if(jenis != null && !jenis.equals("")){
                            jenis = jenis.toUpperCase();
                        }

                        if(isPCL != null && !isPCL.equals("")){
                            isPCL = isPCL.toUpperCase();
                        }

                        if(jenis.equals("ALL")){
                            tacp.add(prepaid);
                            tacp.add(onloan);
                            //tacp.add("NO");
                        }else{
                            tacp.add(packageRule.getPCLPayment().toUpperCase());
                        }

                        Log.d("singo", "isPcl : "+isPCL);
                        Log.d("singo", "selectedDpPercentage : "+selectedDpPercentage);
                        if(isPCL.equals("N")){
                            tacp.clear();
                            tacp.add("NO");
                        }else if(isPCL.equals("ALL")){
                            tacp.add("NO");
                        }
                    }
                }
            }

            for (int i=0; i<tacp.size(); i++){
                String val = tacp.get(i);
                if(!typeTACP.contains(val)){
                    typeTACP.add(val);
                }
            }

            typeTACP.add(prepaid);
            typeTACP.add(onloan);
            typeTACP.add("NO");

            Log.d("typeTACP", String.valueOf(typeTACP.size()));

            SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerTACP, this.typeTACP);
            if(isSimulasiPaket){
                if (typeTACP.size()>1){
                    if (inquiryParam.getLife_insurance() !=null){
                        if (inquiryParam.getLife_insurance().getPayment_type().equals("FO")){
                            spinnerTACP.setSelection(1);
                        }else {
                            spinnerTACP.setSelection(0);
                        }
                    }else {
                        spinnerTACP.setSelection(2);
                    }
                }
            }else if(isSimulasiBudget){
                if (typeTACP.size()>1){
//                    spinnerTACP.setSelection(2);
                    if (inquiryParam.getLife_insurance() !=null){
                        if (inquiryParam.getLife_insurance().getPayment_type().equals("FO")){
                            spinnerTACP.setSelection(1);
                        }else {
                            spinnerTACP.setSelection(0);
                        }
                    }else {
                        spinnerTACP.setSelection(2);
                    }
                }
            }else{
                if (typeTACP.size()>1){
                    if (inquiryParam.getLife_insurance() !=null){
                        if (inquiryParam.getLife_insurance().getPayment_type().equals("FO")){
                            spinnerTACP.setSelection(1);
                        }else {
                            spinnerTACP.setSelection(0);
                        }
                    }else {
                        spinnerTACP.setSelection(2);
                    }
                }
            }
        }
    }

    /*reset value*/
    private void reset(){
        try {
            otrDepresiasis.clear();
            premiAmountPercentage.clear();
            premiAmount.clear();
            premiAmountSumUp.clear();
            pokokHutang.clear();

            pokokHutangPrepaid.clear();

            PHAwal = 0;
            premiAmountSumUpCurrentValue = 0;

            asuransiTDP.clear();
            asuransiCicil.clear();
            premiAmounts.clear();
            asuransiTDPSumUp.clear();
            asuransiCicilSumUp.clear();
            biayaAsuransiKendaraan.clear();
            pokokHutangNonPCL = 0;
            installmentNonPCL = 0;
            pokokHutangWithPCL = 0;
            installmentWithPCL = 0;
            addOn = 0;
            pokokHutangWithAddOn = 0;
            installmentWithAddOn = 0;
            pclPrepaid = 0;
            provision = 0;
            paketServis = 0;
            tdpAkhir = 0;
            totalHutangFinal = 0;
            totalHutangAfterDiskon = 0;
            rateBungaAfterDiskon = 0;
            bungaADDM.clear();
            bungaADDB.clear();
            biayaAdmin.clear();
            biayaAdminADDM.clear();
            packageRulesADDM.clear();
            packageRulesADDB.clear();
            //tenorMaster.clear();
            inputOtr = 0;
            //KalkulatorUtility.resetGlobalData();
        }catch (Exception e){

        }
    }

    private void setMaster(){
        //setTenor();
        //depresiasis = DepresiasiMaster.getDepresiasi();
        depresiasis = DepresiasiMaster.getDepresiasi(tenorMaster);
        /*coverageInsurancesTLO = CoverageInsuranceController.getTLO();
        coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre();*/
        if(userSession.getUser().getType().equals(so)){
            coverageInsurancesTLO = CoverageInsuranceController.getTLO(dealer.getPVInscoCode(),dealer.getInsuranceAreaCode(),dealer.getDealerCode());
            coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre(dealer.getPVInscoCode(),dealer.getInsuranceAreaCode(),dealer.getDealerCode());
        }else{
            coverageInsurancesTLO = CoverageInsuranceController.getTLO();
            coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre();
        }

        //PCL = PCLMaster.getPCL();
        /*String pclStr = SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
        if(!pclStr.equals("")){
            PCL = Double.parseDouble(pclStr);
        }*/

        if(userSession.getUser().getType().equals("so")){
            PCL = Double.parseDouble(dealer.getPCLPremi());
        }else if(userSession.getUser().getType().equals("sales")){
            String pclStr = SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
            if(!pclStr.equals("")){
                PCL = Double.parseDouble(pclStr);
            }
        }else{
            String pclStr = SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
            if(!pclStr.equals("")){
                PCL = Double.parseDouble(pclStr);
            }
        }

        Log.d("singo","PCL Rate : "+PCL);

        //biayaAdmin = BiayaTambahanMaster.getBiayaAdmin();
        polis = BiayaTambahanMaster.getPolis();
    }

    private long countPHAwal(long otrAwal, long dp){
        long ph = otrAwal - dp;
        PHAwal = ph;
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal","otrAwal",otrAwal+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal","dp",dp+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal","ph",ph+"");
        return ph;
    }

    private void countKredit(long otr, String paymentAsuransi, int indexTenor, String pclType, boolean hasPCL, String provisionType,
                             int provisionAmount, String paketServiceType, int paketServiceAmount, String typeAngsuran, long diskon,
                             String coverageAsuransi,double provMin, double provMax, double rateBunga){
        /*hitung otr setelah depresiasi*/
        otrDepresiasis = KalkulatorUtility.getOTRAfterDepresiasi(otr,depresiasis);
        ////////////////////////////////////////////////////////////////////////////////

        if(otrDepresiasis != null){
            /*lookup persentasi asuransi premi*/
            getAsuransiPremiPercentage(otrDepresiasis);
            ////////////////////////////////////////////////

            /*hitung premi amount
             *premi amount = (otr setelah depresiasi x persentase premi amount)+tjh3*/
            getPremiAmount(otrDepresiasis,premiAmountPercentage,tjh3,coverageAsuransi);
            ////////////////////////////////////////////////////////////////////

            /*grouping biaya asuransi untuk jenis prepaid atau onloan*/
            groupingAsuransi();
            /////////////////////////////////////////////////

            /*hitung sum up dari biaya asuransi*/
            countAsuransiSumUp();
            /////////////////////////////////////

            /*hitung installment non pcl*/
            countInstallmentNonPCL(paymentAsuransi,indexTenor,rateBunga, diskon);

            /*hitung add on*/
            countAddOn(hasPCL,provisionType,provisionAmount,paketServiceType,paketServiceAmount,indexTenor,provMin,provMax,rateBunga);

            /*hitung installment dengan pcl*/
            countInstallmentWithPCL(indexTenor,pclType,rateBunga);

            if(diskon>0){
                countDiscount(diskon, indexTenor);
                /*hitung installment non pcl*/
                countInstallmentNonPCL(paymentAsuransi,indexTenor,rateBunga, diskon);

                /*hitung add on*/
                countAddOn(hasPCL,provisionType,provisionAmount,paketServiceType,paketServiceAmount,indexTenor,provMin,provMax,rateBunga);

                /*hitung installment dengan pcl*/
                countInstallmentWithPCL(indexTenor,pclType,rateBunga);
            }

            /*hitung add on*/
            //countAddOn(hasPCL,provisionType,provisionAmount,paketServiceType,paketServiceAmount,indexTenor,provMin,provMax,rateBunga);

            /*hitung diskon*/
            //countDiscount(diskon, indexTenor);

            /*hitung tdp*/
            countTDP(indexTenor,typeAngsuran);

            /*getBungaAmount(pokokHutang,bungaADDB,tenorMaster);
            getTotalHutangRounding(installment1,tenorMaster,PCL,pokokHutang,bungaADDB);
            getTDP_ADDB();

            getBungaAmountPrepaid(pokokHutangPrepaid,bungaADDB,tenorMaster);
            getTotalHutangRoundingPrepaid(installment1Prepaid,tenorMaster,PCL,pokokHutangPrepaid,bungaADDB);
            getTDP_ADDM();*/
        }
    }

    private void getAsuransiPremiPercentage(List<OTRDepresiasi> otrDepresiasis){
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
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","firstYearCoverage",JSONProcessor.toJSON(firstYearCoverage));

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

        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","premiAmountPercentage : rate",JSONProcessor.toJSON(premiAmountPercentage));
    }

    private void getPremiAmount(List<OTRDepresiasi> otrDepresiasis, List<Double> premiAmountPercentage, int tjh3, String coverageType){
        LogUtility.logging(TAG,LogUtility.infoLog,"Calculate","getPremiAmount : otr after depresiasi",JSONProcessor.toJSON(otrDepresiasis));
        //LogUtility.logging(TAG,LogUtility.infoLog,"Calculate","selling rate",JSONProcessor.toJSON(premiAmountPercentage));
        LogUtility.logging(TAG,LogUtility.infoLog,"Calculate","getPremiAmount : tjh3",tjh3+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"Calculate","getPremiAmount : coverage type",coverageType);
        premiAmount = new ArrayList<>();
        premiAmountSumUp = new ArrayList<>();
        pokokHutang = new ArrayList<>();
        pokokHutangPrepaid = new ArrayList<>();
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            Double percentage = premiAmountPercentage.get(i);
            long otr = otrDepresiasi.getOTR();
            long amount = Math.round((float) otr*percentage/100);
            //long amount = new Double((float) otr*percentage/100).longValue();
            long amt = 0;

            if(coverageType.equals(combine)){
                if(i==0){
                    amt = amount+tjh3;
                }else{
                    amt = amount;
                }
            }else{
                if(coverageType.equals(comprehensive)){
                    //amount = new Double (((otr*percentage)/100)).intValue()+tjh3;
                    amt = amount+tjh3;
                }else{
                    //amount = new Double (((otr*percentage)/100)).intValue();
                    amt = amount;
                }
            }

            LogUtility.logging(TAG,LogUtility.infoLog,"singo","otr",otr+"");
            LogUtility.logging(TAG,LogUtility.infoLog,"singo","amt",amt+"");

            premiAmount.add(amt);
            if(spinnerPaymentType.getSelectedItem().toString().equals(prepaid)){
                PremiAmount premiAmount = new PremiAmount(amt,prepaid);
                premiAmounts.add(premiAmount);
            }else if(spinnerPaymentType.getSelectedItem().toString().equals(onloan)){
                PremiAmount premiAmount = new PremiAmount(amt,onloan);
                premiAmounts.add(premiAmount);
            }

            /*insert data to premiAmountSum to handle asuransi cicil or TDP_ADDB*/
            premiAmountSumUpCurrentValue = premiAmountSumUpCurrentValue+amt;
            premiAmountSumUp.add(premiAmountSumUpCurrentValue);
            /*set ph */
            processOnLoanData(PHAwal,premiAmountSumUpCurrentValue);
            processPrepaidData(PHAwal);
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","getPremiAmount : premiAmount",JSONProcessor.toJSON(premiAmount));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","getPremiAmount : premiAmountSumUp",JSONProcessor.toJSON(premiAmountSumUp));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","getPremiAmount : pokokHutang",JSONProcessor.toJSON(pokokHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","getPremiAmount : pokokHutangPrepaid",JSONProcessor.toJSON(pokokHutangPrepaid));
    }

    private void groupingAsuransi(){
        for (int i=0; i<premiAmounts.size(); i++){
            PremiAmount premiAmount = premiAmounts.get(i);
            if(premiAmount.getType().equals(prepaid)){
                asuransiTDP.add(premiAmount.getAmount());
                asuransiCicil.add(0l);
            }else if(premiAmount.getType().equals(onloan)){
                asuransiTDP.add(0l);
                asuransiCicil.add(premiAmount.getAmount());
            }
        }
        LogUtility.logging(TAG,LogUtility.debugLog,"groupingAsuransi","asuransiTDP",new Gson().toJson(asuransiTDP));
        LogUtility.logging(TAG,LogUtility.debugLog,"groupingAsuransi","asuransiCicil",new Gson().toJson(asuransiCicil));
    }

    private void countAsuransiSumUp(){
        LogUtility.logging(TAG,LogUtility.infoLog,"countAsuransiSumUp","tenorMaster",JSONProcessor.toJSON(tenorMaster));
        long sumUpTdp = 0;
        long sumUpCicil = 0;
        for (int i=0; i<tenorMaster.size(); i++){
            long asTDP = asuransiTDP.get(i);
            sumUpTdp = sumUpTdp+asTDP;
            asuransiTDPSumUp.add(sumUpTdp);

            long asCicil = asuransiCicil.get(i);
            sumUpCicil = sumUpCicil+asCicil;
            asuransiCicilSumUp.add(sumUpCicil);
        }
        LogUtility.logging(TAG,LogUtility.debugLog,"countAsuransiSumUp","asuransiTDPSumUp",new Gson().toJson(asuransiTDPSumUp));
        LogUtility.logging(TAG,LogUtility.debugLog,"countAsuransiSumUp","asuransiCicilSumUp",new Gson().toJson(asuransiCicilSumUp));
    }

    private void countInstallmentNonPCL(String paymentAsuransi, int tenorIndex, double rateBunga, long diskon){
        biayaAsuransiKendaraan = asuransiCicilSumUp;
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","countInstallmentNonPCL : biayaAsuransiKendaraan",JSONProcessor.toJSON(biayaAsuransiKendaraan));
        /*if(paymentAsuransi.equals(prepaid)){
            biayaAsuransiKendaraan = asuransiTDPSumUp;
        }else if(paymentAsuransi.equals(onloan)){
            biayaAsuransiKendaraan = asuransiCicilSumUp;
        }*/

        /*pokok hutang*/
        long biayaAsuransi = biayaAsuransiKendaraan.get(tenorIndex);
        long pokokHutang = PHAwal+biayaAsuransi;
        pokokHutangNonPCL = pokokHutang;

        /*bungaADDB amount*/
        double bunga = 0;
        /*if(rbADDB.isChecked()){
            bunga = this.bungaADDB.get(tenorIndex);
        }else if(rbADDM.isChecked()){
            bunga = this.bungaADDM.get(tenorIndex);
        }*/

        //countDiscount(diskon, tenorIndex);
        //bunga = rateBunga;
        bunga = RATE;
        //double bungaADDB = this.bungaADDB.get(tenorIndex);
        int tenorValue = Integer.parseInt(tenorMaster.get(tenorIndex).getName());
        int ten = tenorValue/12;
        long amount = new Double((float)pokokHutangNonPCL*bunga/100).longValue();
        //int bungaAmount = amount*ten;
        long bungaAmount = 0;

        /*String dis = CurrencyFormat.formatNumber(etDiskon.getText().toString());
        double subsidyRate = 0.0;
        if(!dis.equals("")){
            subsidyRate = Double.parseDouble(dis);
        }
        double bungaAmt = (amount*ten)-subsidyRate;*/
        double bungaAmt = ((float)amount*ten);
        bungaAmount = new Double(bungaAmt).longValue();

        /*total hutang setelah di tambah bungaADDB*/
        totalHutangNonPCL = pokokHutangNonPCL+bungaAmount;
        long installmentNonPCL = KalkulatorUtility.roundUp(totalHutangNonPCL/tenorValue);
        this.installmentNonPCL = installmentNonPCL;

        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentNonPCL : rateBunga non pcl",bunga+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentNonPCL : amount non pcl",amount+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentNonPCL : bungaAmount non pcl",bungaAmount+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentNonPCL : totalHutang non pcl",totalHutangNonPCL+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentNonPCL : pokokHutangNonPCL",pokokHutangNonPCL+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentNonPCL : installmentNonPCL",installmentNonPCL+"");
    }

    private void countInstallmentWithPCL(int indexTenor, String type, double rateBunga){
        //double pcl = PCL.get(indexTenor);
        double pcl = this.PCL;
        if(type.equals(no)){
            pcl = 0.0;
        }
        int tenorValue = Integer.parseInt(tenorMaster.get(indexTenor).getName());

        /*total hutang rounding*/
        //long totalHutangRounding = installmentNonPCL*tenorValue;
        long totalHutangRounding = installmentWithAddOn*tenorValue;
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : totalHutangRounding",totalHutangRounding+"");

        /*count premi amount*/
        long premiAmount = new Double((float)totalHutangRounding*pcl/100).longValue();
        SelectedData.pclPremiAmount = premiAmount+"";

        /*count pokok hutang*/
        //pokokHutangWithPCL = pokokHutangNonPCL+premiAmount;
        if (type.equals(onloan)){
            //pokokHutangWithPCL = pokokHutangNonPCL+premiAmount;
            pokokHutangWithPCL = pokokHutangWithAddOn+premiAmount;
        }else if(type.equals(prepaid)){
            //pokokHutangWithPCL = pokokHutangNonPCL;
            pokokHutangWithPCL = pokokHutangWithAddOn;
            pclPrepaid = premiAmount;
        }else{
            pokokHutangWithPCL = pokokHutangWithAddOn;
        }

        /*pokok hutang dengan PCL*/
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : pokokHutangWithPCL",pokokHutangWithPCL+"");

        /*count bungaADDB amount*/
        double bunga = 0;
        /*if(rbADDB.isChecked()){
            bunga = this.bungaADDB.get(indexTenor);
        }else if(rbADDM.isChecked()){
            bunga = this.bungaADDM.get(indexTenor);
        }*/
        //bunga = rateBunga;
        bunga = RATE;
        int ten = tenorValue/12;
        long amount = new Double((float)pokokHutangWithPCL*bunga/100).longValue();
        long bungaAmount = amount*ten;
        //int bungaAmount = 0;

        /*String dis = CurrencyFormat.formatNumber(etDiskon.getText().toString());
        double subsidyRate = 0.0;
        if(!dis.equals("")){
            subsidyRate = Double.parseDouble(dis);
        }*/
        //double subsidyRate = Double.parseDouble(dis);
        //double bungaAmt = bungaAmount-subsidyRate;
        long bungaAmt = bungaAmount;
        bungaAmount = new Double(bungaAmt).longValue();

        /*count total hutang dengan PCL*/
        long totalHutang = pokokHutangWithPCL+bungaAmount;

        /*count installment with PCL*/
        /*if(type.equals(no)){
            installmentWithPCL = installmentNonPCL;
            pokokHutangWithPCL = pokokHutangNonPCL;
        }else{
            installmentWithPCL = KalkulatorUtility.roundUp(totalHutang/tenorValue);
        }*/

        totalHutangFinal = totalHutang;
        setLTVParameters();

        installmentWithPCL = KalkulatorUtility.roundUp(totalHutang/tenorValue);
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : rateBunga",bunga+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : bungaAmount",bungaAmount+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : installmentWithPCL",installmentWithPCL+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : premiPCL",premiAmount+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : PHPCL",pokokHutangWithPCL+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countInstallmentWithPCL : totalHutang Setelah PCL",totalHutang+"");
    }

    private void countAddOn(boolean hasPCL, String provisionType, int provisionAmount, String paketServiceType, int paketServiceAmount, int indexTenor,
                            double provMin, double provMax, double rateBunga){
        //int pokokHutang = 0;
        int tenorValue = Integer.parseInt(tenorMaster.get(indexTenor).getName());

        if(hasPCL){
            //pokokHutangWithAddOn = pokokHutangWithPCL;
            pokokHutangWithAddOn = pokokHutangNonPCL;
        }else{
            pokokHutangWithAddOn = pokokHutangNonPCL;
        }

        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : pokok hutang awal",pokokHutangWithAddOn+"");

        /*hitung provision*/
        if(cbProvision.isChecked()){
            String provType = spinnerProvisionType.getSelectedItem().toString();
            LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : provisionType",provType+"");

            double provRate = 0;
            long provNominal = 0;
            if(!etProvisionAmount.getText().toString().equals("")){
                String prov = CurrencyFormat.formatNumber(etProvisionAmount.getText().toString());
                provNominal = Long.parseLong(prov);
                provRate = KalkulatorUtility.countPercentage(pokokHutangWithAddOn,provNominal);
            }else if(!etProvisionPercentage.getText().toString().equals("")){
                provRate = Double.parseDouble(etProvisionPercentage.getText().toString());
                long provAmount = new Double((float) pokokHutangWithAddOn*provRate/100).longValue();
                //provNominal = KalkulatorUtility.countAmountInt(pokokHutangWithAddOn,provRate);
                provNominal = provAmount;
            }

            LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : provNominal",provNominal+"");
            LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : provRate",provRate+"");

            if(validasiProvisi(provRate,provMin,provMax)){
                provisiStatus = true;
                if(provType != null){
                    if(provType.equals(onloan)){
                        //Log.d("singo", "pokokHutangWithAddOn : "+pokokHutangWithAddOn+" : "+provisionAmount);
                        pokokHutangWithAddOn = pokokHutangWithAddOn+provNominal;
                        provision = provNominal;
                        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","provisionOnloan",provNominal+"");
                    }else if(provType.equals(prepaid)){
                        provision = provNominal;
                    }
                }
            }else{
                provisiStatus = false;
            }
        }


        /*hitung paket servis*/
        /*if(cbServicePackage.isChecked()){
            String packageAmount = etPackageAmount.getText().toString();
            if(packageAmount != null && !packageAmount.equals("")){
                LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : paketServiceAmount",paketServiceAmount+"");
                if(paketServiceType.equals(onloan)){
                    pokokHutangWithAddOn = pokokHutangWithAddOn+paketServiceAmount;
                }else if(paketServiceType.equals(prepaid)){
                    paketServis = paketServiceAmount;
                }
            }
        }*/

        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : pokokHutangWithAddOn",pokokHutangWithAddOn+"");

        /*count bungaADDB amount*/
        //double bungaADDB = this.bungaADDB.get(indexTenor);
        double bunga = 0;
        /*if(rbADDB.isChecked()){
            bunga = this.bungaADDB.get(indexTenor);
        }else if(rbADDM.isChecked()){
            bunga = this.bungaADDM.get(indexTenor);
        }*/

        //bunga = rateBunga;
        bunga = RATE;
        int ten = tenorValue/12;

        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : rateBunga",bunga+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : ten",ten+"");

        long amount = new Double((float)pokokHutangWithAddOn*bunga/100).longValue();
        long bungaAmount = amount*ten;

        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countAddOn : bungaAmount",bungaAmount+"");

        /*count total hutang*/
        long totalHutang = pokokHutangWithAddOn+bungaAmount;
        //totalHutangFinal = totalHutang;
        long totalHutangWithAddOn = totalHutang;
        //setLTVParameters();

        /*count installment with AddOn*/
        installmentWithAddOn = KalkulatorUtility.roundUp(totalHutangWithAddOn/tenorValue);

        //LogUtility.logging(TAG,LogUtility.debugLog,"countAddOn","pokokHutangWithAddOn",pokokHutangWithAddOn+"");
        //LogUtility.logging(TAG,LogUtility.debugLog,"countAddOn","installmentWithAddOn",installmentWithAddOn+"");
    }

    private void countDiscount(long diskon, int indexTenor){
        String rateInput = etRate.getText().toString();
        double rate = Double.parseDouble(rateInput);

        int tenorValue = Integer.parseInt(tenorMaster.get(indexTenor).getName());
        int ten = tenorValue/12;

        long x = new Double(((float)PHAwal*rate*ten/100)).longValue();
        long totalHutangAwal = x+PHAwal;

        double rateDiscount = ((float)diskon/pokokHutangWithPCL/ten)*100;

        /*count diskon*/
        //totalHutangAfterDiskon = totalHutangFinal-diskon;
        //totalHutangAfterDiskon = totalHutangNonPCL-diskon;
        totalHutangAfterDiskon = totalHutangAwal-diskon;

        //rateBungaAfterDiskon = ((((double) totalHutangAfterDiskon/ (double) pokokHutangWithAddOn)-1)*100)/ten;
        long interest = totalHutangAfterDiskon-PHAwal;
        //double rate = interest/PHAwal;
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countDiscount : interest",interest+"");
        // LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countDiscount : rate",rate+"");

        //rateBungaAfterDiskon = (((totalHutangAfterDiskon/ PHAwal)-1)*100)/ten;

        //double a = (float)totalHutangAfterDiskon/ pokokHutangWithAddOn;
        //double a = (float)totalHutangAfterDiskon/ pokokHutangWithPCL;
        //double a = (float)totalHutangAfterDiskon/ PHAwal;
        double a = (float)totalHutangAfterDiskon/ PHAwal;
        double b = a-1;
        double c = b*100/ten;
        //rateBungaAfterDiskon = c;
        rateBungaAfterDiskon = rate-rateDiscount;
        if(diskon > 0){
            //RATE = Double.parseDouble(KalkulatorUtility.setRoundDecimalPlace(rateBungaAfterDiskon));
            RATE = KalkulatorUtility.roundUpDecimal(rateBungaAfterDiskon);
        }else{
            RATE = rate;
        }

        //rateBungaAfterDiskon = ((totalHutangAfterDiskon/pokokHutangWithAddOn)-1)*100;
        //totalHutangFinal = totalHutangAfterDiskon;
spinnerCoverageType.setSelection(0);
        /*count installment with AddOn*/
        //installmentWithAddOn = KalkulatorUtility.roundUp(totalHutangFinal/tenorValue);
        //installmentWithPCL = KalkulatorUtility.roundUp(totalHutangFinal/tenorValue);

        //double inst = (PCL*pokokHutangNonPCL)+interest;
        //double inst = (PCL*pokokHutangWithAddOn)+interest;
        //long newInstallment = new Double(inst).longValue();

        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countDiscount : totalHutangFinal",totalHutangFinal+"");
        //LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","rateBungaAfterDiskon",rateBungaAfterDiskon+"");
        //LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countDiscount : rateBungaAfterDiskon",rateBungaAfterDiskon+"");
        /*LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","interest",interest+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","rate",rate+"");*/
        //LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countDiscount : newInstallment",newInstallment+"");

        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","totalHutangAwal",totalHutangAwal+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","PHAwal",PHAwal+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","rateBungaAfterDiskon",rateBungaAfterDiskon+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","rateDiscount",rateDiscount+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","diskon",diskon+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","pokokHutangWithPCL diskon",pokokHutangWithPCL+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countDiscount","ten diskon",ten+"");
    }

    private void countTDP(int indexTenor, String tipeAngsuran){
        long asuransi = asuransiTDPSumUp.get(indexTenor);
        int admin = 0;
        if(rbADDB.isChecked()){
            admin = biayaAdmin.get(indexTenor);
        }else{
            admin = biayaAdminADDM.get(indexTenor);
        }
        SelectedData.AdminFee = admin+"";
        //int biayaPolis = polis.get(indexTenor);

        //tdpAkhir = asuransi+this.pclPrepaid+this.provision+this.paketServis+admin+biayaPolis+DP;
        Log.d("singo", "provision amount : "+this.provision);
        long pakService = 0;
        if(spinnerAddOnPaymentType.getSelectedItem().toString().equals(onloan)){
            pakService = 0;
        }else{
            pakService = paketServis;
        }

        if(spinnerProvisionType.getSelectedItem().toString().equals(onloan)){
            tdpAkhir = asuransi+this.pclPrepaid+pakService+admin+DP;
        }else {
            tdpAkhir = asuransi+this.pclPrepaid+this.provision+pakService+admin+DP;
        }

        //tdpAkhir = asuransi+this.pclPrepaid+this.provision+this.paketServis+admin+DP;
        if(tipeAngsuran.equals(addm)){
            //tdpAkhir = tdpAkhir+installmentWithAddOn;
            tdpAkhir = tdpAkhir+installmentWithPCL;
            //tdpAkhir = Math.round(tdpAkhir+installmentWithPCL);
        }
        LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countTDP : tdp",tdpAkhir+"");

        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","asuransi",asuransi+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","pclPrepaid",pclPrepaid+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","provisionPrepaid",provision+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","paketServis",paketServis+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","admin",admin+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","DP",DP+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","installmentWithAddOn",installmentWithAddOn+"");
        LogUtility.logging(TAG,LogUtility.debugLog,"countTDP","installmentWithPCL",installmentWithPCL+"");



    }

    private void processOnLoanData(long phAwal, long premiAmountSumUpCurrentValue){
        //pokokHutang = new ArrayList<>();
        long count = phAwal+premiAmountSumUpCurrentValue;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processOnLoanData","count",count+"");
        pokokHutang.add(count);
    }

    private void processPrepaidData(long phAwal){
        //pokokHutangPrepaid = new ArrayList<>();
        long count = phAwal;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processPrepaidData","count",count+"");
        pokokHutangPrepaid.add(count);
    }

    private void getBungaMobil(final double dpPercentage){
        /*bungaADDB.clear();
        bungaADDM.clear();
        if(selectedCar.equals("Avanza")){
            bungaADDB = RateBungaMaster.rateAvanza();
        }else if(selectedCar.equals("Innova")){
            bungaADDB = RateBungaMaster.rateInnova();
        }*/

        bungaADDM.clear();
        bungaADDB.clear();
        //String code_nonpaket = "RCFCVNONPAKET";
        String code_car = SelectedData.CarCode;
        //String carCode = carMaster.get(selectedCarIndex).getCode();
        String carCode = selectedCarModel.getCarCode();
        String carModel = carCode;
        try {
            //int otr = Integer.parseInt(CurrencyFormat.formatNumber(etOtr.getText().toString()));
            long otr = inputOtr;
            for(int i=0; i<packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);
                double minDp = Double.parseDouble(packageRule.getMinDp());
                double maxDp = Double.parseDouble(packageRule.getMaxDp());
                double adminFee = Double.parseDouble(packageRule.getAdminFee());
                if(packageRule.getInstallmentType().equals(addm)){
                    if(dpPercentage>=minDp && dpPercentage<=maxDp){
                       /* bungaADDM.add(Double.parseDouble(packageRule.getBaseRate()));
                        biayaAdminADDM.add((int) adminFee);*/
                        bungaADDM.add(Double.parseDouble(packageRule.getBaseRate()));
                        packageRulesADDM.add(packageRule);
                        minRateADDM.add(Double.parseDouble(packageRule.getMinRate()));
                        maxRateADDM.add(Double.parseDouble(packageRule.getMaxRate()));
                        baseRateADDM.add(Double.parseDouble(packageRule.getBaseRate()));

                        dpMinADDM.add(Double.parseDouble(packageRule.getMinDp()));
                        dpMaxADDM.add(Double.parseDouble(packageRule.getMaxDp()));
                        dbBaseADDM.add(Double.parseDouble(packageRule.getBaseDp()));
                        double minValue = (Double.parseDouble(packageRule.getMinDp())*(double) otr)/100;
                        double maxValue = (Double.parseDouble(packageRule.getMaxDp())*(double)otr)/100;
                        double baseValue = (Double.parseDouble(packageRule.getBaseDp())*(double)otr)/100;
                        dpMinValueADDM.add(minValue);
                        dpMaxValueADDM.add(maxValue);
                        dpBaseValueADDM.add(baseValue);

                        provisiMinADDM.add(Double.parseDouble(packageRule.getMinProvision()));
                        provisiMaxADDM.add(Double.parseDouble(packageRule.getMaxProvision()));
                        provisiBaseADDM.add(Double.parseDouble(packageRule.getBaseProvision()));
                        double provMinValue = (Double.parseDouble(packageRule.getMinProvision())*(double) otr)/100;
                        double provMaxValue = (Double.parseDouble(packageRule.getMaxProvision())*(double) otr)/100;
                        double provBaseValue = (Double.parseDouble(packageRule.getBaseProvision())*(double) otr)/100;
                        provisiMinValueADDM.add(provMinValue);
                        provisiMaxValueADDM.add(provMaxValue);
                        provisiBaseValueADDM.add(provBaseValue);

                        biayaAdminADDM.add((int) adminFee);
                    }

                }else if(packageRule.getInstallmentType().equals(addb)){
                    if(dpPercentage>=minDp && dpPercentage<=maxDp){
                        /*bungaADDB.add(Double.parseDouble(packageRule.getBaseRate()));
                        biayaAdmin.add((int) adminFee);*/

                        bungaADDB.add(Double.parseDouble(packageRule.getBaseRate()));
                        packageRulesADDB.add(packageRule);
                        minRateADDB.add(Double.parseDouble(packageRule.getMinRate()));
                        maxRateADDB.add(Double.parseDouble(packageRule.getMaxRate()));
                        baseRateADDB.add(Double.parseDouble(packageRule.getBaseRate()));

                        dpMinADDB.add(Double.parseDouble(packageRule.getMinDp()));
                        dpMaxADDB.add(Double.parseDouble(packageRule.getMaxDp()));
                        dbBaseADDB.add(Double.parseDouble(packageRule.getBaseDp()));
                        double minValue = (Double.parseDouble(packageRule.getMinDp())*(double) otr)/100;
                        double maxValue = (Double.parseDouble(packageRule.getMaxDp())*(double)otr)/100;
                        double baseValue = (Double.parseDouble(packageRule.getBaseDp())*(double)otr)/100;
                        dpMinValueADDB.add(minValue);
                        dpMaxValueADDB.add(maxValue);
                        dpBaseValueADDB.add(baseValue);

                        provisiMinADDB.add(Double.parseDouble(packageRule.getMinProvision()));
                        provisiMaxADDB.add(Double.parseDouble(packageRule.getMaxProvision()));
                        provisiBaseADDB.add(Double.parseDouble(packageRule.getBaseProvision()));
                        double provMinValue = (Double.parseDouble(packageRule.getMinProvision())*(double) otr)/100;
                        double provMaxValue = (Double.parseDouble(packageRule.getMaxProvision())*(double) otr)/100;
                        double provBaseValue = (Double.parseDouble(packageRule.getBaseProvision())*(double) otr)/100;
                        provisiMinValueADDB.add(provMinValue);
                        provisiMaxValueADDB.add(provMaxValue);
                        provisiBaseValueADDB.add(provBaseValue);

                        biayaAdmin.add((int) adminFee);
                    }
                }
            }

            LogUtility.logging(TAG,LogUtility.infoLog,"calculate","rate ADDB", JSONProcessor.toJSON(bungaADDB));
            LogUtility.logging(TAG,LogUtility.infoLog,"calculate","rate ADDM", JSONProcessor.toJSON(bungaADDM));
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getBungaMobil","Exception",e.getMessage());
            AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                    .setTitle("DP out of range")
                    .setMessage("DP yang Anda inputkan diluar range. NOTE : Jika Anda menggunakan paket servis, besar DP amount dan percentage akan di hitung ulang")
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }


        /*packageRules = PackageRuleController.getPackageRules(code_nonpaket,
                carModel,null,
                SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(code_nonpaket,
                    null,cars.get(selectedCarIndex).getCategoryGroupId(),
                    SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }*/
    }

    private void saveToDraft(String nama){
        String ModelMobil = spinnerModel.getSelectedItem().toString();
        String TipeMobil = "";
        String Paket = etNamaPaket.getText().toString();
        String HargaOtr = etOtr.getText().toString();
        String Rate = etRate.getText().toString();
        String Dp = etDp.getText().toString();
        String Tenor = spinnerTenor.getSelectedItem().toString();
        String JenisAngsuran = "";
        if(rbADDB.isChecked()){
            JenisAngsuran = addb;
        }else {
            JenisAngsuran = addm;
        }
        String NamaAsuransi = etInsuranceName.getText().toString();
        String AsuransiType = spinnerPaymentType.getSelectedItem().toString();
        String CoverageType = spinnerCoverageType.getSelectedItem().toString();
        String PCLType = spinnerTACP.getSelectedItem().toString();
        String Diskon = CurrencyFormat.formatNumber(etDiskon.getText().toString());
        String ProvisionAmount = etProvisionAmount.getText().toString();
        String ProvisionType = spinnerProvisionType.getSelectedItem().toString();
        String ServicePackageAmount = etPackageAmount.getText().toString();
        String ServicePackageType = spinnerAddOnPaymentType.getSelectedItem().toString();

        DataKredit dataKredit = new DataKredit(ModelMobil,TipeMobil,Paket,HargaOtr,Rate,Dp,Tenor,JenisAngsuran,NamaAsuransi,
                AsuransiType,CoverageType,PCLType,Diskon,ProvisionAmount,ProvisionType,ServicePackageAmount,ServicePackageType);
        String jsonData = new Gson().toJson(dataKredit);

        String id = TextUtility.randomString(10);
    }

    private void setCarType(){
        carTypes.clear();
        carTypesList.clear();
        carTypes = CarController.getAllCarType(selectedCarModel.getCarCode());
        carTypesList.add("- Pilih Tipe Mobil -");
        if(carTypes != null){
            for (int i=0; i<carTypes.size(); i++){
                CarType type = carTypes.get(i);
                String name = type.getName();
                this.carTypesList.add(name);
            }
        }
        //SpinnerUtility.setSpinnerItem(DraftinputItemKredit.this, spinnerType,this.carTypesList);
        atType.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarType>(this, android.R.layout.simple_list_item_1, this.carTypes));
    }

    /*private void setTenor(){
        List<com.drife.digitaf.ORM.Database.Tenor> tenors = TenorController.getAllTenor();
        if(tenors != null){
            for (int i=0; i<tenors.size(); i++){
                com.drife.digitaf.ORM.Database.Tenor tenor = tenors.get(i);
                String name = tenor.getCode();
                this.tenors.add(name);

                Tenor ten = new Tenor(tenor.getIdTenor(),tenor.getCode());
                tenorMaster.add(ten);
            }
        }
        SpinnerUtility.setSpinnerItem(DraftinputItemKredit.this, spinnerTenor,this.tenors);
        spinnerTenor.setSelection(SelectedData.Tenor);
    }*/

    private void setTenor(){
        List<com.drife.digitaf.ORM.Database.Tenor> tenors = TenorController.getAllTenor();
        if(tenors != null){
            for (int i=0; i<tenors.size(); i++){
                com.drife.digitaf.ORM.Database.Tenor tenor = tenors.get(i);
                String name = tenor.getCode();
                this.tenors.add(name);

                Tenor ten = new Tenor(tenor.getIdTenor(),tenor.getCode());
                tenorMaster.add(ten);
            }
        }
        SpinnerUtility.setSpinnerItem(DraftinputItemKredit.this, spinnerTenor,this.tenors);
        spinnerTenor.setSelection(SelectedData.Tenor);
    }

    private void saveTemporaryParams(){
        try {
//            InquiryParam inquiryParam = new InquiryParam();
            UserSession userSession = SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this);
            int tenor = Integer.parseInt(spinnerTenor.getSelectedItem().toString());

            /*flag*/
            String flag = "";
            /*if(userSession.getUser().getNpk_no() != null || !userSession.getUser().getNpk_no().equals("")){
                flag = "SO";
            }else{
                flag = "SD";
            }*/
            if(userSession.getUser().getType().equals("so")){
                flag = "SO";
            }else{
                flag = "SD";
            }
            inquiryParam.setFlag(flag);

            /*merk*/
            String merk = selectedCarModel.getBrandCode();
            inquiryParam.setMerk(merk);
            /*model*/
            String model = selectedCarModel.getCarCode();
            inquiryParam.setModel(model);
            /*type*/
            String type = selectedCarType.getCode();
            inquiryParam.setType(type);
            /*suppl_code*/
            String suppl_code = "";
            if(userSession.getUser().getType().equals("so")){
                suppl_code = dealer.getDealerGroupCode();
            }else{
                suppl_code = userSession.getUser().getDealer().getGroup().getCode();
            }
            inquiryParam.setSuppl_code(suppl_code);
            /*suppl_branch*/
            String suppl_branch = "";
            if(userSession.getUser().getType().equals("so")){
                suppl_branch = dealer.getDealerCode();
            }else{
                suppl_branch = userSession.getUser().getDealer().getCode();
            }
            inquiryParam.setSuppl_branch(suppl_branch);
            /*otr*/
            //String otr = SelectedData.Otr;
            String otr = null;
//            if (spinnerAddOnPaymentType.getSelectedItem().toString().equals(onloan)){
//                int a = Integer.parseInt(inquiryParam.getPackage_service_price());
//                otr = inputOtr - a +"";
//            }else {
                otr = inputOtr+"";
//            }
            inquiryParam.setOtr(otr);
            /*dp_net*/
            //String dp_net = SelectedData.Dp;
            String dp_net = CurrencyFormat.formatNumber(etDp.getText().toString());
            inquiryParam.setDp_nett(dp_net);
            /*tdp*/
            String tdp = tdpAkhir+"";
            inquiryParam.setTdp(tdp);
            /*asset_usage*/
            inquiryParam.setAsset_usage("P");
            /*provision_fee*/
            inquiryParam.setProvision_fee(provision+"");
            /*provision_payment_type*/
            inquiryParam.setProvision_payment_type(spinnerProvisionType.getSelectedItem().toString());
            /*fiducia_fee*/
            inquiryParam.setFiducia_fee("0");
            /*tenor*/
            inquiryParam.setTenor(spinnerTenor.getSelectedItem().toString());
            /*first_installment_type*/
            if(SelectedData.JenisAngsuran.equals(addm)){
                inquiryParam.setFirst_installment_type("AD");
            }else if(SelectedData.JenisAngsuran.equals(addb)){
                inquiryParam.setFirst_installment_type("AR");
            }
            /*effective_rate*/
            //String effective_rate = etRate.getText().toString();
            inquiryParam.setEffective_rate(RATE+"");
            /*installment_amount*/
            //inquiryParam.setInstallment_amount(installmentWithAddOn+"");
            inquiryParam.setInstallment_amount(installmentWithPCL+"");
            /*total_ar*/
            inquiryParam.setTotal_ar(AR+"");
            inquiryParam.setCustomer_name(inquiryParam.getCustomer_name());
            /*subsidy_amt*/
            String diskon = CurrencyFormat.formatNumber(etDiskon.getText().toString());
            if(diskon.equals("")){
                diskon = "0";
            }
            inquiryParam.setSubsidy_amt(diskon);
            /*product_offering_code*/
            inquiryParam.setProduct_offering_code(inquiryParam.getProduct_offering_code());
            /*insurance*/
            Insurance insurance = new Insurance();
            String insurance_name = "";
            if(userSession.getUser().getType().equals("so")){
                insurance_name = dealer.getPVInscoCode();
                SelectedData.pvInscoName = dealer.getPVInscoName();;
            }else{
                insurance_name = userSession.getUser().getDealer().getPv_insco().getCode();
                SelectedData.pvInscoName = userSession.getUser().getDealer().getPv_insco().getName();
            }
            insurance.setInsurance_name(insurance_name);
            insurance.setInsurance_fee("0");
            List<InsuranceList> insuranceLists = new ArrayList<>();
            int years = tenor/12;
            for (int i=0; i<years; i++){
                PremiAmount premiAmount = premiAmounts.get(i);
                InsuranceList insuranceList = new InsuranceList();
                int year = i+1;
                insuranceList.setYear(year+"");
                //insuranceList.setCoverage(spinnerCoverageType.getSelectedItem().toString());
                if(spinnerPaymentType.getSelectedItem().toString().equals(onloan)){
                    insuranceList.setPayment_type("FO");
                }else if(spinnerPaymentType.getSelectedItem().toString().equals(prepaid)){
                    insuranceList.setPayment_type("FP");
                }
                //insuranceList.setMainpremi(premiAmount.getAmount()+"");
                if(spinnerCoverageType.getSelectedItem().toString().equals(combine)){
                    if(i==0){
                        long mainPremi = premiAmount.getAmount()-tjh3;
                        insuranceList.setMainpremi(mainPremi+"");
                        insuranceList.setAdditional_premi(tjh3+"");
                        List<AdditionalCoverage> additionalCoverages = new ArrayList<>();
                        AdditionalCoverage additionalCoverage = new AdditionalCoverage();
                        additionalCoverage.setType("TPL");
                        additionalCoverage.setNilai_premi(tjh3+"");
                        additionalCoverages.add(additionalCoverage);
                        insuranceList.setAditional_coverage(additionalCoverages);
                        insuranceList.setCoverage(allrisk);
                    }else{
                        long mainPremi = premiAmount.getAmount();
                        insuranceList.setMainpremi(mainPremi+"");
                        List<AdditionalCoverage> additionalCoverages = new ArrayList<>();
                        insuranceList.setAditional_coverage(additionalCoverages);
                        insuranceList.setCoverage(tlo);
                    }
                }else {
                    if(spinnerCoverageType.getSelectedItem().toString().equals(comprehensive)){
                        long mainPremi = premiAmount.getAmount()-tjh3;
                        insuranceList.setMainpremi(mainPremi+"");
                        insuranceList.setAdditional_premi(tjh3+"");
                        List<AdditionalCoverage> additionalCoverages = new ArrayList<>();
                        AdditionalCoverage additionalCoverage = new AdditionalCoverage();
                        additionalCoverage.setType("TPL");
                        additionalCoverage.setNilai_premi(tjh3+"");
                        additionalCoverages.add(additionalCoverage);
                        insuranceList.setAditional_coverage(additionalCoverages);
                        insuranceList.setCoverage(allrisk);
                    }else{
                        long mainPremi = premiAmount.getAmount();
                        insuranceList.setMainpremi(mainPremi+"");
                        List<AdditionalCoverage> additionalCoverages = new ArrayList<>();
                        insuranceList.setAditional_coverage(additionalCoverages);
                        insuranceList.setCoverage(tlo);
                    }
                }
                insuranceLists.add(insuranceList);
            }
            insurance.setInsurance_list(insuranceLists);
            inquiryParam.setInsurance(insurance);
            /*life_insurance*/
            if(!spinnerTACP.getSelectedItem().toString().equals("NO")){
                Log.d("singo", "YESSSSSSS");
                LifeInsurance lifeInsurance = new LifeInsurance();
                String pcl_code = "";
                if(userSession.getUser().getType().equals("so")){
                    pcl_code = dealer.getPCLInscoCode();
                    SelectedData.pclInscoName = dealer.getPCLInscoName();
                }else{
                    pcl_code = userSession.getUser().getDealer().getPcl_insco().getCode();
                    SelectedData.pclInscoName = userSession.getUser().getDealer().getPcl_insco().getName();
                }
                lifeInsurance.setInsurance_name(pcl_code);
                //lifeInsurance.setInsurance_premi(userSession.getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value());
                Log.d("singo", "crosscheck : "+PCL);
                Log.d("singo", "crosscheck : "+SelectedData.pclPremiAmount);
                lifeInsurance.setInsurance_premi(SelectedData.pclPremiAmount);
                if(spinnerTACP.getSelectedItem().toString().equals(onloan)){
                    Log.d("singo", "lifeInsurance : "+onloan);
                    lifeInsurance.setPayment_type("FO");
                }else if(spinnerTACP.getSelectedItem().toString().equals(prepaid)){
                    Log.d("singo", "lifeInsurance : "+prepaid);
                    lifeInsurance.setPayment_type("FP");
                }
                inquiryParam.setLife_insurance(lifeInsurance);
            }else{
                Log.d("singo","NOOOOOOO");
            }
            /*other fee*/
            List<HashMap<String,String>> other_fees = new ArrayList<>();
            HashMap<String,String> other_fee = new HashMap<>();
            if(rbADDB.isChecked()){
                other_fee.put("admin_fee",packageRulesADDB.get(selectedTenorIndex).getAdminFee());
            }else if(rbADDM.isChecked()){
                other_fee.put("admin_fee",packageRulesADDM.get(selectedTenorIndex).getAdminFee());
            }
            other_fees.add(other_fee);
            inquiryParam.setOther_fee(other_fees);

            /*is_vip*/
            inquiryParam.setIs_vip("N");

            /*package_service_price*/
            inquiryParam.setPackage_service_price(paketServis+"");

            /*encode parameters*/
            String param = JSONProcessor.toJSON(inquiryParam);
            LogUtility.logging(TAG,LogUtility.infoLog,"saveTemporaryParams","param",param);

//            SubmitParameters.inquiryParam = inquiryParam;

            draft.setPayment_type_service_package(spinnerAddOnPaymentType.getSelectedItem().toString());
            draft.setCoverage_type(spinnerCoverageType.getSelectedItem().toString());
            if (isSimulasiPaket){
                draft.setIs_simulasi_paket("1");
                draft.setIs_simulasi_budget("0");
                draft.setIs_non_paket("0");
            }else if (isSimulasiBudget){
                draft.setIs_simulasi_paket("0");
                draft.setIs_simulasi_budget("1");
                draft.setIs_non_paket("0");
            }else {
                draft.setIs_simulasi_paket("0");
                draft.setIs_simulasi_budget("0");
                draft.setIs_non_paket("1");
            }
//            draft.setIs_corporate(draft.getIs_corporate());
//            draft.setIs_npwp(draft.getIs_npwp());
//            draft.setIsNonPaket(draft.getIsNonPaket());
//            draft.setIs_simulasi_paket(draft.getIs_simulasi_paket());
//            draft.setIs_simulasi_budget(draft.getIs_simulasi_budget());

            String drafts = JSONProcessor.toJSON(draft);
            LogUtility.logging(TAG,LogUtility.infoLog,"draft","draft",drafts);
            LogUtility.logging(TAG,LogUtility.infoLog,"getIsNonPaket","getIsNonPaket",JSONProcessor.toJSON(draft.getIs_non_paket()));
//            SubmitParameters.draft = draft;

            SelectedData.DpPercentage = etDpPercentage.getText().toString();
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"saveTemporaryParams","Exception",e.getMessage());
        }
    }

    private boolean validasi(){
        boolean status = true;
        /*if(spinnerType.getSelectedItemPosition()<1){
            status = false;
        }*/

        if(atType.getText().toString().equals("")){
            status = false;
        }
        return status;
    }

    private boolean validateMandatory(EditText editText){
        boolean status = true;

        String data = editText.getText().toString().replaceAll("Rp", "").replaceAll(",", "");

        if (data.equals("")) {
            return false;
        }

        return true;
    }

    private void getPackageRules(String carModel){
        packageCode = SelectedData.SelectedPackageCode;
        packageRules = PackageRuleController.getPackageRules(packageCode,
                carModel,null,
                SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(packageCode,
                    null,selectedCarModel.getCategoryGroupId(),
                    SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }
    }

    private void getPackageRulesNonPaket(String carModel){
        packageCode = SelectedData.SelectedPackageCode;
        int isNonPackage = 1;
        packageRules = PackageRuleController.getPackageRules(isNonPackage,
                carModel,null,
                SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(isNonPackage,
                    null,selectedCarModel.getCategoryGroupId(),
                    SharedPreferenceUtils.getUserSession(DraftinputItemKredit.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }
    }

    private boolean validasiProvisi(double input, double provMin, double provMax){
        LogUtility.logging(TAG,LogUtility.infoLog,"validasiProvisi","input",input+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"validasiProvisi","provMin",provMin+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"validasiProvisi","provMax",provMax+"");
        if(input>= provMin && input<= provMax){
            return true;
        }else{
            return false;
        }
    }

    public void setCarListSimulatiPaket(){
        cars.clear();
        carModel.clear();
        categoryGroupInPackage.clear();
        //cars = CarController.getAllCar();
        Log.d("singo", "packageCode : "+selectedPackageCode);
        categoryGroupInPackage = CarController.getCategoryGroupInPackage(selectedPackageCode);
        carInPackage = CarController.getCarInPackage(selectedPackageCode);
        //cars = CarController.getAllAvailableCarInPackage(categoryGroupId);

        List<com.drife.digitaf.ORM.Database.CarModel> models = new ArrayList<>();

        for (int i=0; i<categoryGroupInPackage.size(); i++){
            List<com.drife.digitaf.ORM.Database.CarModel> carModels = CarController.getAllAvailableCarInPackage(categoryGroupInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                com.drife.digitaf.ORM.Database.CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        for (int i=0; i<carInPackage.size(); i++){
            List<com.drife.digitaf.ORM.Database.CarModel> carModels = CarController.getCarModels(carInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                com.drife.digitaf.ORM.Database.CarModel model = carModels.get(j);
                models.add(model);
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

        LogUtility.logging(TAG,LogUtility.infoLog,"setCarListSimulatiPaket","carModel",JSONProcessor.toJSON(carModel));
        LogUtility.logging(TAG,LogUtility.infoLog,"setCarListSimulatiPaket","categoryGroupInPackage",JSONProcessor.toJSON(categoryGroupInPackage));
        LogUtility.logging(TAG,LogUtility.infoLog,"setCarListSimulatiPaket","cars",JSONProcessor.toJSON(cars));
        /*SpinnerUtility.setSpinnerItem(getApplicationContext(), spinnerModel, this.carModel);
        spinnerModel.setSelection(SelectedData.Car);*/

        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
    }

    public void setCarListSimulatiPaket(String brandCode){
        cars.clear();
        carModel.clear();
        categoryGroupInPackage.clear();
        //cars = CarController.getAllCar();
        Log.d("singo", "packageCode : "+selectedPackageCode);
        categoryGroupInPackage = CarController.getCategoryGroupInPackage(selectedPackageCode);
        carInPackage = CarController.getCarInPackage(selectedPackageCode);
        //cars = CarController.getAllAvailableCarInPackage(categoryGroupId);

        List<com.drife.digitaf.ORM.Database.CarModel> models = new ArrayList<>();

        for (int i=0; i<categoryGroupInPackage.size(); i++){
            List<com.drife.digitaf.ORM.Database.CarModel> carModels = CarController.getAllAvailableCarInPackage(categoryGroupInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                com.drife.digitaf.ORM.Database.CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        for (int i=0; i<carInPackage.size(); i++){
            List<com.drife.digitaf.ORM.Database.CarModel> carModels = CarController.getCarModels(carInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                com.drife.digitaf.ORM.Database.CarModel model = carModels.get(j);
                models.add(model);
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

        LogUtility.logging(TAG,LogUtility.infoLog,"setCarListSimulatiPaket","carModel",JSONProcessor.toJSON(carModel));
        LogUtility.logging(TAG,LogUtility.infoLog,"setCarListSimulatiPaket","categoryGroupInPackage",JSONProcessor.toJSON(categoryGroupInPackage));
        LogUtility.logging(TAG,LogUtility.infoLog,"setCarListSimulatiPaket","cars",JSONProcessor.toJSON(cars));

        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
    }

    private void countPaketServis(){
        /*hitung paket servis*/
        inputOtr = 0;
        if(cbServicePackage.isChecked()){
            String packageAmount = etPackageAmount.getText().toString();
            String paketServiceType = spinnerAddOnPaymentType.getSelectedItem().toString();
            if(packageAmount != null && !packageAmount.equals("")){
                String amount = CurrencyFormat.formatNumber(packageAmount);
                long paketServiceAmount = Long.parseLong(amount);
                LogUtility.logging(TAG,LogUtility.debugLog,"calculate","countPaketService : paketServiceAmount",paketServiceAmount+"");
                if(paketServiceType.equals(onloan)){
                    inputOtr = inputOtr+paketServiceAmount;
                    paketServis = paketServiceAmount;
                }else if(paketServiceType.equals(prepaid)){
                    paketServis = paketServiceAmount;
                }
            }
        }
    }

    private void setLTVParameters(){
        pokokHutangAkhir = pokokHutangWithAddOn;
        String tenor = this.tenorMaster.get(selectedTenorIndex).getName();
        String insurance = String.valueOf(premiAmountSumUp.get(selectedTenorIndex));
        SelectedData.PokokHutangAkhir = String.valueOf(pokokHutangAkhir);
        if(spinnerPaymentType.getSelectedItem().toString().equals(onloan)){
            SelectedData.pvPremiAmount = insurance;
        }else{
            SelectedData.pvPremiAmount = "0";
        }
        SelectedData.isNonPaket = packageRules.get(selectedTenorIndex).getIsNonPackage();
        SelectedData.Otr = CurrencyFormat.formatNumber(etOtr.getText().toString());
        SelectedData.PVPaymentType = spinnerPaymentType.getSelectedItem().toString().toUpperCase();
    }

    private void setDataMobil(){
        selectedCar = selectedCarModel.getCarName();
        //selectedCarIndex = position;
        String carCode = selectedCarModel.getCarCode();
        Log.d("CarCode", carCode);
        //getBungaMobil();
        if(!isSimulasiPaket){
            getPackageRulesNonPaket(carCode);
        }else{
            getPackageRules(carCode);
        }

        /*set spinner tenor*/
        tenorMaster.clear();
        tenors.clear();
        tenorMaster = KalkulatorUtility.getAvailableTenor(packageRules);
        for (int i=0; i<tenorMaster.size(); i++){
            Tenor tenor = tenorMaster.get(i);
            tenors.add(tenor.getName());
        }
        SpinnerUtility.setSpinnerItemDraft(DraftinputItemKredit.this, spinnerTenor,this.tenors, inquiryParam.getTenor());
//        spinnerTenor.setSelection(SelectedData.Tenor);

        setCarType();
        setPaymentTypeList();
        setCoverageTypeList();
        setTACPList();
        setProvisionTypeList();
    }

    public void showToolTip(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert.create().show();
    }

    private double countDpPercentage(long otr, long amount){
        double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
        LogUtility.logging(TAG,LogUtility.debugLog,"countDpPercentage","percentage",percentage+"");
        return percentage;
    }

    private boolean validasiPCL(int indexTenor){
        PackageRule rule;
        boolean status = true;
        if(rbADDM.isChecked()){
            rule = packageRulesADDM.get(indexTenor);
        }else{
            rule = packageRulesADDB.get(indexTenor);
        }

        Log.d("singo","pcl type : "+rule.getPCL());
        if(spinnerTACP.getSelectedItem().toString().equals("NO")){
            if(rule.getPCL().equals("Y")){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(DraftinputItemKredit.this)
                        .setTitle("Wajib mengisi PCL")
                        .setMessage("Untuk DP kurang dari 25% wajib menggunakan PCL")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        }
        return status;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DraftinputItemKredit.this, HasilPerhitungandraft.class);
//                                                intent.putExtra("data",hasilPerhitungan);
        intent.putExtra("data", (Serializable) inquiryParam);
        intent.putExtra("id", id);
        intent.putExtra("user_id", user_id);
        intent.putExtra("draft", (Serializable) draft);
        startActivityForResult(intent,REQUEST_CODE_RESULT);
        finish();
    }

}
