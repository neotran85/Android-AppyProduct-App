package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

public class FavoriteViewModel extends BaseViewModel<FavoriteNavigator> {
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> totalCount = new ObservableField<>("");

    public FavoriteViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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
                    // DONE GET
                    ArrayList<Integer> arrayId = new ArrayList<>();
                    for (ProductFavorite item : favorites) {
                        arrayId.add(item.product_id);
                    }
                    getAllProductsFavorited(arrayId);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void getAllProductsFavorited(ArrayList<Integer> ids) {
        getCompositeDisposable().add(getDataManager().getAllProductsFavorited(ids)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    // DONE GET
                    getNavigator().showProducts(products);
                    updateFavoriteCount(products.size());
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
