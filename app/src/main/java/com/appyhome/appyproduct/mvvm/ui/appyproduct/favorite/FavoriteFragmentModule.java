package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter.FavoriteAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoriteFragmentModule {

    @Provides
    FavoriteViewModel provideFavoriteViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new FavoriteViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory favoriteViewModelProvider(FavoriteViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    FavoriteAdapter provideFavoriteAdapter() {
        return new FavoriteAdapter();
    }

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_favorite;
    }
}
