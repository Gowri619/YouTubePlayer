package com.example.kotlinsample.di.component;

import com.example.kotlinsample.di.module.AndroidModule;
import com.example.kotlinsample.di.module.ApplicationModule;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AndroidModule.class})
public interface ApplicationComponent {
    InjectorComponent.Builder activityComponentBuilder();

    ServiceComponent.Builder serviceComponentBuilder();
}
