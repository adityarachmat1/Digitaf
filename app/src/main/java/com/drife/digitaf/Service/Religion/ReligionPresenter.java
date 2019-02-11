package com.drife.digitaf.Service.Religion;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.Religion;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReligionPresenter {
    private static String TAG = ReligionPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getAllReligion(final Context context, final ReligionCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.getAllReligion(ConnectionUrl.RELIGIONS,header).enqueue(new Callback<BaseObjectResponse<List<Religion>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<Religion>>> call, Response<BaseObjectResponse<List<Religion>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllReligion","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetReligion(response.body().getData());
                    }else{
                        callback.onFailedGetReligion(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        getAllReligion(context,callback);
                        counter++;
                    }else{
                        callback.onFailedGetReligion("Sync gagal");
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<Religion>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllReligion","onFailure", t.getMessage());
                callback.onFailedGetReligion(t.getMessage());
                counter = 0;
            }
        });
    }
}
