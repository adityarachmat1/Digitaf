package com.drife.digitaf.Service.Config;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigPresenter {
    private static final String TAG = ConfigPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getConfig(final Context context, final ConfigCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.getConfig(ConnectionUrl.CONFIG,header).enqueue(new Callback<BaseObjectResponse>() {
            @Override
            public void onResponse(Call<BaseObjectResponse> call, Response<BaseObjectResponse> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllConfig","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        try {
                            callback.onSuccessGetConfig(new JSONObject(JSONProcessor.toJSON(response.body().getData())));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        callback.onFailedGetConfig(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        getConfig(context,callback);
                        counter++;
                    }else{
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllConfig","onFailure", t.getMessage());
                callback.onFailedGetConfig(t.getMessage());
                counter = 0;
            }
        });
    }
}
