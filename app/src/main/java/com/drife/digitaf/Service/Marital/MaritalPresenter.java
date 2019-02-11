package com.drife.digitaf.Service.Marital;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.MaritalStatus;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaritalPresenter {
    private static String TAG = MaritalPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getAllMarital(final Context context, final MaritalCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.getAllMarital(ConnectionUrl.MARITALS,header).enqueue(new Callback<BaseObjectResponse<List<MaritalStatus>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<MaritalStatus>>> call, Response<BaseObjectResponse<List<MaritalStatus>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllMarital","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetMarital(response.body().getData());
                    }else{
                        callback.onFailedGetMarital(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        getAllMarital(context,callback);
                        counter++;
                    }else{
                        callback.onFailedGetMarital("Sync gagal");
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<MaritalStatus>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllMarital","onFailure", t.getMessage());
                callback.onFailedGetMarital(t.getMessage());
                counter = 0;
            }
        });
    }
}
