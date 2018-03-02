package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductCartItemViewModel extends BaseViewModel<ProductCartItemNavigator> {

    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> imageURL = new ObservableField<>("");
    public ObservableField<String> amount = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("");
    public ObservableField<String> variationName = new ObservableField<>("");
    public ObservableField<String> sellerName = new ObservableField<>("");
    public ObservableField<Boolean> checked = new ObservableField<>(false);
    public ObservableField<Boolean> checkedAll = new ObservableField<>(false);

    public ObservableField<Boolean> isFirstProductOfStore = new ObservableField<>(false);

    public ObservableField<Boolean> isEditMode = new ObservableField<>(false);

    private int productId;
    private long productCartId;

    public ProductCartItemViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void productCartUpdate() {
        getCompositeDisposable().add(getDataManager().productCartUpdate(productCartId, checked.get(), Integer.valueOf(amount.get()))
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE ADDED
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getProductCartId() {
        return productCartId;
    }

    public void setProductCartId(long productCartId) {
        this.productCartId = productCartId;
    }
}
