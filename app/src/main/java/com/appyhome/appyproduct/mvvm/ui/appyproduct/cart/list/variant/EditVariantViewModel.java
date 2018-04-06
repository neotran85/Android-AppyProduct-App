package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class EditVariantViewModel extends BaseViewModel<EditVariantNavigator> {

    private ProductCartItemViewModel mProductCartItemViewModel;

    public void setProductCartItemViewModel(ProductCartItemViewModel viewModel) {
        mProductCartItemViewModel = viewModel;
    }

    public EditVariantViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void saveProductCartItem() {

    }
}
