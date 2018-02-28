package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
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
        getCompositeDisposable().add(getDataManager()
                .fetchProductsByIdCategory(new ProductListRequest(idSub, 0))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response.message != null && response.message.length > 0) {
                        addProductsToDatabase(response.message);
                        getNavigator().showAlert(response.message.length + "");
                        //getNavigator().showProducts(response.message);
                    }
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleErrorService(throwable);
                    Crashlytics.logException(throwable);
                }));
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
                    Crashlytics.logException(throwable);
                }));
    }
}
