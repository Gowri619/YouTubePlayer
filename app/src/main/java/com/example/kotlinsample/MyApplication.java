package com.example.kotlinsample;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.kotlinsample.di.component.ApplicationComponent;
import com.example.kotlinsample.di.component.DaggerApplicationComponent;
import com.example.kotlinsample.di.component.InjectorComponent;
import com.example.kotlinsample.di.component.ServiceComponent;
import com.example.kotlinsample.di.module.ActivityModule;
import com.example.kotlinsample.di.module.AndroidModule;
import com.example.kotlinsample.di.module.ApplicationModule;
import com.example.kotlinsample.di.module.ServiceModule;
import com.example.kotlinsample.ui.activity.BaseActivity;
import com.example.kotlinsample.ui.activity.HomeActivity;
import org.jetbrains.annotations.NotNull;

public class MyApplication extends Application {

    private ApplicationComponent injectorComponent;
    private static MyApplication applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(@NotNull Thread thread, @NotNull Throwable e) {

                        handleUncaughtException(thread, e);
                    }
                });

        injectorComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .androidModule(new AndroidModule(this))
                .build();

    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        try {

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    MyApplication.getInstance().getBaseContext(), 0, intent, intent.getFlags());

            AlarmManager mgr = (AlarmManager) MyApplication.getInstance().getBaseContext()
                    .getSystemService(Context.ALARM_SERVICE);

            mgr.set(AlarmManager.RTC, System.currentTimeMillis(),
                    pendingIntent);

            System.exit(0);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static MyApplication getInstance(){
        return applicationContext;
    }

    public InjectorComponent getActivityComponent(BaseActivity baseActivity) {
        return injectorComponent.activityComponentBuilder().activityModule(new ActivityModule(baseActivity)).build();
    }

    public ServiceComponent getServiceComponent() {
        return injectorComponent.serviceComponentBuilder().serviceModule(new ServiceModule()).build();
    }

}
