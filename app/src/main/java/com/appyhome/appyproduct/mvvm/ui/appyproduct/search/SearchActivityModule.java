package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    @Provides
    SearchViewModel provideSearchViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new SearchViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_product_search;
    }
}
