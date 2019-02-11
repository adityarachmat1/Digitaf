package com.drife.digitaf.Service.Profile;


import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahProfilePresenter {
    private static final String TAG = UbahProfilePresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void gantiFotoProfil(final Context context, final String image, final UbahProfileCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.GANTI_FOTOPROFIL;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.gantiFotoProfil(url, image, header).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"gantiProfil","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response.body() != null){
                        JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = jsonObject.getInt("STATUS");
                        JSONObject data = jsonObject.optJSONObject("DATA");
                        if(status==200){
                            callback.onSuccessUbahProfile(data);
                        }else{
                            callback.onFaileUbahProfile();
                        }
                    }else{
                        callback.onFaileUbahProfile();
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"gantiProfil","JSONException",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"gantiProfil","onFailure",t.getMessage());
            }
        });
    }
}
