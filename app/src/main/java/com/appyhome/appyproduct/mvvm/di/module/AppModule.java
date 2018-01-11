package com.appyhome.appyproduct.mvvm.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.appyhome.appyproduct.mvvm.BuildConfig;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.AppDataManager;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.AppDatabase;
import com.appyhome.appyproduct.mvvm.data.local.db.AppDbHelper;
import com.appyhome.appyproduct.mvvm.data.local.db.DbHelper;
import com.appyhome.appyproduct.mvvm.data.local.prefs.AppPreferencesHelper;
import com.appyhome.appyproduct.mvvm.data.local.prefs.PreferencesHelper;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHeader;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHelper;
import com.appyhome.appyproduct.mvvm.data.remote.AppApiHelper;
import com.appyhome.appyproduct.mvvm.di.ApiInfo;
import com.appyhome.appyproduct.mvvm.di.DatabaseInfo;
import com.appyhome.appyproduct.mvvm.di.PreferenceInfo;
import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.utils.rx.AppSchedulerProvider;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUsername(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/TitilliumWeb-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
