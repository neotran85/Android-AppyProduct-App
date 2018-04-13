package com.appyhome.appyproduct.mvvm.ui.splash;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductCartResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductFavoriteResponse;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.ArrayList;

public class SplashViewModel extends BaseViewModel<SplashActivity> {

    private FetchUserInfoViewModel mFetchUserInfoViewModel;

    public SplashViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mFetchUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);
    }

    public void setNavigator(SplashActivity activity) {
        super.setNavigator(activity);
        mFetchUserInfoViewModel.setNavigator(activity);
    }

    public void loadAppData() {
        loadServicesCategories();
        loadServices();
        // FETCH PRODUCTS DATA
        loadProductSubs();
    }

    private void loadServices() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseServices()
                .take(1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(services -> {
                    if (services != null) {
                        getDataManager().getServiceOrderUserInput().setArrayAppyService(services);
                    }
                }, Crashlytics::logException));
    }

    private void loadServicesCategories() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseCategories()
                .take(1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> {
                    if (categories != null) {
                        getDataManager().getServiceOrderUserInput().setArrayAppyServiceCategory(categories);
                    }
                }, Crashlytics::logException));
    }

    private void loadProductCategory() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseProductCategories()
                .take(1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::addProductCategories, Crashlytics::logException));
    }

    private void addProductCategories(ArrayList<ProductCategory> categories) {
        getCompositeDisposable().add(getDataManager().addProductCategories(categories)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE THEN LOAD PRODUCT TOPICS
                    loadProductTopics();
                }, Crashlytics::logException));
    }

    private void addProductSubs(ArrayList<ProductSub> subs) {
        getCompositeDisposable().add(getDataManager().addProductSubs(subs)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE THEN LOAD PRODUCT CATEGORIES
                    loadProductCategory();
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void fetchUserData() {
        // FETCH USER DATA
        if (isUserLoggedIn())
            mFetchUserInfoViewModel.fetchUserData();
        else {
            getNavigator().onFetchUserInfo_Done();
        }
    }

    private void addProductTopics(ArrayList<ProductTopic> topics) {
        getCompositeDisposable().add(getDataManager().addProductTopics(topics)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // FINISHED ALL LOAD PRODUCT DATA, THEN FETCH USER DATA
                    fetchUserData();
                }, Crashlytics::logException));
    }

    private void loadProductTopics() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseProductTopics()
                .take(1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::addProductTopics, Crashlytics::logException));
    }

    private void loadProductSubs() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseProductSubs()
                .take(1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::addProductSubs, Crashlytics::logException));
    }
}
