package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFragmentModule {

    @Provides
    HomeViewModel provideHomeViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider) {
        return new HomeViewModel(dataManager, schedulerProvider);
    }

    @Provides
    BannersAdapter provideBannersAdapter() {
        return new BannersAdapter();
    }

    @Provides
    ViewModelProvider.Factory homeViewModelProvider(HomeViewModel homeViewModel) {
        return new ViewModelProviderFactory<>(homeViewModel);
    }


    @Provides
    int provideLayoutId() {
        return R.layout.fragment_home;
    }

}
