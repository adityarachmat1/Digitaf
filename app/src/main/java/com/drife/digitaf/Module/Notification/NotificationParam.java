package com.drife.digitaf.Module.Notification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationParam implements Serializable {
    @SerializedName("content_message")
    public String content_message = "";
    @SerializedName("customer_name")
    public int submission_id = 0;
    @SerializedName("lead_id")
    public int lead_id = 0;
    @SerializedName("title_message")
    public String title_message = "";

    public NotificationParam() {
    }

    public NotificationParam(String content_message, int submission_id, int lead_id, String title_message) {
        this.content_message = content_message;
        this.submission_id = submission_id;
        this.lead_id = lead_id;
        this.title_message = title_message;
    }

    public String getContent_message() {
        return content_message;
    }

    public void setContent_message(String content_message) {
        this.content_message = content_message;
    }

    public int getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(int submission_id) {
        this.submission_id = submission_id;
    }

    public int getLead_id() {
        return lead_id;
    }

    public void setLead_id(int lead_id) {
        this.lead_id = lead_id;
    }

    public String getTitle_message() {
        return title_message;
    }

    public void setTitle_message(String title_message) {
        this.title_message = title_message;
    }
}
