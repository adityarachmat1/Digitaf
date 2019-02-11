package com.drife.digitaf.Service.Depresiasi;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Service.Car.CarPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.Depresiasi;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepresiasiPresenter {
    private static final String TAG = DepresiasiPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getAllDepresiasi(final Context context, final DepresiasiCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.getAllDepresiasi(ConnectionUrl.DEPRESIASI,header).enqueue(new Callback<BaseObjectResponse<List<Depresiasi>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<Depresiasi>>> call, Response<BaseObjectResponse<List<Depresiasi>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllDepresiasi","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetDepresiasi(response.body().getData());
                    }else{
                        callback.onFailedGetDepresiasi(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        getAllDepresiasi(context,callback);
                        counter++;
                    }else{
                        callback.onFailedGetDepresiasi("Sync gagal");
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<Depresiasi>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllDepresiasi","onFailure", t.getMessage());
                callback.onFailedGetDepresiasi(t.getMessage());
                counter = 0;
            }
        });
    }
}
