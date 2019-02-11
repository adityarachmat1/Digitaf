package com.drife.digitaf.retrofitmodule;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.drife.digitaf.retrofitmodule.ConnectionUrl.BASE_URL;

public class RetrofitConfiguration {
    private static String TAG = RetrofitConfiguration.class.getSimpleName();
    private static String TOKEN_PREFERENCE = "token_preference";
    private static String TOKEN = "token";
    private static Context context;


    public static void setApplicationContext(Context applicationContext){
        context = applicationContext;
    }

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        //final String token = "";
        return new OkHttpClient.Builder()
                /*.addInterceptor(
                        new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)
                )*/
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .build();
                        return chain.proceed(request);

                    }
                })
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static void saveToken(String token){
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN,token);
        editor.commit();
    }

    public static String getToken(){
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCE,Context.MODE_PRIVATE);
        return preferences.getString(TOKEN,"");
    }
}
