package com.appentus.realestate;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainApplication extends Application{
    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(this);
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localizationDelegate.onConfigurationChanged(this);
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }
}
