package com.drife.digitaf.Service.Register;

import com.drife.digitaf.retrofitmodule.Model.User;

public interface RegisterCallback {
    void onSuccessRegister(User user);
    void onFailedRegister(String message);
}
