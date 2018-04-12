package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategorySubBinding;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class SubCategoryAdapter extends SampleAdapter<ProductSub, CategoryItemNavigator> {

    private CategoryItemViewModel mCurrentClickedViewModel = null;

    private CategoryItemNavigator mNavigator;

    public SubCategoryAdapter() {
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
        return R.layout.view_item_sub_category_loading;
    }

    public void clickViewModel(CategoryItemViewModel viewModel) {
        if (mCurrentClickedViewModel != viewModel) {
            viewModel.isHighLight = true;
            viewModel.isActive.set(true);
            if (mCurrentClickedViewModel != null) {
                mCurrentClickedViewModel.isHighLight = false;
                mCurrentClickedViewModel.isActive.set(false);
            }
            mCurrentClickedViewModel = viewModel;
        } else {
            mCurrentClickedViewModel = null;
            viewModel.isHighLight = false;
            viewModel.isActive.set(false);
        }
    }

    public String getSelectedSubIds() {
        return mCurrentClickedViewModel != null ? mCurrentClickedViewModel.getSubIds() : "";
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    @Override
    protected SubCategoryItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCategorySubBinding itemViewBinding = ViewItemProductCategorySubBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubCategoryItemViewHolder(itemViewBinding, this, mNavigator);
    }

    @Override
    public void addItems(RealmResults<ProductSub> items, CategoryItemNavigator navigator) {
        mNavigator = navigator;
        mItems = new ArrayList<>();
        if (items != null && items.size() > 0) {
            CategoryItemViewModel allItemViewModel = new CategoryItemViewModel();
            allItemViewModel.title.set("ALL PRODUCTS");
            allItemViewModel.imageURL.set("images/product/sub/all_sub.png");
            allItemViewModel.setIdCategory(0);
            allItemViewModel.setNavigator(navigator);
            allItemViewModel.isSub = true;
            mItems.add(allItemViewModel);
            String allSubIds = "";
            for (ProductSub item : items) {
                CategoryItemViewModel itemViewModel = new CategoryItemViewModel();
                itemViewModel.title.set(item.name);
                itemViewModel.imageURL.set(item.thumbnail);
                itemViewModel.setIdCategory(item.id);
                itemViewModel.setNavigator(navigator);
                itemViewModel.isSub = true;
                itemViewModel.setSubIds(item.sub_ids);
                allSubIds = allSubIds + item.sub_ids + ",";
                mItems.add(itemViewModel);
            }
            allItemViewModel.setSubIds(allSubIds);
        }
    }
}