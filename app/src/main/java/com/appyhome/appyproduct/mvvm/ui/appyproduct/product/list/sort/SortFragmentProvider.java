package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SortFragmentProvider {

    @ContributesAndroidInjector(modules = SortFragmentModule.class)
    abstract SortFragment provideSortFragmentFactory();

}
