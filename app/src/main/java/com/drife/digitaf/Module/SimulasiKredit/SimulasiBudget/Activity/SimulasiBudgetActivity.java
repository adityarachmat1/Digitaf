package com.drife.digitaf.Module.SimulasiKredit.SimulasiBudget.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.Model.Master.CarModelMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.DepresiasiMaster;
import com.drife.digitaf.KalkulatorKredit.Model.OTRDepresiasi;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.SimulasiKredit.ResultSimulasi.Activity.ResultSimulasiActivity;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.TableData;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.ORM.Controller.CoverageInsuranceController;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Controller.TenorController;
import com.drife.digitaf.ORM.Database.CarModel;
import com.drife.digitaf.ORM.Database.CoverageInsurance;
import com.drife.digitaf.ORM.Database.Dealer;
import com.drife.digitaf.ORM.Database.PackageRule;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.Model.Package;
import com.google.gson.Gson;
import com.orm.SugarContext;
import com.orm.util.NamingHelper;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimulasiBudgetActivity extends AppCompatActivity{
    private String TAG = SimulasiBudgetActivity.class.getSimpleName();

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.cbTDP)
    AppCompatRadioButton cbTDP;
    @BindView(R.id.cbAngsuran)
    AppCompatRadioButton cbAngsuran;
    @BindView(R.id.btSimulasi)
    Button btSimulasi;
    @BindView(R.id.spinModel)
    Spinner spinModel;
    @BindView(R.id.etTdpAngsuran)
    EditText etTdpAngsuran;
    @BindView(R.id.txtTitleTdpAngsuran)
    TextView txtTitleTdpAngsuran;
    @BindView(R.id.spinCovInsurance)
    Spinner spinCovInsurance;
    @BindView(R.id.etOtr)
    EditText etOtr;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;
    @BindView(R.id.atDealer)
    AutoCompleteTextView atDealer;
    @BindView(R.id.lyDealerName)
    LinearLayout lyDealerName;
    @BindView(R.id.atModel)
    AutoCompleteTextView atModel;

    List<CarModel> cars = new ArrayList<>();
    List<String> carModel = new ArrayList<>();
    List<String> coverage = new ArrayList<>();
    List<Tenor> tenor = new ArrayList<>();
    List<Depresiasi> depresiasis = new ArrayList<>();
    List<CoverageInsurance> coverageInsurancesTLO = new ArrayList<>();
    List<CoverageInsurance> coverageInsurancesCOMPRE = new ArrayList<>();
    double PCL = 0.0;

    long otr = 0l;
    long tdp = 0l;
    long angsuran = 0l;
    String coverageInsurance = "";
    String allrisk = "ALL RISK";
    String comprehensive = "COMPREHENSIVE";
    String tlo = "TLO";
    String combine = "COMBINE";
    String selectedCarCode = "";
    String selectedPackage = "RCFCVNONPAKET";
    String selectedCategoryGroup = "";
    //private int selectedCarIndex = -1;
    private String addb = "ADDB";
    private String addm = "ADDM";
    int REQUEST_CODE_SIMULASI = 1;
    private int inputTDP = 0;
    private int inputAngsuran = 1;

    private List<PackageRule> nonPackageGrouping = new ArrayList<>();
    private List<PackageRule> packageRules = new ArrayList<>();
    //private List<Long> adminFee = new ArrayList<>();
    private List<Long> dpMurniADDM = new ArrayList<>();
    private List<Long> dpMurniADDB = new ArrayList<>();
    private List<Double> dpMurniPercentageADDB = new ArrayList<>();
    private List<Double> dpMurniPercentageADDM = new ArrayList<>();
    private List<Long> phAwalADDM = new ArrayList<>();
    private List<Long> phAwalADDB = new ArrayList<>();
    private List<Double> dpPercentage = new ArrayList<>();
    //double dpMin = 0.0;
    //double dpMax = 0.0;
    private int tjh3 = 100000;
    /*private double rangeMinDp = 15.0;
    private double rangeMaxDp = 70.0;*/

    private double rangeMinDp = 25.0;
    private double rangeMaxDp = 100.0;

    List<Double> bungaADDB = new ArrayList<>();
    List<Double> bungaADDM = new ArrayList<>();
    List<Long> biayaAdmin = new ArrayList<>();
    List<Long> biayaAdminADDM = new ArrayList<>();
    List<OTRDepresiasi> otrDepresiasis = new ArrayList<>();
    List<Double> premiAmountPercentage = new ArrayList<>();
    String coverageAsuransi = "";
    List<Long> premiAmount = new ArrayList<>();
    List<Long> premiAmountSumUp = new ArrayList<>();
    List<Long> pokokHutangADDB = new ArrayList<>();
    List<Long> pokokHutangADDM = new ArrayList<>();
    private long premiAmountSumUpCurrentValue = 0;
    List<Long> bungaAmount = new ArrayList<>();
    List<Long> totalHutang = new ArrayList<>();
    List<Long> installment1 = new ArrayList<>();
    List<Long> TDPNonPcl = new ArrayList<>();
    List<Long> totalHutangRounding = new ArrayList<>();
    List<Long> premiAmountPCL = new ArrayList<>();
    List<Long> pokokHutangPCL = new ArrayList<>();
    List<Long> bungaAmountPCL = new ArrayList<>();
    List<Long> totalHutangPCL = new ArrayList<>();
    List<Long> installment2 = new ArrayList<>();
    List<Long> TDP = new ArrayList<>();
    List<Long> totalARADDB = new ArrayList<>();

    //List<Long> pokokHutangPrepaid = new ArrayList<>();
    List<Long> bungaAmountPrepaid = new ArrayList<>();
    List<Long> totalHutangPrepaid = new ArrayList<>();
    List<Long> installment1Prepaid = new ArrayList<>();
    List<Long> totalHutangRoundingPrepaid = new ArrayList<>();
    List<Long> premiAmountPCLPrepaid = new ArrayList<>();
    List<Long> pokokHutangPCLPrepaid = new ArrayList<>();
    List<Integer> bungaAmountPCLPrepaid = new ArrayList<>();
    List<Long> totalHutangPCLPrepaid = new ArrayList<>();
    List<Long> installment2Prepaid = new ArrayList<>();
    List<Long> TDPPrepaid = new ArrayList<>();
    List<Long> TDPPrepaidNonPcl = new ArrayList<>();
    List<Long> totalARADDM = new ArrayList<>();

    ////////////////// input by angsuran //////////////////////////
    List<Long> ARsADDM = new ArrayList<>();
    List<Double> RatesADDM = new ArrayList<>();
    List<Long> PVPremiADDM = new ArrayList<>();
    List<Double> PVPremiPercentageADDM = new ArrayList<>();

    List<Long> ARsADDB = new ArrayList<>();
    List<Double> RatesADDB = new ArrayList<>();
    List<Long> PVPremiADDB = new ArrayList<>();
    List<Double> PVPremiPercentageADDB = new ArrayList<>();

    private UserSession userSession;
    private Dealer dealer;
    private int inputType = -1;
    private CarModel selectedCarModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_budget);
        ButterKnife.bind(this);
        SugarContext.init(this);
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

        cbTDP.setChecked(true);
        selectDasarSimulasi(inputTDP);
        userSession = SharedPreferenceUtils.getUserSession(this);
    }

    private void initListeners(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            public void afterTextChanged(Editable s) {
                CurrencyFormat.changeFormat(this, s, etOtr);
            }
        });

        /*cbTDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cbTDP.setChecked(true);
                //cbAngsuran.setChecked(false);
                selectDasarSimulasi();
            }
        });*/

        cbTDP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    selectDasarSimulasi(inputTDP);
                }
            }
        });

        cbAngsuran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    selectDasarSimulasi(inputAngsuran);
                }
            }
        });

        /*cbAngsuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cbAngsuran.setChecked(true);
                //cbTDP.setChecked(false);
                selectDasarSimulasi();
            }
        });*/

        etTdpAngsuran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {
                    if (!etOtr.getText().toString().equals("")) {
                        long strOtr = Long.parseLong(CurrencyFormat.formatNumber(etOtr.getText().toString()));
                        long strTdpAngsuran = Long.parseLong(CurrencyFormat.formatNumber(s.toString()));

                        if (strTdpAngsuran > strOtr && cbTDP.isChecked()) {
                            Toast.makeText(SimulasiBudgetActivity.this, "Nominal TDP tidak boleh lebih besar dari OTR.", Toast.LENGTH_LONG).show();
                        } else if (strTdpAngsuran > strOtr && cbAngsuran.isChecked()) {
                            Toast.makeText(SimulasiBudgetActivity.this, "Nominal angsuran tidak boleh lebih besar dari OTR.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                CurrencyFormat.changeFormat(this, s, etTdpAngsuran);
            }
        });

        btSimulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validasi()){
                    reset();
                    getInput();
                    count(otr);
                }
            }
        });

        /*spinModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CarModel model = cars.get(i);
                selectedCategoryGroup = getCarCategory(model.getCarCode());
                //packageRules = PackageRuleController.getNonPackage(selectedCategoryGroup);
                selectedCarIndex = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        atDealer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                dealer = (Dealer)adapterView.getItemAtPosition(pos);
                SelectedData.SelectedDealer = dealer;

                String brandCode = dealer.getBrandCode();
                setCarList(brandCode);
                atModel.setText("");

                coverageInsurancesTLO = CoverageInsuranceController.getTLO(dealer.getPVInscoCode(),dealer.getInsuranceAreaCode(),dealer.getDealerCode());
                coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre(dealer.getPVInscoCode(),dealer.getInsuranceAreaCode(),dealer.getDealerCode());
            }
        });

        /*atDealer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
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

        spinCovInsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coverageAsuransi = spinCovInsurance.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                selectedCarModel = (com.drife.digitaf.ORM.Database.CarModel) adapterView.getItemAtPosition(i);
                CarModel model = selectedCarModel;
                selectedCategoryGroup = getCarCategory(model.getCarCode());
                //packageRules = PackageRuleController.getNonPackage(selectedCategoryGroup);
                //selectedCarIndex = i;
            }
        });
    }

    private void callFunctions(){
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
        //setCarList();
    }

    private void setMaster(){
        setTenor();
        depresiasis = DepresiasiMaster.getDepresiasi(tenor);
        coverageInsurancesTLO = CoverageInsuranceController.getTLO();
        coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre();
        LogUtility.logging(TAG,LogUtility.infoLog,"setMaster","coverageInsurancesTLO", JSONProcessor.toJSON(coverageInsurancesTLO));
        LogUtility.logging(TAG,LogUtility.infoLog,"setMaster","coverageInsurancesCOMPRE",JSONProcessor.toJSON(coverageInsurancesCOMPRE));
        String pclStr = SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
        if(!pclStr.equals("")){
            PCL = Double.parseDouble(pclStr);
            LogUtility.logging(TAG,LogUtility.infoLog,"PCL","PCL Rate",PCL+"");
            LogUtility.logging(TAG,LogUtility.infoLog,"PCL","PCL Name",SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getPcl_insco().getName());
        }
        //adminFee = getAdminFee();
    }

    private void getPaket(){
        try {
            nonPackageGrouping.clear();
            nonPackageGrouping = PackageRuleController.getNonPackage();
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getPaket","Exception",e.getMessage());
        }
    }

    /*private void setCarList(){
        if(nonPackageGrouping != null && nonPackageGrouping.size()>0){
            cars = CarController.getAllAvailableCarInPackage(nonPackageGrouping.get(0).getCategoryGroupId());
        }

        for (int i=0; i<cars.size(); i++){
            com.drife.digitaf.ORM.Database.CarModel carModel = cars.get(i);
            String model = carModel.getCarName();
            this.carModel.add(model);
        }
        SpinnerUtility.setSpinnerItem(getApplicationContext(), spinModel, this.carModel);
    }*/

    private void setCarList(){

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
        //SpinnerUtility.setSpinnerItem(getApplicationContext(), spinModel, this.carModel);
        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
    }

    private void setCarList(String brandCode){

        carModel.clear();
        cars.clear();
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
            String brand = model.getBrandCode();
            if(brand.equals(brandCode)){
                if(!carModel.contains(car)){
                    carModel.add(car);
                    cars.add(model);
                }
            }
        }
        //SpinnerUtility.setSpinnerItem(getApplicationContext(), spinModel, this.carModel);
        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
    }

    private void selectDasarSimulasi(int inputType) {
        if (inputType == inputTDP) {
            txtTitleTdpAngsuran.setText(getResources().getString(R.string.text_perkiraan_tdp));
            etTdpAngsuran.setHint(getResources().getString(R.string.text_tdp_dikehendaki));
            this.inputType = inputTDP;
        } else if (inputType == inputAngsuran) {
            txtTitleTdpAngsuran.setText(getResources().getString(R.string.text_perkiraan_angsuranperbulan));
            etTdpAngsuran.setHint(getResources().getString(R.string.text_masukkan_jumlah_angsuran));
            this.inputType = inputAngsuran;
        }
    }

    private void setCoverage(){
        coverage.add(comprehensive);
        //coverage.add(tlo);
        //coverage.add(combine);
        SpinnerUtility.setSpinnerItem(getApplicationContext(),spinCovInsurance,coverage);
    }

    private void getInput(){
        String otr = etOtr.getText().toString();
        if(!otr.equals("")){
            this.otr = Long.parseLong(CurrencyFormat.formatNumber(otr));
        }

        String tdp = etTdpAngsuran.getText().toString();
        if(!tdp.equals("")){
            if(inputType == inputTDP){
                this.tdp = Long.parseLong(CurrencyFormat.formatNumber(tdp));
            }else if(inputType == inputAngsuran){
                this.angsuran = Long.parseLong(CurrencyFormat.formatNumber(tdp));
            }
        }
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

    //private Long count

    private String getCarCategory(String carCode){
        String catGroup = "";
        List<String> list = CarController.getCarCategoryGroup(carCode);
        if(list != null && list.size()>0){
            catGroup = list.get(0);
        }
        return catGroup;
    }

    /*private List<Long> getAdminFee(){
        adminFee = PackageRuleController.getAdminFee();
        LogUtility.logging(TAG, LogUtility.infoLog,"getAdminFee","adminFee",JSONProcessor.toJSON(adminFee));
        return adminFee;
    }*/

    /*private List<Long> getDpMurni(Long tdp){
        List<Long> DpMurni = new ArrayList<>();
        for (int i=0; i<tenor.size(); i++){
            Long admin = adminFee.get(i);
            Long dp = tdp - admin;
            DpMurni.add(dp);
        }

        LogUtility.logging(TAG, LogUtility.infoLog,"getDpMurni","DpMurni",JSONProcessor.toJSON(DpMurni));
        return DpMurni;
    }*/

    /*private List<Long> getPhAwal(Long otr){
        List<Long> phAwal = new ArrayList<>();
        for (int i=0; i<dpMurni.size(); i++){
            Long dp = dpMurni.get(i);
            Long ph = otr - dp;
            phAwal.add(ph);
        }

        LogUtility.logging(TAG, LogUtility.infoLog,"getPhAwal","phAwal",JSONProcessor.toJSON(phAwal));
        return phAwal;
    }*/

    private boolean validasi(){
        boolean status = true;
        if(etOtr.getText().toString().equals("")){
            status = false;
            etOtr.setError("OTR tidak boleh kosong");
        }else if(etTdpAngsuran.getText().toString().equals("")){
            status = false;
            etTdpAngsuran.setError("TDP tidak boleh kosong");
        }

        if(userSession.getUser().getType().equals("so")){
            if(dealer == null){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(SimulasiBudgetActivity.this)
                        .setMessage("Pilih dealer terlebih dahulu")
                        .setTitle("Field required")
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

    private void count(Long otr){
        //dpMurni = getDpMurni(tdp);
        //phAwal = getPhAwal(otr);
        //dpPercentage = getDpPercentage();
        /*get*/

        countOtrDepresiasi(otr);

        if(inputType == inputTDP){
            LogUtility.logging(TAG,LogUtility.debugLog,"count","input tdp");
            getPackageRule();
            getSellingRate();

            if(otrDepresiasis != null){
                getAsuransiPremiPercentage(otrDepresiasis);
                LogUtility.logging(TAG,LogUtility.debugLog,"premiAmountPercentage",new Gson().toJson(premiAmountPercentage));
                getPremiAmount(otrDepresiasis,premiAmountPercentage,tjh3);
                //LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi","pokokHutang",JSONProcessor.toJSON(pokokHutang));
                getBungaAmount(pokokHutangADDB,bungaADDB,tenor);
                getTotalHutangRounding(installment1,tenor,pokokHutangADDB,bungaADDB);
                getTDP();

                getBungaAmountPrepaid(pokokHutangADDM,bungaADDM,tenor);
                getTotalHutangRoundingPrepaid(installment1Prepaid,tenor,pokokHutangADDM,bungaADDM);
                getTDPPrepaid();
            }
        }else{
            LogUtility.logging(TAG,LogUtility.debugLog,"count","input angsuran");
            getPackageRuleByTenor();

            for (int i=0; i<bungaADDM.size(); i++){
                long tdpADDM = 0;
                if(dpMurniADDM != null && dpMurniADDM.size()>0){
                    if(dpMurniADDM.get(i)>0){
                        tdpADDM = dpMurniADDM.get(i)+biayaAdminADDM.get(i)+angsuran;
                    }else{
                        tdpADDM = 0l;
                    }
                }

                long tdpADDB = 0;
                if(dpMurniADDB != null && dpMurniADDB.size()>0){
                    if(dpMurniADDB.get(i)>0){
                        tdpADDB = dpMurniADDB.get(i)+biayaAdmin.get(i);
                    }else{
                        tdpADDB = 0l;
                    }
                }

                installment1.add(angsuran);
                installment1Prepaid.add(angsuran);
                TDPNonPcl.add(tdpADDB);
                TDPPrepaidNonPcl.add(tdpADDM);
            }

            LogUtility.logging(TAG,LogUtility.infoLog,"count","tdpADDB",JSONProcessor.toJSON(TDPNonPcl));
            LogUtility.logging(TAG,LogUtility.infoLog,"count","tdpADDM",JSONProcessor.toJSON(TDPPrepaidNonPcl));

            /*if(otrDepresiasis != null){
                getAsuransiPremiPercentage(otrDepresiasis);
                LogUtility.logging(TAG,LogUtility.debugLog,"premiAmountPercentage",new Gson().toJson(premiAmountPercentage));
                getPremiAmount(otrDepresiasis,premiAmountPercentage,tjh3);
                //LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi","pokokHutang",JSONProcessor.toJSON(pokokHutang));
                getBungaAmount(pokokHutangADDB,bungaADDB,tenor);
                getTotalHutangRounding(installment1,tenor,pokokHutangADDB,bungaADDB);
                getTDP();

                getBungaAmountPrepaid(pokokHutangADDM,bungaADDM,tenor);
                getTotalHutangRoundingPrepaid(installment1Prepaid,tenor,pokokHutangADDM,bungaADDM);
                getTDPPrepaid();
            }*/
        }

        setSelectedData();
        SelectedData.SelectedPackageCode = selectedPackage;
        SelectedData.selectedCarModel = selectedCarModel;

        List<Long> installmentValidADDM = validInstallment(otr,installment1Prepaid,tenor,TDPPrepaidNonPcl);
        List<Long> installmentValidADDB = validInstallment(otr,installment1,tenor,TDPNonPcl);

        Intent intent = new Intent(SimulasiBudgetActivity.this, ResultSimulasiActivity.class);
        TableData tableData = new TableData();
        tableData.setTDPADDBLong(TDPNonPcl);
        tableData.setInstallmentADDBLong(installmentValidADDB);
        tableData.setTDPADDMLong(TDPPrepaidNonPcl);
        tableData.setInstallmentADDMLong(installmentValidADDM);
        tableData.setTenors(tenor);
        tableData.setBunga(bungaADDB);
        tableData.setBungaADDM(bungaADDM);
        tableData.setSelectedPackage("NON PAKET");
        tableData.setSelectedPackageCode(selectedPackage);
        tableData.setDpMurniADDB(dpMurniADDB);
        tableData.setDpMurniADDM(dpMurniADDM);
        tableData.setDpMurniPercentageADDB(dpMurniPercentageADDB);
        tableData.setDpMurniPercentageADDM(dpMurniPercentageADDM);
        intent.putExtra("data", tableData);
        intent.putExtra("isBudget",1);

        if(cbTDP.isChecked()){
            intent.putExtra("isTDP",1);
        }
        startActivityForResult(intent,REQUEST_CODE_SIMULASI);
    }

    private void reset(){
        dpMurniADDB.clear();
        dpMurniADDM.clear();
        phAwalADDB.clear();
        phAwalADDM.clear();
        dpPercentage.clear();
        //dpMin = 0.0;
        //dpMax = 0.0;
        bungaADDB.clear();
        bungaADDM.clear();
        biayaAdmin.clear();
        biayaAdminADDM.clear();

        premiAmount.clear();
        premiAmountSumUp.clear();
        pokokHutangADDB.clear();
        pokokHutangADDM.clear();
        premiAmountSumUpCurrentValue = 0;
        bungaAmount.clear();
        totalHutang.clear();
        installment1.clear();
        TDPNonPcl.clear();
        totalHutangRounding.clear();
        premiAmountPCL.clear();
        pokokHutangPCL.clear();
        bungaAmountPCL.clear();
        totalHutangPCL.clear();
        installment2.clear();
        TDP.clear();

        //List<Long> pokokHutangPrepaid = new ArrayList<>();
        bungaAmountPrepaid.clear();
        totalHutangPrepaid.clear();
        installment1Prepaid.clear();
        totalHutangRoundingPrepaid.clear();
        premiAmountPCLPrepaid.clear();
        pokokHutangPCLPrepaid.clear();
        bungaAmountPCLPrepaid.clear();
        totalHutangPCLPrepaid.clear();
        installment2Prepaid.clear();
        TDPPrepaid.clear();
        TDPPrepaidNonPcl.clear();

        dpMurniPercentageADDB.clear();
        dpMurniPercentageADDM.clear();
        totalARADDB.clear();
        totalARADDM.clear();
        premiAmountPercentage.clear();
        otrDepresiasis.clear();

        ARsADDB.clear();
        ARsADDM.clear();
        RatesADDM.clear();
        RatesADDB.clear();

    }

    /*private List<Double> getDpPercentage(){
        List<Double> dpPercen = new ArrayList<>();
        for (int i=0; i<dpMurni.size(); i++){
            Long dp = dpMurni.get(i);
            double dpPercentage = (dp/otr)*100;
            dpPercen.add(dpPercentage);
        }
        LogUtility.logging(TAG, LogUtility.infoLog,"getDpPercentage","dpPercen",JSONProcessor.toJSON(dpPercen));
        return dpPercen;
    }*/

    private void getPackageRule(){
        String carCode = selectedCarModel.getCarCode();
        String carModel = carCode;

        final String code_nonpaket = selectedPackage;
        int isNonPackage = 1;
        packageRules = new ArrayList<>();
        packageRules = PackageRuleController.getPackageRules(isNonPackage,
                carModel,null,
                SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(isNonPackage,
                    null,selectedCarModel.getCategoryGroupId(),
                    SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRule","packageRules",JSONProcessor.toJSON(packageRules));
    }

    private void getPackageRuleByTenor(){
        String carCode = selectedCarModel.getCarCode();
        String carModel = carCode;
        String orderBy = NamingHelper.toSQLNameDefault("MinDp");
        String sortType = "ASC";

        final String code_nonpaket = selectedPackage;
        int isNonPackage = 1;
        packageRules = new ArrayList<>();
        long pvPremiSumUp = 0l;
        for (int i=0; i<tenor.size(); i++){
            String ten = tenor.get(i).getName();
            List<PackageRule> pRules;
            pRules = PackageRuleController.getPackageRulesByTenor(isNonPackage,
                    carModel,null,
                    SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getBranch().getRate_area().getId(), ten,
                    orderBy,sortType);

            if(pRules.size()==0){
                pRules = PackageRuleController.getPackageRulesByTenor(isNonPackage,
                        null,selectedCarModel.getCategoryGroupId(),
                        SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getBranch().getRate_area().getId(), ten,
                        orderBy,sortType);
            }

            if(pRules.size()>0){
                //boolean stat = false;
                PackageRule selectedRuleADDM = null;
                PackageRule selectedRuleADDB = null;
                long dpMurniADDM = 0l;
                long dpMurniADDB = 0l;
                long pokokHutangADDM = 0l;
                long pokokHutangADDB = 0l;
                double dpMurniPercentageADDM = 0.0;
                double dpMurniPercentageADDB = 0.0;
                long arADDM = 0l;
                long arADDB = 0l;
                long pvPremiADDM = 0l;
                long pvPremiADDB = 0l;
                double rateADDM = 0.0;
                double rateADDB = 0.0;
                long adminFeeADDM = 0l;
                long adminFeeADDB = 0l;
                double pvPremiPercentageADDM = 0.0;
                double pvPremiPercentageADDB = 0.0;

                long sumup = pvPremiSumUp;
                for (int j=0; j<pRules.size(); j++){
                    PackageRule packageRule = pRules.get(j);
                    double baseRate = Double.parseDouble(packageRule.getBaseRate());
                    double minDp = Double.parseDouble(packageRule.getMinDp());
                    double maxDp = Double.parseDouble(packageRule.getMaxDp());
                    int tenor = Integer.parseInt(ten);
                    String firstInstallment = packageRule.getInstallmentType();
                    long adminFee = Long.parseLong(packageRule.getAdminFee());

                    long angsuran = this.angsuran;
                    long AR = countAR(tenor,angsuran);
                    long otrAfterDepresiasi = otrDepresiasis.get(i).getOTR();
                    double pvPremiPercentage = getPVPremi(otrAfterDepresiasi);
                    long pvPremiAmount = countPVPremi(otrAfterDepresiasi,pvPremiPercentage);
                    long sumupPv = sumup + pvPremiAmount;
                    long pokokHutang = countPokokHutang(AR,baseRate,tenor);
                    long dp = Math.round(this.otr - pokokHutang + sumupPv);
                    //long dpMurni = Math.round(this.otr - pokokHutang + sumupPv);
                    long dpMurni = KalkulatorUtility.roundUp(dp);
                    double dpMurniPercentage = ((float)dpMurni/this.otr*100);

                    /*if(stat == false){
                        if(dpMurniPercentage>=minDp && dpMurniPercentage<maxDp){
                            pvPremiSumUp = sumupPv;
                            stat = true;
                            packageRules.add(packageRule);
                        }
                    }*/
                    pvPremiSumUp = sumupPv;

                    if(dpMurniPercentage>rangeMinDp && dpMurniPercentage<=rangeMaxDp){
                        if(dpMurniPercentage>=minDp && dpMurniPercentage<=maxDp){
                            //pvPremiSumUp = sumupPv;
                            Log.d("singo", "MASUK");
                            if(firstInstallment.equals(addm)){
                                selectedRuleADDM = packageRule;
                                dpMurniADDM = dpMurni;
                                pokokHutangADDM = pokokHutang;
                                dpMurniPercentageADDM = dpMurniPercentage;
                                arADDM = AR;
                                rateADDM = baseRate;
                                adminFeeADDM = adminFee;
                                pvPremiADDM = pvPremiAmount;
                                pvPremiPercentageADDM = pvPremiPercentage;
                            }else if(firstInstallment.equals(addb)){
                                selectedRuleADDB = packageRule;
                                dpMurniADDB = dpMurni;
                                pokokHutangADDB = pokokHutang;
                                dpMurniPercentageADDB = dpMurniPercentage;
                                arADDB = AR;
                                rateADDB = baseRate;
                                adminFeeADDB = adminFee;
                                pvPremiADDB = pvPremiAmount;
                                pvPremiPercentageADDB = pvPremiPercentage;
                            }
                        }else{
                            Log.d("singo", "TIDAK");
                        }
                    }

                    Log.d("singo","tenor : "+ten);
                    Log.d("singo","firstInstallment : "+firstInstallment);
                    Log.d("singo","angsuran : "+angsuran);
                    Log.d("singo","AR : "+AR);
                    Log.d("singo","otrAfterDepresiasi : "+otrAfterDepresiasi);
                    Log.d("singo","pvPremi : "+pvPremiAmount);
                    Log.d("singo","sumupPv : "+sumupPv);
                    Log.d("singo","pokokHutang : "+pokokHutang);
                    Log.d("singo","dpMurni : "+dpMurni);
                    Log.d("singo","dpMurniPercentage : "+dpMurniPercentage);
                    Log.d("singo","minDp : "+minDp);
                    Log.d("singo","maxDp : "+maxDp);
                    Log.d("singo","/////////////////////////////////////////////////////////////////////////////////");
                }

                if(selectedRuleADDM != null){
                    packageRules.add(selectedRuleADDM);
                    bungaADDM.add(Double.parseDouble(selectedRuleADDM.getBaseRate()));
                    biayaAdminADDM.add(Long.parseLong(selectedRuleADDM.getAdminFee()));
                    this.dpMurniADDM.add(dpMurniADDM);
                    this.phAwalADDM.add(pokokHutangADDM);
                    this.dpMurniPercentageADDM.add(dpMurniPercentageADDM);
                    this.ARsADDM.add(arADDM);
                    this.RatesADDM.add(rateADDM);
                    this.PVPremiADDM.add(pvPremiADDM);
                    this.PVPremiPercentageADDM.add(pvPremiPercentageADDM);
                }else{
                    bungaADDM.add(0.0);
                    biayaAdminADDM.add(0l);
                    this.dpMurniADDM.add(0l);
                    this.phAwalADDM.add(0l);
                    this.dpMurniPercentageADDM.add(0.0);
                    this.ARsADDM.add(0l);
                    this.RatesADDM.add(0.0);
                    this.PVPremiADDM.add(0l);
                    this.PVPremiPercentageADDM.add(0.0);
                }

                if(selectedRuleADDB != null){
                    packageRules.add(selectedRuleADDB);
                    bungaADDB.add(Double.parseDouble(selectedRuleADDB.getBaseRate()));
                    biayaAdmin.add(Long.parseLong(selectedRuleADDB.getAdminFee()));
                    this.dpMurniADDB.add(dpMurniADDB);
                    this.phAwalADDB.add(pokokHutangADDB);
                    this.dpMurniPercentageADDB.add(dpMurniPercentageADDB);
                    this.ARsADDB.add(arADDB);
                    this.RatesADDB.add(rateADDB);
                    this.PVPremiADDB.add(pvPremiADDB);
                    this.PVPremiPercentageADDB.add(pvPremiPercentageADDB);
                }else{
                    bungaADDB.add(0.0);
                    biayaAdmin.add(0l);
                    this.dpMurniADDB.add(0l);
                    this.phAwalADDB.add(0l);
                    this.dpMurniPercentageADDB.add(0.0);
                    this.ARsADDB.add(0l);
                    this.RatesADDB.add(0.0);
                    this.PVPremiADDB.add(0l);
                    this.PVPremiPercentageADDB.add(pvPremiPercentageADDB);
                }
            }
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRule","packageRules size",packageRules.size()+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"getPackageRule","packageRules",JSONProcessor.toJSON(packageRules));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","dpMurniADDM",JSONProcessor.toJSON(dpMurniADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","dpMurniADDB",JSONProcessor.toJSON(dpMurniADDB));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","biayaAdminADDM",JSONProcessor.toJSON(biayaAdminADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","biayaAdminADDB",JSONProcessor.toJSON(biayaAdmin));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","phAwalADDM",JSONProcessor.toJSON(phAwalADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","phAwalADDB",JSONProcessor.toJSON(phAwalADDB));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","ARsADDM",JSONProcessor.toJSON(ARsADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","ARsADDB",JSONProcessor.toJSON(ARsADDB));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","dpMurniPercentageADDM",JSONProcessor.toJSON(dpMurniPercentageADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","dpMurniPercentageADDB",JSONProcessor.toJSON(dpMurniPercentageADDB));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","RatesADDM",JSONProcessor.toJSON(RatesADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","RatesADDB",JSONProcessor.toJSON(RatesADDB));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","PVPremiADDM",JSONProcessor.toJSON(PVPremiADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","PVPremiADDB",JSONProcessor.toJSON(PVPremiADDB));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","PVPremiPercentageADDM",JSONProcessor.toJSON(PVPremiPercentageADDM));
        LogUtility.logging(TAG,LogUtility.infoLog,"count","PVPremiPercentageADDB",JSONProcessor.toJSON(PVPremiPercentageADDB));
    }

    private long countAR(int tenor, long angsuran){
        return tenor*angsuran;
    }

    private long countPokokHutang(long AR, double baseRate, int tenor){
        int ten = tenor/12;
        double bunga = baseRate*ten;
        //double rate = 100+baseRate;
        double rate = 100+bunga;
        //double rate = 100+baseRate;
        //double PH = (float)Math.round((100/rate)*AR);
        double PH = ((float)100/rate*AR);
        //return new Double(PH).longValue();
        return new Double(PH).longValue();
    }

    private void getSellingRate(){
        if(packageRules.size() != 0){
            /*String min = packageRules.get(0).getMinDp();
            String max = packageRules.get(0).getMaxDp();
            if(!min.equals("")){
                dpMin = Double.parseDouble(min);
            }

            if(!max.equals("")){
                dpMax = Double.parseDouble(max);
            }*/

            List<Double> bungADDM = new ArrayList<>();
            List<Double> bungADDB = new ArrayList<>();
            List<Long> adminADDM = new ArrayList<>();
            List<Long> adminADDB = new ArrayList<>();
            List<Long> dpADDM = new ArrayList<>();
            List<Long> dpADDB = new ArrayList<>();
            List<Long> phADDM = new ArrayList<>();
            List<Long> phADDB = new ArrayList<>();
            List<Double> dpPercentageADDM = new ArrayList<>();
            List<Double> dpPercentageADDB = new ArrayList<>();

            for(int i = 0; i< packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);
                double minDp = Double.parseDouble(packageRule.getMinDp());
                double maxDp = Double.parseDouble(packageRule.getMaxDp());
                long adminFee = Long.parseLong(packageRule.getAdminFee());

                /*count dp murni*/
                long dpMurni = tdp - adminFee;
                double dpPercentage = ((float)dpMurni/otr*100);
                Log.d("singo", "adminFee : "+adminFee);
                Log.d("singo", "dpMurni : "+dpMurni);
                Log.d("singo", "otr : "+otr);
                Log.d("singo", "dpPercentage : "+dpPercentage);

                /*count ph awal*/
                long phAwal = otr - dpMurni;

                if(dpPercentage>rangeMinDp && dpPercentage<=rangeMaxDp){
                    if(packageRule.getInstallmentType().equals(addm)){
                        if(dpPercentage>=minDp && dpPercentage<=maxDp){
                            /*bungaADDM.add(Double.parseDouble(packageRule.getBaseRate()));
                            biayaAdminADDM.add(adminFee);
                            this.dpMurniADDM.add(dpMurni);
                            this.phAwalADDM.add(phAwal);
                            this.dpMurniPercentageADDM.add(dpPercentage);*/

                            bungADDM.add(Double.parseDouble(packageRule.getBaseRate()));
                            adminADDM.add(adminFee);
                            dpADDM.add(dpMurni);
                            phADDM.add(phAwal);
                            dpPercentageADDM.add(dpPercentage);
                        }
                    }else if(packageRule.getInstallmentType().equals(addb)){
                        if(dpPercentage>=minDp && dpPercentage<=maxDp){
                            /*bungaADDB.add(Double.parseDouble(packageRule.getBaseRate()));
                            biayaAdmin.add(adminFee);
                            this.dpMurniADDB.add(dpMurni);
                            this.phAwalADDB.add(phAwal);
                            this.dpMurniPercentageADDB.add(dpPercentage);*/

                            bungADDB.add(Double.parseDouble(packageRule.getBaseRate()));
                            adminADDB.add(adminFee);
                            dpADDB.add(dpMurni);
                            phADDB.add(phAwal);
                            dpPercentageADDB.add(dpPercentage);
                        }
                    }
                }else{
                    if(packageRule.getInstallmentType().equals(addm)){
                        /*bungaADDM.add(0.0);
                        biayaAdminADDM.add(0l);
                        this.dpMurniADDM.add(0l);
                        this.phAwalADDM.add(0l);
                        this.dpMurniPercentageADDM.add(0.0);*/

                        bungADDM.add(0.0);
                        adminADDM.add(0l);
                        dpADDM.add(0l);
                        phADDM.add(0l);
                        dpPercentageADDM.add(0.0);
                    }else if(packageRule.getInstallmentType().equals(addb)){
                        /*bungaADDB.add(0.0);
                        biayaAdmin.add(0l);
                        this.dpMurniADDB.add(0l);
                        this.phAwalADDB.add(0l);
                        this.dpMurniPercentageADDB.add(0.0);*/

                        bungADDB.add(0.0);
                        adminADDB.add(0l);
                        dpADDB.add(0l);
                        phADDB.add(0l);
                        dpPercentageADDB.add(0.0);
                    }
                }
            }

            List<Tenor> tenors = KalkulatorUtility.getAvailableTenor(packageRules);
            if(bungADDM != null && bungADDM.size()>0){
                for (int i=0; i<tenors.size(); i++){
                    this.bungaADDM.add(bungADDM.get(i));
                    this.biayaAdminADDM.add(adminADDM.get(i));
                    this.dpMurniADDM.add(dpADDM.get(i));
                    this.phAwalADDM.add(phADDM.get(i));
                    this.dpMurniPercentageADDM.add(dpPercentageADDM.get(i));
                }
            }else{
                for (int i=0; i<tenors.size(); i++){
                    this.bungaADDM.add(0.0);
                    this.biayaAdminADDM.add(0l);
                    this.dpMurniADDM.add(0l);
                    this.phAwalADDM.add(0l);
                    this.dpMurniPercentageADDM.add(0.0);
                }
            }

            if(bungADDB != null && bungADDB.size()>0){
                for (int i=0; i<tenors.size(); i++){
                    this.bungaADDB.add(bungADDB.get(i));
                    this.biayaAdmin.add(adminADDB.get(i));
                    this.dpMurniADDB.add(dpADDB.get(i));
                    this.phAwalADDB.add(phADDB.get(i));
                    this.dpMurniPercentageADDB.add(dpPercentageADDB.get(i));
                }
            }else{
                for (int i=0; i<tenors.size(); i++){
                    this.bungaADDB.add(0.0);
                    this.biayaAdmin.add(0l);
                    this.dpMurniADDB.add(0l);
                    this.phAwalADDB.add(0l);
                    this.dpMurniPercentageADDB.add(0.0);
                }
            }


            /*for (int i=0; i<tenors.size(); i++){
                bungaADDM.add(bungADDM.get(i));
                biayaAdminADDM.add(adminADDM.get(i));
                this.dpMurniADDM.add(dpADDM.get(i));
                this.phAwalADDM.add(phADDM.get(i));
                this.dpMurniPercentageADDM.add(dpPercentageADDM.get(i));

                bungaADDB.add(bungADDB.get(i));
                biayaAdmin.add(adminADDB.get(i));
                this.dpMurniADDB.add(dpADDB.get(i));
                this.phAwalADDB.add(phADDB.get(i));
                this.dpMurniPercentageADDB.add(dpPercentageADDB.get(i));
            }*/

            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","bungaADDM",JSONProcessor.toJSON(bungaADDM));
            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","biayaAdminADDM",JSONProcessor.toJSON(biayaAdminADDM));
            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","dpMurniADDM",JSONProcessor.toJSON(dpMurniADDM));
            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","phAwalADDM",JSONProcessor.toJSON(phAwalADDM));
            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","bungaADDB",JSONProcessor.toJSON(bungaADDB));
            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","dpMurniADDB",JSONProcessor.toJSON(dpMurniADDB));
            LogUtility.logging(TAG,LogUtility.infoLog,"getSellingRate","phAwalADDM",JSONProcessor.toJSON(phAwalADDM));
        }else{
            Toast.makeText(SimulasiBudgetActivity.this,"This model is not available for NON PAKET",Toast.LENGTH_SHORT).show();
        }
    }

    private void groupingPackageRuleByTenor(){
        for (int i=0; i<packageRules.size(); i++){
            PackageRule packageRule = packageRules.get(i);
            int tenor = Integer.parseInt(packageRule.getTenor());
        }
    }

    private void countOtrDepresiasi(long otr){
        otrDepresiasis = KalkulatorUtility.getOTRAfterDepresiasi(otr,depresiasis);
        //LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi",JSONProcessor.toJSON(otrDepresiasis));
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            long otrdep = otrDepresiasi.getOTR();
            //LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi",otrdep+"");
        }
        /*if(otrDepresiasis != null){
            getAsuransiPremiPercentage(otrDepresiasis);
            LogUtility.logging(TAG,LogUtility.debugLog,"premiAmountPercentage",new Gson().toJson(premiAmountPercentage));
            getPremiAmount(otrDepresiasis,premiAmountPercentage,tjh3);
            //LogUtility.logging(TAG,LogUtility.infoLog,"countOtrDepresiasi","pokokHutang",JSONProcessor.toJSON(pokokHutang));
            getBungaAmount(pokokHutangADDB,bungaADDB,tenor);
            getTotalHutangRounding(installment1,tenor,pokokHutangADDB,bungaADDB);
            getTDP();

            getBungaAmountPrepaid(pokokHutangADDM,bungaADDM,tenor);
            getTotalHutangRoundingPrepaid(installment1Prepaid,tenor,pokokHutangADDM,bungaADDM);
            getTDPPrepaid();
        }*/
    }

    private void getAsuransiPremiPercentage(List<OTRDepresiasi> otrDepresiasis){
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","coverageAsuransi",coverageAsuransi);
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","otrDepresiasis",JSONProcessor.toJSON(otrDepresiasis));

        List<CoverageInsurance> selectedCoverage = new ArrayList<>();
        //List<CoverageInsurance> firstYearCoverage = new ArrayList<>();
        /*if(coverageAsuransi.equals(allrisk)){
            selectedCoverage = coverageInsurancesCOMPRE;
        }else if(coverageAsuransi.equals(combine)){
            firstYearCoverage = coverageInsurancesCOMPRE;
            selectedCoverage = coverageInsurancesTLO;
        }else{
            selectedCoverage = coverageInsurancesTLO;
        }*/

        if(coverageAsuransi.equals(comprehensive)){
            selectedCoverage = coverageInsurancesCOMPRE;
        }else{
            selectedCoverage = coverageInsurancesTLO;
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","selectedCoverage",JSONProcessor.toJSON(selectedCoverage));
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","selectedCoverage Name",selectedCoverage.get(0).getPVInsco());

        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            long otr = otrDepresiasi.getOTR();

            for(int j=0; j<selectedCoverage.size(); j++){
                CoverageInsurance coverageInsurance = selectedCoverage.get(j);
                double min = Double.parseDouble(coverageInsurance.getMinTSI());
                double max = Double.parseDouble(coverageInsurance.getMaxTSI());
                double rate = Double.parseDouble(coverageInsurance.getRate());
                if((double)otr>min && (double)otr<=max){
                    premiAmountPercentage.add(rate);
                }
            }

            /*if(coverageAsuransi.equals(combine) && i==0){
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
            }*/

        }

        //LogUtility.logging(TAG,LogUtility.infoLog,"PV","premiAmountPercentage",JSONProcessor.toJSON(premiAmountPercentage));
    }

    private double getPVPremi(long otrAfterDepresiasi){
        List<CoverageInsurance> selectedCoverage;
        if(coverageAsuransi.equals(comprehensive)){
            selectedCoverage = coverageInsurancesCOMPRE;
        }else{
            selectedCoverage = coverageInsurancesTLO;
        }

        double pvPremi = 0.0;

        for(int j=0; j<selectedCoverage.size(); j++){
            CoverageInsurance coverageInsurance = selectedCoverage.get(j);
            double min = Double.parseDouble(coverageInsurance.getMinTSI());
            double max = Double.parseDouble(coverageInsurance.getMaxTSI());
            double rate = Double.parseDouble(coverageInsurance.getRate());
            if((double)otrAfterDepresiasi>min && (double)otrAfterDepresiasi<=max){
                pvPremi = rate;
            }
        }

        /*double premiAmount = (float)Math.round((otrAfterDepresiasi*pvPremi)/100);
        long amt = 0;
        if(coverageAsuransi.equals(allrisk)){
            amt = new Double(premiAmount+tjh3).longValue();
        }else{
            amt = new Double(premiAmount).longValue();
        }*/

        return pvPremi;
    }

    private long countPVPremi(long otrAfterDepresiasi, double pvPremiPercentage){
        double premiAmount = (float)otrAfterDepresiasi*pvPremiPercentage/100;
        long amt = 0;
        if(coverageAsuransi.equals(comprehensive)){
            amt = new Double(premiAmount+tjh3).longValue();
        }else{
            amt = new Double(premiAmount).longValue();
        }

        return amt;
    }

    private void getPremiAmount(List<OTRDepresiasi> otrDepresiasis, List<Double> premiAmountPercentage, int tjh3){
        LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","otrDepresiasis",JSONProcessor.toJSON(otrDepresiasis));
        LogUtility.logging(TAG,LogUtility.infoLog,"PV","premiAmountPercentage",JSONProcessor.toJSON(premiAmountPercentage));
        premiAmount = new ArrayList<>();
        premiAmountSumUp = new ArrayList<>();
        pokokHutangADDB = new ArrayList<>();
        pokokHutangADDM = new ArrayList<>();
        try {
            for (int i=0; i<otrDepresiasis.size(); i++){
                OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
                Double percentage = premiAmountPercentage.get(i);
                long otr = otrDepresiasi.getOTR();
                //int amount = (int) Math.round((otr*percentage)/100);
                long amount = new Double((float) otr*percentage/100).longValue();
                //Log.d("singo", "coverage : "+coverageAsuransi);
                long amt = 0;
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
                premiAmount.add(amt);

                /*insert data to premiAmountSum to handle asuransi cicil or TDP*/
                premiAmountSumUpCurrentValue = premiAmountSumUpCurrentValue+amt;
                premiAmountSumUp.add(premiAmountSumUpCurrentValue);
                /*set ph */
                processADDB(phAwalADDB.get(i),premiAmountSumUpCurrentValue);
                processADDM(phAwalADDM.get(i),premiAmountSumUpCurrentValue);
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"PV premiAmount","premiAmount",JSONProcessor.toJSON(premiAmount));
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getPremiAmount","Exception",e.getMessage());
        }
    }

    private void processADDB(long phAwal, long premiAmountSumUpCurrentValue){
        //pokokHutang = new ArrayList<>();
        long count = phAwal+premiAmountSumUpCurrentValue;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processOnLoanData","count",count+"");
        pokokHutangADDB.add(count);
    }

    private void processADDM(long phAwal, long premiAmountSumUpCurrentValue){
        //pokokHutangPrepaid = new ArrayList<>();
        long count = phAwal+premiAmountSumUpCurrentValue;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processPrepaidData","count",count+"");
        pokokHutangADDM.add(count);
    }

    private void getBungaAmount(List<Long> pokokHutang,List<Double> bungas,List<Tenor> tenors){
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : pokokHutang onloan",JSONProcessor.toJSON(pokokHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : bungas",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : tenors",JSONProcessor.toJSON(tenors));
        bungaAmount = new ArrayList<>();
        totalHutang = new ArrayList<>();
        installment1 = new ArrayList<>();
        for (int i=0; i<pokokHutang.size(); i++){
            long ph = pokokHutang.get(i);
            double bunga = bungas.get(i);
            Tenor tenor = tenors.get(i);
            int ten = Integer.parseInt(tenor.getName());
            int t = ten/12;
            long a = new Double((float) ph*bunga/100).longValue();
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

            long tdp1 = dpMurniADDB.get(i)+biayaAdmin.get(i);
            TDPNonPcl.add(tdp1);
        }
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : bungas onloan",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : bungaAmount onloan",JSONProcessor.toJSON(bungaAmount));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : AR non PCL onloan",JSONProcessor.toJSON(totalHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : Installment non PCL onloan",JSONProcessor.toJSON(installment1));
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","non pcl : TDPNonPcl non PCL onloan",JSONProcessor.toJSON(TDPNonPcl));
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
                this.PCL = pcl;
            }else if(userSession.getUser().getType().equals("sales")){
                String pclStr = SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
                pcl = Double.parseDouble(pclStr);
                this.PCL = pcl;
            }else{
                String pclStr = SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
                pcl = Double.parseDouble(pclStr);
                this.PCL = pcl;
            }
            long pokHutang = pokokHutang.get(i);
            double bunga = bungas.get(i);

            int ten = Integer.parseInt(tenor1.getName());
            long totHRounding = ins*ten;
            long premiAmount = new Double((float)totHRounding*pcl/100).longValue();
            long pkkHtng = pokHutang+premiAmount;

            int t = ten/12;
            long a = new Double((float) pkkHtng*bunga/100).longValue();
            long bungaAmount = a*t;

            tRound.add(totHRounding);
            pAmount.add(premiAmount);
            pHutang.add(pkkHtng);
            bAmount.add(bungaAmount);
            listpokHutang.add(pokHutang);

            long totHutPCL = pkkHtng+bungaAmount;

            long round = KalkulatorUtility.roundUp(totHutPCL/ten);

            long totARRounding = round*ten;
            totalARADDB.add(totARRounding);

            //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","value",round+"");
            totalHutangRounding.add(totHRounding);
            premiAmountPCL.add(premiAmount);
            pokokHutangPCL.add(pkkHtng);
            bungaAmountPCL.add(bungaAmount);
            totalHutangPCL.add(totHutPCL);
            installment2.add(round);
        }

        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","premiAmountPCL : Onloan", JSONProcessor.toJSON(premiAmountPCL));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","totalHutangPCL (AR) : Onloan", JSONProcessor.toJSON(totalARADDB));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","installment2 : Onloan", JSONProcessor.toJSON(installment2));

        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","tRound : Onloan", JSONProcessor.toJSON(tRound));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","pAmount : Onloan", JSONProcessor.toJSON(pAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","pHutang : Onloan", JSONProcessor.toJSON(pHutang));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","bAmount : Onloan", JSONProcessor.toJSON(bAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","pokHutang : Onloan", JSONProcessor.toJSON(listpokHutang));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","bungas : Onloan", JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG, LogUtility.infoLog,"PCL","tenor : Onloan", JSONProcessor.toJSON(tenor));
    }

    /*final result for TDP Onloan*/
    private void getTDP(){
        TDP = KalkulatorUtility.getTDP_ADDB(dpMurniADDB,biayaAdmin,null,tenor);
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP","TDP",JSONProcessor.toJSON(TDP));
    }

    private void getBungaAmountPrepaid(List<Long> pokokHutang,List<Double> bungas,List<Tenor> tenors){
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","pokokHutang Prepaid",JSONProcessor.toJSON(pokokHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","bungas Prepaid",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","tenors Prepaid",JSONProcessor.toJSON(tenors));
        LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmountPrepaid","DP",dpMurniADDM+"");
        bungaAmountPrepaid = new ArrayList<>();
        totalHutangPrepaid = new ArrayList<>();
        installment1Prepaid = new ArrayList<>();
        for (int i=0; i<pokokHutang.size(); i++){
            long ph = pokokHutang.get(i);
            double bunga = bungas.get(i);
            Tenor tenor = tenors.get(i);
            int ten = Integer.parseInt(tenor.getName());
            int t = ten/12;
            long a = new Double((ph*bunga)/100).longValue();
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
            //installment1Prepaid.add(round);

            long tdp1 = dpMurniADDM.get(i)+biayaAdminADDM.get(i)+round;
            if(dpMurniADDM.get(i)>0){
                TDPPrepaidNonPcl.add(tdp1);
                installment1Prepaid.add(round);
            }else{
                TDPPrepaidNonPcl.add(0l);
                installment1Prepaid.add(0l);
            }

        }

        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungas prepaid",JSONProcessor.toJSON(bungas));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungaAmountPrepaid prepaid",JSONProcessor.toJSON(bungaAmountPrepaid));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","AR non PCL Prepaid",JSONProcessor.toJSON(totalHutangPrepaid));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","Installment non PCL Prepaid",JSONProcessor.toJSON(installment1Prepaid));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","TDPPrepaidNonPcl non PCL Prepaid",JSONProcessor.toJSON(TDPPrepaidNonPcl));
    }

    private void getTotalHutangRoundingPrepaid(List<Long> installment1,List<Tenor> tenor,
                                               List<Long> pokokHutang, List<Double> bungas){
        //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","size",installment1.size()+"");
        totalHutangRoundingPrepaid = new ArrayList<>();

        List<Long> tRound = new ArrayList<>();
        List<Long> pAmount = new ArrayList<>();
        List<Long> pHutang = new ArrayList<>();
        List<Integer> bAmount = new ArrayList<>();
        List<Integer> listpokHutang = new ArrayList<>();
        List<Long> tHutangPCL = new ArrayList<>();
        List<Long> installment2 = new ArrayList<>();
        //List<Long> totalAR = new ArrayList<>();

        /*String pclStr = SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
        if(!pclStr.equals("")){
            PCL = Double.parseDouble(pclStr);
            LogUtility.logging(TAG,LogUtility.infoLog,"ADDM PCL","PCL Rate",PCL+"");
            LogUtility.logging(TAG,LogUtility.infoLog,"ADDM PCL","PCL Name",SharedPreferenceUtils.getUserSession(NonPaketActivity.this).getUser().getDealer().getPcl_insco().getName());
        }*/

        if(userSession.getUser().getType().equals("so")){
            PCL = Double.parseDouble(dealer.getPCLPremi());
        }else if(userSession.getUser().getType().equals("sales")){
            String pclStr = SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
            if(!pclStr.equals("")){
                PCL = Double.parseDouble(pclStr);
            }
        }else{
            String pclStr = SharedPreferenceUtils.getUserSession(SimulasiBudgetActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
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
            long premiAmount = new Double((float)totHRounding*pcl/100).longValue();
            long pkkHtng = pokHutang+premiAmount;

            int t = ten/12;
            int a = new Double((pkkHtng*bunga)/100).intValue();
            int bungaAmount = a*t;

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

            long totARRounding = round*ten;
            totalARADDM.add(totARRounding);

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
        LogUtility.logging(TAG, LogUtility.infoLog,"calculate","ADDM PC : totalHutangRoundingPrepaid", JSONProcessor.toJSON(totalHutangRoundingPrepaid));
        LogUtility.logging(TAG, LogUtility.infoLog,"calculate","ADDM PC : PCL Rate",PCL+"");
        LogUtility.logging(TAG, LogUtility.infoLog,"calculate","ADDM PC : pclAmount", JSONProcessor.toJSON(pAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"calculate","ADDM PC : pkkHtng", JSONProcessor.toJSON(pHutang));
        LogUtility.logging(TAG, LogUtility.infoLog,"calculate","ADDM PC : bAmount", JSONProcessor.toJSON(bAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"calculate","ADDM PC : tHutangPCL", JSONProcessor.toJSON(totalARADDM));
        LogUtility.logging(TAG, LogUtility.infoLog,"calculate","ADDM PC : installment with pcl", JSONProcessor.toJSON(installment2));
    }

    /*final result for TDP Prepaid*/
    private void getTDPPrepaid(){
        TDPPrepaid = KalkulatorUtility.getTDP_ADDM2(dpMurniADDM,biayaAdminADDM,null,installment2Prepaid,premiAmountSumUp,"ONLOAN",tenor);
        LogUtility.logging(TAG,LogUtility.infoLog,"calculate","TDPADDM",JSONProcessor.toJSON(TDPPrepaid));
    }

    private void setSelectedData(){
        //SelectedData.Car = spinModel.getSelectedItemPosition();
        SelectedData.Otr = etOtr.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
        //SelectedData.Dp = etDp.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
        //SelectedData.DpPercentage = etPercentage.getText().toString();
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

    private boolean validasiResult(long otr, long angsuran, int tenor, long tdp){
        long value = (angsuran*tenor)+tdp;
        if(value<otr){
            return false;
        }else{
            return true;
        }
    }

    private List<Long> validInstallment(long otr, List<Long> installment, List<Tenor> tenor, List<Long> tdp){
        List<Long> validValue = new ArrayList<>();
        for (int i=0; i<installment.size(); i++){
            int ten = Integer.parseInt(tenor.get(i).getName());
            long installmentValid = installment.get(i);
            if(validasiResult(otr,installment.get(i),ten,tdp.get(i))){
                validValue.add(installmentValid);
            }else{
                validValue.add(0l);
            }
        }
        return validValue;
    }

}
