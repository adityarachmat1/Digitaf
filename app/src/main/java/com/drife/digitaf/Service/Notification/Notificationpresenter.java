package com.drife.digitaf.Service.Notification;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notificationpresenter {
    private static final String TAG = Notificationpresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void getAllnotification(Context context,final Notificationcallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.LIST_NOTIFICATION;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllNotification(url,header).enqueue(new Callback<BaseObjectResponse<List<NotificationItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<NotificationItem>>> call, Response<BaseObjectResponse<List<NotificationItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getNotification","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetNotification(response.body().getData());
                        }else{
                            callback.onFailedGetNotification(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getNotification","Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<NotificationItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getNotification","onFailure", t.getMessage());
                callback.onFailedGetNotification(t.getMessage());
            }
        });
    }
}
