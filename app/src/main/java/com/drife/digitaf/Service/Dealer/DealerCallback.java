package com.drife.digitaf.Service.Dealer;

import com.drife.digitaf.retrofitmodule.Model.Dealer;
import com.drife.digitaf.retrofitmodule.Model.DealerSimple;

import java.util.List;

public interface DealerCallback {
    void onSuccessGetDealer(List<DealerSimple> dealer);
    void onFailedGetDealer(String message);
}
