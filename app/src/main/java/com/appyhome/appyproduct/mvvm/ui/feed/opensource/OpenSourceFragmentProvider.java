package com.appyhome.appyproduct.mvvm.ui.feed.opensource;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OpenSourceFragmentProvider {

    @ContributesAndroidInjector(modules = OpenSourceFragmentModule.class)
    abstract OpenSourceFragment provideOpenSourceFragmentFactory();

}
