package com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.Currency.CurrencyFormat;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.ImageUtility.Image;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.Dialog.PopupDraft.PopupUtils;
import com.drife.digitaf.Module.InputKredit.InputItemKredit.Activity.InputItemKreditActivity;
import com.drife.digitaf.ORM.Controller.DraftController;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.Service.SendEmail.SendEmailCallback;
import com.drife.digitaf.Service.SendEmail.SendEmailPresenter;
import com.drife.digitaf.Service.Submit.DraftCallback;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.UIModel.HasilPerhitungan;
import com.drife.digitaf.Module.InputKredit.MinimumCustomerData.Activity.MinimumCustomerDataActivity;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.Model.PVPremi;
import com.drife.digitaf.retrofitmodule.SubmitParam.EmailParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.LifeInsurance;
import com.drife.digitaf.retrofitmodule.SubmitParam.SubmitParameters;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HasilPerhitunganActivity extends AppCompatActivity{
    private String TAG = HasilPerhitunganActivity.class.getSimpleName();

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btnLanjutkan)
    LinearLayout btnLanjutkan;
    @BindView(R.id.btnDraft)
    LinearLayout btnDratf;
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

    private HasilPerhitungan data;
    int REQUEST_CODE_MIN_CUS_DATA = 1;

    private UserSession userSession;
    List<Draft> Draft_array = new ArrayList<>();
    private ProgressDialog progressDialog;

    private String spv = "spv";
    private String bm = "bm";

    private String isSimulasiPaket = "";
    private String isSimulasiBudget = "";
    private String isNonPaket = "";
    private String paymentTypeServicePackage = "";
    private String coverageType = "";

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

        if(userSession.getUser().getType().equals(spv) || userSession.getUser().getType().equals(bm)){
            btnDratf.setVisibility(View.GONE);
            btnLanjutkan.setVisibility(View.GONE);
        }

        btnLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userSession.getUser().getType().equals(spv) && !userSession.getUser().getType().equals(bm)){
                    Intent intent = new Intent(HasilPerhitunganActivity.this, MinimumCustomerDataActivity.class);
                    InquiryParam inquiryParam = SubmitParameters.inquiryParam;
                    //Log.d("Dealer",inquiryParam.getSuppl_code());
                    intent.putExtra("data", (Serializable) data);
                    startActivityForResult(intent, REQUEST_CODE_MIN_CUS_DATA);
                }
            }
        });


        btnDratf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_draft();
            }
        });


        tvUlangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //KalkulatorUtility.resetGlobalData();
                finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HasilPerhitunganActivity.this);
                builder.setTitle("Email Address");
                final EditText input = new EditText(HasilPerhitunganActivity.this);
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
                DialogUtility.showToolTip(HasilPerhitunganActivity.this, getResources().getString(R.string.tooltip_pvpremi_calresult));
            }
        });

        iv_tooltipPCLPremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtility.showToolTip(HasilPerhitunganActivity.this, getResources().getString(R.string.tooltip_pclpremi_calresult));
            }
        });
    }

    private void callFunctions(){
        getIntentData();
    }

    private void getIntentData(){

        data = new HasilPerhitungan();
        data = (HasilPerhitungan) getIntent().getSerializableExtra("data");
        Log.d("singo","otr on result : "+data.getOtr());

        tvModel.setText(data.getModelMobil());
        tvOtr.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getOtr())));
        tvPaket.setText(data.getPaket());
        tvTenor.setText(data.getTenor()+"x");
//        tvRate.setText(data.getRate()+"% (Flat Rate)");
        tvRate.setText(data.getRate()+"%");
        tvAngsuran.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getAngsuran())));
        tvDp.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getDP())));
        tvTdp.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getTDP())));
        tvTotalHutang.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getTotalHutang())));
        tvPVPremi.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getPVPremiAmount())));
        tvPCLPremi.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getPCLPremiAmount())));
        tvAdminFee.setText(CurrencyFormat.formatRupiah().format(Double.parseDouble(data.getAdminFee())));
        tvFirstInstallment.setText(data.getFirstInstallment());
        tvType.setText(data.getType());

        isSimulasiPaket = getIntent().getStringExtra("isSimulasiPaket");
        isSimulasiBudget = getIntent().getStringExtra("isSimulasiBudget");
        isNonPaket = getIntent().getStringExtra("isNonPaket");
        paymentTypeServicePackage = getIntent().getStringExtra("paymentTypeServicePackage");
        coverageType = getIntent().getStringExtra("coverageType");
    }

    private void send_draft() {

        InquiryParam inquiryParam = SubmitParameters.inquiryParam;
        SubmitParameters.inquiryParam = inquiryParam;

        String cusName = "";
        if(inquiryParam != null){
            if(inquiryParam.getCustomer_name() != null){
                cusName = inquiryParam.getCustomer_name();
            }
        }

        if (cusName.equals("")) {
            Log.d("customName", "Custom Name Null");
            NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
            PopupUtils.simpanKeDraft(HasilPerhitunganActivity.this, rootView, new PopupUtils.SaveToDraftListener() {
                @Override
                public void isSaved(boolean isSaved) {
                    progressDialog = DialogUtility.createProgressDialog(HasilPerhitunganActivity.this,"","Please wait...");
                    if(!progressDialog.isShowing()){
                        progressDialog.show();
                    }
                    Draft_array.clear();
                    InquiryParam inquiryParam = SubmitParameters.inquiryParam;
                    String param = JSONProcessor.toJSON(inquiryParam);
                    SubmitParameters.inquiryParam = inquiryParam;
                    SubmitPresenter.draftSubmit(HasilPerhitunganActivity.this, new DraftCallback() {
                                @Override
                                public void onSuccessDraft(int formId, String date) {
                                    InquiryParam inquiryParam = SubmitParameters.inquiryParam;
                                    String param = JSONProcessor.toJSON(inquiryParam);
                                    SubmitParameters.inquiryParam = inquiryParam;
                                    Log.d("Form_id", String.valueOf(formId));
                                    Draft draft = new Draft();
                                    if (userSession != null) {
                                        draft.setCustomerId(""+userSession.getUser().getId());
                                    } else {
                                        draft.setCustomerId("");
                                    }
                                    draft.setNamaCustomer(inquiryParam.getCustomer_name());
                                    draft.setData(param);
                                    draft.setIsNonPaket(SelectedData.isNonPaket);
                                    draft.setPaymentType(SelectedData.PaymentType);
                                    draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                    //draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                    draft.setPvPremiAmount(SelectedData.pvPremiSumUp);
                                    draft.setOtr(SelectedData.Otr);
                                    draft.setUser_id(String.valueOf(formId));
                                    draft.setCoverage_type(coverageType);
                                    draft.setPayment_type_service_package(paymentTypeServicePackage);
                                    draft.setIs_non_paket(isNonPaket);
                                    draft.setIs_simulasi_budget(isSimulasiBudget);
                                    draft.setIs_simulasi_paket(isSimulasiPaket);

                                    draft.setLastSaved(date);
                                    Draft_array.add(draft);

                                    new Insert_draft().execute(Draft_array);

                                }

                                @Override
                                public void onFailedDraft(String message) {
                                    Log.d("message", message);
                                    InquiryParam inquiryParam = SubmitParameters.inquiryParam;
                                    String param = JSONProcessor.toJSON(inquiryParam);
                                    SubmitParameters.inquiryParam = inquiryParam;
                                    Draft draft = new Draft();
                                    if (userSession != null) {
                                        draft.setCustomerId(userSession.getUser().getProfile().getFullname());
                                    } else {
                                        draft.setCustomerId("");
                                    }
                                    draft.setNamaCustomer(inquiryParam.getCustomer_name());
                                    draft.setData(param);
                                    draft.setIsNonPaket(SelectedData.isNonPaket);
                                    draft.setPaymentType(SelectedData.PaymentType);
                                    draft.setPokokHutangAkhir(SelectedData.PokokHutangAkhir);
                                    //draft.setPvPremiAmount(SelectedData.pvPremiAmount);
                                    draft.setPvPremiAmount(SelectedData.pvPremiSumUp);
                                    draft.setOtr(SelectedData.Otr);
                                    draft.setUser_id("");
                                    draft.setCoverage_type(coverageType);
                                    draft.setPayment_type_service_package(paymentTypeServicePackage);
                                    draft.setIs_non_paket(isNonPaket);
                                    draft.setIs_simulasi_budget(isSimulasiBudget);
                                    draft.setIs_simulasi_paket(isSimulasiPaket);

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
                                    String currentDateandTime = sdf.format(new Date());

                                    draft.setLastSaved(currentDateandTime);
                                    Draft_array.add(draft);

                                    new Insert_draft().execute(Draft_array);
                                }
                            },
                            "draft", param, "",SelectedData.DpPercentage,isSimulasiPaket,isSimulasiBudget,
                            isNonPaket,paymentTypeServicePackage,coverageType);
                }
            });
        }
    }

    class Insert_draft extends AsyncTask<List<Draft>,Void,Void> {
        List<Draft> drafts;
        @Override
        protected Void doInBackground(List<Draft>... lists) {
            drafts = lists[0];
            if(drafts != null){
                Log.d("SelectedData = ", SelectedData.isNonPaket + ", " + SelectedData.PaymentType + ", "
                        + SelectedData.Otr + ", "+ SelectedData.PokokHutangAkhir + ", " + SelectedData.pvPremiAmount);

                DraftController.InsertDraft(drafts);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
            List<Draft> drafts = new ArrayList<>();
            drafts = DraftController.getAllDraft();
            for (int i = 0; i < drafts.size();i++) {
                com.drife.digitaf.ORM.Database.Draft draft = drafts.get(i);
                Log.d("id", draft.getCustomerId());
                Log.d("Data", draft.getData());
                Log.d("name", draft.getNamaCustomer());
                Log.d("date", draft.getLastSaved());
            }
            drafts.clear();

            NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
            PopupUtils.simpanKeDraftSaved(HasilPerhitunganActivity.this, rootView, 1, new PopupUtils.SaveToDraftListener() {
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

    private void sendEmail(String email){
        final ProgressDialog barProgressDialog = new ProgressDialog(HasilPerhitunganActivity.this);
        barProgressDialog.setTitle("Please wait");
        barProgressDialog.setMessage("Sending email...");
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_SPINNER);
        barProgressDialog.setCancelable(false);
        barProgressDialog.show();

        InquiryParam inquiryParam = SubmitParameters.inquiryParam;

        EmailParam emailParam = new EmailParam();
        emailParam.setEmail(email);
        emailParam.setBrand(SelectedData.selectedCarModel.getBrand());
        emailParam.setModel(SelectedData.selectedCarModel.getCarName());

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

        emailParam.setPv_premi(SelectedData.pvInscoName);
        emailParam.setPaket(SelectedData.SelectedPackage);
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
        if(lifeInsurance != null){
            emailParam.setPcl(inquiryParam.getLife_insurance().getInsurance_premi());
            emailParam.setPcl_name(SelectedData.pclInscoName);
        }else{
            emailParam.setPcl("0");
            emailParam.setPcl_name("-");
        }

        emailParam.setTotal_hutang(inquiryParam.getTotal_ar());
        emailParam.setPokok_hutang(SelectedData.PokokHutangAkhir);
        emailParam.setPv_list(inquiryParam.getInsurance().getInsurance_list());
        emailParam.setPv_total(SelectedData.pvPremiSumUp);
        emailParam.setPv_type(SelectedData.PVPaymentType);
        emailParam.setDp_precentage(SelectedData.DpPercentage);

        String param = JSONProcessor.toJSON(emailParam);
        SendEmailPresenter.sendEmail(HasilPerhitunganActivity.this, new SendEmailCallback() {
            @Override
            public void onSuccessSendEmail(String message) {
                barProgressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(HasilPerhitunganActivity.this)
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HasilPerhitunganActivity.this)
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

}
