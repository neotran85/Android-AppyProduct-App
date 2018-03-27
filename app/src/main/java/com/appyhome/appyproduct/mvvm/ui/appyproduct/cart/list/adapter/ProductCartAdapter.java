package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartEmptyBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;

public class ProductCartAdapter extends SampleAdapter<ProductCart, ProductCartItemNavigator> {

    public HashMap<String, ArrayList<ProductCartItemViewModel>> viewModelManager;
    private ProductCartListViewModel mProductCartListViewModel;
    private ProductCartItemNavigator mNavigator;

    private ArrayList<ProductCartItemViewModel> mDeletedItems = null;

    public ProductCartAdapter() {
        this.mItems = null;
    }

    public void setMainViewModel(ProductCartListViewModel viewModel) {
        mProductCartListViewModel = viewModel;
    }

    public ArrayList<BaseViewModel> getItems() {
        return mItems;
    }

    public void recycle() {
        mProductCartListViewModel = null;
        mNavigator = null;
        viewModelManager.clear();
        mDeletedItems.clear();
        mItems.clear();
        viewModelManager = null;
        mDeletedItems = null;
        mItems = null;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof ProductCartItemViewModel) {
            ProductCartItemViewModel viewModel = (ProductCartItemViewModel) tag;
            viewModel.getNavigator().showContent(this, view, viewModel.getProductId(), getIndexOfItem(viewModel));
        }
    }

    private int getIndexOfItem(ProductCartItemViewModel item) {
        if (mItems != null && mItems.size() > 0) {
            return mItems.indexOf(item);
        }
        return -1;
    }

    public void onUpdateDatabase() {
        if (mItems != null && mItems.size() > 0) {
            for (BaseViewModel item : mItems) {
                ProductCartItemViewModel cartItem = (ProductCartItemViewModel) item;
                cartItem.updateProductCartItem();
            }
        }
        if (mDeletedItems != null && mDeletedItems.size() > 0) {
            for (BaseViewModel item : mDeletedItems) {
                ProductCartItemViewModel cartItem = (ProductCartItemViewModel) item;
                cartItem.removeProductCartItem();
            }
        }
    }

    private ProductCartItemViewModel createViewModel(ProductCart productCart, ProductCartItemNavigator navigator) {
        ProductCartItemViewModel itemViewModel = new ProductCartItemViewModel(mProductCartListViewModel.getDataManager(),
                mProductCartListViewModel.getSchedulerProvider());
        itemViewModel.title.set(productCart.product_name);
        itemViewModel.imageURL.set(productCart.product_avatar);
        itemViewModel.setProductCartId(productCart.id);
        itemViewModel.setProductId(productCart.product_id);
        itemViewModel.sellerName.set(productCart.seller_name);
        itemViewModel.amount.set(productCart.amount + "");
        itemViewModel.price.set(productCart.price + "");
        itemViewModel.setNavigator(navigator);
        itemViewModel.checked.set(productCart.checked);
        return itemViewModel;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_product_cart_empty;
    }

    public void updateTotalCost() {
        float totalCost = 0;
        if (mItems != null && mItems.size() > 0) {
            for (BaseViewModel item : mItems) {
                ProductCartItemViewModel cartItem = (ProductCartItemViewModel) item;
                if (cartItem.checked.get()) {
                    float price = Float.valueOf(cartItem.price.get());
                    int amount = Integer.valueOf(cartItem.amount.get());
                    totalCost = totalCost + (price * amount);
                }
            }
            mProductCartListViewModel.totalCost.set(DataUtils.roundNumber(totalCost, 2) + "");
        }
    }


    private void addProductCartToStoreBySellerName(ProductCartItemViewModel cartItem) {
        String sellerName = cartItem.sellerName.get();
        ArrayList<ProductCartItemViewModel> array = viewModelManager.get(sellerName);
        if (array == null) {
            array = new ArrayList<>();
            cartItem.isFirstProductOfStore.set(true);
            viewModelManager.put(sellerName, array);
        } else {
            cartItem.isFirstProductOfStore.set(false);
        }
        array.add(cartItem);
    }

    public void replaceItem(ProductCart cart, int pos) {
        mItems.add(pos, createViewModel(cart, mNavigator));
    }

    public void addItems(RealmResults<ProductCart> results, ProductCartItemNavigator navigator) {
        mItems = new ArrayList<>();
        mDeletedItems = new ArrayList<>();
        mNavigator = navigator;
        if (viewModelManager != null) {
            viewModelManager.clear();
        }
        viewModelManager = new HashMap();
        if (results != null) {
            for (ProductCart item : results) {
                ProductCartItemViewModel cartItem = createViewModel(item, mNavigator);
                addProductCartToStoreBySellerName(cartItem);
                mItems.add(cartItem);
            }
            for (String sellerName : viewModelManager.keySet()) {
                updateCheckAllBySellerName(sellerName);
            }
        }
        updateTotalCost();
        updateIfCheckedAll();
    }

    @Override
    protected BaseViewHolder getEmptyHolder(ViewGroup parent) {
        ViewItemProductCartEmptyBinding itemViewBinding = ViewItemProductCartEmptyBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCartItemEmptyViewHolder(itemViewBinding, mNavigator);
    }

    @Override
    protected ProductCartItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCartItemBinding itemViewBinding = ViewItemProductCartItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCartItemViewHolder(itemViewBinding, this);
    }

    public void checkAllItems(boolean isChecked) {
        if (mItems != null && mItems.size() > 0) {
            for (BaseViewModel item : mItems) {
                ProductCartItemViewModel cartItem = (ProductCartItemViewModel) item;
                cartItem.checked.set(isChecked);
                cartItem.checkedAll.set(isChecked);
            }
            updateTotalCost();
        }
    }

    public void checkIfCartEmpty() {
        mProductCartListViewModel.isCartEmpty.set(isCartEmpty());
    }

    private boolean isCartEmpty() {
        return getItems() == null || getItems().size() <= 0;
    }

    public void updateIfCheckedAll() {
        mProductCartListViewModel.isCheckedAll.set(isAllItemsChecked());
    }

    private boolean isAllItemsChecked() {
        if (mItems != null && mItems.size() > 0) {
            for (BaseViewModel item : mItems) {
                ProductCartItemViewModel cartItem = (ProductCartItemViewModel) item;
                if (!cartItem.checked.get()) return false;
            }
            return true;
        }
        return false;
    }

    public void removeCartItem(ProductCartItemViewModel itemViewModel) {
        if (!isCartEmpty()) {
            String sellerName = itemViewModel.sellerName.get();
            ArrayList<ProductCartItemViewModel> array = viewModelManager.get(sellerName);
            mDeletedItems.add(itemViewModel);
            int pos = getItems().indexOf(itemViewModel);
            getItems().remove(itemViewModel);
            array.remove(itemViewModel);
            notifyItemRemoved(pos);

            // UPDATE IF FIRST ITEM OF STORE IS REMOVED
            if (array != null && array.size() > 0) {
                ProductCartItemViewModel firstItem = array.get(0);
                firstItem.isFirstProductOfStore.set(true);
                updateCheckAllBySellerName(sellerName);
            }

            // CHECK IF EMPTY CART
            checkIfCartEmpty();
        }
    }

    private boolean isAllItemsCheckedBySellerName(String sellerName) {
        ArrayList<ProductCartItemViewModel> array = viewModelManager.get(sellerName);
        if (array != null && array.size() > 0) {
            for (ProductCartItemViewModel item : array) {
                if (!item.checked.get()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private ProductCartItemViewModel getFirstItemOfStore(String sellerName) {
        ArrayList<ProductCartItemViewModel> array = viewModelManager.get(sellerName);
        if (array != null && array.size() > 0) {
            return array.get(0);
        }
        return null;
    }

    public void updateCheckAllBySellerName(String sellerName) {
        boolean isAllItemsCheckedBySellerName = isAllItemsCheckedBySellerName(sellerName);
        ProductCartItemViewModel firstItem = getFirstItemOfStore(sellerName);
        if (firstItem != null) firstItem.checkedAll.set(isAllItemsCheckedBySellerName);
        updateTotalCost();
        updateIfCheckedAll();
    }

    public void pressCheckAllBySellerName(boolean isChecked, String sellerName) {
        ArrayList<ProductCartItemViewModel> array = viewModelManager.get(sellerName);
        if (array != null && array.size() > 0) {
            for (ProductCartItemViewModel item : array) {
                item.checked.set(isChecked);
            }
            array.get(0).checkedAll.set(isChecked);
        }
        updateTotalCost();
        updateIfCheckedAll();
    }

    public void updateEditModeBySellerName(boolean isEditable, String sellerName) {
        ArrayList<ProductCartItemViewModel> array = viewModelManager.get(sellerName);
        if (array != null && array.size() > 0) {
            for (ProductCartItemViewModel item : array) {
                item.isEditMode.set(isEditable);
            }
        }
    }
}