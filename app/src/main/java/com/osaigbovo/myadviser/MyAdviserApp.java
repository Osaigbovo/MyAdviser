package com.osaigbovo.myadviser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.multidex.MultiDexApplication;

import com.osaigbovo.myadviser.di.AppInjector;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import timber.log.Timber;

public class MyAdviserApp extends MultiDexApplication implements HasAndroidInjector {

    private static MyAdviserApp instance;

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.i("Creating our Application");
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        AppInjector.init(this);
        return androidInjector;
    }

    public static boolean hasNetwork() {
        return instance.checkIfHasNetwork();
    }

    private boolean checkIfHasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
