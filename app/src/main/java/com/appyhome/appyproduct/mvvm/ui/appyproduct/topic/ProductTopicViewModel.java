package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductTopicViewModel extends BaseViewModel<ProductTopicNavigator> {

    public ProductTopicViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllProductTopics() {
        getCompositeDisposable().add(getDataManager().getAllProductTopics()
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(topics -> {
                    // DONE GET
                    if (topics != null && topics.size() > 0) {
                        getNavigator().showTopics(topics);
                    }
                }, Crashlytics::logException));
    }
}
