package com.drife.digitaf.Service.Otp;

import com.drife.digitaf.retrofitmodule.Model.User;

public interface OtpCallback {
    void onSuccess();
    void onFailed(String message);
    void onResend(User user);
    void onFailedResend(String message);
    void onResponseError();
}
