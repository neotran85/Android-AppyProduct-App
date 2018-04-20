package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;

import android.databinding.ObservableField;
import android.util.Log;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class FavoriteViewModel extends BaseViewModel<FavoriteNavigator> {
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> totalCount = new ObservableField<>("");
    public ObservableField<Boolean> isFavoriteEmpty = new ObservableField<>(true);

    private FetchUserInfoViewModel mFetchUserInfoViewModel;

    public FavoriteViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mFetchUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);
    }

    public void setNavigator(FavoriteNavigator navigator) {
        super.setNavigator(navigator);
        mFetchUserInfoViewModel.setNavigator(navigator);
    }

    public void fetchUserData() {
        if (isOnline() && isUserLoggedIn())
            mFetchUserInfoViewModel.fetchUserData();
        else {
            getNavigator().onFetchUserInfo_Done();
        }
    }

    public void updateFavoriteCount(int count) {
        if (count > 0) {
            totalCount.set("(" + count + ")");
        } else totalCount.set("");
    }

    public void getAllFavorites() {
        getCompositeDisposable().add(getDataManager().getAllProductFavorites(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(favorites -> {
                    getNavigator().showProducts(favorites);
                    isFavoriteEmpty.set(favorites == null || favorites.size() == 0);
                    updateFavoriteCount(favorites != null ? favorites.size() : 0);
                }, Crashlytics::logException));
    }

    public void emptyUserWishList() {
        // EMPTY FROM LOCAL DB
        isFavoriteEmpty.set(true);
        updateFavoriteCount(0);
        getCompositeDisposable().add(getDataManager().emptyFavorites(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    getNavigator().showProducts(null);
                }, Crashlytics::logException));
        // EMPTY FROM SERVER
        getCompositeDisposable().add(getDataManager().emptyUserWishList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("emptyUserWishList", "EMPTY USER WISH LIST");
                    }
                }, Crashlytics::logException));
    }
}
