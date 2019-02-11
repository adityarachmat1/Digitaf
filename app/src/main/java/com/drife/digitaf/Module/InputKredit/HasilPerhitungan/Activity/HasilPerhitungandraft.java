package com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.Currency.CurrencyFormat;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.Dialog.PopupDraft.PopupUtils;
import com.drife.digitaf.Module.InputKredit.InputItemKredit.Activity.DraftinputItemKredit;
import com.drife.digitaf.Module.InputKredit.MinimumCustomerData.Activity.MinimumCUSDATADraft;
import com.drife.digitaf.ORM.Controller.CarController;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Controller.DraftController;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Database.CarModel;
import com.drife.digitaf.ORM.Database.CarType;
import com.drife.digitaf.ORM.Database.Dealer;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.ORM.Database.PackageRule;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.SendEmail.SendEmailCallback;
import com.drife.digitaf.Service.SendEmail.SendEmailPresenter;
import com.drife.digitaf.Service.Submit.DraftCallback;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.SubmitParam.EmailParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.LifeInsurance;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HasilPerhitungandraft extends AppCompatActivity{
    private String TAG = HasilPerhitungandraft.class.getSimpleName();
    private ProgressDialog progressDialog;
    List<CarModel> carModels = new ArrayList<>();
    List<CarType> cartype = new ArrayList<>();
    List<PackageRule> packageRules = new ArrayList<>();
    public static String pvPremi="";
    String name, Name_package, car_code;
    String brand;
    private String is_corporate, is_simulasi_paket, is_simulasi_budget, is_non_paket, is_npwp,
            payment_type_service_package, coverage_type;
    private String comprehensive = "COMPREHENSIVE";
    private String tlo = "TLO";
    private String combine = "COMBINATION";
    private String allrisk = "ALL RISK";
    private int tjh3 = 100000;

    private String spv = "spv";
    private String bm = "bm";


    List<com.drife.digitaf.ORM.Database.Dealer> dealers = new ArrayList<>();

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btnLanjutkan)
    LinearLayout btnLanjutkan;

    @BindView(R.id.btnDraft)
    LinearLayout btnDraft;

    @BindView(R.id.lntotal)
    LinearLayout lnTotal;

    @BindView(R.id.ln_total2)
    LinearLayout ln_total2;

    @BindView(R.id.tvModel)
    TextView tvModel;
    @BindView(R.id.tvOtr)
    TextView tvOtr;
    @BindView(R.id.tvPaket)
    TextView tvPaket;
    @BindView(R.id.tvTenor)
    TextView tvTenor;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.tvAngsuran)
    TextView tvAngsuran;
    @BindView(R.id.tvDp)
    TextView tvDp;
    @BindView(R.id.tvTdp)
    TextView tvTdp;
    @BindView(R.id.tvUlangi)
    TextView tvUlangi;
    @BindView(R.id.tvTotalHutang)
    TextView tvTotalHutang;
    @BindView(R.id.tvPVPremi)
    TextView tvPVPremi;
    @BindView(R.id.tvPCLPremi)
    TextView tvPCLPremi;
    @BindView(R.id.tvAdminFee)
    TextView tvAdminFee;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvFirstInstallment)
    TextView tvFirstInstallment;
    @BindView(R.id.ivEmail)
    ImageView ivEmail;
    @BindView(R.id.iv_tooltipPVPremi)
    ImageView iv_tooltipPVPremi;
    @BindView(R.id.iv_tooltipPCLPremi)
    ImageView iv_tooltipPCLPremi;

//    private DraftItem data;
    private InquiryParam inquiryParam;
    private Draft draft;
    int REQUEST_CODE_MIN_CUS_DATA = 2;
    Long id ;
    String user_id;
    private UserSession userSession;
    private Dealer dealer;
    private String selectedCar = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_kalkulasi);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_MIN_CUS_DATA){
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
        userSession = SharedPreferenceUtils.getUserSession(this);
        dealer = SelectedData.SelectedDealer;
        progressDialog = DialogUtility.createProgressDialog(HasilPerhitungandraft.this,"","Please wait...");


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

        if(userSession.getUser().getType().equals(spv) || userSession.getUser().getType().equals(bm)){
            btnDraft.setVisibility(View.GONE);
            btnLanjutkan.setVisibility(View.GONE);
        }

        btnLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userSession.getUser().getType().equals(spv) && !userSession.getUser().getType().equals(bm)){
                    Intent intent = new Intent(HasilPerhitungandraft.this, MinimumCUSDATADraft.class);
                    intent.putExtra("data", (Serializable) inquiryParam);
                    intent.putExtra("id", id);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("draft", (Serializable) draft);
                    if (draft.getIs_non_paket() !=null){
                        Log.d("getIsNonPaket", draft.getIs_non_paket());
                    }else {
                        Log.d("getIsNonPaket", "null");
                    }
                    startActivityForResult(intent, REQUEST_CODE_MIN_CUS_DATA);
                }
            }
        });

        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draftSave();
            }
        });

//        tvUlangi.setVisibility(View.GONE);
        tvUlangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ulangi_perhitungan();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HasilPerhitungandraft.this);
                builder.setTitle("Email Address");
                final EditText input = new EditText(HasilPerhitungandraft.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = input.getText().toString();
                        sendEmail(email);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        iv_tooltipPVPremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtility.showToolTip(HasilPerhitungandraft.this, getResources().getString(R.string.tooltip_pvpremi_calresult));
            }
        });

        iv_tooltipPCLPremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtility.showToolTip(HasilPerhitungandraft.this, getResources().getString(R.string.tooltip_pclpremi_calresult));
            }
        });
    }

    private void callFunctions(){
        getIntentData();
    }

    private void draftSave() {
        progressDialog.show();
        Log.d("tenorDraft", inquiryParam.getTenor());
        final String param = JSONProcessor.toJSON(inquiryParam);
//        SubmitParameters.inquiryParam = inquiryParam;
        LogUtility.logging(TAG, LogUtility.infoLog,"SendDraft","param",param);


        SubmitPresenter.draft(HasilPerhitungandraft.this, new DraftCallback(){
            @Override
            public void onSuccessDraft(int formId, final String date) {
                progressDialog.dismiss();
                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                PopupUtils.simpanKeDraftSaved(HasilPerhitungandraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                    @Override
                    public void isSaved(boolean isSaved) {
                        if (!isSaved) {
                            Intent intent = new Intent();
                            intent.putExtra("is_draft", true);
                            setResult(Activity.RESULT_OK, intent);
                            Draft drafts = Draft.findById(Draft.class, id);
                            drafts.setData(param);
                            drafts.setLastSaved(date);
                            drafts.setNamaCustomer(draft.getNamaCustomer());
                            drafts.setIs_simulasi_budget(draft.getIs_simulasi_budget());
                            drafts.setIs_simulasi_paket(draft.getIs_simulasi_paket());
                            drafts.setIsNonPaket(draft.getIsNonPaket());
                            drafts.setIs_npwp(draft.getIs_npwp());
                            drafts.setIs_corporate(draft.getIs_corporate());
                            drafts.setCoverage_type(draft.getCoverage_type());
                            drafts.setPayment_type_service_package(draft.getPayment_type_service_package());
                            drafts.save();
                            finish();
                        } else {
                            setResult(Activity.RESULT_OK);
                            Draft drafts = Draft.findById(Draft.class, id);
                            drafts.setData(param);
                            drafts.setLastSaved(date);
                            drafts.setNamaCustomer(draft.getNamaCustomer());
                            drafts.setIs_simulasi_budget(draft.getIs_simulasi_budget());
                            drafts.setIs_simulasi_paket(draft.getIs_simulasi_paket());
                            drafts.setIsNonPaket(draft.getIs_non_paket());
                            drafts.setIs_npwp(draft.getIs_npwp());
                            drafts.setIs_corporate(draft.getIs_corporate());
                            drafts.setCoverage_type(draft.getCoverage_type());
                            drafts.setPayment_type_service_package(draft.getPayment_type_service_package());
                            drafts.save();
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onFailedDraft(String message) {
                progressDialog.dismiss();
//                Toast.makeText(HasilPerhitungandraft.this,"Failed save Draft", Toast.LENGTH_SHORT).show();
                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                PopupUtils.simpanKeDraftSaved(HasilPerhitungandraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                    @Override
                    public void isSaved(boolean isSaved) {
                        setResult(Activity.RESULT_OK);
                        Draft drafts = Draft.findById(Draft.class, id);
                        drafts.setData(param);
                        drafts.setNamaCustomer(draft.getNamaCustomer());
                        drafts.setCoverage_type(draft.getCoverage_type());
                        drafts.setPayment_type_service_package(draft.getPayment_type_service_package());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
                        String currentDateandTime = sdf.format(new Date());
                        drafts.setNamaCustomer(draft.getNamaCustomer());
                        drafts.setIs_simulasi_budget(draft.getIs_simulasi_budget());
                        drafts.setIs_simulasi_paket(draft.getIs_simulasi_paket());
                        drafts.setIsNonPaket(draft.getIs_non_paket());
                        drafts.setIs_npwp(draft.getIs_npwp());
                        drafts.setIs_corporate(draft.getIs_corporate());
                        drafts.setLastSaved(currentDateandTime);

                        drafts.save();
                        finish();
                    }
                });
            }
        },"draft",param, user_id, "", SelectedData.DpPercentage, draft.getIs_corporate(), draft.getIs_simulasi_paket(),
                draft.getIs_simulasi_budget(), draft.getIs_non_paket(), draft.getIs_npwp(), draft.getPayment_type_service_package(),
                draft.getCoverage_type());
    }
    private void getIntentData(){

        inquiryParam = (InquiryParam) getIntent().getSerializableExtra("data");
        draft = (Draft) getIntent().getSerializableExtra("draft");
        id = getIntent().getLongExtra("id",1);
        user_id = getIntent().getStringExtra("user_id");
//        is_simulasi_paket = getIntent().getStringExtra("is_simulasi_paket");
//        is_simulasi_budget = getIntent().getStringExtra("is_simulasi_budget");
//        is_non_paket = getIntent().getStringExtra("is_non_paket");
//        is_npwp = getIntent().getStringExtra("is_npwp");
//        payment_type_service_package = getIntent().getStringExtra("payment_type_service_package");
//        is_corporate = getIntent().getStringExtra("is_corporate");
//        coverage_type = getIntent().getStringExtra("coverage_type");
//
//        if (coverage_type.equals("")){
//            Log.d("coveragetype", "kosong");
//        }else {
//            Log.d("draft", draft.getPayment_type_service_package());
//        }


        int tenor = Integer.parseInt(inquiryParam.getTenor());
        int angsuran = Integer.parseInt(inquiryParam.getInstallment_amount());
        int hutang = tenor * angsuran;

        long sum = 0l;
        int newSum = 0;
        for (int i = 0; i < inquiryParam.getInsurance().getInsurance_list().size(); ++i) {
//            Log.d("mainPremi", data.form_value.getInsurance().getInsurance_list().get(i).getMainpremi());
            //sum = Integer.parseInt(inquiryParam.getInsurance().getInsurance_list().get(i).getMainpremi());
            sum = 0;
            String covType = inquiryParam.getInsurance().getInsurance_list().get(i).getCoverage();
            if(covType.equals(combine)){
                if(i==0){
                    sum = Integer.parseInt(inquiryParam.getInsurance().getInsurance_list().get(i).getMainpremi())+tjh3;
                }else{
                    sum = Integer.parseInt(inquiryParam.getInsurance().getInsurance_list().get(i).getMainpremi());
                }
            }else if(covType.equals(allrisk)){
                sum = Integer.parseInt(inquiryParam.getInsurance().getInsurance_list().get(i).getMainpremi())+tjh3;
            }else{
                sum = Integer.parseInt(inquiryParam.getInsurance().getInsurance_list().get(i).getMainpremi());
            }
            newSum += sum;
        }

        List<HashMap<String,String>> other_fee = inquiryParam.getOther_fee();
        String adminfee = other_fee.get(0).get("admin_fee");

        carModels = CarController.getCarModels(inquiryParam.getModel());
        CarModel carModel = carModels.get(0);
        name = carModel.getCarName();
        car_code = carModel.getCarCode();
        brand = carModel.getBrand();

        Log.d("singo", "car type : "+inquiryParam.getType());
        cartype = CarController.getAllCarTypeName(inquiryParam.getType());
        CarType carType = cartype.get(0);
        String type = carType.getName();
//
        packageRules = PackageRuleController.getPackageRulesCode(inquiryParam.getProduct_offering_code());
        PackageRule pkt_rule = packageRules.get(0);
        Name_package = pkt_rule.getPackageName();

        tvModel.setText(name);
        tvOtr.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(inquiryParam.getOtr())));
        tvPaket.setText(Name_package);
        tvTenor.setText(inquiryParam.getTenor()+"x");
//        tvRate.setText(inquiryParam.getEffective_rate()+"% (Flat Rate)");
        tvRate.setText(inquiryParam.getEffective_rate()+"%");
        tvAngsuran.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(inquiryParam.getInstallment_amount())));
        tvDp.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(inquiryParam.getDp_nett())));
        tvTdp.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(inquiryParam.getTdp())));
        tvTotalHutang.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(inquiryParam.getTotal_ar())));
        tvPVPremi.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(String.valueOf(newSum))));

        pvPremi = String.valueOf(newSum);

        String pclPremi = "0";
        if( inquiryParam.getLife_insurance() != null){
            pclPremi = inquiryParam.getLife_insurance().getInsurance_premi();
        }

        tvPCLPremi.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(pclPremi)));
        tvAdminFee.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(adminfee)));
        tvType.setText(type);
        if (inquiryParam.getFirst_installment_type().equals("AD")){
            tvFirstInstallment.setText("ADDM");
        }else {
            tvFirstInstallment.setText("ADDB");
        }
//        lnTotal.setVisibility(View.GONE);
//        ln_total2.setVisibility(View.GONE);
    }

    private void sendEmail(String email){
        final ProgressDialog barProgressDialog = new ProgressDialog(HasilPerhitungandraft.this);
        barProgressDialog.setTitle("Please wait");
        barProgressDialog.setMessage("Sending email...");
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_SPINNER);
        barProgressDialog.setCancelable(false);
        barProgressDialog.show();

        EmailParam emailParam = new EmailParam();
        emailParam.setEmail(email);
        emailParam.setBrand(brand);
        emailParam.setModel(name);

        if (userSession.getUser().getType().equals("so")) {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getBranchName() != null) {
                    emailParam.setBranch(userSession.getUser().getResponseConfins().getBranchName());
                }
            }
        } else {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getDealerName() != null) {
                    txtDealerName.setText(userSession.getUser().getResponseConfins().getDealerName());
                    emailParam.setBranch(userSession.getUser().getResponseConfins().getDealerName());
                }
            }
        }
        emailParam.setPaket(Name_package);
        emailParam.setOtr(inquiryParam.getOtr());

        String firstInstallment = inquiryParam.getFirst_installment_type();
        if(firstInstallment != null){
            if(firstInstallment.equals("AD")){
                emailParam.setFirst_instalment("ADDM");
            }else{
                emailParam.setFirst_instalment("ADDB");
            }
        }

        emailParam.setDp_nett(inquiryParam.getDp_nett());
        emailParam.setFlat_rate(inquiryParam.getEffective_rate());

        HashMap<String,String> otherFee = inquiryParam.getOther_fee().get(0);
        emailParam.setAdmin_fee(otherFee.get("admin_fee"));

        emailParam.setTdp(inquiryParam.getTdp());
        emailParam.setAngsuran(inquiryParam.getInstallment_amount());
        emailParam.setTenor(inquiryParam.getTenor());

        LifeInsurance lifeInsurance = inquiryParam.getLife_insurance();
        if (userSession.getUser().getType().equals("so")) {
            emailParam.setPcl(inquiryParam.getLife_insurance().getInsurance_premi());
            emailParam.setPcl_name(inquiryParam.getLife_insurance().getInsurance_name());
            emailParam.setPv_premi(inquiryParam.getInsurance().getInsurance_name());
        }else {
            if(lifeInsurance != null){
                emailParam.setPcl(inquiryParam.getLife_insurance().getInsurance_premi());
                emailParam.setPcl_name(userSession.getUser().getDealer().getPcl_insco().getName());
            }else{
                emailParam.setPcl("0");
                emailParam.setPcl_name("-");
            }
            emailParam.setPv_premi(userSession.getUser().getDealer().getPv_insco().getName());
        }
        emailParam.setTotal_hutang(inquiryParam.getTotal_ar());

        emailParam.setPv_list(inquiryParam.getInsurance().getInsurance_list());
        emailParam.setPv_total(pvPremi);

        Draft draft = Draft.findById(Draft.class, id);
        if (draft.getPokokHutangAkhir() !=null){
            Log.d("getPokokHutangAkhir", draft.getPokokHutangAkhir());
            emailParam.setPokok_hutang(draft.getPokokHutangAkhir());
        }else {
            if (inquiryParam.getInsurance().getInsurance_list().get(0).getPayment_type().equals("FO")){
                int premi = Integer.parseInt(HasilPerhitungandraft.pvPremi);
                int jmlOtr = Integer.parseInt(inquiryParam.getOtr())-Integer.parseInt(inquiryParam.getDp_nett());
                int hutang_akhir = jmlOtr + premi;
                emailParam.setPokok_hutang(String.valueOf(hutang_akhir));
            }else {
                int hutang_akhir = Integer.parseInt(inquiryParam.getOtr())-Integer.parseInt(inquiryParam.getDp_nett());
                emailParam.setPokok_hutang(String.valueOf(hutang_akhir));
            }
        }

        if (inquiryParam.getLife_insurance().getPayment_type().equals("FO")){
            emailParam.setPv_type("ONLOAN");
        }else {
            emailParam.setPv_type("PREPAID");
        }

            long otr = Long.parseLong(inquiryParam.getOtr());
            long amount = Long.parseLong(inquiryParam.getDp_nett());
            double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
            emailParam.setDp_precentage(KalkulatorUtility.setRoundDecimalPlace(percentage)+"");


        String param = JSONProcessor.toJSON(emailParam);
        SendEmailPresenter.sendEmail(HasilPerhitungandraft.this, new SendEmailCallback() {
            @Override
            public void onSuccessSendEmail(String message) {
                barProgressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(HasilPerhitungandraft.this)
                        .setTitle("Email Sukses Terkirim")
                        .setMessage("Hasil Simulasi telah dikirimkan ke email tersebut.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }

            @Override
            public void onFailedSendEmail(String message) {
                barProgressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(HasilPerhitungandraft.this)
                        .setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        },param);
    }

    @Override
    public void onBackPressed() {
        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
        PopupUtils.backWithDraftOption(HasilPerhitungandraft.this, rootView, 5, new PopupUtils.SaveToDraftListener2() {
            @Override
            public void isSaved(boolean isSaved) {
                progressDialog = DialogUtility.createProgressDialog(HasilPerhitungandraft.this, "", "Please wait...");
                progressDialog.show();
                final String param = JSONProcessor.toJSON(inquiryParam);
//        SubmitParameters.inquiryParam = inquiryParam;
                LogUtility.logging(TAG, LogUtility.infoLog, "SendDraft", "param", param);


                SubmitPresenter.draft(HasilPerhitungandraft.this, new DraftCallback() {
                    @Override
                    public void onSuccessDraft(int formId, final String date) {
                        progressDialog.dismiss();
                        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                        PopupUtils.simpanKeDraftSaved(HasilPerhitungandraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                            @Override
                            public void isSaved(boolean isSaved) {
                                if (!isSaved) {
                                    Intent intent = new Intent();
                                    intent.putExtra("is_draft", true);
                                    setResult(Activity.RESULT_OK, intent);
                                    Draft drafts = Draft.findById(Draft.class, id);
                                    drafts.setData(param);
                                    drafts.setLastSaved(date);
                                    drafts.setNamaCustomer(draft.getNamaCustomer());
                                    drafts.setIs_simulasi_budget(draft.getIs_simulasi_budget());
                                    drafts.setIs_simulasi_paket(draft.getIs_simulasi_paket());
                                    drafts.setIsNonPaket(draft.getIsNonPaket());
                                    drafts.setIs_npwp(draft.getIs_npwp());
                                    drafts.setIs_corporate(draft.getIs_corporate());
                                    drafts.setCoverage_type(draft.getCoverage_type());
                                    drafts.setPayment_type_service_package(draft.getPayment_type_service_package());
                                    drafts.save();
                                    finish();
                                } else {
                                    setResult(Activity.RESULT_OK);
                                    Draft drafts = Draft.findById(Draft.class, id);
                                    drafts.setData(param);
                                    drafts.setLastSaved(date);
                                    drafts.setNamaCustomer(draft.getNamaCustomer());
                                    drafts.setIs_simulasi_budget(draft.getIs_simulasi_budget());
                                    drafts.setIs_simulasi_paket(draft.getIs_simulasi_paket());
                                    drafts.setIsNonPaket(draft.getIsNonPaket());
                                    drafts.setIs_npwp(draft.getIs_npwp());
                                    drafts.setIs_corporate(draft.getIs_corporate());
                                    drafts.setCoverage_type(draft.getCoverage_type());
                                    drafts.setPayment_type_service_package(draft.getPayment_type_service_package());
                                    drafts.save();
                                    finish();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailedDraft(String message) {
                        progressDialog.dismiss();
//                Toast.makeText(HasilPerhitungandraft.this,"Failed save Draft", Toast.LENGTH_SHORT).show();
                        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                        PopupUtils.simpanKeDraftSaved(HasilPerhitungandraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                            @Override
                            public void isSaved(boolean isSaved) {
                                setResult(Activity.RESULT_OK);
                                Draft drafts = Draft.findById(Draft.class, id);
                                drafts.setData(param);
                                drafts.setNamaCustomer(draft.getNamaCustomer());
                                drafts.setIs_simulasi_budget(draft.getIs_simulasi_budget());
                                drafts.setIs_simulasi_paket(draft.getIs_simulasi_paket());
                                drafts.setIsNonPaket(draft.getIsNonPaket());
                                drafts.setIs_npwp(draft.getIs_npwp());
                                drafts.setIs_corporate(draft.getIs_corporate());
                                drafts.setCoverage_type(draft.getCoverage_type());
                                drafts.setPayment_type_service_package(draft.getPayment_type_service_package());
                                drafts.save();
                                finish();
                            }
                        });
                    }
                }, "draft",param, user_id, "", SelectedData.DpPercentage, draft.getIs_corporate(), draft.getIs_simulasi_paket(),
                        draft.getIs_simulasi_budget(), draft.getIs_non_paket(), draft.getIs_npwp(), draft.getPayment_type_service_package(),
                        draft.getCoverage_type());
            }


            @Override
            public void isExit(boolean isExit) {
                //Log.d("exit", "exit");
                finish();
            }
        });
    }

    private void ulangi_perhitungan(){
        progressDialog.show();

        carModels = CarController.getCarModels(inquiryParam.getModel());
        CarModel carModel = carModels.get(0);
        SelectedData.selectedCarModel = carModel;
//                SelectedData.CoverageType = spinCovInsurance.getSelectedItemPosition();
        SelectedData.CategoryGroupId = carModel.getCategoryGroupId();

        SelectedData.SelectedPackage = tvPaket.getText().toString();

        long otr = Long.parseLong(inquiryParam.getOtr());
        long amount = Long.parseLong(inquiryParam.getDp_nett());
        double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
        SelectedData.DpPercentage = percentage+"";

        SelectedData.Dp = inquiryParam.getDp_nett();
        SelectedData.BaseDP = inquiryParam.getDp_nett();
        SelectedData.Rate = inquiryParam.getEffective_rate();
        SelectedData.JenisAngsuran = tvFirstInstallment.getText().toString();


        if (draft.getPayment_type_service_package() != null) {
            if (draft.getPayment_type_service_package().equals("PREPAID")) {
                SelectedData.Otr = inquiryParam.getOtr();
            } else if (draft.getPayment_type_service_package().equals("ONLOAN")){
                int OTR = Integer.parseInt(inquiryParam.getOtr());
                int amount_srv_pkg = Integer.parseInt(inquiryParam.getPackage_service_price());
                int total = OTR - amount_srv_pkg;
                SelectedData.Otr = total+"";
                SelectedData.JenisAngsuran = tvFirstInstallment.getText().toString();
            }else {
                SelectedData.Otr = inquiryParam.getOtr();
                SelectedData.JenisAngsuran = tvFirstInstallment.getText().toString();
            }
        }else {
            SelectedData.Otr = inquiryParam.getOtr();
            SelectedData.JenisAngsuran = tvFirstInstallment.getText().toString();
        }

        Intent intent = new Intent(getApplicationContext(), DraftinputItemKredit.class);
        intent.putExtra("data", (Serializable) inquiryParam);
        intent.putExtra("id", id);
        intent.putExtra("user_id", user_id);
        intent.putExtra("draft", (Serializable) draft);
        startActivityForResult(intent, REQUEST_CODE_MIN_CUS_DATA);
        progressDialog.dismiss();
        finish();
    }

}
