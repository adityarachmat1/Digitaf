package com.drife.digitaf.Service.Tenor;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Service.Car.CarPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.Tenor;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenorPresenter {
    private static final String TAG = TenorPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getAllTenor(final Context context, final TenorCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.getAllTenor(ConnectionUrl.TENOR,header).enqueue(new Callback<BaseObjectResponse<List<Tenor>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<Tenor>>> call, Response<BaseObjectResponse<List<Tenor>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllTenor","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetTenor(response.body().getData());
                    }else{
                        callback.onFailedGetTenor(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter<=1){
                        getAllTenor(context,callback);
                        counter++;
                    }else{
                        callback.onFailedGetTenor("Sync gagal");
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<Tenor>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllTenor","onFailure", t.getMessage());
                callback.onFailedGetTenor(t.getMessage());
                counter = 0;
            }
        });
    }
}
