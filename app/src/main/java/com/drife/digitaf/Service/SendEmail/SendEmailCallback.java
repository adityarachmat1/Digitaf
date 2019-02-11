package com.drife.digitaf.Service.SendEmail;

public interface SendEmailCallback {
    void onSuccessSendEmail(String message);
    void onFailedSendEmail(String message);
}
