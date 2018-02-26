package com.appyhome.appyproduct.mvvm.ui.splash;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyServiceCategory;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class SplashViewModel extends BaseViewModel<SplashActivity> {

    public SplashViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setUp() {
        loadServicesCategories();
        loadServices();
        loadProductCategory();
        loadProductTopics();
        loadProductSubs();
    }

    private void loadServices() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseServices()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(services -> {
                    if (services != null) {
                        getDataManager().getServiceOrderUserInput().setArrayAppyService(services);
                        getNavigator().openMainActivity();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void loadServicesCategories() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> {
                    if (categories != null) {
                        getDataManager().getServiceOrderUserInput().setArrayAppyServiceCategory(categories);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void loadProductCategory() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseProductCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::addProductCategories, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void addProductCategories(ArrayList<ProductCategory> categories) {
        getCompositeDisposable().add(getDataManager().addProductCategories(categories)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void addProductSubs(ArrayList<ProductSub> subs) {
        getCompositeDisposable().add(getDataManager().addProductSubs(subs)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void addProductTopics(ArrayList<ProductTopic> topics) {
        getCompositeDisposable().add(getDataManager().addProductTopics(topics)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void loadProductSubs() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseProductTopics()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::addProductTopics, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    private void loadProductTopics() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseProductSubs()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::addProductSubs, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

}
