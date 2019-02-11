package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

public class DraftData extends SugarRecord {
    private String nama;
    private String inquiryParam;

    public DraftData() {
    }

    public DraftData(String nama, String inquiryParam) {
        this.nama = nama;
        this.inquiryParam = inquiryParam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getInquiryParam() {
        return inquiryParam;
    }

    public void setInquiryParam(String inquiryParam) {
        this.inquiryParam = inquiryParam;
    }
}
