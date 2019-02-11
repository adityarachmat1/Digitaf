package com.drife.digitaf.Service.Dealer;

import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.Service.Login.LoginPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.Dealer;
import com.drife.digitaf.retrofitmodule.Model.DealerSimple;
import com.drife.digitaf.retrofitmodule.Model.User;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerPresenter {
    private static final String TAG = DealerPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getDealerList(final DealerCallback callback){
        /*api.getAllDealer(ConnectionUrl.DEALER_LIST).enqueue(new Callback<BaseObjectResponse<List<Dealer>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<Dealer>>> call, Response<BaseObjectResponse<List<Dealer>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getDealerList","onResponse", JSONProcessor.toJSON(response.body()));
                if(response != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetDealer(response.body().getData());
                    }else{
                        callback.onFailedGetDealer(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<Dealer>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getDealerList","onFailure", t.getMessage());
                callback.onFailedGetDealer(t.getMessage());
            }
        });*/

        api.getAllSimpleDealer(ConnectionUrl.DEALER_LIST).enqueue(new Callback<BaseObjectResponse<List<DealerSimple>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<DealerSimple>>> call, Response<BaseObjectResponse<List<DealerSimple>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getDealerList","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetDealer(response.body().getData());
                    }else{
                        callback.onFailedGetDealer(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter<=1){
                        getDealerList(callback);
                        counter++;
                    }else{
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<DealerSimple>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getDealerList","onFailure", t.getMessage());
                callback.onFailedGetDealer(t.getMessage());
                counter = 0;
            }
        });

    }

    public static void getDealerListString(){
        api.getAllDealerString(ConnectionUrl.DEALER_LIST).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getDealerList","onResponse", JSONProcessor.toJSON(response.body()));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getDealerList","onFailure", t.getMessage());
                //callback.onFailedGetDealer(t.getMessage());
            }
        });

    }
}
