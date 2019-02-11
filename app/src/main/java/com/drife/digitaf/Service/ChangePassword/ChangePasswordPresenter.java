package com.drife.digitaf.Service.ChangePassword;


import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Service.Login.LoginCallback;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.LoginResult;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordPresenter {
    private static final String TAG = ChangePasswordPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void gantiPassword(final Context context, final String old_password, final String new_password, final String new_password_confirmation, final ChangePasswordCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.GANTI_PASSWORD;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.gantiPassword(url,old_password, new_password, new_password_confirmation, header).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"lupaPassword","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response.body() != null){
                        JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = jsonObject.getInt("STATUS");
                        if(status==200){
                            callback.onSuccessChangePassword();
                        }else{
                            callback.onFaileChangePassword();
                        }
                    }else{
                        callback.onFaileChangePassword();
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"lupaPassword","JSONException",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"lupaPassword","onFailure",t.getMessage());
            }
        });
    }
}
