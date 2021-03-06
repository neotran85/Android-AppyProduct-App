package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategoryBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.AppyProductConstants;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class CategoryAdapter extends SampleAdapter<ProductCategory, CategoryItemNavigator> {

    private CategoryItemViewModel mCurrentClickedViewModel = null;
    private CategoryItemNavigator mNavigator;

    public CategoryAdapter() {
        this.mItems = null;
    }

    @Override
    public void recycle() {
        if (mItems != null) {
            mItems.clear();
            mItems = null;
        }
        mCurrentClickedViewModel = null;
    }

    @Override
    protected int getLoadingItemLayout() {
        return R.layout.view_item_product_category_loading;
    }

    public void clickViewModel(CategoryItemViewModel viewModel) {
        if (mCurrentClickedViewModel != viewModel) {
            viewModel.isHighLight = true;
            if (mCurrentClickedViewModel != null) {
                mCurrentClickedViewModel.isHighLight = false;
                int positionOld = mItems.indexOf(mCurrentClickedViewModel);
                notifyItemChanged(positionOld);
            }
            int positionNew = mItems.indexOf(viewModel);
            notifyItemChanged(positionNew);
            mCurrentClickedViewModel = viewModel;
        }
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    @Override
    public ProductCategoryItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCategoryBinding itemViewBinding = ViewItemProductCategoryBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCategoryItemViewHolder(itemViewBinding, this);
    }

    @Override
    public void addItems(RealmResults<ProductCategory> items, CategoryItemNavigator navigator) {
        mNavigator = navigator;
        mItems = new ArrayList<>();
        CategoryItemViewModel allItemViewModel = new CategoryItemViewModel();
        allItemViewModel.title.set("ALL CATEGORIES");
        allItemViewModel.imageURL.set(AppyProductConstants.RESOURCE_URL.PRODUCT_CATEGORY_ALL_SUB_URL);
        allItemViewModel.setIdCategory(0);
        allItemViewModel.setNavigator(navigator);
        mItems.add(allItemViewModel);
        if (items != null && items.size() > 0) {
            for (ProductCategory item : items) {
                CategoryItemViewModel itemViewModel = new CategoryItemViewModel();
                itemViewModel.title.set(item.name);
                itemViewModel.imageURL.set(item.thumbnail);
                itemViewModel.setIdCategory(item.id);
                itemViewModel.setNavigator(navigator);
                mItems.add(itemViewModel);
            }
        }
    }

    public class ProductCategoryItemViewHolder extends CategoryItemViewHolder {

        protected ProductCategoryItemViewHolder(ViewItemProductCategoryBinding binding, SampleAdapter adapter) {
            super(binding, adapter, mNavigator);
        }

        private void createCategoryView() {
            View view = mBinding.llItemCategoryView;
            boolean isHighLight = mBinding.getViewModel().isHighLight;
            view.setBackgroundResource(isHighLight ? R.color.white : R.color.transparent);
            Context context = view.getContext();
            mBinding.tvTitleProduct.setTextColor(ContextCompat.getColor(context, isHighLight ? R.color.colorAccent : R.color.white));
        }

        @Override
        public void setViewModel(CategoryItemViewModel viewModel) {
            mBinding.setViewModel(viewModel);
            createCategoryView();
        }
    }
}