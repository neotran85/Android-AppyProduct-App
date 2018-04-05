package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProductVariantFragmentProvider {

    @ContributesAndroidInjector(modules = ProductVariantFragmentModule.class)
    abstract ProductVariantFragment provideProductVariantFragmentFactory();

}
