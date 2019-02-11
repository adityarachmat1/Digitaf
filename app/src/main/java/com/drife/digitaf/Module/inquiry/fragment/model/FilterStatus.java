package com.drife.digitaf.Module.inquiry.fragment.model;

import android.support.annotation.NonNull;

public class FilterStatus {
    public static String approve = "approve";
    public static String cancel = "cancel";
    public static String credit_review = "credit_review";
    public static String data_entry = "data_entry";
    public static String reject = "reject";
    public static String re_input = "re_input";
    public static String valid = "valid";
    public static String wait = "wait";
    public static String request_doc = "request_doc";
    public static String request_nego = "request_nego";
    public static String request_survey = "request_survey";

    private String name = "";
    private String statusParam;

    public FilterStatus() {
    }

    public FilterStatus(String name, String statusParam) {
        this.name = name;
        this.statusParam = statusParam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusParam() {
        return statusParam;
    }

    public void setStatusParam(String statusParam) {
        this.statusParam = statusParam;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
