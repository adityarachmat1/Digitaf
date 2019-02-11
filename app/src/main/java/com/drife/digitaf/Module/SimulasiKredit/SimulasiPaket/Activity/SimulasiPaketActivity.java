package com.drife.digitaf.Module.SimulasiKredit.SimulasiPaket.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.Model.Master.BiayaTambahanMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.DepresiasiMaster;
import com.drife.digitaf.KalkulatorKredit.Model.OTRDepresiasi;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.InputKredit.InputItemKredit.Activity.InputItemKreditActivity;
import com.drife.digitaf.Module.SimulasiKredit.ResultSimulasi.Activity.ResultSimulasiActivity;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.Activity.NonPaketActivity;
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
import com.drife.digitaf.ORM.Model.Car;
import com.drife.digitaf.UIModel.Paket;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiPaket.Adapter.PaketAdapter;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.google.gson.Gson;
import com.orm.SugarContext;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimulasiPaketActivity extends AppCompatActivity {
    private String TAG = SimulasiPaketActivity.class.getSimpleName();

    @BindView(R.id.rv_paket)
    RecyclerView rvPaket;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.spinMobil)
    Spinner spinMobil;
    @BindView(R.id.btSimulasi)
    Button btSimulasi;
    @BindView(R.id.edOTR)
    EditText edOTR;
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

    private List<Paket> data = new ArrayList<>();
    private PaketAdapter paketAdapter;

    List<CarModel> cars = new ArrayList<>();
    List<String> carModel = new ArrayList<>();
    private List<PackageRule> bannerPackage = new ArrayList<>();
    private List<PackageRule> packageRules = new ArrayList<>();
    private List<String> categoryGroupInPackage = new ArrayList<>();
    private List<String> carInPackage = new ArrayList<>();

    private String selectedPackege = "";
    private PackageRule selectedPackageRule;
    private String selectedCar = "";
    //private int selectedCarIndex = -1;
    List<Double> bungaADDB = new ArrayList<>();
    List<Double> bungaADDM = new ArrayList<>();

    List<OTRDepresiasi> otrDepresiasis = new ArrayList<>();
    List<Double> premiAmountPercentage = new ArrayList<>();
    List<Long> premiAmount = new ArrayList<>();
    List<Long> premiAmountSumUp = new ArrayList<>();
    List<Long> pokokHutang = new ArrayList<>();
    List<Long> bungaAmount = new ArrayList<>();
    List<Long> totalHutang = new ArrayList<>();
    List<Long> installment1 = new ArrayList<>();
    List<Long> totalHutangRounding = new ArrayList<>();
    List<Long> premiAmountPCL = new ArrayList<>();
    List<Long> pokokHutangPCL = new ArrayList<>();
    List<Long> bungaAmountPCL = new ArrayList<>();
    List<Long> totalHutangPCL = new ArrayList<>();
    List<Long> installment2 = new ArrayList<>();
    List<Long> TDP = new ArrayList<>();

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
    List<Long> TDPPrepaid = new ArrayList<>();

    List<Long> TDPNonPcl = new ArrayList<>();
    List<Long> TDPPrepaidNonPcl = new ArrayList<>();

    List<Depresiasi> depresiasis = new ArrayList<>();
    List<Tenor> tenor = new ArrayList<>();
    //List<Double> PCL = new ArrayList<>();
    double PCL = 0.0;
    List<Integer> biayaAdmin = new ArrayList<>();
    List<Integer> biayaAdminADDM = new ArrayList<>();
    List<Integer> polis = new ArrayList<>();
    List<CoverageInsurance> coverageInsurancesTLO = new ArrayList<>();
    List<CoverageInsurance> coverageInsurancesCOMPRE = new ArrayList<>();

    private long inputOtr = 0;
    private int tjh3 = 100000;
    private long premiAmountSumUpCurrentValue = 0;
    private long DP = 0;
    private long PHAwal = 0;

    int REQUEST_CODE_SIMULASI = 1;


    ////// dummy //////
    List<AsuransiPremi> asuransiPremis = new ArrayList<>();
    String coverageAsuransi = "";

    private String addb = "ADDB";
    private String addm = "ADDM";
    private String allrisk = "ALL RISK";
    private String tlo = "TLO";
    private String all = "ALL";
    private String packageCode = "";

    private UserSession userSession;
    private Dealer dealer;
    private CarModel selectedCarModel;

    private String so = "so";
    private String sales = "sales";
    private String spv = "spv";
    private String bm = "bm";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_paket);
        ButterKnife.bind(this);
        SugarContext.init(SimulasiPaketActivity.this);
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

        if (userSession.getUser().getType().equals("so")) {
            lyDealerName.setVisibility(View.VISIBLE);

            if (DealerController.getAllDealer() != null) {
                if (DealerController.getAllDealer().size() > 0) {
                    atDealer.setAdapter(new ArrayAdapter<Dealer>(this, android.R.layout.simple_list_item_1, DealerController.getAllDealer()));
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
            public void onClick(View view) {
                /*try {
                    if(validasi()){
                        reset();
                        getBungaMobil();
                        if(bungaADDB.size()!=0 && bungaADDM.size()!=0){
                            String strotr = CurrencyFormat.formatNumber(edOTR.getText().toString());
                            String strdp = selectedPackageRule.getBaseDp();
                            LogUtility.logging(TAG,LogUtility.infoLog,"input","DP",strdp);

                            int otr = Integer.parseInt(strotr);
                            double baseDp = Double.parseDouble(strdp);

                            double percentage = baseDp;
                            float amount = 0;
                            if(percentage<=100){
                                amount = KalkulatorUtility.countAmount(otr,percentage);
                            }
                            int valueDp = KalkulatorUtility.floadToInt(amount);

                            if(!strotr.equals("") && !strdp.equals("")){
                                inputOtr = Integer.parseInt(strotr);
                                //DP = Integer.parseInt(strdp);
                                DP = valueDp;

                                PHAwal = countPHAwal(inputOtr,DP);
                                LogUtility.logging(TAG,LogUtility.infoLog,"getInput","Pokok Hutang", PHAwal+"");
                                countOtrDepresiasi(inputOtr);

                                setSelectedData();
                                SelectedData.SelectedPackageCode = selectedPackageRule.getPackageCode();
                                SelectedData.Car = spinMobil.getSelectedItemPosition();
                                SelectedData.BaseDP = selectedPackageRule.getBaseDp();
                                SelectedData.JenisAngsuran = selectedPackageRule.getInstallmentType();
                                SelectedData.PVPaymentType = selectedPackageRule.getTAVPPayment();
                                SelectedData.PVCoverageType = selectedPackageRule.getTAVPCoverage();
                                SelectedData.PCLPaymentType = selectedPackageRule.getPCLPayment();

                                Intent intent = new Intent(SimulasiPaketActivity.this, ResultSimulasiActivity.class);
                                TableData tableData = new TableData(TDP,installment2,TDPPrepaid,installment2Prepaid,tenor);
                                tableData.setBunga(bungaADDB);
                                tableData.setBungaADDM(bungaADDM);
                                tableData.setSelectedPackage(selectedPackageRule.getPackageName());
                                tableData.setSelectedPackageCode(selectedPackageRule.getPackageCode());
                                intent.putExtra("data", tableData);
                                intent.putExtra("isPaket",1);
                                intent.putExtra("packageCode", selectedPackege);
                                startActivityForResult(intent,REQUEST_CODE_SIMULASI);
                            }else{
                                Toast.makeText(getApplicationContext(),"Data anda belum lengkap", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SimulasiPaketActivity.this,"This model is not available for NON PAKET",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"btSimulasi.setOnClickListener","Exception",e.getMessage());
                }*/

                if(validasi()){
                    reset();
                    getBungaMobil();
                    if(bungaADDB.size()!=0 && bungaADDM.size()!=0){
                        String strotr = CurrencyFormat.formatNumber(edOTR.getText().toString());
                        String strdp = selectedPackageRule.getBaseDp();
                        LogUtility.logging(TAG,LogUtility.infoLog,"input","DP",strdp);

                        long otr = Long.parseLong(strotr);
                        double baseDp = Double.parseDouble(strdp);

                        double percentage = baseDp;
                        long amount = 0l;
                        if(percentage<=100){
                            amount = KalkulatorUtility.countAmountLong(otr,percentage);
                        }
                        //int valueDp = KalkulatorUtility.floadToInt(amount);
                        long valueDp = amount;

                        if(!strotr.equals("") && !strdp.equals("")){
                            inputOtr = Long.parseLong(strotr);
                            //DP = Integer.parseInt(strdp);
                            DP = valueDp;

                            PHAwal = countPHAwal(inputOtr,DP);
                            LogUtility.logging(TAG,LogUtility.infoLog,"getInput","Pokok Hutang", PHAwal+"");
                            countOtrDepresiasi(inputOtr);

                            setSelectedData();
                            SelectedData.SelectedPackageCode = selectedPackageRule.getPackageCode();
                            //SelectedData.Car = spinMobil.getSelectedItemPosition();
                            SelectedData.BaseDP = selectedPackageRule.getBaseDp();
                            SelectedData.JenisAngsuran = selectedPackageRule.getInstallmentType();
                            SelectedData.PVPaymentType = selectedPackageRule.getTAVPPayment();
                            SelectedData.PVCoverageType = selectedPackageRule.getTAVPCoverage();
                            SelectedData.PCLPaymentType = selectedPackageRule.getPCLPayment();
                            SelectedData.selectedCarModel = selectedCarModel;

                            Intent intent = new Intent(SimulasiPaketActivity.this, ResultSimulasiActivity.class);
                            TableData tableData = new TableData(TDP,installment2,TDPPrepaid,installment2Prepaid,tenor);
                            tableData.setBunga(bungaADDB);
                            tableData.setBungaADDM(bungaADDM);
                            tableData.setSelectedPackage(selectedPackageRule.getPackageName());
                            tableData.setSelectedPackageCode(selectedPackageRule.getPackageCode());
                            intent.putExtra("data", tableData);
                            intent.putExtra("isPaket",1);
                            intent.putExtra("packageCode", selectedPackege);
                            startActivityForResult(intent,REQUEST_CODE_SIMULASI);
                        }else{
                            Toast.makeText(getApplicationContext(),"Data Anda belum lengkap", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SimulasiPaketActivity.this,"This model is not available for NON PAKET",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /*spinMobil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCar = spinMobil.getSelectedItem().toString();
                selectedCarIndex = position;
                String carCode = cars.get(selectedCarIndex).getCarCode();
                getSelectedPackageRules(carCode);
                //getBungaMobil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        edOTR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CurrencyFormat.changeFormat(this, s, edOTR);
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
                setCarList_v2(brandCode);
                atModel.setText("");

                coverageInsurancesTLO = CoverageInsuranceController.getTLO(dealer.getPVInscoCode(),dealer.getInsuranceAreaCode(),dealer.getDealerCode());
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
                selectedCarModel = (com.drife.digitaf.ORM.Database.CarModel) adapterView.getItemAtPosition(i);
                selectedCar = selectedCarModel.getCarName();

                selectedCar = selectedCarModel.getCarName();
                //selectedCarIndex = position;
                String carCode = selectedCarModel.getCarCode();
                getSelectedPackageRules(carCode);
            }
        });
    }

    private void callFunctions(){
        //configureList();
        getPaket();
        setMaster();
        if(bannerPackage != null && bannerPackage.size()>0){
            selectPackage(bannerPackage.get(0).getPackageCode());
            setSelectedPackageRule(bannerPackage.get(0));
            //setCarList(bannerPackage.get(0).getCategoryGroupId());
            //setCarList();

            if(userSession.getUser().getType().equals(so)){
                setCarList();
            }else{
                String brandCode = userSession.getUser().getDealer().getBrand().getCode();
                if(dealer != null){
                    setCarList_v2(brandCode);
                }
            }
        }
    }

    private void configureList(){
        Paket paket1 = new Paket(TextUtility.randomString(10),"test1","test1",true);
        Paket paket2 = new Paket(TextUtility.randomString(10),"test2","test2",false);
        Paket paket3 = new Paket(TextUtility.randomString(10),"test3","test3",false);
        Paket paket4 = new Paket(TextUtility.randomString(10),"test3","test3",false);
        Paket paket5 = new Paket(TextUtility.randomString(10),"test3","test3",false);
        Paket paket6 = new Paket(TextUtility.randomString(10),"test3","test3",false);
        Paket paket7 = new Paket(TextUtility.randomString(10),"test3","test3",false);
        Paket paket8 = new Paket(TextUtility.randomString(10),"test3","test3",false);
        Paket paket9 = new Paket(TextUtility.randomString(10),"test3","test3",false);
        data.add(paket1);
        data.add(paket2);
        data.add(paket3);
        data.add(paket4);
        data.add(paket5);
        data.add(paket6);
        data.add(paket7);
        data.add(paket8);
        data.add(paket9);

        rvPaket.setNestedScrollingEnabled(false);
        rvPaket.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false));
        rvPaket.setHasFixedSize(false);
        paketAdapter = new PaketAdapter(getApplicationContext(),data, bannerPackage);
        rvPaket.setAdapter(paketAdapter);
    }

    public void setCarList(){
        atModel.setText("");
        cars.clear();
        carModel.clear();
        categoryGroupInPackage.clear();
        //cars = CarController.getAllCar();
        categoryGroupInPackage = CarController.getCategoryGroupInPackage(selectedPackageRule.getPackageCode());
        carInPackage = CarController.getCarInPackage(selectedPackageRule.getPackageCode());
        //cars = CarController.getAllAvailableCarInPackage(categoryGroupId);

        List<CarModel> models = new ArrayList<>();

        for (int i=0; i<categoryGroupInPackage.size(); i++){
            List<CarModel> carModels = CarController.getAllAvailableCarInPackage(categoryGroupInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        for (int i=0; i<carInPackage.size(); i++){
            List<CarModel> carModels = CarController.getCarModels(carInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        TextUtility.sortCar(models);

        for (int i=0; i<models.size(); i++){
            CarModel model = models.get(i);
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

        //SpinnerUtility.setSpinnerItem(getApplicationContext(), spinMobil, this.carModel);
        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
    }

    public void setCarList(String brandCode){
        atModel.setText("");
        cars.clear();
        carModel.clear();
        categoryGroupInPackage.clear();
        //cars = CarController.getAllCar();
        categoryGroupInPackage = CarController.getCategoryGroupInPackage(selectedPackageRule.getPackageCode());
        carInPackage = CarController.getCarInPackage(selectedPackageRule.getPackageCode());
        //cars = CarController.getAllAvailableCarInPackage(categoryGroupId);

        List<CarModel> models = new ArrayList<>();

        for (int i=0; i<categoryGroupInPackage.size(); i++){
            List<CarModel> carModels = CarController.getAllAvailableCarInPackage(categoryGroupInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        for (int i=0; i<carInPackage.size(); i++){
            List<CarModel> carModels = CarController.getCarModels(carInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        TextUtility.sortCar(models);

        for (int i=0; i<models.size(); i++){
            CarModel model = models.get(i);
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

    public void setCarList_v2(String brandCode){
        String brnd = brandCode;
        if(dealer != null){
            if(userSession.getUser().getType().equals(so)){
                brnd = dealer.getBrandCode();
            }
        }
        atModel.setText("");
        cars.clear();
        carModel.clear();
        categoryGroupInPackage.clear();
        //cars = CarController.getAllCar();
        String pckCode = "";
        if(selectedPackageRule != null){
            pckCode = selectedPackageRule.getPackageCode();
        }
        //categoryGroupInPackage = CarController.getCategoryGroupInPackage(selectedPackageRule.getPackageCode());
        categoryGroupInPackage = CarController.getCategoryGroupInPackage(pckCode);
        carInPackage = CarController.getCarInPackage(selectedPackageRule.getPackageCode());
        //cars = CarController.getAllAvailableCarInPackage(categoryGroupId);

        List<CarModel> models = new ArrayList<>();

        for (int i=0; i<categoryGroupInPackage.size(); i++){
            List<CarModel> carModels = CarController.getAllAvailableCarInPackage(categoryGroupInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        for (int i=0; i<carInPackage.size(); i++){
            List<CarModel> carModels = CarController.getCarModels(carInPackage.get(i));
            for (int j=0; j<carModels.size(); j++){
                CarModel model = carModels.get(j);
                models.add(model);
            }
        }

        TextUtility.sortCar(models);

        for (int i=0; i<models.size(); i++){
            CarModel model = models.get(i);
            String car = model.getCarName();
            String brand = model.getBrandCode();
            if(brand.equals(brnd)){
                if(!carModel.contains(car)){
                    carModel.add(car);
                    cars.add(model);
                }
            }
        }

        atModel.setAdapter(new ArrayAdapter<com.drife.digitaf.ORM.Database.CarModel>(this, android.R.layout.simple_list_item_1, this.cars));
    }

    private void getPaket(){
        try {
            bannerPackage.clear();
            bannerPackage = PackageRuleController.getPackage();
            if(bannerPackage != null){
                for (int i = 0; i< bannerPackage.size(); i++){
                    PackageRule packageRule = bannerPackage.get(i);
                    String name = packageRule.getPackageName();
                    String image = packageRule.getPackageImage();
                    boolean stat = false;
                    if(i==0){
                        stat = true;
                    }

                    if(!packageRule.getPackageCode().equals("RCFCVNONPAKET") && !packageRule.getPackageCode().equals("RCFCVDNONPAKET")){
                        Paket paket = new Paket(packageRule.getPackageCode(),image,name,stat,"",packageRule.getCategoryGroupId());
                        data.add(paket);
                    }
                }
            }

            for (int i=0; i<bannerPackage.size(); i++){
                Log.d("singo", "display package : "+bannerPackage.get(i).getPackageName());
            }
            rvPaket.setNestedScrollingEnabled(false);
            rvPaket.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false));
            rvPaket.setHasFixedSize(false);
            paketAdapter = new PaketAdapter(getApplicationContext(),data, bannerPackage);
            rvPaket.setAdapter(paketAdapter);
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getPaket","Exception",e.getMessage());
        }
    }

    private boolean validasi(){
        boolean status = true;
        String strOtr = !edOTR.getText().toString().equals("") ? CurrencyFormat.formatNumber(edOTR.getText().toString()) : "0";

        if (dealer == null && userSession.getUser().getType().equals("so")){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(SimulasiPaketActivity.this)
                    .setTitle("Field required")
                    .setMessage("Pilih dealer terlebih dahulu.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        } else if (edOTR.getText().toString().equals("")){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(SimulasiPaketActivity.this)
                    .setTitle("Field required")
                    .setMessage("Harga OTR belum diisi")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        } else if (Long.parseLong(strOtr) < 10000000){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(SimulasiPaketActivity.this)
                    .setTitle("Field required")
                    .setMessage("Harga OTR tidak valid")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }

        return status;
    }

    public void selectPackage(String packageCode){
        selectedPackege = packageCode;
    }

    public void selectPackageCode(String pCode){
        packageCode = pCode;
    }


    private void getBungaMobil(){
        //bungaADDB.clear();
        //bungaADDM.clear();
        //String carModel = cars.get(selectedCarIndex).getCarCode();

        /*String code_nonpaket = selectedPackege;
        List<PackageRule> packageRules;
        packageRules = PackageRuleController.getPackageRules(code_nonpaket,
                carModel,null,
                SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(code_nonpaket,
                    null,cars.get(selectedCarIndex).getCategoryGroupId(),
                    SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }*/

        LogUtility.logging(TAG, LogUtility.infoLog,"getBungaMobil","packageRules",JSONProcessor.toJSON(packageRules));
        if(packageRules.size() != 0){
            String min = packageRules.get(0).getMinDp();
            String max = packageRules.get(0).getMaxDp();

            for(int i=0; i<packageRules.size(); i++){
                PackageRule packageRule = packageRules.get(i);
                double adminFee = Double.parseDouble(packageRule.getAdminFee());
                if(packageRule.getInstallmentType().toUpperCase().equals(addm)){
                    bungaADDM.add(Double.parseDouble(packageRule.getBaseRate()));
                    biayaAdminADDM.add((int) adminFee);
                }else if(packageRule.getInstallmentType().toUpperCase().equals(addb)){
                    bungaADDB.add(Double.parseDouble(packageRule.getBaseRate()));
                    biayaAdmin.add((int) adminFee);
                }
            }

            LogUtility.logging(TAG,LogUtility.infoLog,"getBungaMobil","bungaADDM",JSONProcessor.toJSON(bungaADDM));
            LogUtility.logging(TAG,LogUtility.infoLog,"getBungaMobil","bungaADDB",JSONProcessor.toJSON(bungaADDB));
        }else{
            Toast.makeText(SimulasiPaketActivity.this,"Model ini tidak tersedia di paket ini",Toast.LENGTH_SHORT).show();
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
            //packageRules.clear();
        }catch (Exception e){

        }
    }

    public void setSelectedPackageRule(PackageRule packageRule){
        this.selectedPackageRule = packageRule;
    }

    private long countPHAwal(long otrAwal, long dp){
        long ph = otrAwal - dp;
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal",ph+"");
        return ph;
    }

    private void countOtrDepresiasi(long otr){
        otrDepresiasis = KalkulatorUtility.getOTRAfterDepresiasi(otr,depresiasis);
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            long otrdep = otrDepresiasi.getOTR();
            //LogUtility.logging(TAG,LogUtility.infoLog,"count",otrdep+"");
        }
        if(otrDepresiasis != null){
            getAsuransiPremiPercentage(otrDepresiasis);
            LogUtility.logging(TAG,LogUtility.debugLog,"countOtrDepresiasi","premiAmountPercentage",new Gson().toJson(premiAmountPercentage));
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

    /*private void getAsuransiPremiPercentage(List<OTRDepresiasi> otrDepresiasis){
        premiAmountPercentage = new ArrayList<>();
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            int otr = otrDepresiasi.getOTR();
            for(int j=0; j<asuransiPremis.size(); j++){
                //LogUtility.logging(TAG,LogUtility.infoLog,"loop",j+"");
                AsuransiPremi asuransiPremi = asuransiPremis.get(j);
                int min = asuransiPremi.getCoverageMin();
                int max = asuransiPremi.getCoverageMax();
                double value = 0;
                //LogUtility.logging(TAG,LogUtility.infoLog,"tes","otr : "+otr+", min : "+min+", max : "+max);
                if(otr>min && otr<=max){

                    coverageAsuransi = selectedPackageRule.getTAVPCoverageCode().toUpperCase();
                    if(coverageAsuransi.equals("ALL")){
                        value = asuransiPremi.getRateCompre();
                        premiAmountPercentage.add(value);
                        //LogUtility.logging(TAG,LogUtility.infoLog,"value",value+"");
                    }else if(coverageAsuransi.equals("TLO")){
                        value = asuransiPremi.getRateTLO();
                        premiAmountPercentage.add(value);
                        //LogUtility.logging(TAG,LogUtility.infoLog,"value",value+"");
                    }else {

                    }
                }
            }
        }
    }*/

    private void getAsuransiPremiPercentage(List<OTRDepresiasi> otrDepresiasis){
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","coverageAsuransi",coverageAsuransi);
        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","otrDepresiasis",JSONProcessor.toJSON(otrDepresiasis));
        premiAmountPercentage = new ArrayList<>();
        coverageAsuransi = selectedPackageRule.getTAVPCoverage().toUpperCase();

        List<CoverageInsurance> selectedCoverage;
        if(coverageAsuransi.equals(allrisk)){
            selectedCoverage = coverageInsurancesCOMPRE;
        }else if(coverageAsuransi.equals(all)){
            selectedCoverage = coverageInsurancesCOMPRE;
        }else{
            selectedCoverage = coverageInsurancesTLO;
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","selectedCoverage",JSONProcessor.toJSON(selectedCoverage));

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
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getAsuransiPremiPercentage","premiAmountPercentage",JSONProcessor.toJSON(premiAmountPercentage));
    }

    private void setMaster(){
        //depresiasis = DepresiasiMaster.getDepresiasi();
        setTenor();
        depresiasis = DepresiasiMaster.getDepresiasi(tenor);
        //asuransiPremis = AsuransiPremiMaster.getAsuransiPremis();
        coverageInsurancesTLO = CoverageInsuranceController.getTLO();
        coverageInsurancesCOMPRE = CoverageInsuranceController.getCompre();
        //PCL = PCLMaster.getPCL();
        String pclStr = SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
        if(!pclStr.equals("")){
            PCL = Double.parseDouble(pclStr);
        }
        //biayaAdmin = BiayaTambahanMaster.getBiayaAdmin();
        polis = BiayaTambahanMaster.getPolis();
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

    private void getPremiAmount(List<OTRDepresiasi> otrDepresiasis, List<Double> premiAmountPercentage, int tjh3){
        LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","otrDepresiasis",JSONProcessor.toJSON(otrDepresiasis));
        //LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","premiAmountPercentage",JSONProcessor.toJSON(premiAmountPercentage));
        premiAmount = new ArrayList<>();
        premiAmountSumUp = new ArrayList<>();
        pokokHutang = new ArrayList<>();
        pokokHutangPrepaid = new ArrayList<>();
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            Double percentage = premiAmountPercentage.get(i);
            long otr = otrDepresiasi.getOTR();
            //long amount = Math.round((float) otr*percentage/100);
            long amount = new Double((float) otr*percentage/100).longValue();
            long amt = 0;
            if(coverageAsuransi.equals(allrisk)){
                amt = amount+tjh3;
            }else if(coverageAsuransi.equals(all)){
                amt = amount+tjh3;
            }else{
                amt = amount;
            }
            premiAmount.add(amt);

            /*insert data to premiAmountSum to handle asuransi cicil or TDP*/
            premiAmountSumUpCurrentValue = premiAmountSumUpCurrentValue+amt;
            premiAmountSumUp.add(premiAmountSumUpCurrentValue);
            /*set ph */
            processOnLoanData(PHAwal,premiAmountSumUpCurrentValue);
            processPrepaidData(PHAwal,premiAmountSumUpCurrentValue);
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","PV Premi",JSONProcessor.toJSON(premiAmount));
        LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","PV Premi sumup",JSONProcessor.toJSON(premiAmountSumUp));
        LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","PV Premi percentage",JSONProcessor.toJSON(premiAmount));
        LogUtility.logging(TAG,LogUtility.infoLog,"getPremiAmount","PH + Premi",JSONProcessor.toJSON(pokokHutang));
    }

    private void processOnLoanData(long phAwal, long premiAmountSumUpCurrentValue){
        //pokokHutang = new ArrayList<>();
        long count = phAwal+premiAmountSumUpCurrentValue;
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

    private void getBungaAmount(List<Long> pokokHutang,List<Double> bungas,List<Tenor> tenors){
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","pokokHutang onloan",JSONProcessor.toJSON(pokokHutang));
        LogUtility.logging(TAG,LogUtility.infoLog,"non pcl","bungas",JSONProcessor.toJSON(bungas));
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
            //long a = Math.round((float)ph*bunga/100);
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
            /*String pclStr = SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
            double pcl = Double.parseDouble(pclStr);*/
            double pcl = 0.0;

            //Log.d("singo", "PCL Insco : "+dealer.getPCLInscoName());
            if(userSession.getUser().getType().equals("so")){
                pcl = Double.parseDouble(dealer.getPCLPremi());
                Log.d("singo", "PCL Insco : "+dealer.getPCLInscoName());
            }else{
                String pclStr = SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
                pcl = Double.parseDouble(pclStr);
            }
            long pokHutang = pokokHutang.get(i);
            double bunga = bungas.get(i);

            int ten = Integer.parseInt(tenor1.getName());
            long totHRounding = ins*ten;
            //long premiAmount = Math.round((float) totHRounding*pcl/100);
            long premiAmount = new Double((float) totHRounding*pcl/100).longValue();
            //long pkkHtng = Math.round(pokHutang+premiAmount);
            long pkkHtng = pokHutang+premiAmount;

            int t = ten/12;
            //long a = Math.round((float) pkkHtng*bunga/100);
            long a = new Double((float) pkkHtng*bunga/100).longValue();
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

        LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","premiAmountPCL",JSONProcessor.toJSON(premiAmountPCL));
        LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","totalHutangPCL",JSONProcessor.toJSON(totalHutangPCL));
        LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","installment2",JSONProcessor.toJSON(installment2));
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

        /*String pclStr = SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
        if(!pclStr.equals("")){
            PCL = Double.parseDouble(pclStr);
            LogUtility.logging(TAG,LogUtility.infoLog,"ADDM PCL","PCL Rate",PCL+"");
            LogUtility.logging(TAG,LogUtility.infoLog,"ADDM PCL","PCL Name",SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getPcl_insco().getName());
        }*/

        //double pcl = 0.0;
        if(userSession.getUser().getType().equals("so")){
            PCL = Double.parseDouble(dealer.getPCLPremi());
        }else{
            String pclStr = SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getPcl_insco().getPcl_premi().getPremi_value();
            PCL = Double.parseDouble(pclStr);
        }

        for (int i=0; i<installment1.size(); i++){
            long ins = installment1.get(i);
            Tenor tenor1 = tenor.get(i);
            double pcl = this.PCL;
            long pokHutang = pokokHutang.get(i);
            double bunga = bungas.get(i);

            int ten = Integer.parseInt(tenor1.getName());
            long totHRounding = ins*ten;
            //long premiAmount = Math.round((float) totHRounding*pcl/100);
            long premiAmount = new Double((float) totHRounding*pcl/100).longValue();
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
        premiAmountPCLPrepaid = KalkulatorUtility.getPremiAmountPCLPrepaid(this.totalHutangRoundingPrepaid,this.PCL);
        //pokokHutangPCLPrepaid = KalkulatorUtility.getPokokHutangPCLPrepaid(this.pokokHutangPrepaid,this.premiAmountPCLPrepaid);
        //bungaAmountPCLPrepaid = KalkulatorUtility.getBungaAmountPCLPrepaid(this.pokokHutangPCLPrepaid,this.bungaADDM,this.tenor);
        //totalHutangPCLPrepaid = KalkulatorUtility.getTotalHutangPCLPrepaid(this.pokokHutangPCLPrepaid,this.bungaAmountPCLPrepaid);
        //installment2Prepaid = KalkulatorUtility.getInstallmentFinal(this.totalHutangPCLPrepaid,this.tenor);

        //LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","premiAmountPCL", JSONProcessor.toJSON(premiAmountPCL));
        //LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","totalHutangPCL (AR)", JSONProcessor.toJSON(totalHutangPCL));
        //LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","installment2", JSONProcessor.toJSON(installment2Prepaid));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","totalHutangRoundingPrepaid", JSONProcessor.toJSON(totalHutangRoundingPrepaid));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","pclAmount", JSONProcessor.toJSON(pAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","pkkHtng", JSONProcessor.toJSON(pHutang));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","bAmount", JSONProcessor.toJSON(bAmount));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","tHutangPCL", JSONProcessor.toJSON(tHutangPCL));
        LogUtility.logging(TAG, LogUtility.infoLog,"ADDM PCL","installment2", JSONProcessor.toJSON(installment2));
    }

    /*final result for TDP Onloan*/
    private void getTDP(){
        TDP = KalkulatorUtility.getTDP_ADDB(DP,biayaAdmin,polis);
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP","TDP",JSONProcessor.toJSON(TDP));
    }

    /*final result for TDP Prepaid*/
    private void getTDPPrepaid(){
        TDPPrepaid = KalkulatorUtility.getTDP_ADDM(DP,biayaAdminADDM,polis,installment2Prepaid,premiAmountSumUp,"ONLOAN");
        LogUtility.logging(TAG,LogUtility.infoLog,"TDPPrepaid","TDPPrepaid",JSONProcessor.toJSON(TDPPrepaid));
    }

    private void setSelectedData(){
        SelectedData.CarCode = selectedCarModel.getCarCode();
        SelectedData.Otr = edOTR.getText().toString().replaceAll("Rp", "").replaceAll(",", "");
        SelectedData.Dp = DP+"";
        SelectedData.DpPercentage = selectedPackageRule.getBaseDp()+"";
        SelectedData.PVCoverageCode = selectedPackageRule.getTAVPCoverageCode();
        SelectedData.CategoryGroupId = selectedPackageRule.getCategoryGroupId();
        //SelectedData.Car = selectedCarIndex;
    }

    private void getSelectedPackageRules(String carModel){
        packageCode = selectedPackege;
        packageRules = PackageRuleController.getPackageRules(packageCode,
                carModel,null,
                SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                null,null);

        if(packageRules.size()==0){
            packageRules = PackageRuleController.getPackageRules(packageCode,
                    null,selectedCarModel.getCategoryGroupId(),
                    SharedPreferenceUtils.getUserSession(SimulasiPaketActivity.this).getUser().getDealer().getBranch().getRate_area().getId(),
                    null,null);
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"getSelectedPackageRules","packageRules : size : ",packageRules.size()+"");
    }


}
