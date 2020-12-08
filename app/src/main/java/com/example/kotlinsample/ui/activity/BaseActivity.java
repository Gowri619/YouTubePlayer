package com.example.kotlinsample.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.kotlinsample.MyApplication;
import com.example.kotlinsample.di.component.InjectorComponent;
import com.example.kotlinsample.di.utils.DateTimeUtil;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseActivity extends SFMActivity {

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public InjectorComponent injector() {
        return ((MyApplication) getApplicationContext()).getActivityComponent(this);
    }
    public void handleError(Throwable throwable) {
        Timber.e(throwable);
        Toast.makeText(this, DateTimeUtil.getErrorMessage(throwable, this), Toast.LENGTH_SHORT).show();
    }

}
