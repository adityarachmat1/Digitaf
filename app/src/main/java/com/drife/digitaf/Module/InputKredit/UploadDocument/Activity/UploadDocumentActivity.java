package com.drife.digitaf.Module.InputKredit.UploadDocument.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.drife.digitaf.GeneralUtility.CameraUtility.CameraUtility;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.Module.Dialog.PopupDraft.PopupUtils;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Adapter.ToggleMenuListAdapter;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Adapter.UploadDocumentListAdapter;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.ToggleMenuItem;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.UploadDocumentItem;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Utils.PopupUpload;
import com.drife.digitaf.ORM.Temporary.TemporaryValue;
import com.drife.digitaf.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ferdinandprasetyo on 10/3/18.
 */

public class UploadDocumentActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = UploadDocumentActivity.class.getSimpleName();

    private ImageView imgBack;
    private LinearLayout btnDraft;
    private LinearLayout btnSubmit;
    private CardView toggleMenu;
    private LinearLayout toggleButton;
    private RecyclerView rvUploadDocument;
    private RecyclerView rvToggleMenu;

    private UploadDocumentListAdapter uploadDocumentListAdapter;
    private ToggleMenuListAdapter toggleMenuListAdapter;

    private ArrayList<UploadDocumentItem> listItems;
    private ArrayList<ToggleMenuItem> listToggleMenuItems;
    private boolean show = true;

    private RelativeLayout lyParent;
    private UploadDocumentItem.DocumentType selectedDocumentType;
    private String customerId = "",customerName = "";
    private String pathKtp = "";
    private String pathSPK = "";
    private String pathKartuNama = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.z_activity_uploaddocument);

        initLayout();
        initVariables();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== CameraUtility.REQUEST_IMAGE_CAPTURE_1){
            String path = this.pathKtp;
            if(resultCode == RESULT_OK){
                showPopupWindow(selectedDocumentType);
            }else{
                ImageUtility.deleteImage(path);
                pathKtp = "";
            }
        }
    }

    private void initLayout() {
        imgBack = findViewById(R.id.imgBack);
        btnDraft = findViewById(R.id.btnDraft);
        btnSubmit = findViewById(R.id.btnSubmit);
        rvUploadDocument = findViewById(R.id.rvUploadDocument);
        rvToggleMenu = findViewById(R.id.rvListToggleMenu);
        toggleButton = findViewById(R.id.toggleButton);
        toggleMenu = findViewById(R.id.toggleMenu);
        lyParent = findViewById(R.id.lyParent);

        setListToggle();
        setListUpload();

        imgBack.setOnClickListener(this);
        toggleButton.setOnClickListener(this);
        btnDraft.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void initVariables(){
        if(customerId.equals("")){
            customerId = TemporaryValue.CustomerId;
        }
    }

    private void setListToggle() {
        listToggleMenuItems = new ArrayList<>();
        listToggleMenuItems.add(new ToggleMenuItem("KTP*", "-", true));
        listToggleMenuItems.add(new ToggleMenuItem("SPK*", "-", true));
        listToggleMenuItems.add(new ToggleMenuItem("Kartu Nama*", "-", true));
        listToggleMenuItems.add(new ToggleMenuItem("Kartu Keluarga", "12 JUN 2018", true));
        listToggleMenuItems.add(new ToggleMenuItem("Bukti Keuangan", "12 JUN 2018", true));
        listToggleMenuItems.add(new ToggleMenuItem("NPWP", "12 JUN 2018", true));
        listToggleMenuItems.add(new ToggleMenuItem("KTP Istri/Suami", "-", true));
        listToggleMenuItems.add(new ToggleMenuItem("KTP A/N STNK", "-", true));
        listToggleMenuItems.add(new ToggleMenuItem("Alamat tinggal (beda dengan KTP)", "-", true));

        toggleMenuListAdapter = new ToggleMenuListAdapter(this, listToggleMenuItems);

        //Set layout manager type for recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvToggleMenu.setLayoutManager(layoutManager);
        rvToggleMenu.setItemAnimator(new DefaultItemAnimator());
        rvToggleMenu.setNestedScrollingEnabled(false);
        rvToggleMenu.setAdapter(toggleMenuListAdapter);
    }

    private void setListUpload() {
        listItems = new ArrayList<>();
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KTP, "KTP*", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.SPK, "SPK*", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KartuNama, "Kartu Nama*", "", "",true));
        listItems.add(new UploadDocumentItem("Apakah istri/suami bekerja?", UploadDocumentItem.DocumentType.KTPIstriSuami));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KTPIstriSuami, "KTP Istri/Suami", "", "",false));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KartuNamaIstriSuami, "Kartu Nama Istri/Suami", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KK, "KK", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.NPWP, "NPWP", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.BuktiKeuangan, "Bukti Keuangan", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.BuktiKepemilikanRumah, "Bukti Kepemilikan Rumah", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.BukuNikah, "Buku Nikah", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KontrakKerja, "Kontrak Kerja", "", "",true));
        listItems.add(new UploadDocumentItem("STNK atas Nama Customer", UploadDocumentItem.DocumentType.KKSTNK));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KKSTNK, "KK Atas Nama STNK", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KTPSTNK, "KTP Atas Nama STNK", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.CoverBukuTabungan, "Cover Buku Tabungan", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.KeteranganDomisili, "Keterangan Domisili", "", "",true));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.RekeningKoran, "Laporan Keuangan", "", "",true));
        listItems.add(new UploadDocumentItem("Alamat Customer Sesuai KTP", UploadDocumentItem.DocumentType.AlamatTinggal));
        listItems.add(new UploadDocumentItem(UploadDocumentItem.DocumentType.AlamatTinggal, "Alamat Tempat Tinggal", "", "",true));

        uploadDocumentListAdapter = new UploadDocumentListAdapter(this, listItems);

        //Set layout manager type for recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvUploadDocument.setLayoutManager(layoutManager);
        rvUploadDocument.setItemAnimator(new DefaultItemAnimator());
        rvUploadDocument.setNestedScrollingEnabled(false);
        rvUploadDocument.setAdapter(uploadDocumentListAdapter);
    }

    @Override
    public void onClick(View view) {
        final NestedScrollView rootView;

        switch (view.getId()) {
            case R.id.btnDraft:
                rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                PopupUtils.simpanKeDraftSaved(this, rootView, 1, new PopupUtils.SaveToDraftListener() {
                    @Override
                    public void isSaved(boolean isSaved) {

                    }
                });
                break;
            case R.id.btnSubmit:
                rootView = (NestedScrollView) findViewById(R.id.nestedMinimum);
                PopupUtils.simpanKeDraftSaved(this, rootView, 2, new PopupUtils.SaveToDraftListener() {
                    @Override
                    public void isSaved(boolean isSaved) {
                        if (isSaved) {
                            PopupUtils.simpanKeDraftSaved(UploadDocumentActivity.this, rootView, 3, new PopupUtils.SaveToDraftListener() {
                                @Override
                                public void isSaved(boolean isSaved) {
                                    finish();
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.toggleButton:
                slideMenu();
                break;
        }
    }

    private void slideMenu() {
        if (show) {
            show = false;
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

    public void showPopupWindow(UploadDocumentItem.DocumentType documentType){
        selectedDocumentType = documentType;
        String path = "";
        if(documentType.toString().equals(UploadDocumentItem.DocumentType.KTP.toString())){
            path = pathKtp;
        }else if(documentType.toString().equals(UploadDocumentItem.DocumentType.SPK.toString())){
            path = pathSPK;
        }else if(documentType.toString().equals(UploadDocumentItem.DocumentType.KartuNama.toString())){
            path = pathKartuNama;
        }

        if(documentType.toString().equals(UploadDocumentItem.DocumentType.KTP.toString())){
            String name = "KTP_"+customerName;
            PopupUpload.inputKTP(UploadDocumentActivity.this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {

                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKtp = imagePath;
                }
            },path,customerId,name,null,null);
        }else if(documentType.toString().equals(UploadDocumentItem.DocumentType.SPK.toString())){
            String name = "SPK_"+customerName;
            PopupUpload.inputSPK(UploadDocumentActivity.this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {

                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathSPK = imagePath;
                }
            },path,customerId,name,null);
        }else if(documentType.toString().equals(UploadDocumentItem.DocumentType.KartuNama.toString())){
            String name = "KartuNama_"+customerName;
            PopupUpload.inputKartuNama(UploadDocumentActivity.this, lyParent, new PopupUpload.SaveDataListener() {
                @Override
                public void isSaved(boolean isSaved, HashMap<String, String> datas) {

                }

                @Override
                public void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data) {
                    pathKartuNama = imagePath;
                }
            },path,customerId,name,new HashMap<String, String>(),null);
        }

    }
}
