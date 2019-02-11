package com.drife.digitaf.Service.Login;


import com.drife.digitaf.retrofitmodule.Model.LoginResult;
import com.drife.digitaf.retrofitmodule.Model.User;

public interface LoginCallback {
    void onSuccess(LoginResult data);
    void onFailed(String message);
    void onUserNotActive();
    void onTokenExpired();
    void onTokenRefreshed(String token);
    void onPasswordExpired(String token);
}
