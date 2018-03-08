package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavoriteFragmentProvider {

    @ContributesAndroidInjector(modules = FavoriteFragmentModule.class)
    abstract FavoriteFragment provideFavoriteFragmentFactory();

}
