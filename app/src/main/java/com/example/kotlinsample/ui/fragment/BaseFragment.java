package com.example.kotlinsample.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kotlinsample.di.component.InjectorComponent;
import com.example.kotlinsample.di.fragmentmanager.FragmentChannel;
import com.example.kotlinsample.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseFragment extends SFMFragment<FragmentChannel> {

    protected abstract int getLayoutId();

    protected FragmentChannel fragmentChannel;
    protected View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof FragmentChannel){
            fragmentChannel =((FragmentChannel) context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (fragmentChannel == null && getParentFragment() != null && getParentFragment() instanceof FragmentChannel) {
            fragmentChannel = (FragmentChannel) getParentFragment();
        }

        if (fragmentChannel == null) {
            Timber.d("Parent does not implement fragmentChannel.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        Timber.d(getClass().getName() + " onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d(getClass().getName() + " onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d(getClass().getName() + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d(getClass().getName() + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d(getClass().getName() + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d(getClass().getName() + " onStop");
    }

    public InjectorComponent injector() {
        return ((BaseActivity) getContext()).injector();
    }
}
