package com.osaigbovo.myadviser.di;

import android.app.Application;
import android.content.Context;

import com.osaigbovo.myadviser.data.remote.RequestInterface;
import com.osaigbovo.myadviser.data.remote.ServiceGenerator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    RequestInterface provideACMEService() {
        return ServiceGenerator.createService(RequestInterface.class);
    }

}
