package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ListProductOrderViewModel extends BaseViewModel<ListProductOrderNavigator> {
    public ListProductOrderViewModel(DataManager dataManager,
                                     SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
