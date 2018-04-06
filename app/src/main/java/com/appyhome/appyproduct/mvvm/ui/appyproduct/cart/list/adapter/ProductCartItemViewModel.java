package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
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
    public ObservableField<String> variantStock = new ObservableField<>("");
    public ObservableField<String> sellerName = new ObservableField<>("");
    public ObservableField<Boolean> checked = new ObservableField<>(false);
    public ObservableField<Boolean> checkedAll = new ObservableField<>(false);

    public ObservableField<Boolean> isEditVariantMode = new ObservableField<>(false);

    public ObservableField<Boolean> isFirstProductOfStore = new ObservableField<>(false);

    public ObservableField<Boolean> isEditMode = new ObservableField<>(false);

    private int variantStockNumber = 0;

    private int productId;

    private String variantModelId;

    private long productCartId;

    public int getVariantStockNumber() {
        return variantStockNumber;
    }

    public void setVariantStockNumber(int stockNumber) {
        variantStockNumber = stockNumber;
    }

    public String getVariantModelId() {
        return variantModelId;
    }

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
        getCompositeDisposable().add(getDataManager().updateProductCartItem(productCartId, checked.get(), Integer.valueOf(amount.get()), "")
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

    public void updateProductCart(ProductCart productCart, ProductCartItemNavigator navigator) {
        title.set(productCart.product_name);
        imageURL.set(productCart.product_avatar);
        setProductCartId(productCart.id);
        setProductId(productCart.product_id);
        sellerName.set(productCart.seller_name);
        amount.set(productCart.amount + "");
        price.set(productCart.price + "");
        setNavigator(navigator);
        checked.set(productCart.checked);
        variationName.set(productCart.variant_name);
        setVariantModelId(productCart.variant_model_id);
        variantStock.set("(Stock: " + productCart.variant_stock + ")");
        setVariantStockNumber(productCart.variant_stock);
    }

    public ProductCartItemViewModel clone() {
        ProductCartItemViewModel itemViewModel = new ProductCartItemViewModel(getDataManager(), getSchedulerProvider());
        itemViewModel.title.set(productCart.title.get());
        itemViewModel.isFirstProductOfStore.set(true);
        itemViewModel.imageURL.set(productCart.imageURL.get());
        itemViewModel.setProductCartId(productCart.getProductCartId());
        itemViewModel.setProductId(productCart.getProductId());
        itemViewModel.sellerName.set(productCart.sellerName.get());
        itemViewModel.amount.set(productCart.amount.get());
        itemViewModel.price.set(productCart.price.get());
        itemViewModel.setNavigator(productCart.getNavigator());
        itemViewModel.checked.set(productCart.checked.get());
        //Variant Display
        itemViewModel.variationName.set(productCart.variationName.get());
        itemViewModel.setVariantModelId(productCart.getVariantModelId());
        itemViewModel.variantStock.set(productCart.variantStock.get());
        itemViewModel.setVariantStockNumber(productCart.getVariantStockNumber());
        return itemViewModel;
    }
}
