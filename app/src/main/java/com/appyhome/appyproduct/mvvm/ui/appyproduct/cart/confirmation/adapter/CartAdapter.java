package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemConfirmationBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;

public class CartAdapter extends SampleAdapter {

    public HashMap<String, ArrayList<CartItemViewModel>> viewModelManager;
    private String imageTestPath = "https://redbean2013.files.wordpress.com/2013/07/38361-paul_smith_iphone_5_case_strip_car.jpg";

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

    private CartItemViewModel createViewModel(ProductCart productCart) {
        CartItemViewModel itemViewModel = new CartItemViewModel();
        itemViewModel.title.set(productCart.product_name);
        itemViewModel.imageURL.set(imageTestPath);
        itemViewModel.setProductCartId(productCart.id);
        itemViewModel.setProductId(productCart.product_id);
        itemViewModel.sellerName.set(productCart.seller_name);
        itemViewModel.amount.set(productCart.amount + "");
        itemViewModel.price.set(productCart.price + "");
        itemViewModel.checked.set(productCart.checked);
        return itemViewModel;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_product_cart_empty;
    }

    public void addItems(RealmResults<ProductCart> results) {
        mItems = new ArrayList<>();
        if (viewModelManager != null) {
            viewModelManager.clear();
        }
        viewModelManager = new HashMap();
        if (results != null) {
            for (ProductCart item : results) {
                CartItemViewModel cartItem = createViewModel(item);
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
        float totalCost = 0;
        if (array != null && array.size() > 0) {
            for (CartItemViewModel item : array) {
                totalCost = totalCost + Float.valueOf(item.price.get()) * Integer.valueOf(item.amount.get());
                totalCost = DataUtils.roundNumber(totalCost, 2);
            }
            for (CartItemViewModel item : array) {
                item.totalCostOfStore.set(totalCost + "");
            }
        }
    }

    @Override
    public void onClick(View view) {
        // DO NOTHING
    }

    @Override
    protected CartItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCartItemConfirmationBinding itemViewBinding = ViewItemProductCartItemConfirmationBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartItemViewHolder(itemViewBinding, this);
    }
}