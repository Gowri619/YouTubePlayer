package com.example.kotlinsample.di.component;

import com.example.kotlinsample.di.module.ActivityModule;
import com.example.kotlinsample.ui.activity.HomeActivity;
import com.example.kotlinsample.ui.fragment.ReportFragment;
import com.example.kotlinsample.ui.fragment.StartFragment;
import com.example.kotlinsample.ui.fragment.WelcomeFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {ActivityModule.class})
public interface InjectorComponent {

    void inject(HomeActivity homeActivity);

    void inject(StartFragment startFragment);

    void inject(WelcomeFragment welcomeFragment);

    void inject(ReportFragment reportFragment);

    @Subcomponent.Builder
    interface Builder {
        Builder activityModule(ActivityModule activityModule);

        InjectorComponent build();
    }
}
