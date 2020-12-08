package com.example.kotlinsample.di.module;

import android.content.SharedPreferences;

import com.example.kotlinsample.sharedpreference.CacheRepository;
import com.example.kotlinsample.sharedpreference.CacheRepositoryData;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public CacheRepository provideCacheRepository(SharedPreferences sharedPreferences, Gson gson) {
        return new CacheRepositoryData(sharedPreferences, gson);
    }
}

