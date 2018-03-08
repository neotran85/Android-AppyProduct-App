package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

public class FavoriteViewModel extends BaseViewModel<FavoriteNavigator> {

    public FavoriteViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllFavorites() {
        getCompositeDisposable().add(getDataManager().getAllProductFavorites("1234")
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(favorites -> {
                    // DONE GET
                    ArrayList<Integer> arrayId = new ArrayList<>();
                    if (favorites != null && favorites.size() > 0) {
                        for (ProductFavorite item : favorites) {
                            arrayId.add(item.product_id);
                        }
                    }
                    getAllProductFavorites(arrayId);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
    private void getAllProductFavorites(ArrayList<Integer> ids) {
        getCompositeDisposable().add(getDataManager().getAllProductFavorites(ids)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    // DONE GET
                    getNavigator().showProducts(products);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
