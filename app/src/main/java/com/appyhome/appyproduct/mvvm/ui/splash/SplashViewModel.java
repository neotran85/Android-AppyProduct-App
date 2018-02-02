package com.appyhome.appyproduct.mvvm.ui.splash;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyServiceCategory;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class SplashViewModel extends BaseViewModel<SplashActivity> {

    public SplashViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setUp() {
        loadCategories();
    }

    public void loadServices() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseServices()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ArrayList<AppyService>>() {
                    @Override
                    public void accept(ArrayList<AppyService> services) throws Exception {
                        if (services != null) {
                            ServiceOrderInfo.getInstance().setArrayAppyService(services);
                            getNavigator().openMainActivity();
                        }
                    }
                }));
    }
    public void loadCategories() {
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ArrayList<AppyServiceCategory>>() {
                    @Override
                    public void accept(ArrayList<AppyServiceCategory> categories) throws Exception {
                        if (categories != null) {
                            ServiceOrderInfo.getInstance().setArrayAppyServiceCategory(categories);
                            loadServices();
                        }
                    }
                }));
    }

}
