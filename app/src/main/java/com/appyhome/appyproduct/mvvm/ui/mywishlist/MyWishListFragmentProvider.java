package com.appyhome.appyproduct.mvvm.ui.mywishlist;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyWishListFragmentProvider {

    @ContributesAndroidInjector(modules = MyWishListFragmentModule.class)
    abstract MyWishListFragment provideMyWishListFragmentFactory();

}
