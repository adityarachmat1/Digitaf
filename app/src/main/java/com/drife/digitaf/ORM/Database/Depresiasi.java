package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Depresiasi extends SugarRecord implements Serializable {
    private String DepresiasiId;
    //private String CategoryId;
    //private String CategoryCode;
    //private String Category;
    private String TenorId;
    private String TenorCode;
    private String Tenor;
    private String Percentage;

    public Depresiasi() {
    }

    public Depresiasi(String depresiasiId, String tenorId, String tenorCode, String tenor, String percentage) {
        DepresiasiId = depresiasiId;
        TenorId = tenorId;
        TenorCode = tenorCode;
        Tenor = tenor;
        Percentage = percentage;
    }

    public String getDepresiasiId() {
        return DepresiasiId;
    }

    public void setDepresiasiId(String depresiasiId) {
        DepresiasiId = depresiasiId;
    }

    public String getTenorId() {
        return TenorId;
    }

    public void setTenorId(String tenorId) {
        TenorId = tenorId;
    }

    public String getTenorCode() {
        return TenorCode;
    }

    public void setTenorCode(String tenorCode) {
        TenorCode = tenorCode;
    }

    public String getTenor() {
        return Tenor;
    }

    public void setTenor(String tenor) {
        Tenor = tenor;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

}
