package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListCachedResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortOption;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ProductListViewModel extends BaseViewModel<ProductListNavigator> {

    private final int RETRY_MAX_COUNT = 5;

    private final int RETRY_TIME = 5;

    public ObservableField<Boolean> isSortShowed = new ObservableField<>(false);

    public ObservableField<String> currentSortLabel = new ObservableField<>("Sort");

    public ObservableField<String> filterNumber = new ObservableField<>("");

    public ObservableField<Boolean> isFilter = new ObservableField<>(false);

    private static final int PRODUCTS_PER_PAGE = 100;

    private ProductListCachedResponse cachedResponse;

    private boolean showCachedList = true;

    private int mPageNumber = 1;

    private boolean mIsAbleToLoadMore = false;

    public ProductListViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        updateSortCurrentLabel();
    }

    public void resetPageNumber() {
        mPageNumber = 1;
    }

    public void increasePageNumber() {
        mPageNumber++;
    }

    public int getPageNumber() {
        return mPageNumber;
    }

    public boolean isFirstLoaded() {
        return mPageNumber == 1;
    }

    public int getMaxProducts() {
        return mPageNumber * PRODUCTS_PER_PAGE;
    }

    /******************************  SORT CURRENT METHODS *************** ***************/

    public void updateSortCurrentLabel() {
        currentSortLabel.set(getCurrentSortOption().getName());
    }

    public void saveSortCurrent(SortOption option) {
        getDataManager().setProductsSortCurrent(getUserId(), option.toJson());
    }

    private SortOption getCurrentSortOption() {
        String json = getDataManager().getProductsSortCurrent(getUserId());
        if (json.length() > 0) {
            SortOption.UNKNOWN.fromJson(json);
        }
        return SortOption.UNKNOWN;
    }


    /******************************  PRODUCTS METHODS *************** ***************/

    private void getAllProductsWithFilter() {
        getCompositeDisposable().add(getDataManager().getAllProductsFilter(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    showProductList(products);
                    // CACHED ALL PRODUCTS LOADED
                    addProductsCachedToDatabase();
                }, Crashlytics::logException));
    }

    private void addProductsCachedToDatabase() {
        if (cachedResponse != null && cachedResponse.message != null && cachedResponse.message.size() > 0) {
            mIsAbleToLoadMore = cachedResponse.message.size() == PRODUCTS_PER_PAGE;
            getCompositeDisposable().add(getDataManager().addProductsCached(cachedResponse.message)
                    .take(1)
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(success -> {
                        cachedResponse = null;
                    }, Crashlytics::logException));
        }
    }
    public void emptyProductsLoaded() {
        getCompositeDisposable().add(getDataManager().clearProductsLoaded()
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DO NOTHING
                }, Crashlytics::logException));
    }
    public void clearProductsLoaded() {
        getCompositeDisposable().add(getDataManager().clearProductsLoaded()
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    getNavigator().clearProductsLoaded_Done();
                }, Crashlytics::logException));
    }

    private void showEmptyProducts() {
        if (isFirstLoaded()) {
            getNavigator().showEmptyProducts();
        }
    }

    private ObservableOnSubscribe<ProductListResponse> fetchProductsByObject(Object data) {
        return (ObservableEmitter<ProductListResponse> subscriber) -> {
            getDataManager().fetchProducts(new ProductListRequest(data, mPageNumber, getCurrentSortType()))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(jsonResult -> {
                        // 200 OK
                        if (jsonResult != null && jsonResult.get("code").equals(ApiCode.OK_200)) {
                            Gson gson = new Gson();
                            ProductListResponse response = gson.fromJson(jsonResult.toString(), ProductListResponse.class);
                            cachedResponse = gson.fromJson(jsonResult.toString(), ProductListCachedResponse.class);
                            if (response.message != null && response.message.size() > 0) {
                                addProductsToDatabase(response.message);
                                return;
                            }
                        }
                        // NOT OK
                        showEmptyProducts();
                    }, throwable -> {
                        showEmptyProducts();
                        throwable.printStackTrace();
                        Crashlytics.logException(throwable);
                    });
        };
    }

    private String getCurrentSortType() {
        try {
            String json = getDataManager().getProductsSortCurrent(getUserId());
            return new JSONObject(json).getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void fetchProductsByCommand(Object data) {
        if (isOnline()) {
            ObservableOnSubscribe<ProductListResponse> resultProcessing = null;
            resultProcessing = fetchProductsByObject(data);
            if (resultProcessing != null) {
                Disposable disposable = Observable.create(resultProcessing).retryWhen(throwableObservable -> throwableObservable.zipWith(Observable.range(1, RETRY_MAX_COUNT), (n, i) -> i)
                        .flatMap(retryCount -> Observable.timer(RETRY_TIME, TimeUnit.SECONDS))).subscribe();
                getCompositeDisposable().add(disposable);
            }
        } else {
            getAllProductsWithFilter();
        }
    }

    private void addProductsToDatabase(RealmList<Product> list) {
        getCompositeDisposable().add(getDataManager().addProducts(list)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE ADDED
                    if (success) {
                        getAllProductsWithFilter();
                    } else {
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
        updateCurrentFilter();
        if (products != null && products.size() > 0) {
            getNavigator().showProducts(products);
        } else showEmptyProducts();
    }

    private void showCachedList(RealmList<Product> list) {
        updateCurrentFilter();
        if (showCachedList && list != null && list.size() > 0)
            getNavigator().showProducts(list);
        else showEmptyProducts();
    }

    /******************************  FAVORITE METHODS *************** ***************/

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
                    getNavigator().getAllFavorites_Done(arrayId);
                }, Crashlytics::logException));
    }

    /******************************  FILTER METHODS ******************************/

    public void resetFilter() {
        getCompositeDisposable().add(getDataManager().saveProductFilter(getUserId(), "",
                "", -1, "", "")
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    getNavigator().restartFetching();
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
        getNavigator().updatedFilterCount();
    }

    private void updateCurrentFilter() {
        getCompositeDisposable().add(getDataManager().getCurrentFilter(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    if (filter != null)
                        if (getUserId().equals(filter.user_id))
                            updateCountFilter(filter);
                }, Crashlytics::logException));
    }

    public boolean isIsAbleToLoadMore() {
        return mIsAbleToLoadMore;
    }

    public void setIsAbleToLoadMore(boolean isAbleToLoadMore) {
        this.mIsAbleToLoadMore = isAbleToLoadMore;
    }
}
