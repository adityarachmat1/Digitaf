package com.drife.digitaf.Service.MyApplication;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.Module.myapplication.model.NeedActionItem;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeedActionPresenter {
    private static final String TAG = NeedActionPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void getAllNeedAction(Context context,final NeedActionCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.NEEDACTION;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllNeedAction(url,header).enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetNeedAction(response.body().getData());
                        }else{
                            callback.onFailedGetNeedAction(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","onFailure", t.getMessage());
                callback.onFailedGetNeedAction(t.getMessage());
            }
        });
    }

    public static void getAllNeedAction(Context context,final NeedActionCallback callback, int limit, int offset){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.NEEDACTION+"&limit="+limit+"&offset="+offset;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllNeedAction(url,header).enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","onResponse", JSONProcessor.toJSON(response.body()));
                if (response.body()!=null) {
                    try {
                        if (response != null) {
                            if (response.body().getStatus() == 200) {
                                callback.onSuccessGetNeedAction(response.body().getData());
                            } else {
                                callback.onFailedGetNeedAction(response.message());
                            }
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.infoLog, "getAllNeedAction", "Exception", e.getMessage());
                    }
                }else {
                    callback.onFailedGetNeedAction(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","onFailure", t.getMessage());
                callback.onFailedGetNeedAction(t.getMessage());
            }
        });
    }

    public static void getNeedActionByQuery(Context context, String query, final NeedActionCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        final String url = ConnectionUrl.NEEDACTION+query;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllNeedAction(url,header).enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","onResponse", " "+url+" "+JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetNeedAction(response.body().getData());
                        }else{
                            callback.onFailedGetNeedAction(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllNeedAction","onFailure", t.getMessage());
                callback.onFailedGetNeedAction(t.getMessage());
            }
        });
    }
}
