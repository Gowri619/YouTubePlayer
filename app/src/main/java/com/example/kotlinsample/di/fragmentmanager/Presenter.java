package com.example.kotlinsample.di.fragmentmanager;

public interface Presenter {

    void dispose();

    interface View {
        void dispose();
    }
}
