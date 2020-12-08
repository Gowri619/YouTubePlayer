package com.example.kotlinsample.di.apiclass;

import com.example.kotlinsample.BuildConfig;
import com.example.kotlinsample.Constants;
import com.example.kotlinsample.sharedpreference.CacheRepository;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private Retrofit retrofit;
    private Retrofit geocodeRetrofit;
    private CacheRepository cacheRepository;

    public ApiFactory(Gson gson, CacheRepository cacheRepository) {
        if (Constants.BASE_URL != null && !Constants.BASE_URL.isEmpty()) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addCallAdapterFactory(new RxJava2ErrorHandlerCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getClient())
                    .build();
        }
        this.cacheRepository = cacheRepository;
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json;charset=utf-8")
                            .addHeader("Accept", "application/json")
                            .build();

                    return chain.proceed(request);
                }).build();
    }

    public UploadApi createUploadApi() {
        if (retrofit != null) {
            return retrofit.create(UploadApi.class);
        }
        return null;
    }
}
