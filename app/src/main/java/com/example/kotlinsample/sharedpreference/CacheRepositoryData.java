package com.example.kotlinsample.sharedpreference;

import android.content.SharedPreferences;

import com.example.kotlinsample.Constants;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class CacheRepositoryData implements CacheRepository {
    private SharedPreferences sharedPreferences;
    private final Gson gson;
    // check first time app launch
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public CacheRepositoryData(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    @NotNull
    @Override
    public String getKey() {
        Constants.IS_FIRST_TIME_LOGGED_IN = sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, Constants.IS_FIRST_TIME_LOGGED_IN);
        return "";
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        sharedPreferences.edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, Constants.IS_FIRST_TIME_LOGGED_IN);
    }
}
