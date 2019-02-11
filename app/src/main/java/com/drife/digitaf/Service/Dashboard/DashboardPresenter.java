package com.drife.digitaf.Service.Dashboard;

import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.dashboard.fragment.model.ReportSalesByPackage;
import com.drife.digitaf.Module.home.model.Home_data;
import com.drife.digitaf.Service.Home.HomeCallback;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardPresenter {
    private static final String TAG = DashboardPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void getReportSalesByPackage(Context context, String query, final DashboardCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.REPORT_SALES_BYPACKAGE+query;

        Log.d(TAG, "URL "+url);

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getReportSalesByPackage(url,header).enqueue(new Callback<BaseObjectResponse<List<ReportSalesByPackage>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<ReportSalesByPackage>>> call, Response<BaseObjectResponse<List<ReportSalesByPackage>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getReportSalesByPackage","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetReportSalesByPackage(response.body().getData());
                        }else{
                            callback.onFailedGetReportSalesByPackage(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getReportSalesByPackage","Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<ReportSalesByPackage>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getReportSalesByPackage","onFailure", t.getMessage());
                callback.onFailedGetReportSalesByPackage(t.getMessage());
            }
        });
    }

    public static void getAllIncentive(Context context, String query, final DashboardCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.INCENTIVE_LETTER+query;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllIncentive(url,header).enqueue(new Callback<BaseObjectResponse>() {
            @Override
            public void onResponse(Call<BaseObjectResponse> call, Response<BaseObjectResponse> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllIncentive","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetAllIncentive(response.body().getData());
                        }else{
                            callback.onFailedGetAllIncentive(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getAllIncentive","Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllIncentive","onFailure", t.getMessage());
                callback.onFailedGetAllIncentive(t.getMessage());
            }
        });
    }
}

