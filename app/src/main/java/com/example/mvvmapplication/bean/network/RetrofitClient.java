package com.example.mvvmapplication.bean.network;

import android.os.Environment;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static volatile RetrofitClient mInstance;

    private static final String BASE_RUL = "https://gitee.com/fromis_9/test/raw/";

    private Retrofit mRetrofit;

    private RetrofitClient() {
    }

    public static RetrofitClient getInstance() {
        if (null == mInstance) {
            synchronized (RetrofitClient.class) {
                if (null == mInstance) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    public <T> T getService(Class<T> tClass){
        return getRetrofit().create(tClass);
    }

    private Retrofit getRetrofit(){
        if (null == mRetrofit){
            mRetrofit = new Retrofit.Builder().baseUrl(BASE_RUL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }

        return mRetrofit;
    }
}
