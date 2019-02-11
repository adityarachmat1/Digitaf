package com.drife.digitaf.Service.WayOfPayment;

import com.drife.digitaf.retrofitmodule.Model.WayOfPayment;

import java.util.List;

public interface WayOfPaymentCallback {
    void onSuccessGetWOP(List<WayOfPayment> wayOfPayments);
    void onFailedGetWOP(String message);
}
