package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class ProductListViewModel extends BaseViewModel<ProductListNavigator> {

    private int mIdSub = ProductListActivity.ID_DEFAULT_SUB;
    private String mSortType = "";
    private final int RETRY_MAX_COUNT = 5;
    private final int RETRY_TIME = 5;
    public ObservableField<Boolean> isSortShowed = new ObservableField<>(false);
    public ObservableField<String> currentSortOption = new ObservableField<>("Sort By Popular");

    public ProductListViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchProductsWithFilter(int idSub, String sortType) {
        getCompositeDisposable().add(getDataManager().getAllProductsFilter(getUserId(), idSub)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    if (products != null && products.size() > 0) {
                        getNavigator().showProducts(products);
                    } else getNavigator().showEmptyProducts();
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public void fetchProductsByIdCategory(int idSub, String sortType) {
        mIdSub = idSub;
        mSortType = sortType;
        if (isOnline()) {
            Disposable disposable = Observable.create((ObservableEmitter<ProductListResponse> subscriber) -> {
                fetchProducts().subscribe(response -> {
                    if (response.message != null && response.message.length > 0) {
                        addProductsToDatabase(response.message);
                    }
                }, throwable -> {
                    getNavigator().showEmptyProducts();
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                });
            }).retryWhen(throwableObservable -> throwableObservable.zipWith(Observable.range(1, RETRY_MAX_COUNT), (n, i) -> i)
                    .flatMap(retryCount -> Observable.timer(RETRY_TIME, TimeUnit.SECONDS))).subscribe();
            getCompositeDisposable().add(disposable);
        } else {
            getProductsBySubCategory(mIdSub, new Product[0]);
        }
    }

    private Single<ProductListResponse> fetchProducts() {
        return getDataManager()
                .fetchProductsByIdCategory(new ProductListRequest(mIdSub, 0, mSortType)).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui());
    }

    private void addProductsToDatabase(Product[] list) {
        getCompositeDisposable().add(getDataManager().addProducts(list)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE ADDED
                    if (success)
                        fetchProductsWithFilter(mIdSub, "");
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


    public void resetFilter() {
        getCompositeDisposable().add(getDataManager().saveProductFilter(getUserId(), "",
                "", -1, "", "")
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    // DO NOTHING
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
