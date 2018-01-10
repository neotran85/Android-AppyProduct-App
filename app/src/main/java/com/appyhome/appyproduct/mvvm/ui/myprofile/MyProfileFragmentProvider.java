package com.appyhome.appyproduct.mvvm.ui.myprofile;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyProfileFragmentProvider {

    @ContributesAndroidInjector(modules = MyProfileFragmentModule.class)
    abstract MyProfileFragment provideMyProfileFragmentFactory();

}
