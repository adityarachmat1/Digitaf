package com.drife.digitaf.Service.WayOfPayment;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.WayOfPayment;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WayOfPaymentPresenter {
    private static String TAG = WayOfPaymentPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getAllWOP(final Context context, final WayOfPaymentCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.getAllWOP(ConnectionUrl.WAY_OF_PAYMENT,header).enqueue(new Callback<BaseObjectResponse<List<WayOfPayment>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<WayOfPayment>>> call, Response<BaseObjectResponse<List<WayOfPayment>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllWOP","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetWOP(response.body().getData());
                    }else{
                        callback.onFailedGetWOP(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        getAllWOP(context,callback);
                        counter++;
                    }else{
                        callback.onFailedGetWOP("Sync gagal");
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<WayOfPayment>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllWOP","onFailure", t.getMessage());
                callback.onFailedGetWOP(t.getMessage());
                counter = 0;
            }
        });
    }
}
