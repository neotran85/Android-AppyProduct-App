package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
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
    private final int SEARCH_PAGE_LIMIT = 100;
    private final int LISTING_PAGE_LIMIT = 1000;
    public ObservableField<Boolean> isSortShowed = new ObservableField<>(false);
    public ObservableField<String> currentSortLabel = new ObservableField<>("Sort");
    public ObservableField<String> filterNumber = new ObservableField<>("");
    public ObservableField<Boolean> isFilter = new ObservableField<>(false);
    public ObservableField<Boolean> isCategoriesSelectionShowed = new ObservableField<>(false);
    public ObservableField<Boolean> isAbleToSelectCategories = new ObservableField<>(false);

    private int mPageNumber = 1;

    private boolean mIsAbleToLoadMore = false;

    private boolean mIsFirstLoaded = true;

    private Intent mIntent;

    private int requestCounter = 0;

    public ProductListViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        updateSortCurrentLabel();
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    private Intent getIntent() {
        return mIntent;
    }

    public String getKeywordString() {
        if (getIntent().hasExtra("keyword"))
            return getIntent().getStringExtra("keyword");
        return "";
    }


    private String getCategoryIds() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.containsKey("categoryIds")) {
            return bundle.getString("categoryIds");
        } else {
            return bundle.getString("id_subs");
        }
    }

    public void resetPageNumber() {
        mIsFirstLoaded = true;
        mPageNumber = 1;
    }

    public void increasePageNumber() {
        mPageNumber++;
    }

    public int getPageNumber() {
        return mPageNumber;
    }

    public boolean isFirstLoaded() {
        return mIsFirstLoaded;
    }

    public void setIsFirstLoaded(boolean value) {
        mIsFirstLoaded = value;
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

    public void getAllProductsWithFilter() {
        getCompositeDisposable().add(getDataManager().getAllProductsFilter(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::showProductList, Crashlytics::logException));
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
        if (isFirstLoaded() && !isAbleToLoadMore()) {
            updateCurrentFilter();
            getNavigator().showEmptyProducts();
        }
    }

    private String getKeyCached() {
        String key = getCategoryIds() + ":" + getKeywordString() + ":" + getCurrentSortType() + ":" + getPageNumber();
        return key;
    }


    private int getItemsLimitation() {
        return getKeywordString().length() > 0 ? SEARCH_PAGE_LIMIT : LISTING_PAGE_LIMIT;
    }

    private void updateIsAbleToLoadMore(ProductListResponse response, boolean isFirstRequest) {
        if (getKeywordString().length() > 0) {
            mIsAbleToLoadMore = (response.message.size() == SEARCH_PAGE_LIMIT);
        } else {
            if (isFirstRequest) {
                mIsAbleToLoadMore = false;
            } else mIsAbleToLoadMore = (response.message.size() == LISTING_PAGE_LIMIT);
        }
    }

    private void processResultOfFetchProductsByObject(JSONObject jsonResult) {
        boolean isFirstRequest = requestCounter == 0;
        requestCounter++;
        getDataManager().setCachedResponse("fetchProductsByObject", getKeyCached(), jsonResult.toString());
        try {
            if (jsonResult.get("code").equals(ApiCode.OK_200)) {
                Gson gson = new Gson();
                ProductListResponse response = gson.fromJson(jsonResult.toString(), ProductListResponse.class);
                if (response.message != null && response.message.size() > 0) {
                    addProductsToDatabase(response.message);
                    updateIsAbleToLoadMore(response, isFirstRequest);
                    return;
                }
            }
            mIsAbleToLoadMore = false;
            // NOT OK
            showEmptyProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableOnSubscribe<ProductListResponse> fetchProductsByObject() {
        return (ObservableEmitter<ProductListResponse> subscriber) -> {
            newFetch();
        };
    }

    private void newFetch() {
        getDataManager().fetchProducts(new ProductListRequest(getCategoryIds(), getKeywordString(), getPageNumber(), getCurrentSortType()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonResult -> {
                    // 200 OK
                    processResultOfFetchProductsByObject(jsonResult);
                }, throwable -> {
                    showEmptyProducts();
                    Crashlytics.logException(throwable);
                });
    }

    public String getCurrentSortType() {
        try {
            String json = getDataManager().getProductsSortCurrent(getUserId());
            return new JSONObject(json).getString("value");
        } catch (JSONException e) {
            Crashlytics.logException(e);
        }
        return "";
    }

    public void clearCachedResponse() {
        getDataManager().clearCachedResponse();
    }

    public void fetchProductsByCommand() {
        mIsAbleToLoadMore = false;
        String key = getCategoryIds() + ":" + getKeywordString() + ":" + getCurrentSortType() + ":" + getPageNumber();
        getCompositeDisposable().add(getDataManager().getCachedResponse("fetchProductsByObject", key)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(cached -> {
                    // 200 OK
                    // IF CACHED EXISTED
                    if (cached != null && cached.length() > 0) {
                        try {
                            JSONObject jsonResult = new JSONObject(cached);
                            processResultOfFetchProductsByObject(jsonResult);
                            return;
                        } catch (JSONException e) {
                            Crashlytics.logException(e);
                        }
                    }
                    // NO CACHED
                    if (isOnline()) {
                        ObservableOnSubscribe<ProductListResponse> resultProcessing = fetchProductsByObject();
                        if (resultProcessing != null) {
                            Disposable disposable = Observable.create(resultProcessing).retryWhen(throwableObservable -> throwableObservable.zipWith(Observable.range(1, RETRY_MAX_COUNT), (n, i) -> i)
                                    .flatMap(retryCount -> Observable.timer(RETRY_TIME, TimeUnit.SECONDS))).subscribe();
                            getCompositeDisposable().add(disposable);
                        }
                    } else {
                        getAllProductsWithFilter();
                    }
                }, throwable -> {
                    showEmptyProducts();
                    Crashlytics.logException(throwable);
                    newFetch();
                }));
    }

    private void addProductsToDatabase(RealmList<Product> list) {
        getCompositeDisposable().add(getDataManager().addProducts(getUserId(), list)
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
        getNavigator().showProducts(products);
        updateCurrentFilter();
    }

    private void showCachedList(RealmList<Product> list) {
        getNavigator().showProducts(list);
        updateCurrentFilter();
    }

    /******************************  FILTER METHODS ******************************/

    public void resetFilter() {
        getCompositeDisposable().add(getDataManager().saveProductFilter(getUserId(), "",
                "", -1, "", "")
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    ProductListNavigator navigator = getNavigator();
                    if (navigator != null)
                        navigator.fetchProductsNew();
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

    public boolean isAbleToLoadMore() {
        return mIsAbleToLoadMore;
    }

}
