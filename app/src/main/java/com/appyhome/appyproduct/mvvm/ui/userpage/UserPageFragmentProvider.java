package com.appyhome.appyproduct.mvvm.ui.userpage;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserPageFragmentProvider {

    @ContributesAndroidInjector(modules = UserPageFragmentModule.class)
    abstract UserPageFragment provideUserPageFragmentFactory();

}
