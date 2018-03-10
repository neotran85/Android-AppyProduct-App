package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListRequest;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

public class ProductListViewModel extends BaseViewModel<ProductListNavigator> {

    private int mIdSub = ProductListActivity.ID_DEFAULT_SUB;

    public ProductListViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchProductsByIdCategory(int idSub) {
        mIdSub = idSub;
        if (isOnline()) {
            getCompositeDisposable().add(getDataManager()
                    .fetchProductsByIdCategory(new ProductListRequest(mIdSub, 0))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response.message != null && response.message.length > 0) {
                            addProductsToDatabase(response.message);
                        }
                    }, throwable -> {
                        getNavigator().showEmptyProducts();
                        throwable.printStackTrace();
                        Crashlytics.logException(throwable);
                    }));
        } else {
            getProductsBySubCategory(mIdSub, new Product[0]);
        }
    }

    private void addProductsToDatabase(Product[] list) {
        getCompositeDisposable().add(getDataManager().addProducts(list)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE ADDED
                    if (success)
                        getProductsBySubCategory(mIdSub, list);
                    else {
                        // IF ADDED FAILED
                        getNavigator().showProducts(list);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getNavigator().showProducts(list);
                    Crashlytics.logException(throwable);
                }));
    }

    public void getAllFavorites() {
        getCompositeDisposable().add(getDataManager().getAllProductFavorites(getUserId())
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
                    getNavigator().updateFavorites(arrayId);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void getProductsBySubCategory(int idSub, Product[] cachedList) {
        getCompositeDisposable().add(getDataManager().getProductsBySubCategory(idSub)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    // DONE GET
                    if (products != null && products.size() > 0) {
                        getNavigator().showProducts(products);
                    } else {
                        // SHOW CACHED LIST
                        getNavigator().showProducts(cachedList);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getNavigator().showProducts(cachedList);
                    Crashlytics.logException(throwable);
                }));
    }

}
