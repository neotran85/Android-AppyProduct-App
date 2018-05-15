package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemConfirmationBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.ConfirmationNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;

public class CartAdapter extends SampleAdapter<ProductCart, ConfirmationNavigator> {

    public HashMap<String, ArrayList<CartItemViewModel>> viewModelManager;

    public CartAdapter() {
        this.mItems = null;
    }

    public ArrayList<BaseViewModel> getItems() {
        return mItems;
    }

    @Override
    public void recycle() {
        mItems.clear();
        mItems = null;
    }

    private CartItemViewModel createViewModel(ProductCart productCart, ConfirmationNavigator navigator) {
        CartItemViewModel itemViewModel = new CartItemViewModel(navigator.getViewModel());
        itemViewModel.setSellerId(productCart.seller_id);
        itemViewModel.title.set(productCart.product_name);
        itemViewModel.imageURL.set(productCart.product_image);
        itemViewModel.setProductCartId(productCart.cart_id);
        itemViewModel.setProductId(productCart.product_id);
        itemViewModel.sellerName.set(productCart.seller_name);
        itemViewModel.amount.set(productCart.amount + "");
        itemViewModel.price.set(productCart.price);
        itemViewModel.checked.set(productCart.checked);
        itemViewModel.variationName.set(productCart.variant_name);
        return itemViewModel;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_product_cart_empty;
    }

    public void addItems(RealmResults<ProductCart> results, int addressId, ConfirmationNavigator navigator) {
        mItems = new ArrayList<>();
        if (viewModelManager != null) {
            viewModelManager.clear();
        }
        viewModelManager = new HashMap();
        if (results != null && results.size() > 0) {
            ArrayList<String> cartIds = new ArrayList<>();
            for (ProductCart item : results) {
                cartIds.add(item.cart_id + "");
            }
            String cartIdsStr = TextUtils.join(",", cartIds);
            for (ProductCart item : results) {
                CartItemViewModel cartItem = createViewModel(item, navigator);
                cartItem.setCartIds(cartIdsStr);
                cartItem.setAddressId(addressId);
                addProductCartToStoreBySellerName(cartItem);
                mItems.add(cartItem);
            }
            updateTotalCostOfStore();
        }
    }

    public int getTotalStores() {
        return viewModelManager.keySet().size();
    }

    private void addProductCartToStoreBySellerName(CartItemViewModel cartItem) {
        String sellerName = cartItem.sellerName.get();
        ArrayList<CartItemViewModel> array = viewModelManager.get(sellerName);
        if (array == null) {
            array = new ArrayList<>();
            cartItem.isFirstProductOfStore.set(true);
            viewModelManager.put(sellerName, array);
        } else {
            cartItem.isFirstProductOfStore.set(false);
        }
        array.add(cartItem);
    }

    private void updateTotalCostOfStore() {
        for (String sellerName : viewModelManager.keySet()) {
            updateTotalCostOfStore(sellerName);
        }
    }

    private void updateTotalCostOfStore(String sellerName) {
        ArrayList<CartItemViewModel> array = viewModelManager.get(sellerName);
        double totalCost = 0;
        if (array != null && array.size() > 0) {
            for (CartItemViewModel item : array) {
                totalCost = totalCost + Double.valueOf(item.price.get()) * Integer.valueOf(item.amount.get());
                totalCost = DataUtils.roundNumber(totalCost, 2);
            }
            for (CartItemViewModel item : array) {
                item.totalCostOfStore.set(totalCost);
            }
        }
    }

    @Override
    protected CartItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCartItemConfirmationBinding itemViewBinding = ViewItemProductCartItemConfirmationBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartItemViewHolder(itemViewBinding, this);
    }

    @Override
    protected void addItems(RealmResults<ProductCart> items, ConfirmationNavigator navigator) {
        // DO NOTHING
    }
}