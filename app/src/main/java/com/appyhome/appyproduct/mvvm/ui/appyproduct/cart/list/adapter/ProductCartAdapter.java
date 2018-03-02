package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;

public class ProductCartAdapter extends SampleAdapter {

    private String imageTestPath = "https://redbean2013.files.wordpress.com/2013/07/38361-paul_smith_iphone_5_case_strip_car.jpg";
    private ProductCartListViewModel mViewModel;
    public boolean isChangedByUser = false;
    public HashMap<String, ArrayList<ProductCartItemViewModel>> viewModelManager;

    public ProductCartAdapter(ProductCartListViewModel viewModel) {
        this.mItems = null;
        mViewModel = viewModel;
    }

    public ArrayList<BaseViewModel> getItems() {
        return mItems;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof ProductCartItemViewModel) {
            ProductCartItemViewModel viewModel = (ProductCartItemViewModel) tag;
            viewModel.getNavigator().showContent(this, view, viewModel.getProductId());
        }
    }

    public void onUpdateDatabase() {
        if (isChangedByUser) {
            if (mItems != null && mItems.size() > 0) {
                for (BaseViewModel item : mItems) {
                    ProductCartItemViewModel cartItem = (ProductCartItemViewModel) item;
                    cartItem.productCartUpdate();
                }
            }
            isChangedByUser = false;
        }
    }

    private ProductCartItemViewModel createViewModel(ProductCart productCart, ProductCartItemNavigator navigator) {
        ProductCartItemViewModel itemViewModel = new ProductCartItemViewModel(mViewModel.getDataManager(),
                mViewModel.getSchedulerProvider());
        itemViewModel.title.set(productCart.product_name);
        //itemViewModel.imageURL.set(productCart.product_avatar);
        itemViewModel.imageURL.set(imageTestPath);
        itemViewModel.setProductCartId(productCart.id);
        itemViewModel.setProductId(productCart.product_id);
        itemViewModel.sellerName.set(productCart.seller_name);
        itemViewModel.amount.set(productCart.amount + "");
        itemViewModel.price.set(productCart.price + "");
        itemViewModel.setNavigator(navigator);
        itemViewModel.checked.set(productCart.checked);
        return itemViewModel;
    }

    private void addProductCartToStore(ProductCartItemViewModel cartItem) {
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

    public void addItems(RealmResults<ProductCart> results, ProductCartItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (viewModelManager != null) {
            viewModelManager.clear();
        }
        viewModelManager = new HashMap();
        if (results != null) {
            for (ProductCart item : results) {
                ProductCartItemViewModel cartItem = createViewModel(item, navigator);
                addProductCartToStore(cartItem);
                mItems.add(cartItem);
            }
        }
    }

    @Override
    protected ProductCartItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCartItemBinding itemViewBinding = ViewItemProductCartItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCartItemViewHolder(itemViewBinding, this);
    }
}