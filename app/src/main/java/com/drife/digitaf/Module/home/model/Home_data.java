package com.drife.digitaf.Module.home.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Home_data implements Serializable {
    @SerializedName("total_inquiry")
    public String total_inquiry = "0";
    @SerializedName("total_draft")
    public String total_draft = "0";
    @SerializedName("total_new")
    public String total_new = "0";
    @SerializedName("total_follow_up_doc")
    public String total_follow_up_doc = "0";
    @SerializedName("total_request_negotiation")
    public String total_request_negotiation = "0";
    @SerializedName("total_request_survey")
    public String total_request_survey = "0";
    @SerializedName("total_update_phone")
    public String total_update_phone = "0";
    @SerializedName("total_go_live")
    public String total_go_live = "0";
    @SerializedName("total_approve")
    public String total_approve = "0";
    @SerializedName("total_reject")
    public String total_reject = "0";
    @SerializedName("total_credit_review")
    public String total_credit_review = "0";
    @SerializedName("total_data_entry")
    public String total_data_entry = "0";
    @SerializedName("total_cancel")
    public String total_cancel = "0";
    @SerializedName("total_wait")
    public String total_wait = "0";
    @SerializedName("total_request")
    public String total_request = "0";
    @SerializedName("total_reinput")
    public String total_reinput = "0";

    public Home_data() {
    }

    public Home_data(String total_inquiry, String total_draft, String total_new) {
        this.total_inquiry = total_inquiry;
        this.total_draft = total_draft;
        this.total_new = total_new;
    }

    public String getTotal_inquiry() {
        return total_inquiry;
    }

    public void setTotal_inquiry(String total_inquiry) {
        this.total_inquiry = total_inquiry;
    }

    public String getTotal_draft() {
        return total_draft;
    }

    public void setTotal_draft(String total_draft) {
        this.total_draft = total_draft;
    }

    public String getTotal_new() {
        return total_new;
    }

    public void setTotal_new(String total_new) {
        this.total_new = total_new;
    }

    public String getTotal_follow_up_doc() {
        return total_follow_up_doc;
    }

    public void setTotal_follow_up_doc(String total_follow_up_doc) {
        this.total_follow_up_doc = total_follow_up_doc;
    }

    public String getTotal_update_phone() {
        return total_update_phone;
    }

    public void setTotal_update_phone(String total_update_phone) {
        this.total_update_phone = total_update_phone;
    }

    public String getTotal_go_live() {
        return total_go_live;
    }

    public void setTotal_go_live(String total_go_live) {
        this.total_go_live = total_go_live;
    }

    public String getTotal_approve() {
        return total_approve;
    }

    public void setTotal_approve(String total_approve) {
        this.total_approve = total_approve;
    }

    public String getTotal_reject() {
        return total_reject;
    }

    public void setTotal_reject(String total_reject) {
        this.total_reject = total_reject;
    }

    public String getTotal_credit_review() {
        return total_credit_review;
    }

    public void setTotal_credit_review(String total_credit_review) {
        this.total_credit_review = total_credit_review;
    }

    public String getTotal_data_entry() {
        return total_data_entry;
    }

    public void setTotal_data_entry(String total_data_entry) {
        this.total_data_entry = total_data_entry;
    }

    public String getTotal_cancel() {
        return total_cancel;
    }

    public void setTotal_cancel(String total_cancel) {
        this.total_cancel = total_cancel;
    }

    public String getTotal_wait() {
        return total_wait;
    }

    public void setTotal_wait(String total_wait) {
        this.total_wait = total_wait;
    }

    public String getTotal_request() {
        return total_request;
    }

    public void setTotal_request(String total_request) {
        this.total_request = total_request;
    }

    public String getTotal_reinput() {
        return total_reinput;
    }

    public void setTotal_reinput(String total_reinput) {
        this.total_reinput = total_reinput;
    }

    public String getTotal_request_negotiation() {
        return total_request_negotiation;
    }

    public void setTotal_request_negotiation(String total_request_negotiation) {
        this.total_request_negotiation = total_request_negotiation;
    }

    public String getTotal_request_survey() {
        return total_request_survey;
    }

    public void setTotal_request_survey(String total_request_survey) {
        this.total_request_survey = total_request_survey;
    }
}

