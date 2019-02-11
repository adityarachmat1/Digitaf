package com.drife.digitaf.Service.Register;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.Service.Otp.OtpPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.User;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    private static final String TAG = RegisterPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void register(final HashMap<String,String> param, final RegisterCallback callback){
        String email = param.get("email");
        String emailConfirmation = param.get("email_confirmation");
        String password = param.get("password");
        String passwordConfirmation = param.get("password_confirmation");
        String ktpNo = param.get("ktp_no");
        String npkNo = param.get("npk_no");
        String fullname = param.get("fullname");
        String isOfficer = param.get("is_officer");
        String dealerId = param.get("dealer_id");
        api.register(ConnectionUrl.REGISTER,email,emailConfirmation,password,passwordConfirmation,
                ktpNo,npkNo,fullname,isOfficer,dealerId).enqueue(new Callback<BaseObjectResponse<User>>() {
            @Override
            public void onResponse(Call<BaseObjectResponse<User>> call, Response<BaseObjectResponse<User>> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"register","onResponse", JSONProcessor.toJSON(response.body()));

                if (response.body() != null){
                    if(response.body().getStatus()==200){
                        callback.onSuccessRegister(response.body().getData());
                    }else {
                        callback.onFailedRegister(response.body().getMessage());
                    }
                    counter = 0;
                }else{
                    if(counter <= 1){
                        register(param,callback);
                        counter++;
                    }else{
                        counter = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseObjectResponse<User>> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.errorLog,"register","onFailure", t.getMessage());
                callback.onFailedRegister(t.getMessage());
                counter = 0;
            }
        });

    }
}
