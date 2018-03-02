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
    public ObservableField<Boolean> isFirstProductOfStore = new ObservableField<>(false);
    private ProductCart productCart;

    public ProductCartItemViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void saveProductCart(ProductCart productCart) {
        getCompositeDisposable().add(getDataManager().saveProductCart(productCart)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE ADDED
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public ProductCart getProductCart() {
        return productCart;
    }

    public int getIdProduct() {
        return productCart.product_id;
    }

    public void setProductCart(ProductCart productCart) {
        this.productCart = productCart;
    }
}
