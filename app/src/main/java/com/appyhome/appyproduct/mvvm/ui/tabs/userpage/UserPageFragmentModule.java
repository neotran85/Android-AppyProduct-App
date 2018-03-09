package com.appyhome.appyproduct.mvvm.ui.tabs.userpage;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class UserPageFragmentModule {

    @Provides
    UserPageViewModel provideUserPageViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new UserPageViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory userPageViewModelProvider(UserPageViewModel userPageViewModel) {
        return new ViewModelProviderFactory<>(userPageViewModel);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_user_page;
    }
}
