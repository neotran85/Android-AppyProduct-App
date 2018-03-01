package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ProductCartAdapter extends SampleAdapter {

    private String imageTestPath = "https://redbean2013.files.wordpress.com/2013/07/38361-paul_smith_iphone_5_case_strip_car.jpg";

    public ProductCartAdapter() {
        this.mItems = null;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof ProductCartItemViewModel) {
            ProductCartItemViewModel viewModel = (ProductCartItemViewModel) tag;
            viewModel.getNavigator().showContent(this, view, viewModel.getIdProduct());
        }
    }

    public void addItems(ProductCart[] results, ProductCartItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (ProductCart item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    private ProductCartItemViewModel createViewModel(ProductCart productCart, ProductCartItemNavigator navigator) {
        ProductCartItemViewModel itemViewModel = new ProductCartItemViewModel();
        itemViewModel.title.set(productCart.product_name);
        itemViewModel.imageURL.set(productCart.product_avatar);
        itemViewModel.setIdProduct(productCart.product_id);
        itemViewModel.sellerName.set(productCart.seller_name);
        itemViewModel.amount.set(productCart.amount);
        itemViewModel.setNavigator(navigator);
        itemViewModel.amount.set(productCart.amount);
        return itemViewModel;
    }

    public void addItems(RealmResults<ProductCart> results, ProductCartItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (ProductCart item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    @Override
    protected ProductCartItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCartItemBinding itemViewBinding = ViewItemProductCartItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCartItemViewHolder(itemViewBinding);
    }

    public class ProductCartItemViewHolder extends BaseViewHolder {

        private ViewItemProductCartItemBinding mBinding;

        public ViewItemProductCartItemBinding getBinding() {
            return mBinding;
        }

        private ProductCartItemViewHolder(ViewItemProductCartItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            ProductCartItemViewModel viewModel = (ProductCartItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(ProductCartAdapter.this);
            }
        }
    }
}