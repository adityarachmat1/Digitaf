package com.drife.digitaf.Service.OCR;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.OCRData;
import com.drife.digitaf.retrofitmodule.Model.OCRResult;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OCRPresenter {
    private static final String TAG = OCRPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static Call requestOCR(final Context context, final String param, final OCRCallback callback){
        //String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> body = new HashMap<>();
        body.put("optimized","0");
        body.put("image",param);
        //header.put("Authorization","Bearer "+token);
        //header.put("Content-Type","application/json");
        Call call = api.requestOCR(ConnectionUrl.OCR_REQUEST,body);
        /*api.requestOCR(ConnectionUrl.OCR_REQUEST,body).enqueue(new Callback<OCRData<OCRResult>>() {
            @Override
            public void onResponse(Call<OCRData<OCRResult>> call, Response<OCRData<OCRResult>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"requestOCR","response", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    callback.onSuccessOCR(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<OCRData<OCRResult>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"requestOCR","onFailure", t.getMessage());
                callback.onFailedOCR(t.getMessage());
            }
        });*/

        call.enqueue(new Callback<OCRData<OCRResult>>() {
            @Override
            public void onResponse(Call<OCRData<OCRResult>> call, Response<OCRData<OCRResult>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"requestOCR","response", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    callback.onSuccessOCR(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<OCRData<OCRResult>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"requestOCR","onFailure", t.getMessage());
                callback.onFailedOCR(t.getMessage());
            }
        });

        return call;
    }
}
