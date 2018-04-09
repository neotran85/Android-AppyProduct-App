package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.databinding.ObservableField;
import android.util.Log;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddToCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.DeleteCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartRequest;
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
    public ObservableField<Boolean> isFirstProductOfStore = new ObservableField<>(false);
    public ObservableField<Boolean> isEditMode = new ObservableField<>(false);
    public ObservableField<String> alertText = new ObservableField<>("");

    private int variantStockNumber = 0;

    private int productId;

    private String variantModelId;

    private long productCartId;

    private int variantId;

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
        getCompositeDisposable().add(getDataManager().removeProductCartItem(getProductCartId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // REMOVE FROM LOCAL DB SUCCESSFULLY
                    Log.v("removeProductCartItem", "REMOVED FROM DB SUCCESSFULLY");
                }, Crashlytics::logException));
        // REMOVED FROM SERVER
        getCompositeDisposable().add(getDataManager().deleteProductToCart(new DeleteCartRequest(getProductId(), getVariantId()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(results -> {
                    if (results != null && results.isValid()) {
                        Log.v("deleteProductToCart", "DELETED FROM SERVER SUCCESSFULLY");
                    }
                }, Crashlytics::logException));
    }

    private void editProductCartServer() {
        getCompositeDisposable().add(getDataManager().editProductToCart(new EditCartRequest(getProductId(), getVariantId(), Integer.valueOf(amount.get())))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("editProductCartServer", "CART UPDATED SUCCESSFUL");
                    } else {
                        // IF NOT SUCCESS, THEN ADD A NEW ONE TO CART
                        getCompositeDisposable().add(getDataManager().addProductToCart(new AddToCartRequest(getProductId(), getVariantId(), Integer.valueOf(amount.get())))
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(result -> {
                                    if (result != null && result.isValid()) {
                                        Log.v("addProductCartServer", "CART UPDATED SUCCESSFUL");
                                    }
                                }, Crashlytics::logException));
                    }
                }, Crashlytics::logException));
    }

    public void updateProductCartItem() {
        editProductCartServer(); // EDIT CART SERVER
        getCompositeDisposable().add(getDataManager().updateProductCartItem(productCartId, checked.get(), Integer.valueOf(amount.get()), getVariantModelId())
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

    public void update(ProductCart productCart, ProductCartItemNavigator navigator) {
        title.set(productCart.product_name);
        imageURL.set(productCart.product_avatar);
        setProductCartId(productCart.id);
        setProductId(productCart.product_id);
        sellerName.set(productCart.seller_name);
        price.set(productCart.price + "");
        setNavigator(navigator);
        checked.set(productCart.checked);
        variationName.set(productCart.variant_name);
        setVariantModelId(productCart.variant_model_id);
        variantStock.set("(Stock: " + productCart.variant_stock + ")");
        setVariantStockNumber(productCart.variant_stock);
        setVariantId(productCart.variant_id);
        if (productCart.amount > productCart.variant_stock) {
            alertText.set("Sorry, unable to add more than" + " " + productCart.variant_stock);
            amount.set(productCart.variant_stock + "");
            updateProductCartItemAmount(productCart.variant_stock);
        } else
            amount.set(productCart.amount + "");
        updateProductCartItem(); // UPDATE DB & SERVER DB
    }

    private void updateProductCartItemAmount(int amountForced) {
        getCompositeDisposable().add(getDataManager().updateProductCartItem(getProductCartId(), checked.get(),
                amountForced, getVariantModelId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCart -> {
                    // DO NOTHING
                }, Crashlytics::logException));
    }

    public ProductCartItemViewModel duplicate() {
        ProductCartItemViewModel itemViewModel = new ProductCartItemViewModel(getDataManager(), getSchedulerProvider());
        itemViewModel.title.set(title.get());
        itemViewModel.isFirstProductOfStore.set(isFirstProductOfStore.get());
        itemViewModel.imageURL.set(imageURL.get());
        itemViewModel.setProductCartId(getProductCartId());
        itemViewModel.setProductId(getProductId());
        itemViewModel.sellerName.set(sellerName.get());
        itemViewModel.amount.set(amount.get());
        itemViewModel.price.set(price.get());
        itemViewModel.setNavigator(getNavigator());
        itemViewModel.checked.set(checked.get());
        //Variant Display
        itemViewModel.variationName.set(variationName.get());
        itemViewModel.setVariantModelId(getVariantModelId());
        itemViewModel.variantStock.set(variantStock.get());
        itemViewModel.setVariantStockNumber(getVariantStockNumber());
        itemViewModel.setVariantId(getVariantId());
        return itemViewModel;
    }

    public void update(ProductVariant variant) {
        price.set(variant.price + "");
        variationName.set(variant.variant_name);
        setVariantModelId(variant.model_id);
        variantStock.set("(Stock: " + variant.quantity + ")");
        setVariantStockNumber(variant.quantity);
        setVariantId(variant.id);
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }
}
