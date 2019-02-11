package com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.drife.digitaf.GeneralUtility.CameraUtility.CameraUtility;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.FileUtility.FileUtils;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.Module.Camera.CameraCardNameActivity;
import com.drife.digitaf.Module.Dialog.PopupDialog.PopupViewImage;
import com.drife.digitaf.Module.Dialog.PopupDraft.PopupUtils;
import com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity.HasilPerhitungandraft;
import com.drife.digitaf.Module.InputKredit.MinimumCustomerData.Activity.MinimumCUSDATADraft;
import com.drife.digitaf.Module.InputKredit.MinimumCustomerData.Activity.MinimumCustomerDataActivity;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Adapter.ToggleMenuListAdapter;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.ToggleMenuItem;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.UploadDocumentItem;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Utils.PopupUpload;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Utils.PopupUploadKtp;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Utils.PopupUploadSPK;
import com.drife.digitaf.Module.Login.Activity.LoginActivity;
import com.drife.digitaf.Module.Offline.SubmitService;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.Module.myapplication.childfragment.DraftFragment;
import com.drife.digitaf.ORM.Controller.Outbox_submitController;
import com.drife.digitaf.ORM.Database.DBController;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.ORM.Database.DraftData;
import com.drife.digitaf.ORM.Database.Outbox_submit;
import com.drife.digitaf.ORM.Temporary.TemporaryValue;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.Service.Submit.UpdateDocumentCallback;
import com.drife.digitaf.Service.Submit.UploadCallback;
import com.drife.digitaf.UIModel.ListOCRData;
import com.drife.digitaf.UIModel.OCRData;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.Model.OCRResult;
import com.drife.digitaf.retrofitmodule.SubmitParam.AssetOwner;
import com.drife.digitaf.retrofitmodule.SubmitParam.Customer;
import com.drife.digitaf.retrofitmodule.SubmitParam.CustomerJob;
import com.drife.digitaf.retrofitmodule.SubmitParam.DocTcData;
import com.drife.digitaf.retrofitmodule.SubmitParam.Document;
import com.drife.digitaf.retrofitmodule.SubmitParam.DocumentUpload;
import com.drife.digitaf.retrofitmodule.SubmitParam.ImageAttachment;
import com.drife.digitaf.retrofitmodule.SubmitParam.ImageAttachmentOffline;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.JobAddress;
import com.drife.digitaf.retrofitmodule.SubmitParam.LegalAddress;
import com.drife.digitaf.retrofitmodule.SubmitParam.PdfAttachment;
import com.drife.digitaf.retrofitmodule.SubmitParam.PdfAttachmentOffline;
import com.drife.digitaf.retrofitmodule.SubmitParam.ResidenceAddress;
import com.drife.digitaf.retrofitmodule.SubmitParam.Spouse;
import com.drife.digitaf.retrofitmodule.SubmitParam.SpouseJob;
import com.drife.digitaf.retrofitmodule.SubmitParam.SubmitParameters;
import com.google.gson.Gson;
import com.nekoloop.base64image.Base64Image;
import com.nekoloop.base64image.RequestEncode;
import com.orm.query.Select;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadDocumentActivity extends AppCompatActivity {
    private String TAG = UploadDocumentActivity.class.getSimpleName();
    private SubmitService s;
    UserSession userSession;

    @BindView(R.id.lyItemKTP)
    CardView lyItemKTP;
    @BindView(R.id.lyItemSlipGaji)
    CardView lyItemSlipGaji;
    @BindView(R.id.lyItemSPK)
    CardView lyItemSPK;
    @BindView(R.id.lyItemKartuNama)
    CardView lyItemKartuNama;
    @BindView(R.id.lyItemBukuNikah)
    CardView lyItemBukuNikah;
    @BindView(R.id.lyItemKK)
    CardView lyItemKK;
    @BindView(R.id.lyItemNPWP)
    CardView lyItemNPWP;
    @BindView(R.id.lyItemBuktiKeuangan)
    CardView lyItemBuktiKeuangan;
    @BindView(R.id.lyItemBuktiKepemilikanRumah)
    CardView lyItemBuktiKepemilikanRumah;
    @BindView(R.id.lyItemKontrakKerja)
    CardView lyItemKontrakKerja;
    @BindView(R.id.lyItemKKSTNK)
    CardView lyItemKKSTNK;
    @BindView(R.id.lyItemKTPSTNK)
    CardView lyItemKTPSTNK;
    @BindView(R.id.lyItemCoverTabungan)
    CardView lyItemCoverTabungan;
    @BindView(R.id.lyItemKeteranganDomisili)
    CardView lyItemKeteranganDomisili;
    @BindView(R.id.lyItemRekeningKoran)
    CardView lyItemRekeningKoran;
    @BindView(R.id.lyItemKartuNamaPasangan)
    CardView lyItemKartuNamaPasangan;
    @BindView(R.id.lyItemKTPPasangan)
    CardView lyItemKTPPasangan;
    @BindView(R.id.lySwitchPasanganBekerja)
    CardView lySwitchPasanganBekerja;
    @BindView(R.id.lySwitchSTNKCustomer)
    CardView lySwitchSTNKCustomer;
    @BindView(R.id.lySwitchAlamatTinggal)
    CardView lySwitchAlamatTinggal;
    @BindView(R.id.switchInfoPasanganBekerja)
    Switch switchInfoPasanganBekerja;
    @BindView(R.id.switchInfoSTNKCustomer)
    Switch switchInfoSTNKCustomer;
    @BindView(R.id.switchInfoAlamatTinggal)
    Switch switchInfoAlamatTinggal;
    @BindView(R.id.rvListToggleMenu)
    RecyclerView rvToggleMenu;
    @BindView(R.id.toggleButton)
    LinearLayout toggleButton;
    @BindView(R.id.toggleMenu)
    CardView toggleMenu;
    @BindView(R.id.imgToggle)
    ImageView imgToggle;
    @BindView(R.id.txtCounChecklist)
    TextView txtCounChecklist;
    @BindView(R.id.txtCountMenu)
    TextView txtCountMenu;
    @BindView(R.id.lyParent)
    RelativeLayout lyParent;
    @BindView(R.id.btnAddAttachmentKTP)
    RelativeLayout btnAddAttachmentKTP;
    @BindView(R.id.btnAddAttachmentSPK)
    RelativeLayout btnAddAttachmentSPK;
    @BindView(R.id.btnAddAttachmentKartuNama)
    RelativeLayout btnAddAttachmentKartuNama;
    //@BindView(R.id.etAlamatTinggal) EditText etAlamatTinggal;
    @BindView(R.id.btnAddAttachmentKK)
    RelativeLayout btnAddAttachmentKK;
    @BindView(R.id.btnAddAttachmentNPWP)
    RelativeLayout btnAddAttachmentNPWP;
    @BindView(R.id.btnAddAttachmentBuktiKeuangan)
    RelativeLayout btnAddAttachmentBuktiKeuangan;
    @BindView(R.id.btnAddAttachmentRekeningKoran)
    RelativeLayout btnAddAttachmentRekeningKoran;
    @BindView(R.id.btnAddAttachmentCoverTabungan)
    RelativeLayout btnAddAttachmentCoverTabungan;
    @BindView(R.id.btnAddAttachmentBuktiKepemilikanRumah)
    RelativeLayout btnAddAttachmentBuktiKepemilikanRumah;
    @BindView(R.id.btnAddAttachmentKontrakKerja)
    RelativeLayout btnAddAttachmentKontrakKerja;
    @BindView(R.id.btnAddAttachmentKeteranganDomisili)
    RelativeLayout btnAddAttachmentKeteranganDomisili;
    @BindView(R.id.btnAddAttachmentBukuNikah)
    RelativeLayout btnAddAttachmentBukuNikah;
    @BindView(R.id.btnAddAttachmentKTPPasangan)
    RelativeLayout btnAddAttachmentKTPPasangan;
    @BindView(R.id.btnAddAttachmentKartuNamaPasangan)
    RelativeLayout btnAddAttachmentKartuNamaPasangan;
    @BindView(R.id.btnAddAttachmentKKSTNK)
    RelativeLayout btnAddAttachmentKKSTNK;
    @BindView(R.id.btnAddAttachmentKTPSTNK)
    RelativeLayout btnAddAttachmentKTPSTNK;
    @BindView(R.id.btnAddAttachmentSlipGaji)
    RelativeLayout btnAddAttachmentSlipGaji;

    @BindView(R.id.imgAttachmentKTP)
    ImageView imgAttachmentKTP;
    @BindView(R.id.txtDocumentInfoKTP)
    TextView txtDocumentInfoKTP;
    @BindView(R.id.txtDefaultKTP)
    TextView txtDefaultKTP;
    @BindView(R.id.txtDefaultSPK)
    TextView txtDefaultSPK;
    @BindView(R.id.txtDefaultKartuNama)
    TextView txtDefaultKartuNama;
    @BindView(R.id.txtDefaultKK)
    TextView txtDefaultKK;

    @BindView(R.id.txtDefaultNPWP)
    TextView txtDefaultNPWP;
    @BindView(R.id.txtDefaultBuktiKeuangan)
    TextView txtDefaultBuktiKeuangan;
    @BindView(R.id.txtDefaultRekeningKoran)
    TextView txtDefaultRekeningKoran;
    @BindView(R.id.txtDefaultCoverTabungan)
    TextView txtDefaultCoverTabungan;
    @BindView(R.id.txtDefaultBuktiKepemilikanRumah)
    TextView txtDefaultBuktiKepemilikanRumah;
    @BindView(R.id.txtDefaultKontrakKerja)
    TextView txtDefaultKontrakKerja;
    @BindView(R.id.txtDefaultKetDomisili)
    TextView txtDefaultKetDomisili;
    @BindView(R.id.txtDefaultBukuNikah)
    TextView txtDefaultBukuNikah;
    @BindView(R.id.txtDefaultKTPPasangan)
    TextView txtDefaultKTPPasangan;
    @BindView(R.id.txtDefaultKartuNamaPasangan)
    TextView txtDefaultKartuNamaPasangan;
    @BindView(R.id.txtDefaultKKSTNK)
    TextView txtDefaultKKSTNK;
    @BindView(R.id.txtDefaultKTPSTNK)
    TextView txtDefaultKTPSTNK;
    @BindView(R.id.txtDefaultSlipGaji)
    TextView txtDefaultSlipGaji;

    @BindView(R.id.btnSubmit)
    LinearLayout btnSubmit;
    @BindView(R.id.btnDraft)
    LinearLayout btnDraft;
    @BindView(R.id.lyResAddress)
    LinearLayout lyResAddress;
    @BindView(R.id.edAlamat)
    EditText edAlamat;
    @BindView(R.id.edRT)
    EditText edRT;
    @BindView(R.id.edRW)
    EditText edRW;
    @BindView(R.id.edKelurahan)
    EditText edKelurahan;
    @BindView(R.id.edKecamatan)
    EditText edKecamatan;
    @BindView(R.id.edKota)
    EditText edKota;
    @BindView(R.id.edZipCode)
    EditText edZipCode;

    @BindView(R.id.btnPreviewKTP)
    RelativeLayout btnPreviewKTP;
    @BindView(R.id.btnPreviewSPK)
    RelativeLayout btnPreviewSPK;
    @BindView(R.id.btnPreviewKartuNama)
    RelativeLayout btnPreviewKartuNama;
    @BindView(R.id.btnPreviewKK)
    RelativeLayout btnPreviewKK;
    @BindView(R.id.btnPreviewNPWP)
    RelativeLayout btnPreviewNPWP;
    @BindView(R.id.btnPreviewBuktiKeuangan)
    RelativeLayout btnPreviewBuktiKeuangan;
    @BindView(R.id.btnPreviewRekeningKoran)
    RelativeLayout btnPreviewRekeningKoran;
    @BindView(R.id.btnPreviewCoverTabungan)
    RelativeLayout btnPreviewCoverTabungan;
    @BindView(R.id.btnPreviewBuktiKepemilikanRumah)
    RelativeLayout btnPreviewBuktiKepemilikanRumah;
    @BindView(R.id.btnPreviewKontrakKerja)
    RelativeLayout btnPreviewKontrakKerja;
    @BindView(R.id.btnPreviewKeteranganDomisili)
    RelativeLayout btnPreviewKeteranganDomisili;
    @BindView(R.id.btnPreviewBukuNikah)
    RelativeLayout btnPreviewBukuNikah;
    @BindView(R.id.btnPreviewKTPPasangan)
    RelativeLayout btnPreviewKTPPasangan;
    @BindView(R.id.btnPreviewKartuNamaPasangan)
    RelativeLayout btnPreviewKartuNamaPasangan;
    @BindView(R.id.btnPreviewKKSTNK)
    RelativeLayout btnPreviewKKSTNK;
    @BindView(R.id.btnPreviewKTPSTNK)
    RelativeLayout btnPreviewKTPSTNK;
    @BindView(R.id.btnPreviewSlipGaji)
    RelativeLayout btnPreviewSlipGaji;

    @BindView(R.id.imgAttachmentSPK)
    ImageView imgAttachmentSPK;
    @BindView(R.id.imgAttachmentKartuNama)
    ImageView imgAttachmentKartuNama;
    @BindView(R.id.imgAttachmentKK)
    ImageView imgAttachmentKK;
    @BindView(R.id.imgAttachmentNPWP)
    ImageView imgAttachmentNPWP;
    @BindView(R.id.imgAttachmentRekeningKoran)
    ImageView imgAttachmentRekeningKoran;
    @BindView(R.id.imgAttachmentCoverBukuTabungan)
    ImageView imgAttachmentCoverBukuTabungan;
    @BindView(R.id.imgAttachmentBuktiKepemilikanRumah)
    ImageView imgAttachmentBuktiKepemilikanRumah;
    @BindView(R.id.imgAttachmentKontrakKerja)
    ImageView imgAttachmentKontrakKerja;
    @BindView(R.id.imgAttachmentKeteranganDomisili)
    ImageView imgAttachmentKeteranganDomisili;
    @BindView(R.id.imgAttachmentBukuNikah)
    ImageView imgAttachmentBukuNikah;
    @BindView(R.id.imgAttachmentKtpPasangan)
    ImageView imgAttachmentKtpPasangan;
    @BindView(R.id.imgAttachmentKartuNamaPasangan)
    ImageView imgAttachmentKartuNamaPasangan;
    @BindView(R.id.imgAttachmentKKSTNK)
    ImageView imgAttachmentKKSTNK;
    @BindView(R.id.imgAttachmentKTPSTNK)
    ImageView imgAttachmentKTPSTNK;
    @BindView(R.id.imgAttachmentBuktiKeuangan)
    ImageView imgAttachmentBuktiKeuangan;
    @BindView(R.id.imgAttachmentSlipGaji)
    ImageView imgAttachmentSlipGaji;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtCabang)
    TextView txtCabang;

    private ArrayList<UploadDocumentItem> listItems;
    private ArrayList<ToggleMenuItem> listToggleMenuItems;
    private boolean show = true;
    private ToggleMenuListAdapter toggleMenuListAdapter;

    private UploadDocumentItem.DocumentType selectedDocumentType;
    private String customerId = "", customerName = "", status;
    private String tempPath = "", pathKtp = "", pathSPK = "", pathKartuNama = "", pathKK = "", pathNPWP = "", pathBuktikeu = "", pathRekeningkoran = "",
            pathCoverTabungan = "", pathBuktiKepemilikanRumah = "", pathKontrakKerja = "", pathKeteranganDomisili = "", pathBukuNikah = "",
            pathKtpPasangan = "", pathKartuNamaPasangan = "", pathKKSTNK = "", pathKTPSTNK = "", pathSlipGaji = "";
    private String pathPdfRekeningKoran = "", pathPdfKK = "", pathPdfNPWP = "", pathPdfCoverTabungan = "", pathPdfKontrakKerja = "",
            pathPdfBukuNikah = "", pathPdfKartuNamaPasangan = "", pathPdfKTPSTNK = "", pathPdfBuktiKeuangan = "", pathPdfKeteranganDomisili = "",
            pathPdfKKSTNK = "", pathPdfBuktiKepemilikanRumah = "", pathPdfKtpPasangan = "", pathPdfSlipGaji = "";

    private HashMap<String, String> dataNPWP = new HashMap<>(),
            dataKtpPasangan = new HashMap<>(),
            dataKartuNamaPasangan = new HashMap<>(),
            dataKTPSTNK = new HashMap<>(),
            dataSlipGaji = new HashMap<>(),
            dataKTP = new HashMap<>(),
            dataKartuNama = new HashMap<>(),
            dataKK = new HashMap<>(),
            dataSPK = new HashMap<>(),
            dataBuktikeu = new HashMap<>(),
            dataRekeningkoran = new HashMap<>(),
            dataCoverTabungan = new HashMap<>(),
            dataBuktiKepemilikanRumah = new HashMap<>(),
            dataKontrakKerja = new HashMap<>(),
            dataKeteranganDomisili = new HashMap<>(),
            dataBukuNikah = new HashMap<>(),
            dataKKSTNK = new HashMap<>();

    private String promiseDateKK = "", promiseDateBuktikeu = "", promiseDateRekeningkoran = "", promiseDateCoverTabungan = "", promiseDateBuktiKepemilikanRumah = "",
            promiseDateKontrakKerja = "", promiseDateKeteranganDomisili = "", promiseDateBukuNikah = "", promiseDateKTPSTNK = "",
            promiseDateKKSTNK = "", promiseDateNpwp = "", promiseDateKtpPasangan = "", promiseDateKartuNamaPasangan = "", promiseDateSlipGaji = "";


    private List<OCRData> dataOCR;
    private OCRResult ocrResult;
    private OCRResult ocrResultKtpPasangan;
    private OCRResult ocrResultKtpStnk;
    private String nomorSPK = "";
    private HashMap<String, String> dataOCRKartuNama = new HashMap<>();
    private HashMap<String, String> dataOCRKartuNamaPasangan = new HashMap<>();

    private ProgressDialog progressDialog;

    InquiryParam inquiryParam;
    List<ImageAttachment> imageAttachments = new ArrayList<>();
    List<ImageAttachmentOffline> imageAttachmentsOffline = new ArrayList<>();
    List<PdfAttachment> pdfAttachments = new ArrayList<>();
    List<PdfAttachmentOffline> pdfAttachmentsOffline = new ArrayList<>();
    private HashMap<String, String> base64KTP, base64SPK, base64KartuNama;

    private InquiryItem inquiryItem;
    private Document document;
    //private boolean isUpdate = false;
    public int isUpdate = 0;
    private String formId = "";
    List<Outbox_submit> outbox_submits = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_activity_uploaddocument_2);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables() {
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

        if (customerId.equals("")) {
            customerId = TemporaryValue.CustomerId;
        }

        if (MinimumCUSDATADraft.id_.equals(Long.valueOf(0))) {
            Log.d("_id", "bukandraft");
        } else {
            Log.d("_id", String.valueOf(MinimumCUSDATADraft.id_));
        }
    }

    private void initListeners() {
        switchInfoPasanganBekerja.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lyItemKartuNamaPasangan.setVisibility(View.VISIBLE);

                    toggleMenuListAdapter.setMenuVisible(13, true);
                    updateSlideNumbering();
                } else {
                    lyItemKartuNamaPasangan.setVisibility(View.GONE);

                    toggleMenuListAdapter.setMenuChecked(13, false, promiseDateKartuNamaPasangan);
                    toggleMenuListAdapter.setMenuVisible(13, false);
                    updateSlideNumbering();
                }
            }
        });

        switchInfoSTNKCustomer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyItemKKSTNK.setVisibility(View.GONE);
                    lyItemKTPSTNK.setVisibility(View.GONE);

                    toggleMenuListAdapter.setMenuChecked(14, false, promiseDateKKSTNK);
                    toggleMenuListAdapter.setMenuChecked(15, false, promiseDateKTPSTNK);
                    toggleMenuListAdapter.setMenuVisible(14, false);
                    toggleMenuListAdapter.setMenuVisible(15, false);
                    updateSlideNumbering();
                }else {
                    lyItemKKSTNK.setVisibility(View.VISIBLE);
                    lyItemKTPSTNK.setVisibility(View.VISIBLE);

                    toggleMenuListAdapter.setMenuVisible(14, true);
                    toggleMenuListAdapter.setMenuVisible(15, true);
                    updateSlideNumbering();
                }
            }
        });

        switchInfoAlamatTinggal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lyResAddress.setVisibility(View.GONE);

//                    toggleMenuListAdapter.setMenuChecked(16, false, "");
//                    toggleMenuListAdapter.setMenuVisible(16, false);
                    updateSlideNumbering();
                } else {
                    lyResAddress.setVisibility(View.VISIBLE);

//                    toggleMenuListAdapter.setMenuVisible(16, true);
                    updateSlideNumbering();
                }
            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu();
            }
        });

        btnAddAttachmentKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KTP);
            }
        });

        btnAddAttachmentSPK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.SPK);
            }
        });

        btnAddAttachmentKartuNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KartuNama);
            }
        });

        btnAddAttachmentKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KK);
            }
        });

        btnAddAttachmentNPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.NPWP);
            }
        });

        btnAddAttachmentBuktiKeuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.BuktiKeuangan);
            }
        });

        btnAddAttachmentRekeningKoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.RekeningKoran);
            }
        });

        btnAddAttachmentCoverTabungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.CoverBukuTabungan);
            }
        });

        btnAddAttachmentBuktiKepemilikanRumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.BuktiKepemilikanRumah);
            }
        });

        btnAddAttachmentKontrakKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KontrakKerja);
            }
        });

        btnAddAttachmentKeteranganDomisili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KeteranganDomisili);
            }
        });

        btnAddAttachmentBukuNikah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.BukuNikah);
            }
        });

        btnAddAttachmentKTPPasangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KTPIstriSuami);
            }
        });

        btnAddAttachmentKartuNamaPasangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KartuNamaIstriSuami);
            }
        });

        btnAddAttachmentKKSTNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KKSTNK);
            }
        });

        btnAddAttachmentKTPSTNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.KTPSTNK);
            }
        });

        btnAddAttachmentSlipGaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(UploadDocumentItem.DocumentType.SlipGaji);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(validasiMandatoryDocument() && validasiPromiseDate()){
//                processDraft();

//                }
                /*try {
                    submit();
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog, "btnSubmit","clickListener",e.getMessage());
                    Toast.makeText(UploadDocumentActivity.this,"Data is not complete",Toast.LENGTH_SHORT).show();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }*/
            }
        });


        btnPreviewKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKtp, null);
            }
        });

        btnPreviewSPK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathSPK, null);
            }
        });

        btnPreviewKartuNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKartuNama, null);
            }
        });

        btnPreviewKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKK, null);
            }
        });

        btnPreviewBuktiKeuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathBuktikeu, null);
            }
        });

        btnPreviewRekeningKoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathRekeningkoran, null);
            }
        });

        btnPreviewNPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathNPWP, null);
            }
        });

        btnPreviewCoverTabungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathCoverTabungan, null);
            }
        });

        btnPreviewBuktiKepemilikanRumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathBuktiKepemilikanRumah, null);
            }
        });

        btnPreviewBukuNikah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathBukuNikah, null);
            }
        });

        btnPreviewKontrakKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKontrakKerja, null);
            }
        });

        btnPreviewKeteranganDomisili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKeteranganDomisili, null);
            }
        });

        btnPreviewKKSTNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKKSTNK, null);
            }
        });

        btnPreviewKTPSTNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKTPSTNK, null);
            }
        });

        btnPreviewKTPPasangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKtpPasangan, null);
            }
        });

        btnPreviewKartuNamaPasangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathKartuNamaPasangan, null);
            }
        });

        btnPreviewSlipGaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupViewImage.showPopupImage(UploadDocumentActivity.this, lyParent, pathSlipGaji, null);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setResult(RESULT_OK);
                NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                PopupUtils.backFromUploadDocument(UploadDocumentActivity.this, rootView, 5, new PopupUtils.SaveToDraftListener2() {
                    @Override
                    public void isSaved(boolean isSaved) {
                        //Log.d("draft", "draft");
                        finish();
                    }

                    @Override
                    public void isExit(boolean isExit) {
                        //Log.d("exit", "exit");
                    }
                });
            }
        });
    }

    private void callFunctions() {
        setListToggle();
        switchInfoPasanganBekerja.setChecked(false);
        switchInfoSTNKCustomer.setChecked(true);
        switchInfoAlamatTinggal.setChecked(true);

        filteringMenu();
        getIntentData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_1) {
            String path = this.pathKtp;

            if (resultCode == RESULT_OK) {
                try {
                    ListOCRData ocrData = (ListOCRData) data.getSerializableExtra("data");
                    if (ocrData != null) {
                        dataOCR = ocrData.getOcrDataList();
                    }

                    ocrResult = (OCRResult) data.getSerializableExtra("dataOCR");

                    this.pathKtp = data.getStringExtra("path") != null ? data.getStringExtra("path") : ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();

                    if (data.getSerializableExtra("dataKTP") != null) {
                        dataKTP = (HashMap<String, String>) data.getSerializableExtra("dataKTP");
                    }

                    if (data.getData() != null) {
                        dataKTP.put("is_camera", "1");
                    } else {
                        dataKTP.put("is_camera", "0");
                    }

                    String name = "KTP_" + customerName;
                    PopupUploadKtp.inputKTP(UploadDocumentActivity.this, lyParent, new PopupUploadKtp.SaveDataListener() {
                        @Override
                        public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                            if (!isSaved && pathKtp != null && !pathKtp.equals("")) {
                                if (datas != null) {
                                    if (datas.get("is_saved") == null) {
                                        dataKTP.clear();
                                        ocrResult = null;
                                        pathKtp = "";
                                        imgAttachmentKTP.setImageBitmap(null);
                                    }
                                }
                            }

                            try {
                                Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKtp);
                                if (bitmap != null) {
                                    Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                                    imgAttachmentKTP.setImageBitmap(smallBitmap);
                                }
                            } catch (Exception e) {
                                LogUtility.logging(TAG, LogUtility.errorLog, "showPopupWindow", "PopupUpload.inputKTP", e.getMessage());
                            }

                            if (isSaved) {
                                dataKTP = datas;

                                toggleMenuListAdapter.setMenuChecked(0, true, "");
                                updateSlideNumbering();

                                if (!dataKTP.isEmpty() && !pathKtp.isEmpty()) {
                                    txtDefaultKTP.setText(!dataKTP.get("nik").equals("") ? dataKTP.get("nik") : "Preview Data");
                                    ocrResult = null;
                                }
                            }

                            filteringMenu();
                        }

                        @Override
                        public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                            pathKtp = imagePath;
                            dataKTP = data;
                        }
                    }, this.pathKtp, customerId, name, dataOCR, dataKTP, ocrResult);
                } catch (Exception e) {
                    LogUtility.logging(TAG, LogUtility.errorLog, "onActivityResult", "REQUEST_IMAGE_CAPTURE_1 : RESULT_OK", e.getMessage());
                }
                //showPopupWindow(selectedDocumentType);
            } else if (resultCode == RESULT_CANCELED) {
                try {
                    this.pathKtp = data.getStringExtra("path");
                } catch (Exception e) {
                    LogUtility.logging(TAG, LogUtility.errorLog, "onActivityResult", "REQUEST_IMAGE_CAPTURE_1 : RESULT_CANCELED", e.getMessage());
                }
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKtp = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_2) {
            String path = this.pathSPK;

            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getData() != null) {
                        pathSPK = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                    }
                }

                showPopupWindow(selectedDocumentType);
            } else {
                try {
                    ImageUtility.deleteImage(path);
                } catch (Exception e) {

                }
                pathSPK = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_17) {
            if (resultCode == RESULT_OK) {
                nomorSPK = data.getStringExtra("nomor_spk") != null ? data.getStringExtra("nomor_spk") : "";

                showPopupWindow(selectedDocumentType);
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_3) {
            String path = this.pathKartuNama;
            if (resultCode == RESULT_OK) {
                if (data.getSerializableExtra(CameraCardNameActivity.OCR_KEY_CARDNAME) != null) {
                    dataOCRKartuNama = (HashMap<String, String>) data.getSerializableExtra(CameraCardNameActivity.OCR_KEY_CARDNAME);
                }

                if (data.getData() != null) {
                    dataKartuNama.put("is_camera", "1");
                } else {
                    dataKartuNama.put("is_camera", "0");
                }

                //Log.d("singo", "path kartu nama 1 : "+data.getStringExtra("path"));
                this.pathKartuNama = data.getStringExtra("path") != null ? data.getStringExtra("path") : ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                //Log.d("singo", "path kartu nama : "+pathKartuNama);
                showPopupWindow(selectedDocumentType);
            } else {
                try {
                    ImageUtility.deleteImage(path);
                } catch (Exception e) {

                }
                pathKartuNama = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_4) {
            String path = this.pathKK;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathKK = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataKK.get("isPDF").equalsIgnoreCase("0")) {
                        pathKK = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfKK = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataKK.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfKK = data.getStringExtra("pathPDF");
                        pathKK = "";
                    } else if (dataKK.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKK = "";
                    }
                } else {
                    if (dataKK.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKK = "";
                    }
                }

                promiseDateKK = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKK = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_5) {
            String path = this.pathNPWP;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathNPWP = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataNPWP.get("isPDF").equalsIgnoreCase("0")) {
                        pathNPWP = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfNPWP = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataNPWP.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfNPWP = data.getStringExtra("pathPDF");
                        pathNPWP = "";
                    } else if (dataNPWP.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfNPWP = "";
                    }
                } else {
                    if (dataNPWP.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfNPWP = "";
                    }
                }

                promiseDateNpwp = "";
                dataNPWP.put("promiseDate", "");
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathNPWP = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_6) {
            String path = this.pathBuktikeu;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathBuktikeu = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataBuktikeu.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfBuktiKeuangan = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfBuktiKeuangan = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataBuktikeu.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfBuktiKeuangan = data.getStringExtra("pathPDF");
                        pathBuktikeu = "";
                    } else if (dataBuktikeu.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfBuktiKeuangan = "";
                    }
                } else {
                    if (dataBuktikeu.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfBuktiKeuangan = "";
                    }
                }

                promiseDateBuktikeu = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathBuktikeu = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_7) {
            String path = this.pathRekeningkoran;
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getData() != null && dataRekeningkoran.get("isPDF").equalsIgnoreCase("0")) {
                        pathRekeningkoran = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfRekeningKoran = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataRekeningkoran.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfRekeningKoran = data.getStringExtra("pathPDF");
                        pathRekeningkoran = "";
                    } else if (dataRekeningkoran.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfRekeningKoran = "";
                    }
                } else {
                    if (dataRekeningkoran.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfRekeningKoran = "";
                    }
                }

                promiseDateRekeningkoran = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathRekeningkoran = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_8) {
            String path = this.pathCoverTabungan;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathCoverTabungan = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataCoverTabungan.get("isPDF").equalsIgnoreCase("0")) {
                        pathCoverTabungan = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfCoverTabungan = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataCoverTabungan.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfCoverTabungan = data.getStringExtra("pathPDF");
                        pathCoverTabungan = "";
                    } else if (dataCoverTabungan.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfCoverTabungan = "";
                    }
                } else {
                    if (dataCoverTabungan.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfCoverTabungan = "";
                    }
                }

                promiseDateCoverTabungan = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathCoverTabungan = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_9) {
            String path = this.pathBuktiKepemilikanRumah;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathBuktiKepemilikanRumah = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataBuktiKepemilikanRumah.get("isPDF").equalsIgnoreCase("0")) {
                        pathBuktiKepemilikanRumah = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfBuktiKepemilikanRumah = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataBuktiKepemilikanRumah.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfBuktiKepemilikanRumah = data.getStringExtra("pathPDF");
                        pathBuktiKepemilikanRumah = "";
                    } else if (dataBuktiKepemilikanRumah.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfBuktiKepemilikanRumah = "";
                    }
                } else {
                    if (dataBuktiKepemilikanRumah.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfBuktiKepemilikanRumah = "";
                    }
                }

                promiseDateBuktiKepemilikanRumah = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathBuktiKepemilikanRumah = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_10) {
            String path = this.pathKontrakKerja;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathKontrakKerja = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataKontrakKerja.get("isPDF").equalsIgnoreCase("0")) {
                        pathKontrakKerja = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfKontrakKerja = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataKontrakKerja.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfKontrakKerja = data.getStringExtra("pathPDF");
                        pathKontrakKerja = "";
                    } else if (dataKontrakKerja.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKontrakKerja = "";
                    }
                } else {
                    if (dataKontrakKerja.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKontrakKerja = "";
                    }
                }

                promiseDateKontrakKerja = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKontrakKerja = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_11) {
            String path = this.pathKeteranganDomisili;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathKeteranganDomisili = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataKeteranganDomisili.get("isPDF").equalsIgnoreCase("0")) {
                        pathKeteranganDomisili = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfKeteranganDomisili = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataKeteranganDomisili.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfKeteranganDomisili = data.getStringExtra("pathPDF");
                        pathKeteranganDomisili = "";
                    } else if (dataKeteranganDomisili.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKeteranganDomisili = "";
                    }
                } else {
                    if (dataKeteranganDomisili.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKeteranganDomisili = "";
                    }
                }

                promiseDateKeteranganDomisili = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKeteranganDomisili = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_12) {
            String path = this.pathBukuNikah;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathBukuNikah = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataBukuNikah.get("isPDF").equalsIgnoreCase("0")) {
                        pathBukuNikah = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfBukuNikah = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataBukuNikah.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfBukuNikah = data.getStringExtra("pathPDF");
                        pathBukuNikah = "";
                    } else if (dataBukuNikah.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfBukuNikah = "";
                    }
                } else {
                    if (dataBukuNikah.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfBukuNikah = "";
                    }
                }

                promiseDateBukuNikah = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathBukuNikah = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_13) {
            String path = this.pathKtpPasangan;
            if (resultCode == RESULT_OK) {
                promiseDateKtpPasangan = "";
                dataKtpPasangan.put("promiseDate", "");

                this.pathKtpPasangan = data.getStringExtra("path") != null ? data.getStringExtra("path") : ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();

                ocrResultKtpPasangan = (OCRResult) data.getSerializableExtra("dataOCR");

                if (data.getSerializableExtra("dataKTP") != null) {
                    dataKtpPasangan = (HashMap<String, String>) data.getSerializableExtra("dataKTP");
                }

                if (data.getData() != null) {
                    dataKtpPasangan.put("is_camera", "1");
                } else {
                    dataKtpPasangan.put("is_camera", "0");
                }

                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKtpPasangan = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_14) {
            String path = this.pathKartuNamaPasangan;
            if (resultCode == RESULT_OK) {
                promiseDateKartuNamaPasangan = "";
                dataKartuNamaPasangan.put("promiseDate", "");

                if (data.getSerializableExtra(CameraCardNameActivity.OCR_KEY_CARDNAME) != null) {
                    dataOCRKartuNamaPasangan = (HashMap<String, String>) data.getSerializableExtra(CameraCardNameActivity.OCR_KEY_CARDNAME);
                }

                if (data.getData() != null) {
                    dataKartuNamaPasangan.put("is_camera", "1");
                } else {
                    dataKartuNamaPasangan.put("is_camera", "0");
                }

                this.pathKartuNamaPasangan = data.getStringExtra("path") != null ? data.getStringExtra("path") : ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKartuNamaPasangan = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_15) {
            String path = this.pathKKSTNK;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathKKSTNK = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataKKSTNK.get("isPDF").equalsIgnoreCase("0")) {
                        pathKKSTNK = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfKKSTNK = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataKKSTNK.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfKKSTNK = data.getStringExtra("pathPDF");
                        pathKKSTNK = "";
                    } else if (dataKKSTNK.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKKSTNK = "";
                    }
                } else {
                    if (dataKKSTNK.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfKKSTNK = "";
                    }
                }

                promiseDateKKSTNK = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKKSTNK = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_16) {
            String path = this.pathKTPSTNK;
            if (resultCode == RESULT_OK) {
                promiseDateKTPSTNK = "";
                dataKTPSTNK.put("promiseDate", "");

                this.pathKTPSTNK = data.getStringExtra("path") != null ? data.getStringExtra("path") : ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();

                ocrResultKtpStnk = (OCRResult) data.getSerializableExtra("dataOCR");

                if (data.getSerializableExtra("dataKTP") != null) {
                    dataKTPSTNK = (HashMap<String, String>) data.getSerializableExtra("dataKTP");
                }

                if (data.getData() != null) {
                    dataKTPSTNK.put("is_camera", "1");
                } else {
                    dataKTPSTNK.put("is_camera", "0");
                }

                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathKTPSTNK = "";
            }
        } else if (requestCode == CameraUtility.REQUEST_IMAGE_CAPTURE_19) {
            String path = this.pathSlipGaji;
            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    if (data.getData() != null) {
//                        pathKKSTNK = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
//                    }
//                }

                if (data != null) {
                    if (data.getData() != null && dataSlipGaji.get("isPDF").equalsIgnoreCase("0")) {
                        pathSlipGaji = ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath();
                        pathPdfSlipGaji = "";
                    } else if (data.getStringExtra("pathPDF") != null && dataSlipGaji.get("isPDF").equalsIgnoreCase("1")) {
                        pathPdfSlipGaji = data.getStringExtra("pathPDF");
                        pathSlipGaji = "";
                    } else if (dataSlipGaji.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfSlipGaji = "";
                    }
                } else {
                    if (dataSlipGaji.get("isPDF").equalsIgnoreCase("0")) {
                        pathPdfSlipGaji = "";
                    }
                }

                promiseDateSlipGaji = "";
                showPopupWindow(selectedDocumentType);
            } else {
                ImageUtility.deleteImage(path);
                pathSlipGaji = "";
            }
        }
    }

    private void setListToggle() {
        listToggleMenuItems = new ArrayList<>();
        listToggleMenuItems.add(new ToggleMenuItem("KTP*", "-", false, true));
        listToggleMenuItems.add(new ToggleMenuItem("SPK*", "-", false, true));
        listToggleMenuItems.add(new ToggleMenuItem("Keterangan Pekerjaan / Usaha*", "-", false, true));
        listToggleMenuItems.add(new ToggleMenuItem("Kartu Keluarga", "-", false, true));
        listToggleMenuItems.add(new ToggleMenuItem("NPWP", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("Bukti Keuangan", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("Rekening Koran", "-", false, true));
        listToggleMenuItems.add(new ToggleMenuItem("Cover Tabungan", "-", false, true));
        listToggleMenuItems.add(new ToggleMenuItem("Bukti Kepemilikan Rumah", "-", false, true));
        listToggleMenuItems.add(new ToggleMenuItem("Izin Praktek / Kontrak Kerja", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("SIUP / TDP / Keterangan Domisili", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("Buku Nikah", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("KTP Istri/Suami", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("Keterangan Pekerjaan / Usaha Istri / Suami", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("KK A/N STNK", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("KTP A/N STNK", "-", false, false));
        listToggleMenuItems.add(new ToggleMenuItem("Slip Gaji", "-", false, false));
//        listToggleMenuItems.add(new ToggleMenuItem("Alamat tinggal (beda dengan KTP)", "-", false, false));

        toggleMenuListAdapter = new ToggleMenuListAdapter(this, listToggleMenuItems);
        updateSlideNumbering();

        //Set layout manager type for recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvToggleMenu.setLayoutManager(layoutManager);
        rvToggleMenu.setItemAnimator(new DefaultItemAnimator());
        rvToggleMenu.setNestedScrollingEnabled(false);
        rvToggleMenu.setAdapter(toggleMenuListAdapter);
    }

    private void slideMenu() {
        if (show) {
            show = false;
            imgToggle.setImageResource(R.drawable.ic_chevron_white_left);

            ObjectAnimator animX = ObjectAnimator.ofFloat(toggleMenu,
                    View.TRANSLATION_X, 0, 1 * toggleMenu.getWidth());
            animX.setDuration(300);
            animX.start();
            ObjectAnimator animX1 = ObjectAnimator.ofFloat(toggleButton,
                    View.TRANSLATION_X, 0, 1 * toggleMenu.getWidth());
            animX1.setDuration(300);
            animX1.start();
        } else {
            show = true;
            imgToggle.setImageResource(R.drawable.ic_chevron_white_right);

            ObjectAnimator animX = ObjectAnimator.ofFloat(toggleMenu,
                    View.TRANSLATION_X, 1 * toggleMenu.getWidth(), -1 * toggleMenu.getWidth());
            animX.setDuration(300);
            animX.start();
            ObjectAnimator animX1 = ObjectAnimator.ofFloat(toggleButton,
                    View.TRANSLATION_X, 1 * toggleMenu.getWidth(), 0);
            animX1.setDuration(300);
            animX1.start();
        }
    }

    public void showPopupWindow(UploadDocumentItem.DocumentType documentType) {
        selectedDocumentType = documentType;
        String path = "";
        if (documentType.toString().equals(UploadDocumentItem.DocumentType.KTP.toString())) {
            path = pathKtp;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.SPK.toString())) {
            path = pathSPK;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KartuNama.toString())) {
            path = pathKartuNama;
            Log.d("singo", "image on show popup : " + path);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KK.toString())) {
            path = pathKK;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.NPWP.toString())) {
            path = pathNPWP;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.BuktiKeuangan.toString())) {
            path = pathBuktikeu;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.RekeningKoran.toString())) {
            path = pathRekeningkoran;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.CoverBukuTabungan.toString())) {
            path = pathCoverTabungan;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.BuktiKepemilikanRumah.toString())) {
            path = pathBuktiKepemilikanRumah;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KontrakKerja.toString())) {
            path = pathKontrakKerja;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KeteranganDomisili.toString())) {
            path = pathKeteranganDomisili;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.BukuNikah.toString())) {
            path = pathBukuNikah;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KTPIstriSuami.toString())) {
            path = pathKtpPasangan;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KartuNamaIstriSuami.toString())) {
            path = pathKartuNamaPasangan;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KKSTNK.toString())) {
            path = pathKKSTNK;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KTPSTNK.toString())) {
            path = pathKTPSTNK;
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.SlipGaji.toString())) {
            path = pathSlipGaji;
        }

        if (documentType.toString().equals(UploadDocumentItem.DocumentType.KTP.toString())) {
            String name = "KTP_" + customerName;
            PopupUploadKtp.inputKTP(UploadDocumentActivity.this, lyParent, new PopupUploadKtp.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKtp != null && !pathKtp.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKTP.clear();
                                ocrResult = null;
                                pathKtp = "";
                                imgAttachmentKTP.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKtp);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKTP.setImageBitmap(smallBitmap);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.errorLog, "showPopupWindow", "PopupUpload.inputKTP", e.getMessage());
                    }

                    if (isSaved) {
                        toggleMenuListAdapter.setMenuChecked(0, true, "");
                        updateSlideNumbering();

                        dataKTP = datas;

                        if (!dataKTP.isEmpty() && !pathKtp.isEmpty()) {
                            txtDefaultKTP.setText(!dataKTP.get("nik").equals("") ? dataKTP.get("nik") : "Preview Data");
                        }
                    }

                    filteringMenu();
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKtp = imagePath;
                    dataKTP = data;
                }
            }, path, customerId, name, dataOCR, dataKTP, ocrResult);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.SPK.toString())) {
            String name = "SPK_" + customerName;
            PopupUploadSPK.inputSPK(this, lyParent, new PopupUploadSPK.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathSPK != null && !pathSPK.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataSPK.clear();
                                pathSPK = "";
                                nomorSPK = "";
                                imgAttachmentSPK.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathSPK);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentSPK.setImageBitmap(smallBitmap);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.errorLog, "PopupUpload.inputSPK", "Exception", e.getMessage());
                    }

                    if (isSaved) {
                        toggleMenuListAdapter.setMenuChecked(1, true, "");
                        updateSlideNumbering();

                        dataSPK = datas;

                        if (!dataSPK.isEmpty() && !pathSPK.isEmpty()) {
                            txtDefaultSPK.setText(!dataSPK.get("spk").equals("") ? dataSPK.get("spk") : "Preview Data");
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> datas) {
                    pathSPK = imagePath;
                    dataSPK = datas;
                }
            }, path, customerId, name, nomorSPK, dataSPK);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KartuNama.toString())) {
            String name = "KartuNama_" + customerName;
            PopupUpload.inputKartuNama(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKartuNama != null && !pathKartuNama.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKartuNama.clear();
                                dataOCRKartuNama.clear();
                                pathKartuNama = "";
                                imgAttachmentKartuNama.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKartuNama);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKartuNama.setImageBitmap(smallBitmap);
                        }
                    } catch (Exception e) {

                    }

                    if (isSaved) {
                        toggleMenuListAdapter.setMenuChecked(2, true, "");
                        updateSlideNumbering();

                        dataKartuNama = datas;

                        if (!dataKartuNama.isEmpty() && !pathKartuNama.isEmpty()) {
                            txtDefaultKartuNama.setText(!dataKartuNama.get("perusahaan").equals("") ? dataKartuNama.get("perusahaan") : "Preview Data");
                        }

                        Log.d("singo", "imagePath isSaved : " + datas.get("imagePath"));

                        if (datas.get("imagePath") == null || datas.get("imagePath").equals("")) {
                            pathKartuNama = "";
                            imgAttachmentKartuNama.setImageBitmap(null);
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKartuNama = imagePath;
                    dataKartuNama = data;
                }
            }, path, customerId, name, dataOCRKartuNama, dataKartuNama);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KK.toString())) {
            String name = "KartuKeluarga_" + customerName;
            PopupUpload.inputKartuKeluarga(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKK != null && !pathKK.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKK.clear();
                                pathKK = "";
                                pathPdfKK = "";
                                imgAttachmentKK.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKK);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKK.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentKK.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input KartuKeluarga save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateKK = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataKK = datas;

                        toggleMenuListAdapter.setMenuChecked(3, true, promiseDateKK);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathKK) || !isEmptyPath(pathPdfKK)) || !isEmptyPath(promiseDateKK)) {
                            txtDefaultKK.setText(!isEmptyPath(promiseDateKK) ? promiseDateKK : (!isEmptyPath(pathPdfKK) ? new File(pathPdfKK).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKK = imagePath;
                    dataKK = data;
                }
            }, path, pathPdfKK, customerId, name, promiseDateKK, dataKK);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.NPWP.toString())) {
            String name = "NPWP_" + customerName;
            PopupUpload.inputNPWP(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathNPWP != null && !pathNPWP.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataNPWP.clear();
                                pathNPWP = "";
                                pathPdfNPWP = "";
                                imgAttachmentNPWP.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathNPWP);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentNPWP.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentNPWP.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input npwp save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateNpwp = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataNPWP = datas;

                        toggleMenuListAdapter.setMenuChecked(4, true, promiseDateNpwp);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathNPWP) || !isEmptyPath(pathPdfNPWP)) || !isEmptyPath(promiseDateNpwp)) {
                            txtDefaultNPWP.setText(!isEmptyPath(promiseDateNpwp) ? promiseDateNpwp : (!isEmptyPath(pathPdfNPWP) ? new File(pathPdfNPWP).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathNPWP = imagePath;
                    dataNPWP = data;
                }
            }, path, pathPdfNPWP, customerId, name, dataNPWP);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.BuktiKeuangan.toString())) {
            String name = "BuktiKeuangan_" + customerName;
            PopupUpload.inputBuktiKeuangan(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathBuktikeu != null && !pathBuktikeu.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataBuktikeu.clear();
                                pathBuktikeu = "";
                                pathPdfBuktiKeuangan = "";
                                imgAttachmentBuktiKeuangan.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathBuktikeu);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentBuktiKeuangan.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentBuktiKeuangan.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input BuktiKeuangan save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateBuktikeu = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataBuktikeu = datas;

                        toggleMenuListAdapter.setMenuChecked(5, true, promiseDateBuktikeu);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathBuktikeu) || !isEmptyPath(pathPdfBuktiKeuangan)) || !isEmptyPath(promiseDateBuktikeu)) {
                            txtDefaultBuktiKeuangan.setText(!isEmptyPath(promiseDateBuktikeu) ? promiseDateBuktikeu : (!isEmptyPath(pathPdfBuktiKeuangan) ? new File(pathPdfBuktiKeuangan).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathBuktikeu = imagePath;
                    dataBuktikeu = data;
                }
            }, path, pathPdfBuktiKeuangan, customerId, name, promiseDateBuktikeu, dataBuktikeu);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.RekeningKoran.toString())) {
            String name = "RekeningKoran_" + customerName;
            PopupUpload.inputRekeningKoran(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && (pathRekeningkoran != null && !pathRekeningkoran.equals("")) || (pathPdfRekeningKoran != null && !pathPdfRekeningKoran.equals(""))) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataRekeningkoran.clear();
                                pathRekeningkoran = "";
                                pathPdfRekeningKoran = "";
                                imgAttachmentRekeningKoran.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathRekeningkoran);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentRekeningKoran.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentRekeningKoran.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input RekeningKoran save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateRekeningkoran = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataRekeningkoran = datas;

                        toggleMenuListAdapter.setMenuChecked(6, true, promiseDateRekeningkoran);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathRekeningkoran) || !isEmptyPath(pathPdfRekeningKoran)) || !isEmptyPath(promiseDateRekeningkoran)) {
                            txtDefaultRekeningKoran.setText(!isEmptyPath(promiseDateRekeningkoran) ? promiseDateRekeningkoran : (!isEmptyPath(pathPdfRekeningKoran) ? new File(pathPdfRekeningKoran).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathRekeningkoran = imagePath;
                    dataRekeningkoran = data;
                }
            }, path, pathPdfRekeningKoran, customerId, name, promiseDateRekeningkoran, dataRekeningkoran);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.CoverBukuTabungan.toString())) {
            String name = "CoverTabungan_" + customerName;
            PopupUpload.inputCoverTabungan(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathCoverTabungan != null && !pathCoverTabungan.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataCoverTabungan.clear();
                                pathCoverTabungan = "";
                                pathPdfCoverTabungan = "";
                                imgAttachmentCoverBukuTabungan.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathCoverTabungan);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentCoverBukuTabungan.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentCoverBukuTabungan.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input CoverBukuTabungan save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateCoverTabungan = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataCoverTabungan = datas;

                        toggleMenuListAdapter.setMenuChecked(7, true, promiseDateCoverTabungan);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathCoverTabungan) || !isEmptyPath(pathPdfCoverTabungan)) || !isEmptyPath(promiseDateCoverTabungan)) {
                            txtDefaultCoverTabungan.setText(!isEmptyPath(promiseDateCoverTabungan) ? promiseDateCoverTabungan : (!isEmptyPath(pathPdfCoverTabungan) ? new File(pathPdfCoverTabungan).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathCoverTabungan = imagePath;
                    dataCoverTabungan = data;
                }
            }, path, pathPdfCoverTabungan, customerId, name, promiseDateCoverTabungan, dataCoverTabungan);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.BuktiKepemilikanRumah.toString())) {
            String name = "BuktiKepemilikanRumah_" + customerName;
            PopupUpload.inputBuktiKepemilikanRumah(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathBuktiKepemilikanRumah != null && !pathBuktiKepemilikanRumah.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataBuktiKepemilikanRumah.clear();
                                pathBuktiKepemilikanRumah = "";
                                pathPdfBuktiKepemilikanRumah = "";
                                imgAttachmentBuktiKepemilikanRumah.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathBuktiKepemilikanRumah);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentBuktiKepemilikanRumah.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentBuktiKepemilikanRumah.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input BuktiKepemilikanRumah save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateBuktiKepemilikanRumah = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataBuktiKepemilikanRumah = datas;

                        toggleMenuListAdapter.setMenuChecked(8, true, promiseDateBuktiKepemilikanRumah);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathBuktiKepemilikanRumah) || !isEmptyPath(pathPdfBuktiKepemilikanRumah)) || !isEmptyPath(promiseDateBuktiKepemilikanRumah)) {
                            txtDefaultBuktiKepemilikanRumah.setText(!isEmptyPath(promiseDateBuktiKepemilikanRumah) ? promiseDateBuktiKepemilikanRumah : (!isEmptyPath(pathPdfBuktiKepemilikanRumah) ? new File(pathPdfBuktiKepemilikanRumah).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathBuktiKepemilikanRumah = imagePath;
                    dataBuktiKepemilikanRumah = data;
                }
            }, path, pathPdfBuktiKepemilikanRumah, customerId, name, promiseDateBuktiKepemilikanRumah, dataBuktiKepemilikanRumah);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KontrakKerja.toString())) {
            String name = "KontrakKerja_" + customerName;
            PopupUpload.inputKontrakKerja(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKontrakKerja != null && !pathKontrakKerja.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKontrakKerja.clear();
                                pathKontrakKerja = "";
                                pathPdfKontrakKerja = "";
                                imgAttachmentKontrakKerja.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKontrakKerja);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKontrakKerja.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentKontrakKerja.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input KontrakKerja save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateKontrakKerja = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataKontrakKerja = datas;

                        toggleMenuListAdapter.setMenuChecked(9, true, promiseDateKontrakKerja);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathKontrakKerja) || !isEmptyPath(pathPdfKontrakKerja)) || !isEmptyPath(promiseDateKontrakKerja)) {
                            txtDefaultKontrakKerja.setText(!isEmptyPath(promiseDateKontrakKerja) ? promiseDateKontrakKerja : (!isEmptyPath(pathPdfKontrakKerja) ? new File(pathPdfKontrakKerja).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKontrakKerja = imagePath;
                    dataKontrakKerja = data;
                }
            }, path, pathPdfKontrakKerja, customerId, name, promiseDateKontrakKerja, dataKontrakKerja);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KeteranganDomisili.toString())) {
            String name = "KeteranganDomisili_" + customerName;
            PopupUpload.inputKeteranganDomisili(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKeteranganDomisili != null && !pathKeteranganDomisili.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKeteranganDomisili.clear();
                                pathKeteranganDomisili = "";
                                pathPdfKeteranganDomisili = "";
                                imgAttachmentKeteranganDomisili.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKeteranganDomisili);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKeteranganDomisili.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentKeteranganDomisili.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input KeteranganDomisili save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateKeteranganDomisili = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataKeteranganDomisili = datas;

                        toggleMenuListAdapter.setMenuChecked(10, true, promiseDateKeteranganDomisili);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathKeteranganDomisili) || !isEmptyPath(pathPdfKeteranganDomisili)) || !isEmptyPath(promiseDateKeteranganDomisili)) {
                            txtDefaultKetDomisili.setText(!isEmptyPath(promiseDateKeteranganDomisili) ? promiseDateKeteranganDomisili : (!isEmptyPath(pathPdfKeteranganDomisili) ? new File(pathPdfKeteranganDomisili).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKeteranganDomisili = imagePath;
                    dataKeteranganDomisili = data;
                }
            }, path, pathPdfKeteranganDomisili, customerId, name, promiseDateKeteranganDomisili, dataKeteranganDomisili);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.BukuNikah.toString())) {
            String name = "BukuNikah_" + customerName;
            PopupUpload.inputBukuNikah(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathBukuNikah != null && !pathBukuNikah.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataBukuNikah.clear();
                                pathBukuNikah = "";
                                pathPdfBukuNikah = "";
                                imgAttachmentBukuNikah.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathBukuNikah);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentBukuNikah.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentBukuNikah.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input BukuNikah save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateBukuNikah = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataBukuNikah = datas;

                        toggleMenuListAdapter.setMenuChecked(11, true, promiseDateBukuNikah);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathBukuNikah) || !isEmptyPath(pathPdfBukuNikah)) || !isEmptyPath(promiseDateBukuNikah)) {
                            txtDefaultBukuNikah.setText(!isEmptyPath(promiseDateBukuNikah) ? promiseDateBukuNikah : (!isEmptyPath(pathPdfBukuNikah) ? new File(pathPdfBukuNikah).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathBukuNikah = imagePath;
                    dataBukuNikah = data;
                }
            }, path, pathPdfBukuNikah, customerId, name, promiseDateBukuNikah, dataBukuNikah);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KTPIstriSuami.toString())) {
            String name = "KtpPasangan_" + customerName;
            PopupUpload.inputKtpPasangan(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKtpPasangan != null && !pathKtpPasangan.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKtpPasangan.clear();
                                ocrResultKtpPasangan = null;
                                pathKtpPasangan = "";
                                imgAttachmentKtpPasangan.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKtpPasangan);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKtpPasangan.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentKtpPasangan.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input KtpPasangan save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateKtpPasangan = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataKtpPasangan = datas;

                        toggleMenuListAdapter.setMenuChecked(12, true, promiseDateKtpPasangan);
                        updateSlideNumbering();

                        if (!isEmptyPath(pathKtpPasangan) || !isEmptyPath(promiseDateKtpPasangan)) {
                            txtDefaultKTPPasangan.setText(!isEmptyPath(dataKtpPasangan.get("nik")) ? dataKtpPasangan.get("nik") : !isEmptyPath(promiseDateKtpPasangan) ? promiseDateKtpPasangan : "Preview Data");
                            ocrResultKtpPasangan = null;
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKtpPasangan = imagePath;
                    dataKtpPasangan = data;
                }
            }, path, pathPdfKtpPasangan, customerId, name, ocrResultKtpPasangan, dataKtpPasangan);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KartuNamaIstriSuami.toString())) {
            String name = "KartuNamaPasangan_" + customerName;
            PopupUpload.inputKartuNamaPasangan(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKartuNamaPasangan != null && !pathKartuNamaPasangan.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKartuNamaPasangan.clear();
                                dataOCRKartuNamaPasangan.clear();
                                pathKartuNamaPasangan = "";
                                imgAttachmentKartuNamaPasangan.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKartuNamaPasangan);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKartuNamaPasangan.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentKartuNamaPasangan.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input KartuNamaPasangan save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateKartuNamaPasangan = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataKartuNamaPasangan = datas;

                        toggleMenuListAdapter.setMenuChecked(13, true, promiseDateKartuNamaPasangan);
                        updateSlideNumbering();

                        if ((!dataKartuNamaPasangan.isEmpty() && !pathKartuNamaPasangan.isEmpty()) || !promiseDateKartuNamaPasangan.isEmpty()) {
                            txtDefaultKartuNamaPasangan.setText(!isEmptyPath(dataKartuNamaPasangan.get("perusahaan")) ? dataKartuNamaPasangan.get("perusahaan") : !isEmptyPath(promiseDateKartuNamaPasangan) ? promiseDateKartuNamaPasangan : "Preview Data");
                        }

                        if (datas.get("imagePath") == null || datas.get("imagePath").equals("")) {
                            //Log.d("singo", "image path kosong");
                            pathKartuNamaPasangan = "";
                            imgAttachmentKartuNamaPasangan.setImageBitmap(null);
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKartuNamaPasangan = imagePath;
                    dataKartuNamaPasangan = data;
                }
            }, path, pathPdfKartuNamaPasangan, customerId, name, dataOCRKartuNamaPasangan, dataKartuNamaPasangan);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KKSTNK.toString())) {
            String name = "KKSTNK_" + customerName;
            PopupUpload.inputKKSTNK(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKKSTNK != null && !pathKKSTNK.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKKSTNK.clear();
                                pathKKSTNK = "";
                                pathPdfKKSTNK = "";
                                imgAttachmentKKSTNK.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKKSTNK);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKKSTNK.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentKKSTNK.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input KKSTNK save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateKKSTNK = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataKKSTNK = datas;

                        toggleMenuListAdapter.setMenuChecked(14, true, promiseDateKKSTNK);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathKKSTNK) || !isEmptyPath(pathPdfKKSTNK)) || !isEmptyPath(promiseDateKKSTNK)) {
                            txtDefaultKKSTNK.setText(!isEmptyPath(promiseDateKKSTNK) ? promiseDateKKSTNK : (!isEmptyPath(pathPdfKKSTNK) ? new File(pathPdfKKSTNK).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKKSTNK = imagePath;
                    dataKKSTNK = data;
                }
            }, path, pathPdfKKSTNK, customerId, name, promiseDateKKSTNK, dataKKSTNK);
        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.KTPSTNK.toString())) {
            String name = "KTPSTNK_" + customerName;
            PopupUpload.inputKTPSTNK(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathKTPSTNK != null && !pathKTPSTNK.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataKTPSTNK.clear();
                                ocrResultKtpStnk = null;
                                pathKTPSTNK = "";
                                pathPdfKTPSTNK = "";
                                imgAttachmentKTPSTNK.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKTPSTNK);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentKTPSTNK.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentKTPSTNK.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input KTPSTNK save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateKTPSTNK = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataKTPSTNK = datas;

                        toggleMenuListAdapter.setMenuChecked(15, true, promiseDateKTPSTNK);
                        updateSlideNumbering();

                        if ((dataKTPSTNK != null && !pathKTPSTNK.isEmpty()) || !promiseDateKTPSTNK.isEmpty()) {
                            txtDefaultKTPSTNK.setText(!isEmptyPath(dataKTPSTNK.get("nik")) ? dataKTPSTNK.get("nik") : !isEmptyPath(promiseDateKTPSTNK) ? promiseDateKTPSTNK : "Preview Data");
                            ocrResultKtpStnk = null;
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKTPSTNK = imagePath;
                    dataKTPSTNK = data;
                }
            }, path, pathPdfKTPSTNK, customerId, name, ocrResultKtpStnk, dataKTPSTNK);

        } else if (documentType.toString().equals(UploadDocumentItem.DocumentType.SlipGaji.toString())) {
            String name = "SlipGaji" + customerName;
            PopupUpload.inputSlipGaji(this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {
                    if (!isSaved && pathSlipGaji != null && !pathSlipGaji.equals("")) {
                        if (datas != null) {
                            if (datas.get("is_saved") == null) {
                                dataSlipGaji.clear();
                                pathSlipGaji = "";
                                pathPdfSlipGaji = "";
                                imgAttachmentSlipGaji.setImageBitmap(null);
                            }
                        }
                    }

                    try {
                        Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathSlipGaji);
                        if (bitmap != null) {
                            Bitmap smallBitmap = ImageUtility.resizeImage(bitmap);
                            imgAttachmentSlipGaji.setImageBitmap(smallBitmap);
                        } else {
                            imgAttachmentSlipGaji.setImageBitmap(null);
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.warningLog, "input SlipGaji save listener", "image is null");
                    }

                    if (isSaved) {
                        promiseDateSlipGaji = datas.get("promiseDate") != null ? datas.get("promiseDate") : "";
                        dataSlipGaji = datas;

                        toggleMenuListAdapter.setMenuChecked(16, true, promiseDateSlipGaji);
                        updateSlideNumbering();

                        if ((!isEmptyPath(pathSlipGaji) || !isEmptyPath(pathPdfSlipGaji)) || !isEmptyPath(promiseDateSlipGaji)) {
                            txtDefaultSlipGaji.setText(!isEmptyPath(promiseDateSlipGaji) ? promiseDateSlipGaji : (!isEmptyPath(pathPdfSlipGaji) ? new File(pathPdfSlipGaji).getName() : "Preview Data"));
                        }
                    }
                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathSlipGaji = imagePath;
                    dataSlipGaji = data;
                }
            }, path, pathPdfSlipGaji, customerId, name, promiseDateSlipGaji, dataSlipGaji);
        }
    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
        currDate.setDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .setDateRange(currDate, null)
                .setDoneText("Yes")
                .setCancelText("No");
        cdp.show(getSupportFragmentManager(), "Promise Date");
    }

    private void submit() {
        progressDialog = DialogUtility.createProgressDialog(UploadDocumentActivity.this, "", "Please wait...");
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

        if (isUpdate != 1) {
            if (!validasiLTV()) {
                if (validasiMandatoryDocument() && validasiPromiseDate()) {
                    inquiryParam = SubmitParameters.inquiryParam;
                    UserSession userSession = SharedPreferenceUtils.getUserSession(this);

                    Log.d(TAG, "is update " + isUpdate + " " + JSONProcessor.toJSON(inquiryParam));

                    /*Customer*/
                    Customer customer = new Customer();
                    customer.setId_no(dataKTP.get("nik"));
                    customer.setId_expire_date("2111-12-12 00:00:00.000");
                    String jenisKelamin = dataKTP.get("jenisKelamin");
                    if (jenisKelamin.equals("LAKI - LAKI")) {
                        customer.setGender("M");
                    } else if (jenisKelamin.equals("PEREMPUAN")) {
                        customer.setGender("F");
                    }
                    customer.setBirth_place(dataKTP.get("tempatLahir"));
                    customer.setBirth_date(dataKTP.get("tanggalLahir"));
                    //customer.setBirth_date("2012-12-12 00:00:00.000");
                    String statusPerkawinan = dataKTP.get("statusPerkawinan");

                    customer.setMarital_status(statusPerkawinan);
                    customer.setReligion(dataKTP.get("agama"));
                    inquiryParam.setCustomer(customer);

                    /*spouse*/
                    if (SelectedData.IsMarried && !pathKtpPasangan.equals("") && dataKtpPasangan.get("promiseDate").equals("")) {
                        Spouse spouse = new Spouse();
                        spouse.setSpouse_name(dataKtpPasangan.get("nama").toUpperCase());
                        spouse.setSpouse_id_no(dataKtpPasangan.get("nik"));
                        spouse.setSpouse_birth_place(dataKtpPasangan.get("tempatLahir"));
                        spouse.setSpouse_birth_date(dataKtpPasangan.get("tanggalLahir"));
                        //spouse.setSpouse_birth_date("2012-12-12 00:00:00.000");
                        String jenisKelaminPasangan = dataKtpPasangan.get("jenisKelamin");
                        if (jenisKelaminPasangan != null) {
                            if (jenisKelaminPasangan.equals("LAKI - LAKI")) {
                                spouse.setSpouse_gender("M");
                            } else if (jenisKelaminPasangan.equals("PEREMPUAN")) {
                                spouse.setSpouse_gender("F");
                            }
                        }

                        String alamat = dataKtpPasangan.get("alamat");
                        String kelurahan = dataKtpPasangan.get("kelurahan");
                        String kecamatan = dataKtpPasangan.get("kecamatan");
                        String kota = dataKtpPasangan.get("kota");
                        String agama = dataKtpPasangan.get("agama");

                        if (alamat != null && !alamat.equals("")) {
                            alamat = alamat.toUpperCase();
                        }

                        if (kelurahan != null && !kelurahan.equals("")) {
                            kelurahan = kelurahan.toUpperCase();
                        }

                        if (kecamatan != null && !kecamatan.equals("")) {
                            kecamatan = kecamatan.toUpperCase();
                        }

                        if (kota != null && !kota.equals("")) {
                            kota = kota.toUpperCase();
                        }

                        if (agama != null && !agama.equals("")) {
                            agama = agama.toUpperCase();
                        }

                        spouse.setSpouse_address(alamat);
                        spouse.setSpouse_rt(dataKtpPasangan.get("rt"));
                        spouse.setSpouse_rw(dataKtpPasangan.get("rw"));
                        spouse.setSpouse_kelurahan(kelurahan);
                        spouse.setSpouse_kecamatan(kecamatan);
                        spouse.setSpouse_zipcode(dataKtpPasangan.get("zipCode"));
                        spouse.setSpouse_city(kota);
                        spouse.setSpouse_religion(agama);
//                    spouse.setSpouse_job("PROF");
                        inquiryParam.setSpouse(spouse);
                    } else {
                        inquiryParam.setSpouse(null);
                    }

                    /*legaladdress*/
                    LegalAddress legalAddress = new LegalAddress();
                    legalAddress.setAddress(dataKTP.get("alamat").toUpperCase());
                    legalAddress.setRt(dataKTP.get("rt"));
                    legalAddress.setRw(dataKTP.get("rw"));
                    legalAddress.setKelurahan(dataKTP.get("kelurahan").toUpperCase());
                    legalAddress.setKecamatan(dataKTP.get("kecamatan").toUpperCase());
                    legalAddress.setCity(dataKTP.get("kota").toUpperCase());
                    legalAddress.setZipcode(dataKTP.get("zipCode"));
                    inquiryParam.setLegal_address(legalAddress);


                    /*residence_address*/
                    if (!switchInfoAlamatTinggal.isChecked()) {
                        ResidenceAddress residenceAddress = new ResidenceAddress();
                        residenceAddress.setAddress(edAlamat.getText().toString().toUpperCase());
                        residenceAddress.setRt(edRT.getText().toString());
                        residenceAddress.setRw(edRW.getText().toString());
                        residenceAddress.setKelurahan(edKelurahan.getText().toString().toUpperCase());
                        residenceAddress.setKecamatan(edKecamatan.getText().toString().toUpperCase());
                        residenceAddress.setCity(edKota.getText().toString().toUpperCase());
                        residenceAddress.setZipcode(edZipCode.getText().toString());
                        inquiryParam.setResidence_address(residenceAddress);
                    } else {
                        ResidenceAddress residenceAddress = new ResidenceAddress();
                        residenceAddress.setAddress(dataKTP.get("alamat").toUpperCase());
                        residenceAddress.setRt(dataKTP.get("rt"));
                        residenceAddress.setRw(dataKTP.get("rw"));
                        residenceAddress.setKelurahan(dataKTP.get("kelurahan").toUpperCase());
                        residenceAddress.setKecamatan(dataKTP.get("kecamatan").toUpperCase());
                        residenceAddress.setCity(dataKTP.get("kota").toUpperCase());
                        residenceAddress.setZipcode(dataKTP.get("zipCode"));
                        inquiryParam.setResidence_address(residenceAddress);
                    }

                    /*job address*/
                    JobAddress jobAddress = new JobAddress();
                    jobAddress.setAddress(dataKartuNama.get("alamat").toUpperCase());
                    jobAddress.setRt(dataKartuNama.get("rt"));
                    jobAddress.setRw(dataKartuNama.get("rw"));
                    jobAddress.setKelurahan(dataKartuNama.get("kelurahan").toUpperCase());
                    jobAddress.setKecamatan(dataKartuNama.get("kecamatan").toUpperCase());
                    jobAddress.setCity(dataKartuNama.get("kota").toUpperCase());
                    jobAddress.setZipcode(dataKartuNama.get("zipcode"));
                    inquiryParam.setJob_address(jobAddress);

                    /*customer_job*/
                    CustomerJob customerJob = new CustomerJob();
                    customerJob.setProfession_name(dataKTP.get("pekerjaan").toUpperCase());
                    customerJob.setCompany_name(dataKartuNama.get("perusahaan").toUpperCase());
                    customerJob.setJob_position(dataKartuNama.get("jabatan").toUpperCase());
                    inquiryParam.setCustomer_job(customerJob);

                    /*spouse_job*/
                    if (SelectedData.IsMarried && switchInfoPasanganBekerja.isChecked()) {
                        SpouseJob spouseJob = new SpouseJob();
                        String job = dataKtpPasangan.get("pekerjaan");
                        String perusahaan = dataKartuNamaPasangan.get("perusahaan");
                        String jabatan = dataKartuNamaPasangan.get("jabatan");

                        if (job != null && !job.equals("")) {
                            job = job.toUpperCase();
                        }

                        if (perusahaan != null && !perusahaan.equals("")) {
                            perusahaan = perusahaan.toUpperCase();
                        }

                        if (jabatan != null && !jabatan.equals("")) {
                            jabatan = jabatan.toUpperCase();
                        }

                        spouseJob.setProfession_name(job);
                        spouseJob.setCompany_name(perusahaan);
                        spouseJob.setJob_position(jabatan);
                        inquiryParam.setSpouse_job(spouseJob);
                    } else {
                        //Log.d("singo","dataKartuNamaPasangan null");
                        inquiryParam.setSpouse_job(null);
                    }

                    /*no_kk*/
//                inquiryParam.setNo_kk(dataKK.get("noKK"));
                    inquiryParam.setNo_kk("");

                    /*no_npwp*/
                    inquiryParam.setNo_npwp(dataNPWP.get("npwp"));

                    /*no_spk*/
                    if (dataSPK != null) {
                        inquiryParam.setNo_spk(dataSPK.get("spk"));
                    }

                    /*office*/
                    String office = "";
            /*if(userSession.getUser().getType().equals("so")){
                office = userSession.getUser().getBranch().getCode();
            }else{
                office = userSession.getUser().getDealer().getBranch().getCode();
            }*/
                    office = userSession.getUser().getDealer().getBranch().getCode();
                    inquiryParam.setOffince(office);

                    /*document*/
                    Document document = new Document();
                    /*required*/
                    List<DocumentUpload> required = new ArrayList<>();
                    //ktp
                    DocumentUpload ktp = new DocumentUpload();
                    ktp.setCode(DocTcData.ktp.getCode());
                    if (pathKtp != null && !pathKtp.equals("")) {
                        ktp.setStatus("true");
                    } else {
                        ktp.setStatus("false");
                    }
                    ktp.setPromise_date("");
                    required.add(ktp);
                    //spk
                    DocumentUpload spk = new DocumentUpload();
                    spk.setCode(DocTcData.spk.getCode());
                    if (pathSPK != null && !pathSPK.equals("")) {
                        spk.setStatus("true");
                    } else {
                        spk.setStatus("false");
                    }
                    spk.setPromise_date("");
                    required.add(spk);
                    //kartunama
                    DocumentUpload kartuNama = new DocumentUpload();
                    kartuNama.setCode(DocTcData.kartuNama.getCode());
                    /*if(pathKartuNama != null && !pathKartuNama.equals("")){
                        kartuNama.setStatus("true");
                    }else{
                        kartuNama.setStatus("false");
                    }*/
                    kartuNama.setStatus("true");
                    kartuNama.setPromise_date("");
                    required.add(kartuNama);
                    document.setRequired(required);

                    /*optional*/
                    List<DocumentUpload> optional = new ArrayList<>();
                    //kk
                    DocumentUpload kk = new DocumentUpload();
                    kk.setCode(DocTcData.kk.getCode());
                    if (pathKK != null && !pathKK.equals("")) {
                        kk.setStatus("true");
                        kk.setPromise_date("");
                        optional.add(kk);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateKK.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc kk " + e.getMessage());
                        }
                        kk.setStatus("false");
                        kk.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(kk);
                        }
                    }

                    //Bukti Keuangan
                    DocumentUpload buktiKeuangan = new DocumentUpload();
                    buktiKeuangan.setCode(DocTcData.laporanKeuangan.getCode());
                    if (pathBuktikeu != null && !pathBuktikeu.equals("")) {
                        buktiKeuangan.setStatus("true");
                        buktiKeuangan.setPromise_date("");
                        optional.add(buktiKeuangan);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateBuktikeu.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
//                        progressDialog.dismiss();
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc BuktiKeuangan " + e.getMessage());
                        }
                        buktiKeuangan.setStatus("false");
                        buktiKeuangan.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(buktiKeuangan);
                        }
                    }

                    //Rekening Koran
                    DocumentUpload rekeningKoran = new DocumentUpload();
                    rekeningKoran.setCode(DocTcData.rekeningKoran.getCode());
                    if (pathRekeningkoran != null && !pathRekeningkoran.equals("")) {
                        rekeningKoran.setStatus("true");
                        rekeningKoran.setPromise_date("");
                        optional.add(rekeningKoran);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateRekeningkoran.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc rekeningKoran " + e.getMessage());
                        }
                        rekeningKoran.setStatus("false");
                        rekeningKoran.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(rekeningKoran);
                        }
                    }

                    //Cover buku tabungan
                    DocumentUpload coverBukuTabungan = new DocumentUpload();
                    coverBukuTabungan.setCode(DocTcData.coverTabungan.getCode());
                    if (pathCoverTabungan != null && !pathCoverTabungan.equals("")) {
                        coverBukuTabungan.setStatus("true");
                        coverBukuTabungan.setPromise_date("");
                        optional.add(coverBukuTabungan);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateCoverTabungan.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc coverBukuTabungan " + e.getMessage());
                        }
                        coverBukuTabungan.setStatus("false");
                        coverBukuTabungan.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(coverBukuTabungan);
                        }
                    }

                    //Bukti kepemilikan rumah
                    DocumentUpload pbb = new DocumentUpload();
                    pbb.setCode(DocTcData.pbb.getCode());
                    if (pathBuktiKepemilikanRumah != null && !pathBuktiKepemilikanRumah.equals("")) {
                        pbb.setStatus("true");
                        pbb.setPromise_date("");
                        optional.add(pbb);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateBuktiKepemilikanRumah.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc pbb " + e.getMessage());
                        }
                        pbb.setStatus("false");
                        pbb.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(pbb);
                        }
                    }

                    DocumentUpload bukuNikah = new DocumentUpload();
                    bukuNikah.setCode(DocTcData.suratMenikah.getCode());
                    if (pathBukuNikah != null && !pathBukuNikah.equals("")) {
                        bukuNikah.setStatus("true");
                        bukuNikah.setPromise_date("");
                        optional.add(bukuNikah);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateBukuNikah.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc bukuNikah " + e.getMessage());
                        }
                        bukuNikah.setStatus("false");
                        bukuNikah.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(bukuNikah);
                        }
                    }

                    //Kontrak kerja
                    DocumentUpload kontrakKerja = new DocumentUpload();
                    kontrakKerja.setCode(DocTcData.ijinPraktek.getCode());
                    if (pathKontrakKerja != null && !pathKontrakKerja.equals("")) {
                        kontrakKerja.setStatus("true");
                        kontrakKerja.setPromise_date("");
                        optional.add(kontrakKerja);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateKontrakKerja.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc kontrakKerja " + e.getMessage());
                        }
                        kontrakKerja.setStatus("false");
                        kontrakKerja.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(kontrakKerja);
                        }
                    }

                    //Keterangan Domisili
                    DocumentUpload keteranganDomisili = new DocumentUpload();
                    keteranganDomisili.setCode(DocTcData.keteranganDomisili.getCode());
                    if (pathKeteranganDomisili != null && !pathKeteranganDomisili.equals("")) {
                        keteranganDomisili.setStatus("true");
                        keteranganDomisili.setPromise_date("");
                        optional.add(keteranganDomisili);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateKeteranganDomisili.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc keteranganDomisili " + e.getMessage());
                        }
                        keteranganDomisili.setStatus("false");
                        keteranganDomisili.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(keteranganDomisili);
                        }
                    }

                    if (!switchInfoSTNKCustomer.isChecked()) {
                        //KKSTNK
                        DocumentUpload kkstnk = new DocumentUpload();
                        kkstnk.setCode(DocTcData.KKSTNK.getCode());
                        if (pathKKSTNK != null && !pathKKSTNK.equals("")) {
                            kkstnk.setStatus("true");
                            kkstnk.setPromise_date("");
                            optional.add(kkstnk);
                        } else {
                            String promDate = "";
                            try {
                                String[] promise = promiseDateKKSTNK.split("/");
                                promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                            } catch (Exception e) {
                                LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc kkstnk " + e.getMessage());
                            }
                            kkstnk.setStatus("false");
                            kkstnk.setPromise_date(promDate);

                            if (!promDate.equals("")) {
                                optional.add(kkstnk);
                            }
                        }

                        //KTPSTNK
                        DocumentUpload ktpstnk = new DocumentUpload();
                        ktpstnk.setCode(DocTcData.KTPSTNK.getCode());
                        if (pathKTPSTNK != null && !pathKTPSTNK.equals("")) {
                            ktpstnk.setStatus("true");
                            ktpstnk.setPromise_date("");
                            optional.add(ktpstnk);
                        } else {
                            String promDate = "";
                            try {
                                String[] promise = promiseDateKTPSTNK.split("/");
                                promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                            } catch (Exception e) {
                                LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc ktpstnk " + e.getMessage());
                            }
                            ktpstnk.setStatus("false");
                            ktpstnk.setPromise_date(promDate);

                            if (!promDate.equals("")) {
                                optional.add(ktpstnk);
                            }
                        }
                    }

                    //Ktp Pasangan
                    DocumentUpload ktppasangan = new DocumentUpload();
                    ktppasangan.setCode(DocTcData.ktpSpouse.getCode());
                    if (pathKtpPasangan != null && !pathKtpPasangan.equals("")) {
                        ktppasangan.setStatus("true");
                        ktppasangan.setPromise_date("");
                        optional.add(ktppasangan);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateKtpPasangan.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc ktppasangan " + e.getMessage());
                        }
                        ktppasangan.setStatus("false");
                        ktppasangan.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(ktppasangan);
                        }
                    }

                    if (switchInfoPasanganBekerja.isChecked()) {
                        //kartu nama Pasangan
                        DocumentUpload kartunamapasangan = new DocumentUpload();
                        kartunamapasangan.setCode(DocTcData.kartuNamaSpouse.getCode());
                        if (pathKartuNamaPasangan != null && !pathKartuNamaPasangan.equals("")) {
                            kartunamapasangan.setStatus("true");
                            kartunamapasangan.setPromise_date("");
                            optional.add(kartunamapasangan);
                        } else {
                            String promDate = "";
                            try {
                                String[] promise = promiseDateKartuNamaPasangan.split("/");
                                promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                            } catch (Exception e) {
                                LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc kartunamapasangan " + e.getMessage());
                            }
                            kartunamapasangan.setStatus("false");
                            kartunamapasangan.setPromise_date(promDate);

                            if (!promDate.equals("")) {
                                optional.add(kartunamapasangan);
                            }
                        }
                    }

                    //npwp
                    DocumentUpload npwp = new DocumentUpload();
                    npwp.setCode(DocTcData.npwp.getCode());
                    if (pathNPWP != null && !pathNPWP.equals("")) {
                        npwp.setStatus("true");
                        npwp.setPromise_date("");
                        optional.add(npwp);
                    } else {
                        String promDate = "";
                        try {
                            String[] promise = promiseDateNpwp.split("/");
                            promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                        } catch (Exception e) {
                            LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc npwp " + e.getMessage());
                        }
                        npwp.setStatus("false");
                        npwp.setPromise_date(promDate);

                        if (!promDate.equals("")) {
                            optional.add(npwp);
                        }
                    }

                    if (SelectedData.customerType == 1){
                        DocumentUpload slipgaji = new DocumentUpload();
                        slipgaji.setCode(DocTcData.slipGaji.getCode());
                        if (pathSlipGaji != null && !pathSlipGaji.equals("")) {
                            slipgaji.setStatus("true");
                            slipgaji.setPromise_date("");
                            optional.add(slipgaji);
                        } else {
                            String promDate = "";
                            try {
                                String[] promise = promiseDateSlipGaji.split("/");
                                promDate = promise[2] + "-" + promise[1] + "-" + promise[0] + " 00:00:00.000";
                            } catch (Exception e) {
                                LogUtility.logging(TAG, LogUtility.errorLog, "submit", "doc slipGaji " + e.getMessage());
                            }
                            slipgaji.setStatus("false");
                            slipgaji.setPromise_date(promDate);

                            if (!promDate.equals("")) {
                                optional.add(slipgaji);
                            }
                        }
                    }

                    document.setOptional(optional);
                    inquiryParam.setDocument(document);

                    /*npk*/
                    //inquiryParam.setNpk(userSession.getUser().getNpk_no());
                    String npk_no = userSession.getUser().getNpk_no();
                    if (npk_no != null || !npk_no.equals("")) {
                        inquiryParam.setNpk(npk_no);
                    }
                    /*ktp*/
                    inquiryParam.setKtp_salesman(userSession.getUser().getKtp_no());

                    /*dummy suubsidy_amt*/
                    //inquiryParam.setSubsidy_amt("0");

                    /*auth*/
                    inquiryParam.setAuth_key("");
                    inquiryParam.setLead_id("");

                    /*asset_owner*/
                    if (!switchInfoSTNKCustomer.isChecked()) {
                        String nama = dataKTPSTNK.get("nama");
                        String alamat = dataKTPSTNK.get("alamat");
                        String kelurahan = dataKTPSTNK.get("kelurahan");
                        String kecamatan = dataKTPSTNK.get("kecamatan");
                        String kota = dataKTPSTNK.get("kota");

                        if (nama != null && !nama.equals("")) {
                            nama = nama.toUpperCase();
                        }

                        if (alamat != null && !nama.equals("")) {
                            alamat = alamat.toUpperCase();
                        }

                        if (kelurahan != null && !kelurahan.equals("")) {
                            kelurahan = kelurahan.toUpperCase();
                        }

                        if (kecamatan != null && !kecamatan.equals("")) {
                            kecamatan = kecamatan.toUpperCase();
                        }

                        if (kota != null && !kota.equals("")) {
                            kota = kota.toUpperCase();
                        }

                        AssetOwner assetOwner = new AssetOwner();
                        assetOwner.setOwner_name(nama);
                        assetOwner.setId_no(dataKTPSTNK.get("nik"));
                        assetOwner.setAddr(alamat);
                        assetOwner.setRt(dataKTPSTNK.get("rt"));
                        assetOwner.setRw(dataKTPSTNK.get("rw"));
                        assetOwner.setKelurahan(kelurahan);
                        assetOwner.setKecamatan(kecamatan);
                        assetOwner.setCity(kota);
                        assetOwner.setZipcode(dataKTPSTNK.get("zipCode"));
                        inquiryParam.setAsset_owner(assetOwner);

                        processEncode();
                    } else {
                        AssetOwner assetOwner = new AssetOwner();
                        assetOwner.setOwner_name(dataKTP.get("nama").toUpperCase());
                        assetOwner.setId_no(dataKTP.get("nik"));
                        assetOwner.setAddr(dataKTP.get("alamat").toUpperCase());
                        assetOwner.setRt(dataKTP.get("rt"));
                        assetOwner.setRw(dataKTP.get("rw"));
                        assetOwner.setKelurahan(dataKTP.get("kelurahan").toUpperCase());
                        assetOwner.setKecamatan(dataKTP.get("kecamatan").toUpperCase());
                        assetOwner.setCity(dataKTP.get("kota").toUpperCase());
                        assetOwner.setZipcode(dataKTP.get("zipCode"));
                        inquiryParam.setAsset_owner(assetOwner);

                        processEncode();
                    }

//                  progressDialog.dismiss();
                    Log.d(TAG, "saveTemporaryParam " + JSONProcessor.toJSON(inquiryParam));
                } else {
                    progressDialog.dismiss();
                }
            } else {
                Toast.makeText(UploadDocumentActivity.this, "LTV anda tidak sesuai", Toast.LENGTH_SHORT).show();
            }
        } else {
            processEncode();
        }
    }

    private void filteringMenu() {
        if (SelectedData.wop != null) {
            if (SelectedData.wop.contains("Auto Debit")) {
                Log.d("wop", SelectedData.wop);
                lyItemCoverTabungan.setVisibility(View.VISIBLE);
                toggleMenuListAdapter.setMenuVisible(7, true);
            } else {
                Log.d("wop", "bukan auto");
                lyItemCoverTabungan.setVisibility(View.GONE);
                toggleMenuListAdapter.setMenuVisible(7, false);
            }
        } else {
            lyItemCoverTabungan.setVisibility(View.VISIBLE);
            toggleMenuListAdapter.setMenuVisible(7, true);
        }

        if (SelectedData.isNPWP) {
            lyItemNPWP.setVisibility(View.VISIBLE);
            toggleMenuListAdapter.setMenuVisible(4, true);
        } else {
            lyItemNPWP.setVisibility(View.GONE);
            toggleMenuListAdapter.setMenuVisible(4, false);
        }

        if (SelectedData.IsMarried) {
            lyItemBukuNikah.setVisibility(View.VISIBLE);
            lySwitchPasanganBekerja.setVisibility(View.VISIBLE);
            lyItemKTPPasangan.setVisibility(View.VISIBLE);
            toggleMenuListAdapter.setMenuVisible(11, true);
            toggleMenuListAdapter.setMenuVisible(12, true);
        } else {
            lyItemBukuNikah.setVisibility(View.GONE);
            lySwitchPasanganBekerja.setVisibility(View.GONE);
            lyItemKTPPasangan.setVisibility(View.GONE);
            lyItemKartuNamaPasangan.setVisibility(View.GONE);
            toggleMenuListAdapter.setMenuVisible(11, false);
            toggleMenuListAdapter.setMenuVisible(12, false);
            toggleMenuListAdapter.setMenuVisible(13, false);
        }

        if (SelectedData.customerType == 1) {
            lyItemKontrakKerja.setVisibility(View.GONE);
            lyItemKeteranganDomisili.setVisibility(View.GONE);
            lyItemBuktiKeuangan.setVisibility(View.GONE);
            lyItemSlipGaji.setVisibility(View.VISIBLE);
            toggleMenuListAdapter.setMenuVisible(16, true);
            toggleMenuListAdapter.setMenuVisible(5, false);
            toggleMenuListAdapter.setMenuVisible(9, false);
            toggleMenuListAdapter.setMenuVisible(10, false);
        } else if (SelectedData.customerType == 2) {
            lyItemSlipGaji.setVisibility(View.GONE);
            lyItemKeteranganDomisili.setVisibility(View.GONE);
            lyItemBuktiKeuangan.setVisibility(View.GONE);
            toggleMenuListAdapter.setMenuVisible(16, false);
            toggleMenuListAdapter.setMenuVisible(5, false);
            toggleMenuListAdapter.setMenuVisible(9, true);
            toggleMenuListAdapter.setMenuVisible(10, false);
        } else if (SelectedData.customerType == 3) {
            lyItemSlipGaji.setVisibility(View.GONE);
            lyItemKontrakKerja.setVisibility(View.GONE);
            lyItemBuktiKeuangan.setVisibility(View.GONE);
            toggleMenuListAdapter.setMenuVisible(16, false);
            toggleMenuListAdapter.setMenuVisible(5, false);
            toggleMenuListAdapter.setMenuVisible(9, false);
            toggleMenuListAdapter.setMenuVisible(10, true);
        } else if (SelectedData.customerType == 4) {
            lyItemSlipGaji.setVisibility(View.GONE);
            lyItemKontrakKerja.setVisibility(View.GONE);
            lyItemKeteranganDomisili.setVisibility(View.GONE);
            lyItemBuktiKeuangan.setVisibility(View.GONE);
            toggleMenuListAdapter.setMenuVisible(16, false);
            toggleMenuListAdapter.setMenuVisible(5, false);
            toggleMenuListAdapter.setMenuVisible(9, false);
            toggleMenuListAdapter.setMenuVisible(10, false);
        }

        updateSlideNumbering();
    }

    private void updateSlideNumbering() {
        txtCounChecklist.setText("" + toggleMenuListAdapter.countCheckList());
        txtCountMenu.setText("" + toggleMenuListAdapter.countMenu());
    }

    private void encodeKtp() {
        if (pathKtp != null && !pathKtp.equals("")) {
            Base64Image.with(this)
                    .encode(ImageUtility.loadBitmapFromPath(pathKtp))
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String base64) {
                            Log.d("pathKTP", DocTcData.ktp.getCode());
                            Log.d("pathKTP", pathKtp);
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.ktp.getCode(), "data:image/jpeg;base64," + base64);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.ktp.getCode(), pathKtp);
                            imageAttachmentsOffline.add(imageAttachment_offline);

                            /*HashMap<String,String> ktp = new HashMap<>();
                            String type = "image["+DocTcData.ktp+"]";
                            ktp.put(type,base64);
                            base64KTP = ktp;*/
                            encodeSPK();

                        /*String attachment = JSONProcessor.toJSON(imageAttachments);
                        Log.d("singo","imageku : "+attachment);*/


                            /*SubmitPresenter.submit(UploadDocumentActivity.this, new SubmitCallback() {
                                @Override
                                public void onSuccessSubmit() {
                                    progressDialog.dismiss();
                                    Toast.makeText(UploadDocumentActivity.this,"Success submit", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailedSubmit() {
                                    progressDialog.dismiss();
                                    Toast.makeText(UploadDocumentActivity.this,"Failed submit", Toast.LENGTH_SHORT).show();
                                }
                            },"inquiry",param,attachment);*/
                        }

                        @Override
                        public void onFailure() {
                            encodeSPK();
                        }
                    });
        } else {
            encodeSPK();
        }
    }

    private void encodeSPK() {
        if (pathSPK != null && !pathSPK.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathSPK);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.spk.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.spk.getCode(), pathSPK);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            /*HashMap<String,String> spk = new HashMap<>();
                            String type = "image["+DocTcData.spk+"]";
                            spk.put(type,s);
                            base64SPK = spk;*/
                            encodeKartuNama();
                        }

                        @Override
                        public void onFailure() {
                            encodeKartuNama();
                        }
                    });
        } else {
            encodeKartuNama();
        }
    }

    private void encodeKartuNama() {
        if (pathKartuNama != null && !pathKartuNama.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKartuNama);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.kartuNama.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.kartuNama.getCode(), pathKartuNama);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            /*HashMap<String,String> kartuNama = new HashMap<>();
                            String type = "image["+DocTcData.kartuNama+"]";
                            kartuNama.put(type,s);
                            base64KartuNama = kartuNama;*/
                            encodeKK();
                        }

                        @Override
                        public void onFailure() {
                            encodeKK();
                        }
                    });
        } else {
            encodeKK();
        }
    }

    private void encodeKK() {
        if (pathKK != null && !pathKK.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKK);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            Log.d("path", s);
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.kk.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.kk.getCode(), pathKK);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeBuktiKeuangan();
                        }

                        @Override
                        public void onFailure() {
                            encodeBuktiKeuangan();
                        }
                    });
        } else if (pathPdfKK != null && !pathPdfKK.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfKK);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.kk.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.kk.getCode(), pathPdfKK);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeBuktiKeuangan();
        } else {
            encodeBuktiKeuangan();
        }
    }

    private void encodeBuktiKeuangan() {
        if (pathBuktikeu != null && !pathBuktikeu.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathBuktikeu);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.laporanKeuangan.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.laporanKeuangan.getCode(), pathBuktikeu);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeRekeningKoran();
                        }

                        @Override
                        public void onFailure() {
                            encodeRekeningKoran();
                        }
                    });
        } else if (pathPdfBuktiKeuangan != null && !pathPdfBuktiKeuangan.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfBuktiKeuangan);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.laporanKeuangan.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.laporanKeuangan.getCode(), pathPdfBuktiKeuangan);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeRekeningKoran();
        } else {
            encodeRekeningKoran();
        }
    }

    private void encodeRekeningKoran() {
        if (pathRekeningkoran != null && !pathRekeningkoran.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathRekeningkoran);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.rekeningKoran.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.rekeningKoran.getCode(), pathRekeningkoran);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeCoverBukuTabungan();
                        }

                        @Override
                        public void onFailure() {
                            encodeCoverBukuTabungan();
                        }
                    });
        } else if (pathPdfRekeningKoran != null && !pathPdfRekeningKoran.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfRekeningKoran);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.rekeningKoran.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.rekeningKoran.getCode(), pathPdfRekeningKoran);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeCoverBukuTabungan();
        } else {
            encodeCoverBukuTabungan();
        }
    }

    private void encodeCoverBukuTabungan() {
        if (pathCoverTabungan != null && !pathCoverTabungan.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathCoverTabungan);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.coverTabungan.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.coverTabungan.getCode(), pathCoverTabungan);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeBuktiKepemilikanRumah();
                        }

                        @Override
                        public void onFailure() {
                            encodeBuktiKepemilikanRumah();
                        }
                    });
        } else if (pathPdfCoverTabungan != null && !pathPdfCoverTabungan.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfCoverTabungan);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.coverTabungan.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.coverTabungan.getCode(), pathPdfCoverTabungan);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeBuktiKepemilikanRumah();
        } else {
            encodeBuktiKepemilikanRumah();
        }
    }

    private void encodeBuktiKepemilikanRumah() {
        if (pathBuktiKepemilikanRumah != null && !pathBuktiKepemilikanRumah.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathBuktiKepemilikanRumah);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.pbb.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.pbb.getCode(), pathBuktiKepemilikanRumah);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeBukuNikah();
                        }

                        @Override
                        public void onFailure() {
                            encodeBukuNikah();
                        }
                    });
        } else if (pathPdfBuktiKepemilikanRumah != null && !pathPdfBuktiKepemilikanRumah.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfBuktiKepemilikanRumah);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.pbb.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.pbb.getCode(), pathPdfKK);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeBukuNikah();
        } else {
            encodeBukuNikah();
        }
    }

    private void encodeBukuNikah() {
        if (pathBukuNikah != null && !pathBukuNikah.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathBukuNikah);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.suratMenikah.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.suratMenikah.getCode(), pathBukuNikah);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeKontrakKerja();
                        }

                        @Override
                        public void onFailure() {
                            encodeKontrakKerja();
                        }
                    });
        } else if (pathPdfBukuNikah != null && !pathPdfBukuNikah.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfBukuNikah);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.suratMenikah.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.suratMenikah.getCode(), pathPdfBukuNikah);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeKontrakKerja();
        } else {
            encodeKontrakKerja();
        }
    }

    private void encodeKontrakKerja() {
        if (pathKontrakKerja != null && !pathKontrakKerja.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKontrakKerja);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.ijinPraktek.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.ijinPraktek.getCode(), pathKontrakKerja);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeKeteranganDomisili();
                        }

                        @Override
                        public void onFailure() {
                            encodeKeteranganDomisili();
                        }
                    });
        } else if (pathPdfKontrakKerja != null && !pathPdfKontrakKerja.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathKontrakKerja);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.ijinPraktek.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.ijinPraktek.getCode(), pathKontrakKerja);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeKeteranganDomisili();
        } else {
            encodeKeteranganDomisili();
        }
    }

    private void encodeKeteranganDomisili() {
        if (pathKeteranganDomisili != null && !pathKeteranganDomisili.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKeteranganDomisili);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.keteranganDomisili.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.keteranganDomisili.getCode(), pathKeteranganDomisili);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeKKSTNK();
                        }

                        @Override
                        public void onFailure() {
                            encodeKKSTNK();
                        }
                    });
        } else if (pathPdfKeteranganDomisili != null && !pathPdfKeteranganDomisili.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfKeteranganDomisili);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.keteranganDomisili.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.keteranganDomisili.getCode(), pathPdfKeteranganDomisili);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeKKSTNK();
        } else {
            encodeKKSTNK();
        }
    }

    private void encodeKKSTNK() {
        if (pathKKSTNK != null && !pathKKSTNK.equals("")) {
            //Log.d("singo","pathKKSTNK ga kosong");
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKKSTNK);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            //Log.d("singo","KKSTNK base64 : "+s);
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.KKSTNK.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.KKSTNK.getCode(), pathKKSTNK);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeKTPSTNK();
                        }

                        @Override
                        public void onFailure() {
                            encodeKTPSTNK();
                        }
                    });
        } else if (pathKKSTNK != null && !pathPdfKKSTNK.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfKKSTNK);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.KKSTNK.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.KKSTNK.getCode(), pathPdfKKSTNK);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeKTPSTNK();
        } else {
            encodeKTPSTNK();
        }
    }

    private void encodeKTPSTNK() {
        if (pathKTPSTNK != null && !pathKTPSTNK.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKTPSTNK);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.KTPSTNK.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.KTPSTNK.getCode(), pathKTPSTNK);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeNPWP();
                        }

                        @Override
                        public void onFailure() {
                            encodeNPWP();
                        }
                    });
        } else if (pathPdfKTPSTNK != null && !pathPdfKTPSTNK.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfKTPSTNK);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.KTPSTNK.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.KTPSTNK.getCode(), pathPdfKTPSTNK);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeNPWP();
        } else {
            encodeNPWP();
        }
    }

    private void encodeNPWP() {
        if (pathNPWP != null && !pathNPWP.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathNPWP);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.npwp.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.npwp.getCode(), pathNPWP);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeKtpPasangan();
                        }

                        @Override
                        public void onFailure() {
                            encodeKtpPasangan();
                        }
                    });
        } else if (pathPdfNPWP != null && !pathPdfNPWP.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfNPWP);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.npwp.getCode(), "data:application/pdf;base64," + s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.npwp.getCode(), pathPdfNPWP);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            encodeKtpPasangan();
        } else {
            encodeKtpPasangan();
        }
    }

    private void encodeKtpPasangan() {
        if (pathKtpPasangan != null && !pathKtpPasangan.equals("")) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKtpPasangan);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.ktpSpouse.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.ktpSpouse.getCode(), pathKtpPasangan);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeKartuNamaPasangan();
                        }

                        @Override
                        public void onFailure() {
                            encodeKartuNamaPasangan();
                        }
                    });
        } else {
            encodeKartuNamaPasangan();
        }
    }

    private void encodeKartuNamaPasangan() {
        if (pathKartuNamaPasangan != null && !pathKartuNamaPasangan.equals("") && switchInfoPasanganBekerja.isChecked()) {
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathKartuNamaPasangan);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.kartuNamaSpouse.getCode(), "data:image/jpeg;base64," + s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.kartuNamaSpouse.getCode(), pathKartuNamaPasangan);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            encodeSlipGaji();
                        }

                        @Override
                        public void onFailure() {
                            encodeSlipGaji();
                        }
                    });
        } else {
            encodeSlipGaji();
        }
    }

    private void encodeSlipGaji(){
        if(pathSlipGaji != null && !pathSlipGaji.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(pathSlipGaji);
            Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
            Base64Image.with(UploadDocumentActivity.this)
                    .encode(compressedBitmap)
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            Log.d("path", s);
                            ImageAttachment imageAttachment = new ImageAttachment(DocTcData.slipGaji.getCode(),"data:image/jpeg;base64,"+s);
                            imageAttachments.add(imageAttachment);
                            ImageAttachmentOffline imageAttachment_offline = new ImageAttachmentOffline(DocTcData.slipGaji.getCode(), pathSlipGaji);
                            imageAttachmentsOffline.add(imageAttachment_offline);
                            processSubmit();
                        }

                        @Override
                        public void onFailure() {
                            processSubmit();
                        }
                    });
        }else if (pathPdfSlipGaji != null && !pathPdfSlipGaji.equals("")) {
            String s = FileUtils.getBase64StringPdf(pathPdfSlipGaji);
            PdfAttachment pdfAttachment = new PdfAttachment(DocTcData.slipGaji.getCode(),"data:application/pdf;base64,"+s);
            pdfAttachments.add(pdfAttachment);
            PdfAttachmentOffline pdfAttachmentOffline = new PdfAttachmentOffline(DocTcData.slipGaji.getCode(), pathPdfSlipGaji);
            pdfAttachmentsOffline.add(pdfAttachmentOffline);
            processSubmit();
        }else{
            processSubmit();
        }
    }

//    private void processDraft(){
//        progressDialog = DialogUtility.createProgressDialog(UploadDocumentActivity.this,"","Please wait...");
//        if(!progressDialog.isShowing()){
//            progressDialog.show();
//        }
//        InquiryParam inquiryParam = SubmitParameters.inquiryParam;
//        final String param = JSONProcessor.toJSON(inquiryParam);
//        SubmitParameters.inquiryParam = inquiryParam;
//        LogUtility.logging(TAG, LogUtility.infoLog,"draft","param",param);
//        if (param.equals("null")) {
//            progressDialog.dismiss();
//        }
//
//        SubmitPresenter.submit(UploadDocumentActivity.this, new SubmitCallback() {
//                    @Override
//                    public void onSuccessSubmit(String formId) {
//                        progressDialog.dismiss();
//                        Toast.makeText(UploadDocumentActivity.this,"Success Draft", Toast.LENGTH_SHORT).show();
//                        //uploadKTP(formId);
//                        setResult(Activity.RESULT_OK);
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailedSubmit(String message) {
//                        progressDialog.dismiss();
//                        //Toast.makeText(UploadDocumentActivity.this,"Failed submit", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
//                                .setMessage(message)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                });
//                        builder.show();
//                    }
//                },
//                "draft",param,"","");
//
//    }

    private void processSubmit(){
        /*attachment*/
        final String attachment = JSONProcessor.toJSON(imageAttachments);
//        LogUtility.logging(TAG, LogUtility.infoLog,"submit","attachment",attachment);

        final String attachmentPdf = JSONProcessor.toJSON(pdfAttachments);
//        LogUtility.logging(TAG, LogUtility.infoLog,"submit","attachmentPdf",attachmentPdf);

        if(isUpdate == 1){
            SubmitPresenter.uploadDocument(UploadDocumentActivity.this, new UpdateDocumentCallback() {
                @Override
                public void onSuccessUpadateDocument() {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                            .setMessage("Sukses update dokumen")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Intent intent = new Intent();
                                    intent.putExtra("is_success_submit",true);
                                    setResult(Activity.RESULT_OK,intent);
                                    finish();
                                }
                            });
                    builder.show();
                }

                @Override
                public void onFailedUpdateDocument() {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                            .setMessage("Gagal update dokumen")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    finish();
                                }
                            });
                    builder.show();
                }

                @Override
                public void onLoopEnd() {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                            .setMessage("Gagal update dokumen")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    finish();
                                }
                            });toggleMenuListAdapter.setMenuVisible(14, true);
                    builder.show();
                }

                @Override
                public void onResultFailed() {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                            .setMessage("Gagal update dokumen")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    finish();
                                }
                            });
                    builder.show();
                }
            },formId,attachment,attachmentPdf);
        }else{
            /*submit param*/
            progressDialog.dismiss();
            NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
            PopupUtils.simpanKeOutboxSaved(UploadDocumentActivity.this, rootView, 2, new PopupUtils.SaveToDraftListener() {
                @Override
                public void isSaved(boolean isSaved) {

                    progressDialog.show();
                    final String param = JSONProcessor.toJSON(inquiryParam);
//                    final String attachments = JSONProcessor.toJSON(imageAttachments);
//                    final String attachmentsPdf = JSONProcessor.toJSON(pdfAttachments);
                    SubmitParameters.inquiryParam = inquiryParam;
                    LogUtility.logging(TAG, LogUtility.infoLog,"submit","param",param + "\n"
//                    + "image : " + attachments + "\n"
//                    + "pdf : "+ attachmentsPdf + "\n"
                    + "userid : "+ MinimumCUSDATADraft.user_id_);

//                    outbox_submits.clear();
//
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
//                    String currentDateandTime = sdf.format(new Date());
//
//                    Outbox_submit outbox_submit = new Outbox_submit();
//
//                    if (MinimumCUSDATADraft.user_id_.equals("")){
//                        outbox_submit.setUser_id("");
//                    }else {
//                        outbox_submit.setUser_id(MinimumCUSDATADraft.user_id_);
//                    }
//
//                    Log.d("user_id", MinimumCUSDATADraft.user_id_);
//
//                    outbox_submit.setOutboxId(userSession.getUser().getProfile().getFullname());
//                    outbox_submit.setForm(param);
//                    outbox_submit.setAttach(attachments);
//                    outbox_submit.setStatus("send");
//                    outbox_submit.setCustomerName(inquiryParam.getCustomer_name());
//                    outbox_submit.setDate(currentDateandTime);
//                    outbox_submit.setAttachPDF(attachmentsPdf);
//                    outbox_submits.add(outbox_submit);
//
//
//                    new Insert_submit().execute(outbox_submits);
//                }
//            });

                    final String attachmentOffline = JSONProcessor.toJSON(imageAttachmentsOffline);
//        LogUtility.logging(TAG, LogUtility.infoLog,"submit","attachment",attachment);

                    final String attachmentPdfOffline = JSONProcessor.toJSON(pdfAttachmentsOffline);

                    Log.d("dp_percentage", SelectedData.DpPercentage);
            SubmitPresenter.submit(UploadDocumentActivity.this, new SubmitCallback() {
                        @Override
                        public void onSuccessSubmit(String formId) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                                    .setMessage("Submit terkirim")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            SelectedData.isNPWP = false;
                                            SelectedData.IsMarried = false;
                                            outbox_submits.clear();
                                            if (MinimumCUSDATADraft.id_.equals(Long.valueOf(0))){
                                                Log.d("_id", "bukandraft");
                                            }else {
                                                Draft draft = Draft.findById(Draft.class, MinimumCUSDATADraft.id_);
                                                draft.delete();
                                                MinimumCUSDATADraft.id_ = Long.valueOf(0);
                                                MinimumCUSDATADraft.user_id_ = "";
                                            }
//                                          deleteCache(getApplicationContext());
                                            Intent intent = new Intent();
                                            intent.putExtra("is_success_submit",true);
                                            setResult(Activity.RESULT_OK,intent);
                                            finish();
                                        }
                                    });
                            builder.show();
                        }

                        @Override
                        public void onFailedSubmit(final String message) {
                            progressDialog.dismiss();
                            //Toast.makeText(UploadDocumentActivity.this,"Failed submit", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                                    .setMessage("Failed submit, save to outbox")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            outbox_submits.clear();
                                            SelectedData.isNPWP = false;
                                            SelectedData.IsMarried = false;
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
                                            String currentDateandTime = sdf.format(new Date());

                                            Outbox_submit outbox_submit = new Outbox_submit();

                                            if (MinimumCUSDATADraft.user_id_.equals("")){
                                                outbox_submit.setUser_id("");
                                            }else {
                                                outbox_submit.setUser_id(MinimumCUSDATADraft.user_id_);
                                            }

                                            Log.d("user_id", MinimumCUSDATADraft.user_id_);

                                            outbox_submit.setOutboxId(userSession.getUser().getProfile().getFullname());
                                            outbox_submit.setForm(param);
                                            outbox_submit.setAttach(attachmentOffline);
                                            outbox_submit.setStatus("failed");
                                            outbox_submit.setCustomerName(inquiryParam.getCustomer_name());
                                            outbox_submit.setDate(currentDateandTime);
                                            outbox_submit.setAttachPDF(attachmentPdfOffline);
                                            outbox_submit.setDp_percentage(SelectedData.DpPercentage);
                                            outbox_submits.add(outbox_submit);

                                            if (MinimumCUSDATADraft.id_.equals(Long.valueOf(0))){
                                                Log.d("_id", "bukandraft");
                                            }else {
                                                Draft draft = Draft.findById(Draft.class, MinimumCUSDATADraft.id_);
                                                draft.delete();
                                                MinimumCUSDATADraft.id_ = Long.valueOf(0);
                                                MinimumCUSDATADraft.user_id_ = "";
                                            }

                                            new Insert_submit().execute(outbox_submits);
//                                            deleteCache(getApplicationContext());
                                            setResult(Activity.RESULT_OK);
                                            finish();
                                            dialogInterface.dismiss();
                                        }
                                    });
                            builder.show();
                        }
                    },
                    "inquiry",param,attachment, MinimumCUSDATADraft.user_id_, attachmentPdf,
                    SelectedData.DpPercentage);
                }
             });
        }
    }

    class Insert_submit extends AsyncTask<List<Outbox_submit>,Void,Void> {
        List<Outbox_submit> outbox_submits;
        @Override
        protected Void doInBackground(List<Outbox_submit>... lists) {
            outbox_submits = lists[0];
            if(outbox_submits != null){
                Outbox_submitController.InsertOutboxSubmit(outbox_submits);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            Intent service = new Intent(getApplicationContext(), SubmitService.class);
//            startService(service);
//            progressDialog.dismiss();
//            setResult(Activity.RESULT_OK);
//            finish();
        }
    }

    /*upload image*/
    private void uploadKTP(final String id){
        if(pathKtp != null && !pathKtp.equals("")){
            Base64Image.with(this)
                    .encode(ImageUtility.loadBitmapFromPath(pathKtp))
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String base64) {
                            //Log.d("singo","form_id : "+id);
                            String dummyImage = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/4gKgSUNDX1BST0ZJTEUAAQEAAAKQbGNtcwQwAABtbnRyUkdCIFhZWiAH4gAEAAwAEAA4ABBhY3NwQVBQTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA9tYAAQAAAADTLWxjbXMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAtkZXNjAAABCAAAADhjcHJ0AAABQAAAAE53dHB0AAABkAAAABRjaGFkAAABpAAAACxyWFlaAAAB0AAAABRiWFlaAAAB5AAAABRnWFlaAAAB+AAAABRyVFJDAAACDAAAACBnVFJDAAACLAAAACBiVFJDAAACTAAAACBjaHJtAAACbAAAACRtbHVjAAAAAAAAAAEAAAAMZW5VUwAAABwAAAAcAHMAUgBHAEIAIABiAHUAaQBsAHQALQBpAG4AAG1sdWMAAAAAAAAAAQAAAAxlblVTAAAAMgAAABwATgBvACAAYwBvAHAAeQByAGkAZwBoAHQALAAgAHUAcwBlACAAZgByAGUAZQBsAHkAAAAAWFlaIAAAAAAAAPbWAAEAAAAA0y1zZjMyAAAAAAABDEoAAAXj///zKgAAB5sAAP2H///7ov///aMAAAPYAADAlFhZWiAAAAAAAABvlAAAOO4AAAOQWFlaIAAAAAAAACSdAAAPgwAAtr5YWVogAAAAAAAAYqUAALeQAAAY3nBhcmEAAAAAAAMAAAACZmYAAPKnAAANWQAAE9AAAApbcGFyYQAAAAAAAwAAAAJmZgAA8qcAAA1ZAAAT0AAACltwYXJhAAAAAAADAAAAAmZmAADypwAADVkAABPQAAAKW2Nocm0AAAAAAAMAAAAAo9cAAFR7AABMzQAAmZoAACZmAAAPXP/bAEMABQMEBAQDBQQEBAUFBQYHDAgHBwcHDwsLCQwRDxISEQ8RERMWHBcTFBoVEREYIRgaHR0fHx8TFyIkIh4kHB4fHv/bAEMBBQUFBwYHDggIDh4UERQeHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHv/CABEIAZABkAMBIgACEQEDEQH/xAAcAAEAAwEBAQEBAAAAAAAAAAAABQcIBgQBAgP/xAAVAQEBAAAAAAAAAAAAAAAAAAAAAf/aAAwDAQACEAMQAAABrYWAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASXR+bQRnON08jKXzVMdWZ2hY0oxcEYVk7qNOXSscfzfPoAAAAAAAAAAAAAAAAB0Ojc16UgFAAAAfn9CPi+kHERtkio4y7yZ+i9KjLPm1d5ay40hFlBrpjSqFixZxz2eMAAAAAAAAA9+n8s6mAlAAAAAAAAAAAAAz3yvu8NgAAAAAAAAH3VOVNTHpEoAAAAAAAAAAADxe3ljPQsAAAAAAAAAaZzNpInBKAAAAAAAAAAAArqxafKxFgAAAAAAAADQ+eL/OvEoAAAAAAAAAAAChL7zQkQKAAAAAAAAAXpRd2lgiUAAAAAjuZO3VdGlxqJjU0R+czR9aai86/S9o2mxaMZwI6zlfyAAAAAAAAAAFxU77DUPlzJ5jVX9YaZlAAAAA8kXPjjoyxBVMZdIoSM0cTLvj0fnCgAAAAAAAAAAAAH9f5Tpo79koAAAAAAAAEXmXUmWrPoAAAAAAAAAAAAHccPaRbglAAAAAAAAA/nlbVuWbPOAAAAAAAAAAAABd9IaJOlEoAAAAAAAADMWnc2JCCgAAAAAAAAAAAGpMv2QXEp1LcSnRcSnRcSnfhcas+4JQArUspTouJTouJTouLO3T8NZGAAAAAAAAAAAAAAAOi7gqWcvacKl7jokoADM+kssWfAAAAAAAAAAAAAAAAAAO+4HtC+hKAAABz+cbzoywAAAAAAAAAAAAAAAAAB13I9EaMEoAAAFU1P3vBWAAAAAAAAAAAAAAAAAAJyDkDTwlAAAHwzrzno89gAAAAAAAAAAAAAAAAAD1eX6atU7Ky2a4STOoRkgfsCNkuQM//AEsAAAAAAAAAAAAAAAAAAAAA+fv8iSlOZHc+PkgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB//xAAsEAAABQMDAwMDBQAAAAAAAAABAgMEBQAGQBITFhEwUCAhMQc2gBAjMzQ1/9oACAEBAAEFAvyajGaj92pbEqWlIOWJSrJ4kAgIeOtEelwfqomRQFo2PWBW34hSlbVizirZ6AmUtBxqUtWUKJ4CXJSkdIJ0Yhy+FtsdM73BABBRizUBSCiT0pa8UalLRaDSlnqUpaciFKW5LkpSKkkwOisTMgh6TOKIANKtWyoKw0WrS1tRJ6VtJiYVbP8AdS0XwCe2pcou2y7RbDjx0v8ANutXdnsNsOlzmv1N59h/FJfx5b9TZY4g/DU2trl3WrtQOLDj1icu/FdMbiwBtcLl3+r1c4tq/b+Xeam5O4tnn1QGXMq70ti2P/iYZjFLSj9knSk9EkpS6IotKXe1ClbwUpW65E1KXFLnoRERxbEP1ie45eJoU4uVmlSt4IgCt3uhpW6ZQ9Kz0spSsg/VoxjHHNsD+nR3LdOklE1U+2Zq2MJ4aKOJ7aiDUpaTAaPZ4dFLRegClsyxaUhZUlLNXSIZSDpygU6yxxqES2YjDuIuuDzEi7ipCgQmHLh1isy3092bxHZdTUPjLslLXN4inumPsOX9P0v3cVyGlzl2KloiMWTLpkcu109qCxZ4Ok1lfNNE9pri3QXTPZSZtCnL3Ncvc1y9zXL3Ncvc1y9zXL3Ncvc1y9zTOdmHlMhfm9EzcirGS5e5rl7muXua5e5rl7muXua5e5qUeGfvvAsomRd0ytE1MYWNaeqYU3pXwzKFkndMrRLTKJj2fYXPtoCOofC2KBRmO1cam1B+Gss2md7V8K6Ibw1n/cHa+oCnt4a2jaZ3tXyprmPDQHtN9q5lBVnfDRptEj2R9gcn3XHhmg9HfZlFdmN8OA9BSu9wFJ3e3pO6ow1J3BEHpOQYKUUxTei8FNuB8aUxiik/fJClPSydI3TKEqXnXMk0/Hb/xAAUEQEAAAAAAAAAAAAAAAAAAACQ/9oACAEDAQE/ARx//8QAFBEBAAAAAAAAAAAAAAAAAAAAkP/aAAgBAgEBPwEcf//EAEAQAAECAwIJCAcGBwAAAAAAAAECAwAEESFAEiIjNFFScXKSMFBhYmOCkbETIDGDocHRBTNBc4CBEBQkQkNTsv/aAAgBAQAGPwL9TSZZpSUrUCRhRYhpexyLZFz9qGKuSj6R0tmLQRzdL97/AJPqUcQlQ6RWMpJsHuCMzSncJEYvp291f1jJTrqR0pBjJzjRHWSYxfQL78Zmo7FAxjyUwPdmMZChtEe3mSU36crQisUXKsK2oEWyLQ3bIsQ6jdcjJzT6dtDGTnk95uMVyXX3iIzUK2LEVVIv03Yx2XE7Um+SZ7ZN2tijsu0sdZAMY0iz3U08osZU3urMZN99v9wYyU/Z1m4xJiXUOmoixlCukOCPQzDZbXStDdJc9qnzv0xbYmiR4XRo6Fp879MPa7ij8bpWE7L4+7WmC2T8IturStKAb5M9YBPibtKHsUeV8aa13fIXaTPZJF8lmdCCq7Su6fM3xwf60pT8/ndmBq4Q+N8mnNLpu3vVXTGIEY82wn3gi2dbOy2LFOr2Nxk5R5W0gRkpFI3l1jEbl0d2sZ1g7qBBJ9puziNV08rah9R0IZUqKfy83hapap5xk5JdeusCMlJsp3lExillvYj6xbOKTugCMecmD7wxVaio9Jv01+aPL+GO+0nasQHGlpWg+wg8pVUu0ragRVUgxwxYwpOxwxiPzCf3BjEnj3m4xJhhXiIsabXuuRbIundtirsu6jeQb2UsTDrQPtCVUrFVvOK2qMViVb0NC6Tg7Im+oQBXCUBASPYBS6TY7FXlfZRHaA+Ft1dTpQR8IF8C6VDbZOy6qHRFL5NvaAlN2dGhavO+LcI+8dP0u0ynQ6rzvkqNKcLxu05+ab3SGmtRATdpodavwvaV0wqGtNMZkzxmMyZ4zGZM8ZjMmeMxmTPGYzJnjMZkzxmMyZ4zGZM8Zj+n+yAoa2EQPGKzaJdvoQon1HZVuWbWEUtKuiMyZ4zGZM8ZjMmeMxmTPGYzJnjMZkzxmMyZ4zC5pSAgrpYD0cxZGVXg6ysURWcmu60PmYBblklY/uXjH1ppzS6eZwW5VSU6y8URWcmieq2PnGRlkYWsbTyC3NVJMYWm3mZdQDkTTxHJza+zp42czoGshQ5PAr944BzOxsV5cnKM7yuZ5TpXTk0oH+NoDmeT/NHJzRP4LwfDmeWVodT58lWHXddZV8eZ2T2ifPkpl0GhS0ojbTmgHRGUk2lbqiIykk6ndUDGN6dG1EZ4gbwIjEnGD7wRiqB9R4a9E/Hm6qSUnoMVbnJhPvDFk4pW8AYxvQubyPpCZd1ptFFYVUfj+nf/xAAqEAEAAQEGBQQCAwAAAAAAAAABEQAxQGGxwfAhQVFxoTBQgZEg0YDh8f/aAAgBAQABPyH+TS9oCUcCakmO6WtAy0x5DWLHiGVeZgihGxH23j/mH8DAsRZqEEPhZVCTsrwanDw5TwakHQbv3woXwSnxSEZdRE/ZTzJTnrVSkOc2CuQH1ShGwPz7JOevmH1Y8h0SagjGkH5Bla8/ZrNeMFTzk7mjUn98MqCWAdfrSwccxuVGyX1NpUnUvcuWal2AgSY1gAKgO4fZ5Kh/jtnNGE7pqSubDu3wld6+lfUNOQcxT7pyMjBPO6OPaZC/NwZWNgNVumIK8KLL7w8jyqugywcaUt1OV8NC0vsq4vG1zulp2ogbPFXyZcyfgHxN2RLnlb5xm4g/Cfu7b1gRpfIGbbO7Bk3ZzfEuKsgv1djEh5bre2ojsi9hjS7WG2y6ASL1WKkpY5MmdSYocp5CrVvG1rdXPek+TcgV4cVmacmGEmY0zcoq9W7daPOB9WWD3+IIqXSnmXSEJ6ckmgNgPKKzqvM0/tt6UpPxplWPDGag6X3eOmlAl4V47ZXPQKw/PqMlTao0pxINqQyqdl3tftoRmsdBU5x2yxqTL9E/bQ/0xrFeH4ZaUDy1AF7f4cthYxU+ViqY4oKZ+2b3Sbpgh9DjpfVkRA9ZYoh4EOxdMbT877x63TK67v5V4V8S2IfUwGbdTiCyoy9DF8BkswveXS6tYRryvjCIqHqAZhu2Bi8r4PFZn90t2j+P93uHgWvCkaiUowIu2Iw+wb2SxBpscDZW4tK3FpW4tK3FpW4tK3FpW4tK3FpW4tKYJPzfciKhjiW5PlA/COE6eKoXljW4tK3FpW4tK3FpW4tK3FpW4tK4y/k4IBp7FCs1yPK0qCGx/hX9u+bxs+KACA4fjG7JF7TB7McWDi1LBdo8aPDnnGPt+qjk45fnfQJpg+pKdVa5fPs0hQJIseT6cMWyOzH2fa/E6enwHBhuoS6ezoJen5+n3Yr8Qa+zq0/tjRZ6RqniB0VXKPZ2JfToVrLHQB7Ow1uT+kxFYcaESYCnop9nmbk3j6TEiwcSPNFns7G2qSoDeJbNK2SdYqwi93KoCGvLQKjJdsCTOhJBwZ/AVhln8j9e2wdKxk5A1LAdzOn5222Vaf4zMULKFI2Bgh7/AMd//9oADAMBAAIAAwAAABAIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIK2+m0EsI4IIIIIIIIIIIIIIIILfzzzzzxy799EU0cIIIIIIIIIIHzzzzzzzzzzzzzwMIIIIIIIIIHzzzzzzzzzzzzygoIIIIIIIIJXzzzzzzzzzzzzzoIIIIIIIIIK3zzzzzzzzzzzzyMIIIIIIIIIIHzzzzzzDbu02HMIIIIIIIIIIIfVzzzzzxz55+0IIIIIIIIIIIIIbzzzzzzzzzwkIIIIIIIIIIIIIfzzzzzzzzzzUIIIIIIIIIIIIInzzzzzzzzzy8IIIIIIIIIIIILnLLLbjzjLLKUIIIIIIIIIIIIIIYd35zyUIIIIIIIIIIIIIIIIIIPzzzzyEIIIIIIIIIIIIIIIIIIPTzzzysIIIIIIIIIIIIIIIIIJfzzzzgIIIIIIIIIIIIIIIIIIJbrzTzioIIIIIIIIIIIIIIIIIIIJLKNMIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIL/8QAGxEAAwEBAQEBAAAAAAAAAAAAAAERMEAgYHD/2gAIAQMBAT8Q+qhCE5VjCEITRdK6V0rZ5rClKUu1xhOp9L2Wb2RSlL4pSj4Z4fS/zv8A/8QAHREAAwEBAAMBAQAAAAAAAAAAAAERMEAQIGAxUP/aAAgBAgEBPxD6lspSlReR4UpSlKi5vpfS9l+5vZZvCMhCEJoyYUrKJ3pWzzWzzWzRCEITzCEEuClL5XG/Zcb9l/NjJ2T5j//EACoQAQABAQcDBAIDAQAAAAAAAAERACExQEFRkaFhcYEwULHBECCA0fDx/9oACAEBAAE/EP5NIUAMW5EgsoUOCZAL2AUtLr3iUeKWE1+9jCuWX+VcADNIl4+2ElQbzBzH4g0/B4Oy5sGjjtyUbgq3jHU+8VAChlG3vNf8oBwfjTQyk8qJQADc18LHejPIBj2CfFDK/TkgRSyXIbG3KWhLoGodPY1BYh2D7ouPUdL94hzTK9pKnilhO5/TqkbZ1EbaOvTf64oCweQSfNJKILgQ8LOa1akcexB4q+hYVPMqTAF43dhVrAzpNuLJ9ABejD7ouDCQaUpK14JOa6HkByUUIbOTvaUqo++3O8qFAl6keZKBnoMN43FIjKKq8C5q7XcGdgh4ps2BBWaAiiMO2EYqFxqLvOMaYOhSE48YSmnc72G+qYyGRtxjVuixrlIwjiGFge1tI3ap4YyBP6VUDvQkJLJWrnzhLZl8virodnZLjACSKtfkCja4RuaZhVSueMCHRHTNB5GGATLa+Mml00gT4MLFO1ZsdixijmFmV8brDENEu+MWiItaBwpc8oDDHJaFzgkmp+EJZeMc08L8BdlqmMipfkqcDl0EfMSp06sc7Kp3yFYkrBoyDgxEHoO1fa9GVe6uGGw7T9XuoS5uz8qv7D/CURT7RcZsXFC5HUXhVIsGiJJ0crikwbog2QV1xMXdWsyG1S64xWHWqUIAvWwo5Ri/7FqZeENHUYFjaJ49OCr7ERj3WiKN2b3hU1zIN4CHFSYK6OPEfmiM/KDG9KTJ6VcUaMTla6pmyv8AI2uVH6IpBiwxnJYIEK1iylSHezm7RKqQlUlY61GvMx0C5cHBVmK8HqcY8QYN8CHNQ1kOgAcGEs4mqC0HpjLhgN6hWeSOFB4kQ8hVtNT8YyIlOWoXBhY2Jh7qlbIWh2YxiaI3GxE7w3wtzWrFY4UsZmAAedT2wpbqZ8hxZRfbRqAmXVvzhW5qy2JP5T94sZ0F3bKNsFFyTUeTCt1WFR/mbri3NjwABkloMRPX0W7NmzZs2bNIlA1a0w2kWj2jCh1mg5GE/oz2APf9JwrhcCgRZY8egzZs2bNmzA+5ciSig2gfYVC9ipXVx8YZ8TSh/YsxbbkNJu8qRg0S+BUDMVt4KNAAQAWB+jdQ2w79DDYPZggCjABK0NailvETJeHYSrRyfJUq+B3qyfbNGtuj2ioNP3MciLkI/VXi87il+fZnV581vKVzC2mS+m7yE9osL7PUaYR8eniLyEwfh7PI4B8p9PUHEWutsH8/Z9RodhVcdvSXSFq5bjy9nqRAHPeT7ouPRbmlOUGQhvx7PfhmeQ+6PRuWVXYtp2VnQhE+E9nJlA06AtHo3HvXAfRUIBoR7OxMF7wyVGgC+VdlI3rFh0nkw6c8toEXCJOYUqM6EvDKpMJmY4qak1Pw+4nmSqdlTf7XLrXQbUDKLnF5La7aIFsk4ptL8iuaCI23KxoGnWxIBEYtlM5fx3//2Q==";
                            HashMap<String,String> ktp = new HashMap<>();
                            ktp.put("form_id", id);
                            String type = "image["+DocTcData.ktp.getCode()+"]";
                            /*ktp.put(type,"data:image/jpeg;base64,"+base64);*/
                            ktp.put(type,dummyImage);
                            base64KTP = ktp;

                            SubmitPresenter.upload(UploadDocumentActivity.this, new UploadCallback() {
                                @Override
                                public void onSuccessUpload() {
                                    uploadSPK(id);
                                }

                                @Override
                                public void onFailedUpload() {
                                    uploadSPK(id);
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload KTP", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onLoopEnd() {
                                    uploadSPK(id);
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload KTP", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResultError() {
                                    uploadSPK(id);
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload KTP", Toast.LENGTH_SHORT).show();
                                }
                            },base64KTP);
                        }

                        @Override
                        public void onFailure() {
                            uploadSPK(id);
                            Toast.makeText(UploadDocumentActivity.this,"Failed upload KTP", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            uploadSPK(id);
        }
    }

    private void uploadSPK(final String id){
        if(pathSPK != null && !pathSPK.equals("")){
            Base64Image.with(this)
                    .encode(ImageUtility.loadBitmapFromPath(pathSPK))
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            HashMap<String,String> file = new HashMap<>();
                            String type = "image["+DocTcData.spk.getCode()+"]";
                            file.put(type,"data:image/jpeg;base64,"+s);
                            file.put("form_id",id);
                            base64SPK = file;

                            SubmitPresenter.upload(UploadDocumentActivity.this, new UploadCallback() {
                                @Override
                                public void onSuccessUpload() {
                                    uploadKartuNama(id);
                                }

                                @Override
                                public void onFailedUpload() {
                                    uploadKartuNama(id);
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload SPK", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onLoopEnd() {
                                    uploadKartuNama(id);
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload SPK", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResultError() {
                                    uploadKartuNama(id);
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload SPK", Toast.LENGTH_SHORT).show();
                                }
                            },base64SPK);
                        }

                        @Override
                        public void onFailure() {
                            uploadKartuNama(id);
                            Toast.makeText(UploadDocumentActivity.this,"Failed upload SPK", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            uploadKartuNama(id);
        }
    }

    private void uploadKartuNama(final String id){
        if(pathKartuNama != null && !pathKartuNama.equals("")){
            Base64Image.with(this)
                    .encode(ImageUtility.loadBitmapFromPath(pathKartuNama))
                    .into(new RequestEncode.Encode() {
                        @Override
                        public void onSuccess(String s) {
                            HashMap<String,String> file = new HashMap<>();
                            String type = "image["+DocTcData.kartuNama.getCode()+"]";
                            file.put(type,"data:image/jpeg;base64,"+s);
                            file.put("form_id",id);
                            base64KartuNama = file;

                            SubmitPresenter.upload(UploadDocumentActivity.this, new UploadCallback() {
                                @Override
                                public void onSuccessUpload() {
                                    progressDialog.dismiss();
                                    if (status.equals("draft")){
                                        Toast.makeText(UploadDocumentActivity.this,"Submit Draft Done", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(UploadDocumentActivity.this,"Submit Done", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailedUpload() {
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload kartu nama", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onLoopEnd() {
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload kartu nama", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResultError() {
                                    Toast.makeText(UploadDocumentActivity.this,"Failed upload kartu nama", Toast.LENGTH_SHORT).show();
                                }
                            },base64KartuNama);
                        }

                        @Override
                        public void onFailure() {
                            progressDialog.dismiss();
                            if (status.equals("draft")){
                                Toast.makeText(UploadDocumentActivity.this,"Submit Draft Done", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(UploadDocumentActivity.this,"Submit Done", Toast.LENGTH_SHORT).show();
                            }
                         }
                    });
        }else{
            progressDialog.dismiss();
            Toast.makeText(UploadDocumentActivity.this,"Submit Done", Toast.LENGTH_SHORT).show();
        }
    }

    private void getIntentData(){
        inquiryItem = (InquiryItem) getIntent().getSerializableExtra("data");
        isUpdate = getIntent().getIntExtra("isUpdate",-1);
        if(inquiryItem != null){
            document = inquiryItem.getFormValue().getDocument();
            formId = inquiryItem.getIdInquiry()+"";
            closeAllCard();

            List<DocumentUpload> required = document.getRequired();
            List<DocumentUpload> optional = document.getOptional();

            if (isUpdate == 1 ) {
                toggleMenuListAdapter.setMenuVisible(0, false);
                toggleMenuListAdapter.setMenuVisible(1, false);
                toggleMenuListAdapter.setMenuVisible(2, false);
                toggleMenuListAdapter.setMenuVisible(3, false);
                toggleMenuListAdapter.setMenuVisible(4, false);
                toggleMenuListAdapter.setMenuVisible(5, false);
                toggleMenuListAdapter.setMenuVisible(6, false);
                toggleMenuListAdapter.setMenuVisible(7, false);
                toggleMenuListAdapter.setMenuVisible(8, false);
                toggleMenuListAdapter.setMenuVisible(9, false);
                toggleMenuListAdapter.setMenuVisible(10, false);
                toggleMenuListAdapter.setMenuVisible(11, false);
                toggleMenuListAdapter.setMenuVisible(12, false);
                toggleMenuListAdapter.setMenuVisible(13, false);
                toggleMenuListAdapter.setMenuVisible(14, false);
                toggleMenuListAdapter.setMenuVisible(15, false);
            }

            btnDraft.setVisibility(View.GONE);
            for (int i=0; i<optional.size(); i++){
                DocumentUpload documentUpload = optional.get(i);

                if(documentUpload.getCode().equals(DocTcData.kk.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemKK.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(3, true);
                }

                if(documentUpload.getCode().equals(DocTcData.npwp.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemNPWP.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(4, true);
                }

                if(documentUpload.getCode().equals(DocTcData.suratMenikah.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemBukuNikah.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(11, true);
                }

                if(documentUpload.getCode().equals(DocTcData.laporanKeuangan.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemBuktiKeuangan.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(5, true);
                }

                if(documentUpload.getCode().equals(DocTcData.rekeningKoran.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemRekeningKoran.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(6, true);
                }

                if(documentUpload.getCode().equals(DocTcData.coverTabungan.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemCoverTabungan.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(7, true);
                }

                if(documentUpload.getCode().equals(DocTcData.pbb.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemBuktiKepemilikanRumah.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(8, true);
                }

                if(documentUpload.getCode().equals(DocTcData.ijinPraktek.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemKontrakKerja.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(9, true);
                }

                if(documentUpload.getCode().equals(DocTcData.keteranganDomisili.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemKeteranganDomisili.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(10, true);
                }

                if(documentUpload.getCode().equals(DocTcData.KKSTNK.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemKKSTNK.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(14, true);
                }

                if(documentUpload.getCode().equals(DocTcData.KTPSTNK.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemKTPSTNK.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(15, true);
                }

                if(documentUpload.getCode().equals(DocTcData.ktpSpouse.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemKTPPasangan.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(12, true);
                }

                if(documentUpload.getCode().equals(DocTcData.kartuNamaSpouse.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemKartuNamaPasangan.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(13, true);
                }

                if(documentUpload.getCode().equals(DocTcData.slipGaji.getCode()) && documentUpload.getStatus().equals("false")){
                    lyItemSlipGaji.setVisibility(View.VISIBLE);
                    toggleMenuListAdapter.setMenuVisible(16, true);
                }

                updateSlideNumbering();
            }
        }
    }

    private void closeAllCard(){
        lyItemKTP.setVisibility(View.GONE);
        lyItemKK.setVisibility(View.GONE);
        lyItemSPK.setVisibility(View.GONE);
        lyItemKartuNama.setVisibility(View.GONE);
        lyItemBukuNikah.setVisibility(View.GONE);
        lyItemBuktiKeuangan.setVisibility(View.GONE);
        lyItemNPWP.setVisibility(View.GONE);
        lyItemRekeningKoran.setVisibility(View.GONE);
        lyItemCoverTabungan.setVisibility(View.GONE);
        lyItemBuktiKepemilikanRumah.setVisibility(View.GONE);
        lyItemBukuNikah.setVisibility(View.GONE);
        lyItemKontrakKerja.setVisibility(View.GONE);
        lyItemKeteranganDomisili.setVisibility(View.GONE);
        lyItemKTPPasangan.setVisibility(View.GONE);
        lyItemKartuNamaPasangan.setVisibility(View.GONE);
        lyItemKTPSTNK.setVisibility(View.GONE);
        lyItemKKSTNK.setVisibility(View.GONE);
        lySwitchAlamatTinggal.setVisibility(View.GONE);
        lySwitchSTNKCustomer.setVisibility(View.GONE);
        lySwitchPasanganBekerja.setVisibility(View.GONE);
        lyItemSlipGaji.setVisibility(View.GONE);
    }

    private void processEncode(){
        encodeKtp();
    }
    private void updateDocument(){
        //isUpdate = true;
        processEncode();
        processSubmit();
    }

    private boolean validasiMandatoryDocument(){
        boolean status = true;
        if(dataKTP == null || dataKTP.size()==0){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Dokumen KTP tidak boleh kosong")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }else if(dataSPK == null || dataSPK.size()==0){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Dokumen SPK tidak boleh kosong")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }else if(dataKartuNama == null || dataKartuNama.size() == 0){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Dokumen Keterangan Pekerjaan / Usaha tidak boleh kosong")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }

//        if (status == true && SelectedData.IsMarried) {
//            if((dataKtpPasangan == null || dataKtpPasangan.size() == 0) && (promiseDateKtpPasangan == null || promiseDateKtpPasangan.equals(""))){
//                status = false;
//                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
////                        .setMessage("Input dokumen KTP Pasangan atau isikan promise date terlebih dahulu")
//                        .setMessage("Input dokumen KTP Pasangan")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//                builder.show();
//            }
//        }
//
//        if (status == true && SelectedData.IsMarried) {
//            if((dataKartuNamaPasangan == null || dataKartuNamaPasangan.size() == 0) && (promiseDateKartuNamaPasangan == null || promiseDateKartuNamaPasangan.equals(""))){
//                status = false;
//                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
////                        .setMessage("Input dokumen KTP Pasangan atau isikan promise date terlebih dahulu")
//                        .setMessage("Input dokumen Kartu Nama Istri/Suami")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//                builder.show();
//            }
//        }

        if (status == true && SelectedData.IsMarried) {
            if((dataKtpPasangan == null || dataKtpPasangan.size() == 0)){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                        .setMessage("Input dokumen KTP Pasangan")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        }

        if (status == true && SelectedData.isNPWP) {
            if((dataNPWP == null || dataNPWP.size() == 0) && (pathPdfNPWP == null || pathPdfNPWP.equals("")) && (promiseDateNpwp == null || promiseDateNpwp.equals(""))){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                        .setMessage("Input dokumen NPWP atau isikan promise date terlebih dahulu.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        }

        if (status == true && switchInfoPasanganBekerja.isChecked()) {
            if((dataKartuNamaPasangan == null || dataKartuNamaPasangan.size() == 0) && (promiseDateKartuNamaPasangan == null || promiseDateKartuNamaPasangan.equals(""))){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
//                        .setMessage("Input dokumen Kartu Nama Pasangan atau isikan promise date terlebih dahulu.")
                        .setMessage("Input dokumen Keterangan Pekerjaan / Usaha Istri / Suami")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        }

        if (status == true && !switchInfoSTNKCustomer.isChecked()) {
            if((dataKTPSTNK == null || dataKTPSTNK.size() == 0) && (promiseDateKTPSTNK == null || promiseDateKTPSTNK.equals(""))){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
//                        .setMessage("Input dokumen KTP Atas Nama STNK atau isikan promise date terlebih dahulu.")
                        .setMessage("Input dokumen KTP Atas Nama STNK")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            } else if((pathKKSTNK == null || pathKKSTNK.equals("")) && (pathPdfKKSTNK == null || pathPdfKKSTNK.equals("")) && (promiseDateKKSTNK == null || promiseDateKKSTNK.equals(""))){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
//                        .setMessage("Input dokumen KK Atas Nama STNK atau isikan promise date terlebih dahulu.")
                        .setMessage("Input dokumen KK Atas Nama STNK")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        }

        if (status == true && !switchInfoAlamatTinggal.isChecked()) {
            if(edAlamat.getText().toString().equals("")){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                        .setMessage("Alamat customer sesuai KTP tidak boleh kosong")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        }

        if (SelectedData.customerType == 1){
            if((pathSlipGaji == null || pathSlipGaji.equals("")) && (pathPdfSlipGaji == null || pathPdfSlipGaji.equals("")) && (promiseDateSlipGaji == null || promiseDateSlipGaji.equals(""))){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
//                        .setMessage("Input dokumen KK Atas Nama STNK atau isikan promise date terlebih dahulu.")
                        .setMessage("Input Slip Gaji")
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

    private boolean validasiPromiseDate(){
        boolean status = true;

        if((pathKK == null || pathKK.equals("")) && (pathPdfKK == null || pathPdfKK.equals("")) && (promiseDateKK == null || promiseDateKK.equals(""))){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Input dokumen KK atau isikan promise date terlebih dahulu")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        } else if((pathRekeningkoran == null || pathRekeningkoran.equals("")) && (pathPdfRekeningKoran == null || pathPdfRekeningKoran.equals("")) && (promiseDateRekeningkoran == null || promiseDateRekeningkoran.equals(""))){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Input dokumen Rekening Koran atau isikan promise date terlebih dahulu")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        } else if (SelectedData.wop.contains("Auto Debit") && ((pathCoverTabungan == null || pathCoverTabungan.equals("")) && (pathPdfCoverTabungan== null || pathPdfCoverTabungan.equals("")) && (promiseDateCoverTabungan == null || promiseDateCoverTabungan.equals("")))){
//            if((pathCoverTabungan == null || pathCoverTabungan.equals("")) && (pathPdfCoverTabungan== null || pathPdfCoverTabungan.equals("")) && (promiseDateCoverTabungan == null || promiseDateCoverTabungan.equals(""))){
                status = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                        .setMessage("Input dokumen Cover Tabungan atau isikan promise date terlebih dahulu")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
//            }
        } else if((pathBuktiKepemilikanRumah == null || pathBuktiKepemilikanRumah.equals("")) && (pathPdfBuktiKepemilikanRumah == null || pathPdfBuktiKepemilikanRumah.equals("")) && (promiseDateBuktiKepemilikanRumah == null || promiseDateBuktiKepemilikanRumah.equals(""))){
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Input dokumen Bukti Kepemilikan Rumah atau isikan promise date terlebih dahulu")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }

        if (status == true && SelectedData.IsMarried && ((pathBukuNikah == null || pathBukuNikah.equals("")) && (pathPdfBukuNikah == null || pathPdfBukuNikah.equals("")) && (promiseDateBukuNikah == null || promiseDateBukuNikah.equals("")))) {
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Input dokumen Buku Nikah atau isikan promise date terlebih dahulu")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }

        if (status == true && SelectedData.customerType == 2 && ((pathKontrakKerja == null || pathKontrakKerja.equals("")) && (pathPdfKontrakKerja == null || pathPdfKontrakKerja.equals("")) && (promiseDateKontrakKerja == null || promiseDateKontrakKerja.equals("")))) {
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Input dokumen Izin Praktek / Kontrak Kerja atau isikan promise date terlebih dahulu")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.show();
        }

        if (status == true && SelectedData.customerType == 3 && ((pathKeteranganDomisili == null || pathKeteranganDomisili.equals("")) && (pathPdfKeteranganDomisili == null || pathPdfKeteranganDomisili.equals("")) && (promiseDateKeteranganDomisili == null || promiseDateKeteranganDomisili.equals("")))) {
            status = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this)
                    .setMessage("Input dokumen SIUP / TDP / Keterangan Domisili atau isikan promise date terlebih dahulu")
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

    private boolean isEmptyPath(String path) {
        if (path == null) {
            return true;
        } else if (path != null) {
            if (path.equals("")) {
                return true;
            }
        }

        return false;
    }

    private boolean validasiLTV(){
        boolean status = false;

        if(SelectedData.isNonPaket.equals("1")){
            if (SelectedData.PaymentType == 1){
                if(SelectedData.isNPWP == false){
                    if(countLTV()>90){
                        status = true;
                    }
                }
            }
        }else{
            status = false;
        }

        LogUtility.logging(TAG,LogUtility.infoLog,"LTV","countLTV : status",status+"");
        return status;
    }

    private double countLTV(){
        String pokokHutangAkhir = SelectedData.PokokHutangAkhir;
        String otr = SelectedData.Otr;
        String insurance = SelectedData.pvPremiAmount;

        long phLong = 0l;
        long otrLong = 0l;
        long insuranceLong = 0l;

        if(pokokHutangAkhir != null && !pokokHutangAkhir.equals("")){
            phLong = Long.parseLong(pokokHutangAkhir);
        }

        if(otr != null && !otr.equals("")){
            otrLong = Long.parseLong(otr);
        }

        if(insurance != null && !insurance.equals("")){
            insuranceLong = Long.parseLong(insurance);
        }

        double a = new Double(0.75*insuranceLong).longValue();
        double b = otrLong+a;
        double LTV = new Double(phLong/b*100);

        LogUtility.logging(TAG,LogUtility.infoLog,"LTV","countLTV : value",LTV+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"LTV","countLTV : insurance",insurance+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"LTV","countLTV : otr",otr+"");
        LogUtility.logging(TAG,LogUtility.infoLog,"LTV","countLTV : pokokHutangAkhir",pokokHutangAkhir+"");
        return LTV;
    }

    @Override
    public void onBackPressed() {
        NestedScrollView rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
        PopupUtils.backFromUploadDocument(UploadDocumentActivity.this, rootView, 5, new PopupUtils.SaveToDraftListener2() {
            @Override
            public void isSaved(boolean isSaved) {
                //Log.d("draft", "draft");
                finish();
            }

            @Override
            public void isExit(boolean isExit) {
                //Log.d("exit", "exit");
            }
        });

    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    Log.d("cache", "not clear");
                    return false;
                }else {
                    Log.d("cache", "clear");
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }



}
