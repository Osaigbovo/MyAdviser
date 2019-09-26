package com.osaigbovo.myadviser.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.osaigbovo.myadviser.MyAdviserApp;

import org.jetbrains.annotations.NotNull;

import dagger.android.AndroidInjection;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

public class AppInjector {

    private AppInjector() {
    }

    public static void init(MyAdviserApp myAdviserApp) {

        DaggerAppComponent
                .builder()
                .application(myAdviserApp)
                .build()
                .inject(myAdviserApp);

        myAdviserApp
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(@NotNull Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }

                    @Override
                    public void onActivityStarted(@NotNull Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(@NotNull Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(@NotNull Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(@NotNull Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(@NotNull Activity activity) {

                    }
                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasAndroidInjector) {
            AndroidInjection.inject(activity);
        }

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                                              Bundle savedInstanceState) {
                                    if (f instanceof Injectable) {
                                        AndroidSupportInjection.inject(f);
                                    }
                                }
                            }, true);
        }
    }

}
