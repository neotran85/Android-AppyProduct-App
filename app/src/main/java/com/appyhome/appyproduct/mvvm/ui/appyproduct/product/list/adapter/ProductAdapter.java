package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ProductAdapter extends SampleAdapter {

    private String imageTestPath = "https://redbean2013.files.wordpress.com/2013/07/38361-paul_smith_iphone_5_case_strip_car.jpg";

    public ProductAdapter() {
        this.mItems = null;
    }

    @Override
    public void onClick(View view) {}

    public void addItems(Product[] results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    private ProductItemViewModel createViewModel(Product product, ProductItemNavigator navigator) {
        ProductItemViewModel itemViewModel = new ProductItemViewModel();
        itemViewModel.title.set(product.product_name);
        itemViewModel.imageURL.set(product.avatar_name);
        itemViewModel.setIdCategory(product.category_id);
        itemViewModel.setIdProduct(product.id);
        itemViewModel.price.set("RM " + product.price);
        itemViewModel.setNavigator(navigator);
        return itemViewModel;
    }

    public void addItems(RealmResults<Product> results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    @Override
    protected ProductItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductBinding itemViewBinding = ViewItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductItemViewHolder(itemViewBinding);
    }

    public class ProductItemViewHolder extends BaseViewHolder {

        private ViewItemProductBinding mBinding;

        public ViewItemProductBinding getBinding() {
            return mBinding;
        }

        private ProductItemViewHolder(ViewItemProductBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onBind(int position) {
            ProductItemViewModel viewModel = (ProductItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(view -> {
                    Object tag = view.getTag();
                    if (tag instanceof ProductItemViewModel) {
                        ProductItemViewModel tempModel = (ProductItemViewModel) tag;
                        tempModel.getNavigator().showContent(ProductAdapter.this, view, viewModel.getIdProduct());
                    }
                });
            }
        }
    }
}