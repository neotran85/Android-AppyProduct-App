package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ShippingAddressViewModel extends BaseViewModel<ShippingAddressNavigator> {
    public ShippingAddressViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
