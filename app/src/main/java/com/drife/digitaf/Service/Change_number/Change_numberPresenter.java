package com.drife.digitaf.Service.Change_number;

import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Login.Activity.Change_number;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Change_numberPresenter {
    private static final String TAG = Change_numberPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);
    static int counter = 0;

        public static void ChangeNumber(final Context context, final String lead_id, final String change_number, final Change_numberCallback callback){
            String token = SharedPreferenceUtils.getToken(context);

            HashMap<String,String> header = new HashMap<>();
            header.put("Authorization","Bearer "+token);
            api.change_number(ConnectionUrl.UPDATE_PHONE_NUMBER,header,lead_id,change_number).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    LogUtility.logging(TAG,LogUtility.infoLog,"ChangeNumber","onResponse", JSONProcessor.toJSON(response.body()));
                    try {
                        if(response.body() != null){
                            JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                            int status = jsonObject.getInt("STATUS");
                            if(status==200){
                                callback.onSuccessGetChange_number();
                            }else{
                                callback.onFailedGetChange_number();
                            }
                            counter = 0;
                        }else{
                            if(counter <= 1){
                                ChangeNumber(context,lead_id,change_number,callback);
                                counter++;
                            }else{
                                counter = 0;
                                callback.onLoopEnd();
                            }
                        }
                    }catch (JSONException e){
                        LogUtility.logging(TAG,LogUtility.errorLog,"ChangeNumber","JSONException",e.getMessage());
                        counter = 0;
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    LogUtility.logging(TAG,LogUtility.errorLog,"ChangeNumber","onFailure",t.getMessage());
                }
            });
        }
}
