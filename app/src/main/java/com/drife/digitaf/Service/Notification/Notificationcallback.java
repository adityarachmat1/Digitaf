package com.drife.digitaf.Service.Notification;

import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.retrofitmodule.Model.PackageRule;

import java.util.List;

public interface Notificationcallback {
    void onSuccessGetNotification(List<NotificationItem> notificationItems);
    void onFailedGetNotification(String message);
}

