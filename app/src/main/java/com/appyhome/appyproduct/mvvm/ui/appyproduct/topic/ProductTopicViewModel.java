package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ProductTopicViewModel extends BaseViewModel<ProductTopicNavigator> {

    public ProductTopicViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
