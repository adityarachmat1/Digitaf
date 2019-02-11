package com.drife.digitaf.Service.Otp;

import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.Service.Login.LoginPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.User;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpPresenter {
    private static final String TAG = OtpPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void resendOTP(final String email, final OtpCallback callback){
        api.resendOTP(ConnectionUrl.RESEND_OTP,email).enqueue(new Callback<BaseObjectResponse<User>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<User>> call, Response<BaseObjectResponse<User>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"resendOTP","onResponse", JSONProcessor.toJSON(response));
                LogUtility.logging(TAG,LogUtility.infoLog,"resendOTP","onResponse", JSONProcessor.toJSON(response.body()));
                if (response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onResend(response.body().getData());
                    }else{
                        callback.onFailedResend(response.body().getMessage());
                    }
                    counter = 0;
                }else{
                    if(counter<=1){
                        resendOTP(email,callback);
                        counter++;
                    }else{
                        counter = 0;
                        callback.onResponseError();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<User>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"resendOTP","onResponse", t.getMessage());
                callback.onFailed(t.getMessage());
                counter = 0;
                //callback.onResponseError();
            }
        });
    }

    public static void resendOTP_v2(final String email, final OtpCallback callback){
        api.resendOTP_v2(ConnectionUrl.RESEND_OTP,email).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"resendOTP","onResponse", JSONProcessor.toJSON(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                    if(jsonObject != null){
                        int status = jsonObject.getInt("STATUS");
                        if(status == 200){
                            User user = new Gson().fromJson(jsonObject.getString("DATA"),User.class);
                            callback.onResend(user);
                        }else if(status == 500){
                            String message = jsonObject.getString("MESSAGE");
                            callback.onFailed(message);
                        }


                        /*if (response.body() != null){
                            if(response.body().getStatus()==200){
                                callback.onResend(response.body().getData());
                            }else{
                                callback.onFailedResend(response.body().getMessage());
                            }
                            counter = 0;
                        }else{
                            if(counter<=1){
                                resendOTP(email,callback);
                                counter++;
                            }else{
                                counter = 0;
                                callback.onResponseError();
                            }
                        }*/
                    }
                }catch (JSONException e){
                    callback.onFailed(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"resendOTP","onResponse", t.getMessage());
                callback.onFailed(t.getMessage());
                counter = 0;
                //callback.onResponseError();
            }
        });
    }

    public static void validateOTP(final String otp, final OtpCallback callback){
        api.validateOTP(ConnectionUrl.VALIDATE_OTP,otp).enqueue(new Callback<BaseObjectResponse<User>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<User>> call, Response<BaseObjectResponse<User>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"validateOTP","onResponse", JSONProcessor.toJSON(response.body()));
                if(response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccess();
                    }else {
                        String message = "Otp is not valid";
                        callback.onFailed(message);
                    }
                    counter = 0;
                }else{
                    if(counter<=1){
                        validateOTP(otp,callback);
                        counter++;
                    }else{
                        counter = 0;
                        callback.onResponseError();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<User>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"validateOTP","onFailure", t.getMessage());
                callback.onFailed(t.getMessage());
                counter = 0;
                callback.onResponseError();
            }
        });
    }
}
