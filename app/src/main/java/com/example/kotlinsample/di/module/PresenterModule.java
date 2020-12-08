package com.example.kotlinsample.di.module;

import com.example.kotlinsample.presenter.HomePresenter;
import com.example.kotlinsample.sharedpreference.CacheRepository;
import com.example.kotlinsample.sharedpreference.GetKey;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DataModule.class})
public class PresenterModule {

    @Provides
    public HomePresenter provideHomePresenter(CacheRepository cacheRepository, GetKey getKey) {
        return new HomePresenter(cacheRepository, getKey);
    }
}
