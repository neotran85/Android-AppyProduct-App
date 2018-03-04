package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class AddressItemViewModel extends BaseViewModel<AddressItemNavigator> {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<Boolean> checked = new ObservableField<>(false);

    public AddressItemViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public AddressItemViewModel() {
        super();
    }
}
