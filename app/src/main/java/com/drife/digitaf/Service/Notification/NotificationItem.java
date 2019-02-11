package com.drife.digitaf.Service.Notification;

import com.drife.digitaf.Module.Notification.NotificationParam;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationItem implements Serializable {
    @SerializedName("submission")
    public InquiryItem inquiryItem = new InquiryItem();

    @SerializedName("notification_param")
    public NotificationParam notificationParam = new NotificationParam();

    @SerializedName("created_at")
    public String created_at = "";
    @SerializedName("updated_at")
    public String updated_at = "";
    @SerializedName("is_read")
    public String is_read = "";
    @SerializedName("status")
    public String status = "";

    public NotificationItem() {
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotificationParam getNotificationParam() {
        return notificationParam;
    }

    public void setNotificationParam(NotificationParam notificationParam) {
        this.notificationParam = notificationParam;
    }

    public InquiryItem getInquiryItem() {
        return inquiryItem;
    }

    public void setInquiryItem(InquiryItem inquiryItem) {
        this.inquiryItem = inquiryItem;
    }
}
