package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductItemViewModel extends BaseViewModel<ProductItemNavigator> {

    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> imageURL = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("0");
    public ObservableField<Float> rate = new ObservableField<>(0f);
    public ObservableField<String> rateCount = new ObservableField<>("");
    public ObservableField<String> favoriteCount = new ObservableField<>("");
    public ObservableField<Boolean> isFavorite = new ObservableField<>(false);

    public ProductItemViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private int idProduct;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void updateProductFavorite(int position) {
        getCompositeDisposable().add(getDataManager().addOrRemoveFavorite(idProduct, "1234")
                .observeOn(getSchedulerProvider().ui())
                .subscribe(value -> {
                    isFavorite.set(value);
                    int count = Integer.valueOf(favoriteCount.get());
                    count = value ? count + 1 : count - 1;
                    favoriteCount.set(count + "");
                    getNavigator().notifyItemChanged(position);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public void addProductToCart() {
        getCompositeDisposable().add(getDataManager().addProductToCart(idProduct, "1234")
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCart -> {
                    if (productCart != null) {
                        getNavigator().showAlert("Added to cart");
                        getNavigator().updateCartCount();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
