package com.osaigbovo.myadviser.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.osaigbovo.myadviser.ui.MainViewModel;
import com.osaigbovo.myadviser.util.MainViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindJobViewModel(MainViewModel jobViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MainViewModelFactory factory);
}
