package com.drife.digitaf.Service.Inquiry;

import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryPresenter {
    private static final String TAG = InquiryPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    public static void getAllInquiry(Context context,final InquiryCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.INQUIRY;

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllInquiry(url,header).enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onResponse", JSONProcessor.toJSON(response.body()));
                if (response.body() !=null) {
                    try {
                        if (response != null) {
                            if (response.body().getStatus() == 200) {
                                callback.onSuccessGetInquiry(response.body().getData());
                            } else {
                                callback.onFailedGetInquiry(response.message());
                            }
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.errorLog, "getAllInquiry", "Exception", e.getMessage());
                    }
                }else {
                    callback.onFailedGetInquiry(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onFailure", t.getMessage());
                callback.onFailedGetInquiry(t.getMessage());
            }
        });
    }

    public static void getInquiryByQuery(Context context, String query,final InquiryCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.SUBMISSONS+query;
        LogUtility.logging(TAG,LogUtility.infoLog,"getInquiryByQuery","url", url);

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllInquiry(url,header).enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getInquiryByQuery","onResponse", JSONProcessor.toJSON(response.body()));
                if (response.body() !=null) {
                    try {
                        if (response != null) {
                            if (response.body().getStatus() == 200) {
                                callback.onSuccessGetInquiry(response.body().getData());
                            } else {
                                callback.onFailedGetInquiry(response.message());
                            }
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.errorLog, "getAllInquiry", "Exception", e.getMessage());
                    }
                }else {
                    callback.onFailedGetInquiry(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onFailure", t.getMessage());
                callback.onFailedGetInquiry(t.getMessage());
            }
        });
    }

    public static Call getInquiryByQuery_v2(Context context, String query,final InquiryCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.SUBMISSONS+query;
        LogUtility.logging(TAG,LogUtility.infoLog,"getInquiryByQuery","url", url);

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        Call call = api.requestOCR(url,header);

        call.enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getInquiryByQuery","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetInquiry(response.body().getData());
                        }else{
                            callback.onFailedGetInquiry(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getAllInquiry","Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onFailure", t.getMessage());
                callback.onFailedGetInquiry(t.getMessage());
            }
        });
        return call;
    }

    public static void getAllInquiry(Context context,final InquiryCallback callback, int limit,int offset){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.INQUIRY+"&limit="+limit+"&offset="+offset;
        Log.d("singo", "url : "+url);

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllInquiry(url,header).enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onResponse", JSONProcessor.toJSON(response.body()));
                if (response.body() !=null) {
                    try {
                        if (response != null) {
                            if (response.body().getStatus() == 200) {
                                callback.onSuccessGetInquiry(response.body().getData());
                            } else {
                                callback.onFailedGetInquiry(response.message());
                            }
                        }
                    } catch (Exception e) {
                        LogUtility.logging(TAG, LogUtility.errorLog, "getAllInquiry", "Exception", e.getMessage());
                    }
                }else {
                    callback.onFailedGetInquiry(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onFailure", t.getMessage());
                callback.onFailedGetInquiry(t.getMessage());
            }
        });
    }

    public static Call getAllInquiry_v2(Context context,final InquiryCallback callback, int limit,int offset){
        String token = SharedPreferenceUtils.getToken(context);
        String url = ConnectionUrl.INQUIRY+"&limit="+limit+"&offset="+offset;
        Log.d("singo", "url : "+url);

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        Call call = api.requestOCR(url,header);

        call.enqueue(new Callback<BaseObjectResponse<List<InquiryItem>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<InquiryItem>>> call, Response<BaseObjectResponse<List<InquiryItem>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response != null){
                        if(response.body().getStatus()==200){
                            callback.onSuccessGetInquiry(response.body().getData());
                        }else{
                            callback.onFailedGetInquiry(response.message());
                        }
                    }
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"getAllInquiry","Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<InquiryItem>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllInquiry","onFailure", t.getMessage());
                callback.onFailedGetInquiry(t.getMessage());
            }
        });
        return call;
    }
}
