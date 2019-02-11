package com.drife.digitaf.Module.InputKredit.MinimumCustomerData.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.drife.digitaf.Module.Dialog.PopupDraft.PopupUtils;
import com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity.HasilPerhitunganActivity;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.ORM.Controller.DealerController;
import com.drife.digitaf.ORM.Controller.DraftController;
import com.drife.digitaf.ORM.Controller.WayOfPaymentController;
import com.drife.digitaf.ORM.Database.Dealer;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.ORM.Database.WayOfPayment;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Submit.DraftCallback;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.SubmitParam.Customer;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.Spouse;
import com.drife.digitaf.retrofitmodule.SubmitParam.SubmitParameters;
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

public class MinimumCustomerDataActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = MinimumCustomerDataActivity.class.getSimpleName();
    private LinearLayout btnLanjutkan;
    private RadioButton rbKaryawan, rbProfessional, rbWiraswasta, rbCorporate;
    private Spinner spinnerCaraPembayaran;

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


    List<Draft> Draft_array = new ArrayList<>();
    int REQUEST_CODE_UPLOAD = 1;

    private List<String> caraPembayaran = new ArrayList<>();
    private List<WayOfPayment> wayOfPayments = new ArrayList<>();

    private UserSession userSession;
    private ProgressDialog progressDialog;

    private String isSimulasiPaket = "";
    private String isSimulasiBudget = "";
    private String isNonPaket = "";
    private String paymentTypeServicePackage = "";
    private String coverageType = "";
    private String isNpwp = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_activity_minimumcustomerdata);
        ButterKnife.bind(this);
//        SugarContext.init(MinimumCustomerDataActivity.this);
        initLayout();
        getIntentData();
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

    private void initLayout() {
        btnDraft = findViewById(R.id.btnDraft);
        btnLanjutkan = findViewById(R.id.btnLanjutkan);
        rbKaryawan = findViewById(R.id.rbKaryawan);
        rbProfessional = findViewById(R.id.rbProfessional);
        rbWiraswasta = findViewById(R.id.rbWiraswasta);
        rbCorporate = findViewById(R.id.rbCorporate);
        spinnerCaraPembayaran = findViewById(R.id.spinnerCaraPembayaran);

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
        }

        setSpinnerCaraPembayaran();
        rbKaryawan.setChecked(true);

        switchNpwp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SelectedData.isNPWP = true;
                    isNpwp = "1";
                }else{
                    SelectedData.isNPWP = false;
                    isNpwp = "0";
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

        Log.d("SelectedData = ", SelectedData.isNonPaket + ", " + SelectedData.PaymentType + ", "
                + SelectedData.Otr + ", "+ SelectedData.PokokHutangAkhir + ", " + SelectedData.pvPremiAmount);

        btnDraft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDraft:
                if (validasi()) {
                    progressDialog = DialogUtility.createProgressDialog(MinimumCustomerDataActivity.this,"","Please wait...");
                    if(!progressDialog.isShowing()){
                        progressDialog.show();
                    }
                    if(edNama.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edNama.setError("Nama kosong");
                    }else if (edEmail.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edEmail.setError("email kosong");
                    }else if (edNoHp.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edNoHp.setError("No Hp kosong");
                    }else if (edReferensi.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edReferensi.setError("Nama Referensi Kosong");
                    }else if (edNoHpReferensi.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edNoHpReferensi.setError("No hp Referensi Kosong");
                    }else if (TextUtility.isEmailFormat(edEmail.getText().toString())){
                        send_draft();
                    }
//                    else {
//
//                    }
                }
                break;
            case R.id.btnLanjutkan:
                if(validasi()){
                    if(TextUtility.isEmailFormat(edEmail.getText().toString())){
                        if(rbCorporate.isChecked()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCustomerDataActivity.this)
                                    .setMessage("Untuk tipe Customer Corporate (Badan Usaha/Perusahaan), " +
                                            "harap memberikan dokumen pendukung ke Sales Officer TAF.")
                                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCustomerDataActivity.this)
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
                            saveTemporaryParams();
                            Intent intent = new Intent(this, UploadDocumentActivity.class);
                            SelectedData.wop = wayOfPayments.get(spinnerCaraPembayaran.getSelectedItemPosition()).getName();
                            Log.d("nameDebit", SelectedData.wop);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            startActivityForResult(intent,REQUEST_CODE_UPLOAD);
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MinimumCustomerDataActivity.this)
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
                PopupUtils.backWithDraftOption(MinimumCustomerDataActivity.this, rootView, 5, new PopupUtils.SaveToDraftListener2() {
                    @Override
                    public void isSaved(boolean isSaved) {
                        //Log.d("draft", "draft");
                        //finish();
                        if (validasi()) {
                            progressDialog = DialogUtility.createProgressDialog(MinimumCustomerDataActivity.this,"","Please wait...");
                            if(!progressDialog.isShowing()){
                                progressDialog.show();
                            }
                            if(edNama.getText().toString().equals("")){
                                progressDialog.dismiss();
                                edNama.setError("Nama kosong");
                            }else if (edEmail.getText().toString().equals("")){
                                progressDialog.dismiss();
                                edEmail.setError("email kosong");
                            }else if (edNoHp.getText().toString().equals("")){
                                progressDialog.dismiss();
                                edNoHp.setError("No Hp kosong");
                            }else if (edReferensi.getText().toString().equals("")){
                                progressDialog.dismiss();
                                edReferensi.setError("Nama Referensi Kosong");
                            }else if (edNoHpReferensi.getText().toString().equals("")){
                                progressDialog.dismiss();
                                edNoHpReferensi.setError("No hp Referensi Kosong");
                            }else if (TextUtility.isEmailFormat(edEmail.getText().toString())){
                                send_draft();
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

    private void send_draft(){
        final InquiryParam inquiryParam = SubmitParameters.inquiryParam;
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
        }

        if (switchNpwp.isChecked()){
            isNpwp = "1";
        }else {
            isNpwp = "0";
        }

        final String param = JSONProcessor.toJSON(inquiryParam);
        SubmitParameters.inquiryParam = inquiryParam;
        LogUtility.logging(TAG, LogUtility.infoLog,"SendDraft","param",param);

        Draft_array.clear();

        SubmitPresenter.draftSubmit(MinimumCustomerDataActivity.this, new DraftCallback() {
            @Override
            public void onSuccessDraft(int formId, String date) {
                Draft draft = new Draft();
                if(userSession != null){
                    draft.setCustomerId(""+userSession.getUser().getId());
                }else {
                    draft.setCustomerId("");
                }
                draft.setNamaCustomer(inquiryParam.getCustomer_name());
                draft.setData(param);
                draft.setNpwp(SelectedData.isNPWP);
                if(SelectedData.isNonPaket != null){
                    draft.setIsNonPaket(SelectedData.isNonPaket);
                }else{
                    draft.setIsNonPaket("0");
                }
                draft.setPaymentType(SelectedData.PaymentType);
                draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                draft.setOtr(SelectedData.Otr);
                draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                draft.setUser_id(String.valueOf(formId));
                draft.setLastSaved(date);
                draft.setPayment_type_service_package(paymentTypeServicePackage);
                draft.setCoverage_type(coverageType);
                draft.setIs_npwp(isNpwp);
                draft.setIs_non_paket(isNonPaket);
                draft.setIs_simulasi_budget(isSimulasiBudget);
                draft.setIs_simulasi_paket(isSimulasiPaket);

                Draft_array.add(draft);

                new Insert_draft().execute(Draft_array);
            }

            @Override
            public void onFailedDraft(String message) {
                Draft draft = new Draft();
                if(userSession != null){
                    draft.setCustomerId(userSession.getUser().getProfile().getFullname());
                }else {
                    draft.setCustomerId("");
                }
                draft.setNamaCustomer(inquiryParam.getCustomer_name());
                draft.setData(param);
                draft.setNpwp(SelectedData.isNPWP);
                if(SelectedData.isNonPaket != null){
                    draft.setIsNonPaket(SelectedData.isNonPaket);
                }else{
                    draft.setIsNonPaket("0");
                }
                draft.setPaymentType(SelectedData.PaymentType);
                draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                draft.setOtr(SelectedData.Otr);
                draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                draft.setPayment_type_service_package(paymentTypeServicePackage);
                draft.setCoverage_type(coverageType);
                draft.setIs_npwp(isNpwp);
                draft.setIs_non_paket(isNonPaket);
                draft.setIs_simulasi_budget(isSimulasiBudget);
                draft.setIs_simulasi_paket(isSimulasiPaket);
//                draft.setIs_corporate(drafts.getIs_corporate());

                draft.setUser_id("");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
                String currentDateandTime = sdf.format(new Date());

                draft.setLastSaved(currentDateandTime);
                Draft_array.add(draft);

                new Insert_draft().execute(Draft_array);
            }
        },"draft",param,"",SelectedData.DpPercentage,isSimulasiPaket,isSimulasiBudget,
                isNonPaket,paymentTypeServicePackage,coverageType,isNpwp);
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
                caraPembayaran.add(name);
            }
        }

        SpinnerUtility.setSpinnerItem(getApplicationContext(),spinnerCaraPembayaran,caraPembayaran);
    }

    private void saveTemporaryParams(){
        InquiryParam inquiryParam = SubmitParameters.inquiryParam;
        inquiryParam.setCustomer_name(edNama.getText().toString().toUpperCase());
        inquiryParam.setReference_name(edReferensi.getText().toString().toUpperCase());
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
        }

        if(rbKaryawan.isChecked()){
            SelectedData.customerType = 1;
        }else if(rbProfessional.isChecked()){
            SelectedData.customerType = 2;
        }else if(rbWiraswasta.isChecked()){
            SelectedData.customerType = 3;
        }else if(rbCorporate.isChecked()){
            SelectedData.customerType = 4;
        }

        String param = JSONProcessor.toJSON(inquiryParam);
        SubmitParameters.inquiryParam = inquiryParam;

//        LogUtility.logging(TAG, LogUtility.infoLog,"SendDraft","param",param);
//
//        SubmitPresenter.submit(MinimumCustomerDataActivity.this, new SubmitCallback() {
//            @Override
//            public void onSuccessSubmit(String formId) {
//                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
//                PopupUtils.simpanKeDraftSaved(MinimumCustomerDataActivity.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
//                    @Override
//                    public void isSaved(boolean isSaved) {
//                        setResult(Activity.RESULT_OK);
//                        finish();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailedSubmit(String message) {
//                Toast.makeText(MinimumCustomerDataActivity.this,"Failed submit", Toast.LENGTH_SHORT).show();
//            }
//        },"draft",param,"");
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

    class Insert_draft extends AsyncTask<List<Draft>,Void,Void> {
        List<Draft> drafts;

        @Override
        protected Void doInBackground(List<Draft>... lists) {
            drafts = lists[0];
            if (drafts != null) {
                DraftController.InsertDraft(drafts);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            List<Draft> drafts = new ArrayList<>();
            drafts = DraftController.getAllDraft();
            for (int i = 0; i < drafts.size(); i++) {
                com.drife.digitaf.ORM.Database.Draft draft = drafts.get(i);
                Log.d("Data", draft.getData());
                Log.d("name", draft.getNamaCustomer());
                Log.d("date", draft.getLastSaved());
                Log.d("Userid", draft.getCustomerId());
            }
            drafts.clear();

            progressDialog.dismiss();
            NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
            PopupUtils.simpanKeDraftSaved(MinimumCustomerDataActivity.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
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
    }

    @Override
    public void onBackPressed() {
        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
        PopupUtils.backWithDraftOption(MinimumCustomerDataActivity.this, rootView, 5, new PopupUtils.SaveToDraftListener2() {
            @Override
            public void isSaved(boolean isSaved) {
                //Log.d("draft", "draft");
                //finish();
                if (validasi()) {
                    progressDialog = DialogUtility.createProgressDialog(MinimumCustomerDataActivity.this,"","Please wait...");
                    if(!progressDialog.isShowing()){
                        progressDialog.show();
                    }
                    if(edNama.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edNama.setError("Nama kosong");
                    }else if (edEmail.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edEmail.setError("email kosong");
                    }else if (edNoHp.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edNoHp.setError("No Hp kosong");
                    }else if (edReferensi.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edReferensi.setError("Nama Referensi Kosong");
                    }else if (edNoHpReferensi.getText().toString().equals("")){
                        progressDialog.dismiss();
                        edNoHpReferensi.setError("No hp Referensi Kosong");
                    }else if (TextUtility.isEmailFormat(edEmail.getText().toString())){
                        send_draft();
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

    private void getIntentData(){
        isSimulasiPaket = getIntent().getStringExtra("isSimulasiPaket");
        isSimulasiBudget = getIntent().getStringExtra("isSimulasiBudget");
        isNonPaket = getIntent().getStringExtra("isNonPaket");
        paymentTypeServicePackage = getIntent().getStringExtra("paymentTypeServicePackage");
        coverageType = getIntent().getStringExtra("coverageType");
    }
}
