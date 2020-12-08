package com.example.kotlinsample.di.fragmentmanager;

public interface FragmentPresenter extends Presenter {

    interface View extends Presenter.View {

        void setTitle();

        void handleError(Throwable throwable);
    }
}
