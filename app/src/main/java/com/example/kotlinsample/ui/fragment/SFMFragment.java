package com.example.kotlinsample.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kotlinsample.di.fragmentmanager.FragmentChannel;
import com.example.kotlinsample.di.fragmentmanager.IFragment;

public abstract class SFMFragment<F extends FragmentChannel> extends Fragment implements IFragment {

    protected F fragmenChannel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getParentFragment() != null && getParentFragment() instanceof FragmentChannel) {
            fragmenChannel = (F) getParentFragment();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context != null && context instanceof FragmentChannel) {
            fragmenChannel = (F) context;
        }

    }
}
