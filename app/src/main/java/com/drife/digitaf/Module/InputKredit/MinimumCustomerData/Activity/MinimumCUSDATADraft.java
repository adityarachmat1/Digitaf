package com.drife.digitaf.Module.InputKredit.MinimumCustomerData.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.Dialog.PopupDraft.PopupUtils;
import com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity.HasilPerhitungandraft;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.myapplication.adapter.DraftListAdapter;
import com.drife.digitaf.Module.myapplication.model.DraftItem;
import com.drife.digitaf.ORM.Controller.PackageRuleController;
import com.drife.digitaf.ORM.Controller.WayOfPaymentController;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.ORM.Database.PackageRule;
import com.drife.digitaf.ORM.Database.WayOfPayment;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Submit.DraftCallback;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.SubmitParam.AdditionalCoverage;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.Insurance;
import com.drife.digitaf.retrofitmodule.SubmitParam.InsuranceList;
import com.drife.digitaf.retrofitmodule.SubmitParam.LifeInsurance;
import com.drife.digitaf.retrofitmodule.SubmitParam.SubmitParameters;
import com.google.android.gms.vision.L;
import com.orm.SugarContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ferdinandprasetyo on 10/3/18.
 */

public class MinimumCUSDATADraft extends AppCompatActivity implements View.OnClickListener {
    private String TAG = MinimumCUSDATADraft.class.getSimpleName();
    private LinearLayout btnLanjutkan;
    private RadioButton rbKaryawan, rbProfessional, rbWiraswasta, rbCorporate;
    private Spinner spinnerCaraPembayaran;
    private InquiryParam inquiryParams;
    private ProgressDialog progressDialog;
    public static Long id_ = Long.valueOf(0);
    public static String user_id_ = "";
    List<PackageRule> packageRules = new ArrayList<>();

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.edNama)
    EditText edNama;
    @BindView(R.id.edNoHp)
    EditText edNoHp;
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edReferensi)
    EditText edReferensi;
    @BindView(R.id.edNoHpReferensi)
    EditText edNoHpReferensi;
    @BindView(R.id.switchNPWP)
    Switch switchNpwp;
    @BindView(R.id.lyVIP)
    RelativeLayout lyVIP;
    @BindView(R.id.switchVIP)
    Switch switchVIP;
    @BindView(R.id.btnDraft)
    LinearLayout btnDraft;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtCabang)
    TextView txtCabang;

    int REQUEST_CODE_UPLOAD = 2;
    Long id;
    String user_id;
    private List<String> caraPembayaran = new ArrayList<>();
    private List<WayOfPayment> wayOfPayments = new ArrayList<>();
    private Draft drafts;

    private UserSession userSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_activity_minimumcustomerdata);

        inquiryParams = (InquiryParam) getIntent().getSerializableExtra("data");
        id = getIntent().getLongExtra("id",1);
        user_id = getIntent().getStringExtra("user_id");
        drafts = (Draft) getIntent().getSerializableExtra("draft");

        ButterKnife.bind(this);
//        SugarContext.init(MinimumCUSDATADraft.this);
        initLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SugarContext.terminate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_UPLOAD){
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

    private void initLayout() {
        btnDraft = findViewById(R.id.btnDraft);
        btnLanjutkan = findViewById(R.id.btnLanjutkan);
        rbKaryawan = findViewById(R.id.rbKaryawan);
        rbProfessional = findViewById(R.id.rbProfessional);
        rbWiraswasta = findViewById(R.id.rbWiraswasta);
        rbCorporate = findViewById(R.id.rbCorporate);
        spinnerCaraPembayaran = findViewById(R.id.spinnerCaraPembayaran);

        edNama.setText(inquiryParams.getCustomer_name());
        edEmail.setText(inquiryParams.getEmail());
        edNoHp.setText(inquiryParams.getMobile_phone());

        edReferensi.setText(inquiryParams.getReference_name());
        edNoHpReferensi.setText(inquiryParams.getReference_phone());

        rbKaryawan.setOnClickListener(this);
        rbProfessional.setOnClickListener(this);
        rbWiraswasta.setOnClickListener(this);
        rbCorporate.setOnClickListener(this);
        btnDraft.setOnClickListener(this);
        btnLanjutkan.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        userSession = SharedPreferenceUtils.getUserSession(this);

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

        if (userSession.getUser().getType().equals("so")) {
            lyVIP.setVisibility(View.VISIBLE);
            if (inquiryParams.getIs_vip() !=null){
                if (inquiryParams.getIs_vip().equals("Y")){
                    switchVIP.setChecked(true);
                }else {
                    switchVIP.setChecked(false);
                }
            }
        }else {
            switchVIP.setChecked(false);
        }

        setSpinnerCaraPembayaran();
        if (inquiryParams.getCustomer_model() !=null){
            if (inquiryParams.getCustomer_model().equals("EMP")){
                rbKaryawan.setChecked(true);
            }else if (inquiryParams.getCustomer_model().equals("PROF")){
                rbProfessional.setChecked(true);
            }else if (inquiryParams.getCustomer_model().equals("SME")){
                rbWiraswasta.setChecked(true);
            }else if (inquiryParams.getCustomer_model().equals("SMEBU")){
                rbCorporate.setChecked(true);
            }else {
                rbKaryawan.setChecked(true);
            }
        }else {
            rbKaryawan.setChecked(true);
        }

        Draft draft = Draft.findById(Draft.class, id);
//        if (draft.getNpwp() !=null){
//            if (draft.getNpwp()){
//                switchNpwp.setChecked(true);
//            }else {
//                switchNpwp.setChecked(false);
//            }
//        }
        if (drafts.getIs_npwp() !=null) {
            if (drafts.getIs_npwp().equals("1")) {
                switchNpwp.setChecked(true);
            } else if (drafts.getIs_npwp().equals("0")){
                switchNpwp.setChecked(false);
            }
        }

        if (drafts.getIs_corporate() !=null) {
            if (drafts.getIs_corporate().equals("0")) {
                rbCorporate.setChecked(false);
            } else if (drafts.getIs_corporate().equals("1")) {
                rbCorporate.setChecked(true);
            }
        }

        long otr = Long.parseLong(inquiryParams.getOtr());
        long amount = Long.parseLong(inquiryParams.getDp_nett());
        double percentage = KalkulatorUtility.countPercentageLong(otr,amount);
        SelectedData.DpPercentage = KalkulatorUtility.setRoundDecimalPlace(percentage)+"";

        if (draft.getIsNonPaket() !=null){
            SelectedData.isNonPaket = draft.getIsNonPaket();
        }else {

            packageRules = PackageRuleController.getPackageRulesCode(inquiryParams.getProduct_offering_code());
            PackageRule pkt_rule = packageRules.get(0);
            String Name_package = pkt_rule.getPackageName();

            if (Name_package.equals("RCFCV NON PAKET")){
                SelectedData.isNonPaket = "1";
            }else {
                SelectedData.isNonPaket = "0";
            }
        }

        if (inquiryParams.getInsurance().getInsurance_list().get(0).getPayment_type().equals("FO")){
            SelectedData.PaymentType = 1;
        }else {
            SelectedData.PaymentType = 0;
        }

        if (draft.getPokokHutangAkhir() !=null){
            SelectedData.PokokHutangAkhir = draft.getPokokHutangAkhir();
        }else {
            if (inquiryParams.getInsurance().getInsurance_list().get(0).getPayment_type().equals("FO")){
                int premi = Integer.parseInt(HasilPerhitungandraft.pvPremi);
                int jmlOtr = Integer.parseInt(inquiryParams.getOtr())-Integer.parseInt(inquiryParams.getDp_nett());
                int hutang_akhir = jmlOtr + premi;

                SelectedData.PokokHutangAkhir = String.valueOf(hutang_akhir);
            }else {
                int hutang_akhir = Integer.parseInt(inquiryParams.getOtr())-Integer.parseInt(inquiryParams.getDp_nett());
                SelectedData.PokokHutangAkhir = String.valueOf(hutang_akhir);
            }
        }

        if (draft.getOtr() !=null){
            SelectedData.Otr = draft.getOtr();
        }else {
            SelectedData.Otr = inquiryParams.getOtr();
        }

        if (draft.getPvPremiAmount() !=null){
            SelectedData.pvPremiAmount = draft.getPvPremiAmount();
        }else {
            if (inquiryParams.getInsurance().getInsurance_list().get(0).getPayment_type().equals("FO")){
                SelectedData.pvPremiAmount = HasilPerhitungandraft.pvPremi;
            }else {
                SelectedData.pvPremiAmount = "0";
            }
        }

        Log.d("SelectedData = ", SelectedData.isNonPaket + ", " + SelectedData.PaymentType + ", "
                + SelectedData.Otr + ", "+ SelectedData.PokokHutangAkhir + ", " + SelectedData.pvPremiAmount);

        switchNpwp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SelectedData.isNPWP = true;
                }else{
                    SelectedData.isNPWP = false;
                }
            }
        });

        switchVIP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SelectedData.isVIP = true;
                }else{
                    SelectedData.isVIP = false;
                }
            }
        });

        btnDraft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDraft:
                if (validasi()) {
                    if(TextUtility.isEmailFormat(edEmail.getText().toString())){
                        progressDialog = DialogUtility.createProgressDialog(MinimumCUSDATADraft.this,"","Please wait...");
                        if(!progressDialog.isShowing()){
                            progressDialog.show();
                        }
                        saveTemporaryParams();
                        final String param = JSONProcessor.toJSON(inquiryParams);
                        Log.d("Document", String.valueOf(inquiryParams.getDocument()));
                        Log.d("paramDraft", param);
                        SubmitPresenter.draft(MinimumCUSDATADraft.this, new DraftCallback() {
                            @Override
                            public void onSuccessDraft(int formId, String date) {
                                progressDialog.dismiss();
                                Log.d("date", date);
                                Draft draft = Draft.findById(Draft.class, id);
                                draft.setData(param);
                                draft.setNamaCustomer(inquiryParams.getCustomer_name());
                                draft.setLastSaved(date);
                                draft.setNpwp(SelectedData.isNPWP);
                                draft.setIsNonPaket(SelectedData.isNonPaket);
                                draft.setPaymentType(SelectedData.PaymentType);
                                draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                draft.setOtr(SelectedData.Otr);
                                draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                draft.setUser_id(String.valueOf(formId));
                                draft.setPayment_type_service_package(drafts.getPayment_type_service_package());
                                draft.setCoverage_type(drafts.getCoverage_type());
                                draft.setIs_npwp(drafts.getIs_npwp());
                                draft.setIs_corporate(drafts.getIs_corporate());

                                draft.save();
                                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                                PopupUtils.simpanKeDraftSaved(MinimumCUSDATADraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                                    @Override
                                    public void isSaved(boolean isSaved) {
                                        if (!isSaved) {
                                            Intent intent = new Intent();
                                            intent.putExtra("is_draft", true);
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        } else {
                                            setResult(Activity.RESULT_OK);
                                            finish();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailedDraft(String message) {
                                progressDialog.dismiss();
//                                Toast.makeText(MinimumCUSDATADraft.this,"Failed Draft", Toast.LENGTH_SHORT).show();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
                                String currentDateandTime = sdf.format(new Date());
                                Log.d("date", currentDateandTime);
                                Draft draft = Draft.findById(Draft.class, id);
                                draft.setData(param);
                                draft.setNamaCustomer(inquiryParams.getCustomer_name());
                                draft.setLastSaved(currentDateandTime);
                                draft.setNpwp(SelectedData.isNPWP);
                                draft.setIsNonPaket(SelectedData.isNonPaket);
                                draft.setPaymentType(SelectedData.PaymentType);
                                draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                draft.setOtr(SelectedData.Otr);
                                draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                draft.setPayment_type_service_package(drafts.getPayment_type_service_package());
                                draft.setCoverage_type(drafts.getCoverage_type());
                                draft.setIs_npwp(drafts.getIs_npwp());
                                draft.setIs_corporate(drafts.getIs_corporate());
                                draft.save();
                                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                                PopupUtils.simpanKeDraftSaved(MinimumCUSDATADraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                                    @Override
                                    public void isSaved(boolean isSaved) {
                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    }
                                });

                            }
                        },"draft",param, user_id, "", SelectedData.DpPercentage, drafts.getIs_corporate(), drafts.getIs_simulasi_paket(),
                                drafts.getIs_simulasi_budget(), drafts.getIs_non_paket(), drafts.getIs_npwp(), drafts.getPayment_type_service_package(),
                                drafts.getCoverage_type());

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCUSDATADraft.this)
                                .setMessage("Email address is not valid")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.show();
                    }
                }
                break;
            case R.id.btnLanjutkan:
                if(validasi()){
                    if(TextUtility.isEmailFormat(edEmail.getText().toString())){
                        if(rbCorporate.isChecked()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCUSDATADraft.this)
                                    .setMessage("Untuk tipe Customer Corporate (Badan Usaha/Perusahaan), " +
                                            "harap memberikan dokumen pendukung ke Sales Officer TAF.")
                                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCUSDATADraft.this)
                                                    .setMessage("Untuk saat ini submit belum bisa dilakukan, dikarenakan masih dalam pengembangan. Thanks")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.dismiss();
                                                        }
                                                    })
                                                    ;
                                            builder.show();
                                        }
                                    })
                                    .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    ;
                            builder.show();
                        }else {
                            saveTemporaryParamsSend();
                            Intent intent = new Intent(this, UploadDocumentActivity.class);
                            id_ = id;
                            user_id_ = user_id;
                            SelectedData.wop = wayOfPayments.get(spinnerCaraPembayaran.getSelectedItemPosition()).getName();
                            Log.d("user_id_", user_id_);
                            startActivityForResult(intent,REQUEST_CODE_UPLOAD);
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCUSDATADraft.this)
                                .setMessage("Email address is not valid")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.show();
                    }
                }
                break;
            case R.id.rbKaryawan:
                setupRadioSelected(rbKaryawan);
                break;
            case R.id.rbProfessional:
                setupRadioSelected(rbProfessional);
                break;
            case R.id.rbWiraswasta:
                setupRadioSelected(rbWiraswasta);
                break;
            case R.id.rbCorporate:
                setupRadioSelected(rbCorporate);
                break;
            case R.id.imgBack:
                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                PopupUtils.backWithDraftOption(MinimumCUSDATADraft.this, rootView, 5, new PopupUtils.SaveToDraftListener2() {
                    @Override
                    public void isSaved(boolean isSaved) {
                        if (validasi()) {
                            if (TextUtility.isEmailFormat(edEmail.getText().toString())) {
                                progressDialog = DialogUtility.createProgressDialog(MinimumCUSDATADraft.this, "", "Please wait...");
                                if (!progressDialog.isShowing()) {
                                    progressDialog.show();
                                }
                                saveTemporaryParams();
                                final String param = JSONProcessor.toJSON(inquiryParams);
                                Log.d("Document", String.valueOf(inquiryParams.getDocument()));
                                Log.d("paramDraft", param);
                                SubmitPresenter.draft(MinimumCUSDATADraft.this, new DraftCallback() {
                                    @Override
                                    public void onSuccessDraft(int formId, String date) {
                                        progressDialog.dismiss();
                                        Log.d("date", date);
                                        Draft draft = Draft.findById(Draft.class, id);
                                        draft.setData(param);
                                        draft.setNamaCustomer(inquiryParams.getCustomer_name());
                                        draft.setLastSaved(date);
                                        draft.setNpwp(SelectedData.isNPWP);
                                        draft.setIsNonPaket(SelectedData.isNonPaket);
                                        draft.setPaymentType(SelectedData.PaymentType);
                                        draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                        draft.setOtr(SelectedData.Otr);
                                        draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                        draft.setUser_id(String.valueOf(formId));
                                        draft.setPayment_type_service_package(drafts.getPayment_type_service_package());
                                        draft.setCoverage_type(drafts.getCoverage_type());
                                        draft.setIs_npwp(drafts.getIs_npwp());
                                        draft.setIs_corporate(drafts.getIs_corporate());

                                        draft.save();
                                        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                                        PopupUtils.simpanKeDraftSaved(MinimumCUSDATADraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                                            @Override
                                            public void isSaved(boolean isSaved) {

                                                setResult(Activity.RESULT_OK);
                                                finish();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailedDraft(String message) {
                                        progressDialog.dismiss();
//                                Toast.makeText(MinimumCUSDATADraft.this,"Failed Draft", Toast.LENGTH_SHORT).show();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
                                        String currentDateandTime = sdf.format(new Date());
                                        Log.d("date", currentDateandTime);
                                        Draft draft = Draft.findById(Draft.class, id);
                                        draft.setData(param);
                                        draft.setNamaCustomer(inquiryParams.getCustomer_name());
                                        draft.setLastSaved(currentDateandTime);
                                        draft.setNpwp(SelectedData.isNPWP);
                                        draft.setIsNonPaket(SelectedData.isNonPaket);
                                        draft.setPaymentType(SelectedData.PaymentType);
                                        draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                        draft.setOtr(SelectedData.Otr);
                                        draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                        draft.setPayment_type_service_package(drafts.getPayment_type_service_package());
                                        draft.setCoverage_type(drafts.getCoverage_type());
                                        draft.setIs_npwp(drafts.getIs_npwp());
                                        draft.setIs_corporate(drafts.getIs_corporate());
                                        draft.save();
                                        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                                        PopupUtils.simpanKeDraftSaved(MinimumCUSDATADraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                                            @Override
                                            public void isSaved(boolean isSaved) {
                                                setResult(Activity.RESULT_OK);
                                                finish();
                                            }
                                        });

                                    }
                                }, "draft",param, user_id, "", SelectedData.DpPercentage, drafts.getIs_corporate(), drafts.getIs_simulasi_paket(),
                                        drafts.getIs_simulasi_budget(), drafts.getIs_non_paket(), drafts.getIs_npwp(), drafts.getPayment_type_service_package(),
                                        drafts.getCoverage_type());

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCUSDATADraft.this)
                                        .setMessage("Email address is not valid")
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

                    @Override
                    public void isExit(boolean isExit) {
                        //Log.d("exit", "exit");
                        finish();
                    }
                });
                break;
        }
    }

    private void setupRadioSelected(RadioButton rbSelected) {
        RadioButton[] radioButtons = new RadioButton[]{rbKaryawan, rbProfessional, rbWiraswasta, rbCorporate};

        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i] == rbSelected) {
                radioButtons[i].setChecked(true);
            } else {
                radioButtons[i].setChecked(false);
            }
        }
    }

    private void setSpinnerCaraPembayaran(){
        wayOfPayments = WayOfPaymentController.getAllWOP();
        String nama = "";

        try {
            if(wayOfPayments != null){
                TextUtility.sortWOP(wayOfPayments);
            }
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.warningLog,"setSpinnerCaraPembayaran",e.getMessage());
        }

        if(wayOfPayments != null){
            for (int i=0; i<wayOfPayments.size(); i++){
                WayOfPayment wayOfPayment = wayOfPayments.get(i);
                String name = wayOfPayment.getName();
                if (wayOfPayments.get(i).getCode().equals(inquiryParams.getWop_code())){
                    nama = wayOfPayment.getName();
                }
                caraPembayaran.add(name);
            }
        }

        SpinnerUtility.setSpinnerItemDraft(getApplicationContext(),spinnerCaraPembayaran,caraPembayaran, nama);
    }

    private void saveTemporaryParams(){
//        inquiryParam = SubmitParameters.inquiryParam;
        inquiryParams.setCustomer_name(edNama.getText().toString());
        inquiryParams.setReference_name(edReferensi.getText().toString());
        inquiryParams.setReference_phone(edNoHpReferensi.getText().toString());
        inquiryParams.setMobile_phone(edNoHp.getText().toString());
        inquiryParams.setEmail(edEmail.getText().toString());
        if(rbKaryawan.isChecked()){
            inquiryParams.setCustomer_model("EMP");
        }else if(rbProfessional.isChecked()){
            inquiryParams.setCustomer_model("PROF");
        }else if(rbWiraswasta.isChecked()){
            inquiryParams.setCustomer_model("SME");
        }else if(rbCorporate.isChecked()){
            inquiryParams.setCustomer_model("SMEBU");
        }else{
            inquiryParams.setCustomer_model("EMP");
        }
        inquiryParams.setMother_maiden_name("");

        /*wop_code*/
        String wop = "";
        if(wayOfPayments != null){
            wop = wayOfPayments.get(spinnerCaraPembayaran.getSelectedItemPosition()).getCode();
        }
        inquiryParams.setWop_code(wop);
        if (userSession.getUser().getType().equals("so")) {
            if (switchVIP.isChecked()){
                inquiryParams.setIs_vip("Y");
            }else {
                inquiryParams.setIs_vip("N");
            }
        }else {
            inquiryParams.setIs_vip("N");
        }

        String param = JSONProcessor.toJSON(inquiryParams);
        Log.d("paramMinumin", param);

        if(rbKaryawan.isChecked()){
            SelectedData.customerType = 1;
        }else if(rbProfessional.isChecked()){
            SelectedData.customerType = 2;
        }else if(rbWiraswasta.isChecked()){
            SelectedData.customerType = 3;
        }else if(rbCorporate.isChecked()){
            SelectedData.customerType = 4;
        }

        if (switchNpwp.isChecked()){
            drafts.setIs_npwp("1");
        }else {
            drafts.setIs_npwp("0");
        }

        if (rbCorporate.isChecked()){
            drafts.setIs_corporate("1");
        }else {
            drafts.setIs_corporate("0");
        }

        String draft = JSONProcessor.toJSON(drafts);
        Log.d("draft", draft);

        LogUtility.logging(TAG, LogUtility.infoLog,"saveTemporaryParams","param",param);
    }

    private void saveTemporaryParamsSend(){
        InquiryParam inquiryParam = SubmitParameters.inquiryParam;
        inquiryParam.setCustomer_name(edNama.getText().toString());
        inquiryParam.setReference_name(edReferensi.getText().toString());
        inquiryParam.setReference_phone(edNoHpReferensi.getText().toString());
        inquiryParam.setMobile_phone(edNoHp.getText().toString());
        inquiryParam.setEmail(edEmail.getText().toString());
        if(rbKaryawan.isChecked()){
            inquiryParam.setCustomer_model("EMP");
        }else if(rbProfessional.isChecked()){
            inquiryParam.setCustomer_model("PROF");
        }else if(rbWiraswasta.isChecked()){
            inquiryParam.setCustomer_model("SME");
        }else if(rbCorporate.isChecked()){
            inquiryParam.setCustomer_model("SMEBU");
        }else{
            inquiryParam.setCustomer_model("EMP");
        }
        inquiryParam.setMother_maiden_name("");

        /*wop_code*/
        String wop = "";
        if(wayOfPayments != null){
            wop = wayOfPayments.get(spinnerCaraPembayaran.getSelectedItemPosition()).getCode();
        }
        inquiryParam.setWop_code(wop);

        if (userSession.getUser().getType().equals("so")) {
            if (switchVIP.isChecked()){
                inquiryParam.setIs_vip("Y");
            }else {
                inquiryParam.setIs_vip("N");
            }
        }else {
            inquiryParams.setIs_vip("N");
        }

        inquiryParam.setFlag(inquiryParams.getFlag());
        inquiryParam.setMerk(inquiryParams.getMerk());
        inquiryParam.setModel(inquiryParams.getModel());
        inquiryParam.setType(inquiryParams.getType());
        inquiryParam.setSuppl_code(inquiryParams.getSuppl_code());
        inquiryParam.setSuppl_branch(inquiryParams.getSuppl_branch());
        inquiryParam.setOtr(inquiryParams.getOtr());
        inquiryParam.setDp_nett(inquiryParams.getDp_nett());
        inquiryParam.setTdp(inquiryParams.getTdp());
        inquiryParam.setAsset_usage(inquiryParams.getAsset_usage());
        inquiryParam.setProvision_fee(inquiryParams.getProvision_fee()+"");
        inquiryParam.setProvision_payment_type(inquiryParams.getProvision_payment_type());
        inquiryParam.setFiducia_fee(inquiryParams.getFiducia_fee());
        inquiryParam.setTenor(inquiryParams.getTenor());
        inquiryParam.setFirst_installment_type(inquiryParams.getFirst_installment_type());
        inquiryParam.setEffective_rate(inquiryParams.getEffective_rate());
        inquiryParam.setInstallment_amount(inquiryParams.getInstallment_amount()+"");
        inquiryParam.setTotal_ar(inquiryParams.getTotal_ar()+"");
        inquiryParam.setSubsidy_amt(inquiryParams.getSubsidy_amt());
        inquiryParam.setProduct_offering_code(inquiryParams.getProduct_offering_code());
        inquiryParam.setPackage_service_price(inquiryParams.getPackage_service_price());

        Insurance insurance = new Insurance();

        List<InsuranceList> insuranceLists = new ArrayList<>();
        for (int i=0; i<inquiryParams.getInsurance().getInsurance_list().size(); i++){
            InsuranceList insuranceList = new InsuranceList();
            insuranceList.setYear(inquiryParams.getInsurance().getInsurance_list().get(i).getYear());
            insuranceList.setCoverage(inquiryParams.getInsurance().getInsurance_list().get(i).getCoverage());
            insuranceList.setMainpremi(inquiryParams.getInsurance().getInsurance_list().get(i).getMainpremi());
            insuranceList.setPayment_type(inquiryParams.getInsurance().getInsurance_list().get(i).getPayment_type());

            List<AdditionalCoverage> additionalCoverages = new ArrayList<>();
            for (int j=0; j<inquiryParams.getInsurance().getInsurance_list().get(i).getAditional_coverage().size(); j++){
                AdditionalCoverage adc = new AdditionalCoverage();
                adc.setNilai_premi(inquiryParams.getInsurance().getInsurance_list().get(i).getAditional_coverage().get(j).getNilai_premi());
                adc.setType(inquiryParams.getInsurance().getInsurance_list().get(i).getAditional_coverage().get(j).getType());
                additionalCoverages.add(adc);
            }
            insuranceList.setAditional_coverage(additionalCoverages);
            insuranceLists.add(insuranceList);
        }

        insurance.setInsurance_list(insuranceLists);
        insurance.setInsurance_fee(inquiryParams.getInsurance().getInsurance_fee());
        insurance.setInsurance_name(inquiryParams.getInsurance().getInsurance_name());
        inquiryParam.setInsurance(insurance);

        if(inquiryParams.getLife_insurance() != null){
            LifeInsurance lifeInsurance = new LifeInsurance();
            lifeInsurance.setInsurance_name(inquiryParams.getLife_insurance().getInsurance_name());
            lifeInsurance.setInsurance_premi(inquiryParams.getLife_insurance().getInsurance_premi());
            lifeInsurance.setPayment_type(inquiryParams.getLife_insurance().getPayment_type());
            inquiryParam.setLife_insurance(lifeInsurance);
        }

        inquiryParam.setKtp_salesman(inquiryParams.getKtp_salesman());

        inquiryParam.setOther_fee(inquiryParams.getOther_fee());

        if(rbKaryawan.isChecked()){
            SelectedData.customerType = 1;
        }else if(rbProfessional.isChecked()){
            SelectedData.customerType = 2;
        }else if(rbWiraswasta.isChecked()){
            SelectedData.customerType = 3;
        }else if(rbCorporate.isChecked()){
            SelectedData.customerType = 4;
        }

        String params= JSONProcessor.toJSON(inquiryParam);
        SubmitParameters.inquiryParam = inquiryParam;
        Log.d("params",params);
    }

    private boolean validasi(){
        boolean status = true;
        if(edNama.getText().toString().equals("")){
            edNama.setError("Field nama can not be empty");
            status = false;
        }else if(edNoHp.getText().toString().equals("")){
            edNoHp.setError("Field nomor hp can not be empty");
            status = false;
        }/*else if(edEmail.getText().toString().equals("")){
            edEmail.setError("Field email can not be empty");
            status = false;
        }*/else if(edReferensi.getText().toString().equals("")){
            edReferensi.setError("Field referensi can not be empty");
            status = false;
        }else if(edNoHpReferensi.getText().toString().equals("")){
            edNoHpReferensi.setError("Field nomor hp referensi can not be empty");
            status = false;
        }else if(edNoHp.getText().length() < 8){
            edNoHp.setError("Field nomor hp minimum 8 digit");
            status = false;
        }else if(edNoHpReferensi.getText().length() < 8){
            edNoHpReferensi.setError("Field nomor hp referensi minimum 8 digit");
            status = false;
        }
        return status;
    }

    @Override
    public void onBackPressed() {
        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
        PopupUtils.backWithDraftOption(MinimumCUSDATADraft.this, rootView, 5, new PopupUtils.SaveToDraftListener2() {
            @Override
            public void isSaved(boolean isSaved) {
                if (validasi()) {
                    if (TextUtility.isEmailFormat(edEmail.getText().toString())) {
                        progressDialog = DialogUtility.createProgressDialog(MinimumCUSDATADraft.this, "", "Please wait...");
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                        saveTemporaryParams();
                        final String param = JSONProcessor.toJSON(inquiryParams);
                        Log.d("Document", String.valueOf(inquiryParams.getDocument()));
                        Log.d("paramDraft", param);
                        SubmitPresenter.draft(MinimumCUSDATADraft.this, new DraftCallback() {
                            @Override
                            public void onSuccessDraft(int formId, String date) {
                                progressDialog.dismiss();
                                Log.d("date", date);
                                Draft draft = Draft.findById(Draft.class, id);
                                draft.setData(param);
                                draft.setNamaCustomer(inquiryParams.getCustomer_name());
                                draft.setLastSaved(date);
                                draft.setNpwp(SelectedData.isNPWP);
                                draft.setIsNonPaket(SelectedData.isNonPaket);
                                draft.setPaymentType(SelectedData.PaymentType);
                                draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                draft.setOtr(SelectedData.Otr);
                                draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                draft.setUser_id(String.valueOf(formId));
                                draft.setPayment_type_service_package(drafts.getPayment_type_service_package());
                                draft.setCoverage_type(drafts.getCoverage_type());
                                draft.setIs_npwp(drafts.getIs_npwp());
                                draft.setIs_corporate(drafts.getIs_corporate());

                                draft.save();
                                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                                PopupUtils.simpanKeDraftSaved(MinimumCUSDATADraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                                    @Override
                                    public void isSaved(boolean isSaved) {

                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    }
                                });
                            }

                            @Override
                            public void onFailedDraft(String message) {
                                progressDialog.dismiss();
//                                Toast.makeText(MinimumCUSDATADraft.this,"Failed Draft", Toast.LENGTH_SHORT).show();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
                                String currentDateandTime = sdf.format(new Date());
                                Log.d("date", currentDateandTime);
                                Draft draft = Draft.findById(Draft.class, id);
                                draft.setData(param);
                                draft.setNamaCustomer(inquiryParams.getCustomer_name());
                                draft.setLastSaved(currentDateandTime);
                                draft.setNpwp(SelectedData.isNPWP);
                                draft.setIsNonPaket(SelectedData.isNonPaket);
                                draft.setPaymentType(SelectedData.PaymentType);
                                draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                draft.setOtr(SelectedData.Otr);
                                draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                draft.setPayment_type_service_package(drafts.getPayment_type_service_package());
                                draft.setCoverage_type(drafts.getCoverage_type());
                                draft.setIs_npwp(drafts.getIs_npwp());
                                draft.setIs_corporate(drafts.getIs_corporate());

                                draft.save();
                                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                                PopupUtils.simpanKeDraftSaved(MinimumCUSDATADraft.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                                    @Override
                                    public void isSaved(boolean isSaved) {
                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    }
                                });

                            }
                        }, "draft",param, user_id, "", SelectedData.DpPercentage, drafts.getIs_corporate(), drafts.getIs_simulasi_paket(),
                                drafts.getIs_simulasi_budget(), drafts.getIs_non_paket(), drafts.getIs_npwp(), drafts.getPayment_type_service_package(),
                                drafts.getCoverage_type());

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCUSDATADraft.this)
                                .setMessage("Email address is not valid")
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

            @Override
            public void isExit(boolean isExit) {
                //Log.d("exit", "exit");
                finish();
            }
        });

    }

}

