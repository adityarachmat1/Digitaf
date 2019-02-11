package com.drife.digitaf.Service.Car;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Service.Dealer.DealerPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarPresenter {
    private static final String TAG = CarPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getAllCars(final Context context, final CarCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.getAllCars(ConnectionUrl.CAR_MODEL,header).enqueue(new Callback<BaseObjectResponse<List<CarModel>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<CarModel>>> call, Response<BaseObjectResponse<List<CarModel>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllCars","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetCarModel(response.body().getData());
                    }else{
                        callback.onFailedGetCarModel(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        getAllCars(context,callback);
                        counter++;
                    }else{
                        callback.onFailedGetCarModel("Sync gagal");
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<CarModel>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllCars","onFailure", t.getMessage());
                callback.onFailedGetCarModel(t.getMessage());
                counter = 0;
            }
        });
    }
}
