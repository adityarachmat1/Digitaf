package com.drife.digitaf.Service.Depresiasi;

import com.drife.digitaf.retrofitmodule.Model.Depresiasi;

import java.util.List;

public interface DepresiasiCallback {
    void onSuccessGetDepresiasi(List<Depresiasi> depresiasis);
    void onFailedGetDepresiasi(String message);
}
