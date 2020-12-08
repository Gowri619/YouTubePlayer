package com.example.kotlinsample.utils;

import android.app.Activity;

import com.kaopiz.kprogresshud.KProgressHUD;

import static com.example.kotlinsample.ui.activity.HomeActivity.homeActivity;

public class Animations {

    private static KProgressHUD kProgressHUD;

    public static void showAnimatedProgress(Activity activity, String text) {
        if (kProgressHUD == null) {
            kProgressHUD = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(text)
                    .setCancellable(false)
                    .setDimAmount(0.5f);
            if (homeActivity != null && !homeActivity.isFinishing() && !homeActivity.isDestroyed()) {
                kProgressHUD.show();
            }
        }
    }

    public static void hideAnimatedProgress() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
    }
}
