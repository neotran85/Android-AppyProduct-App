package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProductTopicFragmentProvider {

    @ContributesAndroidInjector(modules = ProductTopicFragmentModule.class)
    abstract ProductTopicFragment provideProductTopicFragmentFactory();

}
