package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoryActivityModule {

    @Provides
    CategoryViewModel provideCategoryViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new CategoryViewModel(dataManager, schedulerProvider);
    }

    @Provides
    CategoryAdapter provideCategoryAdapter() {
        return new CategoryAdapter();
    }
}
