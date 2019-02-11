package com.drife.digitaf.Service.Submit;

import android.content.Context;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Service.Car.CarPresenter;
import com.drife.digitaf.retrofitmodule.Api;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.RetrofitConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitPresenter {
    private static final String TAG = SubmitPresenter.class.getSimpleName();
    private static Api api = RetrofitConfiguration.getRetrofit().create(Api.class);

    static int counter = 0;

    public static void submit(final Context context, final SubmitCallback callback, final String status, String param, String image, String form_id, String pdf){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.submit(ConnectionUrl.SUBMIT,header,param,status,image,form_id, pdf).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","response",response.code()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onResponse",JSONProcessor.toJSON(response.body()));
                if (response.body() != null){
                    try {
                        JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int STATUS = jsonObject.getInt("STATUS");
                        String message = jsonObject.getString("MESSAGE");
                        if(STATUS==200){
                            JSONObject data = jsonObject.getJSONObject("DATA");
                            String id = data.getString("id");
                            callback.onSuccessSubmit(id);
                        }else{
                            callback.onFailedSubmit(message);
                        }
                    }catch (JSONException e){
                        LogUtility.logging(TAG,LogUtility.infoLog,"submit","JSONException",e.getMessage());
                    }
                }else {
                    callback.onFailedSubmit(JSONProcessor.toJSON(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onFailure",t.getMessage());
                callback.onFailedSubmit(t.getMessage());
            }
        });
    }

    public static void draftSubmit(final Context context, final DraftCallback callback, final String status, String param, String image){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        Log.d(TAG, "Submit Present "+status+" "+param+" ");

        api.submitDraft(ConnectionUrl.SUBMIT,header,param,status,image).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //LogUtility.logging(TAG,LogUtility.infoLog,"submit","response",response.code()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onResponse",JSONProcessor.toJSON(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                    int STATUS = jsonObject.getInt("STATUS");
                    String message = jsonObject.getString("MESSAGE");
                    if(STATUS==200){
                        JSONObject data = jsonObject.getJSONObject("DATA");
                        int id = data.getInt("id");
                        String date = data.getString("created_at");
//                        JSONObject form = data.getJSONObject("form_value");
//                        String id = form.toString();
                        callback.onSuccessDraft(id, date);
                    }else{
                        callback.onFailedDraft(message);
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"submit","JSONException",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onFailure",t.getMessage());
                callback.onFailedDraft(t.getMessage());
            }
        });
    }

    public static void draftSubmit(final Context context, final DraftCallback callback, final String status, String param,
                                   String image,String dpPercentage,String isSimulasiPaket,String isSimulasiBudget,String isNonPaket,
                                   String paymentTypeServicePackage, String coverageType){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        Log.d(TAG, "Submit Present "+status+" "+param+" ");

        api.submitDraft(ConnectionUrl.SUBMIT,header,param,status,image,dpPercentage,
                isSimulasiPaket,isSimulasiBudget,isNonPaket,paymentTypeServicePackage,coverageType).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //LogUtility.logging(TAG,LogUtility.infoLog,"submit","response",response.code()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onResponse",JSONProcessor.toJSON(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                    int STATUS = jsonObject.getInt("STATUS");
                    String message = jsonObject.getString("MESSAGE");
                    if(STATUS==200){
                        JSONObject data = jsonObject.getJSONObject("DATA");
                        int id = data.getInt("id");
                        String date = data.getString("created_at");
//                        JSONObject form = data.getJSONObject("form_value");
//                        String id = form.toString();
                        callback.onSuccessDraft(id, date);
                    }else{
                        callback.onFailedDraft(message);
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"submit","JSONException",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onFailure",t.getMessage());
                callback.onFailedDraft(t.getMessage());
            }
        });
    }

    public static void draftSubmit(final Context context, final DraftCallback callback, final String status, String param,
                                   String image,String dpPercentage,String isSimulasiPaket,String isSimulasiBudget,String isNonPaket,
                                   String paymentTypeServicePackage, String coverageType, String isNpwp){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        Log.d(TAG, "Submit Present "+status+" "+param+" ");

        api.submitDraft(ConnectionUrl.SUBMIT,header,param,status,image,dpPercentage,
                isSimulasiPaket,isSimulasiBudget,isNonPaket,paymentTypeServicePackage,coverageType,isNpwp).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //LogUtility.logging(TAG,LogUtility.infoLog,"submit","response",response.code()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onResponse",JSONProcessor.toJSON(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                    int STATUS = jsonObject.getInt("STATUS");
                    String message = jsonObject.getString("MESSAGE");
                    if(STATUS==200){
                        JSONObject data = jsonObject.getJSONObject("DATA");
                        int id = data.getInt("id");
                        String date = data.getString("created_at");
                        callback.onSuccessDraft(id, date);
                    }else{
                        callback.onFailedDraft(message);
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"submit","JSONException",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onFailure",t.getMessage());
                callback.onFailedDraft(t.getMessage());
            }
        });
    }

    public static void submit(final Context context, final SubmitCallback callback, final String status, String param,
                              String image, String form_id, String pdf,
                              String dp_percentage){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.submit(ConnectionUrl.SUBMIT,header,param,status,image,form_id, pdf,
                dp_percentage).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","response",response.code()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","response string",response.message()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onResponse",JSONProcessor.toJSON(response.body()));

                if(response.code() == 413){
                    String message = response.message();
                    callback.onFailedSubmit(message);
                }else{
                    if (response.body() != null){
                        try {
                            JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                            int STATUS = jsonObject.getInt("STATUS");
                            String message = jsonObject.getString("MESSAGE");
                            if(STATUS==200){
                                JSONObject data = jsonObject.getJSONObject("DATA");
                                String id = data.getString("id");

                                callback.onSuccessSubmit(id);
                            }else{
                                callback.onFailedSubmit(message);
                            }
                        }catch (JSONException e){
                            LogUtility.logging(TAG,LogUtility.infoLog,"submit","JSONException",e.getMessage());
                        }
                    }else {
                        callback.onFailedSubmit("Submit gagal...");
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onFailure",t.getMessage());
                callback.onFailedSubmit(t.getMessage());
            }
        });
    }

    public static void submit(final Context context, final SubmitCallback callback, final String status, String param,
                              String image, String form_id, String pdf,
                              String dp_percentage,String is_corporate,String is_simulasi_paket, String is_simulasi_budget,
                              String is_non_paket, String is_npwp, String payment_type_service_package, String coverage_type){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);

        api.submit(ConnectionUrl.SUBMIT,header,param,status,image,form_id, pdf,
                dp_percentage,is_corporate,is_simulasi_paket,is_simulasi_budget,is_non_paket,
                is_npwp,payment_type_service_package,coverage_type).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","response",response.code()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","response string",response.message()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onResponse",JSONProcessor.toJSON(response.body()));

                if(response.code() == 413){
                    String message = response.message();
                    callback.onFailedSubmit(message);
                }else{
                    if (response.body() != null){
                        try {
                            JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                            int STATUS = jsonObject.getInt("STATUS");
                            String message = jsonObject.getString("MESSAGE");
                            if(STATUS==200){
                                JSONObject data = jsonObject.getJSONObject("DATA");
                                String id = data.getString("id");

                                callback.onSuccessSubmit(id);
                            }else{
                                callback.onFailedSubmit(message);
                            }
                        }catch (JSONException e){
                            LogUtility.logging(TAG,LogUtility.infoLog,"submit","JSONException",e.getMessage());
                        }
                    }else {
                        callback.onFailedSubmit("Submit gagal...");
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"submit","onFailure",t.getMessage());
                callback.onFailedSubmit(t.getMessage());
            }
        });
    }

    public static void upload(final Context context, final UploadCallback callback, final HashMap<String,String> params){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        LogUtility.logging(TAG,LogUtility.infoLog,"upload","param",JSONProcessor.toJSON(params));
        api.upload(ConnectionUrl.UPLOAD,header,params).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"upload","onResponse",JSONProcessor.toJSON(response.body()));
                try {
                    if (response.body() != null){
                        JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = jsonObject.getInt("STATUS");
                        if(status==200){
                            callback.onSuccessUpload();
                        }else{
                            callback.onFailedUpload();
                        }
                        counter = 0;
                    }else{
                        if(counter <= 1){
                            upload(context,callback, params);
                            counter++;
                        }else{
                            counter = 0;
                            callback.onLoopEnd();
                        }
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"upload","JSONException",e.getMessage());
                    callback.onResultError();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callback.onFailedUpload();
            }
        });
    }

    public static void uploadDocument(final Context context, final UpdateDocumentCallback callback, final String formId, final String image){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        LogUtility.logging(TAG,LogUtility.infoLog,"uploadDocument","formId",formId);
        header.put("Authorization","Bearer "+token);
        api.updateDocument(ConnectionUrl.UPDATE_DOCUMENT,header,formId,image).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"uploadDocument","onResponse",JSONProcessor.toJSON(response.body()));
                try {
                    if(response.body() != null){
                        JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = jsonObject.getInt("STATUS");
                        if(status == 200){
                            callback.onSuccessUpadateDocument();
                        }else {
                            callback.onFailedUpdateDocument();
                        }
                        counter = 0;
                    }else {
                        if(counter <= 1){
                            uploadDocument(context,callback,formId,image);
                            counter++;
                        }else{
                            counter = 0;
                            callback.onLoopEnd();
                        }
                    }
                }catch (JSONException e){
                    counter = 0;
                    callback.onResultFailed();
                }catch (Exception e){
                    counter = 0;
                    callback.onResultFailed();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"uploadDocument","onFailure",t.getMessage());
                counter = 0;
                callback.onFailedUpdateDocument();
            }
        });
    }

    public static void uploadDocument(final Context context, final UpdateDocumentCallback callback, final String formId, final String image,
            final String pdf){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        LogUtility.logging(TAG,LogUtility.infoLog,"uploadDocument","formId",formId);
        header.put("Authorization","Bearer "+token);
        api.updateDocument(ConnectionUrl.UPDATE_DOCUMENT,header,formId,image,pdf).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogUtility.logging(TAG,LogUtility.infoLog,"uploadDocument","onResponse",JSONProcessor.toJSON(response.body()));
                try {
                    if(response.body() != null){
                        JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                        int status = jsonObject.getInt("STATUS");
                        if(status == 200){
                            callback.onSuccessUpadateDocument();
                        }else {
                            callback.onFailedUpdateDocument();
                        }
                        counter = 0;
                    }else {
                        if(counter <= 1){
                            uploadDocument(context,callback,formId,image,pdf);
                            counter++;
                        }else{
                            counter = 0;
                            callback.onLoopEnd();
                        }
                    }
                }catch (JSONException e){
                    counter = 0;
                    callback.onResultFailed();
                }catch (Exception e){
                    counter = 0;
                    callback.onResultFailed();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"uploadDocument","onFailure",t.getMessage());
                counter = 0;
                callback.onFailedUpdateDocument();
            }
        });

    }

    public static void draft(final Context context, final DraftCallback callback, final String status, String param, String form_id, String pdf
    , String dp_percentage, String is_corporate, String is_simulasi_paket, String is_simulasi_budget, String is_non_paket,
                             String is_npwp, String payment_type_service_package, String coverage_type){
        String token = SharedPreferenceUtils.getToken(context);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
        api.draft(ConnectionUrl.SUBMIT,header,param,status,form_id, pdf, dp_percentage, is_corporate, is_simulasi_paket, is_simulasi_budget,
                is_non_paket, is_npwp, payment_type_service_package, coverage_type).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //LogUtility.logging(TAG,LogUtility.infoLog,"submit","response",response.code()+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"draft","onResponse",JSONProcessor.toJSON(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject(JSONProcessor.toJSON(response.body()));
                    int STATUS = jsonObject.getInt("STATUS");
                    String message = jsonObject.getString("MESSAGE");
                    if(STATUS==200){
                        JSONObject data = jsonObject.getJSONObject("DATA");
                        String date = data.getString("updated_at");
                        int id = data.getInt("id");
                        callback.onSuccessDraft(id, date);
                    }else{
                        callback.onFailedDraft(message);
                    }
                }catch (JSONException e){
                    LogUtility.logging(TAG,LogUtility.infoLog,"draft","JSONException",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogUtility.logging(TAG,LogUtility.infoLog,"draft","onFailure",t.getMessage());
                callback.onFailedDraft(t.getMessage());
            }
        });
    }

}
