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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ProductListViewModel extends BaseViewModel<ProductListNavigator> {

    public static final int PRODUCTS_PER_PAGE = 100;
    private final int RETRY_MAX_COUNT = 5;
    private final int RETRY_TIME = 5;
    public ObservableField<Boolean> isSortShowed = new ObservableField<>(false);
    public ObservableField<String> currentSortLabel = new ObservableField<>("Sort");
    public ObservableField<String> filterNumber = new ObservableField<>("");
    public ObservableField<Boolean> isFilter = new ObservableField<>(false);
    public ObservableField<Boolean> isCategoriesSelectionShowed = new ObservableField<>(false);
    public ObservableField<Boolean> isAbleToSelectCategories = new ObservableField<>(false);
    private ProductListCachedResponse cachedResponse;

    private int mPageNumber = 1;

    private boolean mIsAbleToLoadMore = false;

    private boolean mIsFirstLoaded = true;

    public ProductListViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        updateSortCurrentLabel();
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

    private void getAllProductsWithFilter() {
        getCompositeDisposable().add(getDataManager().getAllProductsFilter(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(products -> {
                    addProductsCachedToDatabase();
                    showProductList(products);
                }, Crashlytics::logException));
    }

    private void addProductsCachedToDatabase() {
        if (cachedResponse != null && cachedResponse.message != null && cachedResponse.message.size() > 0) {
            setIsAbleToLoadMore(cachedResponse.message.size() == PRODUCTS_PER_PAGE);
            getCompositeDisposable().add(getDataManager().addProductsCached(cachedResponse.message)
                    .take(1)
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(success -> {
                        cachedResponse.message.clear();
                        cachedResponse = null;
                    }, Crashlytics::logException));
        }
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
        if (isFirstLoaded() && !isIsAbleToLoadMore()) {
            updateCurrentFilter();
            getNavigator().showEmptyProducts();
        }
    }

    private ObservableOnSubscribe<ProductListResponse> fetchProductsByObject(String categoryIds, String keyword) {
        return (ObservableEmitter<ProductListResponse> subscriber) -> {
            getDataManager().fetchProducts(new ProductListRequest(categoryIds, keyword, getPageNumber(), getCurrentSortType()))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(jsonResult -> {
                        // 200 OK
                        if (jsonResult != null && jsonResult.get("code").equals(ApiCode.OK_200)) {
                            Gson gson = new Gson();
                            ProductListResponse response = gson.fromJson(jsonResult.toString(), ProductListResponse.class);
                            cachedResponse = gson.fromJson(jsonResult.toString(), ProductListCachedResponse.class);
                            if (response.message != null && response.message.size() > 0) {
                                sortResults(response.message);
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

    public void fetchProductsByCommand(String categoryIds, String keywords) {
        if (isOnline()) {
            ObservableOnSubscribe<ProductListResponse> resultProcessing = null;
            resultProcessing = fetchProductsByObject(categoryIds, keywords);
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
        getNavigator().showProducts(products);
        updateCurrentFilter();
    }

    private void showCachedList(RealmList<Product> list) {
        getNavigator().showProducts(list);
        updateCurrentFilter();
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

    private void sortResults(RealmList<Product> result) {
        SortOption sort = getCurrentSortOption();
        if (sort.equals(SortOption.PRICE_HIGHEST)) {
            Collections.sort(result, (o1, o2) -> Math.round((o2.lowest_price - o1.lowest_price) * 100));
            return;
        }
        if (sort.equals(SortOption.PRICE_LOWEST)) {
            Collections.sort(result, (o1, o2) -> Math.round((o1.lowest_price - o2.lowest_price) * 100));
            return;
        }
        if (sort.equals(SortOption.RATING)) {
            Collections.sort(result, (o1, o2) -> Math.round((o1.rate - o2.rate) * 10));
            return;
        }
        if (sort.equals(SortOption.LATEST)) {
            Collections.sort(result, (o1, o2) -> {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date o1Date = sdf.parse(o1.updated_at);
                    Date o2Date = sdf.parse(o2.updated_at);
                    return o2Date.compareTo(o1Date);
                } catch (Exception e) {
                    Crashlytics.logException(e);
                }
                return 0;
            });
            return;
        }
        if (sort.equals(SortOption.POPULAR)) {
            Collections.sort(result, (o1, o2) -> o1.like - o2.like);
            return;
        }
    }

    private class PriceComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return Math.round((o1.lowest_price - o2.lowest_price) * 100);
        }
    }

}
