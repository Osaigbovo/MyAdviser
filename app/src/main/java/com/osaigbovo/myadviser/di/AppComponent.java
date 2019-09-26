package com.osaigbovo.myadviser.di;

import android.app.Application;

import com.osaigbovo.myadviser.MyAdviserApp;
import com.osaigbovo.myadviser.worker.AdviceWorker;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    // Where the dependency injection has to be used.
    void inject(MyAdviserApp app);
    void inject(AdviceWorker adviceWorker);

}
