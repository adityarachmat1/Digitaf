package com.drife.digitaf.Service.Tenor;

import com.drife.digitaf.retrofitmodule.Model.Tenor;

import java.util.List;

public interface TenorCallback {
    void onSuccessGetTenor(List<Tenor> tenors);
    void onFailedGetTenor(String message);
}
