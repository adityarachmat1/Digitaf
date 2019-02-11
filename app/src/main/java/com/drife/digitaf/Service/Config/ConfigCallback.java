package com.drife.digitaf.Service.Config;

import com.drife.digitaf.retrofitmodule.Model.CarModel;

import org.json.JSONObject;

import java.util.List;

public interface ConfigCallback {
    void onSuccessGetConfig(JSONObject jsonObject);
    void onFailedGetConfig(String message);
}
