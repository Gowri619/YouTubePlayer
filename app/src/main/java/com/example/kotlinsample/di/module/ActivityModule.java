package com.example.kotlinsample.di.module;

import com.example.kotlinsample.di.component.ActivityScope;
import com.example.kotlinsample.ui.activity.BaseActivity;

import dagger.Module;

@ActivityScope
@Module(includes = {PresenterModule.class})
public class ActivityModule {

    private final BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }
}
