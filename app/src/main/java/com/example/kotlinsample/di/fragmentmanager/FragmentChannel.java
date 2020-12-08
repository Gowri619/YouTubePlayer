package com.example.kotlinsample.di.fragmentmanager;

import com.example.kotlinsample.model.VideosModel;

public interface FragmentChannel {

    void showHome();

    void showWelcome();

    void showVideo(VideosModel.Item item);
}
