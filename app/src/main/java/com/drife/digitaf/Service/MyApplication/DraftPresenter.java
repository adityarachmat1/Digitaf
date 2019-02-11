package com.drife.digitaf.Service.MyApplication;

import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.myapplication.model.DraftItem;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DraftPresenter {
    private static final String TAG = DraftPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void getAllDraft(Context context,final DraftCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.DRAFT;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllDraft(url,header).enqueue(new Callback<BaseObjectResponse<List<DraftItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<DraftItem>>> call, Response<BaseObjectResponse<List<DraftItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllDraft","onResponse", JSONProcessor.toJSON(response.body()));
                if (response.body()!=null) {
                    try {
                        if (response != null) {
                            if (response.body().getStatus() == 200) {
                                callback.onSuccessGetDraft(response.body().getData());
                            } else {
                                callback.onFailedGetDraft(response.message());
                            }
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.infoLog, "getAllDraft", "Exception", e.getMessage());
                    }
                }else {
                    callback.onFailedGetDraft(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<DraftItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllDraft","onFailure", t.getMessage());
                callback.onFailedGetDraft(t.getMessage());
            }
        });
    }

    public static void getDraftByQuery(Context context, String query, final DraftCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.DRAFT+query;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllDraft(url,header).enqueue(new Callback<BaseObjectResponse<List<DraftItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<DraftItem>>> call, Response<BaseObjectResponse<List<DraftItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllDraft","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetDraft(response.body().getData());
                        }else{
                            callback.onFailedGetDraft(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"getAllDraft","Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<DraftItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllDraft","onFailure", t.getMessage());
                callback.onFailedGetDraft(t.getMessage());
            }
        });
    }
}
