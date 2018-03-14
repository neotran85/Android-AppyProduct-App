package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FilterFragmentProvider {

    @ContributesAndroidInjector(modules = FilterFragmentModule.class)
    abstract FilterFragment provideFilterFragmentFactory();

}
