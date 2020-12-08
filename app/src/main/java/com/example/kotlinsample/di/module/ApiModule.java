package com.example.kotlinsample.di.module;

import androidx.annotation.Nullable;
import com.example.kotlinsample.di.apiclass.AnnotationExclusionStrategy;
import com.example.kotlinsample.di.apiclass.ApiFactory;
import com.example.kotlinsample.di.apiclass.UploadApi;
import com.example.kotlinsample.sharedpreference.CacheRepository;
import com.example.kotlinsample.di.utils.TypeAdapterUtils;
import com.example.kotlinsample.di.wrapper.ErrorWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Boolean.class, TypeAdapterUtils.getBooleanAdapter())
                .registerTypeAdapter(boolean.class, TypeAdapterUtils.getBooleanAdapter())
                .registerTypeAdapter(DateTime.class, TypeAdapterUtils.getDateTimeAdapter())
                .registerTypeAdapter(ErrorWrapper.class, TypeAdapterUtils.getErrorMapAdapter())
                .addSerializationExclusionStrategy(new AnnotationExclusionStrategy())
                .create();
    }

    @Provides
    public ApiFactory provideApiFactory(Gson gson, CacheRepository cacheRepository) {
        return new ApiFactory(gson, cacheRepository);
    }

    /* Date: 19-07-2019 , Name: AIT - ANDROID  , Release Version: US-Pilot Bug ID : 1374
                                    Desc : Added @Nullable annotation since need to allow the null value too from the provider methods*/
    @Nullable
    @Provides
    public UploadApi provideUploadApi(@Nullable ApiFactory apiFactory) {
        return apiFactory.createUploadApi();
    }
}
