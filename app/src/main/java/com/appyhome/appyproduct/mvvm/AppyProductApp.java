package com.appyhome.appyproduct.mvvm;

import android.app.Activity;
import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyMigration;
import com.appyhome.appyproduct.mvvm.di.component.DaggerAppComponent;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.crashlytics.android.Crashlytics;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppyProductApp extends Application implements HasActivityInjector {

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initiateDatabase();
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    private void initiateDatabase() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name(AppConstants.DB_NAME)
                .schemaVersion(AppConstants.DB_VERSION)
                .build();
        try {
            Realm.migrateRealm(config, new AppyMigration());
        } catch (FileNotFoundException ignored) {}

        Realm.setDefaultConfiguration(config);
    }

}
