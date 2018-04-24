package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategorySubBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.AppyProductConstants;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class SubCategoryAdapter extends SampleAdapter<ProductSub, CategoryItemNavigator> {

    private CategoryItemNavigator mNavigator;
    private ArrayList<CategoryItemViewModel> mActiveItems;
    private CategoryItemViewModel allItemViewModel;

    public SubCategoryAdapter() {
        this.mItems = null;
    }

    @Override
    public void recycle() {
        if (mItems != null) {
            mItems.clear();
            mItems = null;
        }
    }

    @Override
    protected int getLoadingItemLayout() {
        return R.layout.view_item_sub_category_loading;
    }

    public void clickViewModel(CategoryItemViewModel viewModel) {
        viewModel.isHighLight = !viewModel.isHighLight;
        viewModel.isActive.set(viewModel.isHighLight);
        if (viewModel.getIdCategory() == 0) {
            for (CategoryItemViewModel item : mActiveItems) {
                item.isHighLight = false;
                item.isActive.set(false);
            }
            mActiveItems.clear();
            if (viewModel.isHighLight) {
                mActiveItems.add(viewModel);
            }
        } else {
            if (viewModel.isHighLight) {
                mActiveItems.add(viewModel);
            } else {
                mActiveItems.remove(viewModel);
            }
            if (allItemViewModel != null) {
                allItemViewModel.isHighLight = false;
                allItemViewModel.isActive.set(false);
                mActiveItems.remove(allItemViewModel);
            }
        }
    }

    public String getSelectedSubIds() {
        if (mActiveItems.contains(allItemViewModel)) {
            return allItemViewModel.getSubIds();
        } else {
            String ids = "";
            for (CategoryItemViewModel item : mActiveItems) {
                ids = ids + item.getIdCategory() + ",";
            }
            return ids;
        }
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
        mActiveItems = new ArrayList<>();
        if (items != null && items.size() > 0) {
            allItemViewModel = new CategoryItemViewModel();
            allItemViewModel.title.set("ALL PRODUCTS");
            allItemViewModel.imageURL.set(AppyProductConstants.RESOURCE_URL.PRODUCT_CATEGORY_ALL_SUB_URL);
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
                itemViewModel.setSubIds(item.id + "");
                allSubIds = allSubIds + item.id + ",";
                mItems.add(itemViewModel);
            }
            allItemViewModel.setSubIds(allSubIds);
            clickViewModel(allItemViewModel);
        }
    }
}