package com.appyhome.appyproduct.mvvm.ui.tabs.tracking;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TrackingFragmentProvider {

    @ContributesAndroidInjector(modules = TrackingFragmentModule.class)
    abstract TrackingFragment provideTrackingFragmentFactory();

}
