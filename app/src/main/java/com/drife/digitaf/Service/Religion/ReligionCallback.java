package com.drife.digitaf.Service.Religion;

import com.drife.digitaf.retrofitmodule.Model.Religion;
import com.drife.digitaf.retrofitmodule.Model.WayOfPayment;

import java.util.List;

public interface ReligionCallback {
    void onSuccessGetReligion(List<Religion> religions);
    void onFailedGetReligion(String message);
}
