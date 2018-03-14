package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortOption;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class FilterViewModel extends BaseViewModel<FilterNavigator> {
    public FilterViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
