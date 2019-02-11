package com.drife.digitaf.Module.dashboard.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReportSalesByPackage  implements Serializable {
    @SerializedName("date")
    public String date = "";
    @SerializedName("month")
    public String month = "";
    @SerializedName("package")
    public String packaged = "";
    @SerializedName("unit")
    public String unit = "0";
    @SerializedName("year")
    public String year = "";

    public ReportSalesByPackage() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPackaged() {
        return packaged;
    }

    public void setPackaged(String packaged) {
        this.packaged = packaged;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

