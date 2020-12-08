package com.example.kotlinsample.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.kotlinsample.MyApplication;
import com.example.kotlinsample.di.utils.RXUtil;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class UploadService extends Service {

    private boolean running = false;
    private CompositeDisposable disposables;

    @Override
    public void onCreate() {
        super.onCreate();
        ((MyApplication) getApplicationContext()).getServiceComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new UploadBinder();
    }

    public class UploadBinder extends Binder {
        public boolean isRunning() {
            return running;
        }
    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy");
        stopSelf();
        RXUtil.dispose(disposables);
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Timber.d("onUnbind");
        return super.onUnbind(intent);
    }
}

