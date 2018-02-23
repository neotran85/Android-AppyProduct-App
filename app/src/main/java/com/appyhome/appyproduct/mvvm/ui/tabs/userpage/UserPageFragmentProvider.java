package com.appyhome.appyproduct.mvvm.ui.tabs.userpage;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserPageFragmentProvider {

    @ContributesAndroidInjector(modules = UserPageFragmentModule.class)
    abstract UserPageFragment provideUserPageFragmentFactory();

}
