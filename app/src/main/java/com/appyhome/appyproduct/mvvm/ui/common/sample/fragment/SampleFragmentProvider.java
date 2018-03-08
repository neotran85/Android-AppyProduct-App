package com.appyhome.appyproduct.mvvm.ui.common.sample.fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SampleFragmentProvider {

    @ContributesAndroidInjector(modules = SampleFragmentModule.class)
    abstract SampleFragment provideProductTopicFragmentFactory();

}
