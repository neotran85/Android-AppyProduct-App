package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RequestFragmentProvider {

    @ContributesAndroidInjector(modules = RequestFragmentModule.class)
    abstract RequestFragment provideRequestFragmentFactory();

}
