package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ProductTrackingViewModel extends BaseViewModel<ProductTrackingNavigator> {
    public ProductTrackingViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
