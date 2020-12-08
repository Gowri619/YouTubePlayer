package com.example.kotlinsample.di.module;

import com.example.kotlinsample.MyApplication;
import com.example.kotlinsample.di.component.InjectorComponent;
import com.example.kotlinsample.di.component.ServiceComponent;

import javax.inject.Singleton;

import dagger.Module;

@Singleton
@Module(subcomponents = {InjectorComponent.class, ServiceComponent.class})
public class ApplicationModule {

    private final MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }
}