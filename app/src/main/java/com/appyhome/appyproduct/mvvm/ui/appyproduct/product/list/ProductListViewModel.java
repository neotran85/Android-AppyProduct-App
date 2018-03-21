package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
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
import io.realm.RealmResults;

public class ProductListViewModel extends BaseViewModel<ProductListNavigator> {

    private final int RETRY_MAX_COUNT = 5;
    private final int RETRY_TIME = 5;
    public ObservableField<Boolean> isSortShowed = new ObservableField<>(false);
    public ObservableField<String> currentSortOption = new ObservableField<>("Sort By Popular");
    public ObservableField<String> filterNumber = new ObservableField<>("");
    public ObservableField<Boolean> isFilter = new ObservableField<>(false);
    private int mIdSub = ProductListActivity.ID_DEFAULT_SUB;
    private String mSortType = "";

    public ProductListViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchProductsWithFilter(int idSub, String sortType) {
        mIdSub = idSub;
        mSortType = sortType;
        getCompositeDisposable().add(getDataManager().getAllProductsFilter(getUserId(), idSub)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    showProductList(products);
                }, Crashlytics::logException));
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
                .fetchProductsByIdCategory(new ProductListRequest(mIdSub, 0, mSortType))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui());
    }

    private void addProductsToDatabase(Product[] list) {
        getCompositeDisposable().add(getDataManager().addProducts(list)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE ADDED
                    if (success)
                        fetchProductsWithFilter(mIdSub, "");
                    else {
                        // IF ADDED FAILED
                        showCachedList(list);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    showCachedList(list);
                    Crashlytics.logException(throwable);
                }));
    }

    private void showProductList(RealmResults<Product> products) {
        if (products != null && products.size() > 0) {
            getNavigator().showProducts(products);
        } else getNavigator().showEmptyProducts();
    }

    private void showCachedList(Product[] list) {
        if (list != null && list.length > 0)
            getNavigator().showProducts(list);
        else getNavigator().showEmptyProducts();
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
                }, Crashlytics::logException));
    }

    private void getProductsBySubCategory(int idSub, Product[] cachedList) {
        getCompositeDisposable().add(getDataManager().getProductsBySubCategory(idSub)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    // DONE GET
                    showProductList(products);
                }, throwable -> {
                    throwable.printStackTrace();
                    showCachedList(cachedList);
                    Crashlytics.logException(throwable);
                }));
    }


    public void resetFilter() {
        getCompositeDisposable().add(getDataManager().saveProductFilter(getUserId(), "",
                "", -1, "", "")
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    fetchProductsWithFilter(mIdSub, mSortType);
                }, Crashlytics::logException));
    }

    private void updateCountFilter(ProductFilter filter) {
        int count = 0;
        if (filter.shipping_from.length() > 0) {
            count++;
        }
        if (filter.discount.length() > 0) {
            count++;
        }
        if (filter.rating > 0) {
            count++;
        }
        if (filter.price_max > 0) {
            count++;
        }
        if (filter.price_min > 0) {
            count++;
        }
        filterNumber.set(count + "");
        isFilter.set(count > 0);
    }

    public void getCurrentFilter() {
        getCompositeDisposable().add(getDataManager().getCurrentFilter(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    updateCountFilter(filter);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
