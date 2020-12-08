package com.example.kotlinsample.presenter;

import com.example.kotlinsample.Constants;
import com.example.kotlinsample.di.fragmentmanager.FragmentPresenter;
import com.example.kotlinsample.di.utils.RXUtil;
import com.example.kotlinsample.sharedpreference.CacheRepository;
import com.example.kotlinsample.sharedpreference.GetKey;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements FragmentPresenter {
    private CompositeDisposable disposables;
    private final CacheRepository cacheRepository;
    private final GetKey getKey;
    private View view;

    public HomePresenter(CacheRepository cacheRepository, GetKey getKey) {
        this.cacheRepository = cacheRepository;
        this.getKey = getKey;
    }

    public void init(Single<Boolean> isUploadRunningSingle) {
        disposables = RXUtil.initDisposables(disposables);
        Disposable disposable = Single.zip(getKey.execute(), isUploadRunningSingle, (key, isRunning) -> key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(key -> {
                    if (Constants.IS_FIRST_TIME_LOGGED_IN) {
                        view.showWelcome();
                    } else {
                        view.showHome();
                    }
                }, throwable -> {
                    view.handleError(throwable);
                });
        disposables.add(disposable);
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        RXUtil.dispose(disposables);
    }

    public interface View extends FragmentPresenter.View {

        void showWelcome();

        void showHome();
    }
}
