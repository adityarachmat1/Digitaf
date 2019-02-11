package com.drife.digitaf.Service.Change_number;

import com.drife.digitaf.Service.Notification.NotificationItem;

import java.util.List;

public interface Change_numberCallback {
    void onSuccessGetChange_number();
    void onFailedGetChange_number();
    void onLoopEnd();
}

