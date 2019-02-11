package com.drife.digitaf.Module.InputKredit.UploadDocument.Model;

/**
 * Created by ferdinandprasetyo on 10/3/18.
 */

public class UploadDocumentItem {
    public static final int TYPE_DOCUMENT = 0;
    public static final int TYPE_INFO = 1;

    /*public static final int KTP = 2;
    public static final int SPK = 3;
    public static final int KartuNama = 4;
    public static final int KK = 5;
    public static final int BuktiKeuangan = 6;
    public static final int NPWP = 7;
    public static final int KTPIstriSuami = 8;*/

    public static enum DocumentType {
        KTP, SPK, KartuNama, KTPIstriSuami, KartuNamaIstriSuami, KK, NPWP, BuktiKeuangan,
        BuktiKepemilikanRumah, BukuNikah, KontrakKerja, KKSTNK, CoverBukuTabungan, KeteranganDomisili,
        RekeningKoran, KTPSTNK, AlamatTinggal, SlipGaji
    }

    DocumentType documentType;
    String titleDocument = "";
    String documentInfo = "";
    String imagePath = "";
    int viewType = TYPE_DOCUMENT;
    boolean enable = false;

    public UploadDocumentItem(DocumentType documentType, String titleDocument, String documentInfo, String imagePath, boolean enable) {
        this.documentType = documentType;
        this.titleDocument = titleDocument;
        this.documentInfo = documentInfo;
        this.imagePath = imagePath;
        this.viewType = TYPE_DOCUMENT;
        this.enable = enable;
    }

    public UploadDocumentItem(String titleDocument, DocumentType documentType) {
        this.titleDocument = titleDocument;
        this.viewType = TYPE_INFO;
        this.documentType = documentType;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentInfo() {
        return documentInfo;
    }

    public void setDocumentInfo(String documentInfo) {
        this.documentInfo = documentInfo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitleDocument() {
        return titleDocument;
    }

    public void setTitleDocument(String titleDocument) {
        this.titleDocument = titleDocument;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public static int getTypeDocument() {
        return TYPE_DOCUMENT;
    }

    public static int getTypeInfo() {
        return TYPE_INFO;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
