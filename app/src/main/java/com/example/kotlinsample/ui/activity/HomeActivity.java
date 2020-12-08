package com.example.kotlinsample.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.kotlinsample.R;
import com.example.kotlinsample.di.exception.UploadInProgressException;
import com.example.kotlinsample.di.fragmentmanager.FragmentChannel;
import com.example.kotlinsample.model.VideosModel;
import com.example.kotlinsample.presenter.HomePresenter;
import com.example.kotlinsample.service.UploadService;
import com.example.kotlinsample.sharedpreference.CacheRepository;
import com.example.kotlinsample.ui.fragment.VideoPlayerFragment;
import com.example.kotlinsample.ui.fragment.StartFragment;
import com.example.kotlinsample.ui.fragment.WelcomeFragment;

import javax.inject.Inject;

import io.reactivex.Single;

public class HomeActivity extends BaseActivity implements FragmentChannel, HomePresenter.View {

    @Inject
    CacheRepository cacheRepository;
    @Inject
    HomePresenter homePresenter;

    public static HomeActivity homeActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getFrameLayoutContainerId() {
        return R.id.activity_home_fl_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        getSupportActionBar().hide();
        homePresenter.setView(this);
        homeActivity = this;
        //checking logged in already
        if (savedInstanceState == null) {
            homePresenter.init(getServiceFlowable());
        }
    }

    public void showWelcome() {
        simpleFragmentManager.replaceFragment(WelcomeFragment.newInstance());
    }

    @Override
    public void showVideo(VideosModel.Item item) {
        simpleFragmentManager.replaceFragment(VideoPlayerFragment.newInstance(item));
    }

    @Override
    public void showHome() {
        if (!isFinishing() && !isDestroyed()) {
            simpleFragmentManager.popUpAll();
            simpleFragmentManager.replaceFragment(StartFragment.newInstance());
        }
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void handleError(Throwable throwable) {
        super.handleError(throwable);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        simpleFragmentManager.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        simpleFragmentManager.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose();
    }

    @Override
    public void dispose() {
        simpleFragmentManager.dispose();
        homePresenter.dispose();
    }

    private Single<Boolean> getServiceFlowable() {
        return Single.create(e -> bindService(new Intent(this,
                UploadService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                boolean running = ((UploadService.UploadBinder) service).isRunning();
                unbindService(this);
                if (!running) {
                    e.onSuccess(running);
                } else {
                    e.onError(new UploadInProgressException());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE));
    }

    @Override
    public void onBackPressed() {
        simpleFragmentManager.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
