package com.drife.digitaf.Service.Home;

import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.home.model.Home_data;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private static final String TAG = HomePresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void getHome_data(Context context, final HomeCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.Home_summary;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getHome_data(url,header).enqueue(new Callback<BaseObjectResponse<Home_data>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<Home_data>> call, Response<BaseObjectResponse<Home_data>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getHome","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetHome_data(response.body().getData());
                        }else{
                            callback.onFailedGetHome_data(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getHome","Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<Home_data>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getHome","onFailure", t.getMessage());
                callback.onFailedGetHome_data(t.getMessage());
            }
        });
    }

    public static void getHomeDataByQuery(Context context, String query, final HomeCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.Home_summary+query;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getHome_data(url,header).enqueue(new Callback<BaseObjectResponse<Home_data>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<Home_data>> call, Response<BaseObjectResponse<Home_data>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getHomeByQuery","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetHome_data(response.body().getData());
                        }else{
                            callback.onFailedGetHome_data(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getHomeByQuery","Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<Home_data>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getHomeByQuery","onFailure", t.getMessage());
                callback.onFailedGetHome_data(t.getMessage());
            }
        });
    }
}

