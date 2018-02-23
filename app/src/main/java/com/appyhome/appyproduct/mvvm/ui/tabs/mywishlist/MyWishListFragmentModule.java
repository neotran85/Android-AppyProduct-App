package com.appyhome.appyproduct.mvvm.ui.tabs.mywishlist;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MyWishListFragmentModule {

    @Provides
    MyWishListViewModel provideMyWishListViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider) {
        return new MyWishListViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory myWishListViewModelProvider(MyWishListViewModel myWishListViewModel) {
        return new ViewModelProviderFactory<>(myWishListViewModel);
    }

}
