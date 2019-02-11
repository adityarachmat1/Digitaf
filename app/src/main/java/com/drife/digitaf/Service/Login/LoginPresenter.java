package com.drife.digitaf.Service.Login;


import android.content.Context;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.LoginResult;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void login(final String fcm_reg_id,final String username, final String password, final LoginCallback callback){
        api.login(ConnectionUrl.AUTH,fcm_reg_id,username,password).enqueue(new Callback<BaseObjectResponse<LoginResult>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<LoginResult>> call, Response<BaseObjectResponse<LoginResult>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"login","onResponse", JSONProcessor.toJSON(response.body()));
                if (response.body() != null){
                    try{
                        if(response.body().getStatus()==200){
                            callback.onSuccess(response.body().getData());
                        }else if(response.body().getStatus()==401){
                            callback.onUserNotActive();
                        }else if(response.body().getStatus()==403){
                            callback.onFailed(response.body().getMessage());
                        } else if(response.body().getStatus()==501){
                            callback.onPasswordExpired(JSONProcessor.toJSON(response.body()));
                        }else{
                            callback.onFailed(response.body().getMessage());
                        }
                        counter = 0;
                    }catch (Exception e){

                    }
                }else{
                    if(counter <= 1){
                        login(fcm_reg_id,username,password,callback);
                        counter++;
                    }else{
                        counter = 0;
                        String message = "Login gagal, cek kembali koneksi anda";
                        callback.onFailed(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<LoginResult>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"login","onFailure",t.getMessage());
                callback.onFailed(t.getMessage());
                counter = 0;
            }
        });
    }

    public static void loginFingerprint(final Context context, final LoginCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        //LogUtility.logging(TAG,LogUtility.infoLog,"loginFingerprint","token", token);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.loginFingerprint(ConnectionUrl.AUTH_FINGERPRINT,header).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    LogUtility.logging(TAG,LogUtility.infoLog,"loginFingerprint","onResponse", JSONProcessor.toJSON(response.body()));
                    if(response.body() != null){
                        JSONObject rspn = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = rspn.getInt("STATUS");
                        LogUtility.logging(TAG,LogUtility.infoLog,"loginFingerprint","status", status+"");
                        if(status==4002){
                            callback.onTokenExpired();
                        }else if(status==200){
                            LoginResult loginResult = new Gson().fromJson(rspn.getJSONObject("DATA").toString(),LoginResult.class);
                            callback.onSuccess(loginResult);
                        }else if(status==401){
                            callback.onUserNotActive();
                        }else if(status==403){
                            callback.onFailed(rspn.getString("MESSAGE"));
                        }else if(status==4003){
                            callback.onTokenExpired();
                        }else if(status==501){
                            callback.onPasswordExpired(JSONProcessor.toJSON(response.body()));
                        }
                        counter = 0;
                    }else{
                        if(counter <= 1){
                            loginFingerprint(context,callback);
                            counter++;
                        }else{
                            counter = 0;
                        }
                    }
                } catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"login","JSONException",e.getMessage());
                    callback.onFailed(e.getMessage());
                    counter = 0;
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"loginFingerprint","onFailure",t.getMessage());
                callback.onFailed(t.getMessage());
                counter = 0;
            }
        });
    }

    public static void refreshToken(final Context context, final LoginCallback callback){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.refreshToken(ConnectionUrl.REFRESH_TOKEN,header).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    LogUtility.logging(TAG,LogUtility.infoLog,"refreshToken","onResponse", JSONProcessor.toJSON(response.body()));
                    if(response.body() != null){
                        JSONObject rspn = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = rspn.getInt("STATUS");
                        LogUtility.logging(TAG,LogUtility.infoLog,"refreshToken","status", status+"");
                        if(status==200){
                            String token = rspn.getJSONObject("DATA").getString("token");
                            callback.onTokenRefreshed(token);
                            counter = 0;
                        }else if(status==4002){
                            //callback.onTokenExpired();
                            if(counter <= 1){
                                refreshToken(context,callback);
                                counter++;
                            }else{
                                counter = 0;
                                callback.onTokenExpired();
                            }
                        }else if(status==4003){
                            //callback.onTokenExpired();
                            if(counter <= 1){
                                refreshToken(context,callback);
                                counter++;
                            }else{
                                counter = 0;
                                callback.onTokenExpired();
                            }
                        }
                    }else{
                        if(counter <= 1){
                            refreshToken(context,callback);
                            counter++;
                        }else{
                            counter = 0;
                            callback.onTokenExpired();
                        }
                    }
                    //counter = 0;
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"refreshToken","JSONException",e.getMessage());
                    counter = 0;
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"refreshToken","onFailure",t.getMessage());
                counter = 0;
            }
        });
    }

    public static void lupaPassword(final Context context, final String email, final LupaPasswordCallback callback){
        api.lupaPassword(ConnectionUrl.LUPA_PASSWORD,email).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"lupaPassword","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    if(response.body() != null){
                        JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = jsonObject.getInt("STATUS");
                        if(status==200){
                            callback.onSuccessLupaPassword();
                        }else{
                            callback.onFailedLupaPassword();
                        }
                        counter = 0;
                    }else{
                        if(counter <= 1){
                            lupaPassword(context,email,callback);
                            counter++;
                        }else{
                            counter = 0;
                            callback.onLoopEnd();
                        }
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"lupaPassword","JSONException",e.getMessage());
                    counter = 0;
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"lupaPassword","onFailure",t.getMessage());
            }
        });
    }
}
