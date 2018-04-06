package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EditVariantFragmentProvider {

    @ContributesAndroidInjector(modules = EditVariantFragmentModule.class)
    abstract EditVariantFragment provideEditVariantFragmentFactory();

}
