package com.osaigbovo.myadviser.di;

import com.osaigbovo.myadviser.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}
