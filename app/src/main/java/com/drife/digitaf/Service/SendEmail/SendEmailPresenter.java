package com.drife.digitaf.Service.SendEmail;

import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendEmailPresenter {
    private static final String TAG = SendEmailPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void sendEmail(final Context context, final SendEmailCallback callback, String param){
        Log.d("singo", "param : "+param);
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.sendEmail(ConnectionUrl.SEND_EMAIL,header,param).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"sendEmail","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                    int STATUS = jsonObject.getInt("STATUS");
                    String MESSAGE = jsonObject.getString("MESSAGE");
                    if(STATUS == 200){
                        callback.onSuccessSendEmail(MESSAGE);
                    }else{
                        callback.onFailedSendEmail(MESSAGE);
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"sendEmail","JSONException",e.getMessage());
                    callback.onFailedSendEmail("Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"sendEmail","onFailure",t.getMessage());
                callback.onFailedSendEmail(t.getMessage());
            }
        });
    }
}
