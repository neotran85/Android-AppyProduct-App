package com.appyhome.appyproduct.mvvm.di.component;

import android.app.Application;

import com.appyhome.appyproduct.mvvm.AppyProductApp;
import com.appyhome.appyproduct.mvvm.di.builder.ActivityBuilder;
import com.appyhome.appyproduct.mvvm.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(AppyProductApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

}
