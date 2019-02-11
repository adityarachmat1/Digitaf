package com.drife.digitaf.Service.Marital;

import com.drife.digitaf.retrofitmodule.Model.MaritalStatus;

import java.util.List;

public interface MaritalCallback {
    void onSuccessGetMarital(List<MaritalStatus> maritals);
    void onFailedGetMarital(String message);
}
