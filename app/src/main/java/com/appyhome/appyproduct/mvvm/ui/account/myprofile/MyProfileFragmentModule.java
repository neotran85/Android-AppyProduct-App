package com.appyhome.appyproduct.mvvm.ui.account.myprofile;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MyProfileFragmentModule {

    @Provides
    MyProfileViewModel provideMyProfileViewModel(DataManager dataManager,
                                                 SchedulerProvider schedulerProvider) {
        return new MyProfileViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory myProfileViewModelProvider(MyProfileViewModel myProfileViewModel) {
        return new ViewModelProviderFactory<>(myProfileViewModel);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_my_profile;
    }
}
