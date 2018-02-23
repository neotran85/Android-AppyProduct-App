package com.appyhome.appyproduct.mvvm.ui.account.myprofile;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyProfileFragmentProvider {

    @ContributesAndroidInjector(modules = MyProfileFragmentModule.class)
    abstract MyProfileFragment provideMyProfileFragmentFactory();

}
