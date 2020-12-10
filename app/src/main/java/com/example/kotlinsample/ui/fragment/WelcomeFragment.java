package com.example.kotlinsample.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.kotlinsample.R;
import com.example.kotlinsample.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

import static com.example.kotlinsample.ui.activity.HomeActivity.homeActivity;

public class WelcomeFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "Welcome";

    @BindView(R.id.mViewpager_ID)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private ViewPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);
    }

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welcome;
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void dispose() {

    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new ViewPagerAdapter(homeActivity.getSupportFragmentManager());
        mAdapter.addFragment(StartFragment.newInstance(), "Videos");
        mAdapter.addFragment(ReportFragment.newInstance(), "Reports");
        mAdapter.addFragment(new Fragment3(), "Third");
        mAdapter.addFragment(new BlankFragment2(), "Fourth");
        viewPager.setAdapter(mAdapter);
        viewPager.setSaveFromParentEnabled(false);
        tabLayout.setupWithViewPager(viewPager);
    }
}
