package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
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
    private String variantModelId;
    private long productCartId;

    public void setVariantModelId(String id) {
        variantModelId = id;
    }

    public ProductCartItemViewModel() {
        super();
    }

    public ProductCartItemViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void removeProductCartItem() {
        getCompositeDisposable().add(getDataManager().removeProductCartItem(productCartId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // REMOVED ADDED
                }, Crashlytics::logException));
    }

    public void updateProductCartItem() {
        getCompositeDisposable().add(getDataManager().updateProductCartItem(productCartId, checked.get(), Integer.valueOf(amount.get()))
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE ADDED
                }, Crashlytics::logException));
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
