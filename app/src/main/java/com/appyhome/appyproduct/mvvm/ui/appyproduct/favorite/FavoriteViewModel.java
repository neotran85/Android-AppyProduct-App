package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;

import android.databinding.ObservableField;
import android.util.Log;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.realm.RealmResults;

public class FavoriteViewModel extends BaseViewModel<FavoriteNavigator> {
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> totalCount = new ObservableField<>("");
    public ObservableField<Boolean> isFavoriteEmpty = new ObservableField<>(true);

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
                    getAllProductsFavorited(arrayId, favorites);
                }, Crashlytics::logException));
    }

    private void getAllProductsFavorited(ArrayList<Integer> ids, RealmResults<ProductFavorite> favorites) {
        getCompositeDisposable().add(getDataManager().getAllProductsFavorited(getUserId(), ids)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    Product[] items = new Product[0];
                    if (products != null) {
                        items = new Product[products.size()];
                        int index = 0;
                        for (Product item : products) {
                            items[index] = item;
                            index++;
                        }
                    }
                    getNavigator().showProducts(items);
                    isFavoriteEmpty.set(items.length <= 0);
                    updateFavoriteCount(items.length);
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
