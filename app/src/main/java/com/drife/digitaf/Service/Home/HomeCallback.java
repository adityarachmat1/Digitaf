package com.drife.digitaf.Service.Home;

import com.drife.digitaf.Module.home.model.Home_data;

import java.util.List;

public interface HomeCallback {
    void onSuccessGetHome_data(Home_data homedata);

    void onFailedGetHome_data(String message);
}
