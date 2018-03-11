package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ProductDetailViewModel extends BaseViewModel<ProductDetailNavigator> {
    public ProductDetailViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
