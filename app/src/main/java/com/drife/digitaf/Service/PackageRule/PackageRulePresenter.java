package com.drife.digitaf.Service.PackageRule;

import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Service.Car.CarPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.PackageRule;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageRulePresenter {
    private static final String TAG = PackageRulePresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void getAllPackageRule(final Context context, final PackageRuleCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        //Log.d("singo","user session : "+JSONProcessor.toJSON(SharedPreferenceUtils.getUserSession(context)));
        String id = SharedPreferenceUtils.getUserSession(context).getUser().getDealer().getBranch().getRate_area().getId();
        String url = ConnectionUrl.PACKAGE_RULE+id;
        LogUtility.logging(TAG,LogUtility.infoLog,"getAllPackageRule", "url : ",url);

        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.getAllPackageRule(url,header).enqueue(new Callback<BaseObjectResponse<List<PackageRule>>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<List<PackageRule>>> call, Response<BaseObjectResponse<List<PackageRule>>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllPackageRule","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessGetPackageRule(response.body().getData());
                    }else{
                        callback.onFailedGetModePackageRule(response.message());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        getAllPackageRule(context,callback);
                        counter++;
                    }else{
                        counter = 0;
                        callback.onFailedGetModePackageRule("Sync gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<List<PackageRule>>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"getAllPackageRule","onFailure", t.getMessage());
                callback.onFailedGetModePackageRule(t.getMessage());
                counter = 0;
            }
        });
    }
}
