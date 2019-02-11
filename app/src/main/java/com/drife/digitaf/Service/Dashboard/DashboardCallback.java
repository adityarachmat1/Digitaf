package com.drife.digitaf.Service.Dashboard;

import com.drife.digitaf.Module.dashboard.fragment.model.ReportSalesByPackage;

import java.util.List;

public interface DashboardCallback {
    void onSuccessGetReportSalesByPackage(List<ReportSalesByPackage> listReportSalesByPackage);
    void onFailedGetReportSalesByPackage(String message);

    void onSuccessGetAllIncentive(Object object);
    void onFailedGetAllIncentive(String message);
}
